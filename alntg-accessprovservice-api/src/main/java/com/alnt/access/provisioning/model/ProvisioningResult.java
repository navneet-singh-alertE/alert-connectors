package com.alnt.access.provisioning.model;

import java.util.ArrayList;
import java.util.List;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public class ProvisioningResult implements IProvisioningResult {

	boolean userCreated;
	String password;
	String generatedEmail;
	String provAction;
	List roleList = new ArrayList();
	List<IRoleInformation> invalidRoles = new ArrayList<IRoleInformation>();
	String msgCode;
	String msgDesc;
	String userValidity;
	boolean provFailed;
	String validFrom;
	boolean warningsExists = false;
	private String userId;
	private boolean isAsynchronous = false;
	private IBadgeInformation badgeInformation;
	private String sourceId;
	private boolean provisioningPending;
	private String referenceId;
	private String errorMsgCode;

	public boolean isAsynchronous() {
		return isAsynchronous;
	}
	public void setAsynchronous(boolean isAsynchronous) {
		this.isAsynchronous = isAsynchronous;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#isUserCreated()
	 */
	public boolean isUserCreated() {
		return userCreated;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setUserCreated(boolean)
	 */
	public void setUserCreated(boolean userCreated) {
		this.userCreated = userCreated;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getGeneratedEmail()
	 */
	public String getGeneratedEmail() {
		return generatedEmail;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setGeneratedEmail(java.lang.String)
	 */
	public void setGeneratedEmail(String generatedEmail) {
		this.generatedEmail = generatedEmail;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getProvAction()
	 */
	public String getProvAction() {
		return provAction;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setProvAction(java.lang.String)
	 */
	public void setProvAction(String provAction) {
		this.provAction = provAction;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getRoleList()
	 */
	public List getRoleList() {
		return roleList;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setRoleList(java.util.List)
	 */
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getMsgCode()
	 */
	public String getMsgCode() {
		return msgCode;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setMsgCode(java.lang.String)
	 */
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getMsgDesc()
	 */
	public String getMsgDesc() {
		return msgDesc;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setMsgDesc(java.lang.String)
	 */
	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getUserValidity()
	 */
	public String getUserValidity() {
		return userValidity;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setUserValidity(java.lang.String)
	 */
	public void setUserValidity(String userValidity) {
		this.userValidity = userValidity;
	}
//	public ErrorCode getErrorCode() {
//		return errorCode;
//	}
	
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#isProvFailed()
	 */
	public boolean isProvFailed() {
		return provFailed;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setProvFailed(boolean)
	 */
	public void setProvFailed(boolean provFailed) {
		this.provFailed = provFailed;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getValidFrom()
	 */
	public String getValidFrom() {
		return validFrom;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setValidFrom(java.lang.String)
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#isWarningsExists()
	 */
	public boolean isWarningsExists() {
		return warningsExists;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setWarningsExists(boolean)
	 */
	public void setWarningsExists(boolean warningsExists) {
		this.warningsExists = warningsExists;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getInvalidRoles()
	 */
	public List<IRoleInformation> getInvalidRoles() {
		return invalidRoles;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setInvalidRoles(java.util.List)
	 */
	public void setInvalidRoles(List<IRoleInformation> invalidRoles) {
		this.invalidRoles = invalidRoles;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#getUserId()
	 */
	public String getUserId() {
		return userId;
	}
	/* (non-Javadoc)
	 * @see com.alnt.access.provisioning.model.IProvisioningResult#setUserId(java.lang.String)
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public IBadgeInformation getBadgeInformation() {

		return badgeInformation;
	}
	public void setBadgeInformation(IBadgeInformation badgeDetails) {

		this.badgeInformation = badgeDetails;		
	}
	
	public String getSourceId() {
		return sourceId;
	}
	
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
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
	public String getErrorMsgCode() {
		return errorMsgCode;
	}
	public void setErrorMsgCode(String errorMsgCode) {
		this.errorMsgCode = errorMsgCode;
	}
	


}
