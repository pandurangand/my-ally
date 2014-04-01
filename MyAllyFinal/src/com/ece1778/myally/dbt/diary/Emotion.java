package com.ece1778.myally.dbt.diary;

import com.ece1778.myally.R;

public enum Emotion {
	ANGRY,
	ANXIOUS,
	AFRAID,
	HAPPY,
	MISERABLE,
	SAD,
	ASHAMED,
	UNKNOWN;

	public static Emotion getEmotion(int id) {
		Emotion emotion = null;

		switch(id) {
		
		case R.id.seekTextAnger: emotion = ANGRY; break;
		case R.id.seekTextAnxious: emotion = ANXIOUS; break;
		case R.id.seekTextFear: emotion = AFRAID; break;
		case R.id.seekTextHappy: emotion = HAPPY; break;
		case R.id.seekTextMisery: emotion = MISERABLE; break;
		case R.id.seekTextSad: emotion = SAD; break;
		case R.id.seekTextShame: emotion = ASHAMED; break;
		default: emotion = UNKNOWN; break;
		}

		return emotion;
	}
}
