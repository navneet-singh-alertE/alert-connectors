package com.alnt.restconnector.provisioning.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;

import com.alnt.access.certification.model.CertificationInformation;
import com.alnt.access.certification.model.ICertificationInformation;
import com.alnt.access.certification.model.IUserCertificationInformation;
import com.alnt.access.certification.model.IUserCertificationRequest;
import com.alnt.access.certification.model.UserCertificationInformation;
import com.alnt.access.certification.model.UserCertificationRequest;
import com.alnt.access.certification.services.ICertificationInterface;
import com.alnt.access.common.constants.IProvisoiningConstants;
import com.alnt.access.provisioning.model.IBadgeInformation;
import com.alnt.access.provisioning.model.IProvisioningAttributes;
import com.alnt.access.provisioning.model.IProvisioningResult;
import com.alnt.access.provisioning.model.IProvisioningStatus;
import com.alnt.access.provisioning.model.ISystemInformation;
import com.alnt.access.provisioning.services.IConnectionInterface;
import com.alnt.access.provisioning.utils.ConnectorUtil;
import com.alnt.extractionconnector.common.constants.IExtractionConstants;
import com.alnt.extractionconnector.common.constants.IExtractionConstants.MULTI_VALUES_OPERATION;
import com.alnt.extractionconnector.common.constants.IExtractionConstants.USER_ENTITLEMENT_KEY;
import com.alnt.extractionconnector.common.model.IUserInformation;
import com.alnt.extractionconnector.common.model.ReconExtensionInfo;
import com.alnt.extractionconnector.common.service.IAgentExtractionInterface;
import com.alnt.extractionconnector.common.service.IAgentRoleExtractionInterface;
import com.alnt.extractionconnector.common.service.IExtractionEventCallback;
import com.alnt.extractionconnector.common.service.IExtractionInterface;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;
import com.alnt.extractionconnector.user.model.MultiValue;
import com.alnt.extractionconnector.user.model.MultiValueDetails;
import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;
import com.alnt.restconnector.provisioning.commons.IRequestResponseHandler;
import com.alnt.restconnector.provisioning.commons.IResponse;
import com.alnt.restconnector.provisioning.commons.RequestResponseHandlersFactory;
import com.alnt.restconnector.provisioning.commons.XMLResponse;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import com.alnt.restconnector.provisioning.model.BadgeInformation;
import com.alnt.restconnector.provisioning.model.ProvisioningResult;
import com.alnt.restconnector.provisioning.model.RoleAuditInfo;
import com.alnt.restconnector.provisioning.model.RoleInformation;
import com.alnt.restconnector.provisioning.model.SystemInformation;
import com.alnt.restconnector.provisioning.model.UserInformation;
import com.alnt.restconnector.provisioning.utils.Utils;


/**
 * The generic restful web service connector aims to connect to systems which have published
 * their services. Make rest calls for provisioning and reconciliation activities. 
 * Usess basic HTTPClient methods like GET, PUT, POST, DELETE to do the provisioning and reconciliation.
 * 
 * @author amit.ghildiyal
 *
 */
