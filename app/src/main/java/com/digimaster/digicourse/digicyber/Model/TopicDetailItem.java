package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class TopicDetailItem {

	@SerializedName("action_order")
	private String actionOrder;

	@SerializedName("module_id")
	private String moduleId;

	@SerializedName("topic_access")
	private String topicAccess;

	@SerializedName("action_id")
	private String actionId;

	@SerializedName("sakral")
	private String sakral;

	@SerializedName("action_tipe")
	private String actionTipe;

	@SerializedName("action_name")
	private String actionName;

	@SerializedName("topic_name")
	private String topicName;

	@SerializedName("action_material_tipe")
	private Object actionMaterialTipe;

	@SerializedName("finished")
	private String finished;

	@SerializedName("topic_id")
	private String topicId;

	public void setActionOrder(String actionOrder){
		this.actionOrder = actionOrder;
	}

	public String getActionOrder(){
		return actionOrder;
	}

	public void setModuleId(String moduleId){
		this.moduleId = moduleId;
	}

	public String getModuleId(){
		return moduleId;
	}

	public void setTopicAccess(String topicAccess){
		this.topicAccess = topicAccess;
	}

	public String getTopicAccess(){
		return topicAccess;
	}

	public void setActionId(String actionId){
		this.actionId = actionId;
	}

	public String getActionId(){
		return actionId;
	}

	public void setSakral(String sakral){
		this.sakral = sakral;
	}

	public String getSakral(){
		return sakral;
	}

	public void setActionTipe(String actionTipe){
		this.actionTipe = actionTipe;
	}

	public String getActionTipe(){
		return actionTipe;
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

	public void setActionMaterialTipe(Object actionMaterialTipe){
		this.actionMaterialTipe = actionMaterialTipe;
	}

	public Object getActionMaterialTipe(){
		return actionMaterialTipe;
	}

	public void setFinished(String finished){
		this.finished = finished;
	}

	public String getFinished(){
		return finished;
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
			"TopicDetailItem{" + 
			"action_order = '" + actionOrder + '\'' + 
			",module_id = '" + moduleId + '\'' + 
			",topic_access = '" + topicAccess + '\'' + 
			",action_id = '" + actionId + '\'' + 
			",sakral = '" + sakral + '\'' + 
			",action_tipe = '" + actionTipe + '\'' + 
			",action_name = '" + actionName + '\'' + 
			",topic_name = '" + topicName + '\'' + 
			",action_material_tipe = '" + actionMaterialTipe + '\'' + 
			",finished = '" + finished + '\'' + 
			",topic_id = '" + topicId + '\'' + 
			"}";
		}
}