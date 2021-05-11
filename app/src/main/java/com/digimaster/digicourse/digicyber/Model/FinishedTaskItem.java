package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class FinishedTaskItem{

	@SerializedName("uid")
	private String uid;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("submission")
	private String submission;

	@SerializedName("material_name")
	private String materialName;

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setSubmission(String submission){
		this.submission = submission;
	}

	public String getSubmission(){
		return submission;
	}

	public void setMaterialName(String materialName){
		this.materialName = materialName;
	}

	public String getMaterialName(){
		return materialName;
	}

	@Override
 	public String toString(){
		return 
			"FinishedTaskItem{" + 
			"uid = '" + uid + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",submission = '" + submission + '\'' + 
			",material_name = '" + materialName + '\'' + 
			"}";
		}
}