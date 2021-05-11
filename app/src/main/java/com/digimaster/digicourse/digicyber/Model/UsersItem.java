package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class UsersItem{

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	public UsersItem(String phone) {
		this.phone = phone;
	}

	@SerializedName("id")
	private String id;

	@SerializedName("group")
	private String group;

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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setGroup(String group){
		this.group = group;
	}

	public String getGroup(){
		return group;
	}

	@Override
 	public String toString(){
		return 
			"UsersItem{" + 
			"phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",group = '" + group + '\'' + 
			"}";
		}
}