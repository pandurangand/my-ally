package com.dbt.myally;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.dbt.myally.R.color;


public class MainActivity extends Activity {

	private Handler _handler = null;
	private TextSwitcher _quote;
	Animation in, out;
    private String[] words; // = new String[]{"one","two","three"};

	//AnimationSet as;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		words = getResources().getStringArray(R.array.quotes);
		_handler = new Handler();
		
		_quote = (TextSwitcher)findViewById(R.id.quote_text);
		
		_quote.setFactory(new ViewFactory() {
			public View makeView() {
				TextView quote = new TextView(getApplicationContext());
				quote.setGravity(Gravity.CENTER);
				quote.setTextColor(color.GhostWhite);
				quote.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Large);
				quote.setTypeface(null, Typeface.ITALIC);
				return quote;
			}
		});
		
		in = new AlphaAnimation (0.0f, 1.0f);
		in.setDuration(2000);
		
		out = new AlphaAnimation(1.0f, 0.0f);
		out.setDuration(2000);
		
		_quote.setInAnimation(in);
		_quote.setOutAnimation(out);
		
		_handler.postDelayed(_updateQuoteTask, 0);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void launchCrisis(View view) {
		Intent intent = new Intent(MainActivity.this, SubjectiveMeasureActivity.class);
		startActivity(intent);
	}
	
	public void launchHomework(View view) {
		Intent intent = new Intent(MainActivity.this, HomeworkActivity.class);
		startActivity(intent);
	}
	
	public void launchCommunity(View view) {
		Intent intent = new Intent(MainActivity.this, EarthActivity.class);
		startActivity(intent);
	}
	
	private Runnable _updateQuoteTask = new Runnable() {
		public void run() {
			int num = words.length;
			Random r = new Random();
			int i = (r.nextInt(num));
			_quote.setText(String.valueOf(words[i]).trim());
			_handler.postDelayed(this, 6000);
		}
	};
}
