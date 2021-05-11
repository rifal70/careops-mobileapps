package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MaterialResponse{

	@SerializedName("material")
	private List<MaterialItem> material;

	public void setMaterial(List<MaterialItem> material){
		this.material = material;
	}

	public List<MaterialItem> getMaterial(){
		return material;
	}

	@Override
 	public String toString(){
		return 
			"MaterialResponse{" + 
			"material = '" + material + '\'' + 
			"}";
		}
}