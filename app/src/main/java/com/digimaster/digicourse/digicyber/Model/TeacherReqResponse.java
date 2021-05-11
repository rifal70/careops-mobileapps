package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherReqResponse{

	@SerializedName("teacher_request")
	private List<TeacherRequestItem> teacherRequest;

	public void setTeacherRequest(List<TeacherRequestItem> teacherRequest){
		this.teacherRequest = teacherRequest;
	}

	public List<TeacherRequestItem> getTeacherRequest(){
		return teacherRequest;
	}

	@Override
 	public String toString(){
		return 
			"TeacherReqResponse{" + 
			"teacher_request = '" + teacherRequest + '\'' + 
			"}";
		}
}