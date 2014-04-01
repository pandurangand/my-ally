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
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class UrgeFragment extends Fragment implements OnSeekBarChangeListener {
	private SparseIntArray _textIds;
	private HashMap<Urge, Integer> _values = new HashMap<Urge, Integer>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View inflated =  inflater.inflate(R.layout.fragment_diarycard_urge, 
				container, false);
		
		((SeekBar) inflated.findViewById(R.id.seekBarAlcohol))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarDrug))
		.setOnSeekBarChangeListener(this);
		((SeekBar) inflated.findViewById(R.id.seekBarHarm))
		.setOnSeekBarChangeListener(this);
		
		_textIds = new SparseIntArray();
		_textIds.put(R.id.seekBarAlcohol, R.id.seekTextAlcohol);
		_textIds.put(R.id.seekBarDrug, R.id.seekTextDrug);
		_textIds.put(R.id.seekBarHarm, R.id.seekTextHarm);
		
		return inflated;
	}
	
	public Map<Urge, Integer> getUrgeValues() {				
		return _values;
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		int textId = _textIds.get(seekBar.getId());
		int value = Math.round((float) progress / 20);

		TextView text = (TextView) getView().findViewById(textId);
		text.setText(Integer.toString(value));
		_values.put(Urge.getUrge(textId), value);
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {		
	}

}
