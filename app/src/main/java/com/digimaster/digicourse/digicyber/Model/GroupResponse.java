package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupResponse{

	@SerializedName("group")
	private List<GroupItem> group;

	public void setGroup(List<GroupItem> group){
		this.group = group;
	}

	public List<GroupItem> getGroup(){
		return group;
	}

	@Override
 	public String toString(){
		return 
			"GroupResponse{" + 
			"group = '" + group + '\'' + 
			"}";
		}
}