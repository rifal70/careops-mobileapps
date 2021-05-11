package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberResponse{

	@SerializedName("member")
	private List<MemberItem> member;

	public void setMember(List<MemberItem> member){
		this.member = member;
	}

	public List<MemberItem> getMember(){
		return member;
	}

	@Override
 	public String toString(){
		return 
			"MemberResponse{" + 
			"member = '" + member + '\'' + 
			"}";
		}
}