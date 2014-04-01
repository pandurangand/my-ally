package com.ece1778.myally.dbt.diary;

import com.ece1778.myally.R;
import com.ece1778.myally.dbt.Therapy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class DiaryCardActivity extends FragmentActivity 
implements Therapy {
	private EmotionFragment _emotionFragment;
	private UrgeFragment _urgeFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diarycard);

		// Check whether the activity is using the layout version with
		// the fragment_container FrameLayout. If so, we must add the first fragment
		if (findViewById(R.id.diarycard_container) != null) {

			//However, if we're being restored from a previous state,
			//then we don't need to do anything and should return or else
			//we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}

			_emotionFragment = new EmotionFragment();

			//In case this activity was started with special instructions from
			//an Intent, pass the Intent's extras to the fragment as arguments
			_emotionFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container'
			getSupportFragmentManager().beginTransaction()
			.add(R.id.diarycard_container, _emotionFragment).commit();
		}
	}

	@Override
	public String getName() {
		return "Diary Card";
	}

	@Override
	public void onContinueClick(View view) {
		if(view.getId() == R.id.emotionContinue) {
			// TODO Save Emotions to Database
			_emotionFragment.getEmotionValues();


			//Create new fragment and transaction
			if(_urgeFragment == null) {
				_urgeFragment = new UrgeFragment();
			}
			
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();

			//Replace whatever is in the fragment_container view with this 
			//fragment, and add the transaction to the back stack
			transaction.replace(R.id.diarycard_container, _urgeFragment);
			transaction.addToBackStack(null);

			// Commit the transaction
			transaction.commit();
		} else if (view.getId() == R.id.urgeContinue) {
			finish();
		}

	}

}
