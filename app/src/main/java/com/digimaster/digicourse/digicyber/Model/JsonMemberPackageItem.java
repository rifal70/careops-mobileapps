package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class JsonMemberPackageItem{

	@SerializedName("price")
	private String price;

	@SerializedName("package_name")
	private String packageName;

	@SerializedName("school_level")
	private String schoolLevel;

	@SerializedName("package_id")
	private String packageId;

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setPackageName(String packageName){
		this.packageName = packageName;
	}

	public String getPackageName(){
		return packageName;
	}

	public void setSchoolLevel(String schoolLevel){
		this.schoolLevel = schoolLevel;
	}

	public String getSchoolLevel(){
		return schoolLevel;
	}

	public void setPackageId(String packageId){
		this.packageId = packageId;
	}

	public String getPackageId(){
		return packageId;
	}

	@Override
 	public String toString(){
		return 
			"JsonMemberPackageItem{" + 
			"price = '" + price + '\'' + 
			",package_name = '" + packageName + '\'' + 
			",school_level = '" + schoolLevel + '\'' + 
			",package_id = '" + packageId + '\'' + 
			"}";
		}
}