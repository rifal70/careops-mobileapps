package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FinishedTaskResponse{

	@SerializedName("finished_task")
	private List<FinishedTaskItem> finishedTask;

	public void setFinishedTask(List<FinishedTaskItem> finishedTask){
		this.finishedTask = finishedTask;
	}

	public List<FinishedTaskItem> getFinishedTask(){
		return finishedTask;
	}

	@Override
 	public String toString(){
		return 
			"FinishedTaskResponse{" + 
			"finished_task = '" + finishedTask + '\'' + 
			"}";
		}
}