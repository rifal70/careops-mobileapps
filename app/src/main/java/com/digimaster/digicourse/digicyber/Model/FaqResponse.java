package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FaqResponse{

	@SerializedName("faq:")
	private List<FaqItem> faq;

	public void setFaq(List<FaqItem> faq){
		this.faq = faq;
	}

	public List<FaqItem> getFaq(){
		return faq;
	}

	@Override
	public String toString(){
		return
				"FaqResponse{" +
						"faq: = '" + faq + '\'' +
						"}";
	}
}