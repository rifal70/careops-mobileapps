package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class MaterialItem{

	@SerializedName("section_name")
	private String sectionName;

	public void setSectionName(String sectionName){
		this.sectionName = sectionName;
	}

	public String getSectionName(){
		return sectionName;
	}

	@Override
 	public String toString(){
		return 
			"MaterialItem{" + 
			"section_name = '" + sectionName + '\'' + 
			"}";
		}
}