package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class AchievementItem{

	@SerializedName("course_name")
	private String courseName;

	@SerializedName("action_name")
	private String actionName;

	@SerializedName("topic_name")
	private String topicName;

	@SerializedName("nickname")
	private String nickname;

	@SerializedName("topic_id")
	private String topicId;

	public void setCourseName(String courseName){
		this.courseName = courseName;
	}

	public String getCourseName(){
		return courseName;
	}

	public void setActionName(String actionName){
		this.actionName = actionName;
	}

	public String getActionName(){
		return actionName;
	}

	public void setTopicName(String topicName){
		this.topicName = topicName;
	}

	public String getTopicName(){
		return topicName;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public String getNickname(){
		return nickname;
	}

	public void setTopicId(String topicId){
		this.topicId = topicId;
	}

	public String getTopicId(){
		return topicId;
	}

	@Override
 	public String toString(){
		return 
			"AchievementItem{" + 
			"course_name = '" + courseName + '\'' + 
			",action_name = '" + actionName + '\'' + 
			",topic_name = '" + topicName + '\'' + 
			",nickname = '" + nickname + '\'' + 
			",topic_id = '" + topicId + '\'' + 
			"}";
		}
}