package com.alnt.access.certification.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserCertificationInformation implements
		IUserCertificationInformation {

	private static final long serialVersionUID = -6902552495336561499L;

	private String userId;
	private Map<String, Object> userDetails;

	private List<ICertificationInformation> certificationInformation = new ArrayList<ICertificationInformation>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<ICertificationInformation> getCertificationInformation() {
		return certificationInformation;
	}

	public void setCertificationInformation(
			List<ICertificationInformation> certificationInformation) {
		this.certificationInformation = certificationInformation;
	}
	
	public void setUserDetails(Map<String, Object> userDetails) {
		this.userDetails = userDetails;
	}

	public Map<String, Object> getUserDetails() {
		// TODO Auto-generated method stub
		return userDetails;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\" UserCertification [certificationInformation=");
		builder.append(certificationInformation);
		builder.append("] \"");
		return builder.toString();
	}
	
	
}
