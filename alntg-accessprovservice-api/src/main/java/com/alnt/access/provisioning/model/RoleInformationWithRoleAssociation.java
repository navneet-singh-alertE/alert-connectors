package com.alnt.access.provisioning.model;

import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IRoleInformationWithRoleAssociation;



public class RoleInformationWithRoleAssociation extends RoleInformation implements IRoleInformationWithRoleAssociation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 126467734319496991L;
	
	
	private List<Map<String,String>> roleAssociation;


	public List<Map<String, String>> getRoleAssociation() {
		return roleAssociation;
	}


	public void setRoleAssociation(List<Map<String, String>> roleAssociation) {
		this.roleAssociation = roleAssociation;
	}
	

}
