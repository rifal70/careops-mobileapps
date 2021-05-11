package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class LibraryItem{
	@SerializedName("course_id")
	private String courseId;

	@SerializedName("institut_name")
	private String institutName;

	@SerializedName("course_name")
	private String courseName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("Total_Module")
	private String TotalModule;

	@SerializedName("Total_Topic")
	private String TotalTopic;

	@SerializedName("Total_Action")
	private String TotalAction;



	public void setInstitutName(String institutName){
		this.institutName = institutName;
	}

	public String getInstitutName(){
		return institutName;
	}

	public void setCourseId(String courseId){
		this.courseId = courseId;
	}

	public String getCourseId(){
		return courseId;
	}


	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return courseName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTotalModule(String TotalModule){this.TotalModule = TotalModule;}
	public String getTotalModule() {return TotalModule;}

	public void setTotalTopic(String TotalTopic){this.TotalTopic = TotalTopic;}
	public String getTotalTopic() {return TotalTopic;}

	public void setTotalAction(String TotalAction){this.TotalAction = TotalAction;}
	public String getTotalAction() {return TotalAction;}
	@Override
	public String toString(){
		return
				"LibraryItem{" +
						"institut_name = '" + institutName + '\'' +
						",course_name = '" + courseName + '\'' +
						",created_at = '" + createdAt + '\'' +
						",Total_Action = '" + TotalAction + '\'' +
						",Total_Module = '" + TotalModule + '\'' +
						",Total_Topic = '" + TotalTopic + '\'' +
						",course_id = '" + courseId +'\''+
						"}";
	}
}