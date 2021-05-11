package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class LibraryDetailItem{

	@SerializedName("sub_library_id")
	private String subLibraryId;

	@SerializedName("pdf")
	private String pdf;

	@SerializedName("library_id")
	private String libraryId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("status")
	private String status;

	public void setSubLibraryId(String subLibraryId){
		this.subLibraryId = subLibraryId;
	}

	public String getSubLibraryId(){
		return subLibraryId;
	}

	public void setPdf(String pdf){
		this.pdf = pdf;
	}

	public String getPdf(){
		return pdf;
	}

	public void setLibraryId(String libraryId){
		this.libraryId = libraryId;
	}

	public String getLibraryId(){
		return libraryId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LibraryDetailItem{" + 
			"sub_library_id = '" + subLibraryId + '\'' + 
			",pdf = '" + pdf + '\'' + 
			",library_id = '" + libraryId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}