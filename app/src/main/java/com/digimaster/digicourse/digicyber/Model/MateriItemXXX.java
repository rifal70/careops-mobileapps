package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class MateriItemXXX {

	@SerializedName("pdf")
	private Object pdf;

	@SerializedName("material")
	private String material;

	@SerializedName("submaterial")
	private String submaterial;

	@SerializedName("material_id")
	private String materialId;

	@SerializedName("video")
	private String video;

	public void setPdf(Object pdf){
		this.pdf = pdf;
	}

	public Object getPdf(){
		return pdf;
	}

	public void setMaterial(String material){
		this.material = material;
	}

	public String getMaterial(){
		return material;
	}

	public void setSubmaterial(String submaterial){
		this.submaterial = submaterial;
	}

	public String getSubmaterial(){
		return submaterial;
	}

	public void setMaterialId(String materialId){
		this.materialId = materialId;
	}

	public String getMaterialId(){
		return materialId;
	}

	public void setVideo(String video){
		this.video = video;
	}

	public String getVideo(){
		return video;
	}

	@Override
 	public String toString(){
		return 
			"MateriItem{" +
			"pdf = '" + pdf + '\'' + 
			",material = '" + material + '\'' + 
			",submaterial = '" + submaterial + '\'' + 
			",material_id = '" + materialId + '\'' + 
			",video = '" + video + '\'' + 
			"}";
		}
}