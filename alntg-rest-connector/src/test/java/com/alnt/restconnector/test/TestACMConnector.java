package com.alnt.restconnector.test;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.alnt.access.common.constants.IProvisoiningConstants;
import com.alnt.access.provisioning.model.IProvisioningResult;
import com.alnt.access.provisioning.model.ISystemInformation;
import com.alnt.extractionconnector.common.model.IRoleInformation;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.model.RoleInformation;
import com.alnt.restconnector.provisioning.services.RestConnectionInterface;

public class TestACMConnector {
	
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
		
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_ID,"");
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_SECRET,"");
		conParams.put(RestConstants.SYS_CON_ATTR_USERID,"AlertEnterprise");
		conParams.put(RestConstants.SYS_CON_ATTR_GRDN,"AE2015");
		conParams.put(RestConstants.SYS_CON_ATTR_SSL,"true");
		conParams.put(RestConstants.SYS_CON_ATTR_GRANT_TYPE,"");
		conParams.put(RestConstants.SYS_CON_ATTR_FORMAT,"xml");
		conParams.put(RestConstants.SYS_CON_ATTR_USER_IDENTIFIER_KEY,"cn");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"");
		conParams.put(RestConstants.SYS_CON_ATTR_BADGE_IDENTIFIER_KEY,"");
		//conParams.put(RestConstants.SYS_CON_ATTR_LOCKED_ATTRIBUTE,"IsActive:false");
		conParams.put(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT,"yyyy-MM-dd HH:mm:ss");
		//conParams.put(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT,"yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT,"MM/dd/yyyy HH:mm:ss");
		//conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_URL,"");

		
		conParams.put(RestConstants.SYS_CON_ATTR_USER_LAST_RUNTIME_ATTRIBUTE,"LastModifiedDate");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_LAST_RUNTIME_ATTRIBUTE,"");
		
		conParams.put(RestConstants.SYS_CON_ATTR_SYSTEM_NAME,"ACM Connector 1");
		
		conParams.put(RestConstants.SYS_CON_ATTR_INSTANCE_URL,"https://demo.redcloudsecurity.com:63447");//https://www.soco.com
		conParams.put(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR,"D:\\XMLTemplates\\ACM");

		conParams.put(IProvisoiningConstants.PROV_ACTION_CREATE_USER+"0", "POST:identities.xml");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UPDATE_USER+"0", "PUT:identities/{cn}.xml");
		conParams.put(IProvisoiningConstants.PROV_ACTION_DELETE_USER+"0", "DELETE:identities/{cn}.xml");
		conParams.put(IProvisoiningConstants.PROV_ACTION_LOCK_USER+"0", "PUT:identities/{cn}.xml");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UNLOCK_USER+"0", "PUT:identities/{cn}.xml");
		conParams.put("SEARCH_USER0", "GET:identities/?filter=plasecId={ExternalSystemId}");
		conParams.put(IProvisoiningConstants.ADD_ROLE+"_ROLE0", "PUT:identities/{cn}.xml" );
		conParams.put(IProvisoiningConstants.REMOVE_ROLE+"_ROLE0", "PUT:identities/{cn}.xml" );
		
		conParams.put(IProvisoiningConstants.PROV_ACTION_ASSIGN_BADGE+"0", "POST:/identities/{cn}/tokens.xml");
		conParams.put(RestConstants.PROV_ACTION_UPDATE_BADGE+"0", "PUT:/identities/{cn}/tokens/{TokenId}.xml");
		conParams.put(RestConstants.PROV_ACTION_ACTIVATE_BADGE+"0", "PUT:/identities/{cn}/tokens/{TokenId}.xml");
		conParams.put(RestConstants.PROV_ACTION_DEACTIVATE_BADGE+"0", "PUT:/identities/{cn}/tokens/{TokenId}.xml");
		
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_USERS_TAG,"Users");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_ROLES_TAG,"Roles");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_CERTIFICATIONS_TAG,"UserCertificationList");
		
		connectionInterface = new RestConnectionInterface(conParams);
	}
	
	@Test
	public void testConnection(){
		boolean connected=false;
		try {
			connected=connectionInterface.testConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing Connection", connected);
	}
	
	/*@Test
	public void testConnection(){
		HttpClient httpClient = new HttpClient("demo.redcloudsecurity.com", 63447);
		http.use_ssl = true
		http.start do |http|
		# this request is for a list of all doors
		req = Net::HTTP::Get.new('/doors.xml')
		# give our login and password for authorization and authentication
		# !!IMPORTANT: Secure this in the real world!!!
		req.basic_auth(‘user’, 'pasword')
		# set the content type to xml for REST commands
		req["Content-Type"] = "application/xml"
		#send the REST request
		resp, data = http.request(req)
		# this sample program displays the response and data
		puts "RESPONSE: #{resp} \n\n"
		puts "DATA: "
		puts "#{data
	}*/
	
	@Ignore
	public void testUserProvisioned(){
		boolean isUserProvisioned=false;
		 try {
			 Map<String,Object> params=new LinkedHashMap<String,Object>();
			 ISystemInformation info=connectionInterface.isUserProvisioned("5ded59ccba79401c");
			 isUserProvisioned=info.isProvisioned();
		 } catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue( "Testing if user already exists",isUserProvisioned==true);
	}
	
	@Ignore
	 public  void testLockAccount(){
			IProvisioningResult provResult=null;
			try {
				Map<String,Object> params=new HashMap<String,Object>();params.put("cn", "5ded59ccba79401c");
				params.put("IDStatus","2");
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
				Map<String,Object> params=new HashMap<String,Object>();params.put("cn", "5ded59ccba79401c");
				params.put("IDStatus","1");
				provResult=connectionInterface.unlock(null, null, params, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertTrue("Testing Unlock Account", !provResult.isProvFailed());
		}
	
	
	@Ignore
	public void testUserLocked(){
		 boolean isUserLocked=false;
		 try {
			 Map<String,Object> params=new LinkedHashMap<String,Object>();
			 isUserLocked=connectionInterface.isUserLocked("5ded59ccba79401c");
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
					params.put("ExternalSystemId", "121213");
				 	params.put("FirstName","TestUser6");
					params.put("LastName","TestUser6");
					params.put("MiddleName","X");
					params.put("IDStatus","1");
					params.put("EmailAddress","test2.user2@aaa.com");
					params.put("Status", "Terminated");
					//params.put("PageTimeout","604800000");
					params.put("IssueDate","20141213000000Z");
					params.put("Department","Sales");
					params.put("Type","Contractor");
					params.put("Title","Manager");
					params.put("Division","Federal Sector");
					params.put("Address","317 Cat lane");
					params.put("City","Edison");
					params.put("State","New Jersey");
					params.put("ZipCode","08817");
					params.put("Phone","6666666666");
					params.put("WorkPhone","7777777777");
					params.put("SiteLocation","South");
					params.put("Building","Warehouse");
			 	provResult=connectionInterface.create(null, null, params,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Creation", provResult.isUserCreated());
	}

	
	@Ignore
	public void testUpdateUser(){
		 IProvisioningResult provResult=null;
		 try {
					Map<String,Object> params=new LinkedHashMap<String,Object>();
					params.put("cn", "5ded59ccba79401c");
				 	params.put("FirstName","TestUser5");
					params.put("LastName","TestUser5");
					params.put("MiddleName","K");
					params.put("IDStatus","1");
					params.put("EmailAddress","test5.user5.com");
					params.put("Status", "Terminated");
					params.put("PageTimeout","604800000");
					params.put("IssueDate","20141213000000Z");
					params.put("Department","Sales");
					params.put("Type","Contractor");
					params.put("Title","Manager");
					params.put("Division","Federal Sector");
					params.put("Address","317 Cat lane");
					params.put("City","Edison");
					params.put("State","New Jersey");
					params.put("ZipCode","08817");
					params.put("Phone","6666666666");
					params.put("WorkPhone","7777777777");
					params.put("SiteLocation","South");
					params.put("Building","Warehouse");
				
			 	provResult=connectionInterface.update(null, null, params,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Creation", provResult.isUserCreated());
	}
	
	
	@Ignore
	public void  testChangeAccess( ){
		IProvisioningResult provResult=null;
		try {
			List deprovisionRoles= new ArrayList<IRoleInformation>();
			List roles= new ArrayList<IRoleInformation>();
			IRoleInformation role1=new RoleInformation();
			role1.setName("00Ei00000014JOyEAM");
			role1.setValidFrom(new java.util.Date());
			role1.setValidTo(new java.util.Date());
			role1.setDescription("Role1 Description");
			roles.add(role1);
			
			Map<String,List<String>> memberMap=role1.getMemberData();
			ArrayList<String> list=new ArrayList();
			list.add("Member Data Value");
			memberMap.put("MemberData", list);
			
			Map<String,Object> parameters=new HashMap<String,Object>();
			parameters.put("cn", "5ded59ccba79401c");
			provResult=connectionInterface.changeAccess(null, roles,  parameters,
					null, deprovisionRoles, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing change Access", !provResult.isProvFailed());
	}
	
	
	/*@Ignore
	public void testDeleteAccount(){
		 IProvisioningResult provResult=null;
		 try {
			 	Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("cn", "5ded59ccba79401c");//0f1bdafae05242dd
			 	provResult=connectionInterface.deleteAccount(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Deletion", !provResult.isProvFailed());
	}
	*/

	@Ignore
	public void testAddBadge(){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("cn", "5ded59ccba79401c");
		params.put("TokenInternalNumber","888891");
		params.put("TokenEmbossedNumber","888888");
		params.put("TokenIssueDate","20141213000000Z");
		params.put("TokenActivateDate","20150311000000Z");
		params.put("TokenDeactivateDate","20250308000000Z");
		params.put("TokenDownload","TRUE");
		params.put("TokenPin","8889");
		params.put("TokenLevel","0");
		params.put("TokenVIP","FALSE");
		params.put("TokenNoExpire","TRUE");
		params.put("TokenTrace","FALSE");
		params.put("TokenStatus","1");
		params.put("TokenPinExempt","FALSE");
		params.put("TokenExtAccess","FALSE");
		params.put("TokenUserLoseExempt","FALSE");
		try {
			connectionInterface.addBadge(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Ignore
	public void testChangeBadge(){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("cn", "5ded59ccba79401c");
		params.put("TokenId","3");
		params.put("TokenInternalNumber","888890");
		params.put("TokenEmbossedNumber","888888");
		params.put("TokenIssueDate","20141213000000Z");
		params.put("TokenActivateDate","20150311000000Z");
		params.put("TokenDeactivateDate","20260308000000Z");
		params.put("TokenDownload","TRUE");
		params.put("TokenPin","9099");
		params.put("TokenLevel","0");
		params.put("TokenVIP","TRUE");
		params.put("TokenNoExpire","TRUE");
		params.put("TokenTrace","TRUE");
		params.put("TokenStatus","1");
		params.put("TokenPinExempt","TRUE");
		params.put("TokenExtAccess","TRUE");
		params.put("TokenUserLoseExempt","TRUE");
		try {
			connectionInterface.changeBadge(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Ignore
	public void testActivateBadge(){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("cn", "5ded59ccba79401c");
		params.put("TokenId","3");
		params.put("TokenStatus","1");
		try {
			connectionInterface.activateBadge(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testDeactivateBadge(){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("cn", "5ded59ccba79401c");
		params.put("TokenId","3");
		params.put("TokenStatus","2");
		try {
			connectionInterface.deActivateBadge(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*@Ignore
	public void testRemoveBadge(){
		Map<String,Object> params =  new HashMap<String,Object>();
		params.put("cn", "5ded59ccba79401c");
		params.put("TokenId","3");
		params.put("TokenStatus","2");
		try {
			lenelConnectorInterface.removeBadge(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
