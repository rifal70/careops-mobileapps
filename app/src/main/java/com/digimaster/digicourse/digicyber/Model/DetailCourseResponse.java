package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailCourseResponse{

	@SerializedName("course_detail")
	private List<CourseDetailItem> courseDetail;

	public void setCourseDetail(List<CourseDetailItem> courseDetail){
		this.courseDetail = courseDetail;
	}

	public List<CourseDetailItem> getCourseDetail(){
		return courseDetail;
	}

	@Override
 	public String toString(){
		return 
			"DetailCourseResponse{" + 
			"course_detail = '" + courseDetail + '\'' + 
			"}";
		}
}