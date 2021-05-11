package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskAssResponse{

	@SerializedName("request")
	private Request request;

	@SerializedName("code")
	private int code;

	@SerializedName("task_ass")
	private List<TaskAssItem> taskAss;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setRequest(Request request){
		this.request = request;
	}

	public Request getRequest(){
		return request;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setTaskAss(List<TaskAssItem> taskAss){
		this.taskAss = taskAss;
	}

	public List<TaskAssItem> getTaskAss(){
		return taskAss;
	}

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"TaskAssResponse{" + 
			"request = '" + request + '\'' + 
			",code = '" + code + '\'' + 
			",task_ass = '" + taskAss + '\'' + 
			",success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}