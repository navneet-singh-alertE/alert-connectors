package com.alnt.restconnector.provisioning.commons;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONResponse implements IResponse {

	private JSONObject jsonObject=null;
	
	public JSONResponse(String responseBody){
		setResponseObject(responseBody);
	}
	
	
	public Object getResponseObject(){
		return this.jsonObject;
	}

	
	public void setResponseObject(String responseBody)  {
		if(responseBody!=null)
			try {
				this.jsonObject=new JSONObject(responseBody);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}
	
	
	public boolean hasAttribute(String attrName) {
		if(attrName==null||attrName.isEmpty()) return false;
		return jsonObject.has(attrName);
	}

	
	public Object getAttributeValue(String attrName) throws Exception {
		Object value=jsonObject.get(attrName);
		return value;
	}
	
	/**
	 * Get values from jsonobject for groups/roles or list of users
	 * 
	 * @param tagName eg. "user", "group" to fetch array of elements
	 * @param identificationKey From the array retrieve the sub field value which signifies name of the group
	 * 
	 * @return List of users/groups or roles
	 */
	
	public List getValues(String tagName, String identificationKey) throws Exception{
		if(!jsonObject.has(tagName)) return null;
		
		JSONArray jsonArray=jsonObject.getJSONArray(tagName);
		List values=new ArrayList<Object>();
		for(int i=0;i<jsonArray.length();i++){
			String value=jsonArray.getJSONObject(i).getString(identificationKey);
			values.add(value);
		}
		return values;
	}
	
	/**
	 * Return array of individual data strings that can be manipulated
	 * 
	 * @param tagName name of the tag that denotes the array of objects in the json eg groups , users
	 * 
	 * @return List of entities eg emails , addresses 
	 */
	
	public List getValues(String tagName) throws Exception{
		if(!jsonObject.has(tagName)) return null;
		
		JSONArray jsonArray=jsonObject.getJSONArray(tagName);
		List values=new ArrayList<Object>();
		for(int i=0;i<jsonArray.length();i++){
			//String dataString=jsonArray.getJSONObject(i).toString();
			Object object=jsonArray.get(i);
			if(object instanceof JSONObject)
				values.add(jsonArray.getJSONObject(i));
			else
				values.add(object);
		}	
		return values;
	}
	
	/**
	 * 
	 */
	
	public List getValuesBasedOnInternalArray(String headeTagName,String arrayTagName) throws Exception{
		if(!jsonObject.has(headeTagName)) return null;
		
		JSONArray jsonArrayComplete=jsonObject.getJSONArray(headeTagName);
		List values=new ArrayList<Object>();
		for(int i=0;i<jsonArrayComplete.length();i++){
			//String dataString=jsonArray.getJSONObject(i).toString();
			Object object=jsonArrayComplete.get(i);
			if(object instanceof JSONObject)
			{
				JSONObject singleJSONObj =jsonArrayComplete.getJSONObject(i);
				if(!singleJSONObj.has(arrayTagName)) 
					return null;
				
				else
				{
					JSONObject commonPartOfJson = new JSONObject(singleJSONObj, JSONObject.getNames(singleJSONObj));
					commonPartOfJson.remove(arrayTagName);
					JSONArray jsonArray1= singleJSONObj.getJSONArray(arrayTagName);
					for(int j=0;j<jsonArray1.length();j++)
					{
						JSONObject arrayPartOfJson =jsonArray1.getJSONObject(j);
						JSONObject newjson = new JSONObject();
						for (String key : JSONObject.getNames(arrayPartOfJson)) {
							newjson.put(arrayTagName+"#"+key, arrayPartOfJson.get(key));
						}
						for (String key : JSONObject.getNames(commonPartOfJson)) {
							newjson.put(key, commonPartOfJson.get(key));
						}
						values.add(newjson);
					}
					
				}
			}
			else
				values.add(object);
		}	
		return values;
	}


}
