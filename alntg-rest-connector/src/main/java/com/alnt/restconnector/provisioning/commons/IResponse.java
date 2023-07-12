package com.alnt.restconnector.provisioning.commons;

import java.util.List;


public interface IResponse {
	public boolean hasAttribute(String attrName);
	public Object getAttributeValue(String attrName) throws Exception;
	public void setResponseObject(String responseBody) throws Exception;
	public List getValues(String tagName, String fieldKey  ) throws Exception;
	public List getValues(String tagName) throws Exception;
	public Object getResponseObject();
	List getValuesBasedOnInternalArray(String headeTagName, String arrayTagName)
			throws Exception;
}
