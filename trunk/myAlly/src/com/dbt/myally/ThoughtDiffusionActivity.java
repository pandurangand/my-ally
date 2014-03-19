package com.dbt.myally;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ThoughtDiffusionActivity extends Activity {
	private EditText _thoughtText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tdiffusion);

		final ThoughtDiffusionView diffusion = (ThoughtDiffusionView) findViewById(R.id.thought_view);
		
		_thoughtText = (EditText) findViewById(R.id.thought_text);
		_thoughtText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
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

}
