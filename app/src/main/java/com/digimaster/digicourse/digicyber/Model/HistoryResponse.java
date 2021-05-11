package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse{

	@SerializedName("history_request")
	private List<HistoryRequestItem> historyRequest;

	public void setHistoryRequest(List<HistoryRequestItem> historyRequest){
		this.historyRequest = historyRequest;
	}

	public List<HistoryRequestItem> getHistoryRequest(){
		return historyRequest;
	}

	@Override
 	public String toString(){
		return 
			"HistoryResponse{" + 
			"history_request = '" + historyRequest + '\'' + 
			"}";
		}
}