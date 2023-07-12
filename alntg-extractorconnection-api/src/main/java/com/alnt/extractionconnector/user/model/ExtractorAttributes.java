	package com.alnt.extractionconnector.user.model;

import com.alnt.extractionconnector.common.constants.IExtractionConstants;

public class ExtractorAttributes {
	
	private String attributeName;	
	private String format="";
	private IExtractionConstants.TYPE fieldType=IExtractionConstants.TYPE.STRING;
	private boolean roleAttr;
	
	private boolean userAttr;
	private boolean badgeAttr;
	private boolean tokenAttr;
	private boolean entRoleAttr;
	private boolean locationAttr;
	private boolean activityAttr;
	private boolean companyAttr;
	
	public boolean isCompanyAttr() {
		return companyAttr;
	}

	public void setCompanyAttr(boolean companyAttr) {
		this.companyAttr = companyAttr;
	}
	private boolean assetAttr;
	
	private boolean userCertificationAttr;
	
	public ExtractorAttributes(String attributeName,String format,IExtractionConstants.TYPE fieldType){
		this.attributeName = attributeName;
		this.format = format;
		this.fieldType = fieldType;
	}
	
	public ExtractorAttributes(String attributeName){
		this.attributeName = attributeName;
	}	

	public IExtractionConstants.TYPE getFieldType() {
		return fieldType;
	}

	public void setFieldType(IExtractionConstants.TYPE fieldType) {
		this.fieldType = fieldType;
	}

	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public boolean isRoleAttr() {
		return roleAttr;
	}

	public void setRoleAttr(boolean roleAttr) {
		this.roleAttr = roleAttr;
	}
	
	public boolean isBadgeAttr() {
		return badgeAttr;
	}

	public void setBadgeAttr(boolean badgeAttr) {
		this.badgeAttr = badgeAttr;
	}
	
	public boolean isLocationAttr() {
		return locationAttr;
	}

	public void setLocationAttr(boolean locationAttr) {
		this.locationAttr = locationAttr;
	}
	
	public boolean isActivityAttr() {
		return activityAttr;
	}

	public void setActivityAttr(boolean activityAttr) {
		this.activityAttr = activityAttr;
	}

	public boolean isTokenAttr() {
		return tokenAttr;
	}

	public void setTokenAttr(boolean tokenAttr) {
		this.tokenAttr = tokenAttr;
	}
	
	public boolean isAssetAttr() {
		return assetAttr;
	}

	public void setAssetAttr(boolean assetAttr) {
		this.assetAttr = assetAttr;
	}

	public boolean isUserAttr() {
		return userAttr;
	}

	public void setUserAttr(boolean userAttr) {
		this.userAttr = userAttr;
	}

	public boolean isEntRoleAttr() {
		return entRoleAttr;
	}

	public void setEntRoleAttr(boolean entRoleAttr) {
		this.entRoleAttr = entRoleAttr;
	}

	public boolean isUserCertificationAttr() {
		return userCertificationAttr;
	}

	public void setUserCertificationAttr(boolean userCertificationAttr) {
		this.userCertificationAttr = userCertificationAttr;
	}
}
