package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListGroupResponse{

	@SerializedName("list_group")
	private List<ListGroupItem> listGroup;

	public void setListGroup(List<ListGroupItem> listGroup){
		this.listGroup = listGroup;
	}

	public List<ListGroupItem> getListGroup(){
		return listGroup;
	}

	@Override
 	public String toString(){
		return 
			"ListGroupResponse{" + 
			"list_group = '" + listGroup + '\'' + 
			"}";
		}
}