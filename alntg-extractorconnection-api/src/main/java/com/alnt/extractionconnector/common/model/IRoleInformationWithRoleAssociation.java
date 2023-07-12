package com.alnt.extractionconnector.common.model;

import java.util.List;
import java.util.Map;

public interface IRoleInformationWithRoleAssociation extends IRoleInformation {
	
	public List<Map<String,String>> getRoleAssociation();
	public void setRoleAssociation(List<Map<String,String>> roleAssociation);
}
