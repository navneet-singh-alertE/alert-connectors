package com.alnt.restconnector.test;


import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.alnt.access.common.constants.IProvisoiningConstants;
import com.alnt.access.provisioning.model.IProvisioningResult;
import com.alnt.access.provisioning.model.ISystemInformation;
import com.alnt.extractionconnector.common.constants.IExtractionConstants;
import com.alnt.extractionconnector.common.constants.IExtractionConstants.TYPE;
import com.alnt.extractionconnector.common.model.IRoleInformation;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;
import com.alnt.restconnector.provisioning.commons.IResponse;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.model.RoleAuditInfo;
import com.alnt.restconnector.provisioning.model.RoleInformation;
import com.alnt.restconnector.provisioning.services.RestConnectionInterface;
import com.alnt.restconnector.provisioning.utils.Utils;

public class TestSalesForceApp {

	private static Map<String, String> conParams = new HashMap<String, String>();
	private static RestConnectionInterface connectionInterface;
	
	@BeforeClass
	  public  static void setUpBeforeClass() throws Exception{
	    init();
	  }
	 
	  @AfterClass
	  public  static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Initialize test parameters to test connector
	 */
	public static void init(){
		
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_ID,"3MVG9A2kN3Bn17hsMNQtEJ4MzPxhY5m3J3RhwVnSnWPmq49zX30wMqkFjH66S4sgGXAzLV3pDHXr1V6n8w4TD");
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_SECRET,"4863273995726522630");
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_EMAIL,"");
		conParams.put(RestConstants.SYS_CON_ATTR_CUSTOMER_ID,"");
		conParams.put(RestConstants.SYS_CON_ATTR_DOMAIN,"");
		
