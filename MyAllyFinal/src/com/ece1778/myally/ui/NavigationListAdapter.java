package com.ece1778.myally.ui;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ece1778.myally.R;
import com.ece1778.myally.database.InfoDbHelper;
import com.ece1778.myally.dbt.TherapyManager;
import com.ece1778.myally.ui.NavigationDrawer.TherapyLauncher;

public class NavigationListAdapter extends BaseExpandableListAdapter {

	private Activity _activity;
	private Map<String, List<String>> _dbtCollections;
	private List<String> _therapies;
	// The class responsible for launching DBT therapies
	private TherapyLauncher _therapyLauncher;
	// The therapies available in the app
	private TherapyManager _therapyManager;
	private InfoDbHelper _db;
	private static final int COLORS[] = new int[] { 0xFF0099CC, 0xFF9933CC,
			0xFF669900, 0xFFFF8800, 0xFFCC0000 };

	public NavigationListAdapter(Activity activity, List<String> therapies,
			Map<String, List<String>> dbtCollections) {
		_activity = activity;
		_dbtCollections = dbtCollections;
		_therapies = therapies;
		_db = new InfoDbHelper(_activity.getApplicationContext());
	}

	public void set_therapyLauncher(TherapyLauncher therapyLauncher) {
		_therapyLauncher = therapyLauncher;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return _dbtCollections.get(_therapies.get(groupPosition)).get(
				childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String therapy = (String) getChild(groupPosition, childPosition);
		LayoutInflater inflater = _activity.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.navigation_listitem, null);
		}

		TextView item = (TextView) convertView.findViewById(R.id.nav_listitem);
		item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (_therapyLauncher != null) {
					_therapyManager = new TherapyManager();

					Class<?> therapyClass = _therapyManager.get_therapies()
							.get(therapy);
					_therapyLauncher.onTherapyLaunch(therapyClass);
				}
			}

		});

		final ImageView button = (ImageView) convertView
				.findViewById(R.id.nav_button);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				button.setImageResource(R.drawable.tire_pressed);
				List<String> child = _dbtCollections.get(_therapies
						.get(groupPosition));
				

				_db.updateOrAddCrisis(therapy);
				//child.remove(childPosition);
				//notifyDataSetChanged();

			}
		});

		item.setText(therapy);
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return _dbtCollections.get(_therapies.get(groupPosition)).size();
	}

	public Object getGroup(int groupPosition) {
		return _therapies.get(groupPosition);
	}

	public int getGroupCount() {
		return _therapies.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final String therapyName = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) _activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.navigation_group_item,
					null);
		}

		ImageView colour = (ImageView) convertView
				.findViewById(R.id.item_color);
		colour.setBackgroundColor(COLORS[groupPosition % COLORS.length]);

		TextView item = (TextView) convertView.findViewById(R.id.group_text);
		item.setTypeface(null, Typeface.BOLD);
		item.setText(therapyName);

		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}