package com.ece1778.myally.dbt.thoughtdiffusion;

import java.util.ArrayList;

import com.ece1778.myally.detectors.FlingDetector;
import com.ece1778.myally.detectors.FlingDetector.FlingListener;
import com.ece1778.myally.ui.AnimatedView;

import com.ece1778.myally.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ThoughtDiffusionView extends AnimatedView implements FlingListener {
	//If true, then thoughts bounce off the edges. Otherwise they reappear on
	//the other side
	private final boolean BOUNCE = true;
	//The number of times the thought should bounce/reappear
	private final int MAX_BOUNCES = 10;
	//Reusable Rect variable
	private Rect _bounds = new Rect();
	//Active thoughts
	private ArrayList<Thought> _thoughts;
	//Currently touched thought
	private Thought _activeThought = null;
	//Detects flings
	private FlingDetector _flingDetector;
	//Cloud bitmap
	private Bitmap _cloud;

	public ThoughtDiffusionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		_flingDetector = new FlingDetector(this.getContext());
		_flingDetector.setFlingListener(this);

		_thoughts = new ArrayList<Thought>();
		
		_cloud = BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
	}
	
	@Override
	public boolean onFling(float velocityX, float velocityY) {
		if(_activeThought != null) {
			_activeThought.fling(velocityX, velocityY);
			_activeThought.reset_numBounces();
			_activeThought = null;

			return true;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		
		//For each thought
		for(Thought t : _thoughts) {
			//Check if a thought was touched
			if(t.get_cloudBounds().contains((int)event.getX(), 
					(int)event.getY())) {
				t.set_touched(true);
				_activeThought = t;
				break;
			}
		}

		//Register flings
		return _flingDetector.onTouchEvent(event);
	}

	public void addThought(String thought) {
		_thoughts.add(new Thought(thought));
	}
	
	@Override
	protected void onDrawAnimation(Canvas c) {
		for(Thought t : _thoughts) {
			Paint paint = t.get_paint();
			
			c.drawBitmap(_cloud, null, t.get_cloudBounds(), paint);
			
			paint.setColor(Color.WHITE);
			c.drawText(t.get_thought(), t.get_x(), t.get_y(), paint);
		}		
	}

	/**
	 * Go through all thoughts and update their x and y positions.
	 */
	@Override
	protected void onUpdate() {
		ArrayList<Thought> toRemove = new ArrayList<Thought>();

		for(Thought t : _thoughts) {
			t.translate();

			//Check if the thought has been touched
			if(t.is_touched()) {
				_bounds = t.get_cloudBounds();

				if(BOUNCE) {
					//Bounce it back on x
					if(_bounds.right >= getWidth() || _bounds.left <= 0) {
						t.invert_x_direction();
						t.incrementBounces();
					}

					//Bounce it back on y
					if(_bounds.bottom >= getHeight() || _bounds.top <= 0) {
						t.invert_y_direction();
						t.incrementBounces();
					}
				} else {
					if(_bounds.left > getWidth()) {
						t.set_x(0);
						t.incrementBounces();
					} else if (_bounds.right < 0) {
						t.set_x(this.getWidth());
						t.incrementBounces();
					}

					if(_bounds.top > getHeight()) {
						t.set_y(0);
						t.incrementBounces();
					} else if (_bounds.bottom < 0) {
						t.set_y(this.getWidth());
						t.incrementBounces();
					}
				}
			} else {
				//Remove it from the list of thoughts when it is off screen
				if (_bounds.right < 0 && _bounds.bottom < 0) {
					toRemove.add(t);
				} else if (_bounds.top > this.getHeight() || 
						_bounds.left > this.getWidth()) {
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
}
