package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public class BadgeInformation implements Serializable,IBadgeInformation{
	boolean provisioned=false;
	Date validFrom,validTo;	
	private String badgeStatus;
	private String badgeType;
	private String badgeId;
	private String description;
	private String provAction;
	private String badgeStatusStr;
	private String badgeTypeStr;
	private Map memberData;
	List<IRoleInformation> badgeRoles = new ArrayList<IRoleInformation>();
	
	public BadgeInformation(boolean provisioned)
	{
		this.provisioned=provisioned;
	}
	public BadgeInformation() {
		
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
	
	public List<IRoleInformation> getBadgeRoles() {
		return badgeRoles;
	}
	public void setBadgeRoles(List<IRoleInformation> badgeRoles) {
		this.badgeRoles = badgeRoles;
	}

	public String getBadgeId() {
		return badgeId;
	}
	public void setBadgeId(String badgeId) {
		this.badgeId = badgeId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProvAction() {
		return provAction;
	}
	public void setProvAction(String provAction) {
		this.provAction = provAction;
	}
	public String getBadgeStatusStr() {
		return badgeStatusStr;
	}
	public void setBadgeStatusStr(String badgeStatusStr) {
		this.badgeStatusStr = badgeStatusStr;
	}
	public String getBadgeTypeStr() {
		return badgeTypeStr;
	}
	public void setBadgeTypeStr(String badgeTypeStr) {
		this.badgeTypeStr = badgeTypeStr;
	}
	public String getBadgeStatus() {
		return badgeStatus;
	}
	public void setBadgeStatus(String badgeStatus) {
		this.badgeStatus = badgeStatus;
	}
	public String getBadgeType() {
		return badgeType;
	}
	public void setBadgeType(String badgeType) {
		this.badgeType = badgeType;
	}
	
	public Map getMemberData() {
		return memberData;
	}
	public void setMemberData(Map memberData) {
		this.memberData = memberData;
	}

}
