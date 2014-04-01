package com.ece1778.myally.ui;

import java.util.ArrayList;

import com.ece1778.myally.dbt.TherapyManager;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NavigationDrawer implements ListView.OnItemClickListener {
	public interface TherapyLauncher {
		public void onTherapyLaunch(Class<?> therapy);
	}
	
	private DrawerLayout _drawerLayout;
	
	private ListView _drawerList;
	
	private TherapyLauncher _therapyLauncher;
	
	private TherapyManager _therapyManager;
	
	ArrayList<String> _therapies;
	
	public NavigationDrawer(final Context context, 
			DrawerLayout drawerLayout, ListView drawerList) {
		_drawerLayout = drawerLayout;
		_drawerList = drawerList;
		
		_therapyManager = new TherapyManager();
		_therapies = new ArrayList<String>();
		_therapies.addAll(_therapyManager.get_therapies().keySet());
		
		
		_drawerList.setAdapter(new ArrayAdapter<String>(context, 
				android.R.layout.simple_list_item_1, _therapies));
		_drawerList.setOnItemClickListener(this);
	}

	public void set_therapyLauncher(TherapyLauncher therapyLauncher) {
		_therapyLauncher = therapyLauncher;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, 
			long id) {
		String therapy = _therapies.get(position);
		
		Class<?> therapyClass = _therapyManager.get_therapies().get(therapy);
		_therapyLauncher.onTherapyLaunch(therapyClass);
	}

	public DrawerLayout get_drawerLayout() {
		return _drawerLayout;
	}
}
