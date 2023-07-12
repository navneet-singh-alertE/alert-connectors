package com.alnt.access.certification.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IUserCertificationInformation extends Serializable {
	public String getUserId();

	public void setUserId(String userId);

	public List<ICertificationInformation> getCertificationInformation();

	public void setCertificationInformation(
			List<ICertificationInformation> certificationInformations);
	
	public Map<String,Object> getUserDetails();
	public void setUserDetails( Map<String, Object>  userDetails);
	
}
