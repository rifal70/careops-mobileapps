package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndoResponse{

	@SerializedName("kurikulum_indo")
	private List<KurikulumIndoItem> kurikulumIndo;

	public void setKurikulumIndo(List<KurikulumIndoItem> kurikulumIndo){
		this.kurikulumIndo = kurikulumIndo;
	}

	public List<KurikulumIndoItem> getKurikulumIndo(){
		return kurikulumIndo;
	}

	@Override
 	public String toString(){
		return 
			"IndoResponse{" + 
			"kurikulum_indo = '" + kurikulumIndo + '\'' + 
			"}";
		}
}