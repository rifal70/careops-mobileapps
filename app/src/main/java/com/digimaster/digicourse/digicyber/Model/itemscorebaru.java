package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class itemscorebaru {

    @SerializedName("library_date")
    private String libraryDate;

    @SerializedName("assign_code")
    private String assignCode;

    @SerializedName("assign_id")
    private String assignId;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("task_id")
    private String taskId;

    @SerializedName("assign_group")
    private String assignGroup;

    @SerializedName("library_description")
    private String libraryDescription;

    @SerializedName("assign_status")
    private String assignStatus;

    @SerializedName("updated_at")
    private Object updatedAt;

    @SerializedName("library_id")
    private String libraryId;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("library_image")
    private String libraryImage;

    @SerializedName("library_status")
    private String libraryStatus;

    @SerializedName("library_name")
    private String libraryName;

    @SerializedName("assign_group_member")
    private String assignGroupMember;

    public void setLibraryDate(String libraryDate){
        this.libraryDate = libraryDate;
    }

    public String getLibraryDate(){
        return libraryDate;
    }

    public void setAssignCode(String assignCode){
        this.assignCode = assignCode;
    }

    public String getAssignCode(){
        return assignCode;
    }

    public void setAssignId(String assignId){
        this.assignId = assignId;
    }

    public String getAssignId(){
        return assignId;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setTaskId(String taskId){
        this.taskId = taskId;
    }

    public String getTaskId(){
        return taskId;
    }

    public void setAssignGroup(String assignGroup){
        this.assignGroup = assignGroup;
    }

    public String getAssignGroup(){
        return assignGroup;
    }

    public void setLibraryDescription(String libraryDescription){
        this.libraryDescription = libraryDescription;
    }

    public String getLibraryDescription(){
        return libraryDescription;
    }

    public void setAssignStatus(String assignStatus){
        this.assignStatus = assignStatus;
    }

    public String getAssignStatus(){
        return assignStatus;
    }

    public void setUpdatedAt(Object updatedAt){
        this.updatedAt = updatedAt;
    }

    public Object getUpdatedAt(){
        return updatedAt;
    }

    public void setLibraryId(String libraryId){
        this.libraryId = libraryId;
    }

    public String getLibraryId(){
        return libraryId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setLibraryImage(String libraryImage){
        this.libraryImage = libraryImage;
    }

    public String getLibraryImage(){
        return libraryImage;
    }

    public void setLibraryStatus(String libraryStatus){
        this.libraryStatus = libraryStatus;
    }

    public String getLibraryStatus(){
        return libraryStatus;
    }

    public void setLibraryName(String libraryName){
        this.libraryName = libraryName;
    }

    public String getLibraryName(){
        return libraryName;
    }

    public void setAssignGroupMember(String assignGroupMember){
        this.assignGroupMember = assignGroupMember;
    }

    public String getAssignGroupMember(){
        return assignGroupMember;
    }

    @Override
    public String toString(){
        return
                "TaskLibItem{" +
                        "library_date = '" + libraryDate + '\'' +
                        ",assign_code = '" + assignCode + '\'' +
                        ",assign_id = '" + assignId + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",task_id = '" + taskId + '\'' +
                        ",assign_group = '" + assignGroup + '\'' +
                        ",library_description = '" + libraryDescription + '\'' +
                        ",assign_status = '" + assignStatus + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",library_id = '" + libraryId + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",library_image = '" + libraryImage + '\'' +
                        ",library_status = '" + libraryStatus + '\'' +
                        ",library_name = '" + libraryName + '\'' +
                        ",assign_group_member = '" + assignGroupMember + '\'' +
                        "}";
    }
}