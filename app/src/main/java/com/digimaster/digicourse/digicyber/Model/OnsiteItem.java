package com.digimaster.digicourse.digicyber.Model;


import com.google.gson.annotations.SerializedName;


public class OnsiteItem{

	@SerializedName("image")
	private String image;

	@SerializedName("course_type")
	private String courseType;

	@SerializedName("author")
	private String author;

	@SerializedName("price")
	private String price;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCourseType(String courseType){
		this.courseType = courseType;
	}

	public String getCourseType(){
		return courseType;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"OnsiteItem{" + 
			"image = '" + image + '\'' + 
			",course_type = '" + courseType + '\'' + 
			",author = '" + author + '\'' + 
			",price = '" + price + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}