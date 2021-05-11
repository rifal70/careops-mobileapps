package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskLibResponse{

	@SerializedName("request")
	private Request request;

	@SerializedName("code")
	private int code;

	@SerializedName("success")
	private boolean success;

	@SerializedName("task_lib")
	private List<TaskLibItem> taskLib;

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

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setTaskLib(List<TaskLibItem> taskLib){
		this.taskLib = taskLib;
	}

	public List<TaskLibItem> getTaskLib(){
		return taskLib;
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
				"TaskLibResponse{" +
						"request = '" + request + '\'' +
						",code = '" + code + '\'' +
						",success = '" + success + '\'' +
						",task_lib = '" + taskLib + '\'' +
						",message = '" + message + '\'' +
						"}";
	}
}