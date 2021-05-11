package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResponse{

	@SerializedName("schedule")
	private List<ScheduleItem> schedule;

	public void setSchedule(List<ScheduleItem> schedule){
		this.schedule = schedule;
	}

	public List<ScheduleItem> getSchedule(){
		return schedule;
	}

	@Override
 	public String toString(){
		return 
			"ScheduleResponse{" + 
			"schedule = '" + schedule + '\'' + 
			"}";
		}
}