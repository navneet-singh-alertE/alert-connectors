package com.alnt.restconnector.provisioning.services;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;

import com.alnt.access.provisioning.utils.ConnectorUtil;
import com.alnt.restconnector.provisioning.constants.RestConstants;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.Credentials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * Class makes connection using REST API using OAuth Authentication
 * supported by systems like GoogleApps, Salesforce which have exposed their restful services.
 * The OAuth authentication techniques can vary as per the underlying system
 * 
 * @author amit.ghildiyal
 *
 */
public class RestConnector implements IConnector {
	
	private static final String CLASS_NAME = RestConnector.class.getName();
	static Logger logger = LogManager.getLogger(CLASS_NAME);
	
	private HttpClient httpClient = new HttpClient();
	private boolean tokenBased = true;
	private String accessToken=null;
	private String authorizationCode=null;
	private String customerId=null;
	private String clientId=null ;
	private String clientSecret = null;
	private String clientEmail=null;
	private String redirectURI=null;
	private String scope=null;
	private String responseType=null;
	private String userId = null;
	private String password = null;

	private String instanceUrl=null;
	
	private String reconUsersTag=null;
	private String reconRolesTag=null;
	private String reconBadgesTag=null;
	private String reconImagesTag=null;
	
	private String connectionURL = null;
	private String loginURL = null;
	private String authURL = null;
	private String tokenURL = null;
	private String tokenValidationURL = null;
	
	private String format = null;
	private String grantType = null;
	private String domain=null;
	private String certificateLocation=null;
	private String certificateType=null;
	private String certificatePassword=null;
	private String certificateAlias=null;
	
	private String createdUserIdentifierKey=null;
	private String userIdentifierKey=null;
	private String roleIdentifierKey=null;
	private String badgeIdentifierKey=null;
	private String lockedAttributeName=null;
	private String imageAttributeName=null;
	private String userLastRunTimeField=null;
	private String roleLastRunTimeField=null;
	private String alertApplicationDateFormat = null;
	
	private String reconDateFormat = null;
	private String provisioningDateFormat = null;
	
	private String templateDir=null;
	private Map connectorParams=null;
	private final  Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private String reconCertificationsTag=null;
	private String systemName=null;
	private String useSSL=null;
	private String proxyHost=null;
	private String proxyPort=null;
	private String proxyUser=null;
	private String proxyPwd=null;
	//setting the default value to 10 minutes.
	private int connectionTimeOut=10*60*1000;

	protected Set<String> excludeLogAttrList = null;
	
	public RestConnector() {
    }
    
	public RestConnector(Map params) {
    	setConnectorParams(params);
    }
	
