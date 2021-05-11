package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageResponse{

	@SerializedName("package")
	private List<JsonMemberPackageItem> jsonMemberPackage;

	public void setJsonMemberPackage(List<JsonMemberPackageItem> jsonMemberPackage){
		this.jsonMemberPackage = jsonMemberPackage;
	}

	public List<JsonMemberPackageItem> getJsonMemberPackage(){
		return jsonMemberPackage;
	}

	@Override
 	public String toString(){
		return 
			"PackageResponse{" + 
			"package = '" + jsonMemberPackage + '\'' + 
			"}";
		}
}