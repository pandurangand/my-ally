package com.ece1778.myally.dbt.musclerelax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ece1778.myally.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MuscleRelaxationActivity extends Activity {
	private TextView instructions;
	private ImageView main;
	private Handler mHandler = new Handler();
	private TextView countdown;
	private Iterator<bodyPart> it;
	private ImageView balloon;
	private Animation breathing;

	private enum bodyPart {
		HEAD, SHOULDER, STOMACH, HAND, LEG, FEET
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tension);
		Bundle info = this.getIntent().getExtras();
		balloon = (ImageView) findViewById(R.id.balloon);
		breathing = AnimationUtils.loadAnimation(this,
				R.anim.animation_breathing);

		final List<bodyPart> bp = new ArrayList<bodyPart>();

		if(info != null && !info.isEmpty()) {

			if (info.getInt("head") == 1)
				bp.add(bodyPart.HEAD);
			if (info.getInt("shoulder") == 1)
				bp.add(bodyPart.SHOULDER);
			if (info.getInt("stomach") == 1)
				bp.add(bodyPart.STOMACH);
			if (info.getInt("hand") == 1)
				bp.add(bodyPart.HAND);
			if (info.getInt("leg") == 1)
				bp.add(bodyPart.LEG);
			if (info.getInt("feet") == 1)
				bp.add(bodyPart.FEET);
		} else {
			// TODO Request User Info
		}
		
		main = (ImageView) findViewById(R.id.tension_image);
		instructions = (TextView) findViewById(R.id.tension_instruction);
		countdown = (TextView) findViewById(R.id.tension_countdown);

		mHandler.postDelayed(new Runnable() {
			public void run() {
				instructions.setVisibility(View.INVISIBLE);

				it = bp.iterator();
				if (it.hasNext())
					clenchActivity(it.next());
				else
					doneScan();

			}
		}, 3000);

	}

	private void clenchActivity(final bodyPart part) {
		CountDownTimer timer = null;
		balloon.setVisibility(View.VISIBLE);
		balloon.startAnimation(breathing);

		timer = new CountDownTimer(5500, 1000) {
			int secondsLeft = 5;
			int count = 0;

			public void onTick(long millisUntilFinished) {
				switch (part) {
				case HEAD:
					main.setImageResource(R.drawable.head_clenched);
					countdown.setText("Squeeze your face for " + secondsLeft + "s");
					break;
				case SHOULDER:
					main.setImageResource(R.drawable.shoulder_clenched);
					countdown.setText("Shrug your shoulders for " + secondsLeft + "s");
					break;
				case HAND:
					main.setImageResource(R.drawable.hand_clenched);
					countdown.setText("Clench your hands for " + secondsLeft + "s");
					break;
				case STOMACH:
					main.setImageResource(R.drawable.stomach_clenched);
					countdown.setText("Clench your abs for " + secondsLeft + "s");
					break;
				case LEG:
					main.setImageResource(R.drawable.leg_clenched);
					countdown.setText("Clench your leg for " + secondsLeft + "s");
					break;
				case FEET:
					main.setImageResource(R.drawable.feet_clenched);
					countdown.setText("Clench your feet for " + secondsLeft + "s");
					break;
				}
				secondsLeft--;
			}

			public void onFinish() {
				secondsLeft = 5;
				count++;
				countdown.setText("Now Relax");

				switch (part) {
				case HEAD:
					main.setImageResource(R.drawable.head_unclenched);
					break;
				case SHOULDER:
					main.setImageResource(R.drawable.shoulder_unclenched);
					break;
				case HAND:
					main.setImageResource(R.drawable.hand_unclenched);
					break;
				case STOMACH:
					main.setImageResource(R.drawable.stomach_unclenched);
					break;
				case LEG:
					main.setImageResource(R.drawable.leg_unclenched);
					break;
				case FEET:
					main.setImageResource(R.drawable.feet_unclenched);
					break;
				}

				mHandler.postDelayed(new Runnable() {
					public void run() {
						if (count != 1) {
							start();
						} else {
							cancel();
							balloon.clearAnimation();
							dialog(part);

						}
					}
				}, 5500);

			}
		};
		timer.start();



	}

	public void dialog(bodyPart part) {
		final bodyPart p = part;

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Would you like to do that again?")
		.setCancelable(false)
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				clenchActivity(p);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				if (it.hasNext())
					clenchActivity(it.next());
				else
					doneScan();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	public void doneScan() {
		setResult(1);
		finish();
	}
}
