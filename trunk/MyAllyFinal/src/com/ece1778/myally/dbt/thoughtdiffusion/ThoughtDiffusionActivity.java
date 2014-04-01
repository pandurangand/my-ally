package com.ece1778.myally.dbt.thoughtdiffusion;

import com.ece1778.myally.R;
import com.ece1778.myally.dbt.Therapy;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ThoughtDiffusionActivity extends Activity implements Therapy {
	private EditText _thoughtText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tdiffusion);

		final ThoughtDiffusionView diffusion = 
				(ThoughtDiffusionView) findViewById(R.id.thought_view);
		
		_thoughtText = (EditText) findViewById(R.id.thought_text);
		
		//Listen to when new thoughts have been inputted
		_thoughtText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, 
					KeyEvent event) {
				boolean handled = false;
				
				//Check if the "Send" action has been selected
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					//Add the string to the view
					diffusion.addThought(_thoughtText.getText().toString());
					handled = true;
				}
				
				return handled;
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public String getName() {
		return "Thought Diffusion";
	}

}
