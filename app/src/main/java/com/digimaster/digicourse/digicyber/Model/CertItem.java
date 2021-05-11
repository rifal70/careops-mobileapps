package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class CertItem{

	@SerializedName("no")
	private String no;

	@SerializedName("cert_path")
	private String certPath;

	@SerializedName("certificate_name")
	private String certificateName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("title")
	private String title;

	public void setNo(String no){
		this.no = no;
	}

	public String getNo(){
		return no;
	}

	public void setCertPath(String certPath){
		this.certPath = certPath;
	}

	public String getCertPath(){
		return certPath;
	}

	public void setCertificateName(String certificateName){
		this.certificateName = certificateName;
	}

	public String getCertificateName(){
		return certificateName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"CertItem{" + 
			"no = '" + no + '\'' + 
			",cert_path = '" + certPath + '\'' + 
			",certificate_name = '" + certificateName + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}