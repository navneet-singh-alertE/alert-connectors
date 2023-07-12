package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.Date;

public class SuperUserProvisionResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2567567480292853260L;

	private String superUserId;
	private String userId;
	//private SysConnector sysConnector;
	private String action;
	private Boolean successFlag;
	private String messageTxt;
	private Date ActiveFrom;
	private Date ActiveTo;
	
	public Date getActiveFrom() {
		return ActiveFrom;
	}
	public void setActiveFrom(Date activeFrom) {
		ActiveFrom = activeFrom;
	}
	public Date getActiveTo() {
		return ActiveTo;
	}
	public void setActiveTo(Date activeTo) {
		ActiveTo = activeTo;
	}
	public String getSuperUserId() {
		return superUserId;
	}
	public void setSuperUserId(String superUserId) {
		this.superUserId = superUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*public SysConnector getSysConnector() {
		return sysConnector;
	}
	public void setSysConnector(SysConnector sysConnector) {
		this.sysConnector = sysConnector;
	}*/
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Boolean getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(Boolean successFlag) {
		this.successFlag = successFlag;
	}
	public String getMessageTxt() {
		return messageTxt;
	}
	public void setMessageTxt(String messageTxt) {
		this.messageTxt = messageTxt;
	}
}
