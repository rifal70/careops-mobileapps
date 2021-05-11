package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Faqcategoryresponse{

    @SerializedName("faq_cat:")
    private List<FaqCatItem> faqCat;

    public void setFaqCat(List<FaqCatItem> faqCat){
        this.faqCat = faqCat;
    }

    public List<FaqCatItem> getFaqCat(){
        return faqCat;
    }

    @Override
    public String toString(){
        return
                "Faqcategoryresponse{" +
                        "faq_cat: = '" + faqCat + '\'' +
                        "}";
    }
}