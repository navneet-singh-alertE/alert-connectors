package com.alnt.restconnector.provisioning.commons;

import java.util.Map;



public class RequestResponseHandlersFactory {
	
		public static IRequestResponseHandler getRequestResponseHandler(String type, Map connectorParams){
			IRequestResponseHandler responseHandler=null;
			if(("json").equalsIgnoreCase(type)){
				responseHandler= new JSONRequestResponseHandler(connectorParams);
			}else if(("xml").equalsIgnoreCase(type)){
				responseHandler= new XMLRequestResponseHandler(connectorParams);
			}else if(("text").equalsIgnoreCase(type)){
				//Implement if response in text format
			}
			return responseHandler;
		}
}
