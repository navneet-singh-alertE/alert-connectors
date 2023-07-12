package com.alnt.access.provisioning.model;

import java.util.List;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public interface IProvisioningResult{
	public boolean isUserCreated();
	public void setUserCreated(boolean userCreated);
	public String getPassword();
	public void setPassword(String password);
	public String getGeneratedEmail();
	public void setGeneratedEmail(String generatedEmail);
	public String getProvAction() ;
	public void setProvAction(String provAction);
	public List getRoleList();
	public void setRoleList(List roleList);
	public String getMsgCode();
	public void setMsgCode(String msgCode);
	public String getMsgDesc();
	public void setMsgDesc(String msgDesc);
	public String getUserValidity();	
	public void setUserValidity(String userValidity);	
	public boolean isProvFailed();
	public void setProvFailed(boolean provFailed);	
	public String getValidFrom();
	public void setValidFrom(String validFrom);	
	public boolean isWarningsExists();
	public void setWarningsExists(boolean warningsExists);
	public List<IRoleInformation> getInvalidRoles();
	public void setInvalidRoles(List<IRoleInformation> invalidRoles);
	public String getUserId();
	public void setUserId(String userId);
	public boolean isAsynchronous();
	public void setAsynchronous(boolean isAsynchronous);
	public IBadgeInformation getBadgeInformation();
	public void setBadgeInformation(IBadgeInformation badgeDetails);
	public default void setSourceId(String sourceId) {};
	public default String getSourceId() {
		return "";
	};
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
	
	public default String getErrorMsgCode() {
		return "";
	}
	public default void setErrorMsgCode(String errorMsgCode) {
	
	}
	
	
}
