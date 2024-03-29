package com.ece1778.myally.dbt.bodyscan;


import com.ece1778.myally.R;
import com.ece1778.myally.dbt.musclerelax.MuscleRelaxationActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	
	private int TIME = 1500;

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
				TranslateAnimation.RELATIVE_TO_PARENT, 0.12f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.31f);
		mAnim_head.setDuration(TIME);
		mAnim_head.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_head.setFillAfter(true);

		mAnim_shoulder = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.31f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.38f);
		mAnim_shoulder.setDuration(TIME);
		mAnim_shoulder.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_shoulder.setFillAfter(true);

		mAnim_stomach = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.38f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.55f);
		mAnim_stomach.setDuration(TIME);
		mAnim_stomach.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_stomach.setFillAfter(true);

		mAnim_hand = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.55f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.66f);
		mAnim_hand.setDuration(TIME);
		mAnim_hand.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_hand.setFillAfter(true);

		mAnim_leg = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.66f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.87f);
		mAnim_leg.setDuration(TIME);
		mAnim_leg.setInterpolator(new AccelerateDecelerateInterpolator());
		mAnim_leg.setFillAfter(true);

		mAnim_feet = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.87f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.93f);
		mAnim_feet.setDuration(TIME);
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
				}, TIME);
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
				}, TIME);

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
				}, TIME);
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
				}, TIME);
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
				}, TIME);
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
				}, TIME);
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

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				Intent intent = new Intent(BodyScanActivity.this, MuscleRelaxationActivity.class);
				intent.putExtras(_info);
				startActivityForResult(intent, 0);				
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				finish();
				break;
			}
		}
	};

	public void doneScan() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//builder.setTitle("Load Entries");
		builder.setMessage("Would you like to relax your muscles?");
		builder.setPositiveButton("Yes", dialogClickListener);
		builder.setNegativeButton("No", dialogClickListener);

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		finish();
	}
}
