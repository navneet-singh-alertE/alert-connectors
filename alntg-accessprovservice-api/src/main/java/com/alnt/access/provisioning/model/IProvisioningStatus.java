package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public interface IProvisioningStatus{	
	public Long getProvStatus();
	public void setProvStatus(Long provStatus);
	public  String getErrorCode();
	public  void setErrorCode(String errorCode);
	public  String getMessageDesc();
	public  void setMessageDesc(String messageDesc);
	public  Map getParameters();
	public  void setParameters(Map parameters);
}
