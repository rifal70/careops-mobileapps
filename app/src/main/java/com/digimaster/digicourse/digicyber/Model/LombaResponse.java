package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LombaResponse{

	@SerializedName("lomba")
	private List<LombaItem> lomba;

	public void setLomba(List<LombaItem> lomba){
		this.lomba = lomba;
	}

	public List<LombaItem> getLomba(){
		return lomba;
	}

	@Override
 	public String toString(){
		return 
			"LombaResponse{" + 
			"lomba = '" + lomba + '\'' + 
			"}";
		}
}