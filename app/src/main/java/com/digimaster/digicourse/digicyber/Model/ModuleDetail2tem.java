package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ModuleDetail2tem{

    @SerializedName("module_description")
    private String moduleDescription;

    @SerializedName("module_id")
    private String moduleId;

    public void setModuleDescription(String moduleDescription){
        this.moduleDescription = moduleDescription;
    }

    public String getModuleDescription(){
        return moduleDescription;
    }

    public void setModuleId(String moduleId){
        this.moduleId = moduleId;
    }

    public String getModuleId(){
        return moduleId;
    }

    @Override
    public String toString(){
        return
                "ModuleDetailItem{" +
                        "module_description = '" + moduleDescription + '\'' +
                        ",module_id = '" + moduleId + '\'' +
                        "}";
    }
}

