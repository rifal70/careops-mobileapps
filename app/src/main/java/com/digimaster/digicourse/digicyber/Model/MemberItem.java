package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class MemberItem{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("group_name")
	private String groupName;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

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

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"MemberItem{" + 
			"user_id = '" + userId + '\'' + 
			",group_id = '" + groupId + '\'' + 
			",group_name = '" + groupName + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}