	@Override
	public void setConnectorParams(Map params){
		try{
    		this.connectorParams=params;
    		this.accessToken=null;
    		systemName=this.getConnectorParam(RestConstants.SYS_CON_ATTR_SYSTEM_NAME);
			grantType=this.getConnectorParam(RestConstants.SYS_CON_ATTR_GRANT_TYPE);
			customerId = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CUSTOMER_ID);
			clientId = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CLIENT_ID);
			clientSecret = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CLIENT_SECRET);
			clientEmail=this.getConnectorParam(RestConstants.SYS_CON_ATTR_CLIENT_EMAIL);
			domain=this.getConnectorParam(RestConstants.SYS_CON_ATTR_DOMAIN);
			instanceUrl=this.getConnectorParam(RestConstants.SYS_CON_ATTR_INSTANCE_URL);
			scope = this.getConnectorParam(RestConstants.SYS_CON_ATTR_SCOPE);
			scope=scope.replaceAll(","," ");
			//redirectURI = this.getConnectorParam(GoogleAppsConstants.SYS_CON_ATTR_REDIRECT_URI);
			//responseType = this.getConnectorParam(GoogleAppsConstants.SYS_CON_ATTR_RESPONSE_TYPE);
			useSSL=this.getConnectorParam(RestConstants.SYS_CON_ATTR_SSL);
			userId = this.getConnectorParam(RestConstants.SYS_CON_ATTR_USERID);
			password = this.getConnectorParam(RestConstants.SYS_CON_ATTR_GRDN);
			authURL = this.getConnectorParam(RestConstants.SYS_CON_ATTR_AUTH_URL);
			tokenURL = this.getConnectorParam(RestConstants.SYS_CON_ATTR_TOKEN_URL);
			tokenValidationURL= this.getConnectorParam(RestConstants.SYS_CON_ATTR_TOKEN_VALIDATION_URL);
			format = this.getConnectorParam(RestConstants.SYS_CON_ATTR_FORMAT);
			certificateLocation = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CERTIFICATE_LOCATION);
			certificateType = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CERTIFICATE_TYPE);
			certificatePassword = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CERTIFICATE_GRDN);
			certificateAlias = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CERTIFICATE_ALIAS);
			
			userIdentifierKey=this.getConnectorParam(RestConstants.SYS_CON_ATTR_USER_IDENTIFIER_KEY);
			roleIdentifierKey=this.getConnectorParam(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY);
			badgeIdentifierKey=this.getConnectorParam(RestConstants.SYS_CON_ATTR_BADGE_IDENTIFIER_KEY);
			
			createdUserIdentifierKey=this.getConnectorParam(RestConstants.SYS_CON_ATTR_CREATED_USER_IDENTIFIER_KEY);
			lockedAttributeName=this.getConnectorParam(RestConstants.SYS_CON_ATTR_LOCKED_ATTRIBUTE);
			imageAttributeName=this.getConnectorParam(RestConstants.SYS_CON_ATTR_IMAGE_ATTRIBUTE);
			
			reconUsersTag=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_USERS_TAG);
			reconRolesTag=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_ROLES_TAG);
			reconBadgesTag=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_BADGES_TAG);
			reconImagesTag=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_IMAGES_TAG);
			reconCertificationsTag=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_CERTIFICATIONS_TAG);
			
			userLastRunTimeField=this.getConnectorParam(RestConstants.SYS_CON_ATTR_USER_LAST_RUNTIME_ATTRIBUTE);
			roleLastRunTimeField=this.getConnectorParam(RestConstants.SYS_CON_ATTR_ROLE_LAST_RUNTIME_ATTRIBUTE);
			
			alertApplicationDateFormat=this.getConnectorParam(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT);
			provisioningDateFormat=this.getConnectorParam(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT);
			reconDateFormat=this.getConnectorParam(RestConstants.SYS_CON_ATTR_RECON_DATE_FORMAT);
			templateDir=this.getConnectorParam(RestConstants.SYS_CON_ATTR_TEMPLATE_DIR);
			proxyHost=this.getConnectorParam(RestConstants.SYS_CON_ATTR_PROXY_HOST);
			proxyPort=this.getConnectorParam(RestConstants.SYS_CON_ATTR_PROXY_PORT);
			proxyUser=this.getConnectorParam(RestConstants.SYS_CON_ATTR_PXOXY_USER);
			proxyPwd=this.getConnectorParam(RestConstants.SYS_CON_ATTR_PXOXY_GRDN);
			
			String connTimeOut = this.getConnectorParam(RestConstants.SYS_CON_ATTR_CONN_TIMEOUT);
			if(connTimeOut!=null && !connTimeOut.isEmpty()){
				try{
					connectionTimeOut = (Integer.parseInt(connTimeOut))*1000;
				}catch(Exception e){
					connectionTimeOut = 10*60*1000;
				}
			}
			logger.debug(CLASS_NAME+" RestConnector Constructor : Http connection timeout = "+ connTimeOut + " : "+ connectionTimeOut);
			
			String excludeAttributes = this.getConnectorParam(RestConstants.SYS_CON_ATTR_SENSITIVE_ATTRIBUTES);
			if(excludeAttributes!=null && !excludeAttributes.isEmpty()){
				excludeLogAttrList = ConnectorUtil.convertStringToList(excludeAttributes, ",");
			}
			if(excludeLogAttrList==null){
				excludeLogAttrList = new HashSet<String>();
			}

    	} catch(Exception e){
    		logger.error(CLASS_NAME+" RestConnector Constructor : execution failed ",e);
    	
    	}
	}
	
	public String getImageAttributeName() {
		return imageAttributeName;
	}


	public void setImageAttributeName(String imageAttributeName) {
		this.imageAttributeName = imageAttributeName;
	}


	/**
	 * Make connection with the underlyings system eg google apps, salesforce etc.
	 * and get access token . This is specific to google apps.
	 * 
	 * The call goes to Authentication server OAuth and then returns token. 
	 * This token needs to be used for subsequent calls. 
	 * @param params
	 * @return
	 * true if access token is retrieve successfully , false if not
	 * @throws Exception
	 */
	
	private String getNewAccessTokenWithJWT(){
		
		 try {
			logger.debug("Trying to get new access token ..." );
			JSONObject header = new JSONObject();
		    header.put("alg", "RS256");
		    header.put("typ", "JWT");
		    String headerStr = header.toString();
	
		    JSONObject claim = new JSONObject();
		    claim.put("iss", clientEmail);
		    claim.put("prn", userId);
		    if(scope!=null && !scope.isEmpty())
		    	claim.put("scope", scope);
		    claim.put("aud", tokenURL);//tokenURL);
		    claim.put("exp", ""+(System.currentTimeMillis() + 3*60000L)/1000);//
		    claim.put("iat", ""+System.currentTimeMillis()/1000); //System.currentTimeMillis()        
		    String claimStr = claim.toString();
	    	
	        byte[] encodedHeaderArr = Base64.encodeBase64(headerStr.getBytes());//
	        byte[] encodedClaimArr = Base64.encodeBase64(claimStr.getBytes());
	        String inputStr = new String(encodedHeaderArr) + "." + new String(encodedClaimArr);
	      
	        
	        Signature signature = Signature.getInstance("SHA256withRSA");
	   
	        if(certificateType.equals("PKCS12")){
		        KeyStore keystore=KeyStore.getInstance(certificateType);
		        InputStream inputStream=new FileInputStream(new File(certificateLocation));
		        keystore.load(inputStream, certificatePassword.toCharArray());
		        PrivateKey privateKey = (PrivateKey)keystore.getKey(certificateAlias, certificatePassword.toCharArray());
		        signature.initSign(privateKey);
	        }else if(certificateType.equals("X.509")){
		         InputStream inputStream=new FileInputStream(new File(certificateLocation));
	             CertificateFactory cf = CertificateFactory.getInstance("X.509");
	            // X509Certificate cert = (X509Certificate)cf.generateCertificate(inputStream);
			     //signature.initSign(cert.getPublicKey());
	        }
	        
	       
	        signature.update(inputStr.getBytes());  
	        String encodedSignature=new String(Base64.encodeBase64(signature.sign()));
	        
	        String strJWT=inputStr+"." + encodedSignature ;
	        
	        logger.debug("POST " + tokenURL);
	        PostMethod method = new PostMethod(tokenURL);
			method.addParameter("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
			method.addParameter("assertion", strJWT);
			this.executeRequest(method,true,false);
			
			if(HttpStatus.SC_OK==method.getStatusCode()){
				String response=method.getResponseBodyAsString();
				JSONObject jsonObj = new JSONObject(response);
				accessToken=(String) jsonObj.get("access_token");
			}
		 }catch(Exception e){
				logger.error(CLASS_NAME + "Error getting logging in",e);
				accessToken=null;
		}
		
		//System.out.println("JSONResponse:Access Token=" + accessToken);
		return this.accessToken;
	}
	
	/**
	 * Fetches token based on user credentials. Userid and password are required
	 * 
	 * @return
	 */
	private String getNewAccessTokenWithBasicCredentials(){
		PostMethod method = null;
		try{
			method=new PostMethod(tokenURL);
			method.addParameter("grant_type", grantType);
			method.addParameter("client_id", clientId);
			method.addParameter("client_secret", clientSecret);
			method.addParameter("username", userId);
			method.addParameter("password", password);
			method.addParameter("format", format);
			method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			httpClient.executeMethod(method);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(connectionTimeOut);
			logger.debug("response body : "+method.getResponseBodyAsString());
			JSONObject response = new JSONObject(method.getResponseBodyAsString());
			logger.debug("json resp: "+response);
			accessToken = response.getString("access_token");
			if(response.has("instance_url")){
				instanceUrl=response.getString("instance_url");
			}
		}catch(Exception e){
			logger.error(CLASS_NAME + "Error getting logging in",e);
			accessToken=null;
		}
		return this.accessToken;
	}
		
	private boolean login() throws Exception {
		PostMethod method = new PostMethod(loginURL);
    	method.addParameter("Email", userId);
    	method.addParameter("Passwd", password);
		this.executeRequest(method,true,false);
		return (method.getStatusCode()==200)? true:false;
	}
	
		/*public boolean  makeConnection() throws Exception {
		        
		        try{
		        	httpClient=new HttpClient(); 
		 	        
		        	PostMethod method = new PostMethod(loginURL);// + authPathURL);
					//post.addParameter("grant_type", grantType);
					//post.addParameter("client_id", clientId);
					//post.addParameter("client_secret", clientSecret);
		        	method.addParameter("Email", userId);
		        	method.addParameter("Passwd", password);
					this.executeRequest(method);
					if(method.getStatusCode()==200){//Login successful
						
				  	 	method = new PostMethod(authURL);
				  	 	method.addParameter("client_id", clientId);
				  	 	method.addParameter("response_type", responseType);
				  	 	method.addParameter("scope", scope);
				  	 	method.addParameter("redirect_uri", redirectURI);
				  	 	method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				  	 	this.executeRequest(method);
						System.out.println();
						//accessToken="AIzaSyCFj15TpkchL4OUhLD1Q2zgxQnMb7v3XaM";
						authorizationCode="4/zjtpruNXHMuOsP9XLdWKkER_h-Ut.4gnyzNjfwTsVXE-sT2ZLcbQ3ZqS4hwI";
						
						method = new PostMethod(tokenURL);
				  	 	method.addParameter("code", authorizationCode);
				  	 	method.addParameter("client_id", clientId);
				  	 	method.addParameter("client_secret", clientSecret);
				  	 	method.addParameter("redirect_uri", redirectURI);
				  	 	method.addParameter("grant_type", "authorization_code");
				  	 	//method.addParameter("scope", scope);
				  	 	this.executeRequest(method);
						System.out.println(method.getResponseBodyAsString());
						//JSONObject response = new JSONObject(post.getResponseBodyAsString());
						//accessToken = response.getString("access_token");
						//instanceUrl = response.getString("instance_url");
						//doGet(accessToken, instanceUrl);
					}
				}catch(Exception e){
					logger.error(CLASS_NAME + "Error getting logging in",e);
				}
				boolean status= (accessToken !=null && !accessToken.isEmpty());
				return status;
		}
	*/
   
	@Override
	public void setAccessToken(String token){
		this.accessToken=token;
	}
	
	@Override
	public String getAccessToken(){
		if(tokenBased==false) return null;
		if(checkTokenValidity(this.accessToken)==false)
			return this.getNewAccessToken();
		else 
			return this.accessToken;
			
	}

	/**
	 * Depending on authentication mechanism invoke the underlying authentication 
	 * strategy and retrieve the token.
	 * @return
	 */
	@Override
	public String getNewAccessToken() {
		if(grantType!=null && !grantType.isEmpty() && grantType.equals("password")){
			return this.getNewAccessTokenWithBasicCredentials();
		}else{
			return this.getNewAccessTokenWithJWT();
		}
	}


	/**
	 * If token has expired since some token are valid for only an hour or so.
	 * Need to get a new token in that case. Validate the access token agains
	 * the validate token url of the underlying system
	 * 
	 * @param accessToken
	 * @return
	 */
	@Override
	public boolean checkTokenValidity(String accessToken) {
		
			GetMethod method=null;
			boolean isValidToken=false;
			try{
				if(null==this.accessToken) return false;
				if(null==tokenValidationURL||tokenValidationURL.isEmpty()) return true;
				String url=tokenValidationURL+"?access_token="+this.accessToken;
				method=new GetMethod(url);
				this.executeRequest(method,true,false);
				//System.out.println(method.getResponseBodyAsString());
				if(HttpStatus.SC_OK==method.getStatusCode()){
					String response=method.getResponseBodyAsString();
					JSONObject jsonObj = new JSONObject(response);
					isValidToken= !jsonObj.has("error");
				}
		}catch(Exception e){
			logger.error(CLASS_NAME + "Error checking token validity",e);
		}
		return isValidToken;
	}

	
	@Override
	public Map getConnectorParams() {
		return this.connectorParams;
	}

	public String getConnectorParam(String key){
		Object value=this.connectorParams.get(key);
		return (value!=null)?(String)value:"";
	}


	public void executeRequest(HttpMethod method, boolean logURI, boolean logResponse) throws Exception {
		if(logURI)
			logger.debug(method.getURI());
		
		if(proxyHost!=null && !proxyHost.isEmpty() && proxyPort!=null && !proxyPort.isEmpty()){
			logger.debug(CLASS_NAME + "Proxy host details are provided: "+ proxyHost+", port: "+ proxyPort);
			int port = Integer.parseInt(proxyPort);
			HostConfiguration config = httpClient.getHostConfiguration();
	        config.setProxy(proxyHost, port);
	       if(proxyUser!=null && !proxyUser.isEmpty() && proxyPwd!=null && !proxyPwd.isEmpty()){
	    	   logger.debug(CLASS_NAME + "Proxy credential are provided: ");
	    	   Credentials credentials = new UsernamePasswordCredentials(proxyUser, proxyPwd);
	    	   AuthScope authScope = new AuthScope(proxyHost, port);	
	    	   httpClient.getState().setProxyCredentials(authScope, credentials);
	       }
		}
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(connectionTimeOut);
		
		this.httpClient.executeMethod(method);
		
		if(logResponse)
			logger.debug("Response Body:"+method.getResponseBodyAsString());
	}


	public void releaseConnection(HttpMethod method) {
		if(method!=null)
			method.releaseConnection();
	}


	public String getInstanceUrl() {
		return instanceUrl;
	}
	
	public void setInstanceUrl(String instanceUrl) {
		this.instanceUrl = instanceUrl;
	}
	
	public void setUserIdentifierKey(String userIdentifierKey) {
		this.userIdentifierKey = userIdentifierKey;
	}

	public String getUserIdentifierKey() {
			return userIdentifierKey;
	}

	public void setRoleIdentifierKey(String userIdentifierKey) {
		this.roleIdentifierKey = roleIdentifierKey;
	}
	
	public String getRoleIdentifierKey() {
		return roleIdentifierKey;
	}
	
	public void setBadgeIdentifierKey(String badgeIdentifierKey) {
		this.badgeIdentifierKey = badgeIdentifierKey;
	}
	
	public String getBadgeIdentifierKey() {
		return this.badgeIdentifierKey;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}


	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getClientId() {
		return clientId;
	}


	public void setClientId(String clientId) {
		this.clientId = clientId;
	}


	public String getClientSecret() {
		return clientSecret;
	}


	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}


	public String getClientEmail() {
		return clientEmail;
	}


	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}


	public String getRedirectURI() {
		return redirectURI;
	}


	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public String getResponseType() {
		return responseType;
	}


	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConnectionURL() {
		return connectionURL;
	}


	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}


	public String getLoginURL() {
		return loginURL;
	}


	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}


	public String getAuthURL() {
		return authURL;
	}


	public void setAuthURL(String authURL) {
		this.authURL = authURL;
	}


	public String getTokenURL() {
		return tokenURL;
	}


	public void setTokenURL(String tokenURL) {
		this.tokenURL = tokenURL;
	}


	public String getTokenValidationURL() {
		return tokenValidationURL;
	}


	public void setTokenValidationURL(String tokenValidationURL) {
		this.tokenValidationURL = tokenValidationURL;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public String getGrantType() {
		return grantType;
	}


	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}


	public String getCertificateLocation() {
		return certificateLocation;
	}


	public void setCertificateLocation(String certificateLocation) {
		this.certificateLocation = certificateLocation;
	}


	public String getCertificateType() {
		return certificateType;
	}


	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}


	public String getCertificatePassword() {
		return certificatePassword;
	}


	public void setCertificatePassword(String certificatePassword) {
		this.certificatePassword = certificatePassword;
	}


	public String getCertificateAlias() {
		return certificateAlias;
	}


	public void setCertificateAlias(String certificateAlias) {
		this.certificateAlias = certificateAlias;
	}


	public String getReconRolesTag() {
		return reconRolesTag;
	}


	public void setReconRolesTag(String rolesTag) {
		this.reconRolesTag = rolesTag;
	}


	public String getReconUsersTag() {
		return reconUsersTag;
	}

	public void setReconUsersTag(String usersTag) {
		this.reconUsersTag = usersTag;
	}
	
	public String getReconBadgesTag() {
		return reconBadgesTag;
	}
	public void setReconBadgesTag(String usersTag) {
		this.reconBadgesTag = usersTag;
	}

	public String getReconCertificationsTag() {
		return this.reconCertificationsTag;
	}
	
	public void setReconCertificationsTag(String tag) {
		this.reconCertificationsTag = tag;
	}

	public String getLockedAttributeName() {
		return lockedAttributeName;
	}


	public void setLockedAttributeName(String lockedAttributeName) {
		this.lockedAttributeName = lockedAttributeName;
	}

	public String getTemplateDir() {
		return templateDir;
	}


	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	
	public String getUserLastRunTimeField() {
		return this.userLastRunTimeField;
	}


	public String getRoleLastRunTimeField() {
		return this.roleLastRunTimeField;
	}
	
	public String getAlertApplicationDateFormat() {
		return alertApplicationDateFormat;
	}


	public String getReconDateFormat() {
		return reconDateFormat;
	}

	public void setReconDateFormat(String reconDateFormat) {
		this.reconDateFormat = reconDateFormat;
	}

	
	public String getProvisioningDateFormat() {
		return provisioningDateFormat;
	}

	public void setProvisioningDateFormat(String provisioningDateFormat) {
		this.provisioningDateFormat = provisioningDateFormat;
	}
	

	public void setCreatedUserIdentifierKey(String key) {
		this.createdUserIdentifierKey=key;
	}

	public String getCreatedUserIdentifierKey() {
		return this.createdUserIdentifierKey;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public String getBadgeLastRunTimeField() {
		return null;
	}

	@Override
	public boolean getSSL() {
		if(this.useSSL!=null && this.useSSL.equalsIgnoreCase("true"))
			return true;
		else 
			return false;
	}

	public String getReconImagesTag() {
		return this.reconImagesTag;
	}

	public boolean getTokenBased() {
		return this.tokenBased;
	}
	
	public void setTokenBased(boolean tokenBased) {
		 this.tokenBased =tokenBased;
	}
	
	public Set<String> getExcludeLogAttributesList(){
		return this.excludeLogAttrList;
	}

}