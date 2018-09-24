package com.zaxtyson.qmkg;

import android.app.*;
import android.content.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.widget.*;

/* 本类用于接收"下载完成"的广播，并在完成时进行提醒*/
public class DownloadBR extends BroadcastReceiver{
	@Override
	public void onReceive(Context context,Intent intent){
		if ( DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction()) ){
			Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show();
			SharedPreferences settings = context.getSharedPreferences("com.zaxtyson.qmkg_preferences",0);
			Boolean allowed_vibrate = settings.getBoolean("allowed_vibrate",false);
			Boolean allowed_alarm = settings.getBoolean("allowed_alarm",false);
			if ( allowed_alarm ){
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone mRingtone = RingtoneManager.getRingtone(context,notification);
				mRingtone.play();
			}
			if ( allowed_vibrate ){
				Vibrator mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
				mVibrator.vibrate(new long[]{500,200,500,200},-1);
			}
		}
	}
}

