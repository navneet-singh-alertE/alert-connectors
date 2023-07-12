package com.alnt.restconnector.provisioning.commons;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.xmlbeans.xmlDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLResponse implements IResponse {

	private Document xmlDocument=null;
	
	public XMLResponse(String responseBody){
		setResponseObject(responseBody);
	}
	
	
	public Object getResponseObject(){
		return this.xmlDocument;
	}

	
	public void setResponseObject(String responseBody)  {
		if(responseBody!=null)
			try {
				this.xmlDocument= parseXml(responseBody);//xmlDocument.Factory.parse(responseBody);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public boolean hasAttribute(String attrName) {
		if(attrName==null||attrName.isEmpty()) return false;
		List<Object> values=null;
		try {
			values= getValues(attrName);
		} catch (Exception e) {
			return false;
		}
		return values.size()>0;
	}

	
	public Object getAttributeValue(String attrName) throws Exception {
		//Object value=xmlDocument.selectPath(attrName);
		 List values=getValues(attrName);
		 if(values==null || values.isEmpty()) return null;
		 Element element= (Element)values.get(0);
		 return element.getTextContent();
		 
	}
	
	/**
	 * Get values from object for groups/roles or list of users
	 * 
	 * @param tagName eg. "user", "group" to fetch array of elements
	 * @param identificationKey From the array retrieve the sub field value which signifies name of the group
	 * 
	 * @return List of users/groups or roles
	 */
	
	public List getValues(String tagName, String identificationKey) throws Exception{
		//if(!jsonObject.has(tagName)) return null;
		
		//JSONArray jsonArray=jsonObject.getJSONArray(tagName);
		List values=new ArrayList<Object>();
		/*for(int i=0;i<jsonArray.length();i++){
			String value=jsonArray.getJSONObject(i).getString(identificationKey);
			values.add(value);
		}*/
		return values;
	}
	
	/**
	 * Return array of individual data strings that can be manipulated
	 * 
	 * @param tagName name of the tag that denotes the array of objects in the xml eg groups , users
	 * 
	 * @return List of entities eg emails , addresses 
	 */
	
	public List getValues(String tagName) throws Exception{
		List values=new ArrayList<Object>();
		//get the root element
		Element docElement = xmlDocument.getDocumentElement();
		
		NodeList nl = docElement.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				//Element el = (Element)nl.item(i);
				Node node = nl.item(i);
				//System.out.println("\nCurrent Element :" + node.getNodeName());
		 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					//System.out.println("User Id : " + element.getElementsByTagName("cn").item(0).getTextContent());
					//System.out.println("First Name : " + element.getElementsByTagName("plasecFname").item(0).getTextContent());
					values.add(element);
				}
				//values.add(el.getTextContent());
				
			}
		}
		
		return values;
	}

	/** 
	 * Create a document object from xml
	 * 
	 * @param xmlString
	 */
	public static Document parseXml(String xmlString){
		Document document=null;
		try {
			
			// Added for special character handling
			if(xmlString!=null && !xmlString.isEmpty()){
                if(!xmlString.startsWith("<")){
                      xmlString = xmlString.substring(xmlString.indexOf("<"), xmlString.length());
                }

			//get the factory
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			//Using factory get an instance of document builder
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			document = docBuilder.parse(new InputSource(new StringReader(xmlString)));
			}

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	
	private String getTextValue(Element ele, String tagName) {
			String textVal = null;
			NodeList nl = ele.getElementsByTagName(tagName);
			if(nl != null && nl.getLength() > 0) {
				Element el = (Element)nl.item(0);
				textVal = el.getFirstChild().getNodeValue();
			}

			return textVal;
		}


		/**
		 * Calls getTextValue and returns a int value
		 */
		private int getIntValue(Element ele, String tagName) {
			//in production application you would catch the exception
			return Integer.parseInt(getTextValue(ele,tagName));
		}

		
		public List getValuesBasedOnInternalArray(String headeTagName,
				String arrayTagName) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

}
