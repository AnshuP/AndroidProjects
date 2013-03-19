package com.example.classproject_anshup;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;



public class MainActivity extends Activity {

	
	private ActionBar mActionBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
     // Turn off animations
     		getWindow().setWindowAnimations(android.R.style.Animation);
        
        //Setup Action Bar tabs
        mActionBar = this.getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab t1 = mActionBar.newTab();
        t1.setText("Home");
        TabListener<Tab1Fragment> tab = new TabListener<Tab1Fragment>(this,
        		                "Home", Tab1Fragment.class);

        t1.setTabListener(tab);
        
        Tab t2 = mActionBar.newTab();
        t2.setText("Baby Book");
        TabListener<Tab2Fragment> tab2 = new TabListener<Tab2Fragment>(this,
                "Baby Book", Tab2Fragment.class);
        t2.setTabListener(tab2);
        
        /*Tab t3 = mActionBar.newTab();
        t3.setText("Baby Care");
        t3.setTabListener(this);*/
        
        Tab t3 = mActionBar.newTab();
        t3.setText("Deals");
        TabListener<Tab3Fragment> tab3 = new TabListener<Tab3Fragment>(this,
                "Deals", Tab3Fragment.class);
        t3.setTabListener(tab3);
        
        mActionBar.addTab(t1);
		mActionBar.addTab(t2);
		//mActionBar.addTab(t3);
    }

    
    
    private class TabListener<T extends Fragment> implements
                ActionBar.TabListener {
            private Fragment mFragment;
            private final Activity mActivity;
            private final String mTag;
            private final Class<T> mClass;
            
            public TabListener(Activity activity, String tag, Class<T> clz) {
            	            mActivity = activity;
            	            mTag = tag;
            	            mClass = clz;
            	        }
            
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		
        	}

        	public void onTabSelected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		// Check if the fragment is already initialized
        		            if (mFragment == null) {
        		                // If not, instantiate and add it to the activity
        		                mFragment = Fragment.instantiate(mActivity, mClass.getName());
        		                ft.add(android.R.id.content, mFragment, mTag);
        		            } else {
        		                // If it exists, simply attach it in order to show it
        		                ft.attach(mFragment);
        		            }

        		
        	}

        	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        		// TODO Auto-generated method stub
        		if (mFragment != null) {
        			                // Detach the fragment, because another one is being attached
        			                ft.detach(mFragment);
        			            }

        	}
    }


    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	

	
}
