package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisteredEventResponse{

	@SerializedName("registered_event")
	private List<RegisteredEventItem> registeredEvent;

	public void setRegisteredEvent(List<RegisteredEventItem> registeredEvent){
		this.registeredEvent = registeredEvent;
	}

	public List<RegisteredEventItem> getRegisteredEvent(){
		return registeredEvent;
	}

	@Override
 	public String toString(){
		return 
			"RegisteredEventResponse{" + 
			"registered_event = '" + registeredEvent + '\'' + 
			"}";
		}
}