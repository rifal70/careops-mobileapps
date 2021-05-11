package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CertResponse{

	@SerializedName("cert")
	private List<CertItem> cert;

	public void setCert(List<CertItem> cert){
		this.cert = cert;
	}

	public List<CertItem> getCert(){
		return cert;
	}

	@Override
 	public String toString(){
		return 
			"CertResponse{" + 
			"cert = '" + cert + '\'' + 
			"}";
		}
}