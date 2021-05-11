package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class CourseListModuleItem{

    @SerializedName("course_id")
    private String courseId;

    @SerializedName("module_id")
    private String moduleId;

    @SerializedName("module_name")
    private String moduleName;

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public String getCourseId(){
        return courseId;
    }

    public void setModuleId(String moduleId){
        this.moduleId = moduleId;
    }

    public String getModuleId(){
        return moduleId;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }

    public String getModuleName(){
        return moduleName;
    }

    @Override
    public String toString(){
        return
                "CourseListModuleItem{" +
                        "course_id = '" + courseId + '\'' +
                        ",module_id = '" + moduleId + '\'' +
                        ",module_name = '" + moduleName + '\'' +
                        "}";
    }
}