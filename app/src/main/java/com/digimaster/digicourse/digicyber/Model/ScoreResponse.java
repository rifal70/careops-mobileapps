package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScoreResponse{

	@SerializedName("score_user")
	private List<ScoreUserItem> scoreUser;

	@SerializedName("task_unfinish")
	private String taskUnfinish;

	@SerializedName("task_finish")
	private String taskFinish;

	public void setScoreUser(List<ScoreUserItem> scoreUser){
		this.scoreUser = scoreUser;
	}

	public List<ScoreUserItem> getScoreUser(){
		return scoreUser;
	}

	public void setTaskUnfinish(String taskUnfinish){
		this.taskUnfinish = taskUnfinish;
	}

	public String getTaskUnfinish(){
		return taskUnfinish;
	}

	public void setTaskFinish(String taskFinish){
		this.taskFinish = taskFinish;
	}

	public String getTaskFinish(){
		return taskFinish;
	}

	@Override
 	public String toString(){
		return 
			"ScoreResponse{" + 
			"score_user = '" + scoreUser + '\'' + 
			",task_unfinish = '" + taskUnfinish + '\'' + 
			",task_finish = '" + taskFinish + '\'' + 
			"}";
		}
}