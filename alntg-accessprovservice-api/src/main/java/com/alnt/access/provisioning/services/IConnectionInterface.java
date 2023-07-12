package com.alnt.access.provisioning.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alnt.access.common.constants.IProvisoiningConstants;
import com.alnt.access.provisioning.model.IProvisioningResult;
import com.alnt.access.provisioning.model.IProvisioningStatus;
import com.alnt.access.provisioning.model.ISystemInformation;
import com.alnt.access.provisioning.utils.ConnectorUtil;
import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;

/**
 * Contains methods to access the external systems of a particular System
 * Connector type (LDAP, SAP, PACS etc). These methods include methods to
 * provision and methods to retrieve System Connector specific roles.
 * 
 * @author
 */
public interface IConnectionInterface {

	public static final String DETAIL_KEY_TCODES_FOR_ROLES = "TCODES_FOR_ROLES";
	
	/**
	 * retrieves roles based on a search string
	 * 
	 * @param searchString
	 *            search string
	 * @return roles based on a search string; empty list if there are no roles
	 *         matching the search string
	 * @throws Exception;
	 *             if there is any error in retrieving roles
	 */
	public List getRoles (String searchString)
			throws Exception; 
	
	
	public List getAllRoles (String searchString)
	throws Exception;
	

	public List getRolesForUser (String user)
			throws Exception; 

	public boolean supportsProvisioning (); 

	/**
	 * does provisioning on the external system based on the AlertEnterprise
	 * request information. Provisioning includes establishing provisioning for
	 * a new user, updating access for an existing user and deleting user/access
	 * from the system.
	 * 
	 * @param requestNumber
	 *            request number
	 * @param action
	 *            provisioning action to perform
	 * @param roles
	 *            list of roles. This list can be empty to denote no roles.
	 * @param parameters
	 *            parameters for provisioning
	 * @param requestDetails
	 *            request details
	 * @throws Exception;
	 *             if there is any error in provisioning on the external system
	 */
	public List provision(Long requestNumber,String userId, List provActions, List<IRoleInformation> roles,
			Map parameters,
			List requestDetails, 
			List<IRoleInformation> deprovisionRole,
			Map<String,String> attMapping)
			throws Exception;

	public List getAttributes ()
			throws Exception; 

	public IProvisioningResult create(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,Map<String,String> attMapping)
			throws Exception;

	public IProvisioningResult update(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,
			Map<String,String> attMapping)
			throws Exception;

	public IProvisioningResult lock(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,
			Map<String,String> attMapping)
			throws Exception;

	public IProvisioningResult unlock(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,
			Map<String,String> attMapping)
			throws Exception;

	public IProvisioningResult changeAccess(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,
			List<IRoleInformation> deprovisionRole,
			Map<String,String> attMapping)
			throws Exception;
	
	public ISystemInformation isUserProvisioned(String userId) 
			throws Exception;

	public boolean isUserLocked(String userId) 
		throws Exception;

	
	public IProvisioningResult deleteAccount(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	
	public IProvisioningResult delimitUser(Long requestNumber, List roles,
			Map parameters, List requestDetails,String action,			
			Map<String,String> attMapping)
			throws Exception;
	
	public IProvisioningResult addBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult addTempBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult changeBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult removeBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult activateBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult deActivateBadge(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;
	public IProvisioningResult changeBadgeRoles(Long requestNumber, List roles,
			Map parameters, List requestDetails,
			Map<String,String> attMapping)
			throws Exception;	
	public List getExistingBadges(String userId) throws Exception;
	
	public List getDetailsAsList(String detailName, String keyForDetails) throws Exception;
	public boolean testConnection() throws Exception;
	public IProvisioningStatus getProvisioningStatus(Map<String,String> params)throws Exception;
	public void setTaskId(Long taskId);
	/*
	 * This method is used to get the last badge activity for a user associated badges.
	 */
	public List getBadgeLastLocation(String userId) throws Exception;
	/*r
	 * This method is overloaded create method where we can send the attribute which we need
	 * to check for duplicate check.
	 */
	public IProvisioningResult create(Long requestNumber, List roles,
			Map parameters, List requestDetails, Map<String, String> attMapping, Map<String,String> validateAttr)
			throws Exception;
	
/* This method is addded so that class using this interface has to implement this method */
	
	public IProvisioningResult updatePassword(Long requestNumber,String userId,String origPwd,String newPwd)  throws Exception;
	public List getAttributes (Map params)throws Exception;
	
	public default boolean isRetryableErrorCode(String errorMsg, Map<String, String> connectionParams) {

		if (connectionParams != null && !connectionParams.isEmpty()) {
			String retryAbleErrorMsgs = (String) connectionParams.get(IProvisoiningConstants.RETRYABLE_ERROR_MSGS);
			Set<String> errorCodeList = ConnectorUtil.convertStringToList(retryAbleErrorMsgs, "~");
			if (errorCodeList != null && !errorCodeList.isEmpty() && errorMsg!=null && !errorMsg.isEmpty()) {
				boolean isRetryableException=errorCodeList.stream().filter(a -> errorMsg.indexOf(a) != -1).findAny().isPresent();
				if (isRetryableException) {
						return true;
				}
			}
		}
		return false;
	}

}
