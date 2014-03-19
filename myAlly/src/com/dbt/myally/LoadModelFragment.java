package com.dbt.myally;

import rajawali.RajawaliFragment;
import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class LoadModelFragment extends RajawaliFragment implements
		View.OnClickListener {

	private EarthRenderer myRenderer;
	private float mPreviousX;
	private float mPreviousY;
	private final float TOUCH_SCALE_FACTOR = 180f / 3200;
	private static final int NONE = 0;
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	float oldDist = 1f;
	private int mode = NONE;

	PointF start = new PointF();
	PointF mid = new PointF();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myRenderer = new EarthRenderer(this.getActivity());
		myRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(myRenderer);

		mGestureDetector = new GestureDetector(getActivity(),
				new MyGestureDetector());

		mGestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {

				float x = event.getX();
				float y = event.getY();

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					start.set(event.getX(), event.getY());
					mode = DRAG;
					myRenderer.down_press();
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						midPoint(mid, event);
						mode = ZOOM;
					}
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
					} else if (mode == ZOOM) {
						float newDist = spacing(event);
						if (newDist > 10f) {
							float scale = newDist / oldDist;
							myRenderer.pinch(scale);
						}
					}
					/*
					 * Camera c = myRenderer.getCurrentCamera(); if (c.getRotX()
					 * == 180) { c.setRotation(c.getRotation().add(dy *
					 * TOUCH_SCALE_FACTOR, dx * TOUCH_SCALE_FACTOR, 0));
					 * 
					 * } else { c.setRotation(c.getRotation().add(-dy *
					 * TOUCH_SCALE_FACTOR, dx * TOUCH_SCALE_FACTOR, 0)); }
					 */
					break;

				}

				mPreviousX = x;
				mPreviousY = y;

				return mGestureDetector.onTouchEvent(event);
			}
		};
		mSurfaceView.setOnClickListener(this);
		mSurfaceView.setOnTouchListener(mGestureListener);

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
	 * 
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { float x =
	 * event.getX(); float y = event.getY();
	 * 
	 * switch (event.getAction() & MotionEvent.ACTION_MASK) { case
	 * MotionEvent.ACTION_DOWN: start.set(event.getX(), event.getY()); mode =
	 * DRAG; myRenderer.down_press(); break; case MotionEvent.ACTION_UP: case
	 * MotionEvent.ACTION_POINTER_UP: mode = NONE;
	 * myRenderer.pinchOrDragFinished(); break; case MotionEvent.ACTION_MOVE: if
	 * (mode == DRAG) { float dx = x - start.x; float dy = y - start.y; if
	 * (Math.abs(x) > 10f || Math.abs(y) > 10f) myRenderer.drag(dx, -dy); }
	 * break;
	 * 
	 * }
	 * 
	 * mPreviousX = x; mPreviousY = y;
	 * 
	 * return true; }
	 */
	// Determine the space between the first two fingers

	private float spacing(MotionEvent event) {
		return distance(event.getX(0), event.getX(1), event.getY(0),
				event.getY(1));
	}

	// Determine the distance between two points

	private float distance(float x1, float x2, float y1, float y2) {
		float x = x1 - x2, y = y1 - y2;
		return FloatMath.sqrt(x * x + y * y);
	}

	// Calculate the mid point of the first two fingers

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	// We need an empty onClick() for touch to work properly

	public void onClick(View unused) {
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		/*@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				float x1 = e1.getX(), y1 = e1.getY(), x2 = e2.getX(), y2 = e2
						.getY();

				if (distance(x1, x2, y1, y2) > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) + Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
					myRenderer.swipe(x2 - x1, y1 - y2);
					return true;
				}
			} catch (Exception e) {
			}
			return false;
		}
		
		 * @Override public boolean onSingleTapConfirmed(MotionEvent e) {
		 * myRenderer.singleTap(); return true; }
		 * 
		 * @Override public boolean onDoubleTapEvent(MotionEvent e) {
		 * myRenderer.doubleTap(); return true; }
		 */
	}
}
