package com.ece1778.myally.dbt.diary;

import com.ece1778.myally.R;
import com.ece1778.myally.dbt.Therapy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class DiaryCardActivity extends FragmentActivity 
implements Therapy {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diarycard);
	}

	@Override
	public String getName() {
		return "Diary Card";
	}

	@Override
	public void onContinueClick(View view) {
		if(view.getId() == R.id.emotionContinue) {
			finish();
		}		
	}

}
