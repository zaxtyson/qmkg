package com.zaxtyson.qmkg;

import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;

public class Main extends ActionBarActivity implements View.OnClickListener{

	private Button parser;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		parser=(Button)findViewById(R.id.parser);
		parser.setOnClickListener(this);

	}


	@Override
	public void onClick(View v){
		switch ( v.getId() ){
			case R.id.parser:

				if ( getNetType(this)==netType.noneNet ){
					Toast.makeText(this,"‼(•'╻'• )没网你要我怎么下啊",Toast.LENGTH_SHORT).show();
					break;
				}

				// 从剪贴板获取下载链接
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				String url=cm.getText().toString();
				if (url.isEmpty()){
					Toast.makeText(this,"(｡･ˇ_ˇ･｡:)你好歹输入点东西啊",Toast.LENGTH_SHORT).show();
					break;
				}
				if ( !isRightMusicUrl(url) ){
					Toast.makeText(this,"(╯>д<)╯这个音乐链接不对",Toast.LENGTH_SHORT).show();
					break;
				}

				// 开始解析数据
				HtmlParser myPaser = new HtmlParser();
				myPaser.startParser(url);

				if ( myPaser.cover_link.isEmpty() ){
					Toast.makeText(this,"（；￣д￣）解析过程异常",Toast.LENGTH_SHORT).show();
					break;
				}

				// 正常情况，跳转到下载页面，并传递解析好的链接过去
				Intent intent = new Intent();
				intent.putExtra("title",myPaser.title);
				intent.putExtra("cover_link",myPaser.cover_link);
				intent.putExtra("music_link",myPaser.music_link);
				intent.setClass(Main.this,Download.class);
				startActivity(intent);
				break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.main,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch ( item.getItemId() ){
			case R.id.setting:
				startActivity(new Intent(Main.this,SettingsActivity.class));
				break;
			case R.id.share:
				Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT,"(*σ´∀`)σ 一键导出全民k歌内所有音乐，快来试试吧~\nhttps://github.com/zaxtyson/qmkg");
                startActivity(Intent.createChooser(textIntent,"(o'ω'o)分享给谁哇"));
				break;

			case R.id.exit:
				this.finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}



	/* 链接检查，符合全民k歌的链接返回true，否则返回false */
	public boolean isRightMusicUrl(String url){
		if ( url.contains("qq.com") ){
			return true;
		}
		return false;
	}

	/* 网络检查，调用getNetType(context)，返回netType类型*/
	public static enum netType{WIFI, CMNET, CMWAP, noneNet}
    public static netType getNetType(Context context){
        ConnectivityManager connMgr = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if ( networkInfo==null ){
            return netType.noneNet;
        }
        int nType = networkInfo.getType();

        if ( nType==ConnectivityManager.TYPE_MOBILE ){
            if ( networkInfo.getExtraInfo().toLowerCase().equals("cmnet") ){
                return netType.CMNET;
            }

            else{
                return netType.CMWAP;
            }
        }
		else if ( nType==ConnectivityManager.TYPE_WIFI ){
            return netType.WIFI;
        }
        return netType.noneNet;

    }

}
