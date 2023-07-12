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
import com.alnt.extractionconnector.common.model.IRoleInformation;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.model.RoleAuditInfo;
import com.alnt.restconnector.provisioning.model.RoleInformation;
import com.alnt.restconnector.provisioning.services.RestConnectionInterface;

public class TestGoogleApp {

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
		
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_ID,"46264846140-bqgiof8klo25tuiut2qb0i3qvrt1gkpa.apps.googleusercontent.com");
		//conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_SECRET,"JpFrPeKD_VxZIyLCql_Uj9_n");
		conParams.put(RestConstants.SYS_CON_ATTR_CLIENT_EMAIL,"46264846140-bqgiof8klo25tuiut2qb0i3qvrt1gkpa@developer.gserviceaccount.com");
		conParams.put(RestConstants.SYS_CON_ATTR_CUSTOMER_ID,"C04e56i28");
		conParams.put(RestConstants.SYS_CON_ATTR_DOMAIN,"alertenterprise.net");
		//conParams.put(RestConstants.SYS_CON_ATTR_REDIRECT_URI,"urn:ietf:wg:oauth:2.0:oob");
		//conParams.put(RestConstants.SYS_CON_ATTR_RESPONSE_TYPE,"code");
		conParams.put(RestConstants.SYS_CON_ATTR_FORMAT,"json");
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_LOCATION,"d:\\keyFile.p12");
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_TYPE,"PKCS12");
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_GRDN,"notasecret");
		conParams.put(RestConstants.SYS_CON_ATTR_CERTIFICATE_ALIAS,"privatekey");
		
		conParams.put(RestConstants.SYS_CON_ATTR_USER_IDENTIFIER_KEY,"primaryEmail");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"email");
		conParams.put(RestConstants.SYS_CON_ATTR_LOCKED_ATTRIBUTE,"suspended:true");
		conParams.put(RestConstants.SYS_CON_ATTR_IMAGE_ATTRIBUTE,"photoData");
		conParams.put(RestConstants.SYS_CON_ATTR_USER_LAST_RUNTIME_ATTRIBUTE,"LastModifiedDate,yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
		conParams.put(RestConstants.SYS_CON_ATTR_ROLE_LAST_RUNTIME_ATTRIBUTE,"");
		
		
		conParams.put(RestConstants.SYS_CON_ATTR_SCOPE,
				"https://www.googleapis.com/auth/admin.directory.user.readonly" +
				" https://www.googleapis.com/auth/admin.directory.user" +
				" https://www.googleapis.com/auth/admin.directory.group.readonly" +
				" https://www.googleapis.com/auth/admin.directory.group");
		conParams.put(RestConstants.SYS_CON_ATTR_AUTH_URL,"https://accounts.google.com/o/oauth2/auth");
		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_URL,"https://accounts.google.com/o/oauth2/token");
		conParams.put(RestConstants.SYS_CON_ATTR_TOKEN_VALIDATION_URL,"https://www.googleapis.com/oauth2/v1/tokeninfo");
	
		conParams.put(RestConstants.SYS_CON_ATTR_USERID,"alertadmin@alertenterprise.net");
		conParams.put(RestConstants.SYS_CON_ATTR_GRANT_TYPE,"");
		
		conParams.put(RestConstants.SYS_CON_ATTR_INSTANCE_URL,"https://www.googleapis.com");
		//conParams.put(RestConstants.SYS_CON_ATTR_USERS_URL,"admin/directory/v1/users");
		//conParams.put(RestConstants.SYS_CON_ATTR_ROLES_URL,"admin/directory/v1/groups");
		conParams.put(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR,"D:\\JsonTemplates\\GoogleApps");
		conParams.put("USER_PROVISIONED"+"0", "GET:admin/directory/v1/users/{primaryEmail}");
		conParams.put("USER_LOCKED"+"0", "GET:admin/directory/v1/users/{primaryEmail}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_CREATE_USER+"0", "POST:admin/directory/v1/users");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UPDATE_USER+"0", "PUT:admin/directory/v1/users/{primaryEmail}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_DELETE_USER+"0", "DELETE:admin/directory/v1/users/{primaryEmail}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_LOCK_USER+"0", "PUT:admin/directory/v1/users/{primaryEmail}");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UNLOCK_USER+"0", "PUT:admin/directory/v1/users/{primaryEmail}");
		conParams.put(IProvisoiningConstants.ADD_ROLE+"_ROLE0", "POST:admin/directory/v1/groups/{email}/members" );
		conParams.put(IProvisoiningConstants.REMOVE_ROLE+"_ROLE0", "DELETE:admin/directory/v1/groups/{email}/members/{primaryEmail}" );
		conParams.put("ADD_IMAGE"+"0", "PUT:admin/directory/v1/users/{primaryEmail}/photos/thumbnail");
		conParams.put("UPDATE_IMAGE"+"0", "PUT:admin/directory/v1/users/{primaryEmail}/photos/thumbnail");
		conParams.put("GET_IMAGE"+"0", "GET:admin/directory/v1/users/{primaryEmail}/photos/thumbnail");
		conParams.put(IProvisoiningConstants.PROV_ACTION_UPDATE_GRDN+"0","POST:admin/directory/v1/users/{primaryEmail}/password");
		conParams.put(IProvisoiningConstants.PROV_ACTION_CHANGE_VALIDITY_DATES+"0", null);
		conParams.put(IProvisoiningConstants.PROV_ACTION_DELIMIT_USER+"0", null);
		conParams.put("GET_USERS0","GET:admin/directory/v1/users");
		//conParams.put("GET_USER_ROLE0","GET:");
		//conParams.put("GET_INCREMENTAL_USERS0","GET:");
		//conParams.put("GET_INCREMENTAL_USER_ROLE0","GET:");
		conParams.put("GET_ROLES0","GET:admin/directory/v1/groups?domain={domain}");
		//conParams.put("GET_INCREMENTAL_ROLES0",null);//not supported
		
		
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_USERS_TAG,"users");
		conParams.put(RestConstants.SYS_CON_ATTR_RECON_ROLES_TAG,"groups");
		
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
	
	
	@Ignore
	public void testGetAllUsers(){
		 try {
			 connectionInterface.getAllUsers(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	@Test
	public void testUserProvisioned(){
		boolean isUserProvisioned=false;
		 try {
			 ISystemInformation info=connectionInterface.isUserProvisioned("vikas.sharma@alertenterprise.net");
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
			 isUserLocked=connectionInterface.isUserLocked("alertadmin@alertenterprise.net");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Locked", isUserLocked);
	}
	
	@Ignore
	public void testCreateUser(){
		 IProvisioningResult provResult=null;
		 try {
			 /*String jsonRequestString="{"
			  		+"\"name\": {"+"\"familyName\": \"Gupta\","+"\"givenName\": \"Vikas\","+"\"fullName\": \"Vikas Gupta\""+"},"
			  		+"\"password\": \"Alert125\"
			  		+"}";*/
		
				Map<String,Object> params=new LinkedHashMap<String,Object>();
				params.put("password","Alert125");
				params.put("primaryEmail","vikas.sharma@alertenterprise.net");
				params.put("name.familyName","Sharma");
				params.put("name.givenName","Vikas");
				params.put("name.fullName","Vikas Sharma");
				
				params.put("addresses1.type","home");
				params.put("addresses1.primary","true");
				params.put("addresses2.customType","");
				params.put("addresses1.postalCode","600001");
				params.put("addresses1.region","Mani Majra");
				params.put("addresses2.country","USA");
				
				params.put("addresses2.type","work");
				params.put("addresses2.customType","");
				params.put("addresses2.postalCode","24901");
				params.put("addresses1.country","India");
				
				params.put("emails1.type","home");
				params.put("emails1.primary","true");
				params.put("emails1.address","a@b.com");
				
				params.put("emails2.type","work");
				params.put("emails2.primary","false");
				params.put("emails2.address","c@d.com");
				
				params.put("phones1.type","work");
				params.put("phones1.primary",true);
				params.put("phones1.value",new Integer(77777777));
				params.put("phones2.type","pager");
				params.put("phones2.value",new Integer(88888888));
				params.put("phones3.type","mobile");
				params.put("phones3.value",new Integer(99999999));
			 	provResult=connectionInterface.create(null, null, params,null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Creation", provResult.isUserCreated());
	}
	
	@Ignore
	 public  void testUpdateAccount(){
			try {
				/*String jsonRequestString={ "name": {  "familyName": "Raman",  "fullName": "Raman Arikath",  "givenName": "Arikath" },
				 *  "addresses": [  {  },  {   "country": "India",   "postalCode": "24900"  },  {   "country": "USA",   "streetAddress": "Yosko Drive",   "region": "North East"  } ], 
				 * "password": "alert12345",
				 *  "suspended": false}*/
				Map<String,Object> params=new LinkedHashMap<String,Object>();
				params.put("password","Alert12345");
				params.put("primaryEmail","vikas.sharma@alertenterprise.net");
				params.put("name.familyName","Ram");
				params.put("name.givenName","Sureddy");
				params.put("name.fullName","Ram Sureddy1");
				params.put("password","alert12345");
				
				params.put("addresses1.type","custom");
				params.put("addresses2.customType","");
				params.put("addresses1.postalCode","600001");
				params.put("addresses1.region","Mani Majra");
				params.put("addresses2.country","USA");
				
				params.put("addresses2.type","custom");
				params.put("addresses2.customType","");
				params.put("addresses2.postalCode","24901");
				params.put("addresses1.country","India");
				
				params.put("emails1.type","home");
				params.put("emails1.primary","true");
				params.put("emails1.address","a@b.com");
				
				params.put("emails2.type","work");
				params.put("emails2.primary","false");
				params.put("emails2.address","c@d.com");
				
				params.put("phones1.type","work");
				params.put("phones1.primary",true);
				params.put("phones1.value",new Integer(77777777));
				params.put("phones2.type","pager");
				params.put("phones2.value",new Integer(88888888));
				params.put("phones3.type","mobile");
				params.put("phones3.value",new Integer(99999999));
				connectionInterface.update(null, null, params, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	@Ignore
	public void testDeleteAccount(){
		 IProvisioningResult provResult=null;
		 try {
			 	Map<String,Object> params=new HashMap<String,Object>();
			 	params.put("primaryEmail", "vikas.sharma@alertenterprise.net");
			 	provResult=connectionInterface.deleteAccount(null, null, params, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertTrue("Testing User Deletion", !provResult.isProvFailed());
	}
	
	
	@Ignore
	public void testDeprovisionRoles( ){
		List<RoleAuditInfo> roleAuditInfoList=null;
		try {
			String userId="vikas.sharma@alertenterprise.net";
			
			List deprovisionRoles= new ArrayList<IRoleInformation>();
			IRoleInformation role1=new RoleInformation();
			IRoleInformation role2=new RoleInformation();
			role1.setName("support1@alertenterprise.net");
			role2.setName("support2@alertenterprise.net");
			
			deprovisionRoles.add(role1);
			deprovisionRoles.add(role2);
			
			List invalidRoles=new ArrayList<IRoleInformation>();
			//roleAuditInfoList=connectionInterface.deprovisionRoles(userId, deprovisionRoles, invalidRoles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue("Testing change Access", !roleAuditInfoList.isEmpty());
	}
	
	@Test
	public void  testChangeAccess(){
		IProvisioningResult provResult=null;
		try {
			String userId="vikas.sharma@alertenterprise.net";
			List deprovisionRoles= new ArrayList<IRoleInformation>();
			List roles= new ArrayList<IRoleInformation>();
			IRoleInformation role1=new RoleInformation();
			IRoleInformation role2=new RoleInformation();
			role1.setName("support1@alertenterprise.net");
			role1.setValidFrom(new java.util.Date());
			role1.setValidTo(new java.util.Date());
			role2.setName("support2@alertenterprise.net");
			role2.setValidFrom(new java.util.Date());
			role2.setValidTo(new java.util.Date());
			roles.add(role1);
			roles.add(role2);
			Map<String,Object> parameters=new HashMap<String,Object>();
			parameters.put("primaryEmail", userId);
			//parameters.put("email", userId);
			provResult=connectionInterface.changeAccess(null, roles,  parameters,
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
				Map<String,Object> params=new LinkedHashMap<String,Object>();
				params.put("primaryEmail","vikas.sharma@alertenterprise.net");
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
				params.put("primaryEmail","vikas.sharma@alertenterprise.net");
				provResult=connectionInterface.unlock(null, null, params, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertTrue("Testing Unlock Account", !provResult.isProvFailed());
		}
	
	@Ignore
	public void testFetchUsers(){
		int maxResults=3;
		//IResponse response=connectionInterface.fetchUsers(maxResults,null);
		//assertTrue("Testing fetch users",response!=null);
	}
	
	
	@Ignore
	public void testGetAllRolesWithCallBack(){  
		try {
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		  	
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("Email");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("email",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Description");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("description",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("DirectMembersCount");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("directMembersCount",extractorlist);
			
			connectionInterface.getAllRoles(options, 2, null, new TestRestAppCallback());
		} catch (ExtractorConnectionException e) {
			e.printStackTrace();
		}
	}
	
	
	@Ignore
	 public void testGetAllUsersWithCallBack(){
		 try {
			 
			Map<String, List<ExtractorAttributes>> options = new LinkedHashMap<String, List<ExtractorAttributes>>();
		
			List<ExtractorAttributes>	extractorlist = new ArrayList<ExtractorAttributes>();
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("PrimaryEmail");//alert attribute
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("primaryEmail",extractorlist);//external attr
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Emails1Address");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("emails1.address",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Emails2Address");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("emails2.address",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("LastLoginTime");
			extractorAttributes.setUserAttr(true);
			//extractorAttributes.setFieldType(TYPE.DATE);
			//extractorAttributes.setFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			extractorlist.add(extractorAttributes);
			options.put("lastLoginTime",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Suspended");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("suspended",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("DirectMembersCount");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("directMembersCount",extractorlist);
				
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
			ExtractorAttributes extractorAttributes = new ExtractorAttributes("PrimaryEmail");//alert attribute
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("primaryEmail",extractorlist);//external attr
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("PhotoData");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("photoData",extractorlist);
			
			extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("Emails1Address");
			extractorAttributes.setUserAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("emails1.address",extractorlist);
			
			/*extractorlist = new ArrayList<ExtractorAttributes>();
			extractorAttributes = new ExtractorAttributes("DirectMembersCount");
			extractorAttributes.setRoleAttr(true);
			extractorlist.add(extractorAttributes);
			options.put("directMembersCount",extractorlist);*/
				
			Map<String,String> searchCriteria = new HashMap<String, String>();
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");;
			Date date=sdf.parse("01/01/2012 00:00:00");
			connectionInterface.getIncrementalUsersWithCallback(date,options, 100, searchCriteria, new TestRestAppCallback());
		
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
		            Map<String,Object> params=new LinkedHashMap<String,Object>();
					params.put("primaryEmail","vikas.sharma@alertenterprise.net");
		            params.put("photoData", imageBytes);
					
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
				file= new File("C:\\Users\\amit.ghildiyal\\Downloads\\test.jpg");
				
				byte[] imageBytes=connectionInterface.getImage("vikas.sharma@alertenterprise.net");
	        
		        FileOutputStream fos = new FileOutputStream(file);
		        fos.write(imageBytes);
		        fos.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
        assertTrue("Testing Getting Image", file.exists() && file.length()>0);
	}
	
	
}
