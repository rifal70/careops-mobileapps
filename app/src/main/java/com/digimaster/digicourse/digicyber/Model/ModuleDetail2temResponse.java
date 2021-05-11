package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModuleDetail2temResponse{

    @SerializedName("module_detail:")
    private List<ModuleDetail2tem> moduleDetail;

    public void setModuleDetail(List<ModuleDetail2tem> moduleDetail){
        this.moduleDetail = moduleDetail;
    }

    public List<ModuleDetail2tem> getModuleDetail(){
        return moduleDetail;
    }

    @Override
    public String toString(){
        return
                "ModuleDetailResponse{" +
                        "module_detail: = '" + moduleDetail + '\'' +
                        "}";
    }
}