package com.ece1778.myally.dbt.diary;

import com.ece1778.myally.R;

public enum Urge {
	ALCOHOL,
	DRUGS,
	SELF_HARM, 
	UNKNOWN;
	
	public static Urge getUrge(int id) {
		Urge emotion = null;

		switch(id) {
		
		case R.id.seekTextAlcohol: emotion = ALCOHOL; break;
		case R.id.seekTextDrug: emotion = DRUGS; break;
		case R.id.seekTextHarm: emotion = SELF_HARM; break;
		default: emotion = UNKNOWN; break;
		}

		return emotion;
	}
}
