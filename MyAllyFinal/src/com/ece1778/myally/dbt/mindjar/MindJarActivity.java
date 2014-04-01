package com.ece1778.myally.dbt.mindjar;

import com.ece1778.myally.R;
import com.ece1778.myally.dbt.Therapy;
import com.ece1778.myally.detectors.ShakeDetector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MindJarActivity extends Activity implements Therapy {
	private ShakeDetector _shakeDetector;

	private MindJarView _mindJarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mindjar);

		_mindJarView = (MindJarView) findViewById(R.id.mindjar);
		
		//Start Accelerometer
		_shakeDetector = new ShakeDetector(this);
		_shakeDetector.listenForShakes(_mindJarView);		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_mindjar);
		
		_shakeDetector.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		_shakeDetector.pause();
	}

	@Override
	public String getName() {
		return "Mind Jar";
	}

	@Override
	public void onContinueClick(View view) {
		finish();		
	}
}
