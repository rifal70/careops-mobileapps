package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailCourseResponseXXX {

	@SerializedName("vid")
	private String vid;

	@SerializedName("materi")
	private List<MateriItemXXX> materi;

	@SerializedName("img")
	private String img;

	@SerializedName("code")
	private int code;

	@SerializedName("special_price")
	private String specialPrice;

	@SerializedName("author")
	private String author;

	@SerializedName("price")
	private String price;

	@SerializedName("title")
	private String title;

	@SerializedName("slug")
	private String slug;

	@SerializedName("desc")
	private String desc;

	@SerializedName("vid_pre")
	private String vidPre;

	public void setVid(String vid){
		this.vid = vid;
	}

	public String getVid(){
		return vid;
	}

	public void setMateri(List<MateriItemXXX> materi){
		this.materi = materi;
	}

	public List<MateriItemXXX> getMateri(){
		return materi;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setSpecialPrice(String specialPrice){
		this.specialPrice = specialPrice;
	}

	public String getSpecialPrice(){
		return specialPrice;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	public void setVidPre(String vidPre){
		this.vidPre = vidPre;
	}

	public String getVidPre(){
		return vidPre;
	}

	@Override
 	public String toString(){
		return 
			"DetailCourseResponse{" + 
			"vid = '" + vid + '\'' + 
			",materi = '" + materi + '\'' + 
			",img = '" + img + '\'' + 
			",code = '" + code + '\'' + 
			",special_price = '" + specialPrice + '\'' + 
			",author = '" + author + '\'' + 
			",price = '" + price + '\'' + 
			",title = '" + title + '\'' + 
			",slug = '" + slug + '\'' + 
			",desc = '" + desc + '\'' + 
			",vid_pre = '" + vidPre + '\'' + 
			"}";
		}
}