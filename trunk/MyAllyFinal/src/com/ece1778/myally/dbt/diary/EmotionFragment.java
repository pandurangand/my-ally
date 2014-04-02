package com.ece1778.myally.dbt.diary;

import java.util.HashMap;
import java.util.Map;

import com.ece1778.myally.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class EmotionFragment extends Fragment 
implements OnSeekBarChangeListener {
	private SparseIntArray _textIds;
	private HashMap<Emotion, Integer> _values = new HashMap<Emotion, Integer>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View inflated =  inflater.inflate(R.layout.fragment_diarycard_emotion, 
				container, false);

		((SeekBar) inflated.findViewById(R.id.seekBarAnger))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarAnxious))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarFear))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarHappy))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarMisery))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarSad))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarShame))
		.setOnSeekBarChangeListener(this);

		_textIds = new SparseIntArray();
		_textIds.put(R.id.seekBarAnger, R.id.seekTextAnger);
		_textIds.put(R.id.seekBarAnxious, R.id.seekTextAnxious);
		_textIds.put(R.id.seekBarFear, R.id.seekTextFear);
		_textIds.put(R.id.seekBarHappy, R.id.seekTextHappy);
		_textIds.put(R.id.seekBarMisery, R.id.seekTextMisery);
		_textIds.put(R.id.seekBarSad, R.id.seekTextSad);
		_textIds.put(R.id.seekBarShame, R.id.seekTextShame);

		return inflated;
	}

	public HashMap<Emotion, Integer> getEmotionValues() {				
		return _values;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		int textId = _textIds.get(seekBar.getId());
		int value = Math.round((float) progress / 20);

		TextView text = (TextView) getView().findViewById(textId);
		text.setText(Integer.toString(value));
		_values.put(Emotion.getEmotion(textId), value);
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {		
	}


}
