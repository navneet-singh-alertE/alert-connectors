package com.alnt.restconnector.provisioning.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IRoleInformation;

public class RoleInformation implements IRoleInformation, com.alnt.fabric.component.rolemanagement.search.IRoleInformation {

	private String name;
	private String roleType;
	private Date validFrom,validTo;
	private String validFromMappingAttr,validToMappingAttr;
	private String description;
	private Map<String,List<String>> memberData = new HashMap<String, List<String>>();
	private String enterpriseRoleName;
	private String longDescription = "";
	private String language;
	private String repositoryRoleName;
	private String roleAction;
	public String getRoleAction() {
		return roleAction;
	}
	public void setRoleAction(String roleAction) {
		this.roleAction = roleAction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getValidFromMappingAttr() {
		return validFromMappingAttr;
	}
	public void setValidFromMappingAttr(String validFromMappingAttr) {
		this.validFromMappingAttr = validFromMappingAttr;
	}
	public String getValidToMappingAttr() {
		return validToMappingAttr;
	}
	public void setValidToMappingAttr(String validToMappingAttr) {
		this.validToMappingAttr = validToMappingAttr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, List<String>> getMemberData() {
		return memberData;
	}
	public void setMemberData(Map<String, List<String>> memberData) {
		this.memberData = memberData;
	}
	public String getEnterpriseRoleName() {
		return enterpriseRoleName;
	}
	public void setEnterpriseRoleName(String enterpriseRoleName) {
		this.enterpriseRoleName = enterpriseRoleName;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRepositoryRoleName() {
		return repositoryRoleName;
	}
	public void setRepositoryRoleName(String repositoryRoleName) {
		this.repositoryRoleName = repositoryRoleName;
	}
	
}
