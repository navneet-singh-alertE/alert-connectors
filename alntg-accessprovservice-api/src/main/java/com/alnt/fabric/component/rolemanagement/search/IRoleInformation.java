package com.alnt.fabric.component.rolemanagement.search;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoleInformation {	
	public String getName();
	public void setName(String name);
	public String getRepositoryRoleName();
	public void setRepositoryRoleName(String name);
	public Date getValidFrom();
	public void setValidFrom(Date validFrom);
	public Date getValidTo();
	public void setValidTo(Date validTo);
	public String getValidFromMappingAttr();	
	public void setValidFromMappingAttr(String validFromMappingAttr);
	public String getValidToMappingAttr();
	public void setValidToMappingAttr(String validToMappingAttr);
	public String getDescription();
	public void setDescription(String description);
	public String getLongDescription() ;
	public void setLongDescription(String longDescription);
	public Map<String, List<String>> getMemberData();
	public void setMemberData(Map<String, List<String>> memberData);
	public String getEnterpriseRoleName();
	public void setEnterpriseRoleName(String rbacRoleType);	
	public String getLanguage();
	public void setLanguage(String language);
	public String getRoleType();
	public void setRoleType(String language);
	public String getRoleAction();
	public void setRoleAction(String roleAction);
	
	public default void setDelayedForFutureDate(Boolean futureDateFlag){
		
	}
	
	public default Boolean isDelayedForFutureDate(){
		return Boolean.FALSE;
	}
}

