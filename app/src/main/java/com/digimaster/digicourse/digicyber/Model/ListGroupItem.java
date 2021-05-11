package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ListGroupItem{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("group_name")
	private String groupName;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setGroupId(String groupId){
		this.groupId = groupId;
	}

	public String getGroupId(){
		return groupId;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return groupName;
	}

	@Override
 	public String toString(){
		return 
			"ListGroupItem{" + 
			"user_id = '" + userId + '\'' + 
			",group_id = '" + groupId + '\'' + 
			",group_name = '" + groupName + '\'' + 
			"}";
		}
}