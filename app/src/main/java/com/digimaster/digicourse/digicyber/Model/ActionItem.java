package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ActionItem{

	@SerializedName("institut_id")
	private String institutId;

	@SerializedName("action_category")
	private String actionCategory;

	@SerializedName("admin_institut_id")
	private String adminInstitutId;

	@SerializedName("action_fifth_choice")
	private Object actionFifthChoice;

	@SerializedName("action_material_file")
	private Object actionMaterialFile;

	@SerializedName("action_answer")
	private Object actionAnswer;

	@SerializedName("action_quiz_audio")
	private Object actionQuizAudio;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("action_first_choice")
	private Object actionFirstChoice;

	@SerializedName("action_question")
	private Object actionQuestion;

	@SerializedName("action_quiz_type")
	private Object actionQuizType;

	@SerializedName("action_quiz_image")
	private Object actionQuizImage;

	@SerializedName("action_second_choice")
	private Object actionSecondChoice;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("action_id")
	private String actionId;

	@SerializedName("action_name")
	private String actionName;

	@SerializedName("action_material_content")
	private String actionMaterialContent;

	@SerializedName("action_material_type")
	private String actionMaterialType;

	@SerializedName("action_third_choice")
	private Object actionThirdChoice;

	@SerializedName("topic_id")
	private String topicId;

	@SerializedName("action_status")
	private String actionStatus;

	public void setInstitutId(String institutId){
		this.institutId = institutId;
	}

	public String getInstitutId(){
		return institutId;
	}

	public void setActionCategory(String actionCategory){
		this.actionCategory = actionCategory;
	}

	public String getActionCategory(){
		return actionCategory;
	}

	public void setAdminInstitutId(String adminInstitutId){
		this.adminInstitutId = adminInstitutId;
	}

	public String getAdminInstitutId(){
		return adminInstitutId;
	}

	public void setActionFifthChoice(Object actionFifthChoice){
		this.actionFifthChoice = actionFifthChoice;
	}

	public Object getActionFifthChoice(){
		return actionFifthChoice;
	}

	public void setActionMaterialFile(Object actionMaterialFile){
		this.actionMaterialFile = actionMaterialFile;
	}

	public Object getActionMaterialFile(){
		return actionMaterialFile;
	}

	public void setActionAnswer(Object actionAnswer){
		this.actionAnswer = actionAnswer;
	}

	public Object getActionAnswer(){
		return actionAnswer;
	}

	public void setActionQuizAudio(Object actionQuizAudio){
		this.actionQuizAudio = actionQuizAudio;
	}

	public Object getActionQuizAudio(){
		return actionQuizAudio;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setActionFirstChoice(Object actionFirstChoice){
		this.actionFirstChoice = actionFirstChoice;
	}

	public Object getActionFirstChoice(){
		return actionFirstChoice;
	}

	public void setActionQuestion(Object actionQuestion){
		this.actionQuestion = actionQuestion;
	}

	public Object getActionQuestion(){
		return actionQuestion;
	}

	public void setActionQuizType(Object actionQuizType){
		this.actionQuizType = actionQuizType;
	}

	public Object getActionQuizType(){
		return actionQuizType;
	}

	public void setActionQuizImage(Object actionQuizImage){
		this.actionQuizImage = actionQuizImage;
	}

	public Object getActionQuizImage(){
		return actionQuizImage;
	}

	public void setActionSecondChoice(Object actionSecondChoice){
		this.actionSecondChoice = actionSecondChoice;
	}

	public Object getActionSecondChoice(){
		return actionSecondChoice;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setActionId(String actionId){
		this.actionId = actionId;
	}

	public String getActionId(){
		return actionId;
	}

	public void setActionName(String actionName){
		this.actionName = actionName;
	}

	public String getActionName(){
		return actionName;
	}

	public void setActionMaterialContent(String actionMaterialContent){
		this.actionMaterialContent = actionMaterialContent;
	}

	public String getActionMaterialContent(){
		return actionMaterialContent;
	}

	public void setActionMaterialType(String actionMaterialType){
		this.actionMaterialType = actionMaterialType;
	}

	public String getActionMaterialType(){
		return actionMaterialType;
	}

	public void setActionThirdChoice(Object actionThirdChoice){
		this.actionThirdChoice = actionThirdChoice;
	}

	public Object getActionThirdChoice(){
		return actionThirdChoice;
	}

	public void setTopicId(String topicId){
		this.topicId = topicId;
	}

	public String getTopicId(){
		return topicId;
	}

	public void setActionStatus(String actionStatus){
		this.actionStatus = actionStatus;
	}

	public String getActionStatus(){
		return actionStatus;
	}

	@Override
 	public String toString(){
		return 
			"ActionItem{" + 
			"institut_id = '" + institutId + '\'' + 
			",action_category = '" + actionCategory + '\'' + 
			",admin_institut_id = '" + adminInstitutId + '\'' + 
			",action_fifth_choice = '" + actionFifthChoice + '\'' + 
			",action_material_file = '" + actionMaterialFile + '\'' + 
			",action_answer = '" + actionAnswer + '\'' + 
			",action_quiz_audio = '" + actionQuizAudio + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",action_first_choice = '" + actionFirstChoice + '\'' + 
			",action_question = '" + actionQuestion + '\'' + 
			",action_quiz_type = '" + actionQuizType + '\'' + 
			",action_quiz_image = '" + actionQuizImage + '\'' + 
			",action_second_choice = '" + actionSecondChoice + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",action_id = '" + actionId + '\'' + 
			",action_name = '" + actionName + '\'' + 
			",action_material_content = '" + actionMaterialContent + '\'' + 
			",action_material_type = '" + actionMaterialType + '\'' + 
			",action_third_choice = '" + actionThirdChoice + '\'' + 
			",topic_id = '" + topicId + '\'' + 
			",action_status = '" + actionStatus + '\'' + 
			"}";
		}
}