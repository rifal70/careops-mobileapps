package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TopicResponse{

	@SerializedName("topic_detail")
	private List<TopicDetailItem> topicDetail;

	public void setTopicDetail(List<TopicDetailItem> topicDetail){
		this.topicDetail = topicDetail;
	}

	public List<TopicDetailItem> getTopicDetail(){
		return topicDetail;
	}

	@Override
 	public String toString(){
		return 
			"TopicResponse{" + 
			"topic_detail = '" + topicDetail + '\'' + 
			"}";
		}
}