package com.alnt.access.provisioning.model;

public interface IProvisioningAttributes {

	public String getAttributeName();

	public void setAttributeName(String attributeName);

	public boolean isMandatory();

	public void setMandatory(boolean isMandatory);

	public String getType();

	public void setType(String type);
	
	public void setAttributeDesc(String description);
	
	public String getAttributeDesc();

}