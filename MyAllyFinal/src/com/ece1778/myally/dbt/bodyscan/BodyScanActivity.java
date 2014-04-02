package com.ece1778.myally.dbt.bodyscan;


import com.ece1778.myally.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BodyScanActivity extends Activity {
	private Bundle _info;

	private int count = 0;
	ImageView redline;
	ImageView head, shoulder, stomach, hand, leg, feet;
	Animation mAnim_head, mAnim_shoulder, mAnim_stomach, mAnim_hand, mAnim_leg,
			mAnim_feet;
	TextView instructions;

	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		_info = this.getIntent().getExtras();

		if (_info == null) {
			_info = new Bundle();
		}
		redline = (ImageView) findViewById(R.id.redline);
		head = (ImageView) findViewById(R.id.headdot);
		shoulder = (ImageView) findViewById(R.id.shoulderdot);
		stomach = (ImageView) findViewById(R.id.stomachdot);
		hand = (ImageView) findViewById(R.id.handdot);
		leg = (ImageView) findViewById(R.id.legdot);
		feet = (ImageView) findViewById(R.id.feetdot);

		instructions = (TextView) findViewById(R.id.textView_scan);

		mAnim_head = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.ABSOLUTE, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.08f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.34f);
		mAnim_head.setDuration(5000);
		mAnim_head.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_head.setFillAfter(true);

		mAnim_shoulder = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.34f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.42f);
		mAnim_shoulder.setDuration(5000);
		mAnim_shoulder.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_shoulder.setFillAfter(true);

		mAnim_stomach = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.42f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.57f);
		mAnim_stomach.setDuration(5000);
		mAnim_stomach.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_stomach.setFillAfter(true);

		mAnim_hand = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.57f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.65f);
		mAnim_hand.setDuration(5000);
		mAnim_hand.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_hand.setFillAfter(true);

		mAnim_leg = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.65f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.84f);
		mAnim_leg.setDuration(5000);
		mAnim_leg.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_leg.setFillAfter(true);

		mAnim_feet = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.84f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.91f);
		mAnim_feet.setDuration(5000);
		mAnim_feet.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_feet.setFillAfter(true);

		mAnim_head.setAnimationListener(mAnimListener);
		mAnim_shoulder.setAnimationListener(mAnimListener);
		mAnim_stomach.setAnimationListener(mAnimListener);
		mAnim_hand.setAnimationListener(mAnimListener);
		mAnim_leg.setAnimationListener(mAnimListener);
		mAnim_feet.setAnimationListener(mAnimListener);

		redline.setAnimation(mAnim_head);

	}

	AnimationListener mAnimListener = new AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			if (count == 1) {
				instructions
						.setText("Tap the screen if you feel tension in your head");
				mHandler.postDelayed(new Runnable() {
					public void run() {
						redline.startAnimation(mAnim_shoulder);
					}
				}, 5000);
				head.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						_info.putInt("head", 1);
						head.setImageResource(R.drawable.head_dot_red);
					}

				});
			} else if (count == 2) {
				instructions
						.setText("Tap the screen if you feel tension in your shoulders");

				mHandler.postDelayed(new Runnable() {
					public void run() {
						redline.startAnimation(mAnim_stomach);
					}
				}, 5000);

				shoulder.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						_info.putInt("shoulder", 1);

						shoulder.setImageResource(R.drawable.shoulder_dot_red);
					}

				});
			} else if (count == 3) {
				instructions
						.setText("Tap the screen if you feel queasy or uncomfortable in your stomach");

				mHandler.postDelayed(new Runnable() {
					public void run() {
						redline.startAnimation(mAnim_hand);
					}
				}, 5000);
				stomach.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						_info.putInt("stomach", 1);

						stomach.setImageResource(R.drawable.stomach_dot_red);
					}

				});
			} else if (count == 4) {
				instructions
						.setText("Tap the screen if you feel tension in your hands");

				mHandler.postDelayed(new Runnable() {
					public void run() {
						redline.startAnimation(mAnim_leg);
					}
				}, 5000);
				hand.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						_info.putInt("hand", 1);

						hand.setImageResource(R.drawable.hand_dot_red);
					}

				});
			} else if (count == 5) {
				instructions
						.setText("Tap the screen if you feel tension in your legs");

				mHandler.postDelayed(new Runnable() {
					public void run() {
						redline.startAnimation(mAnim_feet);
					}
				}, 5000);
				leg.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						_info.putInt("leg", 1);

						leg.setImageResource(R.drawable.leg_dot_red);
					}

				});
			} else if (count == 6) {
				instructions
						.setText("Tap the screen if you feel tension in your feet");
				mHandler.postDelayed(new Runnable() {
					public void run() {
						doneScan();
					}
				}, 5000);
				feet.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						_info.putInt("feet", 1);

						feet.setImageResource(R.drawable.feet_dot_red);
					}

				});
			}
		}

		@Override
		public void onAnimationStart(Animation animation) {
			count++;
			if (count == 1) {
				head.setImageResource(R.drawable.head_dot_blue);
				head.setVisibility(View.VISIBLE);
				instructions
						.setText("Start by examining your head for any tension");
			} else if (count == 2) {
				shoulder.setImageResource(R.drawable.shoulder_dot_blue);
				shoulder.setVisibility(View.VISIBLE);
				head.setOnClickListener(null);
				instructions.setText("Now examine your shoulders");
			} else if (count == 3) {
				stomach.setImageResource(R.drawable.stomach_dot_blue);
				stomach.setVisibility(View.VISIBLE);
				shoulder.setOnClickListener(null);
				instructions.setText("Feel your stomach");
			} else if (count == 4) {
				hand.setImageResource(R.drawable.hand_dot_blue);
				hand.setVisibility(View.VISIBLE);
				stomach.setOnClickListener(null);
				instructions.setText("Be conscious of your hands");
			} else if (count == 5) {
				leg.setImageResource(R.drawable.leg_dot_blue);
				leg.setVisibility(View.VISIBLE);
				hand.setOnClickListener(null);
				instructions.setText("Examine the length of your legs");
			} else if (count == 6) {
				feet.setImageResource(R.drawable.feet_dot_blue);
				feet.setVisibility(View.VISIBLE);
				leg.setOnClickListener(null);
				instructions.setText("And finally, your feet");
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

	};

	public void doneScan() {
		//Intent intent = new Intent(ScanningActivity.this, TensionActivity.class);
		//intent.putExtras(_info);
		//startActivityForResult(intent, 0);

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		finish();
	}
}
