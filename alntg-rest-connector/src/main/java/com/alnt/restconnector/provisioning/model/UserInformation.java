package com.alnt.restconnector.provisioning.model;

import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IUserInformation;

public class UserInformation implements IUserInformation {

	private Map<String, Object> userDetails;
	private Map<String, Map<String, List<Map<String, Object>>>> entitlements;

	public Map<String, Object> getUserDetails() {
		// TODO Auto-generated method stub
		return userDetails;
	}

	public Map<String, Map<String, List<Map<String, Object>>>> getEntitlements() {
		// TODO Auto-generated method stub
		return entitlements;
	}

	public void setUserDetails(Map<String, Object> userDetails) {
		this.userDetails = userDetails;
	}

	public void setEntitlements(
			Map<String, Map<String, List<Map<String, Object>>>> entitlements) {
		this.entitlements = entitlements;
	}


}
