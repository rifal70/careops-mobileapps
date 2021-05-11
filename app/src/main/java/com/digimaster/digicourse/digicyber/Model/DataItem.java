package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("task_name")
	private String taskName;

	@SerializedName("task_status")
	private String taskStatus;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("assign_id")
	private String assignId;

	@SerializedName("task_date_end")
	private String taskDateEnd;

	@SerializedName("update_at")
	private Object updateAt;

	@SerializedName("task_date_start")
	private String taskDateStart;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("task_id")
	private String taskId;

	@SerializedName("task_admin_id")
	private String taskAdminId;

	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	public String getTaskName(){
		return taskName;
	}

	public void setTaskStatus(String taskStatus){
		this.taskStatus = taskStatus;
	}

	public String getTaskStatus(){
		return taskStatus;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setAssignId(String assignId){
		this.assignId = assignId;
	}

	public String getAssignId(){
		return assignId;
	}

	public void setTaskDateEnd(String taskDateEnd){
		this.taskDateEnd = taskDateEnd;
	}

	public String getTaskDateEnd(){
		return taskDateEnd;
	}

	public void setUpdateAt(Object updateAt){
		this.updateAt = updateAt;
	}

	public Object getUpdateAt(){
		return updateAt;
	}

	public void setTaskDateStart(String taskDateStart){
		this.taskDateStart = taskDateStart;
	}

	public String getTaskDateStart(){
		return taskDateStart;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	public String getTaskId(){
		return taskId;
	}

	public void setTaskAdminId(String taskAdminId){
		this.taskAdminId = taskAdminId;
	}

	public String getTaskAdminId(){
		return taskAdminId;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"task_name = '" + taskName + '\'' + 
			",task_status = '" + taskStatus + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",assign_id = '" + assignId + '\'' + 
			",task_date_end = '" + taskDateEnd + '\'' + 
			",update_at = '" + updateAt + '\'' + 
			",task_date_start = '" + taskDateStart + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",task_id = '" + taskId + '\'' + 
			",task_admin_id = '" + taskAdminId + '\'' + 
			"}";
		}
}