package com.ece1778.myally.dbt;

import java.util.HashMap;
import java.util.Map;

import com.ece1778.myally.dbt.breathing.BreathingActivity;
import com.ece1778.myally.dbt.mindjar.MindJarActivity;
import com.ece1778.myally.dbt.thoughtdiffusion.ThoughtDiffusionActivity;

public class TherapyManager {
	private Map<String, Class<?>> _therapies;
	
	public TherapyManager() {
		_therapies = new HashMap<String, Class<?>>();
		
		_therapies.put("Slow Breathing", BreathingActivity.class);
		_therapies.put("Thought Diffusion", ThoughtDiffusionActivity.class);
		_therapies.put("Mind Jar", MindJarActivity.class);
	}

	public Map<String, Class<?>> get_therapies() {
		return _therapies;
	}
}
