package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryDetailResponse{

	@SerializedName("library_detail")
	private List<LibraryDetailItem> libraryDetail;

	public void setLibraryDetail(List<LibraryDetailItem> libraryDetail){
		this.libraryDetail = libraryDetail;
	}

	public List<LibraryDetailItem> getLibraryDetail(){
		return libraryDetail;
	}

	@Override
 	public String toString(){
		return 
			"LibraryDetailResponse{" + 
			"library_detail = '" + libraryDetail + '\'' + 
			"}";
		}
}