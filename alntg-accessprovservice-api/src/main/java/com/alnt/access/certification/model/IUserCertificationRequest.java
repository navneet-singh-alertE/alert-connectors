package com.alnt.access.certification.model;

import java.util.List;
import java.util.Map;

public interface IUserCertificationRequest {

	public abstract Map<String, String> getUserDetails();

	public abstract void setUserDetails(Map<String, String> userDetails);

	public abstract List<String> getCertificationNames();

	public abstract void setCertificationNames(List<String> certificationNames);

	public abstract Map<String, List> getCertMapping();

	public abstract void setCertMapping(Map<String, List> certMapping);

}