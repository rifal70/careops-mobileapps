package com.digimaster.digicourse.digicyber.Model;
import com.google.gson.annotations.SerializedName;

public class FaqCatItem{

    @SerializedName("faq_category")
    private String faqCategory;

    public void setFaqCategory(String faqCategory){
        this.faqCategory = faqCategory;
    }

    public String getFaqCategory(){
        return faqCategory;
    }

    @Override
    public String toString(){
        return
                "FaqCatItem{" +
                        "faq_category = '" + faqCategory + '\'' +
                        "}";
    }
}