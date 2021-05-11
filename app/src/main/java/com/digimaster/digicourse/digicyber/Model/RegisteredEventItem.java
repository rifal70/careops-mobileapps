package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class RegisteredEventItem{

	@SerializedName("date")
	private String date;

	@SerializedName("uid")
	private String uid;

	@SerializedName("img")
	private String img;

	@SerializedName("name")
	private String name;

	@SerializedName("place")
	private String place;

	@SerializedName("desc")
	private String desc;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPlace(String place){
		this.place = place;
	}

	public String getPlace(){
		return place;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	@Override
 	public String toString(){
		return 
			"RegisteredEventItem{" + 
			"date = '" + date + '\'' + 
			",uid = '" + uid + '\'' + 
			",img = '" + img + '\'' + 
			",name = '" + name + '\'' + 
			",place = '" + place + '\'' + 
			",desc = '" + desc + '\'' + 
			"}";
		}
}