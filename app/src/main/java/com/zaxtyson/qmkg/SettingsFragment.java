import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.v7.appcompat.*;
import android.view.*;
import com.zaxtyson.qmkg.*;

import android.support.v7.appcompat.R;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener{

	private EditTextPreference save_path;
	private CheckBoxPreference allowed_vibrate;
	private CheckBoxPreference allowed_alarm;
	private CheckBoxPreference down_cover;
	private Preference about;
	private Preference feedback;
	private Context mContext = null;

	@Override  
	public void onCreate(Bundle savedInstanceState){  
		super.onCreate(savedInstanceState);  
		addPreferencesFromResource(R.xml.settings);

		save_path=(EditTextPreference)findPreference("save_path");
		allowed_vibrate=(CheckBoxPreference)findPreference("allowed_vibrate");
		allowed_alarm=(CheckBoxPreference)findPreference("allowed_alarm");
		down_cover=(CheckBoxPreference)findPreference("down_cover");
		about=(Preference)findPreference("about");
		feedback=(Preference)findPreference("feedback");

		// 注册设置选项点击事件
		save_path.setOnPreferenceClickListener(this); 
		about.setOnPreferenceClickListener(this); 
		feedback.setOnPreferenceClickListener(this);  

		// 注册设置开关改变事件
		allowed_vibrate.setOnPreferenceChangeListener(this);  
		allowed_alarm.setOnPreferenceChangeListener(this);
		down_cover.setOnPreferenceChangeListener(this);

		mContext=SettingsActivity.getContext();
	}


	@Override
	public boolean onPreferenceChange(Preference preference,Object newValue){

		// 监听开关改变事件 
		switch ( preference.getKey() ){
			default:
				//Toast.makeText(mContext,preference.getKey()+"状态改变:"+newValue,Toast.LENGTH_SHORT).show();
		}
		return true;
	}  

	@Override
	public boolean onPreferenceClick(Preference preference){  

		// 监听设置项点击事件
		switch ( preference.getKey() ){
			case "about":
				AlertDialog alert=new AlertDialog.Builder(mContext).create(); 
				alert.show();
				alert.setContentView(R.layout.about);
				break;

			case "feedback":
				String qqUrl = "mqqwpa://im/chat?chat_type=wpa&uin=3034557307&version=1";
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(qqUrl)));
				break;

			default:
				//Toast.makeText(mContext,preference.getKey()+"被点击",Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	} 






}
