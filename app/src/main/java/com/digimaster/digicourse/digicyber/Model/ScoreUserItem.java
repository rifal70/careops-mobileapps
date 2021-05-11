package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ScoreUserItem{

	@SerializedName("course_id")
	private String courseId;

	@SerializedName("score")
	private String score;

	@SerializedName("course_name")
	private String courseName;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("score_id")
	private String scoreId;

	@SerializedName("material_id")
	private String materialId;

	@SerializedName("material_name")
	private String materialName;

	public void setCourseId(String courseId){
		this.courseId = courseId;
	}

	public String getCourseId(){
		return courseId;
	}

	public void setScore(String score){
		this.score = score;
	}

	public String getScore(){
		return score;
	}

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return courseName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setScoreId(String scoreId){
		this.scoreId = scoreId;
	}

	public String getScoreId(){
		return scoreId;
	}

	public void setMaterialId(String materialId){
		this.materialId = materialId;
	}

	public String getMaterialId(){
		return materialId;
	}

	public void setMaterialName(String materialName){
		this.materialName = materialName;
	}

	public String getMaterialName(){
		return materialName;
	}

	@Override
 	public String toString(){
		return 
			"ScoreUserItem{" + 
			"course_id = '" + courseId + '\'' + 
			",score = '" + score + '\'' + 
			",course_name = '" + courseName + '\'' + 
			",user_id = '" + userId + '\'' + 
			",score_id = '" + scoreId + '\'' + 
			",material_id = '" + materialId + '\'' + 
			",material_name = '" + materialName + '\'' + 
			"}";
		}
}