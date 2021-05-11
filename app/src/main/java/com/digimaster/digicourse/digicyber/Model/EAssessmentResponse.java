package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EAssessmentResponse{

	@SerializedName("assessment")
	private List<AssessmentItem> assessment;

	public void setAssessment(List<AssessmentItem> assessment){
		this.assessment = assessment;
	}

	public List<AssessmentItem> getAssessment(){
		return assessment;
	}

	@Override
 	public String toString(){
		return 
			"EAssessmentResponse{" + 
			"assessment = '" + assessment + '\'' + 
			"}";
		}
}