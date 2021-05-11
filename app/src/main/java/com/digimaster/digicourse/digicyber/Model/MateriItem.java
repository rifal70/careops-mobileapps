package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class MateriItem{

	@SerializedName("sub_task_id")
	private String subTaskId;

	@SerializedName("sub_task_name")
	private String subTaskName;

	@SerializedName("task_id")
	private String taskId;

	public void setSubTaskId(String subTaskId){
		this.subTaskId = subTaskId;
	}

	public String getSubTaskId(){
		return subTaskId;
	}

	public void setSubTaskName(String subTaskName){
		this.subTaskName = subTaskName;
	}

	public String getSubTaskName(){
		return subTaskName;
	}

	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	public String getTaskId(){
		return taskId;
	}

	@Override
 	public String toString(){
		return 
			"MateriItem{" + 
			"sub_task_id = '" + subTaskId + '\'' + 
			",sub_task_name = '" + subTaskName + '\'' + 
			",task_id = '" + taskId + '\'' + 
			"}";
		}
}