package com.alnt.access.common.constants;

//import de.schlichtherle.util.ObfuscatedString;

public interface IProvisoiningConstants {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String IS_PARTIAL_SUCCESS = "IS_PARTIAL_SUCCESS";
	public static final String IS_ALL_SUCCESS = "IS_ALL_SUCCESS";
	public static final String IS_ALL_FAILURE = "IS_ALL_FAILURE";
	public static final String PROV_ACTION_CREATE_USER = "CREATE_USER";
	public static final String PROV_ACTION_UPDATE_GRDN = "UPDATE_PASSWORD";
	public static final String PROV_ACTION_CHANGE_ROLES = "CHANGE_ROLES";
	public static final String PROV_ACTION_CHANGE_USER = "CHANGE_USER";
	public static final String PROV_ACTION_UPDATE_USER = "UPDATE_USER";
	public static final String PROV_ACTION_CHANGE_VALIDITY_DATES = "CHANGE_VALIDITY_DATES";
	public static final String PROV_ACTION_DELETE_USER = "DELETE_USER";
	public static final String PROV_ACTION_DELIMIT_USER = "DELIMIT_USER";
	public static final String PROV_ACTION_LOCK_USER = "LOCK_USER";
	public static final String PROV_ACTION_UNLOCK_USER = "UNLOCK_USER";
	public static final String PROV_ACTION_GRDN_RESET = "PASSWORD_RESET";
	public static final String PROV_ACTION_ASSIGN_BADGE = "ASSIGN_BADGE";
	public static final String PROV_ACTION_ASSIGN_ASSET = "ASSIGN_ASSET";
	public static final String PROV_ACTION_CREATE_TEMP_BADGE = "CREATE_TEMP_BADGE";
	public static String PROV_ACTION_GET_CONN = "GET_CONNECTION";
	
	
	public static final String PROV_ACTION_REMOVE_ROLES = "REMOVE_ROLES";
	public static final String CREATE_USER_SUCCESS = "CREATE_USER_SUCCESS";
	public static final String UPDATE_GRDN_SUCCESS = "UPDATE_PASSWORD_SUCCESS";	
	public static final String CREATE_USER_FAILURE = "CREATE_USER_FAILURE";
	public static final String UPDATE_GRDN_FAILURE = "UPDATE_PASSWORD_FAILURE";
	public static final String CHANGE_USER_SUCCESS = "CHANGE_USER_SUCCESS";
	public static final String CHANGE_USER_FAILURE = "CHANGE_USER_FAILURE";
	public static final String LOCK_USER_SUCCESS = "LOCK_USER_SUCCESS";
	public static final String LOCK_USER_FAILURE = "LOCK_USER_FAILURE";
	public static final String UNLOCK_USER_SUCCESS = "UNLOCK_USER_SUCCESS";
	public static final String UNLOCK_USER_FAILURE = "UNLOCK_USER_FAILURE";
	public static final String DELETE_USER_SUCCESS = "DELETE_USER_SUCCESS";
	public static final String DELETE_USER_FAILURE = "DELETE_USER_FAILURE";
	public static final String DELIMIT_USER_SUCCESS = "DELIMIT_USER_SUCCESS";
	public static final String DELIMIT_USER_FAILURE = "DELIMIT_USER_FAILURE";
	public static final String CHANGE_VALIDITY_DATES_SUCCESS = "CHANGE_VALIDITY_DATES_SUCCESS";
	public static final String CHANGE_VALIDITY_DATES_FAILURE = "CHANGE_VALIDITY_DATES_FAILURE";
	public static final String ASSIGN_ROLES_SUCCESS = "ASSIGN_ROLES_SUCCESS";
	public static final String ASSIGN_ROLES_FAILURE = "ASSIGN_ROLES_FAILURE";
	public static final String ASSIGN_ROLES_PARTIAL_SUCCESS = "ASSIGN_ROLES_PARTIAL_SUCCESS";
	public static final String GRDN_RESET_SUCCESS = "PASSWORD_RESET_SUCCESS";
	public static final String GRDN_RESET_FAILURE = "PASSWORD_RESET_FAILURE";
	public static final String MAIL_SUBJECT = "Account Created";
	public static final String ADD_ROLE = "ADD";
	public static final String REMOVE_ROLE = "REMOVE";
	public static final String UPDATE_ROLE = "UPDATE";
	public static final String SKIP_ROLE = "SKIP";
	public static final String REMOVE_ROLES = "Role Remove";//For AD added by abhineet
	public static String PROV_ACTION_GET_CONN_SUCCESS = "GET_CONN_SUCCESS";
	public static String PROV_ACTION_GET_CONN_FAILURE = "GET_CONN_FAILURE";
	public static String TEMPORARY_ROLE_ATTR="TemporaryRole";
	public static String REGION_CONNECTOR_SUBMIT_REQUEST = "REGION_CONNECTOR_SUBMIT_REQUEST"; //devtrack 43152
	public static String REGION_CONNECTOR_SUBMIT_SUCCESS = "REGION_CONNECTOR_SUBMIT_SUCCESS"; //devtrack 43152
	public static String REGION_CONNECTOR_SUBMIT_FAILURE = "REGION_CONNECTOR_SUBMIT_FAILURE"; //devtrack 43152
	public static final String ALREADY_ADDED_ROLE = "ALREADY_ADDED";
	public static final String ALREADY_REMOVED_ROLE = "ALREADY_REMOVED";

	public static interface USERLOCKSTATUS {
		static final Long ACTIVE = new Long(1);
		static final Long INACTIVE = new Long(2);
		static final Long LOCKEDOUT = new Long(3);
		static final Long UNKNOWN = new Long(4);
	};

	public static final String SUPER_USER_IDS = "SUPER_USER_IDS";
	public static final String USER_ID = "UserId";
	public static final String CREATE_USER_ASYNC_SUCCESS = "CREATE_USER_ASYNC_SUCCESS";
	public static final String CHANGE_USER_ASYNC_SUCCESS = "CHANGE_USER_ASYNC_SUCCESS";
	public static final String LOCK_USER_ASYNC_SUCCESS = "LOCK_USER_ASYNC_SUCCESS";
	public static final String UNLOCK_USER_ASYNC_SUCCESS = "UNLOCK_USER_ASYNC_SUCCESS";
	public static final String DELIMIT_USER_ASYNC_SUCCESS = "DELIMIT_USER_ASYNC_SUCCESS";
	public static final String CHANGE_VALIDITY_USER_ASYNC_SUCCESS = "CHANGE_VALIDITY_USER_ASYNC_SUCCESS";
	public static final String ASSIGN_ROLES_ASYNC_SUCCESS = "ASSIGN_ROLES_ASYNC_SUCCESS";
	
	//LAWA-383 - This code is used by caller, to retry the provisioning for specific type of failures.
	public static final String RETRYABLE_FAILURE = "RETRYABLE_FAILURE";
	public static final String RETRYABLE_ERROR_MSGS = "RETRYABLE_ERROR_MSGS";
	public static final String EXT_REF_ID = "ExternalRefID";
	public static final String AUTO_GEN_ID_UPDATED = "AutoGeneratedIDUpdated";	
//	public static final String grdn = new ObfuscatedString(new long[] { 0x7F5D2B994A8A27FEL, 0x95040059083A0873L }).toString();
}