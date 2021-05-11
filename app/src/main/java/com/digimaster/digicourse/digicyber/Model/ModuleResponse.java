package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModuleResponse{

	@SerializedName("module_detail")
	private List<ModuleDetailItem> moduleDetail;

	@SerializedName("course_image")
	private String courseImage;

	public void setModuleDetail(List<ModuleDetailItem> moduleDetail){
		this.moduleDetail = moduleDetail;
	}

	public List<ModuleDetailItem> getModuleDetail(){
		return moduleDetail;
	}

	public void setCourseImage(String courseImage){
		this.courseImage = courseImage;
	}

	public String getCourseImage(){
		return courseImage;
	}

	@Override
 	public String toString(){
		return 
			"ModuleResponse{" + 
			"module_detail = '" + moduleDetail + '\'' + 
			",course_image = '" + courseImage + '\'' + 
			"}";
		}
}