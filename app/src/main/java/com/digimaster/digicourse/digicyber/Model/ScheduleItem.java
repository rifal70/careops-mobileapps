package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class ScheduleItem{

	@SerializedName("end_date")
	private String endDate;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("schedule_id")
	private String scheduleId;

	@SerializedName("start_date")
	private String startDate;

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return endDate;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setScheduleId(String scheduleId){
		this.scheduleId = scheduleId;
	}

	public String getScheduleId(){
		return scheduleId;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return startDate;
	}

	@Override
 	public String toString(){
		return 
			"ScheduleItem{" + 
			"end_date = '" + endDate + '\'' + 
			",start_time = '" + startTime + '\'' + 
			",end_time = '" + endTime + '\'' + 
			",schedule_id = '" + scheduleId + '\'' + 
			",start_date = '" + startDate + '\'' + 
			"}";
		}
}