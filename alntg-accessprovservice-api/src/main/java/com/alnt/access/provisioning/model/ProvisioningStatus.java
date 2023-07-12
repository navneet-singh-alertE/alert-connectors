package com.alnt.access.provisioning.model;

import java.util.Map;

public class ProvisioningStatus implements  com.alnt.access.provisioning.model.IProvisioningStatus{	
	Long provStatus;
	String errorCode;
	String messageDesc;
	Map  parameters;
	public Long getProvStatus() {
		return provStatus;
	}
	public void setProvStatus(Long provStatus) {
		this.provStatus = provStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessageDesc() {
		return messageDesc;
	}
	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

}
