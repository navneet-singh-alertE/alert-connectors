/**
 * 
 */
package com.alnt.access.provisioning.model;

import java.util.Date;
import java.util.List;

/**
 * @author vamshi.rokandla
 *
 */
public interface IBadgeInformation {
	
	public String getBadgeId();
	public void setBadgeId(String badgeId);
	public String getDescription();
	public void setDescription(String description);
	public Date getValidFrom();
	public void setValidFrom(Date validFrom);
	public Date getValidTo();
	public void setValidTo(Date validTo);
	public String getProvAction();
	public void setProvAction(String provAction);
	public String getBadgeStatus();
	public void setBadgeStatus(String badgeStatus);
	public String getBadgeType();
	public void setBadgeType(String badgeType);
	public String getBadgeStatusStr();
	public void setBadgeStatusStr(String badgeStatusStr);
	public String getBadgeTypeStr();
	public void setBadgeTypeStr(String badgeTypeStr);
	

}
