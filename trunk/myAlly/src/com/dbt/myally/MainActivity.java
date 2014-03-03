package com.dbt.myally;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		BootstrapButton heartrate_button = (BootstrapButton) findViewById(R.id.heart_rate);
		BootstrapButton start_button = (BootstrapButton) findViewById(R.id.start);
		BootstrapButton earth_button = (BootstrapButton) findViewById(R.id.earth);

		heartrate_button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, HeartRateMonitor.class);
    			startActivity(i);
			}
		});
		
		
		start_button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SubjectiveMeasureActivity.class);
    			startActivity(i);
			}
		});
		
		earth_button.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, EarthActivity.class);
    			startActivity(i);
				
				
		        
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
