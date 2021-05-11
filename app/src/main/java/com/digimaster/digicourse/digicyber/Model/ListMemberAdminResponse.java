package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListMemberAdminResponse{

	@SerializedName("list_member_admin")
	private List<ListMemberAdminItem> listMemberAdmin;

	public void setListMemberAdmin(List<ListMemberAdminItem> listMemberAdmin){
		this.listMemberAdmin = listMemberAdmin;
	}

	public List<ListMemberAdminItem> getListMemberAdmin(){
		return listMemberAdmin;
	}

	@Override
 	public String toString(){
		return 
			"ListMemberAdminResponse{" + 
			"list_member_admin = '" + listMemberAdmin + '\'' + 
			"}";
		}
}