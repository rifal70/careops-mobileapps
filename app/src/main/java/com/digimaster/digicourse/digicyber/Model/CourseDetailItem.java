package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class CourseDetailItem{

	@SerializedName("course_id")
	private String courseId;

	@SerializedName("course_description")
	private String courseDescription;

	public void setCourseId(String courseId){
		this.courseId = courseId;
	}

	public String getCourseId(){
		return courseId;
	}

	public void setCourseDescription(String courseDescription){
		this.courseDescription = courseDescription;
	}

	public String getCourseDescription(){
		return courseDescription;
	}

	@Override
	public String toString(){
		return
				"CourseDetailItem{" +
						"course_id = '" + courseId + '\'' +
						",course_description = '" + courseDescription + '\'' +
						"}";
	}
}