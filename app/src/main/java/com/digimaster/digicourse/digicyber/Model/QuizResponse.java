package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuizResponse{

	@SerializedName("assessment_quiz")
	private List<AssessmentQuizItem> assessmentQuiz;

	public void setAssessmentQuiz(List<AssessmentQuizItem> assessmentQuiz){
		this.assessmentQuiz = assessmentQuiz;
	}

	public List<AssessmentQuizItem> getAssessmentQuiz(){
		return assessmentQuiz;
	}

	@Override
 	public String toString(){
		return 
			"QuizResponse{" + 
			"assessment_quiz = '" + assessmentQuiz + '\'' + 
			"}";
		}
}