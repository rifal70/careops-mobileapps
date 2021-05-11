package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ModuleDetailItem{

	@SerializedName("module_finish")
	private String moduleFinish;

	@SerializedName("course_access")
	private String courseAccess;

	@SerializedName("course_author")
	private String courseAuthor;

	@SerializedName("module_id")
	private String moduleId;

	@SerializedName("module_desc")
	private String moduleDesc;

	@SerializedName("total_topic")
	private String totalTopic;

	@SerializedName("module_image")
	private String moduleImage;

	@SerializedName("module_name")
	private String moduleName;

	public void setModuleFinish(String moduleFinish){
		this.moduleFinish = moduleFinish;
	}

	public String getModuleFinish(){
		return moduleFinish;
	}

	public void setCourseAccess(String courseAccess){
		this.courseAccess = courseAccess;
	}

	public String getCourseAccess(){
		return courseAccess;
	}

	public void setCourseAuthor(String courseAuthor){
		this.courseAuthor = courseAuthor;
	}

	public String getCourseAuthor(){
		return courseAuthor;
	}

	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}

	public String getModuleId(){
		return moduleId;
	}

	public void setModuleDesc(String moduleDesc){
		this.moduleDesc = moduleDesc;
	}

	public String getModuleDesc(){
		return moduleDesc;
	}

	public void setTotalTopic(String totalTopic){
		this.totalTopic = totalTopic;
	}

	public String getTotalTopic(){
		return totalTopic;
	}

	public void setModuleImage(String moduleImage){
		this.moduleImage = moduleImage;
	}

	public String getModuleImage(){
		return moduleImage;
	}

	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}

	public String getModuleName(){
		return moduleName;
	}

	@Override
 	public String toString(){
		return 
			"ModuleDetailItem{" + 
			"module_finish = '" + moduleFinish + '\'' + 
			",course_access = '" + courseAccess + '\'' + 
			",course_author = '" + courseAuthor + '\'' + 
			",module_id = '" + moduleId + '\'' + 
			",module_desc = '" + moduleDesc + '\'' + 
			",total_topic = '" + totalTopic + '\'' + 
			",module_image = '" + moduleImage + '\'' + 
			",module_name = '" + moduleName + '\'' + 
			"}";
		}
}