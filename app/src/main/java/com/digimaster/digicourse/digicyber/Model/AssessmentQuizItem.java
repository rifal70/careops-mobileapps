package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class AssessmentQuizItem{
	@SerializedName("action_material_pdf")
	private String pdf;

	@SerializedName("action_material_description")
	private String vidDesc;

	@SerializedName("action_name")
	private String vidTit;

	@SerializedName("quiz_audio")
	private String quizAudio;

	@SerializedName("question")
	private String question;

	@SerializedName("quiz_type")
	private String quizType;

	@SerializedName("pil1")
	private String pil1;

	@SerializedName("finished")
	private String finished;

	@SerializedName("pil2")
	private String pil2;

	@SerializedName("gf_status")
	private String gfStatus;

	@SerializedName("quiz_image")
	private String quizImage;

	@SerializedName("content")
	private String content;

	@SerializedName("material_type")
	private String materialType;

	@SerializedName("file")
	private String file;

	@SerializedName("pil3")
	private String pil3;

	@SerializedName("answer")
	private String answer;

	@SerializedName("action_id")
	private String actionId;

	@SerializedName("pil4")
	private String pil4;

	@SerializedName("topic_id")
	private String topicId;

	@SerializedName("content_image")
	private String contentImage;

	@SerializedName("category")
	private String category;

	@SerializedName("pil5")
	private String pil5;


	public void setVidDesc(String vidDesc){
		this.vidDesc = vidDesc;
	}

	public String getVidDesc(){
		return vidDesc;
	}

	public void setVidTit(String vidTit){
		this.vidTit = vidTit;
	}

	public String getVidTit(){
		return vidTit;
	}

	public void setQuizAudio(String quizAudio){
		this.quizAudio = quizAudio;
	}

	public String getQuizAudio(){
		return quizAudio;
	}

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setQuizType(String quizType){
		this.quizType = quizType;
	}

	public String getQuizType(){
		return quizType;
	}

	public void setPil1(String pil1){
		this.pil1 = pil1;
	}

	public String getPil1(){
		return pil1;
	}

	public void setFinished(String finished){
		this.finished = finished;
	}

	public String getFinished(){
		return finished;
	}

	public void setPil2(String pil2){
		this.pil2 = pil2;
	}

	public String getPil2(){
		return pil2;
	}

	public void setGfStatus(String gfStatus){
		this.gfStatus = gfStatus;
	}

	public String getGfStatus(){
		return gfStatus;
	}

	public void setQuizImage(String quizImage){
		this.quizImage = quizImage;
	}

	public String getQuizImage(){
		return quizImage;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setMaterialType(String materialType){
		this.materialType = materialType;
	}

	public String getMaterialType(){
		return materialType;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setPil3(String pil3){
		this.pil3 = pil3;
	}

	public String getPil3(){
		return pil3;
	}

	public void setAnswer(String answer){
		this.answer = answer;
	}

	public String getAnswer(){
		return answer;
	}

	public void setActionId(String actionId){
		this.actionId = actionId;
	}

	public String getActionId(){
		return actionId;
	}

	public void setPil4(String pil4){
		this.pil4 = pil4;
	}

	public String getPil4(){
		return pil4;
	}

	public void setTopicId(String topicId){
		this.topicId = topicId;
	}

	public String getTopicId(){
		return topicId;
	}

	public void setContentImage(String contentImage){
		this.contentImage = contentImage;
	}

	public String getContentImage(){
		return contentImage;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setPil5(String pil5){
		this.pil5 = pil5;
	}

	public String getPil5(){
		return pil5;
	}

	public void setPdf(String pdf){
		this.pdf = pdf;
	}

	public String getPdf(){
		return pdf;
	}

	@Override
 	public String toString(){
				return
					"AssessmentQuizItem{" +
							"action_material_pdf = '" + pdf + '\'' +
							"quiz_audio = '" + quizAudio + '\'' +
							",question = '" + question + '\'' +
							",quiz_type = '" + quizType + '\'' +
							",pil1 = '" + pil1 + '\'' +
							",finished = '" + finished + '\'' +
							",pil2 = '" + pil2 + '\'' +
							",gf_status = '" + gfStatus + '\'' +
							",quiz_image = '" + quizImage + '\'' +
							",content = '" + content + '\'' +
							",material_type = '" + materialType + '\'' +
							",file = '" + file + '\'' +
							",pil3 = '" + pil3 + '\'' +
							",answer = '" + answer + '\'' +
							",action_id = '" + actionId + '\'' +
							",pil4 = '" + pil4 + '\'' +
							",topic_id = '" + topicId + '\'' +
							",content_image = '" + contentImage + '\'' +
							",category = '" + category + '\'' +
							",pil5 = '" + pil5 + '\'' +
							",action_name = '" + vidTit + '\'' +
							",action_material_content = '" + vidDesc + '\'' +
					"}";
		}
}