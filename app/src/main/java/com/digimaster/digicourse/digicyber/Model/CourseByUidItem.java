package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class CourseByUidItem{

	@SerializedName("image")
	private String image;

	@SerializedName("total_finished")
	private String totalFinished;

	@SerializedName("total_topic")
	private String totalTopic;

	@SerializedName("author")
	private String author;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("detail")
	private String detail;

	@SerializedName("title")
	private String title;

	@SerializedName("total_action")
	private String totalAction;

	@SerializedName("total_module")
	private String totalModule;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setTotalFinished(String totalFinished){
		this.totalFinished = totalFinished;
	}

	public String getTotalFinished(){
		return totalFinished;
	}

	public void setTotalTopic(String totalTopic){
		this.totalTopic = totalTopic;
	}

	public String getTotalTopic(){
		return totalTopic;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getAuthor(){
		return author;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setTotalAction(String totalAction){
		this.totalAction = totalAction;
	}

	public String getTotalAction(){
		return totalAction;
	}

	public void setTotalModule(String totalModule){
		this.totalModule = totalModule;
	}

	public String getTotalModule(){
		return totalModule;
	}

	@Override
 	public String toString(){
		return 
			"CourseByUidItem{" + 
			"image = '" + image + '\'' + 
			",total_finished = '" + totalFinished + '\'' + 
			",total_topic = '" + totalTopic + '\'' + 
			",author = '" + author + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",detail = '" + detail + '\'' + 
			",title = '" + title + '\'' + 
			",total_action = '" + totalAction + '\'' + 
			",total_module = '" + totalModule + '\'' + 
			"}";
		}
}