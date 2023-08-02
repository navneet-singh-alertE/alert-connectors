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
import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import com.alnt.restconnector.provisioning.utils.ParserFactory;
import com.alnt.restconnector.provisioning.utils.XMLProvisioningParser;
	/**
	 * This class creates objects of type xml from request parameters which 
	 * could be map. And also creates xml return types from responses.
	 * The customization for a respective connector can happen here depending
	 * on return type and their complex data structures
	 * 
	 * @author amit.ghildiyal
	 *
	 */
	public class XMLRequestResponseHandler implements IRequestResponseHandler {

	      private  String CLASS_NAME = "com.alnt.ws.provisioning.commons.XMLRequestResponseHandler";
		  private ParserFactory factory = null;
	      private XMLProvisioningParser provisioningParser = null;
	      private XMLReconParser reconParser = null;
	      private Map connectorParams=null;
	      private  Map <String,String> templates = new HashMap();
	  	  private Logger logger = LogManager.getLogger(CLASS_NAME);
	    
	    //private String roleIdentifier=null;
	    
	    public XMLRequestResponseHandler(Map connectorParams){
	       factory=ParserFactory.getInstance();
	       provisioningParser = factory.getXMLProvisioningParser();
	       provisioningParser.setConnectorParams(connectorParams);
	       reconParser = factory.getXMLReconParser();
	       reconParser.setConnectorParams(connectorParams);
	    }

		public XMLRequestResponseHandler(Map connectorParams, Map<String, String> templates){
			this(connectorParams);
			this.templates = templates;
		}

		
		public void setConnectorParams(Map connectorParams) {
			this.connectorParams=connectorParams;
			provisioningParser.setConnectorParams(connectorParams);
			reconParser.setConnectorParams(connectorParams);
		}
	    
		/**
		 * Create request xml object from parameters
		 */
		
		public String handleRequest(Map<String,Object> parameters) throws Exception{
			XmlObject xmlObject= buildXMLObjectFromTemplate(parameters);
			logger.debug(CLASS_NAME+"handleRequest(): Returning XML String "+xmlObject.xmlText());
			return xmlObject.xmlText();
		}

		
		public IResponse handleResponse(Object responseBody) throws Exception {
			if(responseBody==null) return null;
			String responseStr=(String)responseBody;
			if(responseStr.trim().isEmpty()) return null;
			return new XMLResponse(responseStr);
		}
		
		
		/**
		 * Build a xml object from the template mentioned in the parameters. Populate
		 * the template variables with properties in the parameters
		 * 
		 * @param parameters
		 * @return
		 * @throws Exception
		 */
		private XmlObject buildXMLObjectFromTemplate(Map<String,Object> parameters) throws Exception{
			String templateFileName=(String)parameters.get("template");
			logger.debug(CLASS_NAME+"handleRequest(): Template File Name is="+templateFileName);
			String content=getTemplate(templateFileName);
			if(content==null || content.isEmpty()) return null;
			parameters.remove("template");
			
			provisioningParser.setParams(parameters);
			return provisioningParser.parseXML(content);
		}

		/**
		 * Build a parameters list from the template and response xml . Primarily done
		 * during recon operations
		 * 
		 * @param parameters
		 * @return
		 * @throws Exception
		 */
		
		public Map<String,Object> buildParamsFromTemplate(String templateFileName, Object responseObject) throws Exception{
			String content=getTemplate(templateFileName);
			if(content==null || content.isEmpty()) return null;
			
			reconParser.setResponse(responseObject);
			Map<String,Object> params = reconParser.parseXML(content);
			return params;
		}

		/**
		 * Get template content. 
		 * 
		 * @param templateKey
		 * @return
		 */
		public String getTemplate(String templateKey){
			/*if(templates.get(fileName)==null){
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
				templates.put(fileName, content);
			}*/
			return  templates.get(templateKey);
	}
		
		/**
		 * Get the value from the xml object based on fieldName
		 * 
		 * @param dataString xml response
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
			//value=xmlPath.read(dataString, path);
			return value;
		}
		
		
		
		public String[] getAttributeNames(Object dataObject) throws Exception {
			Element element= ((Element)dataObject);
			NamedNodeMap nodeMap=element.getAttributes();
			String [] attributes=new String[nodeMap.getLength()];
			for(int i=0;i<nodeMap.getLength();i++){
				attributes[i]=nodeMap.item(i).getNodeName();
			}
			return null;
		}
		
		
		public Object getAttributeValue(Object dataObject, String attrName) throws Exception{
				//Object value=((Element)dataObject).getAttributeValue(attrName);
			 	Element element= ((Element)dataObject);
			 	return element.getElementsByTagName(attrName).item(0).getTextContent();
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
		
			return dataString;
		}

		
		
	}
