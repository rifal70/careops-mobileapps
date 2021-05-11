package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherResponse{

	@SerializedName("teachers")
	private List<TeachersItem> teachers;

	public void setTeachers(List<TeachersItem> teachers){
		this.teachers = teachers;
	}

	public List<TeachersItem> getTeachers(){
		return teachers;
	}

	@Override
 	public String toString(){
		return 
			"TeacherResponse{" + 
			"teachers = '" + teachers + '\'' + 
			"}";
		}
}