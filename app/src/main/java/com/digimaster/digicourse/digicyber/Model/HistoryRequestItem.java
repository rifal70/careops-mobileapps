package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class HistoryRequestItem{

	@SerializedName("date")
	private String date;

	@SerializedName("teacher_name")
	private String teacherName;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("teacher_id")
	private String teacherId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("time")
	private String time;

	@SerializedName("schedule_id")
	private String scheduleId;

	@SerializedName("status")
	private String status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setTeacherName(String teacherName){
		this.teacherName = teacherName;
	}

	public String getTeacherName(){
		return teacherName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}

	public String getTeacherId(){
		return teacherId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setScheduleId(String scheduleId){
		this.scheduleId = scheduleId;
	}

	public String getScheduleId(){
		return scheduleId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"HistoryRequestItem{" + 
			"date = '" + date + '\'' + 
			",teacher_name = '" + teacherName + '\'' + 
			",user_id = '" + userId + '\'' + 
			",teacher_id = '" + teacherId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",time = '" + time + '\'' + 
			",schedule_id = '" + scheduleId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}