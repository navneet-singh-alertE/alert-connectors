package com.alnt.extractionconnector.common.constants;


public interface IExtractionConstants {
	
	public static final String DETAIL_KEY_TCODES_FOR_ROLES = "TCODES_FOR_ROLES";
	public enum TYPE{STRING,DATE,MULTIVALUE,IMAGE,OBJECT};
	public enum USER_ENTITLEMENT_KEY{BADGES,ROLES,TOKENS, ENTROLES, LOCATIONS,ASSETS,COMPANY};
	public enum MULTI_VALUE_ACTION{ADD,REMOVE,UPDATE,LOCK_USER,UNLOCK_USER,UPDATE_PASSWORD};
	public enum MULTI_VALUES_OPERATION{MERGE,REPLACE};
	public enum ASSET_TYPES{camera,reader,key,permanent_badge,temporary_badge};
	public static final String EXTRACTION_INTERRUPT = "509";
	public static final String EXTRACTION_CONNECTOR = "510";
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	public static final String DATE_PATTERN_WITH_SECONDS = "MM/dd/yyyy HH:mm:ss";
	public static final String ERR_INPUT_MISSING = "ERR_INPUT_MISSING";
	public static final String MISSION_ENTITY_LOOKUP = "11106"; // Mission Lookup value
	public static final String BADGE_ENTITY_LOOKUP = "11096"; // Badge Lookup Value
	public static final String ALERT_ENTITYTYPE = "ALERT_ENTITYTYPE"; // Attribute which store the Badge/Mission Lookup value
	public static final String USER_BADGE_ROLES_CONST = "UserBadgeRoles"; // Badge Attribute which store Badge Roles as part of recon
	public static final String TECHNICAL_ROLE_NAME = "TechnicalRoleName";
	public static final String ROLE_TYPE_SINGLE = "Role";

}
