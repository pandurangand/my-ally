package com.ece1778.myally.dbt;

import java.util.HashMap;
import java.util.Map;

import com.ece1778.myally.dbt.bodyscan.BodyScanActivity;
import com.ece1778.myally.dbt.breathing.BreathingActivity;
import com.ece1778.myally.dbt.mindjar.MindJarActivity;
import com.ece1778.myally.dbt.musclerelax.MuscleRelaxationActivity;
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
		_therapies.put("Body Scan", BodyScanActivity.class);
		_therapies.put("Muscle Relaxation", MuscleRelaxationActivity.class);
	}

	public Map<String, Class<?>> get_therapies() {
		return _therapies;
	}
}
