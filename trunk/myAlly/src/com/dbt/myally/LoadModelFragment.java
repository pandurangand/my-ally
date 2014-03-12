package com.dbt.myally;

import rajawali.RajawaliFragment;
import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class LoadModelFragment extends RajawaliFragment implements
		OnTouchListener {

	private EarthRenderer myRenderer;
	private float mPreviousX;
	private float mPreviousY;
	private final float TOUCH_SCALE_FACTOR = 180f / 3200;
	private static final int NONE = 0;

	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	private int mode = NONE;

	PointF start = new PointF();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myRenderer = new EarthRenderer(this.getActivity());
		myRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(myRenderer);
		mSurfaceView.setOnTouchListener(this);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayout = (FrameLayout) inflater.inflate(R.layout.fragment_frame,
				container, false);

		mLayout.addView(mSurfaceView);
		return mLayout;
	}

	/*
	 * @Override public boolean onTouch(View v, MotionEvent event) { float x =
	 * event.getX(); float y = event.getY();
	 * 
	 * switch (event.getAction()) {
	 * 
	 * case MotionEvent.ACTION_MOVE: float dx = x - mPreviousX; float dy = y -
	 * mPreviousY;
	 * 
	 * Camera c = myRenderer.getCurrentCamera();
	 * 
	 * float half_width = myRenderer.vpWidth() / 2; float half_height =
	 * myRenderer.vpHeight() / 2; if (x < half_width) { dx = (float)
	 * c.getRotation().y + (-mPreviousX + Math.abs(x)) / 100; } else { dx =
	 * (float) c.getRotation().y + (Math.abs(x) - mPreviousX) / 100; } if (y <
	 * half_height) { dy = (float) c.getRotation().x + (-mPreviousY +
	 * Math.abs(y)) / 100; } else { dy = (float) c.getRotation().x +
	 * (Math.abs(y) - mPreviousY) / 100; } c.setRotation(dy, dx, 0);
	 * 
	 * break;
	 * 
	 * }
	 * 
	 * mPreviousX = x; mPreviousY = y; return true; }
	 */

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			start.set(event.getX(), event.getY());
			mode = DRAG;
			myRenderer.down_press();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			myRenderer.pinchOrDragFinished();
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				float dx = x - start.x;
				float dy = y - start.y;
				if (Math.abs(x) > 10f || Math.abs(y) > 10f)
					myRenderer.drag(dx, -dy);
			}
			/*
			 * Camera c = myRenderer.getCurrentCamera(); if (c.getRotX() == 180)
			 * { c.setRotation(c.getRotation().add(dy * TOUCH_SCALE_FACTOR, dx *
			 * TOUCH_SCALE_FACTOR, 0));
			 * 
			 * } else { c.setRotation(c.getRotation().add(-dy *
			 * TOUCH_SCALE_FACTOR, dx * TOUCH_SCALE_FACTOR, 0)); }
			 */
			break;

		}

		mPreviousX = x;
		mPreviousY = y;

		return true;
	}

}
