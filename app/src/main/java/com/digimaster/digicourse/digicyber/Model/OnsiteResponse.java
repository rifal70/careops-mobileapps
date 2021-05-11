package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnsiteResponse{

	@SerializedName("onsite")
	private List<OnsiteItem> onsite;

	public void setOnsite(List<OnsiteItem> onsite){
		this.onsite = onsite;
	}

	public List<OnsiteItem> getOnsite(){
		return onsite;
	}

	@Override
 	public String toString(){
		return 
			"OnsiteResponse{" + 
			"onsite = '" + onsite + '\'' + 
			"}";
		}
}