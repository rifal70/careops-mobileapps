package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class FaqItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("faq_category")
	private String faqCategory;

	@SerializedName("faq_answer")
	private String faqAnswer;

	@SerializedName("faq_status")
	private String faqStatus;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("faq_id")
	private String faqId;

	@SerializedName("faq_question")
	private String faqQuestion;

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setFaqCategory(String faqCategory){
		this.faqCategory = faqCategory;
	}

	public String getFaqCategory(){
		return faqCategory;
	}

	public void setFaqAnswer(String faqAnswer){
		this.faqAnswer = faqAnswer;
	}

	public String getFaqAnswer(){
		return faqAnswer;
	}

	public void setFaqStatus(String faqStatus){
		this.faqStatus = faqStatus;
	}

	public String getFaqStatus(){
		return faqStatus;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setFaqId(String faqId){
		this.faqId = faqId;
	}

	public String getFaqId(){
		return faqId;
	}

	public void setFaqQuestion(String faqQuestion){
		this.faqQuestion = faqQuestion;
	}

	public String getFaqQuestion(){
		return faqQuestion;
	}

	@Override
	public String toString(){
		return
				"FaqItem{" +
						"updated_at = '" + updatedAt + '\'' +
						",faq_category = '" + faqCategory + '\'' +
						",faq_answer = '" + faqAnswer + '\'' +
						",faq_status = '" + faqStatus + '\'' +
						",created_at = '" + createdAt + '\'' +
						",faq_id = '" + faqId + '\'' +
						",faq_question = '" + faqQuestion + '\'' +
						"}";
	}
}