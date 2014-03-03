package com.dbt.myally;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class BreathingActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breathing);
		
		ImageView image = (ImageView) findViewById(R.id.balloon);
		Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.animation_breathing);
		image.startAnimation(hyperspaceJump);
	}
}
