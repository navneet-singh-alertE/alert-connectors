package com.alnt.access.provisioning.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

public class RoleInformation implements IRoleInformation, Serializable,com.alnt.extractionconnector.common.model.IRoleInformation {

	private String name;
	private Date validFrom,validTo;
	private String validFromMappingAttr,validToMappingAttr;
	private String description;
	private Map<String,List<String>> memberData = new HashMap<String, List<String>>();
	private String enterpriseRoleName;
	private String longDescription = "";
	private String language;
	private String repositoryRoleName;
	private String roleType;
	private String roleAction;
	@Override
	public String getRoleAction() {
		return roleAction;
	}
	@Override
	public void setRoleAction(final String roleAction) {
		this.roleAction = roleAction;
	}
	@Override
	public String getRoleType() {
		return roleType;
	}
	@Override
	public void setRoleType(final String roleType) {
		this.roleType = roleType;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(final String name) {
		this.name = name;
	}
	@Override
	public Date getValidFrom() {
		return validFrom;
	}
	@Override
	public void setValidFrom(final Date validFrom) {
		this.validFrom = validFrom;
	}
	@Override
	public Date getValidTo() {
		return validTo;
	}
	@Override
	public void setValidTo(final Date validTo) {
		this.validTo = validTo;
	}
	@Override
	public String getValidFromMappingAttr() {
		return validFromMappingAttr;
	}
	@Override
	public void setValidFromMappingAttr(final String validFromMappingAttr) {
		this.validFromMappingAttr = validFromMappingAttr;
	}
	@Override
	public String getValidToMappingAttr() {
		return validToMappingAttr;
	}
	@Override
	public void setValidToMappingAttr(final String validToMappingAttr) {
		this.validToMappingAttr = validToMappingAttr;
	}
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(final String description) {
		this.description = description;
	}
	@Override
	public Map<String, List<String>> getMemberData() {
		return memberData;
	}
	@Override
	public void setMemberData(final Map<String, List<String>> memberData) {
		this.memberData = memberData;
	}
	@Override
	public String getEnterpriseRoleName() {
		return enterpriseRoleName;
	}
	@Override
	public void setEnterpriseRoleName(final String enterpriseRoleName) {
		this.enterpriseRoleName = enterpriseRoleName;
	}
	@Override
	public String getLongDescription() {
		return longDescription;
	}
	@Override
	public void setLongDescription(final String longDescription) {
		this.longDescription = longDescription;
	}
	@Override
	public String getLanguage() {
		return language;
	}
	@Override
	public void setLanguage(final String language) {
		this.language = language;
	}
	@Override
	public String getRepositoryRoleName() {
		return repositoryRoleName;
	}
	@Override
	public void setRepositoryRoleName(final String repositoryRoleName) {
		this.repositoryRoleName = repositoryRoleName;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoleInformation [name=").append(name).append(", validFrom=").append(validFrom)
		.append(", validTo=").append(validTo).append(", validFromMappingAttr=").append(validFromMappingAttr)
		.append(", validToMappingAttr=").append(validToMappingAttr).append(", description=").append(description)
		.append(", memberData=").append(memberData).append(", enterpriseRoleName=").append(enterpriseRoleName)
		.append(", longDescription=").append(longDescription).append(", language=").append(language)
		.append(", repositoryRoleName=").append(repositoryRoleName).append(", roleType=").append(roleType)
		.append(", roleAction=").append(roleAction).append("]");
		return builder.toString();
	}
}
