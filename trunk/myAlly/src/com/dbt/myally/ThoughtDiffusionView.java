package com.dbt.myally;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ThoughtDiffusionView extends View {
	//If true, then thoughts bounce off the edges. Otherwise they reappear on
	//the other side
	private final boolean BOUNCE = false;
	//The number of times the thought should bounce/reappear
	private final int MAX_BOUNCES = 10;
	//The FPS of the game
	private final int FRAME_RATE = 30;
	//Used to start the runnable again at FRAME_RATE milliseconds
	private Handler _h;
	//Reusable Paint variable
	private Paint _paint;
	//Reusable Rect variable
	private Rect _bounds = new Rect();
	//Active thoughts
	private ArrayList<Thought> _thoughts;
	//Currently touched thought
	private Thought _activeThought = null;
	//Random positions and velocities for thoughts
	private Random _rand = new Random();
	//Used for flings
	private GestureDetector _detector;

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			update(); 		//Update locations/speed
			invalidate();  	//Update visuals
		}
	};

	public ThoughtDiffusionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		_h = new Handler();

		_paint = new Paint();
		_paint.setColor(Color.BLACK);
		_paint.setTextSize(100);
		_paint.setTextAlign(Paint.Align.CENTER);
		_paint.setStyle(Paint.Style.FILL);

		_detector = new GestureDetector(ThoughtDiffusionView.this.getContext(), new GestureListener());

		_thoughts = new ArrayList<Thought>();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//For each thought
		for(Thought t : _thoughts) {
			//Check if a thought was touched
			if(t.get_bounds().contains((int)event.getX(), (int)event.getY())) {
				t.set_touched(true);
				_activeThought = t;
				break;
			}
		}

		//Register flings
		return _detector.onTouchEvent(event);
	}

	public void addThought(String thought) {
		_thoughts.add(new Thought(thought));
	}

	protected void onDraw(Canvas c) {
		for(Thought t : _thoughts) {
			_paint.getTextBounds(t.get_thought(), 0, t.get_thought().length(), _bounds);

			_paint.setColor(Color.RED);
			c.drawRect(t.get_bounds(), _paint);
			_paint.setColor(Color.BLACK);
			c.drawText(t.get_thought(), t.get_x(), t.get_y(), _paint);
		}

		//Delay the next update by FRAME_RATE milliseconds
		_h.postDelayed(r, FRAME_RATE);
	}

	/**
	 * Go through all thoughts and update their x and y positions.
	 */
	private void update() {
		ArrayList<Thought> toRemove = new ArrayList<Thought>();

		for(Thought t : _thoughts) {
			t.translate();

			//Check if the thought has been touched
			if(t.is_touched()) {

				if(BOUNCE) {
					//Bounce it back on x
					if(t.get_bounds().right >= this.getWidth() || 
							t.get_bounds().left <= 0) {
						t.invert_x_direction();
						t.incrementBounces();
					}

					//Bounce it back on y
					if(t.get_bounds().bottom >= this.getHeight() || 
							t.get_bounds().top <= 0) {
						t.invert_y_direction();
						t.incrementBounces();
					}
				} else {
					if(t.get_bounds().left > this.getWidth()) {
						t.set_x(0);
						t.incrementBounces();
					} else if (t.get_bounds().right < 0) {
						t.set_x(this.getWidth());
						t.incrementBounces();
					}
					
					if(t.get_bounds().top > this.getHeight()) {
						t.set_y(0);
						t.incrementBounces();
					} else if (t.get_bounds().bottom < 0) {
						t.set_y(this.getWidth());
						t.incrementBounces();
					}
				}
			} else {
				//Remove it from the list of thoughts when it is off screen
				if (t.get_bounds().right < 0 && t.get_bounds().bottom < 0) {
					toRemove.add(t);
				} else if (t.get_bounds().top > this.getHeight() || 
						t.get_bounds().left > this.getWidth()) {
					toRemove.add(t);
				}
			}

			//After a certain amount of bounces, it is no longer "touhced"
			if(t.get_numBounces() > MAX_BOUNCES) {
				t.reset_numBounces();
				t.set_touched(false);
			}
		}

		//Remove thoughts that are no longer on the screen
		if(toRemove.size() > 0) {
			_thoughts.removeAll(toRemove);
		}

	}

	/**
	 * Thought container class. Contains x, y positions and velocities, as well
	 * as the thought message itself and whether or not the thought has been touched.
	 *
	 */
	private class Thought {
		private int _x = -1;
		private int _y = -1;

		private int _xVelocity;
		private int _yVelocity;

		private String _thought;

		private boolean _touched = false;

		private int _numBounces = 0;

		public Thought(String thought) {
			_thought = thought;

			_x = _rand.nextInt(300) + 100;
			_y = _rand.nextInt(300) + 100;

			_xVelocity = _rand.nextInt(3) + 1;
			_yVelocity = _rand.nextInt(3) + 1;	
		}

		public void incrementBounces() {
			_numBounces++;
		}

		public int get_numBounces() {
			return _numBounces;
		}

		public void reset_numBounces() {
			_numBounces = 0;
		}

		public void translate() {
			_x = _x + _xVelocity;
			_y = _y + _yVelocity;
		}

		public Rect get_bounds() {
			_paint.getTextBounds(get_thought(), 0, get_thought().length(), 
					_bounds);

			Rect bounds = new Rect();
			int fudge_factor = 10;
			bounds.set(
					get_x() - _bounds.width() / 2 - fudge_factor, 
					get_y() - _bounds.height(), 
					get_x() + _bounds.width() / 2, 
					get_y() + _bounds.height()/3 - fudge_factor);


			return bounds;
		}

		public int get_x() {
			return _x;
		}

		public void set_xVelocity(int _xVelocity) {
			this._xVelocity = _xVelocity;
		}

		public void set_yVelocity(int _yVelocity) {
			this._yVelocity = _yVelocity;
		}

		public int get_y() {
			return _y;
		}

		public void set_x(int _x) {
			this._x = _x;
		}

		public void set_y(int _y) {
			this._y = _y;
		}

		public void invert_x_direction() {
			_xVelocity *= -1;
		}

		public void invert_y_direction() {
			_yVelocity *= -1;
		}

		public String get_thought() {
			return _thought;
		}

		public boolean is_touched() {
			return _touched;
		}

		public void set_touched(boolean _touched) {
			this._touched = _touched;
		}
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if(_activeThought != null) {
				_activeThought.set_xVelocity((int) velocityX / 80);
				_activeThought.set_yVelocity((int) velocityY / 80);
				_activeThought = null;

				return true;
			}

			return false;

		}

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

	}
}
