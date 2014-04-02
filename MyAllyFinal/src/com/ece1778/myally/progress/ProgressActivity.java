package com.ece1778.myally.progress;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ece1778.myally.R;

public class ProgressActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress);
	}

	

	public void onContinueClick(View view) {
		finish();
	}
}
