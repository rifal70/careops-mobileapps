package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CourseResponse{

	@SerializedName("course_by_uid")
	private List<CourseByUidItem> courseByUid;

	public void setCourseByUid(List<CourseByUidItem> courseByUid){
		this.courseByUid = courseByUid;
	}

	public List<CourseByUidItem> getCourseByUid(){
		return courseByUid;
	}

	@Override
 	public String toString(){
		return 
			"CourseResponse{" + 
			"course_by_uid = '" + courseByUid + '\'' + 
			"}";
		}
}