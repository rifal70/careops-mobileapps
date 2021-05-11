package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class CourseTitleItem{

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("institut_name")
    private String institutName;

    @SerializedName("course_name")
    private String courseName;

    @SerializedName("Total_Module")
    private String totalModule;

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public String getCourseId(){
        return courseId;
    }

    public void setInstitutName(String institutName){
        this.institutName = institutName;
    }

    public String getInstitutName(){
        return institutName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public String getCourseName(){
        return courseName;
    }

    public void setTotalModule(String totalModule){
        this.totalModule = totalModule;
    }

    public String getTotalModule(){
        return totalModule;
    }

    @Override
    public String toString(){
        return
                "CourseTitleItem{" +
                        "course_id = '" + courseId + '\'' +
                        ",institut_name = '" + institutName + '\'' +
                        ",course_name = '" + courseName + '\'' +
                        ",total_Module = '" + totalModule + '\'' +
                        "}";
    }
}