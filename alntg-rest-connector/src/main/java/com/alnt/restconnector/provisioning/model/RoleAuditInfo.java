package com.alnt.restconnector.provisioning.model;

import java.io.Serializable;
import java.util.Date;

import com.alnt.access.provisioning.model.IRoleAuditInfo;

public class RoleAuditInfo implements IRoleAuditInfo, Serializable {

	String roleName,action;
	Date validFrom,validTo;
	String enterpriseRoleName;
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#getRoleName()
	 */
	public String getRoleName() {
		return roleName;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#setRoleName(java.lang.String)
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#getValidFrom()
	 */
	public Date getValidFrom() {
		return validFrom;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#setValidFrom(java.util.Date)
	 */
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#getValidTo()
	 */
	public Date getValidTo() {
		return validTo;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#setValidTo(java.util.Date)
	 */
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#getAction()
	 */
	public String getAction() {
		return action;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#setAction(java.lang.String)
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#getEnterpriseRoleName()
	 */
	public String getEnterpriseRoleName() {
		return enterpriseRoleName;
	}

	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IRoleAuditInfo#setEnterpriseRoleName(java.lang.String)
	 */
	public void setEnterpriseRoleName(String enterpriseRoleName) {
		this.enterpriseRoleName = enterpriseRoleName;
	}

}
