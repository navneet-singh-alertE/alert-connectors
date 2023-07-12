package com.alnt.access.provisioning.model;

import java.util.Date;

public interface IRoleAuditInfo {

	public abstract String getRoleName();

	public abstract void setRoleName(String roleName);

	public abstract Date getValidFrom();

	public abstract void setValidFrom(Date validFrom);

	public abstract Date getValidTo();

	public abstract void setValidTo(Date validTo);

	public abstract String getAction();

	public abstract void setAction(String action);
	
	public default void setDelayedForFutureDate(Boolean futureDateFlag){
		
	}
	
	public default Boolean isDelayedForFutureDate(){
		return Boolean.FALSE;
	}
	
	public default String getStatusCode() {
		return "";
	}
	
	public default void setStatusCode(String statusCode) {
		
	}
	

	public default String getMessage() {
		return "";
	}

	public default void setMessage(String message) {
		
	}
	
	public default boolean isProvisioningPending() {
		return false;
	}
	
	public default void setProvisioningPending(boolean isProvisioningPending) {
	}
	
	public default String getReferenceId() {
		return "";
	}
	
	public default void setReferenceId(String referenceId) {
	
	}

}