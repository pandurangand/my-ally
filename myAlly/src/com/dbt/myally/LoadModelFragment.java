package com.dbt.myally;

import rajawali.RajawaliFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class LoadModelFragment extends RajawaliFragment {

	private EarthRenderer myRenderer;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myRenderer = new EarthRenderer(this.getActivity());
		myRenderer.setSurfaceView(mSurfaceView);
		super.setRenderer(myRenderer);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mLayout = (FrameLayout) inflater.inflate(R.layout.fragment_frame,
				container, false);

		mLayout.addView(mSurfaceView);
		return mLayout;
	}
}
