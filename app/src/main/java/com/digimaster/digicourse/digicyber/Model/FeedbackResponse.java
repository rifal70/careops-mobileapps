package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedbackResponse{

	@SerializedName("feedback_user")
	private List<FeedbackUserItem> feedbackUser;

	public void setFeedbackUser(List<FeedbackUserItem> feedbackUser){
		this.feedbackUser = feedbackUser;
	}

	public List<FeedbackUserItem> getFeedbackUser(){
		return feedbackUser;
	}

	@Override
 	public String toString(){
		return 
			"FeedbackResponse{" + 
			"feedback_user = '" + feedbackUser + '\'' + 
			"}";
		}
}