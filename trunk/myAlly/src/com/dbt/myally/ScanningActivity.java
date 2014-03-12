package com.dbt.myally;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScanningActivity extends Activity {
	private Bundle _info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		_info = this.getIntent().getExtras();

	}
	
	
	public void doneScan(View view) {

		if (_info != null) {
			Intent intent = new Intent(ScanningActivity.this,
					SubjectiveMeasureActivity.class);

			intent.putExtras(_info);
			startActivity(intent);
		} else {
			finish();
		}
	}	
}
