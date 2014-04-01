package com.ece1778.myally.ui;

import java.util.ArrayList;

import com.ece1778.myally.dbt.TherapyManager;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * The class for the Navigation Drawer accessible from the home screen.
 * 
 * @author Mario
 *
 */
public class NavigationDrawer implements ListView.OnItemClickListener {
	/**
	 * An interface to launch DBT therapies. 
	 * 
	 * @author Mario
	 *
	 */
	public interface TherapyLauncher {
		public void onTherapyLaunch(Class<?> therapy);
	}
	//The Drawer Layout
	private DrawerLayout _drawerLayout;
	//The list UI
	private ListView _drawerList;
	//The class responsible for launching DBT therapies
	private TherapyLauncher _therapyLauncher;
	//The therapies available in the app
	private TherapyManager _therapyManager;
	//The names of therapies available in the app
	ArrayList<String> _therapies;
	
	public NavigationDrawer(final Context context, 
			DrawerLayout drawerLayout, ListView drawerList) {
		_drawerLayout = drawerLayout;
		_drawerList = drawerList;
		
		//Create a therapy manager (should probably be a singleton)
		_therapyManager = new TherapyManager();
		_therapies = new ArrayList<String>();
		_therapies.addAll(_therapyManager.get_therapies().keySet());
		
		//Setup the ListView with data and listen for selections
		_drawerList.setAdapter(new ArrayAdapter<String>(context, 
				android.R.layout.simple_list_item_1, _therapies));
		_drawerList.setOnItemClickListener(this);
	}

	public void set_therapyLauncher(TherapyLauncher therapyLauncher) {
		if(_therapyLauncher != null) {
			_therapyLauncher = therapyLauncher;
		}
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
