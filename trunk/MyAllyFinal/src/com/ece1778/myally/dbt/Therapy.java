package com.ece1778.myally.dbt;

import android.view.View;

/**
 * Currently doesn't do much, but for the future all DBT activities should
 * implement this class.
 * 
 * @author Mario
 *
 */
public interface Therapy {
	public String getName();
	
	public void onContinueClick(View view);
}
