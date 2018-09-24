package com.zaxtyson.qmkg;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.preference.*;
import android.support.v7.app.*;
import android.widget.*;
import SettingsFragment;


public class SettingsActivity extends ActionBarActivity{

    private SettingsFragment mSettingsFragment;
	private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
		mContext=this;
        if ( savedInstanceState==null ){
            mSettingsFragment=new SettingsFragment();
            replaceFragment(R.id.settings_container,mSettingsFragment);
			/* api21时preference没有actionBar，这里给它加上，
			 业务逻辑在SettingsFragment里面处理
			 */
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId,android.app.Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId,fragment).commit();
    }

	// 在SettingsFragment中调用Settings.getContext()即可得到当前上下文
	public static Context getContext(){
		return mContext;
	}
}

