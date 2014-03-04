package com.dbt.myally;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BreathingActivity extends Activity {
	private HeartRateDetector hrDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing);

		ImageView image = (ImageView) findViewById(R.id.balloon);
		Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.animation_breathing);
		image.startAnimation(hyperspaceJump);

		hrDetector = new HeartRateDetector((PowerManager) getSystemService(Context.POWER_SERVICE), (SurfaceView) findViewById(R.id.surfaceView));
		hrDetector.init();

		
		final TextView heartRate = (TextView) findViewById(R.id.textViewHR);
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								heartRate.setText("" + hrDetector.getHeartRate());
							}
						});
					}
				} catch (InterruptedException e) {
				}
			}
		};

		t.start();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onResume() {
		super.onResume();
		hrDetector.resume();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();
		hrDetector.pause();
	}
}
