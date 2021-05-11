package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ListMemberAdminItem{

	@SerializedName("member_id")
	private String memberId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("user_phone")
	private String userPhone;

	public void setMemberId(String memberId){
		this.memberId = memberId;
	}

	public String getMemberId(){
		return memberId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserPhone(String userPhone){
		this.userPhone = userPhone;
	}

	public String getUserPhone(){
		return userPhone;
	}

	@Override
 	public String toString(){
		return 
			"ListMemberAdminItem{" + 
			"member_id = '" + memberId + '\'' + 
			",user_name = '" + userName + '\'' + 
			",user_phone = '" + userPhone + '\'' + 
			"}";
		}
}