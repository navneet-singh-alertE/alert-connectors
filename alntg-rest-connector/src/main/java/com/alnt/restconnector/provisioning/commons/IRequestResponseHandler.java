package com.alnt.restconnector.provisioning.commons;

import java.util.Map;


public interface IRequestResponseHandler {

		public String  handleRequest(Map<String,Object> parameters) throws Exception;
		public IResponse handleResponse(Object responseBody) throws Exception;
		public Object getAttributeValue(String dataString, String attributeName) throws Exception;
		public String[] getAttributeNames(Object dataObject) throws Exception;
		public Object getAttributeValue(Object dataObject, String attributeName) throws Exception;
		public String getErrorMessage(String responseBody);
		public Map<String,Object> buildParamsFromTemplate(String templateFileName,Object responseObject) throws Exception;
		public void setConnectorParams(Map connectorParams);
}
