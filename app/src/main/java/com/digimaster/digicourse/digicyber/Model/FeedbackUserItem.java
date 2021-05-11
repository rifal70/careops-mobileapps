package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class FeedbackUserItem{

	@SerializedName("task_name")
	private String taskName;

	@SerializedName("feedback")
	private String feedback;

	@SerializedName("sub_task_id")
	private String subTaskId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("task_id")
	private String taskId;

	public void setTaskName(String taskName){
		this.taskName = taskName;
	}

	public String getTaskName(){
		return taskName;
	}

	public void setFeedback(String feedback){
		this.feedback = feedback;
	}

	public String getFeedback(){
		return feedback;
	}

	public void setSubTaskId(String subTaskId){
		this.subTaskId = subTaskId;
	}

	public String getSubTaskId(){
		return subTaskId;
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

	@Override
 	public String toString(){
		return 
			"FeedbackUserItem{" + 
			"task_name = '" + taskName + '\'' + 
			",feedback = '" + feedback + '\'' + 
			",sub_task_id = '" + subTaskId + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",task_id = '" + taskId + '\'' + 
			"}";
		}
}