package com.ece1778.myally.crisis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ece1778.myally.R;
import com.ece1778.myally.database.InfoDb.ActivityEntry;

public class SubjectiveMeasureActivity extends Activity implements
		OnClickListener {
	Bundle b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subj_measure);

		b = this.getIntent().getExtras();
		if (b != null) {
			TextView tv = (TextView) findViewById(R.id.Instructions);
			tv.setText("How do you feel now?");
		} else {
			b = new Bundle();
		}
		ImageButton angryButton = (ImageButton) findViewById(R.id.anger);
		ImageButton ashamedButton = (ImageButton) findViewById(R.id.shame);
		ImageButton sadButton = (ImageButton) findViewById(R.id.sadness);
		ImageButton happyButton = (ImageButton) findViewById(R.id.happy);
		ImageButton anxiousButton = (ImageButton) findViewById(R.id.anxious);
		ImageButton miseryButton = (ImageButton) findViewById(R.id.misery);
		ImageButton fearButton = (ImageButton) findViewById(R.id.fear);

		angryButton.setOnClickListener(this);
		ashamedButton.setOnClickListener(this);
		sadButton.setOnClickListener(this);
		happyButton.setOnClickListener(this);
		anxiousButton.setOnClickListener(this);
		miseryButton.setOnClickListener(this);
		fearButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// Bundle b = new Bundle();
		switch (v.getId()) {
		case R.id.anger:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "anger");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "anger");
			break;
		case R.id.shame:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "shame");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "shame");
			break;
		case R.id.sadness:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "sad");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "sad");
			break;
		case R.id.happy:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "happy");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "happy");
			break;
		case R.id.anxious:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "anxious");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "anxious");
			break;
		case R.id.misery:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "misery");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "misery");
			break;
		case R.id.fear:
			if (!b.isEmpty())
				b.putString(ActivityEntry.COLUMN_POST_MOOD, "fear");
			else
				b.putString(ActivityEntry.COLUMN_PRE_MOOD, "fear");
			break;
		}
		Intent i = new Intent(SubjectiveMeasureActivity.this,
				ObjectiveMeasureActivity.class);
		i.putExtras(b);
		startActivity(i);
	}
}
