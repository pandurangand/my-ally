package com.dbt.myally;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class EarthActivity extends Activity {

    //private EarthRenderer mRenderer; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_frame);
        //mSurfaceView.setZOrderMediaOverlay(true);
			//setGLBackgroundTransparent(true);
			
        LoadModelFragment fragment = new LoadModelFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        
      
    }
}