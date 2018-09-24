package com.zaxtyson.qmkg;

import java.io.*;
import java.util.regex.*;
import okhttp3.*;

public class HtmlParser{

	String title = null;
	String music_link = null;
	String cover_link = null;

	public void startParser(String url){
		try{
			String regex = "fb_cover\":\"(.*)\",\"file_mid.*playurl\":\"(.*)\",\"playurl_video.*song_name\":\"(.*)\",\"tail_name";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(getHtml(url));
			if ( m.find() ){
				this.cover_link=m.group(1).toString();//封面链接
				this.music_link=m.group(2).toString();//音乐下载地址
				this.title=m.group(3).toString();//歌曲标题
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	private String getHtml(String url){
		//新线程后台解析
		getHtmlThread myThread = new getHtmlThread(url);
		myThread.start();
		try{
			myThread.join(); //将子线程合并到主线程，否则返回的值始终为null
		}
		catch (InterruptedException e){}
		return myThread.html_data;
	}
}

class getHtmlThread extends Thread{

	String html_data;
	String raw_url;
	public getHtmlThread(String url){
		this.raw_url=url;
	}
	//使用okhttp获取网页源码
	@Override
	public void run(){
		try{
			String agent="User-Agent:Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) App leWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D257 Safari/9537.53";
			OkHttpClient client = new OkHttpClient();
			Request req = new Request.Builder()
			.url(this.raw_url)
			.addHeader("User-Agent",agent)
			.build();
			Response res = client.newCall(req).execute();
			this.html_data=res.body().string();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}

