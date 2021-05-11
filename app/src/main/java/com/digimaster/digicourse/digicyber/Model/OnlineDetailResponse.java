package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineDetailResponse{

	@SerializedName("online")
	private List<OnlineItem> online;

	public void setOnline(List<OnlineItem> online){
		this.online = online;
	}

	public List<OnlineItem> getOnline(){
		return online;
	}

	@Override
 	public String toString(){
		return 
			"OnlineDetailResponse{" + 
			"online = '" + online + '\'' + 
			"}";
		}
}