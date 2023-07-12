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

public class CertificationInformation implements ICertificationInformation {
	
	private String courseId;
	private Map<String,Object> courseDetails; 
   
    public Map<String, Object> getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(Map<String, Object> courseDetails) {
		this.courseDetails = courseDetails;
	}

	public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(courseId);
		builder.append(courseDetails);
		return builder.toString();
	}

    
	
}
