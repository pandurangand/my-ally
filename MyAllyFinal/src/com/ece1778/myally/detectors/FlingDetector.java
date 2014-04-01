package com.ece1778.myally.detectors;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class FlingDetector extends GestureDetector.SimpleOnGestureListener {
	public interface FlingListener {
		public boolean onFling(float velocityX, float velocityY);
	}
	
	private FlingListener _flingListener;
	
	//Used for flings
	private GestureDetector _gestureDetector;
	
	public FlingDetector(final Context context) {
		_gestureDetector = new GestureDetector(context, this);
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		return _gestureDetector.onTouchEvent(event);
	}
	
	public void setFlingListener(FlingListener flingListener) {
		_flingListener = flingListener;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, 
			float velocityY) {
		return _flingListener.onFling(velocityX, velocityY);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}
}
