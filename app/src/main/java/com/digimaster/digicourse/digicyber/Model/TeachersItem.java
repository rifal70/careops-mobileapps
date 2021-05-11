package com.digimaster.digicourse.digicyber.Model;

import com.google.gson.annotations.SerializedName;

public class TeachersItem{

	@SerializedName("address")
	private String address;

	@SerializedName("education")
	private String education;

	@SerializedName("pengalamanTeach")
	private String pengalamanTeach;

	@SerializedName("phone")
	private String phone;

	@SerializedName("teacher_id")
	private String teacherId;

	@SerializedName("pic")
	private String pic;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("priceTeacher")
	private String priceTeacher;

	@SerializedName("username")
	private String username;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setEducation(String education){
		this.education = education;
	}

	public String getEducation(){
		return education;
	}

	public void setPengalamanTeach(String pengalamanTeach){
		this.pengalamanTeach = pengalamanTeach;
	}

	public String getPengalamanTeach(){
		return pengalamanTeach;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setTeacherId(String teacherId){
		this.teacherId = teacherId;
	}

	public String getTeacherId(){
		return teacherId;
	}

	public void setPic(String pic){
		this.pic = pic;
	}

	public String getPic(){
		return pic;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setPriceTeacher(String priceTeacher){
		this.priceTeacher = priceTeacher;
	}

	public String getPriceTeacher(){
		return priceTeacher;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"TeachersItem{" + 
			"address = '" + address + '\'' + 
			",education = '" + education + '\'' + 
			",pengalamanTeach = '" + pengalamanTeach + '\'' + 
			",phone = '" + phone + '\'' + 
			",teacher_id = '" + teacherId + '\'' + 
			",pic = '" + pic + '\'' + 
			",first_name = '" + firstName + '\'' + 
			",priceTeacher = '" + priceTeacher + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}