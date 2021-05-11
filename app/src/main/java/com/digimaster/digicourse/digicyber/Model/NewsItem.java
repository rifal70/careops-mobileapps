package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class NewsItem{

	@SerializedName("time_posted")
	private String timePosted;

	@SerializedName("title")
	private String title;

	@SerializedName("news_id")
	private String newsId;

	@SerializedName("isi")
	private String isi;

	public void setTimePosted(String timePosted){
		this.timePosted = timePosted;
	}

	public String getTimePosted(){
		return timePosted;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setNewsId(String newsId){
		this.newsId = newsId;
	}

	public String getNewsId(){
		return newsId;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	@Override
 	public String toString(){
		return 
			"NewsItem{" + 
			"time_posted = '" + timePosted + '\'' + 
			",title = '" + title + '\'' + 
			",news_id = '" + newsId + '\'' + 
			",isi = '" + isi + '\'' + 
			"}";
		}
}