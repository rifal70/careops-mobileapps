package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CourseListModuleResponse{

    @SerializedName("course_list_module:")
    private List<CourseListModuleItem> courseListModule;

    public void setCourseListModule(List<CourseListModuleItem> courseListModule){
        this.courseListModule = courseListModule;
    }

    public List<CourseListModuleItem> getCourseListModule(){
        return courseListModule;
    }

    @Override
    public String toString(){
        return
                "ListModulelib{" +
                        "course_list_module: = '" + courseListModule + '\'' +
                        "}";
    }
}