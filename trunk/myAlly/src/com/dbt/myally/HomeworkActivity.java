package com.dbt.myally;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeworkActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hmk);
		String[] homework = new String [] { 
				"Balloon Breathing",
				"Body Scan and Tension Relief",
				"Thought Diffusion"
		};
		final Class<?>[] activities = new Class [] {
				BreathingActivity.class,
				ScanningActivity.class,
				ThoughtDiffusionActivity.class
		};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, homework);
		ListView listview = (ListView) findViewById(R.id.ListOfActivities);
		
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(HomeworkActivity.this, activities[position]);
				startActivity(intent);
			}
			
		});
	}
}
