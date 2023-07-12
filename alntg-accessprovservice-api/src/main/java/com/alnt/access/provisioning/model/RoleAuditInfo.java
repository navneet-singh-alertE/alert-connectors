package com.alnt.access.provisioning.model;

import java.util.Date;

public class RoleAuditInfo implements IRoleAuditInfo {
	
	private String roleName,action;
	private Date validFrom,validTo;
	private String enterpriseRoleName;
	private Boolean delayedForFutureDate = Boolean.FALSE;
	private String statusCode, message;
	private boolean provisioningPending;
	private String referenceId;
	
	public String getAction() {		
		return action;
	}

	public String getRoleName() {		
		return roleName;
	}

	public Date getValidFrom() {		
		return validFrom;
	}

	public Date getValidTo() {		
		return validTo;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;

	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public String getEnterpriseRoleName() {
		return enterpriseRoleName;
	}

	public void setEnterpriseRoleName(String enterpriseRoleName) {
		this.enterpriseRoleName = enterpriseRoleName;
	}

	public Boolean isDelayedForFutureDate() {
		return delayedForFutureDate;
	}

	public void setDelayedForFutureDate(Boolean delayedForFutureDate) {
		this.delayedForFutureDate = delayedForFutureDate;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public  boolean isProvisioningPending() {
		return provisioningPending;
	}
	public  void setProvisioningPending(boolean isProvisioningPending) {
		this.provisioningPending = isProvisioningPending;
	}
	public  String getReferenceId() {
		return referenceId;
	}
	public  void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	
	}
	
	
}
