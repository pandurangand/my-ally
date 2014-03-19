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
	private Handler h;
	private final int FRAME_RATE = 30;

	private Paint _paint;

	private ArrayList<Thought> _thoughts;

	//Currently touched thought
	private Thought _activeThought = null;

	private Rect _bounds = new Rect();

	//Random positions and velocities for thoughts
	private Random _rand = new Random();

	//Used for flings
	private GestureDetector _detector;

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			update(); //Update locations/speed
			invalidate();  //Update visuals
		}
	};

	public ThoughtDiffusionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		h = new Handler();

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
			_paint.getTextBounds(t.get_thought(), 0, 1, _bounds);

			//Check if a thought was touched
			if((event.getX() >= t.get_x() - _bounds.width() / 2) &&
					event.getX() <= t.get_x() + _bounds.width() / 2) {
				if((event.getY() >= t.get_y() - _bounds.height() / 2) &&
						event.getY() <= t.get_y() + _bounds.height() / 2) {
					t.set_touched(true);
					_activeThought = t;
					break;
				}
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
			c.drawText(t.get_thought(), t.get_x(), t.get_y(), _paint);
		}

		//Delay the next update by FRAME_RATE milliseconds
		h.postDelayed(r, FRAME_RATE);
	}

	/**
	 * Go through all thoughts and update their x and y positions.
	 */
	private void update() {
		ArrayList<Thought> toRemove = new ArrayList<Thought>();

		for(Thought t : _thoughts) {
			_paint.getTextBounds(t.get_thought(), 0, 1, _bounds);

			//If the thought has been touched, have it bounce back
			if(t.is_touched()) {
				if (t.get_x() < 0 && t.get_y() < 0) {
					t.set_x(0);
					t.set_y(0);
				} else {
					t.translate();

					if ((t.get_x() > this.getWidth() - 2*_bounds.width()) || (t.get_x() - 2*_bounds.width() < 0)) {
						t.invert_x_direction();
						t.set_touched(false);
						t.reset_velocity();
					}

					if ((t.get_y() > this.getHeight() - _bounds.height()/2) || (t.get_y() - _bounds.height() < 0)) {
						t.invert_y_direction();
						t.set_touched(false);
						t.reset_velocity();
					}
				}
			} else {
				//Otherwise, have the thought drift away
				t.translate();

				if (t.get_x() < 0 && t.get_y() < 0) {
					toRemove.add(t);
				} else if (t.get_x() > this.getWidth() || t.get_y() - _bounds.height() > this.getHeight()) {
					toRemove.add(t);
				}
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

		public Thought(String thought) {
			_thought = thought;

			_x = _rand.nextInt(300) + 100;
			_y = _rand.nextInt(300) + 100;

			_xVelocity = _rand.nextInt(5) + 1;
			_yVelocity = _rand.nextInt(5) + 1;	
		}

		public void reset_velocity() {
			if(_xVelocity > 5) {
				_xVelocity = 5;
			} else if (_xVelocity < 0) {
				_xVelocity = -1;
			}
			
			if(_yVelocity > 5) {
				_yVelocity = 5;
			} else if (_yVelocity < 0) {
				_yVelocity = -1;
			}
		}
		
		public void translate() {
			_x = _x + _xVelocity;
			_y = _y + _yVelocity;
		}

		public int get_x() {
			return _x;
		}

		public void set_x(int _x) {
			this._x = _x;
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
