package com.alnt.restconnector.test;


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

import com.alnt.access.certification.model.IUserCertificationRequest;
import com.alnt.access.certification.model.UserCertificationRequest;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.services.RestConnectionInterface;

public class TestSocoConnector {
	
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

	
	/**
	 * Initialize test parameters to test connector
	 */
	public static void init(){
		
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_ID,"3MVG9A2kN3Bn17hsWfTBbyQ6FGjw1oW44qEwFKJli4nsoEpe1d.dung4hBkNDBemt2yfMdajWWerTeLcNB0_c");
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_SECRET,"4925027475473660610");
		conParams.put(RestConstants.SYS_CON_ATTR_USERID,"alertadmin@testrest.com");
		conParams.put(RestConstants.SYS_CON_ATTR_GRDN,"alert@123sFGIVFtCQ2FsV35xJJdNGH27");
		conParams.put(RestConstants.SYS_CON_ATTR_GRANT_TYPE,"password");
		conParams.put(RestConstants.SYS_CON_ATTR_FORMAT,"json");
		conParams.put(RestConstants.SYS_CON_ATTR_USER_IDENTIFIER_KEY,"UserId");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"");
		conParams.put(RestConstants.SYS_CON_ATTR_BADGE_IDENTIFIER_KEY,"");
		//conParams.put(RestConstants.SYS_CON_ATTR_LOCKED_ATTRIBUTE,"IsActive:false");
		conParams.put(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT,"yyyy-MM-dd HH:mm:ss");
		//conParams.put(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT,"yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT,"MM/dd/yyyy HH:mm:ss");
		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_URL,"https://login.soco.com/services/oauth2/token");

		
		conParams.put(RestConstants.SYS_CON_ATTR_USER_LAST_RUNTIME_ATTRIBUTE,"LastModifiedDate");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_LAST_RUNTIME_ATTRIBUTE,"");
		
		conParams.put(RestConstants.SYS_CON_ATTR_SYSTEM_NAME,"Soco Connector 1");
		
		conParams.put(RestConstants.SYS_CON_ATTR_INSTANCE_URL,"http://localhost:8080/RestfulWS/rest");//https://www.soco.com
		conParams.put(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR,"D:\\JsonTemplates\\Soco");
		conParams.put("USER_PROVISIONED0", "GET:Users/{UserId}");
		conParams.put("GET_USERS0","GET:Users?SystemName={SystemName}&pageToken={NextPageToken}");
		conParams.put("GET_INCREMENTAL_USERS0","GET:Users?SystemName={SystemName}&LastModifiedDate={LastModifiedDate}&pageToken={NextPageToken}");
		conParams.put("EVALUATE_CERTIFICATES_REQUEST0","POST:Users/Certifications/EvaluateUserCertifications/{UserId}");
		conParams.put("GET_USER_CERTIFICATIONS0","GET:Users/Certifications?SystemName={SystemName}&pageToken={NextPageToken}");
		conParams.put("GET_INCREMENTAL_USER_CERTIFICATIONS0","GET:Users/Certifications?SystemName={SystemName}&LastModifiedDate={LastModifiedDate}&pageToken={NextPageToken}");
		
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_USERS_TAG,"Users");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_ROLES_TAG,"Roles");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_CERTIFICATIONS_TAG,"UserCertificationList");
		
		connectionInterface = new RestConnectionInterface(conParams);
	}
	
	@Ignore
	public  void testEvaluateUserCertifications() {
		try {
			Map<String, String> userParams = new HashMap<String, String>();
			userParams.put("UserId", "Amit.Ghildiyal");

			List<String> certificationNames = new ArrayList<String>(); 			
			certificationNames.add("Sample User Training 1");
			certificationNames.add("Sample User Training 2");			
			
			Map<String, List> certMapping = new HashMap<String, List>();	
			
			List AEStatusList = new ArrayList();
			AEStatusList.add("AEStatus");
			//AEStatusList.add("Active");
			certMapping.put("Status", AEStatusList);
			
			List AECompletionDate = new ArrayList();
			AECompletionDate.add("ValidTo");			
			certMapping.put("CompletionDate", AECompletionDate);
			
			IUserCertificationRequest userCertResult = new UserCertificationRequest();
			userCertResult.setUserDetails(userParams);
			userCertResult.setCertificationNames(certificationNames);
			userCertResult.setCertMapping(certMapping);
			
			connectionInterface.evaluateUserCertifications(userCertResult);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetUserCertifications(){
		Map<String,String> searchCriteria = new HashMap<String, String>();
		try {
			  connectionInterface.getAllUserCertifications(getCertificationExtractionAttributes(), 3, searchCriteria,new TestRestAppCallback());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testGetIncrementalUserCertifications(){
		Map<String,String> searchCriteria = new HashMap<String, String>();
		try {
			  SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			  Date date  = sdfDate.parse("11/13/2013 11:00:00");
			  connectionInterface.getIncrementalUserCertifications(date,getCertificationExtractionAttributes(), 3, searchCriteria,new TestRestAppCallback());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, List<ExtractorAttributes>> getCertificationExtractionAttributes(){
		Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();

		ExtractorAttributes extractorAttributes = new ExtractorAttributes("UserId");
		extractorAttributes.setUserCertificationAttr(true);
		List<ExtractorAttributes> extractorlist = new ArrayList<ExtractorAttributes>();
		extractorlist.add(extractorAttributes);
		options.put("UserId", extractorlist);
		
		ExtractorAttributes trainingIdExtractorAttributes = new ExtractorAttributes("Name");
		trainingIdExtractorAttributes.setUserCertificationAttr(true);
		List<ExtractorAttributes> tIdExtractorlist = new ArrayList<ExtractorAttributes>();
		tIdExtractorlist.add(trainingIdExtractorAttributes);
		options.put("Name", tIdExtractorlist);
		
		ExtractorAttributes statusAttr = new ExtractorAttributes("Status");
		statusAttr.setUserCertificationAttr(true);
		List<ExtractorAttributes> statusAttrList = new ArrayList<ExtractorAttributes>();
		statusAttrList.add(statusAttr);
		options.put("Status", statusAttrList);
		
		ExtractorAttributes trainingNameExtractorAttributes = new ExtractorAttributes("ValidTo");
		trainingNameExtractorAttributes.setUserCertificationAttr(true);
		//trainingNameExtractorAttributes.setFieldType(IExtractionConstants.TYPE.DATE);
		//trainingNameExtractorAttributes.setFormat("MM/dd/yyyy");
		List<ExtractorAttributes> tNameExtractorlist = new ArrayList<ExtractorAttributes>();
		tNameExtractorlist.add(trainingNameExtractorAttributes);
		options.put("CompletionDate", tNameExtractorlist);
		
		return options;

	}

}
