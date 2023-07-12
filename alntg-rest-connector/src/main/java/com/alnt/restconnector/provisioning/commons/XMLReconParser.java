package com.alnt.restconnector.provisioning.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alnt.fabric.component.rolemanagement.search.IRoleInformation;
import com.alnt.restconnector.provisioning.constants.RestConstants;


/**
 * Extracts parameters from response xml and sends it back to the 
 * application
 * 
 * @author amit.ghildiyal
 *
 */
public class XMLReconParser {
	private int maxLength=-1;
	//private StringBuilder key=new StringBuilder();
	//private StringBuilder value=new StringBuilder();
	private Map<String,Object> params=null;;
	private String ROLE_IDENTIFIER=null;
	private String BADGE_IDENTIFIER=null;
	private String applicationDateFormat=null;
	private String provisioningDateFormat=null;
	private Object responseObject=null;
	private String responseString=null;
	
	
	public void setConnectorParams(Map<String,Object> conParams) {
		this.ROLE_IDENTIFIER=(String)conParams.get(RestConstants.SYS_CON_ATTR_ROLE_IDENTIFIER_KEY);
		this.applicationDateFormat=(String)conParams.get(RestConstants.SYS_CON_ATTR_AE_DATE_FORMAT);
		this.provisioningDateFormat=(String)conParams.get(RestConstants.SYS_CON_ATTR_PROV_DATE_FORMAT);
		//this.BADGE_IDENTIFIER
	}

	public  void setParams(Map params) {
		this.params=params;
	}
	
	
	public void setResponse(Object responseObject) {
		this.responseObject=responseObject;
		//this.responseString=responseObject.toString();
	}
	
	public Map<String,Object> parseXML(String content) throws Exception {
		Map<String,Object> extractedAttributes=new LinkedHashMap<String,Object>();
		if(content==null || content.trim().equals(""))
			throw new IllegalArgumentException("Input XML string is null/empty...");
		
		Document xmlTemplateDoc=XMLResponse.parseXml(content);
		Element templateElement = xmlTemplateDoc.getDocumentElement();
		Element responseElement=(Element)responseObject;
		NodeList nl =templateElement.getChildNodes();
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				Node templateNode = nl.item(i);
				if (templateNode.getNodeType() == Node.ELEMENT_NODE) {
					String templateKey=templateNode.getNodeName();
					String templateValue=templateNode.getTextContent();
					//List values= responseObject.getValues("");
					NodeList n2=responseElement.getElementsByTagName(templateKey);
					if(templateKey!=null && n2!=null && n2.item(0)!=null){
						if(templateValue.indexOf("[$")>=0){//is an array
							StringBuilder value=new StringBuilder();
							for(int j = 0 ; j< n2.getLength();j++) {
								value.append(responseElement.getElementsByTagName(templateKey).item(j).getTextContent()).append(";");
							}
							String attrName=templateValue.replace("[$","").replace("]","");//get alert attribute tag, replace $ symbol
							value= value.deleteCharAt(value.length()-1);
							extractedAttributes.put(attrName, value.toString());
						}else{
							String value=responseElement.getElementsByTagName(templateKey).item(0).getTextContent();
							String attrName=templateValue.replace("$","");//get alert attribute tag, replace $ symbol
							extractedAttributes.put(attrName, value);
						}
					}
				}
			}
		}
		//System.out.println(extractedAttributes);
		return  extractedAttributes;
	}
	
	
}
