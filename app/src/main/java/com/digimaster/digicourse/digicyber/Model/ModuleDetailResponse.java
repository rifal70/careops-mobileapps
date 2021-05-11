package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModuleDetailResponse{

    @SerializedName("module_detail:")
    private List<ModuleDetailItem> moduleDetail;

    public void setModuleDetail(List<ModuleDetailItem> moduleDetail){
        this.moduleDetail = moduleDetail;
    }

    public List<ModuleDetailItem> getModuleDetail(){
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