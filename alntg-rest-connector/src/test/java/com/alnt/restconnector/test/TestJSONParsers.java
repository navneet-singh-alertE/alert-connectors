package com.alnt.restconnector.test;

import java.io.*;
import java.util.*;
import org.junit.*;

import org.json.JSONObject;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.model.RoleInformation;
import com.alnt.restconnector.provisioning.utils.*;

/**
 * Test JSON parsers for provisioning and recon operations based on rest templates
 * 
 * @author amit.ghildiyal
 *
 */
public class TestJSONParsers
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public static void init() {

	}

	@Test
	public void testJSONReconParser() throws Exception{
		System.out.println("Testing JSON Recon Parser");
		Map conParams=new HashMap();
	    conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"UserRoles");
	    conParams.put(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT,"");
	    conParams.put(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT,"");
	    conParams.put(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT,"");
	    
	    String template = getContent("D:\\JsonTemplates\\GoogleApps\\GET_USERS0.json");
	    JSONObject responseJsonObject=new JSONObject(getContent("D:\\getuser.json"));
	    ParserFactory factory = ParserFactory.getInstance();
	    JSONReconParser parser = factory.getJSONReconParser();
	    parser.initialize(JSONReconParser.class.getClassLoader().getResourceAsStream("com/json/config/json-config.xml"));
	
	    parser.setValidating(false);
	    
	    parser.setResponse(responseJsonObject);
	    //parser.setRoleIdentifier("UserRoles");
	   // parser.setBadgeIdentifier("UserBadges");
	    Map finalJsonData = parser.parseJson(template);
	    System.out.println(finalJsonData);
}

	/**
	 * Parser for provisioning purposes based onn template
	 * @throws Exception
	 */
	@Test
	public void testJSONProvisioningParser() throws Exception {

		  System.out.println("Testing JSON Provisioning Parser");
		  Map conParams=new HashMap();
	      conParams.put(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY,"UserRoles");
	      conParams.put(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT,"");
	      conParams.put(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT,"");
	      conParams.put(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT,"");
	      
		  String template = getContent("D:\\JsonTemplates\\SalesForce\\GET_ROLES0.json");

	      ParserFactory factory = ParserFactory.getInstance();
	      JSONProvisioningParser parser = factory.getJSONProvisioningParser();
	      parser.initialize(JSONProvisioningParser.class.getClassLoader().getResourceAsStream("com/json/config/json-config.xml"));
	
	      parser.setValidating(false);
	      
	      parser.setConnectorParams(conParams);
	      parser.setParams(getParams());
	      //parser.setRoleIdentifier("UserRoles");
	     // parser.setBadgeIdentifier("UserBadges");
	      Map finalJsonData = parser.parseJson(template);
	
	      System.out.println(finalJsonData);
	}

	/**
	 * Get content from the file 
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
   String getContent(String fileName) throws Exception {
	    char[] charBuffer = new char[8192];
	    BufferedReader br=null;
	    StringBuilder builder=null;
	    try{
		     builder = new StringBuilder();
		    int offset = -1;
		     br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		    while ((offset = br.read(charBuffer)) > -1)
		    {
		      builder.append(charBuffer, 0, offset);
		    }
	    }finally {
	        if (br != null)
	          try
	          {
	            br.close();
	          }
	          catch (Exception localException) {
	          }
	      }
	    return builder.toString();
  }
  

	 public static Map getParams(){
			Map<String,Object> params=new LinkedHashMap<String,Object>();
		 	params.put("primaryEmail" , "amitg@alert.com");
			params.put("name.firstName","Amit");
			params.put("name.givenName", "Ghildiyal");
			params.put("ims", new String[]{"ims1","ims2","ims3"});
			
			params.put("addresses1.type","custom");
			params.put("addresses1.postalCode","600001");
			params.put("addresses1.region","Mani Majra");
			params.put("addresses1.country","USA");
			
			params.put("addresses2.type","custom");
			params.put("addresses2.postalCode","24901");
			params.put("addresses2.country","India");
			
			 List<IRoleInformation> roles= new ArrayList<IRoleInformation>();
			 
			 IRoleInformation role1=new RoleInformation();
			 role1.setName("Role1");
			 role1.setValidFrom(new java.util.Date());
			 role1.setValidTo(new java.util.Date());
			 role1.setDescription("Role1 Description");
			/* Map memberMap= new HashMap<String,List<String>>();
			 memberMap.put("key1", "value1");
			 memberMap.put("key2", "value2");
			 role1.setMemberData(memberMap);*/
			 roles.add(role1);
			 
			 IRoleInformation role2=new RoleInformation();
			 role2.setName("Role2");
			 role2.setValidFrom(new java.util.Date());
			 role2.setValidTo(new java.util.Date());
			 role2.setDescription("Role2 Description");
			 /*memberMap= new HashMap<String,List<String>>();
			 memberMap.put("key1", "value3");
			 memberMap.put("key2", "value4");*/
			 roles.add(role2);
			
			 List roleList=new ArrayList();
			 for(IRoleInformation role:roles){
				 Map<String,Object> rolesMap= new HashMap<String,Object>();
				 rolesMap.put("ROLE_NAME_INDEXES",role.getName() );
				 rolesMap.put("ROLE_DESCRIPTION_INDEXES",role.getDescription());
				 rolesMap.put("ROLE_VALID_FROM_INDEXES",role.getValidFrom());
				 rolesMap.put("ROLE_VALID_TO_INDEXES",role.getValidTo());
				 //rolesMap.putAll(role.getMemberData());
				 roleList.add(rolesMap);
			 }

			 params.put("UserRoles", roleList);
			 
			return params;
		}
}