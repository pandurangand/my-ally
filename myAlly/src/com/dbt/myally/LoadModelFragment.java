package com.dbt.myally;

import rajawali.Camera;
import rajawali.RajawaliFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class LoadModelFragment extends RajawaliFragment implements OnTouchListener {

	private EarthRenderer myRenderer;
	private float mPreviousX;
	private float mPreviousY;
	private final float TOUCH_SCALE_FACTOR = 180f / 3200;
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		switch(event.getAction()) {
		
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			
			Camera c = myRenderer.getCurrentCamera();
			c.setRotation(c.getRotation().add(dy * TOUCH_SCALE_FACTOR,dx * TOUCH_SCALE_FACTOR,0));
			break;
		}
		
		mPreviousX = x;
		mPreviousY = y;
		
		return true;
	}
}
