package com.alnt.extractionconnector.common.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IUserInformation extends Serializable {
	
	public static final String TRIGGER_NAME = "TRIGGER_NAME";
	public static final String REF_ID = "REF_ID";
	/*
	 * Output Param: Map<String,Object>
	 * 
	 * Key => Will be the user attributes name like firstname,lastname,...
	 * Value => Will be the user attribute values. This can be of the  type : STRING OR DATE.
	 * 
	 */
	public Map<String,Object> getUserDetails();
	
	/*
	 * Output Param : Map<String,Map<String,Map<String,Object>>>
	 * 
	 * Key1 => UserId. User Id of the selected user.
	 * Key2 => Entitlement types. This can take the values from {BADGE,ROLE,TOKEN}.
	 * Key3 => Entitlement attribute name like BadgeName,ROleValidFrom, RoleValidTo etc.
	 * Value => Entitlement attribute value. Value can be of the type :Date or String.
	 * List will have the set of the entitlements of the given type.
	 */
	public Map<String,Map<String,List<Map<String,Object>>>> getEntitlements();
	
	public void setUserDetails( Map<String, Object>  userDetails);
	public void setEntitlements(Map<String,Map<String,List<Map<String,Object>>>> entitlements);
	
}
