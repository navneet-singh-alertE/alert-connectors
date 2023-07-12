package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public interface ISystemInformation{	
	public boolean isProvisioned();
	public void setProvisioned(boolean provisioned);
	public Date getValidFrom();
	public void setValidFrom(Date validFrom) ;
	public Date getValidTo();
	public void setValidTo(Date validTo);
	public boolean getLocked();
	public void setLocked(boolean locked);
	public List<IRoleInformation> getUserRoles();
	public void setUserRoles(List<IRoleInformation> userRoles);
	public String getSystemUserId();
	public void setSystemUserId(String systemUserId);

}