public class RestConnectionInterface 
			implements IConnectionInterface, IExtractionInterface,ICertificationInterface ,IAgentExtractionInterface,IAgentRoleExtractionInterface{


	private IConnector sysConnector=null;
	private  String CLASS_NAME = "com.alnt.ws.provisioning.services.RestConnectionInterface";
	private Logger logger = LogManager.getLogger(CLASS_NAME);
	private IRequestResponseHandler requestResponseHandler=null;
	private String permRoleValidTo = "12/30/9999";

	private boolean logResponse=false;
	private boolean logURI=true;
	
	private Map<String, String> templatesFileCache=new HashMap();
	private String multiValSeperator=null;
	 
	public RestConnectionInterface(){
		
	}
	
	public RestConnectionInterface(Map<String, String> conParams){
		logger.debug(CLASS_NAME+" RestConnectionInterface(): Initializing the RestConnectionInterface");
		if(conParams.get("LogResponse")!=null && !"".equals(conParams.get("LogResponse"))){
			logResponse = new Boolean(conParams.get("LogResponse"));
		}
		if(conParams.get("LogURI")!=null && !"".equals(conParams.get("LogURI"))){
			logURI = new Boolean(conParams.get("LogURI"));
		}
		
		multiValSeperator = conParams.get("multiValueSeperator");
		if(multiValSeperator==null || multiValSeperator.isEmpty()){
			multiValSeperator = ";";
		}
		
		this.sysConnector=new RestConnector(conParams);
		init();
		

	}
	
	public void setConnectorParams(Map<String, String> conParams){
		logger.debug(CLASS_NAME+" RestConnectionInterface(): Setting connector params");
		
		if(this.sysConnector==null){
			this.sysConnector=new RestConnector();
		}
		if(conParams.get("LogResponse")!=null && !"".equals(conParams.get("LogResponse"))){
			logResponse = new Boolean(conParams.get("LogResponse"));
		}
		if(conParams.get("LogURI")!=null && !"".equals(conParams.get("LogURI"))){
			logURI = new Boolean(conParams.get("LogURI"));
		}
		
		multiValSeperator = conParams.get("multiValueSeperator");
		if(multiValSeperator==null || multiValSeperator.isEmpty()){
			multiValSeperator = ";";
		}
		
		this.sysConnector.setConnectorParams(conParams);
		init();
		setKeyStoreParameters(conParams);
	}
	
	public void init(){
		try{
			requestResponseHandler= RequestResponseHandlersFactory.getRequestResponseHandler
					(sysConnector.getFormat(), sysConnector.getConnectorParams());
			//this.testConnection();
		}catch(Throwable exc){
			logger.error(exc);
		}
	}
	/**
	 * Pass a new connector in case you wish to override default RestConnector.
	 * In case authentication mechanism changes for making connection to underlying
	 * system then pass the new Connector.
	 * 
	 * @param connector
	 */
	public void setConnector(IConnector connector){
		this.sysConnector=connector;
	}
	
	public IConnector getConnector(){
		return this.sysConnector;
	}
	
	
	
	/**
	 * In case this class is extended can pass the class name of derived class
	 * @param CLASS_NAME
	 */
	public void setClassName(String CLASS_NAME){
		this.CLASS_NAME=CLASS_NAME;
	}
	
	/**
	 * Make a call to get an access token. If access token is retrieved successfully
	 * the connection is made successfully as access token is returned only after authorized
	 * connection.
	 */
	@Override
	public boolean testConnection() throws Exception { 
		logger.debug(CLASS_NAME+" Testing connection...");
		
		String tokenURL = (String)sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_TOKEN_URL);
		
		//Test connection for OAUTH systems
		String token= sysConnector.getAccessToken();
		boolean status=  (token !=null && !token.isEmpty());
		logger.debug(CLASS_NAME+" Testing connection status="+status);
		return status;
	}

	
	/**
	 * Create user account. Makes a call to REST API with parameters to provision the user.
	 * 
	 */
	@Override
	public IProvisioningResult create(Long reqNo, List roles, Map parameters,
			List requestDetails, Map<String,String> attributeMapping) throws Exception {
	
			IProvisioningResult provResult= doOperation(parameters, "Create User",
				IProvisoiningConstants.PROV_ACTION_CREATE_USER,
				IProvisoiningConstants.CREATE_USER_SUCCESS, 
				IProvisoiningConstants.CREATE_USER_FAILURE );
			
			if(!provResult.isProvFailed()){
				if(parameters.containsKey(sysConnector.getImageAttributeName())){// if contains image data then provision image
					provisionImage(parameters,"Add Image",
							RestConstants.ADD_IMAGE,
							IProvisoiningConstants.CREATE_USER_SUCCESS,
							IProvisoiningConstants.CREATE_USER_FAILURE);
				}
			}
			
		return provResult;
	}


	@Override
	public IProvisioningResult create(Long requestNumber, List roles, Map parameters,
			List requestDetails, Map<String,String> attributeMapping, Map<String, String>validationReqAttrs)
			throws Exception {
		try {
			return create(requestNumber, roles, parameters, requestDetails, attributeMapping);		
		} catch (Exception e) {
			logger.error(CLASS_NAME+"create()", e);
			throw e;
		}
	}
	
	@Override
	public IProvisioningStatus getProvisioningStatus(Map<String, String> arg0)
			throws Exception {
		return null;
	}

	/**
	 * Check if user is locked
	 */
	@Override
	public boolean isUserLocked(String userId) throws Exception {
		boolean userLocked=false;
		try{
			Map params=new HashMap();
			params.put(sysConnector.getUserIdentifierKey(), userId);
			IResponse response=doReconOperation("User Locked","USER_LOCKED",params);
			String templateFileName=getTemplateFile("USER_LOCKED");
			Map<String,Object> responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,response.getResponseObject());
			
			String items[]=sysConnector.getLockedAttributeName().split(":");
			String  matchKey=null;
			String matchValue=null;
			if(items.length==2){
				matchKey=items[0];
				matchValue=items[1];
			}
			for(String key:responseMap.keySet()){
				if(key.equals(matchKey) && matchValue.equalsIgnoreCase(responseMap.get(key).toString()))
					userLocked=true;
			}
			}catch(Exception ex){
				logger.error(CLASS_NAME + " Error while performing isUserLocked operation.", ex);
			}
		
		//String[] items=sysConnector.getLockedAttributeName().split(":"); //eg: IsActive:false, Suspended:true
		//boolean status=checkUserAttribute("USER_LOCKED",userId,items[0] , items[1]);
		return userLocked;
	}

	/**
	 * Check if a user already exists in underlying system.
	 */
	@Override
	public ISystemInformation isUserProvisioned(String userId) throws Exception {
		ISystemInformation systemInfo=new SystemInformation();
		//boolean status=checkUserAttribute("USER_PROVISIONED",userId, null, null);
		boolean userProvisioned=false;
		try{
			Map params=new HashMap();
			params.put(sysConnector.getUserIdentifierKey(), userId);
			IResponse response=doReconOperation("Is User provisioned","USER_PROVISIONED",params);
			if(response!=null) {
				String templateFileName=getTemplateFile("USER_PROVISIONED");
				Map<String,Object> responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,response.getResponseObject());
				if(!responseMap.isEmpty())
					userProvisioned=true;
			}
				
			}catch(Exception ex){
				logger.error(CLASS_NAME + " Error while performing isUserProvisioned operation.", ex);
			}
		
		//String[] items=sysConnector.getLockedAttributeName().split(":"); //eg: IsActive:false, Suspended:true
		//boolean status=checkUserAttribute("USER_LOCKED",userId,items[0] , items[1]);
		systemInfo.setProvisioned(userProvisioned);
		return systemInfo;
	}

	

	@Override
	public List provision(Long arg0, String arg1, List arg2,
			List<IRoleInformation> arg3, Map arg4, List arg5,
			List<IRoleInformation> arg6, Map<String, String> arg7)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public IProvisioningResult delimitUser(Long arg0, List arg1, Map parameters,
			List arg3, String arg4, Map<String, String> arg5) throws Exception {
		return doOperation(parameters,"Delimit user",
				IProvisoiningConstants.PROV_ACTION_DELIMIT_USER,
				IProvisoiningConstants.DELIMIT_USER_SUCCESS, 
				IProvisoiningConstants.DELIMIT_USER_FAILURE);
	}

	@Override
	public List getAttributes() throws Exception {
		List<IProvisioningAttributes> provAttributes = new ArrayList<IProvisioningAttributes>();
		return provAttributes;
	}

	@Override
	public List getAttributes(Map arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getBadgeLastLocation(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getDetailsAsList(String arg0, String arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getExistingBadges(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setTaskId(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsProvisioning() {
		return false;
	}

	@Override
	public IProvisioningResult lock(Long reqNo, List arg1, Map parameters, List arg3,
			Map<String, String> arg4) throws Exception {
		
		return doOperation(parameters,"Lock user",
				IProvisoiningConstants.PROV_ACTION_LOCK_USER,
				IProvisoiningConstants.LOCK_USER_SUCCESS, 
				IProvisoiningConstants.LOCK_USER_FAILURE );
	}
	
	@Override
	public IProvisioningResult unlock(Long reqNo, List arg1, Map parameters,
			List arg3, Map<String, String> arg4) throws Exception {
		
		return doOperation(parameters,"Unlock user",
				IProvisoiningConstants.PROV_ACTION_UNLOCK_USER,
				IProvisoiningConstants.UNLOCK_USER_SUCCESS, 
				IProvisoiningConstants.UNLOCK_USER_FAILURE );
	}
	
	/**
	 * Deletes user account.
	 */
	@Override
	public IProvisioningResult deleteAccount(Long requestNumber, List roles,
			Map parameters, List requestDetails, Map<String,String> attributeMapping) throws Exception {
		return doOperation(parameters,"Delete user",
				IProvisoiningConstants.PROV_ACTION_DELETE_USER,
				IProvisoiningConstants.DELETE_USER_SUCCESS, 
				IProvisoiningConstants.DELETE_USER_FAILURE );
	}
	
	@Override
	public IProvisioningResult updatePassword(Long arg0, String userId,
			String oldPasswd, String newPasswd) throws Exception {
			
			Map requiredParameters= new HashMap();
			String userKey=sysConnector.getUserIdentifierKey();
			requiredParameters.put(userKey, userId);
			requiredParameters.put("NewPassword", newPasswd);
			return doOperation(requiredParameters,"Change user password",
					IProvisoiningConstants.PROV_ACTION_UPDATE_GRDN,
					IProvisoiningConstants.UPDATE_GRDN_SUCCESS, 
					IProvisoiningConstants.UPDATE_GRDN_FAILURE);
	}
	@Override
	public IProvisioningResult update(Long reqNo, List arg1, Map parameters,
			List arg3, Map<String, String> arg4) throws Exception {

		IProvisioningResult provResult= doOperation(parameters,"Update user",
				IProvisoiningConstants.PROV_ACTION_UPDATE_USER,
				IProvisoiningConstants.CHANGE_USER_SUCCESS, 
				IProvisoiningConstants.CHANGE_USER_FAILURE );
		// if contains image data then provision image
		if(!provResult.isProvFailed()
				&& parameters.containsKey(sysConnector.getImageAttributeName())){
			provisionImage(parameters,"Update Image",
					RestConstants.UPDATE_IMAGE,
					IProvisoiningConstants.CHANGE_USER_SUCCESS,
					IProvisoiningConstants.CHANGE_USER_FAILURE);
		}
		return provResult;
	}
	
	/**
	 * Get response object from 
	 * @param actionName
	 * @param provisioning_action_code
	 * @return
	 * @throws Exception
	 */
	public IResponse doReconOperation( String actionName, String provisioning_action_code,
			Map<String,Object> params) throws Exception {
		
		
		HttpMethod method =null;
		IResponse response=null;
		try {
			logger.debug(actionName+" operation starts...");
			
			if(params!=null)
				params.put(RestConstants.SYS_CON_ATTR_SYSTEM_NAME, sysConnector.getSystemName());
			String url=sysConnector.getInstanceUrl()+"/"
							+sysConnector.getConnectorParams().get(provisioning_action_code+"0");
			method = getHttpMethodFromURL(params,url);
			
			if(sysConnector.getAccessToken()!=null){
				method.addRequestHeader("Authorization","Bearer "+ sysConnector.getAccessToken());
			}
			
			sysConnector.executeRequest(method,logURI,logResponse);
			
			if (method.getStatusCode() == HttpStatus.SC_OK) {
					response = requestResponseHandler.handleResponse(method.getResponseBodyAsString());
					logger.debug( actionName + " operation performed successfully...");
			}
			
		} catch (Exception e) {
			logger.error(CLASS_NAME + " " + actionName +" not successful",e);
		} finally {
			sysConnector.releaseConnection(method);
		}
		logger.debug(actionName + " operation ends...");
		return response;
	}

	
	/**
	 * Get response object(certification status) by sending list of certifications for a given user
	 *  
	 * @param actionName
	 * @param provisioning_action_code
	 * @param userCert User Certification Object with details about user and certificates to be evaluated
	 * @return
	 * @throws Exception
	 */
	public IResponse doEvalCertOperation( String actionName, String provisioning_action_code, IUserCertificationRequest userCert) throws Exception {
		
		HttpMethod method =null;
		IResponse response=null;
		try {
			logger.debug(actionName+" operation starts...");

			String request=null;
			String url=sysConnector.getInstanceUrl()+"/"
							+sysConnector.getConnectorParams().get(provisioning_action_code+"0");
			

			Map<String,Object> params =new HashMap(userCert.getUserDetails());
			method = getHttpMethodFromURL(params,url);
			
			//Make list of certificates as key value pair as to be sent to a server
			
			int i=1;
			StringBuilder sb=new StringBuilder();
			for(String certName:userCert.getCertificationNames()){
				//Map<String,String> certMap= new HashMap();
				//params.put("\"Certifications"+(i++)+"."+"Name\"","\""+certName+"\"");
				sb.append(certName).append(";");
			}
			params.put("Certifications",sb.toString());
			params.put("template",getTemplateFile(provisioning_action_code));
			request=requestResponseHandler.handleRequest(params);
			
			method.addRequestHeader("Authorization","Bearer "+ sysConnector.getAccessToken());
			if(method instanceof PostMethod){
				((PostMethod)method).setRequestEntity(new StringRequestEntity(request,"application/"+sysConnector.getFormat(), null));
			}else if(method instanceof PutMethod){
				((PutMethod)method).setRequestEntity(new StringRequestEntity(request,"application/"+sysConnector.getFormat(), null));
			}
			sysConnector.executeRequest(method,logURI,logResponse);
			
			if (method.getStatusCode() == HttpStatus.SC_OK) {
					response = requestResponseHandler.handleResponse(method.getResponseBodyAsString());
					logger.debug( actionName + " operation performed successfully...");
			}else{
				logger.debug( actionName + " operation not performed successfully. Status code="+method.getStatusCode()+". "+method.getStatusText());
			}
			
		} catch (Exception e) {
			logger.error(CLASS_NAME + " " + actionName +" not successful",e);
		} finally {
			sysConnector.releaseConnection(method);
		}
		logger.debug(actionName + " operation ends...");
		return response;
	}
	/**
	 * Updating the user information or user attributes. Common method to handle various
	 * user update operations. 
	 * 
	 * @param actionName
	 * @param action_code
	 * @param success_code
	 * @param failure_code
	 */
	public IProvisioningResult doOperation( Map parameters, 
			String actionName,
			String provisioning_action_code, 
			String success_code, 
			String failure_code) throws Exception {
		
		IProvisioningResult provResult = null;
		HttpMethod method =null;
		
		try {
			logger.debug(actionName+" operation starts...");
			ConnectorUtil.logInputParamters(parameters, sysConnector.getExcludeLogAttributesList(), logger);
			parameters.put(RestConstants.SYS_CON_ATTR_SYSTEM_NAME, sysConnector.getSystemName());
			String userId=(String)parameters.get(sysConnector.getUserIdentifierKey());
			if(!IProvisoiningConstants.PROV_ACTION_CREATE_USER.equalsIgnoreCase(provisioning_action_code)){
				if (userId==null||userId.isEmpty()){
					provResult = createProvisioningResult(provisioning_action_code,
							failure_code , true, actionName+":Provisioning mapping is not set for user");
					return provResult;		
				}
			}
			String templateFile=getTemplateFile(provisioning_action_code);
			if(templateFile==null){//file does not exist hence operation not supported{
				provResult = createProvisioningResult(provisioning_action_code,
						failure_code , true, actionName + ":Provisioning action not supported since template file not found." );
				return provResult;		
			}
			String url=sysConnector.getInstanceUrl()+"/"
							+sysConnector.getConnectorParams().get(provisioning_action_code+"0");
			logger.debug(CLASS_NAME+"doOperation(): Request URL is: "+url);
			method = getHttpMethodFromURL(parameters,url);
			
			
			if(sysConnector.getAccessToken()!=null){
				method.addRequestHeader("Authorization","Bearer "+ sysConnector.getAccessToken());
			}
			
			String request=null;
			if(method instanceof PostMethod){
				parameters.put("template", templateFile);
				request=requestResponseHandler.handleRequest(parameters);
				((PostMethod)method).setRequestEntity(new StringRequestEntity(request,"application/"+sysConnector.getFormat(), null));
			}else if(method instanceof PutMethod){
				logger.debug(CLASS_NAME+"doOperation(): Inside PUT method");
				parameters.put("template", templateFile);
				request=requestResponseHandler.handleRequest(parameters);
				((PutMethod)method).setRequestEntity(new StringRequestEntity(request,"application/"+sysConnector.getFormat(), null));
			}
			
			if(!provisioning_action_code.equals(IProvisoiningConstants.PROV_ACTION_UPDATE_GRDN))
				logger.debug("doOperation(): Request XML is="+request);
			
			sysConnector.executeRequest(method,logURI,logResponse);
			
			provResult=getProvisioningResult(userId,method,provisioning_action_code,actionName
													,success_code,failure_code);
			
		} catch (Exception e) {
			logger.debug("doOperation(): Error in sending the request"+e.getMessage());
			provResult = createProvisioningResult(provisioning_action_code, failure_code, true, e.getMessage());
			logger.error(CLASS_NAME + " " + actionName +" not successful",e);
		} finally {
			sysConnector.releaseConnection(method);
		}
		logger.debug(actionName + " operation ends...");
		return provResult;
	}

	/**
	 * Create ProvisioningResult from rest calls 
	 * 
	 * @param userId User for which provisioning actions is performed
	 * @param method HTTPClient method 
	 * @param provisioningAction Create,Delete,Update user
	 * @param actionName
	 * @param successCode 
	 * @param failureCode
	 * @return
	 * @throws Exception
	 */
	public IProvisioningResult getProvisioningResult( String userId,
			HttpMethod method, String provisioningActionType,
			String actionName, String successCode, String failureCode) throws Exception {
		
		IProvisioningResult provResult=null;
		logger.debug(CLASS_NAME + "getProvisioningResult() StatusCode-->"+method.getStatusCode());
		if (method.getStatusCode() == HttpStatus.SC_OK 
				|| method.getStatusCode() == HttpStatus.SC_CREATED) {
			try {
				provResult = createProvisioningResult(provisioningActionType,
						successCode, false, actionName +" successful.");
				if(provisioningActionType.equalsIgnoreCase(IProvisoiningConstants.PROV_ACTION_CREATE_USER)){
					IResponse response =requestResponseHandler.handleResponse(method.getResponseBodyAsString());
					if (response.hasAttribute(sysConnector.getCreatedUserIdentifierKey())) {
							userId=(String)response.getAttributeValue(sysConnector.getCreatedUserIdentifierKey()).toString();
							provResult.setUserCreated(true);
							//provResult.setUserId(userId);
					}
				}
				
			} catch (Exception e) {
					provResult = createProvisioningResult(provisioningActionType, failureCode , true, e.getMessage());
					logger.error(CLASS_NAME + "Error operating on user.",e);
			}
		}else if (method.getStatusCode() == HttpStatus.SC_NO_CONTENT) {
					provResult = createProvisioningResult(provisioningActionType,
								successCode, false, actionName +" successful for \""+userId+"\"");
				
		}else{
			
				ByteBuffer inputBuffer = ByteBuffer.wrap(method.getResponseBody());
				String failMsg = new String(inputBuffer.array());
				logger.error(actionName +" not successful. " +failMsg);
				provResult = createProvisioningResult(provisioningActionType,
						failureCode, true, actionName +" not successful "+failMsg);
		}

		provResult.setUserId(userId);
		return provResult;
	}
	
	/**
	 *  Create provisioning result
	 *  
	 * @param provisioningAction
	 * @param msgCode
	 * @param isFailed
	 * @param msgDesc
	 * @return
	 */
	public IProvisioningResult createProvisioningResult(String provisioningActionType,String msgCode,Boolean isFailed, String msgDesc){
		IProvisioningResult provisioningResult = new ProvisioningResult();
		provisioningResult.setProvAction(provisioningActionType);
		provisioningResult.setMsgCode(msgCode);
		if(null != isFailed){
			provisioningResult.setProvFailed(isFailed);
		}
		provisioningResult.setMsgDesc(msgDesc);
		logger.debug(msgDesc);
		return provisioningResult;
	}

	/** Update image to user profile
	 * 
	 * @param imageData encoded data bytes of the image
	 * @return
	 */
	public String provisionImage(Map parameters, 
			String actionName,
			String provisioning_action_code, 
			String success_code, 
			String failure_code) throws Exception{

		HttpMethod method =null;
		boolean status=true;
		try {
			
			String userId=(String)parameters.get(sysConnector.getUserIdentifierKey());
			if(userId==null) return "UserId information not provided";
			String imageAttrName=sysConnector.getImageAttributeName();
			if(parameters.get(imageAttrName)==null) return "Image information is not available";
			
			/*byte[] imageBytes=(byte[])parameters.get(imageAttrName);
			String encodedImage = Utils.encodeValue(imageBytes);
			if(encodedImage==null) return false;
			parameters.put(imageAttrName,encodedImage);*/
			
			//Map paramsToSend=new HashMap();
			String templateFile=getTemplateFile(provisioning_action_code);
			if(templateFile==null){
				logger.debug("Provisioning Image method invoked but no template file exists. Exiting...");
				return "Image Template file is missing";
			}
			//paramsToSend.put(imageAttrName,encodedImage);
			IProvisioningResult provResult = doOperation(parameters,actionName,provisioning_action_code,success_code,failure_code);
			if(provResult.isProvFailed()){
				return provResult.getMsgDesc();
			}
			
		} catch (Exception e) {
			logger.error(CLASS_NAME + " Provisioning image not successful",e);
			status=false;
			throw e;
		} finally {
			sysConnector.releaseConnection(method);
		}
		logger.debug("Provision Image operation ends. Image Provisioned= "+ status);
		return null;
	}
	
	
	/** Get Images
	 * 
	 * @param imageData encoded data bytes of the image
	 * @return
	 */
	public byte[] getImage(String userId) {

		GetMethod method =null;
		boolean status=false;
		byte[] imageBytes=null;
		try {
			//String imageAttrName=sysConnector.getImageAttributeName();
			Map params=new HashMap();
			params.put(sysConnector.getUserIdentifierKey(), userId);
			IResponse response=doReconOperation("Get Image","GET_IMAGE",params);
			List<Object> images= response.getValues(sysConnector.getReconImagesTag());

			String templateFileName=getTemplateFile("GET_IMAGE");
			if (templateFileName==null) return null;
			for(Object imageObj:images){
				Map<String,Object> responseMap=null;
					try{
						responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,imageObj);
						String imageAttribute=sysConnector.getImageAttributeName();
						Object value=responseMap.get(imageAttribute);
						if(value!=null){
							imageBytes=Utils.decodeValue((String)value);
							return imageBytes;//right now return first image only
						}
					}catch(Exception ex){
						logger.error("Process Role Information>>Could not generate responseMap from templateFile.",ex);
					}
			}
		} catch (Exception e) {
			logger.error(CLASS_NAME + "Retrieving image not successful",e);
		} finally {
			sysConnector.releaseConnection(method);
		}
		logger.debug("Image retrieval operation ends");
		return imageBytes;
	}

	public IProvisioningResult updateUserInformation(Long arg0, List arg1,
			Map arg2, List arg3, Map<String, String> arg4) throws Exception {
		IProvisioningResult provResult = new ProvisioningResult();
			return provResult;
	}


	@Override
	public IProvisioningResult addBadge(Long requestNumber, List roles,	Map parameters, 
			List requestDetails, Map<String,String> attMapping)	throws Exception{

			IBadgeInformation badgeInfo = new BadgeInformation();
			IProvisioningResult result= doOperation(parameters, "Add Badge",
				RestConstants.PROV_ACTION_ASSIGN_BADGE,
				IProvisoiningConstants.CREATE_USER_SUCCESS, 
				IProvisoiningConstants.CREATE_USER_FAILURE);
			
			result.setProvAction(IProvisoiningConstants.PROV_ACTION_CREATE_USER);
	        result.setBadgeInformation(badgeInfo);
			return result;
	}
	
	@Override
	public IProvisioningResult 	activateBadge(Long requestNumber, List roles,	Map parameters, 
			List requestDetails,	Map<String,String> attMapping)	throws Exception{

		IBadgeInformation badgeInfo = new BadgeInformation();
		IProvisioningResult result= doOperation(parameters,"Activate Badge",
				RestConstants.PROV_ACTION_ACTIVATE_BADGE,
				IProvisoiningConstants.UNLOCK_USER_SUCCESS, 
				IProvisoiningConstants.UNLOCK_USER_FAILURE);
		 result.setProvAction(IProvisoiningConstants.PROV_ACTION_UNLOCK_USER);
	     result.setBadgeInformation(badgeInfo);
	     return result;
	}
	
	@Override
	public IProvisioningResult deActivateBadge(Long requestNumber, List roles,	Map parameters, List requestDetails,	
			Map<String,String> attMapping)	throws Exception{

        IBadgeInformation badgeInfo = new BadgeInformation();
		IProvisioningResult result= doOperation(parameters,"Deactivate Badge",
				RestConstants.PROV_ACTION_DEACTIVATE_BADGE,
				IProvisoiningConstants.LOCK_USER_SUCCESS, 
				IProvisoiningConstants.LOCK_USER_FAILURE);
		result.setProvAction(IProvisoiningConstants.PROV_ACTION_LOCK_USER);
        result.setBadgeInformation(badgeInfo);
		return result;
	}

	@Override
	public IProvisioningResult removeBadge(Long requestNumber, List roles,	Map parameters,
			List requestDetails,	Map<String,String> attMapping)	throws Exception{
		IBadgeInformation badgeInfo = new BadgeInformation();
		IProvisioningResult  result= doOperation(parameters,"Remove Badge",
				RestConstants.PROV_ACTION_REMOVE_BADGE,
				IProvisoiningConstants.DELETE_USER_SUCCESS, 
				IProvisoiningConstants.DELETE_USER_FAILURE);
		
		result.setProvAction(IProvisoiningConstants.PROV_ACTION_DELETE_USER);
        result.setBadgeInformation(badgeInfo);
		return result;
	}
	

	@Override
	public IProvisioningResult addTempBadge(Long requestNumber, List roles,	Map parameters, 
			List requestDetails, Map<String,String> attMapping) throws Exception {
		return addBadge(requestNumber,roles,parameters,requestDetails,attMapping);
	}

	/**
	 * Change access method would get existing roles assigned to a user. Based on roles to be added/removed 
	 * cumulative role list would then be sent to the underlying system.
	 * 
	 */
	@Override
	public IProvisioningResult changeAccess(Long requestNumber, List provisionRoles, Map parameters,
			List requestDetails, List<IRoleInformation> deprovisionRoles, Map<String,String> attributeMapping)
			throws Exception {
		
		IProvisioningResult provResult = new ProvisioningResult();
		try{
			
			logger.debug("Change of access starts..."+parameters);
			String errorInRoles=null;
			List<IRoleInformation> invalidRoles = new ArrayList<IRoleInformation>();
			
			provResult.setProvAction(IProvisoiningConstants.PROV_ACTION_CHANGE_ROLES);
			List<RoleAuditInfo> roleAuditInfoList = new ArrayList();
			String userId=(String)parameters.get(sysConnector.getUserIdentifierKey());
			
			if(userId == null || userId.isEmpty()){
					provResult = createProvisioningResult(IProvisoiningConstants.PROV_ACTION_CHANGE_ROLES,
							IProvisoiningConstants.ASSIGN_ROLES_FAILURE, true, 
							"Change access could not be performed as UserId is not specified");
					return  provResult;
			}
			
			
			List<IRoleInformation> assignedRoles=this.getAllRolesForUser(parameters);
			
			List<IRoleInformation> tempList=new ArrayList();
			if (deprovisionRoles != null && deprovisionRoles.size() > 0) {
				for(IRoleInformation assignedRole:assignedRoles){
					for(IRoleInformation removeRole:deprovisionRoles){
						//assignedRole.getMemberData().get(IExtractionConstants.TECHNICAL_ROLE_NAME)
						if(assignedRole.getName().equals(removeRole.getName())){
							tempList.add(assignedRole);
							break;
						}
					}
				
				}
			}
			
			assignedRoles.removeAll(tempList);
			tempList.clear();
			
			//Provision roles
			if (provisionRoles != null && provisionRoles.size() > 0) {

				for(IRoleInformation provisionRole:((ArrayList<IRoleInformation>)provisionRoles)){
					boolean found=false;
					for(IRoleInformation assignedRole:assignedRoles){
						if(assignedRole.getName().equals(provisionRole.getName())){
							if(provisionRole.getValidFrom()!=null)
								assignedRole.setValidFrom(provisionRole.getValidFrom());
							if(provisionRole.getValidTo()!=null)
								assignedRole.setValidTo(provisionRole.getValidTo());						
							found=true;//skip since role already exists
							break;
						}
					}
					if(!found){
						tempList.add(provisionRole);
					}
				}
			}
			assignedRoles.addAll(tempList);
			if(!assignedRoles.isEmpty()){
				roleAuditInfoList=provisionRoles(userId, assignedRoles, parameters, invalidRoles);
			}
			
			provResult.setRoleList(roleAuditInfoList);
			if(!invalidRoles.isEmpty())
				provResult.setInvalidRoles(invalidRoles);
			provResult = createProvisioningResult(IProvisoiningConstants.PROV_ACTION_CHANGE_ROLES,
					IProvisoiningConstants.ASSIGN_ROLES_SUCCESS , false, "Change Access action performed. " +
							"Total "+ roleAuditInfoList.size()+" roles provisioned/deprovisioned");
			logger.debug("End of change access operation");
		}catch (Exception e) {
			provResult=createProvisioningResult(IProvisoiningConstants.PROV_ACTION_CHANGE_ROLES,
					IProvisoiningConstants.ASSIGN_ROLES_FAILURE, true, "Error in change of access :" + e.getMessage());
			logger.error("Error in change of access :" + e.getMessage(),e);
		}
		
		return provResult;
	}
	
	/**
	 * Get all roles assigned to a user
	 * 
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public List<IRoleInformation> getAllRolesForUser(Map parameters)  throws Exception{
		List<IRoleInformation> roleListForUser=new ArrayList();
		try{
			logger.debug(CLASS_NAME + " Start of getAllRolesForUser()");
			IResponse response= doReconOperation( "Get Specific User","GET_SPECIFIC_USER", parameters);
			List<Object> users= response.getValues(sysConnector.getReconUsersTag());
		 	Object userObj=users.get(0);
			
			Object userRoleObj=requestResponseHandler.getAttributeValue(userObj, sysConnector.getRoleIdentifierKey());
			ArrayList<String> roleNamesList=new ArrayList();
			if(userRoleObj instanceof String){
				roleNamesList.add((String)userRoleObj);
			}else if(userRoleObj instanceof ArrayList){
				ArrayList list=(ArrayList)userRoleObj;
				roleNamesList.addAll(0,list);
			}
			
			for(String roleName: roleNamesList){
				IRoleInformation roleInformation= new RoleInformation();
				roleInformation.setRoleType(IExtractionConstants.ROLE_TYPE_SINGLE);
				roleInformation.setName(roleName);
				//List<String> values = new ArrayList<String>();
				//values.add(technicalRoleName);
				//roleInformation.getMemberData().put(IExtractionConstants.TECHNICAL_ROLE_NAME,values);
				roleListForUser.add(roleInformation);
			}
			
			//IUserInformation userInformation =processUser(userObj, options, "GET_SPECIFIC_USER", "GET_USER_ROLE", parameters);
			//userInformation.getEntitlements().get(USER_ENTITLEMENT_KEY.ROLES.toString());
			logger.debug(CLASS_NAME+" getAllRolesForUser() of RestConnectionInterface() : End");
		}catch (Exception e) {
			logger.error(CLASS_NAME+" getAllRolesForUser() : execution failed ",e);
		}
		return roleListForUser;
	}

	/**
	 * Provision roles for  a user
	 * 
	 * @param userId Userid for which roles need to be provisioned
	 * @param roles List of roles to be provisioned
	 * 
	 * @param invalidRoles
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RoleAuditInfo> provisionRoles( String userId, List<IRoleInformation> roles, Map parameters, List<IRoleInformation> invalidRoles)
	throws Exception {
	
		logger.debug(CLASS_NAME+" provisionRoles(): Started provisioning roles...");
		List<RoleAuditInfo> roleAuditInfoList = new ArrayList();
		List<Map> roleList=createRoleListForTemplate(roles,roleAuditInfoList);
		
		parameters.put(sysConnector.getUserIdentifierKey(), userId);
		parameters.put(sysConnector.getRoleIdentifierKey(), roleList);
		
		IProvisioningResult result=doOperation(parameters, "Add roles",
					IProvisoiningConstants.ADD_ROLE+"_ROLE",
					IProvisoiningConstants.ASSIGN_ROLES_SUCCESS, 
					IProvisoiningConstants.ASSIGN_ROLES_FAILURE );
		
		
		for(IRoleInformation role:roles){
			if(!result.isProvFailed()){
				 RoleAuditInfo roleAuditInfo= new RoleAuditInfo();
				 roleAuditInfo.setRoleName(role.getName());					
				 roleAuditInfo.setAction(IProvisoiningConstants.ADD_ROLE);
				 roleAuditInfo.setEnterpriseRoleName(role.getEnterpriseRoleName());
				 roleAuditInfoList.add(roleAuditInfo);
			}else{
				invalidRoles.add(role);
			}
		}
		logger.debug(CLASS_NAME+" proivsionRoles(): Finished provisioning roles...");
		return roleAuditInfoList;
	}

	
	/**
	 * DO NOT REMOVE COMMENTED CODE FOR DEPROVISIONING, MIGHT BE REQUIRED IN FUTURE
	 */
	/*public List<RoleAuditInfo> deprovisionRoles( String userId, List<IRoleInformation> deprovisionRoles, List<IRoleInformation> invalidRoles)
								throws Exception {
		
				logger.debug(CLASS_NAME + "Deprovisioning of the roles starts...");

				List<RoleAuditInfo> roleAuditInfoList = new ArrayList<RoleAuditInfo>();
				//List<String> existingRoles = getRolesForUserAsString(userId);
				Iterator<IRoleInformation> rolesIterator = deprovisionRoles.iterator();
				
				while (rolesIterator.hasNext()) {
					 IRoleInformation role = rolesIterator.next();
					 
					 RoleAuditInfo roleAuditInfo= new RoleAuditInfo();
					 roleAuditInfo.setRoleName(role.getName());					
					 roleAuditInfo.setAction(IProvisoiningConstants.REMOVE_ROLE);
					 roleAuditInfo.setEnterpriseRoleName(role.getEnterpriseRoleName());

					Map parameters=new HashMap();
					parameters.put(sysConnector.getUserIdentifierKey(), userId);
					parameters.put(sysConnector.getRoleIdentifierKey(), role.getName());
					boolean deprovisionStatus=deprovisionRole(parameters);
					
					if(!deprovisionStatus){
						invalidRoles.add(role);
						logger.debug(CLASS_NAME + "Role not found : "+ role.getName());
					}else{
						roleAuditInfoList.add(roleAuditInfo);
					}
				}
			logger.debug(CLASS_NAME + "Change Access::Deprovisioning roles ends.");
		
		return roleAuditInfoList;
	}
	
	
	public boolean deprovisionRole(Map parameters){
		IProvisioningResult result=null;;
		try {
			result = doOperation(parameters, "Deprovision role",
					IProvisoiningConstants.REMOVE_ROLE+"_ROLE",
					IProvisoiningConstants.ASSIGN_ROLES_SUCCESS, 
					IProvisoiningConstants.ASSIGN_ROLES_FAILURE );
		} catch (Exception e) {
			return false;
		}
		return !result.isProvFailed();
	
	}*/
	
	
	/**
	 * Make role parameter map from the all the role set. This is the comprehensive list 
	 * 
	 * @param roles
	 * @return
	 */
	List<Map> createRoleListForTemplate(List<IRoleInformation> roles, List<RoleAuditInfo> roleAuditInfoList){
		List<Map> roleList=new ArrayList();
		Iterator<IRoleInformation> rolesIterator=roles.iterator();
		while (rolesIterator.hasNext()) {
				IRoleInformation role =  rolesIterator.next();
				logger.debug(CLASS_NAME + 
						 " Adding role : "+role.getName()+" with validity "+ role.getValidFrom()+" to "+ role.getValidTo()+"");
				
				RoleAuditInfo roleAuditInfo= new RoleAuditInfo();
				roleAuditInfo = new RoleAuditInfo();
				roleAuditInfo.setRoleName(role.getName());
				roleAuditInfo.setValidFrom(role.getValidFrom());
				roleAuditInfo.setValidTo(role.getValidTo());
				roleAuditInfo.setAction(IProvisoiningConstants.ADD_ROLE);	
				roleAuditInfoList.add(roleAuditInfo);
				
				//Add role information in format in accordance with template.
				Map<String,Object> roleMap= new HashMap<String,Object>();
				roleMap.put("ROLE_NAME",role.getName() );
				roleMap.put("ROLE_DESCRIPTION",role.getDescription());
				roleMap.put("ROLE_VALID_FROM",role.getValidFrom());
				roleMap.put("ROLE_VALID_TO",role.getValidTo());
				for(String key:role.getMemberData().keySet()){
					List<String> list=role.getMemberData().get(key);
					roleMap.put("ROLE_"+key.toUpperCase(),list.get(0));
				}
				
				//In case template has arrays add indexes
				roleMap.put("ROLE_NAME_INDEXES",role.getName() );
				roleMap.put("ROLE_DESCRIPTION_INDEXES",role.getDescription());
				roleMap.put("ROLE_VALID_FROM_INDEXES",role.getValidFrom());
				roleMap.put("ROLE_VALID_TO_INDEXES",role.getValidTo());
				
				for(String key:role.getMemberData().keySet()){
					List<String> list=role.getMemberData().get(key);
					roleMap.put("ROLE_"+key.toUpperCase()+"_INDEXES",list.get(0));
				}
				roleList.add(roleMap);
		}
		return roleList;
	}
	

	@Override
	public IProvisioningResult changeBadge(Long requestNumber, List roles,	Map parameters, 
			List requestDetails, Map<String,String> attMapping) throws Exception {
		
		IBadgeInformation badgeInfo = new BadgeInformation();
		IProvisioningResult result= doOperation(parameters, "Update Badge",
					RestConstants.PROV_ACTION_UPDATE_BADGE,
					IProvisoiningConstants.CHANGE_USER_SUCCESS, 
					IProvisoiningConstants.CHANGE_USER_FAILURE);
		result.setProvAction(IProvisoiningConstants.PROV_ACTION_CHANGE_USER);
        result.setBadgeInformation(badgeInfo);
		return result;
	}

	@Override
	public IProvisioningResult changeBadgeRoles(Long arg0, List arg1, Map arg2,
			List arg3, Map<String, String> arg4) throws Exception {
		return null;
	}

	@Override
	public List getAllRoles(String arg0) throws ExtractorConnectionException {
		return null;
	}

	@Override
	public List getRoles(String arg0) throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getRolesForUser(String arg0) throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.alnt.extractionconnector.common.model.IRoleInformation> getAllRoles(
			Map<String, List<ExtractorAttributes>> arg0, int arg1, int arg2,
			Map<String, String> arg3) throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get all roles from the underlying system
	 * 
	 * @param options Attributes to extract from the roles
	 * @param fetchSize Size before callback method is invoked to send roles
	 * @param callback Callback method to call on reaching fetchsize
	 */

	public void getAllRoles(Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria,
			ISearchCallback callback,IExtractionEventCallback extractionEventCallback) throws ExtractorConnectionException {

		try{
			logger.debug(CLASS_NAME
					+ " getRoles() of RestConnectionInterface() : Start of getRoles(), "
					+ "going with fetch Size: " + fetchSize);
	
			IResponse response = doReconOperation("Get Roles", "GET_ROLES", null);
			
			String templateFileName = getTemplateFile("GET_ROLES");
			if (templateFileName == null)
				return;
	
			List<Object> roles = response.getValues(sysConnector.getReconRolesTag());
			processRoles(roles, options, fetchSize, callback,extractionEventCallback,templateFileName);
			logger.debug(CLASS_NAME+" getAllRoles() of RestConnectionInterface() : End of getIncrementalRoles(), ");
		}catch (Exception e) {
			logger.error(CLASS_NAME+" getAllRoles() : execution failed ",e);
		}
	}
	
	
	/**
	 * Get incremental roles from last run time. These are the roles that have been added/modified
	 * since last runtime
	 * 
	 * @param lastRunDate The last run date since which the roles were modified/added
	 * @param options Attributes to extract from the roles
	 * @param fetchSize Size before callback method is invoked to send roles
	 * @param callback Callback method to call on reaching fetchsize
	 */
	
	public void getIncrementalRoles(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> arg3, ISearchCallback callback,IExtractionEventCallback extractionEventCallback)
			throws ExtractorConnectionException {
		
		try{
			logger.debug(CLASS_NAME+" getIncrementalRoles() of RestConnectionInterface() : " +
					"Start of getIncrementalRoles(), " + "going with fetch Size: " + fetchSize + ", LastRunDate:" + lastRunDate);
			List<IRoleInformation> rolesList = new ArrayList<IRoleInformation>();
			
			Map params=parseRunTimeField(sysConnector.getRoleLastRunTimeField(), sysConnector.getReconDateFormat(), lastRunDate);
			
			int index=0;
			
			IResponse response=doReconOperation("Get Incremental Roles","GET_INCREMENTAL_ROLES",params);
			String templateFileName=getTemplateFile("GET_INCREMENTAL_ROLES");
			if (templateFileName==null) return ;
			
			Map<String, Object> userAttributes = new HashMap();
			
			List<Object> roles=response.getValues(sysConnector.getReconRolesTag());
			processRoles(roles, options, fetchSize, callback,extractionEventCallback,templateFileName);
			logger.debug(CLASS_NAME+" getIncrementalRoles() of RestConnectionInterface() : End of getIncrementalRoles(), ");
		}catch (Exception e) {
			logger.error(CLASS_NAME+" getIncrementalRoles() : execution failed ",e);
		}
	}
	
	/**
	 * Process Roles and populate Role objects for recon operation
	 * 
	 * @param roles List of extracted role objects from the response
	 * @param options Extractor attributes to extract the required role fields only
	 * @param fetchSize Size of roles before the callback method is called
	 * @param templateFileName Template filename for role
	 */
	public void processRoles(List<Object> roles, Map<String, List<ExtractorAttributes>> options, 
			int fetchSize,ISearchCallback callback, IExtractionEventCallback extractionEventCallback, String templateFileName){

			List<IRoleInformation> rolesList = new ArrayList<IRoleInformation>();
			try{
				Map<String,Object> responseMap=null;
				int index = 0;
				
				for (int i = 0; i < roles.size(); i++) {
					try{
						Object roleObj=roles.get(i);
						responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,roleObj);
					}catch(Exception ex){
						logger.error("Process Role Information>>Could not generate responseMap from templateFile.",ex);
					}
					
					if(responseMap==null) continue;//should not be the case
					
					IRoleInformation roleInformation = processRole(responseMap, options);
					if(extractionEventCallback!=null)
					{
						extractionEventCallback.sendEvent(roleInformation);
					}
					else{
						rolesList.add(roleInformation);
						
						if(index>=fetchSize-1){
							//if(callback==null) continue;
							try{
								logger.debug(CLASS_NAME+" processRoles(): Calling callback:");
								callback.processSearchResult(rolesList);
								logger.debug(CLASS_NAME+" processRoles(): back from callback:");
							}catch(Exception e){
								logger.error(CLASS_NAME+" processORoles() : execution failed while calling processSearchResult() for greater index value ",e);
							}
							index=0;
							rolesList = new ArrayList<IRoleInformation>();
						}else{
							index++;
						}
					}
			}
			
			if(index!=0){
				///if(callback!=null){
					try{
						logger.debug(CLASS_NAME+" processRoles(): Calling callback for remaining items#:"+index);
						callback.processSearchResult(rolesList);
					}catch(Exception e){
						logger.error(CLASS_NAME+" processRoles() : execution failed while calling processSearchResult() for index!=0 ",e);
					}
				//}
		}
		
		}catch (Exception e) {
			logger.error(CLASS_NAME+" processRoles() : execution failed ",e);
		}
		//return rolesList;
	}
	
	
	/**
	 * Process individual role from response sent by external system during recon.
	 * 
	 * @param responseMap The map equivalent of the role response sent by underlying system
	 * @param options Extraction attributes for role
	 * @return
	 */
	public IRoleInformation processRole(Map responseMap, Map<String, List<ExtractorAttributes>> options){
		IRoleInformation roleInformation = null;
		try{
			
			String reconDateFormat=sysConnector.getReconDateFormat();
			SimpleDateFormat sdf =new SimpleDateFormat(reconDateFormat);
			
			roleInformation= new RoleInformation();
			roleInformation.setRoleType(IExtractionConstants.ROLE_TYPE_SINGLE);
			Object value=responseMap.get("ROLE_NAME");
			if(value!=null) roleInformation.setName((String)value);
			value=responseMap.get("ROLE_DESCRIPTION");
			if(value!=null) roleInformation.setDescription((String)responseMap.get("ROLE_DESCRIPTION"));
			value=responseMap.get("ROLE_VALID_FROM");
			if(reconDateFormat!=null && value!=null){
				Date date=sdf.parse((String)value);
				roleInformation.setValidFrom(date);
			}
			value=responseMap.get("ROLE_VALID_TO");
			if(reconDateFormat!=null && value!=null){
				Date date=sdf.parse((String)value);
				roleInformation.setValidTo(date);
			}
			
			//Set Role Member Data
			Map<String, List<String>> memberData = new HashMap<String, List<String>>();
			List<String> values = new ArrayList<String>();
			String technicalRoleName=(String)responseMap.get(IExtractionConstants.TECHNICAL_ROLE_NAME);
			if(technicalRoleName==null){
				technicalRoleName=""+responseMap.get(sysConnector.getRoleIdentifierKey());
			}
			values.add(technicalRoleName);
			memberData.put(IExtractionConstants.TECHNICAL_ROLE_NAME,values);

			String externalDateFormat =  sysConnector.getReconDateFormat();
			for(String extractAttrName:options.keySet()){
					List<ExtractorAttributes> extractionAttributes = options.get(extractAttrName);
					for (ExtractorAttributes extractorAttribute : extractionAttributes) {
						if(extractorAttribute.isRoleAttr()){
							if(responseMap.get(extractAttrName)!=null){
								values = new ArrayList<String>();
								values.add((String)format(extractorAttribute, extractAttrName,responseMap.get(extractAttrName),externalDateFormat));
								memberData.put(extractorAttribute.getAttributeName(), values);
							}
						}
					}
			}
			
			roleInformation.setMemberData(memberData);
		}catch (Exception e) {
			logger.error(CLASS_NAME+" processRole() : execution failed while calling processRole()",e);
		}
		return roleInformation;
	}
	
	@Override
	public List<com.alnt.extractionconnector.common.model.IRoleInformation> getIncrementalRoles(
			Date arg0, Map<String, List<ExtractorAttributes>> arg1, int arg2,
			int arg3, Map<String, String> arg4)
			throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void searchRoles(String arg0, ISearchCallback arg1)
			throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		
	}

	
	
	
	/**
	 * Check if user exists . If attributeName is not null then check if the particular attribute exists
	 * and if the value is equal to value to compare field
	 * 
	 * @param userId
	 * @return
	 */
	public  boolean checkUserAttribute(String provisioningActionCode, String userId, String attributeName, String valueToCompare) {
		HttpMethod method =null;
		try {
			if(userId==null || userId.isEmpty()){
				return false;
			}
			Map params=new HashMap();
			params.put(sysConnector.getUserIdentifierKey(), userId);
			String url=sysConnector.getInstanceUrl()+"/"
				+sysConnector.getConnectorParams().get(provisioningActionCode+"0");
			method = getHttpMethodFromURL(params,url);
			method.addRequestHeader("Authorization","Bearer " + sysConnector.getAccessToken());
			
			sysConnector.executeRequest(method,logURI,logResponse);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
					logger.debug( "User "+userId+ " exists...");
					if(attributeName!=null){
						IResponse response=requestResponseHandler.handleResponse(method.getResponseBodyAsString());
						if(response.hasAttribute(attributeName)){
							String setValue=response.getAttributeValue(attributeName).toString();
							boolean status= valueToCompare.equals(setValue);
							logger.debug(userId + ", Attribute Name:"+ attributeName + ", Set Value:" + setValue);
							return status;
						}else {
							logger.debug(userId + ", Attribute Name:"+ attributeName + " not found.");
							return false;
						}
					}
					logger.debug( "User \""+userId+ "\" exists.");
					return true;
			}else{
				logger.debug( "User \""+userId+ "\" does not exist.");
			}
		} catch (Exception e) {
			logger.error(CLASS_NAME + "Error retrieving user details",e);
		} finally {
			sysConnector.releaseConnection(method);
		}
		
		return false;
	}

	@Override
	public List<IUserInformation> getAllUsers(
						Map<String, List<ExtractorAttributes>> arg0, int arg1, int arg2,
						Map<String, String> arg3) throws ExtractorConnectionException {
		return null;
	}
	
	/**
	 * Create UserInformatijon object from the  object returned in the response
	 * during recon operation
	 * 
	 * @param userObj User information returned in the response
	 * @param options Extraction attributes from user
	 * @param userProvisioningAction 
	 * @param roleProvisioningAction 
	 * 
	 * @return
	 */
	public IUserInformation processUser(Object userObj, Map<String, List<ExtractorAttributes>> options,
			String userProvisioningAction, String roleProvisioningAction, 
			String badgeProvisioningAction, Map params){
		
		IUserInformation userInformation = null;
		String userID=null;
		
		try{
			userInformation = new UserInformation();
			userID=(String)requestResponseHandler.getAttributeValue(userObj, sysConnector.getUserIdentifierKey());
			logger.debug(CLASS_NAME+" Processing User Information for : " + userID);
			String templateFileName=getTemplateFile(userProvisioningAction);
			Map<String, Object> userAttributes = new HashMap();
			Map<String,Object> responseMap=null;
			try{
				responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,userObj);
			}catch(Exception ex){
				logger.error(ex);
			}
			
			//String userObjString=userObj.toString();
			if(responseMap!=null){
				for(String fieldName:options.keySet()){
					Object fieldValue=null;
					try{
						if(fieldName.equals(sysConnector.getImageAttributeName())){
							byte[] imageBytes=getImage(userID);
							if(imageBytes!=null && imageBytes.length>0)
								fieldValue=imageBytes;
							else 
								continue;
						}else{
							fieldValue=responseMap.get(fieldName);
						}
					}catch(Exception ex){
						logger.error(ex);
					}
					
					userAttributes = convertFormat(fieldName, fieldValue, options, userAttributes, "User");
				}
			}
			
			//Get All entitilements for a user
			Map<String,Map<String,List<Map<String,Object>>>> allEntitlements = new HashMap<String, Map<String,List<Map<String,Object>>>>();
			
			//In some cases roles may not be applicable for a user like Soco connector.
			//Object userRolesObj=requestResponseHandler.getAttributeValue(userObj, sysConnector.getRoleIdentifierKey());
			Map entitlements=getUserRoleEntitlements(userObj, options, roleProvisioningAction, params);
			if(entitlements!=null){
				allEntitlements.put(userID, entitlements);
			}
			if(sysConnector.getBadgeIdentifierKey()!=null && !sysConnector.getBadgeIdentifierKey().isEmpty()){
				Object userBadgesObj=requestResponseHandler.getAttributeValue(userObj, sysConnector.getBadgeIdentifierKey());
				entitlements=getUserBadgeEntitlements(userBadgesObj,options, badgeProvisioningAction, params);
				if(entitlements!=null){
					allEntitlements.put(userID, entitlements);
				}
			}
			//	allEntitlements.put(userID, getUserBadgeEntitlements());
			
			userInformation.setEntitlements(allEntitlements);
			userInformation.setUserDetails(userAttributes);
			logger.debug(CLASS_NAME+" Exiting user processing ");
			logger.debug(CLASS_NAME+" Processed user details successfully for: " + userID);
		}catch (Exception ex) {
			logger.error(CLASS_NAME+" Error in processing user information for user: " + userID,ex);
		}
		return userInformation;
	}
	
	/**
	 * Get Roles related information for a given user
	 * 
	 * @param userRolesObj Object containing user role information
	 * @param options Attributes to be extracted for roles 
	 * @param roleProvisioningAction 
	 * @param params
	 * @param responseMap
	 * 
	 * @return User Role Entitlements
	 */
	public Map<String, List<Map<String, Object>>> getUserRoleEntitlements(Object userRolesObj,Map<String, 
			List<ExtractorAttributes>> options, String roleProvisioningAction, Map params) {
	
		String templateFileName=getTemplateFile(roleProvisioningAction);
		Map<String, List<Map<String,Object>>> entitlements = null;
		if(templateFileName!=null && !templateFileName.isEmpty()){
			
			if(userRolesObj!=null){
				ArrayList<String> roleNamesList=new ArrayList();
				if(userRolesObj instanceof String){
					roleNamesList.add((String)userRolesObj);
				}else if(userRolesObj instanceof ArrayList){
					ArrayList list=(ArrayList)userRolesObj;
					roleNamesList.addAll(0,list);
				}
				
				//String roleName=(String)requestResponseHandler.getAttributeValue(userObj, sysConnector.getRoleIdentifierKey());
				String lastRunTimeField=sysConnector.getRoleLastRunTimeField();
				String reconDateFormat=sysConnector.getReconDateFormat();
				SimpleDateFormat sdf=new SimpleDateFormat(reconDateFormat);
				
				if(!roleNamesList.isEmpty()){//roles exist
					
					List<Map<String,Object>> rolesList = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < roleNamesList.size(); i++) {
						String roleName=roleNamesList.get(i);
						try{
							Map map=new HashMap();
							if(params!=null) map.putAll(params);
							map.put(sysConnector.getRoleIdentifierKey(),roleName);
							IResponse response=doReconOperation("Get User Role",roleProvisioningAction,map);
							List<Object> roles=response.getValues(sysConnector.getReconRolesTag());
							Object roleObj=roles.get(0);
							Map<String,Object> responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,roleObj);
							
							//String[] attributeNames=requestResponseHandler.getAttributeNames(roleObj);
							Map<String,Object> attributesMap= new HashMap<String,Object>();
							for(String attributeName:responseMap.keySet()){
								Object attributeValue=responseMap.get(attributeName);
								if(attributeName.startsWith("ROLE_NAME")){
									attributeName="Name";
								}else if(attributeName.startsWith("ROLE_DESCRIPTION")){
									attributeName="Description";
								}else if(attributeName.startsWith("ROLE_VALID_FROM")){
									attributeName="ValidFrom";
									attributeValue=sdf.parse((String)attributeValue);
								}else if(attributeName.startsWith("ROLE_VALID_TO")){
									attributeName="ValidTo";
									attributeValue=sdf.parse((String)attributeValue);
								}
								attributesMap=convertFormat(attributeName, attributeValue, options, attributesMap, "Role");
							}
							//String technicalRoleName=(String)responseMap.get(sysConnector.getRoleIdentifierKey());
							//attributesMap.put(IExtractionConstants.TECHNICAL_ROLE_NAME, technicalRoleName);
							rolesList.add(attributesMap);
						}catch(Exception ex){
							logger.error("Error in getting response map from Parser", ex);
						}
					}
					if(rolesList != null && rolesList.size()>0){
						entitlements = new HashMap<String, List<Map<String,Object>>>();
						entitlements.put(USER_ENTITLEMENT_KEY.ROLES.toString(),rolesList);
					}
				}
			}
		}
			return entitlements;
	}
	
	/**
	 * Get Badge related information for a given user
	 * 
	 * @param userBadgesObj Object containing user role information
	 * @param options Attributes to be extracted for badges 
	 * @param badgeProvisioningAction 
	 * @param params
	 * @param responseMap
	 * 
	 * @return User Badge Entitlements
	 */
	public Map<String, List<Map<String, Object>>> getUserBadgeEntitlements(Object userBadgesObj,Map<String, 
			List<ExtractorAttributes>> options, String badgeProvisioningAction, Map params) {
	
			Map<String, List<Map<String,Object>>> entitlements = null;
			if(userBadgesObj!=null){
				ArrayList<String> badgeIdList=new ArrayList();
				if(userBadgesObj instanceof String){
					badgeIdList.add((String)userBadgesObj);
				}else if(userBadgesObj instanceof ArrayList){
					ArrayList list=(ArrayList)userBadgesObj;
					badgeIdList.addAll(0,list);
				}
				
				//String roleName=(String)requestResponseHandler.getAttributeValue(userObj, sysConnector.getRoleIdentifierKey());
				//String lastRunTimeField=sysConnector.getBadgeLastRunTimeField();
				String reconDateFormat=sysConnector.getReconDateFormat();
				SimpleDateFormat sdf=new SimpleDateFormat(reconDateFormat);
				
				if(!badgeIdList.isEmpty()){//badges exist
					String templateFileName=getTemplateFile(badgeProvisioningAction);
					List<Map<String,Object>> badgesList = new ArrayList<Map<String,Object>>();
					for (int i = 0; i < badgeIdList.size(); i++) {
						String badgeId=badgeIdList.get(i);
						try{
							Map map=new HashMap();
							if(params!=null) map.putAll(params);
							map.put(sysConnector.getBadgeIdentifierKey(),badgeId);
							IResponse response=doReconOperation("Get User Badge",badgeProvisioningAction,map);
							List<Object> badges=response.getValues(sysConnector.getReconBadgesTag());
							Object badgeObj=badges.get(0);
							Map<String,Object> responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,badgeObj);
							
							Map<String,Object> attributesMap= new HashMap<String,Object>();
							for(String attributeName:responseMap.keySet()){
								Object attributeValue=responseMap.get(attributeName);
								attributesMap=convertFormat(attributeName, attributeValue, options, attributesMap, "Badge");
							}
							
							badgesList.add(attributesMap);
						}catch(Exception ex){
							logger.error("Error in getting response map from Parser", ex);
						}
					}
					if(badgesList != null && badgesList.size()>0){
						entitlements = new HashMap<String, List<Map<String,Object>>>();
						entitlements.put(USER_ENTITLEMENT_KEY.BADGES.toString(),badgesList);
					}
				}
			}
			return entitlements;
	}

	
	
	
	/**
	 * Convert value into the desired output format.
	 * 
	 * @param fieldName
	 * @param fieldValue
	 * @param extractorAttributesList
	 * @param userAttr
	 * @param type
	 * @return
	 */
	public Map<String,Object> convertFormat(String fieldName, Object fieldValue, 
			Map<String, List<ExtractorAttributes>> options, Map<String, Object> userAttr, String type){
		
		try{
			if(options!= null && options.size()>0 && options.containsKey(fieldName)){
				
				List<ExtractorAttributes> extractorAttributesList = options.get(fieldName);
				if(extractorAttributesList!= null && extractorAttributesList.size()>0){
					if(extractorAttributesList != null && extractorAttributesList.size() > 0 ){
						for (ExtractorAttributes extractorAttribute : extractorAttributesList) {
							
							boolean typeMatch =  (type.equals("User") && extractorAttribute.isUserAttr()) 
								||(type.equals("Role") && extractorAttribute.isRoleAttr()
										||(type.equals("Badge") && extractorAttribute.isBadgeAttr()));
							
							if(extractorAttribute != null && typeMatch){
								logger.debug(CLASS_NAME+" Setting value for: "+extractorAttribute.getAttributeName());
								if(extractorAttribute.getFieldType()==IExtractionConstants.TYPE.DATE){
									Date dateFieldValue=null;
									if(fieldValue!=null){//first convert to date object based on external system date format
										SimpleDateFormat sdf=new SimpleDateFormat(sysConnector.getReconDateFormat());
										dateFieldValue=sdf.parse(fieldValue.toString());
									}
									//convert date to date format sent by Alert application
									userAttr.put(extractorAttribute.getAttributeName(),convertDateFormat(dateFieldValue,extractorAttribute.getFormat()));
								}else if(extractorAttribute.getFieldType()==IExtractionConstants.TYPE.IMAGE){
										userAttr.put(extractorAttribute.getAttributeName(),fieldValue);
								}else if(IExtractionConstants.TYPE.MULTIVALUE == extractorAttribute.getFieldType()) {
										MultiValueDetails multiValDetails =null;
										if(fieldValue!=null && fieldValue instanceof String){
											String[] multiValArray = ((String)fieldValue).split(multiValSeperator);
											if(multiValArray.length>0){
												multiValDetails = new MultiValueDetails();
												ArrayList<MultiValue> multiValues = new ArrayList<MultiValue>();
												for (int i = 0; i < multiValArray.length; i++) {
													MultiValue multiVal  = new MultiValue();
													multiVal.setValue(multiValArray[i]);
													multiVal.setAction(IExtractionConstants.MULTI_VALUE_ACTION.ADD);
													multiValues.add(multiVal);
												}
												multiValDetails.setOperation(MULTI_VALUES_OPERATION.REPLACE);
												multiValDetails.setMultiValues(multiValues);
											}
										}
										userAttr.put(extractorAttribute.getAttributeName(),multiValDetails);
									}else{
										String value=(fieldValue!=null)?fieldValue.toString():null;
										userAttr.put(extractorAttribute.getAttributeName(),value);
								}
								//break;
							}
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error(CLASS_NAME+" convertFormat() : Error setting the value for "+fieldName,e);
		}
		return userAttr;
	}

	
	public String convertDateFormat(Date date, String dateFormat) throws ParseException{
		if(date==null) return null;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	
	@Override
	public void getAllUsersWithCallback(
			Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria,
			IExtractionEventCallback callback)
			throws ExtractorConnectionException {
		getAllUsersWithCallback(options, intFetchSize, searchCriteria, null, callback);
		
	}

	@Override
	public void getIncrementalUsersWithCallback(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria,IExtractionEventCallback callback)
			throws ExtractorConnectionException {
		getIncrementalUsersWithCallback(lastRunDate, options, intFetchSize, searchCriteria, null,callback);
		
	}

	@Override
	public void getIncrementalUsersWithCallback(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria,
			IExtractionEventCallback callback,
			ReconExtensionInfo reconExtensionInfo)
			throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		
	}
	public void getAllUsersWithCallback(Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback) throws ExtractorConnectionException{
		logger.debug(CLASS_NAME+" getAllUsersWithCallback(): Start of getAllUsersWithCallback(): With Search Callback going with fetch Size: " + fetchSize);
		getAllUsersWithCallback(options, fetchSize, searchCriteria, callback, null);
		logger.debug(CLASS_NAME+" getAllUsersWithCallback(): End of getAllUsersWithCallback(): With Search Callback ");
	}

	public void getAllUsersWithCallback(Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback,IExtractionEventCallback extractionEventCallback) throws ExtractorConnectionException{
		 try {	
			 	int index=0;
			 	logger.debug(CLASS_NAME+" getAllUsersWithCallback(): Start of getAllUsersWithCallback(): going with fetch Size: " + fetchSize);
			 	List<String> usersAlreadyProcessed = new ArrayList<String>();
				List<IUserInformation> userInfoList = new ArrayList<IUserInformation>();
				Map params=new HashMap();
				//logger.trace(CLASS_NAME+" getAllUsersWithCallback(): Fetching user for criteria:  " + userSearchCriteria);
				boolean flgMoreUsersExists=true;
				String nextPageToken=null;
				while(flgMoreUsersExists){
					params.put("NextPageToken", nextPageToken);
					IResponse response= doReconOperation( "Get Users","GET_USERS", params);//fetchUsers(fetchSize,nextPageToken);
					if(!response.hasAttribute("nextPageToken")){
						flgMoreUsersExists=false;
						nextPageToken=null;
					}else
						nextPageToken=(String)response.getAttributeValue("nextPageToken");
					
					List<Object> users= response.getValues(sysConnector.getReconUsersTag());
				 	for (int i = 0; i < users.size(); i++) {
				 		Object userObj=users.get(i);
						IUserInformation userInformation =processUser(userObj, options, "GET_USERS", "GET_USER_ROLE", "GET_USER_BADGE",null);
						if(extractionEventCallback!=null)
						{
							extractionEventCallback.sendEvent(userInformation);
						}
						else{	
							userInfoList.add(userInformation);
							if(index >= fetchSize-1){						
								try{
									callback.processSearchResult(userInfoList);
									userInfoList = new ArrayList<IUserInformation>();
									index=0;
								}catch(ExtractorConnectionException ex) {
									if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase("509")) {
										logger.debug(CLASS_NAME+" getAllUsersWithCallback() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
										break;
									}
									logger.error(CLASS_NAME+" getAllUsersWithCallback() : execution failed due to ExtractorConnectionException ",ex);
								}catch(Exception e){
									logger.error(CLASS_NAME+" getAllUsersWithCallback() : execution failed while Call back of Processing Search Results of greater Index ",e);
								}
							}else{
								index++;
							}
						}
					}
				}
				
				if(index!=0){
						try{
							callback.processSearchResult(userInfoList);
						}catch(ExtractorConnectionException ex) {
							if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase(
									"509")) {
								logger.debug(CLASS_NAME+" getAllUsersWithCallback() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
							}
							logger.error(CLASS_NAME+" getAllUsersWithCallback() : execution failed due to ExtractorConnectionException ",ex);
						} catch(Exception e){
							logger.error(CLASS_NAME+" getAllUsersWithCallback() : execution failed while Calling ProcessingSearchResults of greater Index",e);
						}
				}
				
	            logger.debug(CLASS_NAME+" getAllUsersWithCallback() of RestConnectionInterface() : End of getAllUsersWithCallback()");
	        } catch (Exception ex) {	        	
	        	logger.error(CLASS_NAME+" getAllUsersWithCallback() : execution failed ",ex);
	        }
		    logger.debug(CLASS_NAME+" getAllUsersWithCallback() of RestConnectionInterface() : End of getAllUsersWithCallback()");
	}
	


	@Override
	public void getIncrementalRoles(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
			throws ExtractorConnectionException {
		logger.debug(CLASS_NAME+" getIncrementalRoles() Start of RestConnectionInterface() with SearchCallback  : ");
		getIncrementalRoles(lastRunDate, options, fetchSize, searchCriteria,callback, null);
		logger.debug(CLASS_NAME+" getIncrementalRoles() End of RestConnectionInterface() with SearchCallback  : ");
	}

	@Override
	public void getAllRoles(Map<String, List<ExtractorAttributes>> options,
			int fetchSize, Map<String, String> searchCriteria,
			ISearchCallback callback) throws ExtractorConnectionException {
		logger.debug(CLASS_NAME+" getAllRoles() Start of RestConnectionInterface() with SearchCallback  : ");
		getAllRoles(options, fetchSize, searchCriteria, callback, null);
		logger.debug(CLASS_NAME+" getAllRoles() End of RestConnectionInterface() with SearchCallback  : ");
	}

	@Override
	public void getAllRoles(Map<String, List<ExtractorAttributes>> options,
			int fetchSize, Map<String, String> searchCriteria,
			IExtractionEventCallback eventCallback)
			throws ExtractorConnectionException {
		logger.debug(CLASS_NAME+" getAllRoles() Start of RestConnectionInterface() with Extraction Event  : ");
		getAllRoles(options, fetchSize, searchCriteria, null, eventCallback);
		logger.debug(CLASS_NAME+" getAllRoles() End of RestConnectionInterface() with Extraction Event  : ");
	}

	@Override
	public void getIncrementalRoles(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria,
			IExtractionEventCallback eventCallback)
			throws ExtractorConnectionException {
		logger.debug(CLASS_NAME+" getIncrementalRoles() Start of RestConnectionInterface() with Extraction Event  : ");
		getIncrementalRoles(lastRunDate, options, fetchSize, searchCriteria, null,eventCallback);
		logger.debug(CLASS_NAME+" getIncrementalRoles() End of RestConnectionInterface() with Extraction Event  : ");
	}


	@Override
	public List<IUserInformation> getIncrementalUsers(Date arg0,
			Map<String, List<ExtractorAttributes>> arg1, int arg2, int arg3,
			Map<String, String> arg4) throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get list of users based on last run date. 
	 * 
	 */
	
	public void getIncrementalUsersWithCallback(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
	throws ExtractorConnectionException {
getIncrementalUsersWithCallback(lastRunDate, options, fetchSize, searchCriteria, callback, null);
	}
	
	public void getIncrementalUsersWithCallback(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback,IExtractionEventCallback extractionEventCallback )
		throws ExtractorConnectionException {
		try {	
		 	int index=0;
		 	logger.debug(CLASS_NAME+" getIncrementalUsersWithCallback(): Start of getIncrementalUsersWithCallback(): going with fetch Size: " + fetchSize);
		 	
		 	Map params=parseRunTimeField(sysConnector.getUserLastRunTimeField(), sysConnector.getReconDateFormat(), lastRunDate);
		
			boolean flgMoreUsersExists=true;
			List<String> usersAlreadyProcessed = new ArrayList<String>();
			List<IUserInformation> userInfoList = new ArrayList<IUserInformation>();
			String nextPageToken=null;
			while(flgMoreUsersExists){
				params.put("NextPageToken", nextPageToken);
				IResponse response= doReconOperation( "Get Incremental Users","GET_INCREMENTAL_USERS", params);//(fetchSize,nextPageToken);
				if(!response.hasAttribute("nextPageToken")){
					flgMoreUsersExists=false;
					nextPageToken=null;
				}else
					nextPageToken=(String)response.getAttributeValue("nextPageToken");
				
				List users= response.getValues(sysConnector.getReconUsersTag());
			 	for (int i = 0; i < users.size(); i++) {
			 		Object userObj=users.get(i);
			 		//String datetimeStr=(String)requestResponseHandler.getAttributeValue(userObj, lastRunTimeFieldName);//2011-12-12T14:01:26.000Z
			 		//Date date = sdf.parse(datetimeStr);
			 		
			 		//if(date.before(lastRunDate))continue;
					IUserInformation userInformation =processUser(userObj, options,"GET_INCREMENTAL_USERS", "GET_INCREMENTAL_USER_ROLE", "GET_INCREMENTAL_USER_BADGE",params);
					if(extractionEventCallback!=null)
					{
						extractionEventCallback.sendEvent(userInformation);
					}
					else{
						userInfoList.add(userInformation);
						if(index >= fetchSize-1){						
							try{
								callback.processSearchResult(userInfoList);
								userInfoList = new ArrayList<IUserInformation>();
								index=0;
							}catch(ExtractorConnectionException ex) {
								if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase("509")) {
									logger.debug(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
									break;
								}
								logger.error(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed due to ExtractorConnectionException ",ex);
							}catch(Exception e){
								logger.error(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed while Call back of Processing Search Results of greater Index ",e);
							}
						}else{
							index++;
						}
					}
			 	}
			}
			 if(index!=0){
					try{
						callback.processSearchResult(userInfoList);
					}catch(ExtractorConnectionException ex) {
						if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase("509")) {
							logger.debug(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
						}
						logger.error(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed due to ExtractorConnectionException ",ex);
					} catch(Exception e){
						logger.error(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed while Calling ProcessingSearchResults of greater Index",e);
					}
			 }
			
            logger.debug(CLASS_NAME+" getIncrementalUsersWithCallback() of RestConnectionInterface() : End of getAllUsersWithCallback()");
        } catch (Exception ex) {	        	
        	logger.error(CLASS_NAME+" getIncrementalUsersWithCallback() : execution failed ",ex);
        }
	    logger.debug(CLASS_NAME+" getIncrementalUsersWithCallback() of RestConnectionInterface() : End of getAllUsersWithCallback()");
	}

	public Map parseRunTimeField(String lastRunTimeField,String dateFormat, Date lastRunDate) {
		Map map=null;
		if(lastRunTimeField!=null && !lastRunTimeField.trim().isEmpty()){
			map=new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			map.put(lastRunTimeField, sdf.format(lastRunDate));
		}
		return map;
	}

	@Override
	public Map getUsers(Map arg0, List arg1)
			throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getUsers(String arg0, ISearchCallback arg1)
			throws ExtractorConnectionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invokeUsers(Map arg0, List arg1)
			throws ExtractorConnectionException {
	}



	@Override
	public Map getAllUsers(Map arg0, List arg1)
			throws ExtractorConnectionException {
		return null;
	}

	/**
	 * Get template file from given template name. The file is located in templates directory
	 * 
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public String getTemplateFile(String provisioningAction)  {
		if(templatesFileCache.get(provisioningAction)==null){
		
			String templateDir=(String)sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR);
			String templateFilePath=null;
			if(new File(templateDir).exists()){
				templateFilePath= templateDir+File.separator+provisioningAction+"0."+sysConnector.getFormat();
				if(provisioningAction==null || provisioningAction.isEmpty()){
					 templateFilePath= templateDir+File.separator+"DefaultTemplate"+sysConnector.getFormat();
				}
				if(!new File(templateFilePath).exists()){
					//throw new FileNotFoundException("Template File Not Found");
					logger.debug("Could not find template file for " + provisioningAction);
					return null;
				}
			}
			templatesFileCache.put(provisioningAction,templateFilePath);
		}
		return templatesFileCache.get(provisioningAction) ;
		//return templateFilePath;
	}
	
	/**
	 * Get http method from url eg PATCH:/services/USER/xxxxx
	 * @param url
	 * @return
	 */
	public HttpMethod getHttpMethodFromURL(Map<String,Object>params, String url){
		
		HttpMethod method=null;
		
		url=replaceUrlPlaceHolders(params, url);
		
		if(url.indexOf("GET:")>=0) {
			url=url.replaceAll("GET:","");
			method= new GetMethod(url);
		}else if(url.indexOf("PATCH:")>=0) {
			url=url.replaceAll("PATCH:","");
			return  new PostMethod(url) {
					@Override public String getName() { return "PATCH"; }
			};
		}else if(url.indexOf("PUT:")>=0) {
			url=url.replaceAll("PUT:","");
			method= new PutMethod(url);
		}else if(url.indexOf("POST:")>=0) {
			url=url.replaceAll("POST:","");
			method=new PostMethod(url);
		}else  if(url.indexOf("DELETE:")>=0) {
			url=url.replaceAll("DELETE:","");
			method=new DeleteMethod(url);
		}
		
		if(sysConnector.getSSL()){
			String[] arr=sysConnector.getInstanceUrl().split(":");
			String host=arr[1].replaceAll("/", "");
			int port=Integer.valueOf(arr[2].trim());
			SSLProtocolSocketFactory sf = new SSLProtocolSocketFactory();
			Protocol protocol = new Protocol("https", new SSLProtocolSocketFactory(), port);
			method.getHostConfiguration().setHost(host, port, protocol);
			String authStr = sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_USERID) 
    			+ ":" + sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_GRDN);
			String encodedString = new String(Base64.encodeBase64(authStr.getBytes()));
			method.addRequestHeader("Authorization", "Basic "+ encodedString);
		}
		return method;
		
	}

	/**
	 * Replace tokens with values. 
	 * 
	 * @param params
	 * @param url
	 * @return
	 */
	public String replaceUrlPlaceHolders(Map<String,Object>params, String url) {

			try {
				if(params==null) return url;
			 	Pattern pattern = Pattern.compile("\\{\\w+\\}");
			 	Matcher matcher=pattern.matcher(url);
			 	while (matcher.find()) {
			 		String matchValue=matcher.group();
			 		String findValue=matchValue.replace("{", "").replace("}","");
			 		String replaceValue=(String)params.get(findValue);
			 		if(replaceValue!=null)
			 			replaceValue=URLEncoder.encode(replaceValue, "UTF-8");
			 		else
			 			replaceValue="";
					//url=matcher.replaceFirst();
					url=url.replace(matchValue,replaceValue);
			 	  }
			 	
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
			return url;
		}

	public IRequestResponseHandler getRequestResponseHandler() {
		return this.requestResponseHandler;
	}
	
	
	/**
	 * Evaluate List of certifications that needs to be evaluated for user in target system.
	 * 
	 * @param userCertResult Object that contains the user related certificates that need evaluation
	 * 
	 * @return list of certificate information returned by the external system with certificate name, status etc
	 * @throws Exception
	 */
	public List<ICertificationInformation> evaluateUserCertifications(
			IUserCertificationRequest userCertResult) throws Exception {
		
		logger.debug(CLASS_NAME+" Start of method evaluateUserCertifications()");
		List<ICertificationInformation> certificationList = null;
		
		IResponse response = doEvalCertOperation("Evaluate Certificates Request", "EVALUATE_CERTIFICATES_REQUEST", userCertResult);
		
		//String testResponse=getTestCertResponseString();
		//System.out.println(testResponse);
		//IResponse response = requestResponseHandler.handleResponse(testResponse);
		String templateFileName = getTemplateFile("EVALUATE_CERTIFICATES_RESPONSE");
		if (templateFileName == null) return null;
		Map<String,Object> responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,response.getResponseObject());
		
		//Iterate response xml and check for training name whether response object contains training name or not		
        certificationList = parseTrainingRespsonse(responseMap, userCertResult);    
        logger.debug("Exit from evaluateUserCertifications()");
		return certificationList;
	} 
	
	/**
	 * Create list of certification information objects from response Map for a given user
	 * 
	 * @param responseMap Map created from template file based on response returned by server
	 * @param userCertResult Input usercertification object with details of certifications whose status need to be retrieved
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ICertificationInformation> parseTrainingRespsonse(Map<String,Object> responseMap,IUserCertificationRequest userCertResult) throws Exception{
		
		List<ICertificationInformation> listCertificationInformation = new ArrayList<ICertificationInformation>();

		Map<String, Map<String,String>> certificationMap=new HashMap();
		//e.g Response like UserId=SampleUser1,Certifications1.Name=Sample User Training 1,Certifications1.CompletionDate=10/27/2014,Certifications1.Status=Completed,Certifications2.Name=Sample User Training 2,Certifications2.CompletionDate=10/28/2014,Certifications2.Status=Not Completed

		for(String respKey:responseMap.keySet()){
			String value=responseMap.get(respKey).toString();
			
			if(respKey.startsWith("Certifications")){
					int pos=respKey.indexOf(".");
					String key=respKey.substring(0,pos);
					String subKey=respKey.substring(pos+1);
					if(!certificationMap.containsKey(key)){
						certificationMap.put(key, new HashMap<String,String>());
					}
					certificationMap.get(key).put(subKey, value);
			}
		}
		
		for(Entry<String, Map<String, String>> entry:certificationMap.entrySet()){
				ICertificationInformation certificationInformation = new CertificationInformation();
				Map<String, Object> courseDetails = new HashMap<String, Object>();
				
				Map<String,String> valuesMap=entry.getValue();
				for(String key:valuesMap.keySet()){
					String value=valuesMap.get(key);
					if(key.equals("Name")||key.equals("Title")){
						certificationInformation.setCourseId(value);
					}else if(key.equals("CompletionDate")){
		                	try{
		                		value = getDate(value.toString());
		                	}catch(Exception e){
		                		logger.error(CLASS_NAME+" parserTrainintResponse():Exception in parsing the completion date",e);
		                	}
		           }
		           for (Map.Entry<String, List> certEntry : userCertResult.getCertMapping().entrySet()){
		                if(certEntry.getKey().equalsIgnoreCase(key)){
		                		List<String> alertAttributes = certEntry.getValue();
		                		for (String alertAttribute : alertAttributes) {
		                			courseDetails.put(alertAttribute, value.toString());
		                    	}
		                		break;	                			
		                	}
		               }
                	}
				    certificationInformation.setCourseDetails(courseDetails);
				    listCertificationInformation.add(certificationInformation);
			}
			logger.debug("Exit from parseTrainingRespsonse()");
			return listCertificationInformation;
	}
		
	/**
	 * Returns string equivalent of date returned by external system in AE date format
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public String getDate(String dateValue) throws Exception{
		String externalDateFormat=(String)sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT);
		SimpleDateFormat externalDF = new SimpleDateFormat(externalDateFormat);
		Date completionDate  = externalDF.parse(dateValue);
		String aeDateFormat=(String)sysConnector.getConnectorParams().get(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT);
		SimpleDateFormat applicationDF = new SimpleDateFormat(aeDateFormat);
		return applicationDF.format(completionDate);
		
	}
	
	public String getDate(String dateValue, String srcDateFormat, String destDateFormat) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(srcDateFormat);
		Date srcDate  = sdf.parse(dateValue);
		SimpleDateFormat applicationDF = new SimpleDateFormat(destDateFormat);
		return applicationDF.format(srcDate);
		
	}

	
	
	/**
	 * Get  Certifications for all users
	 * 
	 * @param options Extraction Attributes
	 * @param fetchSize Size for batch fetching
	 * @param searchCriteria
	 * @param callback Callback method to be invoked on reaching batchsize
	 */
	@Override
	public void getAllUserCertifications(
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
			throws ExtractorConnectionException {
			try {	
				 	int index=0;
				 	logger.debug(CLASS_NAME+" getAllUserCertifications(): Start of method: going with fetch Size: " + fetchSize);
				 	List<String> usersAlreadyProcessed = new ArrayList<String>();
					List<IUserCertificationInformation> userCertList = new ArrayList<IUserCertificationInformation>();
					Map params=new HashMap<String,Object>();
					boolean flgMoreUsersExists=true;
					String nextPageToken=null;
					while(flgMoreUsersExists){
						params.put("NextPageToken", nextPageToken);
						IResponse response= doReconOperation( "Get All Users Certifications","GET_USER_CERTIFICATIONS", params);//fetchUsers(fetchSize,nextPageToken);
						//String testResponse=this.getTestAllUserCertResponseString();
						//System.out.println(testResponse);
						//IResponse response = requestResponseHandler.handleResponse(testResponse);
						if(!response.hasAttribute("nextPageToken")){
							flgMoreUsersExists=false;
							nextPageToken=null;
						}else
							nextPageToken=(String)response.getAttributeValue("nextPageToken");
						
						List<Object> users= response.getValues(sysConnector.getReconCertificationsTag());
					 	for (int i = 0; i < users.size(); i++) {
					 		Object userObj=users.get(i);
					 		IUserCertificationInformation userCertInformation =processUserCertifications(userObj, options, "GET_USER_CERTIFICATIONS");
							userCertList.add(userCertInformation);
							if(index >= fetchSize-1){						
								try{
									callback.processSearchResult(userCertList);
									userCertList = new ArrayList<IUserCertificationInformation>();
									index=0;
								}catch(ExtractorConnectionException ex) {
									if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase("509")) {
										logger.debug(CLASS_NAME+" getAllUserCertifications() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
										break;
									}
									logger.error(CLASS_NAME+" getAllUserCertifications() : execution failed due to ExtractorConnectionException ",ex);
								}catch(Exception e){
									logger.error(CLASS_NAME+" getAllUserCertifications() : execution failed while Call back of Processing Search Results of greater Index ",e);
								}
							}else{
								index++;
							}
						}
					}
					
					if(index!=0){
							try{
								callback.processSearchResult(userCertList);
							}catch(ExtractorConnectionException ex) {
								if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase(
										"509")) {
									logger.debug(CLASS_NAME+" getAllUserCertifications() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
								}
								logger.error(CLASS_NAME+" getAllUserCertifications() : execution failed due to ExtractorConnectionException ",ex);
							} catch(Exception e){
								logger.error(CLASS_NAME+" getAllUserCertifications() : execution failed while Calling ProcessingSearchResults of greater Index",e);
							}
					}
				  } catch (Exception ex) {	        	
		        	logger.error(CLASS_NAME+" getAllUserCertifications() : execution failed ",ex);
		        }
			    logger.debug(CLASS_NAME+" getAllUserCertifications() of RestConnectionInterface() : End of method.");
	}

	
	/**
	 * Get certifications for all modified users 
	 * 
	 * @param options Extraction Attributes
	 * @param fetchSize Size for batch fetching
	 * @param searchCriteria
	 * @param callback Callback method to be invoked on reaching batch size
	 */
	@Override
	public void getIncrementalUserCertifications(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
			throws ExtractorConnectionException {
		try {	
		 	int index=0;
		 	logger.debug(CLASS_NAME+" getIncrementalUserCertifications(): Start of method: going with fetch Size: " + fetchSize);
		 	List<String> usersAlreadyProcessed = new ArrayList<String>();
			List<IUserCertificationInformation> userCertList = new ArrayList<IUserCertificationInformation>();
			Map params=parseRunTimeField(sysConnector.getUserLastRunTimeField(), sysConnector.getReconDateFormat(), lastRunDate);
			boolean flgMoreUsersExists=true;
			String nextPageToken=null;
			while(flgMoreUsersExists){
				params.put("NextPageToken", nextPageToken);
				IResponse response= doReconOperation( "Get Incremental User Certifications","GET_INCREMENTAL_USER_CERTIFICATIONS", params);
				//String testResponse=this.getTestAllUserCertResponseString();
				//System.out.println(testResponse);
				//IResponse response = requestResponseHandler.handleResponse(testResponse);
				if(!response.hasAttribute("nextPageToken")){
					flgMoreUsersExists=false;
					nextPageToken=null;
				}else
					nextPageToken=(String)response.getAttributeValue("nextPageToken");
				
				List<Object> users= response.getValues(sysConnector.getReconCertificationsTag());
			 	for (int i = 0; i < users.size(); i++) {
			 		Object userObj=users.get(i);
			 		IUserCertificationInformation userCertInformation =processUserCertifications(userObj, options, "GET_INCREMENTAL_USER_CERTIFICATIONS");
					userCertList.add(userCertInformation);
					if(index >= fetchSize-1){						
						try{
							callback.processSearchResult(userCertList);
							userCertList = new ArrayList<IUserCertificationInformation>();
							index=0;
						}catch(ExtractorConnectionException ex) {
							if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase("509")) {
								logger.debug(CLASS_NAME+" getIncrementalUserCertifications() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
								break;
							}
							logger.error(CLASS_NAME+" getIncrementalUserCertifications() : execution failed due to ExtractorConnectionException ",ex);
						}catch(Exception e){
							logger.error(CLASS_NAME+" getIncrementalUserCertifications() : execution failed while Call back of Processing Search Results of greater Index ",e);
						}
					}else{
						index++;
					}
				}
			}
			
			if(index!=0){
					try{
						callback.processSearchResult(userCertList);
					}catch(ExtractorConnectionException ex) {
						if(ex.getErrorCode() != null && ex.getErrorCode().equalsIgnoreCase(
								"509")) {
							logger.debug(CLASS_NAME+" getIncrementalUserCertifications() : execution failed due to ExtractorConnectionException of EXTRACTION_INTERRUPT");
						}
						logger.error(CLASS_NAME+" getIncrementalUserCertifications() : execution failed due to ExtractorConnectionException ",ex);
					} catch(Exception e){
						logger.error(CLASS_NAME+" getIncrementalUserCertifications() : execution failed while Calling ProcessingSearchResults of greater Index",e);
					}
			}
	   } catch (Exception ex) {	        	
        	logger.error(CLASS_NAME+" getIncrementalUserCertifications() : execution failed ",ex);
        }
	    logger.debug(CLASS_NAME+" getIncrementalUserCertifications() of RestConnectionInterface() : End of method");
	}
	
	/**
	 * Process User Certification.
	 * 
	 * @param userObj
	 * @param options
	 * @param string
	 * 
	 * @return User Certification object
	 */
	public IUserCertificationInformation processUserCertifications(Object userObj,
			Map<String, List<ExtractorAttributes>> options, 
			String userProvisioningAction){
		
		IUserCertificationInformation userCertInformation = null;
		String userID=null;
		try{
			
			userCertInformation = new UserCertificationInformation();
			userID=(String)requestResponseHandler.getAttributeValue(userObj, sysConnector.getUserIdentifierKey());
			logger.debug(CLASS_NAME+" Processing User Certification Information for : " + userID);
			String templateFileName=getTemplateFile(userProvisioningAction);
			Map<String, Object> userAttributes = new HashMap();
			Map<String,Object> responseMap=null;
			try{
				responseMap=requestResponseHandler.buildParamsFromTemplate(templateFileName,userObj);
			}catch(Exception ex){
				logger.error(ex);
			}
			
			/*if(responseMap!=null){
				for(String fieldName:options.keySet()){
					Object fieldValue=null;
					try{
						fieldValue=responseMap.get(fieldName);
					}catch(Exception ex){
					}
					
					userAttributes = convertFormat(fieldName, fieldValue, options, userAttributes, "User");
				}
			}*/
			
			//Construct UserCert Request Object to filter only required fields
			Map<String, String> userParams = new HashMap<String, String>();
			userParams.put("UserId", userID);
			Map<String, List> certMapping = new HashMap<String, List>();	
			
			for(String attribute:options.keySet()){
				List<ExtractorAttributes> extractorAttrList = options.get(attribute);
				for(ExtractorAttributes extractorAttr:extractorAttrList){
				List<String> certAttributeList=	new ArrayList();
				certAttributeList.add(extractorAttr.getAttributeName());
				//AEStatusList.add("Active");
				certMapping.put(attribute, certAttributeList);
				}
			}
			
			
			IUserCertificationRequest userCertRequest = new UserCertificationRequest();
			userCertRequest.setUserDetails(userParams);
			userCertRequest.setCertMapping(certMapping);
			List<ICertificationInformation> certificationList = parseTrainingRespsonse(responseMap, userCertRequest); 
			
			userCertInformation.setUserId(userID);
			userCertInformation.setCertificationInformation(certificationList);
			logger.debug(CLASS_NAME+" Exiting user processing ");
			logger.debug(CLASS_NAME+" Processed user details successfully for: " + userID);
		}catch (Exception ex) {
			logger.error(CLASS_NAME+" Error in processing user information for user: " + userID,ex);
		}
		return userCertInformation;
	}
	
	private boolean isTempRole(
			com.alnt.fabric.component.rolemanagement.search.IRoleInformation roleInformation) {
		boolean tempRole = false;
		if(roleInformation.getMemberData() != null && roleInformation.getMemberData().containsKey(IProvisoiningConstants.TEMPORARY_ROLE_ATTR)){
			List<String> flagValue = (List<String>)roleInformation.getMemberData().get(IProvisoiningConstants.TEMPORARY_ROLE_ATTR);
			if(flagValue != null && flagValue.size() > 0){
				tempRole = Boolean.valueOf(flagValue.get(0)).booleanValue();
			}
		}else if(roleInformation.getValidTo()!=null){
			String roleValidTo = formatRoleDate(roleInformation.getValidTo());						
			if(roleValidTo!=null && !roleValidTo.equals(permRoleValidTo)){
				tempRole = true;
			}
		}
		return tempRole;
	}
	
	protected String formatRoleDate(java.util.Date date) {
		if(date==null){
			return "";
		}
		Object format = null;
		format = sysConnector.getConnectorParams().get("dateFormat");
		if(format == null){
			format = "MM/dd/yyyy";	
		}
		SimpleDateFormat formater = new SimpleDateFormat((String)format);
		return formater.format(date);	
	}

	/**
	 * Format Attribute based on extraction attribute
	 * 
	 * @param extractorAttributes
	 * @param paramName
	 * @param value
	 * @return
	 */
	private Object format(ExtractorAttributes extractorAttribute,String paramName,Object value, String externalDateFormat) {
		if(null == value) return "";
		/*if(String.valueOf(value).equals("0")){
			if(!integerAttributesList.contains(paramName)){
				return "";
			}
		}*/
		String format = sysConnector.getAlertApplicationDateFormat();
		
		if(extractorAttribute.getFieldType()==IExtractionConstants.TYPE.DATE){			
			try {
				if(extractorAttribute.getFormat()!=null && extractorAttribute.getFormat()!=""){
					format = extractorAttribute.getFormat();
				}
				Date externalDate = null;
				if(value instanceof Date){
					externalDate = (Date)value;
				}else if(value instanceof GregorianCalendar){
					externalDate = ((GregorianCalendar)value).getTime();
				}else{
					DateFormat externalDateFormatter = new SimpleDateFormat(externalDateFormat);
					externalDate = externalDateFormatter.parse(((String)value).substring(0, 14));
				}
				DateFormat formatter = new SimpleDateFormat(format); 			
				value = formatter.format(externalDate);
								
			} catch (ParseException e) {				
				logger.error("  format(): Failed during parsing the date format ",e);
			}	
			catch (Exception e) {				
				logger.error("  format(): Failed during parsing the date format ",e);
			}	
		}else if(null != value && extractorAttribute.getFieldType()==IExtractionConstants.TYPE.IMAGE){
			return value;
		}
		return value.toString();
	}
	/**
	 * DO NOT REMOVE THESE METHODS YET AS THIS IS USED FOR TESTING SOME CERTIFICATE OPERATIONS
	 * 
	public String getTestCertResponseString(){
		StringBuilder sb=new StringBuilder();
		sb.append("{")
			.append("\"UserId\": \"SampleUser1\",")
			.append("\"Certifications\":")
				.append("[")
					.append("{")	
					.append("\"Name\": \"Sample User Training 1\",")
					.append("\"CompletionDate\": \"10/27/2014 12:00:00\",")
					.append("\"Status\": \"Completed\"") 
					.append("},")
					.append("{")	
					.append("\"Name\": \"Sample User Training 2\",")
					.append("\"CompletionDate\": \"10/28/2014 13:00:00\",")
					.append("\"Status\": \"Not Completed\"") 
					.append("}")
				.append("]")
			.append("}");
		return sb.toString();
	}
	
	public String getTestAllUserCertResponseString(){
		StringBuilder sb=new StringBuilder();
		sb.append("{")
		.append("\"UserCertificationList\":")
		.append("[")
			.append("{")
				.append("\"UserId\": \"SampleUser1\",")
				.append("\"Certifications\":")
					.append("[")
						.append("{")	
						.append("\"Name\": \"Sample User Training 1\",")
						.append("\"CompletionDate\": \"10/27/2014 12:00:00\",")
						.append("\"Status\": \"Completed\"") 
						.append("},")
						.append("{")	
						.append("\"Name\": \"Sample User Training 2\",")
						.append("\"CompletionDate\": \"10/28/2014 13:00:00\",")
						.append("\"Status\": \"Not Completed\"") 
						.append("}")
					.append("]")
				.append("},")
				.append("{")
				.append("\"UserId\": \"SampleUser2\",")
				.append("\"Certifications\":")
				.append("[")
					.append("{")	
					.append("\"Name\": \"Sample User Training 3\",")
					.append("\"CompletionDate\": \"11/27/2014 12:00:00\",")
					.append("\"Status\": \"Completed\"") 
					.append("},")
					.append("{")	
					.append("\"Name\": \"Sample User Training 4\",")
					.append("\"CompletionDate\": \"11/28/2014 13:00:00\",")
					.append("\"Status\": \"Not Completed\"") 
					.append("}")
				.append("]")
			.append("}")
		.append("]")
		.append("}");
		return sb.toString();
	}
*/
	/**
	 * Read content from file 
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFromFile(String fileName){

        StringBuilder sb = new StringBuilder();
		BufferedReader br=null;
		try {
		    	br = new BufferedReader(new FileReader(fileName));
		        String line = br.readLine();
		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		 }catch(Exception ex){
		        	ex.printStackTrace();
		 }finally {
		        try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
		 return sb.toString();
	}
	
	public Map<String, String> getTemplatesFileCache(){
		return this.templatesFileCache;
	}
	private void setKeyStoreParameters(Map<String,String> conParams) {
		if(conParams.get(RestConstants.EXTERNAL_KEYSTORE)!=null && !conParams.get(RestConstants.EXTERNAL_KEYSTORE).isEmpty()){
			if(System.getProperty("javax.net.ssl.keyStore")==null) {
				System.setProperty("javax.net.ssl.keyStore",conParams.get(RestConstants.EXTERNAL_KEYSTORE));
				logger.trace(CLASS_NAME+ "setSystemParameters(): Setting javax.net.ssl.keyStore = "+conParams.get(RestConstants.EXTERNAL_KEYSTORE));
			}
		}
		if(conParams.get(RestConstants.EXTERNAL_KEYSTORE_PWD)!=null && !conParams.get(RestConstants.EXTERNAL_KEYSTORE_PWD).isEmpty()){
			if(System.getProperty("javax.net.ssl.keyStorePassword")==null) {
				System.setProperty("javax.net.ssl.keyStorePassword",conParams.get(RestConstants.EXTERNAL_KEYSTORE_PWD));
				logger.trace(CLASS_NAME+ "setSystemParameters(): Setting javax.net.ssl.keyStorePassword = ");
			}
		}
		if(conParams.get(RestConstants.EXTERNAL_TRUSTSTORE)!=null && !conParams.get(RestConstants.EXTERNAL_TRUSTSTORE).isEmpty()){
			if(System.getProperty("javax.net.ssl.trustStore")==null) {
				System.setProperty("javax.net.ssl.trustStore",conParams.get(RestConstants.EXTERNAL_TRUSTSTORE));
				logger.trace(CLASS_NAME+ "setSystemParameters(): Setting javax.net.ssl.trustStore" + conParams.get(RestConstants.EXTERNAL_TRUSTSTORE));
			}
		}
		if(conParams.get(RestConstants.EXTERNAL_TRUSTSTORE_PWD)!=null && !conParams.get(RestConstants.EXTERNAL_TRUSTSTORE_PWD).isEmpty()){
			if(System.getProperty("javax.net.ssl.trustStorePassword")==null) {
				System.setProperty("javax.net.ssl.trustStorePassword",conParams.get(RestConstants.EXTERNAL_TRUSTSTORE_PWD));
				logger.trace(CLASS_NAME+ "setSystemParameters(): Setting javax.net.ssl.trustStorePassword");
			}
		}		
	} 
	
	
}