//		conParams.put(RestConstants.SYS_CON_ATTR_USERID,"alertadmin2@testrest.com");
//		conParams.put(RestConstants.SYS_CON_ATTR_GRDN,"alert@123ct0che81pv59Nn77fEnua4Mh");
		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_URL,"http://192.168.192.142/api/oauth/token");
		conParams.put(RestConstants.SYS_CON_ATTR_USERID,"admin");
			conParams.put(RestConstants.SYS_CON_ATTR_GRDN,"AEadmin1234");
		conParams.put(RestConstants.SYS_CON_ATTR_GRANT_TYPE,"password");
		conParams.put(RestConstants.SYS_CON_ATTR_FORMAT,"json");
		
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_LOCATION,null);
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_TYPE,null);
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_GRDN,null);
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_ALIAS,null);
		
		conParams.put(RestConstants.SYS_CON_ATTR_USER_IDENTIFIER_KEY,"Id");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"ProfileId");
		conParams.put(RestConstants.SYS_CON_ATTR_CREATED_USER_IDENTIFIER_KEY,"id");
		
		conParams.put(RestConstants.SYS_CON_ATTR_LOCKED_ATTRIBUTE,"IsActive:false");
		conParams.put(RestConstants.SYS_CON_ATTR_USER_LAST_RUNTIME_ATTRIBUTE,"LastModifiedDate");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_LAST_RUNTIME_ATTRIBUTE,"LastModifiedDate");
		conParams.put(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT,"mm/dd/yyyy HH:mm:ss");
		conParams.put(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT,"yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT,"yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
		
		conParams.put(RestConstants.SYS_CON_ATTR_IMAGE_ATTRIBUTE,"imageAttachment");
		
		conParams.put(RestConstants.SYS_CON_ATTR_SCOPE,"");
//		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_URL,"https://login.salesforce.com/services/oauth2/token");
		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_VALIDATION_URL,"");
		
		conParams.put(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR,"D:\\JsonTemplates\\SalesForce");
		conParams.put("USER_PROVISIONED"+"0", "GET:services/data/v29.0/sobjects/User/{Id}");
		conParams.put("USER_LOCKED"+"0", "GET:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_CREATE_USER+"0", "POST:services/data/v29.0/sobjects/User");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UPDATE_USER+"0", "PATCH:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_DELETE_USER+"0", "DELETE:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_LOCK_USER+"0", "PATCH:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UNLOCK_USER+"0", "PATCH:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.ADD_ROLE+"_ROLE0", "PATCH:services/data/v29.0/sobjects/User/{Id}" );
		conParams.put(IProvisoiningConstants.REMOVE_ROLE+"_ROLE0", "PATCH:services/data/v29.0/sobjects/User/{Id}" );
		//conParams.put("ADD_IMAGE"+"0", "POST:profilephoto/005/F");//"POST:/services/data/v29.0/chatter/feeds/news/me/feed-items"
		//conParams.put("UPDATE_IMAGE"+"0", "PATCH:profilephoto/005/F");
		//conParams.put("GET_IMAGE"+"0", "GET:profilephoto/005/F");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UPDATE_GRDN+"0","POST:services/data/v29.0/sobjects/User/{Id}/password");
		conParams.put(IProvisoiningConstants.PROV_ACTION_CHANGE_VALIDITY_DATES+"0", "PATCH:services/data/v29.0/sobjects/User/{Id}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_DELIMIT_USER+"0", "PATCH:services/data/v29.0/sobjects/User/{Id}");
		
		conParams.put("GET_SPECIFIC_USER0","GET:services/data/v29.0/query?q=select%20Id%2CUsername%2CLastName%2CFirstName%2CName%2CCompanyName%2CDivision%2CDepartment%2CTitle%2CStreet%2CCity%2CState%2CPostalCode%2CCountry%2CEmail%2CPhone%2CFax%2CMobilePhone%2CAlias%2CCommunityNickname%2CIsActive%2CTimeZoneSidKey%2CUserRoleId%2CLocaleSidKey%2CReceivesInfoEmails%2CReceivesAdminInfoEmails%2CEmailEncodingKey%2CProfileId%2CLanguageLocaleKey%2CEmployeeNumber%2CDelegatedApproverId%2COfflineTrialExpirationDate%2COfflinePdaTrialExpirationDate%2CUserPermissionsMarketingUser%2CUserPermissionsOfflineUser%2CUserPermissionsCallCenterAutoLogin%2CUserPermissionsMobileUser%2CUserPermissionsSFContentUser%2CUserPermissionsKnowledgeUser%2CUserPermissionsInteractionUser%2CUserPermissionsSupportUser%2CUserPermissionsSiteforceContributorUser%2CUserPermissionsSiteforcePublisherUser%2CUserPermissionsChatterAnswersUser%2CForecastEnabled%2CUserPreferencesActivityRemindersPopup%2CUserPreferencesEventRemindersCheckboxDefault%2CUserPreferencesTaskRemindersCheckboxDefault%2CUserPreferencesReminderSoundOff%2CUserPreferencesDisableAllFeedsEmail%2CUserPreferencesDisableFollowersEmail%2CUserPreferencesDisableProfilePostEmail%2CUserPreferencesDisableChangeCommentEmail%2CUserPreferencesDisableLaterCommentEmail%2CUserPreferencesDisProfPostCommentEmail%2CUserPreferencesContentNoEmail%2CUserPreferencesDisableAutoSubForFeeds%2CUserPreferencesContentEmailAsAndWhen%2CUserPreferencesApexPagesDeveloperMode%2CUserPreferencesHideCSNGetChatterMobileTask%2CUserPreferencesDisableMentionsPostEmail%2CUserPreferencesDisMentionsCommentEmail%2CUserPreferencesHideCSNDesktopTask%2CUserPreferencesDisCommentAfterLikeEmail%2CUserPreferencesDisableLikeEmail%2CUserPreferencesDisableMessageEmail%2CUserPreferencesOptOutOfTouch%2CUserPreferencesDisableBookmarkEmail%2CUserPreferencesDisableSharePostEmail%2CContactId%2CAccountId%2CCallCenterId%2CExtension%2CFederationIdentifier%2CAboutMe%2CDigestFrequency%2CDefaultGroupNotificationFrequency%2CCreatedDate%2CLastModifiedDate%20from%20User%20where%20Id%3D'{Id}'");
		conParams.put("GET_USERS0","GET:services/data/v29.0/query?q=select%20Id%2CUsername%2CLastName%2CFirstName%2CName%2CCompanyName%2CDivision%2CDepartment%2CTitle%2CStreet%2CCity%2CState%2CPostalCode%2CCountry%2CEmail%2CPhone%2CFax%2CMobilePhone%2CAlias%2CCommunityNickname%2CIsActive%2CTimeZoneSidKey%2CUserRoleId%2CLocaleSidKey%2CReceivesInfoEmails%2CReceivesAdminInfoEmails%2CEmailEncodingKey%2CProfileId%2CLanguageLocaleKey%2CEmployeeNumber%2CDelegatedApproverId%2COfflineTrialExpirationDate%2COfflinePdaTrialExpirationDate%2CUserPermissionsMarketingUser%2CUserPermissionsOfflineUser%2CUserPermissionsCallCenterAutoLogin%2CUserPermissionsMobileUser%2CUserPermissionsSFContentUser%2CUserPermissionsKnowledgeUser%2CUserPermissionsInteractionUser%2CUserPermissionsSupportUser%2CUserPermissionsSiteforceContributorUser%2CUserPermissionsSiteforcePublisherUser%2CUserPermissionsChatterAnswersUser%2CForecastEnabled%2CUserPreferencesActivityRemindersPopup%2CUserPreferencesEventRemindersCheckboxDefault%2CUserPreferencesTaskRemindersCheckboxDefault%2CUserPreferencesReminderSoundOff%2CUserPreferencesDisableAllFeedsEmail%2CUserPreferencesDisableFollowersEmail%2CUserPreferencesDisableProfilePostEmail%2CUserPreferencesDisableChangeCommentEmail%2CUserPreferencesDisableLaterCommentEmail%2CUserPreferencesDisProfPostCommentEmail%2CUserPreferencesContentNoEmail%2CUserPreferencesDisableAutoSubForFeeds%2CUserPreferencesContentEmailAsAndWhen%2CUserPreferencesApexPagesDeveloperMode%2CUserPreferencesHideCSNGetChatterMobileTask%2CUserPreferencesDisableMentionsPostEmail%2CUserPreferencesDisMentionsCommentEmail%2CUserPreferencesHideCSNDesktopTask%2CUserPreferencesDisCommentAfterLikeEmail%2CUserPreferencesDisableLikeEmail%2CUserPreferencesDisableMessageEmail%2CUserPreferencesOptOutOfTouch%2CUserPreferencesDisableBookmarkEmail%2CUserPreferencesDisableSharePostEmail%2CContactId%2CAccountId%2CCallCenterId%2CExtension%2CFederationIdentifier%2CAboutMe%2CDigestFrequency%2CDefaultGroupNotificationFrequency%2CCreatedDate%2CLastModifiedDate%20from%20User");
		conParams.put("GET_USER_ROLE0","GET:services/data/v29.0/query?q=SELECT%20Id%2CName%2CPermissionsEditTask%2CPermissionsEditEvent%2CPermissionsManageUsers%2CPermissionsModifyAllData%2CPermissionsManageCases%2CPermissionsMassInlineEdit%2CPermissionsEditKnowledge%2CPermissionsManageKnowledge%2CPermissionsManageSolutions%2CPermissionsCustomizeApplication%2CPermissionsEditReadonlyFields%2CPermissionsRunReports%2CPermissionsViewSetup%2CPermissionsTransferAnyEntity%2CPermissionsNewReportBuilder%2CPermissionsImportLeads%2CPermissionsManageLeads%2CPermissionsTransferAnyLead%2CPermissionsViewAllData%2CPermissionsEditPublicDocuments%2CPermissionsSendSitRequests%2CPermissionsManageRemoteAccess%2CPermissionsCanUseNewDashboardBuilder%2CPermissionsManageCategories%2CPermissionsConvertLeads%2CPermissionsPasswordNeverExpires%2CPermissionsUseTeamReassignWizards%2CPermissionsInstallMultiforce%2CPermissionsPublishMultiforce%2CPermissionsEditOppLineItemUnitPrice%2CPermissionsCreateMultiforce%2CPermissionsBulkApiHardDelete%2CPermissionsSolutionImport%2CPermissionsManageCallCenters%2CPermissionsManageSynonyms%2CPermissionsManageEmailClientConfig%2CPermissionsEnableNotifications%2CPermissionsManageDataIntegrations%2CPermissionsDistributeFromPersWksp%2CPermissionsViewDataCategories%2CPermissionsManageDataCategories%2CPermissionsAuthorApex%2CPermissionsManageMobile%2CPermissionsApiEnabled%2CPermissionsManageCustomReportTypes%2CPermissionsEditCaseComments%2CPermissionsTransferAnyCase%2CPermissionsManageAnalyticSnapshots%2CPermissionsScheduleReports%2CPermissionsManageBusinessHourHolidays%2CPermissionsManageDynamicDashboards%2CPermissionsCustomSidebarOnAllPages%2CPermissionsManageInteraction%2CPermissionsViewMyTeamsDashboards%2CPermissionsResetPasswords%2CPermissionsFlowUFLRequired%2CPermissionsCanInsertFeedSystemFields%2CPermissionsManageKnowledgeImportExport%2CPermissionsEmailTemplateManagement%2CPermissionsEmailAdministration%2CPermissionsManageChatterMessages%2CPermissionsChatterFileLink%2CPermissionsViewCaseInteraction%2CPermissionsManageAuthProviders%2CUserLicenseId%2CUserType%2CCreatedDate%2CCreatedById%2CLastModifiedDate%2CLastModifiedById%2CSystemModstamp%2CDescription%20from%20profile%20where%20Id%3D'{ProfileId}'");
		
		conParams.put("GET_INCREMENTAL_USERS0","GET:services/data/v29.0/query?q=select%20Id%2CUsername%2CLastName%2CFirstName%2CName%2CCompanyName%2CDivision%2CDepartment%2CTitle%2CStreet%2CCity%2CState%2CPostalCode%2CCountry%2CEmail%2CPhone%2CFax%2CMobilePhone%2CAlias%2CCommunityNickname%2CIsActive%2CTimeZoneSidKey%2CUserRoleId%2CLocaleSidKey%2CReceivesInfoEmails%2CReceivesAdminInfoEmails%2CEmailEncodingKey%2CProfileId%2CLanguageLocaleKey%2CEmployeeNumber%2CDelegatedApproverId%2COfflineTrialExpirationDate%2COfflinePdaTrialExpirationDate%2CUserPermissionsMarketingUser%2CUserPermissionsOfflineUser%2CUserPermissionsCallCenterAutoLogin%2CUserPermissionsMobileUser%2CUserPermissionsSFContentUser%2CUserPermissionsKnowledgeUser%2CUserPermissionsInteractionUser%2CUserPermissionsSupportUser%2CUserPermissionsSiteforceContributorUser%2CUserPermissionsSiteforcePublisherUser%2CUserPermissionsChatterAnswersUser%2CForecastEnabled%2CUserPreferencesActivityRemindersPopup%2CUserPreferencesEventRemindersCheckboxDefault%2CUserPreferencesTaskRemindersCheckboxDefault%2CUserPreferencesReminderSoundOff%2CUserPreferencesDisableAllFeedsEmail%2CUserPreferencesDisableFollowersEmail%2CUserPreferencesDisableProfilePostEmail%2CUserPreferencesDisableChangeCommentEmail%2CUserPreferencesDisableLaterCommentEmail%2CUserPreferencesDisProfPostCommentEmail%2CUserPreferencesContentNoEmail%2CUserPreferencesDisableAutoSubForFeeds%2CUserPreferencesContentEmailAsAndWhen%2CUserPreferencesApexPagesDeveloperMode%2CUserPreferencesHideCSNGetChatterMobileTask%2CUserPreferencesDisableMentionsPostEmail%2CUserPreferencesDisMentionsCommentEmail%2CUserPreferencesHideCSNDesktopTask%2CUserPreferencesDisCommentAfterLikeEmail%2CUserPreferencesDisableLikeEmail%2CUserPreferencesDisableMessageEmail%2CUserPreferencesOptOutOfTouch%2CUserPreferencesDisableBookmarkEmail%2CUserPreferencesDisableSharePostEmail%2CContactId%2CAccountId%2CCallCenterId%2CExtension%2CFederationIdentifier%2CAboutMe%2CDigestFrequency%2CDefaultGroupNotificationFrequency%2CCreatedDate%2CLastModifiedDate%20from%20User%20where%20LastModifiedDate%3E{LastModifiedDate}");
		conParams.put("GET_INCREMENTAL_USER_ROLE0","GET:services/data/v29.0/query?q=SELECT%20Id%2CName%2CPermissionsEditTask%2CPermissionsEditEvent%2CPermissionsManageUsers%2CPermissionsModifyAllData%2CPermissionsManageCases%2CPermissionsMassInlineEdit%2CPermissionsEditKnowledge%2CPermissionsManageKnowledge%2CPermissionsManageSolutions%2CPermissionsCustomizeApplication%2CPermissionsEditReadonlyFields%2CPermissionsRunReports%2CPermissionsViewSetup%2CPermissionsTransferAnyEntity%2CPermissionsNewReportBuilder%2CPermissionsImportLeads%2CPermissionsManageLeads%2CPermissionsTransferAnyLead%2CPermissionsViewAllData%2CPermissionsEditPublicDocuments%2CPermissionsSendSitRequests%2CPermissionsManageRemoteAccess%2CPermissionsCanUseNewDashboardBuilder%2CPermissionsManageCategories%2CPermissionsConvertLeads%2CPermissionsPasswordNeverExpires%2CPermissionsUseTeamReassignWizards%2CPermissionsInstallMultiforce%2CPermissionsPublishMultiforce%2CPermissionsEditOppLineItemUnitPrice%2CPermissionsCreateMultiforce%2CPermissionsBulkApiHardDelete%2CPermissionsSolutionImport%2CPermissionsManageCallCenters%2CPermissionsManageSynonyms%2CPermissionsManageEmailClientConfig%2CPermissionsEnableNotifications%2CPermissionsManageDataIntegrations%2CPermissionsDistributeFromPersWksp%2CPermissionsViewDataCategories%2CPermissionsManageDataCategories%2CPermissionsAuthorApex%2CPermissionsManageMobile%2CPermissionsApiEnabled%2CPermissionsManageCustomReportTypes%2CPermissionsEditCaseComments%2CPermissionsTransferAnyCase%2CPermissionsManageAnalyticSnapshots%2CPermissionsScheduleReports%2CPermissionsManageBusinessHourHolidays%2CPermissionsManageDynamicDashboards%2CPermissionsCustomSidebarOnAllPages%2CPermissionsManageInteraction%2CPermissionsViewMyTeamsDashboards%2CPermissionsResetPasswords%2CPermissionsFlowUFLRequired%2CPermissionsCanInsertFeedSystemFields%2CPermissionsManageKnowledgeImportExport%2CPermissionsEmailTemplateManagement%2CPermissionsEmailAdministration%2CPermissionsManageChatterMessages%2CPermissionsChatterFileLink%2CPermissionsViewCaseInteraction%2CPermissionsManageAuthProviders%2CUserLicenseId%2CUserType%2CCreatedDate%2CCreatedById%2CLastModifiedDate%2CLastModifiedById%2CSystemModstamp%2CDescription%20from%20Profile%20where%20Id%3D'{ProfileId}'%20and%20LastModifiedDate%3E{LastModifiedDate}");
		
		conParams.put("GET_ROLES0","GET:services/data/v29.0/query?q=SELECT%20Id%2CName%2CPermissionsEditTask%2CPermissionsEditEvent%2CPermissionsManageUsers%2CPermissionsModifyAllData%2CPermissionsManageCases%2CPermissionsMassInlineEdit%2CPermissionsEditKnowledge%2CPermissionsManageKnowledge%2CPermissionsManageSolutions%2CPermissionsCustomizeApplication%2CPermissionsEditReadonlyFields%2CPermissionsRunReports%2CPermissionsViewSetup%2CPermissionsTransferAnyEntity%2CPermissionsNewReportBuilder%2CPermissionsImportLeads%2CPermissionsManageLeads%2CPermissionsTransferAnyLead%2CPermissionsViewAllData%2CPermissionsEditPublicDocuments%2CPermissionsSendSitRequests%2CPermissionsManageRemoteAccess%2CPermissionsCanUseNewDashboardBuilder%2CPermissionsManageCategories%2CPermissionsConvertLeads%2CPermissionsPasswordNeverExpires%2CPermissionsUseTeamReassignWizards%2CPermissionsInstallMultiforce%2CPermissionsPublishMultiforce%2CPermissionsEditOppLineItemUnitPrice%2CPermissionsCreateMultiforce%2CPermissionsBulkApiHardDelete%2CPermissionsSolutionImport%2CPermissionsManageCallCenters%2CPermissionsManageSynonyms%2CPermissionsManageEmailClientConfig%2CPermissionsEnableNotifications%2CPermissionsManageDataIntegrations%2CPermissionsDistributeFromPersWksp%2CPermissionsViewDataCategories%2CPermissionsManageDataCategories%2CPermissionsAuthorApex%2CPermissionsManageMobile%2CPermissionsApiEnabled%2CPermissionsManageCustomReportTypes%2CPermissionsEditCaseComments%2CPermissionsTransferAnyCase%2CPermissionsManageAnalyticSnapshots%2CPermissionsScheduleReports%2CPermissionsManageBusinessHourHolidays%2CPermissionsManageDynamicDashboards%2CPermissionsCustomSidebarOnAllPages%2CPermissionsManageInteraction%2CPermissionsViewMyTeamsDashboards%2CPermissionsResetPasswords%2CPermissionsFlowUFLRequired%2CPermissionsCanInsertFeedSystemFields%2CPermissionsManageKnowledgeImportExport%2CPermissionsEmailTemplateManagement%2CPermissionsEmailAdministration%2CPermissionsManageChatterMessages%2CPermissionsChatterFileLink%2CPermissionsViewCaseInteraction%2CPermissionsManageAuthProviders%2CUserLicenseId%2CUserType%2CCreatedDate%2CCreatedById%2CLastModifiedDate%2CLastModifiedById%2CSystemModstamp%2CDescription%20from%20Profile");
		conParams.put("GET_INCREMENTAL_ROLES0","GET:services/data/v29.0/query?q=SELECT%20Id%2CName%2CPermissionsEditTask%2CPermissionsEditEvent%2CPermissionsManageUsers%2CPermissionsModifyAllData%2CPermissionsManageCases%2CPermissionsMassInlineEdit%2CPermissionsEditKnowledge%2CPermissionsManageKnowledge%2CPermissionsManageSolutions%2CPermissionsCustomizeApplication%2CPermissionsEditReadonlyFields%2CPermissionsRunReports%2CPermissionsViewSetup%2CPermissionsTransferAnyEntity%2CPermissionsNewReportBuilder%2CPermissionsImportLeads%2CPermissionsManageLeads%2CPermissionsTransferAnyLead%2CPermissionsViewAllData%2CPermissionsEditPublicDocuments%2CPermissionsSendSitRequests%2CPermissionsManageRemoteAccess%2CPermissionsCanUseNewDashboardBuilder%2CPermissionsManageCategories%2CPermissionsConvertLeads%2CPermissionsPasswordNeverExpires%2CPermissionsUseTeamReassignWizards%2CPermissionsInstallMultiforce%2CPermissionsPublishMultiforce%2CPermissionsEditOppLineItemUnitPrice%2CPermissionsCreateMultiforce%2CPermissionsBulkApiHardDelete%2CPermissionsSolutionImport%2CPermissionsManageCallCenters%2CPermissionsManageSynonyms%2CPermissionsManageEmailClientConfig%2CPermissionsEnableNotifications%2CPermissionsManageDataIntegrations%2CPermissionsDistributeFromPersWksp%2CPermissionsViewDataCategories%2CPermissionsManageDataCategories%2CPermissionsAuthorApex%2CPermissionsManageMobile%2CPermissionsApiEnabled%2CPermissionsManageCustomReportTypes%2CPermissionsEditCaseComments%2CPermissionsTransferAnyCase%2CPermissionsManageAnalyticSnapshots%2CPermissionsScheduleReports%2CPermissionsManageBusinessHourHolidays%2CPermissionsManageDynamicDashboards%2CPermissionsCustomSidebarOnAllPages%2CPermissionsManageInteraction%2CPermissionsViewMyTeamsDashboards%2CPermissionsResetPasswords%2CPermissionsFlowUFLRequired%2CPermissionsCanInsertFeedSystemFields%2CPermissionsManageKnowledgeImportExport%2CPermissionsEmailTemplateManagement%2CPermissionsEmailAdministration%2CPermissionsManageChatterMessages%2CPermissionsChatterFileLink%2CPermissionsViewCaseInteraction%2CPermissionsManageAuthProviders%2CUserLicenseId%2CUserType%2CCreatedDate%2CCreatedById%2CLastModifiedDate%2CLastModifiedById%2CSystemModstamp%2CDescription%20from%20Profile%20where%20LastModifiedDate%3E{LastModifiedDate}");
		
		conParams.put(RestConstants.SYS_CON_ATTR_INSTANCE_URL,"http://192.168.192.142");//"https://www.salesforce.com"
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_USERS_TAG,"records");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_ROLES_TAG,"records");
		connectionInterface = new RestConnectionInterface(conParams);
	}
	
	@Test
	public void testConnection(){
		boolean connected=false;
		try {
			connected=connectionInterface.testConnection();
		   System.out.println("Is Test connection successfull: "+connected);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing Connection", connected);
	}
	
	@Ignore
	public void testUserProvisioned(){
		boolean isUserProvisioned=false;
		 try {
			 Map<String,Object> params=new LinkedHashMap<String,Object>();
			 ISystemInformation info=connectionInterface.isUserProvisioned("005i0000002rtTmAAI");
			 isUserProvisioned=info.isProvisioned();
		 } catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue( "Testing if user already exists",isUserProvisioned==true);
	}
	
	@Ignore
	public void testUserLocked(){
		 boolean isUserLocked=false;
		 try {
			 Map<String,Object> params=new LinkedHashMap<String,Object>();
			 isUserLocked=connectionInterface.isUserLocked("005i0000002rtTmAAI");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Locked", isUserLocked);
	}
	
	@Ignore
	public void testCreateUser(){
		 IProvisioningResult provResult=null;
		 try {
					Map<String,Object> params=new LinkedHashMap<String,Object>();

				 	params.put("FirstName","Venkar");
					params.put("LastName","Kumar");
					params.put("Username","Venkar.kumar@aaa.com");
					params.put("Email","Venkar.Kumar@aaa.com");
					params.put("Alias","Venkar");
					params.put("TimeZoneSidKey","America/Los_Angeles");
					params.put("LocaleSidKey","en_US");
					params.put("EmailEncodingKey","ISO-8859-1");
					params.put("LanguageLocaleKey","en_US");
					params.put("ProfileId","00ei0000001hnvHAAQ");
					params.put("CommunityNickname", "Venkar.Kumar");
					params.put("IsActive", true);
					params.put("TimeZoneSidKey", "America/Los_Angeles");
					params.put("UserRoleId", null);
					params.put("LocaleSidKey", "en_US");
					params.put("EmailEncodingKey", "ISO-8859-1");
					params.put("UserType", "Standard");
					params.put("LanguageLocaleKey", "en_US");
					params.put("EmployeeNumber", null);
					params.put("ManagerId", null);
					params.put("ContactId", null);
					params.put("AccountId", null);
					params.put("CallCenterId", null);
				
			 	provResult=connectionInterface.create(null, null, params,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Creation", provResult.isUserCreated());
	}

	
	@Ignore
	public void testDeleteAccount(){
		 IProvisioningResult provResult=null;
		 try {
			 	Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("Id", "005i0000002rtTmAAI");
			 	provResult=connectionInterface.deleteAccount(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Deletion", !provResult.isProvFailed());
	}
	
	@Ignore
	public void testDelimitUser(){
		 IProvisioningResult provResult=null;
		 try {
			 	Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("Id", "005i0000002rtTmAAI");
			 	params.put("startDate", new java.util.Date());
			 	params.put("endDate", new java.util.Date());
			 	provResult=connectionInterface.delimitUser(null, null, params, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Deletion", !provResult.isProvFailed());
	}
	
	@Ignore
	 public  void testUpdateUser(){
			try {
				Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("Id", "005i0000002rtTmAAI");
				params.put("CompanyName","Space Time");
				params.put("Email", "udit.kumar@aaa.com");
				params.put("Alias", "Udit");
				params.put("CommunityNickname", "Udit.Kumar1.3947157687470803E12");
				params.put("IsActive", true);
				params.put("TimeZoneSidKey", "America/Los_Angeles");
				params.put("UserRoleId", null);
				params.put("LocaleSidKey", "en_US");
				params.put("ReceivesInfoEmails", false);
				params.put("ReceivesAdminInfoEmails", false);
				params.put("EmailEncodingKey", "ISO-8859-1");
				params.put("ProfileId", null);
				params.put("UserType", "Standard");
				params.put("LanguageLocaleKey", "en_US");
				params.put("EmployeeNumber", null);
				params.put("DelegatedApproverId", null);
				params.put("ManagerId", null);
				params.put("LastLoginDate", null);
				params.put("ContactId", null);
				params.put("AccountId", null);
				params.put("CallCenterId", null);
				params.put("Extension", null);
				params.put("FederationIdentifier", null);
				params.put("AboutMe", null);
				params.put("CurrentStatus", null);
				params.put("FullPhotoUrl", "https,//c.na15.content.force.com/profilephoto/005/F");
				params.put("SmallPhotoUrl", "https,//c.na15.content.force.com/profilephoto/005/T");
				params.put("DigestFrequency", "D");
				params.put("DefaultGroupNotificationFrequency", "N");
				connectionInterface.update(null, null, params, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	@Ignore
	 public  void testUpdatePassword(){
		try{
			connectionInterface.updatePassword(null, "005i0000002s1qSAAQ", "", "udit123956");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testDeprovisionRoles( ){
		List<RoleAuditInfo> roleAuditInfoList=null;
		try {
			String userId="005i0000002s1qSAAQ";
			
			List deprovisionRoles= new ArrayList<IRoleInformation>();
			IRoleInformation role1=new RoleInformation();
			role1.setName("00Ei00000014JOyEAM");
			deprovisionRoles.add(role1);

			//IRoleInformation role2=new RoleInformation();
			//role2.setName("");
			//deprovisionRoles.add(role2);
			
			List invalidRoles=new ArrayList<IRoleInformation>();
			//roleAuditInfoList=connectionInterface.deprovisionRoles(userId, deprovisionRoles, invalidRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue("Testing change Access", !roleAuditInfoList.isEmpty());
	}
	
	@Test
	public void  testChangeAccess( ){
		IProvisioningResult provResult=null;
		try {
			List deprovisionRoles= new ArrayList<IRoleInformation>();
			List provisionRoles= new ArrayList<IRoleInformation>();
			IRoleInformation role1=new RoleInformation();
			role1.setName("System Administrator");//00Ei00000014JOyEAM;
			role1.setValidFrom(new java.util.Date());
			role1.setValidTo(new java.util.Date());
			role1.setDescription("");
			provisionRoles.add(role1);
			Map<String,List<String>> memberMap=role1.getMemberData();
			ArrayList<String> list=new ArrayList();
			list.add("00Ei00000014JOyEAM");
			memberMap.put(IExtractionConstants.TECHNICAL_ROLE_NAME, list);
			
			Map<String,Object> parameters=new HashMap<String,Object>();
			parameters.put("Id", "005i0000002QQq8AAG");
			provResult=connectionInterface.changeAccess(null, provisionRoles,  parameters,
					null, deprovisionRoles, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing change Access", !provResult.isProvFailed());
	}
	
	@Ignore
	 public  void testLockAccount(){
			IProvisioningResult provResult=null;
			try {
				Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("Id", "005i0000002rtTmAAI");
				provResult=connectionInterface.lock(null, null, params, null, null);
			} catch (Exception e) {
				
				
				e.printStackTrace();
			}
			assertTrue("Testing Lock Account", !provResult.isProvFailed());
		}
	
	@Ignore
	 public  void testUnLockAccount(){
			IProvisioningResult provResult=null;
			try {
				Map<String,Object> params=new LinkedHashMap<String,Object>();
				params.put("Id", "005i0000002rtTmAAI");
				provResult=connectionInterface.unlock(null, null, params, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertTrue("Testing Unlock Account", !provResult.isProvFailed());
		}
	
	
	@Ignore
	public void testReconOperations(){
		String userId="All";
		IResponse response=null;
		try {
			response = connectionInterface.doReconOperation("Get Users","GET_USERS", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing fetch roles",response!=null);
	}
	
	
	@Ignore
	public void testGetAllRolesWithCallBack(){  
		try {
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		  	
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("Id");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Id",extractorlist);

			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Name");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Name",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Description");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Description",extractorlist);
		
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastModifiedDate");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("LastModifiedDate",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("CreatedDate");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("CreatedDate",extractorlist);
			
			
			connectionInterface.getAllRoles(options, 100, null, new TestRestAppCallback());
		} catch (ExtractorConnectionException e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testGetIncrementalRolesWithCallBack(){  
		try {
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		  	
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("Id");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Id",extractorlist);

			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Name");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Name",extractorlist);
		
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastModifiedDate");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("LastModifiedDate",extractorlist);
			
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Description");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Description",extractorlist);
			
			
			Map<String,String> searchCriteria = new HashMap<String, String>();
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");;
			Date lastRunDate=sdf.parse("01/01/2012 12:50:16");
			connectionInterface.getIncrementalRoles(lastRunDate,options, 100, searchCriteria, new TestRestAppCallback());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	 public void testGetAllUsersWithCallBack(){
		 try {
			 
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("UserId");//alert attribute
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Id",extractorlist);//external attr
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastName");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("LastName",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("CreatedDate");
			extractorAttributes.setUserAttr(true);
			extractorAttributes.setFieldType(TYPE.DATE);
			extractorAttributes.setFormat("mm/dd/yyyy HH:mm:ss");
			extractorlist.add(extractorAttributes);
			options.put("CreatedDate",extractorlist);
			
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LockFlag");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("IsActive",extractorlist);
			
		
			Map<String,String> searchCriteria = new HashMap<String, String>();
			int fetchSize=4;
			connectionInterface.getAllUsersWithCallback(options, fetchSize, searchCriteria, new TestRestAppCallback());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	
	@Ignore
	 public void testGetIncrementalUsersWithCallBack(){
		 try {
			 
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("UserId");//alert attribute
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Id",extractorlist);//external attr
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastName");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("LastName",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastModifiedDate");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("LastModifiedDate",extractorlist);
			

			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LockFlag");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("IsActive",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Id");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Id",extractorlist);

			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Name");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Name",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Description");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("Description",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Valid From");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("ValidFrom",extractorlist);
		
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Valid To");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("ValidTo",extractorlist);
			
				
			Map<String,String> searchCriteria = new HashMap<String, String>();
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");;
			Date lastRunDate=sdf.parse("01/01/2012 12:50:16");
			connectionInterface.getIncrementalUsersWithCallback(lastRunDate,options, 100, searchCriteria, new TestRestAppCallback());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	@Ignore
	public void testProvisionImage(){
			boolean status=false;
			File file= new File("C:\\Users\\amit.ghildiyal\\Downloads\\gerbera.jpg");
			if(file.exists()){
		        try {
		        	FileInputStream fis = new FileInputStream(file);
			        byte[] buf = new byte[1024];
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
		            for (int readNum; (readNum = fis.read(buf)) != -1;) {
		                bos.write(buf, 0, readNum); 
		            }
		            bos.flush();
		            byte[] imageBytes = bos.toByteArray();
		           // String value=new String(Base64.encodeBase64(imageBytes));
		            String encodedImage = Utils.encodeValue(imageBytes);
		            Map<String,Object> params=new LinkedHashMap<String,Object>();
					params.put("Id","005i0000002rtTmAAI");
		            //params.put("imageTitle", "Image Title");
		            //params.put("imageDescription", "Image Description");
		            //params.put("imageFileName", "udit.jpg");
		            params.put("photoData", encodedImage);
		            //System.out.println(new String(imageBytes));
					
					//status=connectionInterface.provisionImage(params);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
			}
	        assertTrue("Testing Provisioning Image", status);
	}
	
	@Ignore
	public void testGetImage(){
		File file=null;
		try {
				byte[] imageBytes=connectionInterface.getImage("005i0000002ri2FAAQ");
				file= new File("C:\\Users\\amit.ghildiyal\\Downloads\\test.jpg");
				FileOutputStream fos = new FileOutputStream(file);
		        fos.write(imageBytes);
		        fos.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
        assertTrue("Testing Getting Image", file.exists() && file.length()>0);
	}
}
