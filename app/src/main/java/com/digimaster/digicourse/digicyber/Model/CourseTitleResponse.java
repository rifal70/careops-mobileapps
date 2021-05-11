package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CourseTitleResponse{

    @SerializedName("course_title:")
    private List<CourseTitleItem> courseTitle;

    public void setCourseTitle(List<CourseTitleItem> courseTitle){
        this.courseTitle = courseTitle;
    }

    public List<CourseTitleItem> getCourseTitle(){
        return courseTitle;
    }

    @Override
    public String toString(){
        return
                "CourseTitleResponse{" +
                        "course_title: = '" + courseTitle + '\'' +
                        "}";
    }
}