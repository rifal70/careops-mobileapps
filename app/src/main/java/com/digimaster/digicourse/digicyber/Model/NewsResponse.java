package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse{

	@SerializedName("news")
	private List<NewsItem> news;

	public void setNews(List<NewsItem> news){
		this.news = news;
	}

	public List<NewsItem> getNews(){
		return news;
	}

	@Override
 	public String toString(){
		return 
			"NewsResponse{" + 
			"news = '" + news + '\'' + 
			"}";
		}
}