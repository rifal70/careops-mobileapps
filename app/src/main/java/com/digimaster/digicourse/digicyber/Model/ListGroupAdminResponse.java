package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListGroupAdminResponse{

	@SerializedName("list_group_admin")
	private List<ListGroupAdminItem> listGroupAdmin;

	public void setListGroupAdmin(List<ListGroupAdminItem> listGroupAdmin){
		this.listGroupAdmin = listGroupAdmin;
	}

	public List<ListGroupAdminItem> getListGroupAdmin(){
		return listGroupAdmin;
	}

	@Override
 	public String toString(){
		return 
			"ListGroupAdminResponse{" + 
			"list_group_admin = '" + listGroupAdmin + '\'' + 
			"}";
		}
}