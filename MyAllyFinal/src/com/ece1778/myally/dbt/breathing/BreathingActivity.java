package com.ece1778.myally.dbt.breathing;

import com.ece1778.myally.R;
import com.ece1778.myally.dbt.Therapy;
import com.ece1778.myally.detectors.HeartRateDetector;
import com.ece1778.myally.detectors.HeartRateDetector.HeartRateListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BreathingActivity extends Activity implements HeartRateListener, 
Therapy {
	private HeartRateDetector _hrDetector;

	private Handler _handler = null;
	private TextView _textViewBreath = null;

	private boolean _breathIn = true;
	
	@Override
	public String getName() {
		return "Slow Breathing";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing);

		_hrDetector = new HeartRateDetector(
				(PowerManager) getSystemService(Context.POWER_SERVICE),
				(SurfaceView) findViewById(R.id.surfaceView));
		_hrDetector.init();
		_hrDetector.setListener(this);

		ImageView image = (ImageView) findViewById(R.id.balloon);
		Animation breathAnim = AnimationUtils.loadAnimation(this,
				R.anim.animation_breathing);
		image.startAnimation(breathAnim);

		_textViewBreath = (TextView) findViewById(R.id.textView_breath);
		_handler = new Handler();

		// TODO: Find a better way to update heart rate (perhaps with a listener
		// TODO: interface?)
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
	
	@Override
	public void onHeartRateChange(final int heartRate) {
		//Broken? Update TextView on UI Thread...
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
}
