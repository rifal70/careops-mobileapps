package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InterResponse{

	@SerializedName("kurikulum_inter")
	private List<KurikulumInterItem> kurikulumInter;

	public void setKurikulumInter(List<KurikulumInterItem> kurikulumInter){
		this.kurikulumInter = kurikulumInter;
	}

	public List<KurikulumInterItem> getKurikulumInter(){
		return kurikulumInter;
	}

	@Override
 	public String toString(){
		return 
			"InterResponse{" + 
			"kurikulum_inter = '" + kurikulumInter + '\'' + 
			"}";
		}
}