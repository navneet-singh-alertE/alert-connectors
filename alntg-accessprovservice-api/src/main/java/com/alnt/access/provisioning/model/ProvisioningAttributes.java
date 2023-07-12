package com.alnt.access.provisioning.model;


public class ProvisioningAttributes implements IProvisioningAttributes {


	String attributeName;
	boolean isMandatory;
	String type;
	String description;
	
	public ProvisioningAttributes(String attributeName,String description, boolean isMandatory,
			String type) {
		this.attributeName=attributeName;
		this.isMandatory=isMandatory;
		this.type = type;
		this.description=description;
	}
	
	public ProvisioningAttributes(String attributeName, boolean isMandatory) {
		this.attributeName=attributeName;
		this.isMandatory=isMandatory;
	}
	

	public String getAttributeName() {	
		return attributeName;
	}

	public String getType() {
		return type;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
		
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAttributeDesc(String description) {
		this.description=description;
		
	}


	public String getAttributeDesc() {
		// TODO Auto-generated method stub
		return description;
	}



}
