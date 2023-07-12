package com.alnt.access.certification.model;

import java.util.Date;
import java.util.Map;

public class UserBGCheckRequest {
	
	String userId;
	String firstName;
	String lastName;
	Date dateOfBirth;
	Map<String,Object> userDetails;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirsName() {
		return firstName;
	}

	public void setFirsName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Map<String, Object> getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(Map<String, Object> userDetails) {
		this.userDetails = userDetails;
	}

}
