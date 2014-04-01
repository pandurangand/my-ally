package com.ece1778.myally;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.TextSwitcher;

import com.ece1778.myally.database.InfoDbHelper;
import com.ece1778.myally.ui.NavigationDrawer;
import com.ece1778.myally.ui.NavigationDrawer.TherapyLauncher;
import com.ece1778.myally.ui.QuoteSwitcher;

public class MainActivity extends Activity implements TherapyLauncher {
	//TextSwitcher used to display quotes in View
	private QuoteSwitcher _quotesSwitcher;

	private NavigationDrawer _navigationDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InfoDbHelper db = new InfoDbHelper(this);
		
		_quotesSwitcher = new QuoteSwitcher(getApplicationContext(), 
				(TextSwitcher)findViewById(R.id.FadingQuoteSwitcher), 
				getResources().getStringArray(R.array.quotes));
		_quotesSwitcher.init();

		_navigationDrawer = new NavigationDrawer(this,
				(DrawerLayout) findViewById(R.id.drawer_layout),
				(ExpandableListView) findViewById(R.id.left_drawer));
		_navigationDrawer.set_therapyLauncher(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTherapyLaunch(Class<?> therapy) {
		Intent intent = new Intent(MainActivity.this, therapy);
		startActivity(intent);

	}

}
