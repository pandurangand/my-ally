package com.dbt.myally;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		Button heartrate_button = (Button) findViewById(R.id.heart_rate);
		Button start_button = (Button) findViewById(R.id.start);
		Button earth_button = (Button) findViewById(R.id.earth);

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
		});*/
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void launchCrisis(View view) {
		//Intent intent = new Intent(MainActivity.this, BreathingActivity.class);
		Intent intent = new Intent(MainActivity.this, SubjectiveMeasureActivity.class);
		startActivity(intent);
	}
	
	public void launchHomework(View view) {
		Intent intent = new Intent(MainActivity.this, BreathingActivity.class);
		startActivity(intent);
	}
	
	public void launchCommunity(View view) {
		Intent intent = new Intent(MainActivity.this, EarthActivity.class);
		startActivity(intent);
	}

}
