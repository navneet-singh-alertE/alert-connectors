package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public class SystemInformation implements Serializable,ISystemInformation {

	boolean provisioned=false;
	Date validFrom,validTo;
	boolean locked=false;
	private String systemUserId;
	List<IRoleInformation> userRoles = new ArrayList<IRoleInformation>();
	
	public SystemInformation(boolean provisioned)
	{
		this.provisioned=provisioned;
	}
	public SystemInformation() {
		
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#isProvisioned()
	 */
	public boolean isProvisioned() {
		return provisioned;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setProvisioned(boolean)
	 */
	public void setProvisioned(boolean provisioned) {
		this.provisioned = provisioned;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#getValidFrom()
	 */
	public Date getValidFrom() {
		return validFrom;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setValidFrom(java.util.Date)
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#getValidTo()
	 */
	public Date getValidTo() {
		return validTo;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setValidTo(java.util.Date)
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#getLocked()
	 */
	public boolean getLocked() {
		return locked;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setLocked(boolean)
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#getUserRoles()
	 */
	public List<IRoleInformation> getUserRoles() {
		return userRoles;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setUserRoles(java.util.List)
	 */
	public void setUserRoles(List<IRoleInformation> userRoles) {
		this.userRoles = userRoles;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#getSystemUserId()
	 */
	public String getSystemUserId() {
		return systemUserId;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.ISystemInformation#setSystemUserId(java.lang.String)
	 */
	public void setSystemUserId(String systemUserId) {
		this.systemUserId = systemUserId;
	}

}
