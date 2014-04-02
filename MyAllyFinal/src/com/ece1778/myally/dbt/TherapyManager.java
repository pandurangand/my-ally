package com.ece1778.myally.dbt;

import java.util.HashMap;
import java.util.Map;

import com.ece1778.myally.dbt.breathing.BreathingActivity;
import com.ece1778.myally.dbt.diary.DiaryCardActivity;
import com.ece1778.myally.dbt.mindjar.MindJarActivity;
import com.ece1778.myally.dbt.thoughtdiffusion.ThoughtDiffusionActivity;

/**
 * New DBT Activities should be added here.
 * 
 * @author Mario
 *
 */
public class TherapyManager {
	private Map<String, Class<?>> _therapies;
	
	public TherapyManager() {
		_therapies = new HashMap<String, Class<?>>();
		
		_therapies.put("Slow Breathing", BreathingActivity.class);
		_therapies.put("Thought Diffusion", ThoughtDiffusionActivity.class);
		_therapies.put("Mind Jar", MindJarActivity.class);
		
		//Just Testing
		_therapies.put("Diary Card", DiaryCardActivity.class);
	}

	public Map<String, Class<?>> get_therapies() {
		return _therapies;
	}
}