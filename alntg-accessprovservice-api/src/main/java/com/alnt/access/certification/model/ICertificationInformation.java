package com.alnt.access.certification.model;

import java.util.Map;

/**
 * This class represents a Certification result (for a particular course type)
 * This class provides a facility if customers do not have a LMS system and they
 * want to have attestation or certification functionality.
 * 
 * 
 * 
 */

public interface ICertificationInformation {

	public String getCourseId();

	public void setCourseId(String courseId);

	public Map<String, Object> getCourseDetails();

	public void setCourseDetails(Map<String, Object> courseDetails);
}