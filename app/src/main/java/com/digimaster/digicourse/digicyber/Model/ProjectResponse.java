package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectResponse{

	@SerializedName("users")
	private List<UsersItem> users;

	public void setUsers(List<UsersItem> users){
		this.users = users;
	}

	public List<UsersItem> getUsers(){
		return users;
	}

	@Override
 	public String toString(){
		return 
			"ProjectResponse{" + 
			"users = '" + users + '\'' + 
			"}";
		}
}