package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class Request{

	@SerializedName("uid")
	private String uid;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	@Override
 	public String toString(){
		return 
			"Request{" + 
			"uid = '" + uid + '\'' + 
			"}";
		}
}