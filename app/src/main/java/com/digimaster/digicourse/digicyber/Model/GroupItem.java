package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class GroupItem{

	@SerializedName("admin_id")
	private String adminId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("admin_name")
	private String adminName;

	public void setAdminId(String adminId){
		this.adminId = adminId;
	}

	public String getAdminId(){
		return adminId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAdminName(String adminName){
		this.adminName = adminName;
	}

	public String getAdminName(){
		return adminName;
	}

	@Override
 	public String toString(){
		return 
			"GroupItem{" + 
			"admin_id = '" + adminId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",admin_name = '" + adminName + '\'' + 
			"}";
		}
}