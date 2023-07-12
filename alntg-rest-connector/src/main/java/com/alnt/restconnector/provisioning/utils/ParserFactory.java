

package com.alnt.restconnector.provisioning.utils;

import com.alnt.restconnector.provisioning.commons.XMLReconParser;
import com.json.config.handlers.ConfigHandler;
import com.json.config.handlers.XmlConfigHandler;

/**
 * Creates Parsers based on input. Creates either recon parser
 * or provisioning parser. The parsers read the template and then as per the
 * template either create request object from the parameters sent by the connector
 * or creates response map based on the response json sent by the external system.
 * 
 * @author amit.ghildiyal
 *
 */
public class ParserFactory {
	private static ParserFactory instance=null;
	
	private ParserFactory()
	{
	}
	
	public static ParserFactory getInstance() {
		if (instance == null) {
			synchronized (ParserFactory.class) {
				if (instance == null) {
					instance = new ParserFactory();
				}
			}
		}

		return instance;
	}
	
	public JSONProvisioningParser getJSONProvisioningParser()
	{
		JSONProvisioningParser parser=new JSONProvisioningParser();
		ConfigHandler configHandler=new XmlConfigHandler();
		parser.setConfigHandler(configHandler);
		return parser;
	}
	
	public JSONReconParser getJSONReconParser()
	{
		JSONReconParser parser=new JSONReconParser();
		ConfigHandler configHandler=new XmlConfigHandler();
		parser.setConfigHandler(configHandler);
		return parser;
	}
	
	public XMLProvisioningParser getXMLProvisioningParser()
	{
		XMLProvisioningParser parser=new XMLProvisioningParser();
		//ConfigHandler configHandler=new XmlConfigHandler();
		//parser.setConfigHandler(configHandler);
		return parser;
	}
	
	public XMLReconParser getXMLReconParser()
	{
		XMLReconParser parser=new XMLReconParser();
		//ConfigHandler configHandler=new XmlConfigHandler();
		//parser.setConfigHandler(configHandler);
		return parser;
	}
	
	
	
	/*
	@SuppressWarnings("rawtypes")
	public JSONProvisioningParser getJSONProvisioningParser(ValidationConfigType type)
	{
		JSONProvisioningParser parser=new JSONProvisioningParser();
		
		ConfigHandler configHandler=null;
		
		switch(type)
		{
			case JSON: configHandler=new JsonConfigHandler();
					   break;
			case XML:
			default: configHandler=new XmlConfigHandler();
		}

		configHandler.setParserSelfInstance(parser);
		
		parser.setConfigHandler(configHandler);
		
		parser.setCollectionTypes(new CollectionTypes(){
			
			public List getListType() {
				return new LinkedList();
			}
			
			public Map getMapType() {
				return new TreeMap();
			}
		});
		
		return parser;
	}
	
	public JSONProvisioningParser getJSONProvisioningParser(CollectionTypes collectionTypes)
	{
		JSONProvisioningParser parser= getJSONProvisioningParser();
		parser.setCollectionTypes(collectionTypes);
		return parser;
	}
	
	public JSONProvisioningParser getJSONProvisioningParser(ValidationConfigType type, CollectionTypes collectionTypes)
	{
		JSONProvisioningParser parser=getJSONProvisioningParser(type);
		parser.setCollectionTypes(collectionTypes);
		return parser;
	}
	
	*/
}