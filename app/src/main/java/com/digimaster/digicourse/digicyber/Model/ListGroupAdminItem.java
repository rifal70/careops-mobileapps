package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ListGroupAdminItem{

	@SerializedName("group_id")
	private String groupId;

	@SerializedName("group_name")
	private String groupName;

	@SerializedName("admin_id")
	private String adminId;

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

	public void setAdminId(String adminId){
		this.adminId = adminId;
	}

	public String getAdminId(){
		return adminId;
	}

	@Override
 	public String toString(){
		return 
			"ListGroupAdminItem{" + 
			"group_id = '" + groupId + '\'' + 
			",group_name = '" + groupName + '\'' + 
			",admin_id = '" + adminId + '\'' + 
			"}";
		}
}