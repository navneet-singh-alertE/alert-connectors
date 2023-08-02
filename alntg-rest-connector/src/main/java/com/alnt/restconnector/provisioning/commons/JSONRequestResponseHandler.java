package com.alnt.restconnector.provisioning.commons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.alnt.access.provisioning.services.RESTTransformationMapper;
import com.alnt.access.provisioning.services.TypesFieldMappingsSet;
import com.alnt.restconnector.provisioning.utils.*;
//import com.jayway.jsonpath.JsonPath;

/**
 * This class creates objects of type json from request parameters which 
 * could be map. And also creates json return types from responses.
 * The customization for a respective connector can happen here depending
 * on return type and their complex data structures
 * 
 * @author amit.ghildiyal
 *
 */
public class JSONRequestResponseHandler implements IRequestResponseHandler {

	private ParserFactory factory = null;
    private JSONProvisioningParser provisioningParser = null;
    private JSONReconParser reconParser = null;
    private Map connectorParams=null;
    private  Map <String,String> templates = new HashMap();
    private  final static String CLASS_NAME = "com.alnt.restconnector.provisioning.commons.JSONRequestResponseHandler";
    private Logger logger = LogManager.getLogger(CLASS_NAME);
    //private String roleIdentifier=null;
    
    public JSONRequestResponseHandler(Map connectorParams){
       factory=ParserFactory.getInstance();
       provisioningParser = factory.getJSONProvisioningParser();
       provisioningParser.setConnectorParams(connectorParams);
       provisioningParser.initialize(JSONProvisioningParser.class.getClassLoader().getResourceAsStream("com/json/config/json-config.xml"));
       provisioningParser.setValidating(false);
       
       reconParser = factory.getJSONReconParser();
       reconParser.setConnectorParams(connectorParams);
       reconParser.initialize(JSONProvisioningParser.class.getClassLoader().getResourceAsStream("com/json/config/json-config.xml"));
       reconParser.setValidating(false);
    }

	public JSONRequestResponseHandler(Map connectorParams, Map<String, String> templates){
		this(connectorParams);
		this.templates = templates;
	}

	public void setConnectorParams(Map connectorParams) {
		this.connectorParams=connectorParams;
		provisioningParser.setConnectorParams(connectorParams);
		reconParser.setConnectorParams(connectorParams);
	}
    
	/**
	 * Create request json object from parameters
	 */
	
	public String handleRequest(Map<String,Object> parameters) throws Exception{
		//Map<String,String> parameters=(Map<String,String>) requestParams;
		JSONObject json= buildJSONObjectFromTemplate(parameters);
		return json.toString();
	}

	
	public IResponse handleResponse(Object responseBody) throws Exception {
		if(responseBody==null) return null;
		return new JSONResponse((String)responseBody);
	}
	
	
	/**
	 * Build a json object from the template mentioned in the parameters. Populate
	 * the template variables with properties in the parameters
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	private JSONObject buildJSONObjectFromTemplate(Map<String,Object> parameters) throws Exception{
		String templateFileName=(String)parameters.get("template");
		String content=getTemplate(templateFileName);
		if(content==null || content.isEmpty()) {
			logger.warn("buildJSONObjectFromTemplate(): Template File desn't exist or Path is not correct: "+ templateFileName);
			return null;
		}
		parameters.remove("template");
		
		provisioningParser.setParams(parameters);
		//provisioningParser.setRoleIdentifier(roleIdentifier);
		Map finalJsonData = provisioningParser.parseJson(content);
		return new JSONObject(finalJsonData);
	}

	public Map<String,Object> buildParamsFromTemplate(String templateFileName, Object responseObject) throws Exception{
		String content=getTemplate(templateFileName);
		if(content==null || content.isEmpty()){
			logger.warn("buildParamsFromTemplate(): Template File desn't exist or Path is not correct: "+ templateFileName);
			return null;
		}
		
		reconParser.setResponse((JSONObject) responseObject);
		//reconParser.setRoleIdentifier(roleIdentifier);
		Map params = reconParser.parseJson(content);
		return params;
	}

	/**
	 * Get template content. 
	 * 
	 * @param templateKey
	 * @return
	 */
	public String getTemplate(String templateKey){
			/*if(templates.get(templateKey)==null){
			    StringBuilder builder = new StringBuilder();
			    BufferedReader br=null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
					char[] charBuffer = new char[8192];
				    int offset = -1;
				    while ((offset = br.read(charBuffer)) > -1){
				      builder.append(charBuffer, 0, offset);
				    }
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(br!=null)
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			   String content=builder.toString();
				templates.put(templateKey, content);
			}*/
			return templates.get(templateKey);
	}
	
	/**
	 * Get the value from the json object based on fieldName
	 * 
	 * @param dataString json response
	 * @param attrName fieldName for which value needs to be extracted from data
	 * 
	 * @return value of the extracted field
	 */
	
	public Object getAttributeValue(String dataString, String attrName) throws Exception{
		Object value=null;
		if(dataString==null||dataString.isEmpty()) return null;
		if(attrName==null||attrName.isEmpty()) return null;
		
		char[] charArray=attrName.toCharArray();
		StringBuilder sb=new StringBuilder();
		for(char c:charArray){
			if(Character.isDigit(c)){
				int val=Character.getNumericValue(c)-1;//eg email1 should be converted to email0
				if(val<0) return "";
				sb.append('[').append(val).append(']');
			}else{
				sb.append(c);
			}
		}
		String path="$."+sb.toString();
		
		JSONObject jobj=new JSONObject(dataString);
		if(jobj.has(sb.toString())){
			value = jobj.get(sb.toString());
		}
		//value=JsonPath.read(dataString, path);
		return value;
	}
	
	
	
	public String[] getAttributeNames(Object dataObject) throws Exception {
		return JSONObject.getNames((JSONObject)dataObject);
	}
	
	
	public Object getAttributeValue(Object dataObject, String attrName) throws Exception{
			if(!((JSONObject)dataObject).has(attrName) || ((JSONObject)dataObject).isNull(attrName)) return null;
			return  ((JSONObject) dataObject).get(attrName).toString();
	}

	
	/**
	 * Checks if the tokenName is and array type or not. For eg. addresses1.postalcode and addresses2.postalcode
	 * will return token addresses
	 */
	public String  getActualTokenName(String token){
		String tokenName=token;
		if(token==null || token.isEmpty())
			return "";
		char[] charArray=token.toCharArray();
		int size=charArray.length;
		int pos=size;
		for(int i=size-1; i>=0; i--){
				boolean isDigit=Character.isDigit(charArray[i]);
				if(!isDigit) {
					break;
				}else{
					pos--;
				}
		}
		tokenName=tokenName.substring(0, pos);
		//System.out.println(token + ", " + tokenName);
		return tokenName;
	}

	
	public String getErrorMessage(String dataString) {
		/*String errMsg="";
		
		if(dataString!=null && !dataString.isEmpty()){
			try {
				errMsg="Status Code=" +getAttributeValue(dataString,"error.code")+", "
						+ getAttributeValue(dataString,"error.message") ;
			} catch (Exception ex) {
			}
		}
		return errMsg;*/
		return dataString;
	}



	
	
}
