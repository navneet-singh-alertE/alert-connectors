package com.alnt.restconnector.provisioning.utils;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;
import com.alnt.restconnector.provisioning.constants.RestConstants;

/**
 * Parser Used for provisioning purposes. Creates a request object 
 * from the parameters and the request xml  file template
 * 
 * @author Amit.Ghildiyal
 *
 */

public class XMLProvisioningParser {
	
	private int maxLength=-1;
	private String arrayName=null;
	private boolean isValidating=false;
	//private ConfigHandler configHandler=null;
	//private CollectionTypes collectionTypes=null;
	private Map<String,Object> params=null;;
	private String ROLE_IDENTIFIER=null;
	private String BADGE_IDENTIFIER=null;
	private String applicationDateFormat=null;
	private String provisioningDateFormat=null;
	
	
	public void setConnectorParams(Map<String,Object> conParams) {
		this.ROLE_IDENTIFIER=(String)conParams.get(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY);
		this.applicationDateFormat=(String)conParams.get(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT);
		this.provisioningDateFormat=(String)conParams.get(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT);
		//this.BADGE_IDENTIFIER
	}

	public  void setParams(Map params) {
		this.params=params;
	}
	
	

	/**
	 * Replace template xml content with values 
	 * 
	 * @param content
	 * @return
	 * @throws XmlException
	 */

	public XmlObject parseXML(String content) throws XmlException {
		
		//TODO:(this  needs to be enhanced like json parser
		
		if(content==null || content.trim().equals(""))
			throw new IllegalArgumentException("Input XML string is null/empty...");
		
		int index=0;
		String[] lines=new String(content).split("\n");
		StringBuffer replacedXML=new StringBuffer();
		for(String xmlData:lines){
		
			if(xmlData.indexOf("$ROLE_NAME")>=0){
				List<IRoleInformation> roleList=(List<IRoleInformation>)params.get("ROLE_NAME");
				if(roleList!=null){
					StringBuilder sb=new StringBuilder();
					if (roleList.size()>0){
						for(IRoleInformation role:roleList){
							sb.append("\t<").append(ROLE_IDENTIFIER).append(">")
									.append(encodeXMLSpecialChar(role.getName())).append("</").append(ROLE_IDENTIFIER).append(">\n");
						}
					}else{
						//have no element
							sb.append("\t<").append(ROLE_IDENTIFIER).append("></").append(ROLE_IDENTIFIER).append(">\n");
					}
					xmlData=sb.toString();
					replacedXML.append(xmlData).append("\n");
				}
			}else{
				
				if(xmlData.indexOf("$")<=0){//nothing to replace 
					replacedXML.append(xmlData).append("\n");
					continue;
				}
				else if(xmlData.indexOf(">[$")>=0){//is an array comma separated values
					int pos=xmlData.indexOf(">[$");
					String arrayName=xmlData.substring(pos+3,xmlData.indexOf("]<"));
					String value=(String)params.get(arrayName);
					if(value==null) continue;
					String[] arrayValues=value.split(";");
					for(String valueStr:arrayValues){
						valueStr=encodeXMLSpecialChar(valueStr);
						valueStr=xmlData.replace("[$"+arrayName+"]",valueStr);
						replacedXML.append(valueStr).append("\n");
					}
					continue;
				}
				
				for(String key:params.keySet() ){
					if(xmlData.indexOf(">$"+key+"<")>=0){
							Object value=params.get(key);
							if(value==null) break;
							String valueStr="";
							if(value!=null){
								if(value instanceof byte[]){
									valueStr=new String((byte[])value);
								}else
								{
									valueStr = (String)params.get(key);
									valueStr=encodeXMLSpecialChar(valueStr);
								}
							}
							xmlData=xmlData.replaceAll("\\$"+key, valueStr);
							break;
					}
				}
				
				if(xmlData.indexOf("$")<=0){
					replacedXML.append(xmlData).append("\n");
				}
			}
		}
		return  XmlObject.Factory.parse(replacedXML.toString());
	}
	/**
	 * TO replace the special value characters in XML like &,>,<,\,'
	 * @param value
	 * @return
	 */
	private String encodeXMLSpecialChar(String value){
		String returnValue=StringEscapeUtils.escapeXml(value);
		return returnValue;
	}
	
}