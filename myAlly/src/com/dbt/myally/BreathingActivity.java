package com.dbt.myally;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BreathingActivity extends Activity {
	private HeartRateDetector _hrDetector;

	private Handler _handler = null;
	private TextView _textViewBreath = null;

	private boolean _breathIn = true;
	private Bundle _info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing);

		_info = this.getIntent().getExtras();

		_hrDetector = new HeartRateDetector(
				(PowerManager) getSystemService(Context.POWER_SERVICE),
				(SurfaceView) findViewById(R.id.surfaceView));
		_hrDetector.init();

		ImageView image = (ImageView) findViewById(R.id.balloon);
		Animation hyperspaceJump = AnimationUtils.loadAnimation(this,
				R.anim.animation_breathing);
		image.startAnimation(hyperspaceJump);

		_textViewBreath = (TextView) findViewById(R.id.textView_breath);
		_handler = new Handler();

		// TBD: Find a better way to update heart rate (perhaps with a listener
		// interface?)
		final TextView heartRate = (TextView) findViewById(R.id.textView_HR);
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								heartRate.setText(""
										+ _hrDetector.getHeartRate());
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
		_handler.removeCallbacks(_updateTimeTask);
		_handler.postDelayed(_updateTimeTask, 1);
		_hrDetector.resume();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onPause() {
		super.onPause();
		_handler.removeCallbacks(_updateTimeTask);
		_hrDetector.pause();
	}

	private Runnable _updateTimeTask = new Runnable() {
		public void run() {
			if (_breathIn) {
				_textViewBreath.setText("Breathe In");
			} else {
				_textViewBreath.setText("Breathe Out");

			}

			_breathIn = !_breathIn;
			_handler.postDelayed(this, 5500);
		}
	};

	public void doneBreath(View view) {
		if (_info != null) {
			Intent intent = new Intent(BreathingActivity.this,
					SubjectiveMeasureActivity.class);

			intent.putExtras(_info);
			startActivity(intent);
		} else {
			finish();
		}
	}
}
