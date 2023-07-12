package com.alnt.extractionconnector.common.model;

import java.io.Serializable;
import java.util.Map;

public interface ILocationInformation extends Serializable {

	public Map<String,Object> getLocationDetails();
	public void setLocationDetails( Map<String, Object>  locationDetails);
}
