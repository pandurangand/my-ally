package com.ece1778.myally.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;

import com.ece1778.myally.community.EarthActivity;
import com.ece1778.myally.dbt.TherapyManager;
import com.ece1778.myally.progress.ProgressActivity;

/**
 * The class for the Navigation Drawer accessible from the home screen.
 * 
 * @author Mario
 * 
 */
public class NavigationDrawer {
	/**
	 * An interface to launch DBT therapies.
	 * 
	 * @author Mario
	 * 
	 */
	public interface TherapyLauncher {
		public void onTherapyLaunch(Class<?> therapy);
	}

	// The Drawer Layout
	private DrawerLayout _drawerLayout;
	// The class responsible for launching DBT therapies
	private TherapyLauncher _therapyLauncher;
	// The therapies available in the app
	private TherapyManager _therapyManager;
	// The names of therapies available in the app
	ArrayList<String> _therapies;

	private List<String> _groupList;
	private List<String> _childList;
	private Map<String, List<String>> _dbtCollection;
	private ExpandableListView _expListView;

	public NavigationDrawer(final Activity activity, DrawerLayout drawerLayout,
			ExpandableListView expandableListView) {
		_drawerLayout = drawerLayout;
		_expListView = expandableListView;

		createGroupList();
		createCollection();

		// Setup the ListView with data and listen for selections
		final NavigationListAdapter expListAdapter = new NavigationListAdapter(
				activity, _groupList, _dbtCollection, _drawerLayout);
		expListAdapter.set_therapyLauncher((TherapyLauncher) activity);
		_expListView.setGroupIndicator(null);
		_expListView.setAdapter(expListAdapter);
		_expListView.setItemsCanFocus(true);
		_expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {

				String groupName = _groupList.get(groupPosition);
				if (!groupName.equals("Activities")) {
					_drawerLayout.closeDrawers();
				}
				if (groupName.equals("Progress")) {
					_therapyLauncher.onTherapyLaunch(ProgressActivity.class);
				} else if (groupName.equalsIgnoreCase("community")) {
					_therapyLauncher.onTherapyLaunch(EarthActivity.class);
				}
				return false;
			}

		});

	}

	public void set_therapyLauncher(TherapyLauncher therapyLauncher) {
		_therapyLauncher = therapyLauncher;
	}


	public DrawerLayout get_drawerLayout() {
		return _drawerLayout;
	}

	private void createGroupList() {
		_groupList = new ArrayList<String>();
		_groupList.add("Activities");
		_groupList.add("Progress");
		_groupList.add("Community");
	}

	private void createCollection() {
		// Create a therapy manager (should probably be a singleton)
		_therapyManager = new TherapyManager();
		_therapies = new ArrayList<String>();
		_therapies.addAll(_therapyManager.get_therapies().keySet());

		_dbtCollection = new LinkedHashMap<String, List<String>>();

		for (String listitem : _groupList) {
			if (listitem.equals("Activities")) {
				loadChild(_therapies);
			} else {
				loadChild(null);
			}
			_dbtCollection.put(listitem, _childList);
		}
	}

	private void loadChild(ArrayList<String> therapies) {
		_childList = new ArrayList<String>();
		if (therapies != null) {
			for (String model : therapies)
				_childList.add(model);
		}
	}

}