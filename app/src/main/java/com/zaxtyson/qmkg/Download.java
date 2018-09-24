package com.zaxtyson.qmkg;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.zaxtyson.qmkg.*;

public class Download extends ActionBarActivity implements View.OnClickListener{

	CoverImageView cover;
	Button down;

	String title;
	String cover_link;
	String music_link;
	BroadcastReceiver receiver = null;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.down);
		cover=(CoverImageView) findViewById(R.id.cover_image);
		down=(Button) findViewById(R.id.down_music);
		down.setOnClickListener(this);

		//接收数据
		Intent i = getIntent();
		title=i.getStringExtra("title");
		cover_link=i.getStringExtra("cover_link");
		music_link=i.getStringExtra("music_link");
		setTitle(title);
		cover.setImageURL(cover_link);
	}

	@Override
	public void onClick(View p1){
		switch ( p1.getId() ){
			case R.id.down_music:
				// 获取设置的下载目录，这里的根目录为/sdcard，如/Music指的是/sdcard/Music
				SharedPreferences settings = getSharedPreferences("com.zaxtyson.qmkg_preferences",0);
				String save_path = settings.getString("save_path","/");
				Boolean down_cover = settings.getBoolean("down_cover",false);

				// 如果设置了下载封面
				if ( down_cover ){
					String filenmae = title+".png";
					Toast.makeText(this," (/^▽^)/ 开始下载<"+filenmae+">",Toast.LENGTH_SHORT).show();
					DownFile(cover_link,save_path,filenmae);
				}
				// 下载音乐文件,全民k歌下载的音乐格式为m4a，改成mp3后缀会导致部分软件无法播放
				String filenmae = title+".m4a"; 
				Toast.makeText(this," (/^▽^)/ 开始下载<"+filenmae+">",Toast.LENGTH_SHORT).show();
				DownFile(music_link,save_path,filenmae);

				break;
		}
	}

	/* DownFile(链接,保存路径,文件名) */
	private void DownFile(String url,String path,String filename){
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
		request.setTitle("下载");
		request.setDescription("正在下载<"+filename+">");
		request.setDestinationInExternalPublicDir(path,filename);
		DownloadManager dm= (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		dm.enqueue(request);
	}


}



