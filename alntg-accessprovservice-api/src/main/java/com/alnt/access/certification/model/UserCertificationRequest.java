package com.alnt.access.certification.model;

import java.util.List;
import java.util.Map;

public class UserCertificationRequest implements IUserCertificationRequest {

	private Map<String, String> userDetails;

	private List<String> certificationNames;
	/**
	 * key = external attr, value = Alertattribute
	 */
	private Map<String, List> certMapping;

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#getUserDetails()
	 */
	
	public Map<String, String> getUserDetails() {
		return userDetails;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#setUserDetails(java.util.Map)
	 */
	
	public void setUserDetails(Map<String, String> userDetails) {
		this.userDetails = userDetails;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#getCertificationNames()
	 */
	
	public List<String> getCertificationNames() {
		return certificationNames;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#setCertificationNames(java.util.List)
	 */
	
	public void setCertificationNames(List<String> certificationNames) {
		this.certificationNames = certificationNames;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#getCertMapping()
	 */
	
	public Map<String, List> getCertMapping() {
		return certMapping;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.certification.model.IUserCertificationRequest#setCertMapping(java.util.Map)
	 */
	
	public void setCertMapping(Map<String, List> certMapping) {
		this.certMapping = certMapping;
	}

	
	public String toString() {
		return "UserCertificationRequest [userDetails=" + userDetails
				+ ", certificationNames=" + certificationNames
				+ ", certMapping=" + certMapping + "]";
	}

}
