package com.digimaster.digicourse.digicyber.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AchievementResponse{

	@SerializedName("achievement")
	private List<AchievementItem> achievement;

	public void setAchievement(List<AchievementItem> achievement){
		this.achievement = achievement;
	}

	public List<AchievementItem> getAchievement(){
		return achievement;
	}

	@Override
 	public String toString(){
		return 
			"AchievementResponse{" + 
			"achievement = '" + achievement + '\'' + 
			"}";
		}
}