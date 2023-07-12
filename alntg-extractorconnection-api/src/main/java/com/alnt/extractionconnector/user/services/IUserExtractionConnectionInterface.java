package com.alnt.extractionconnector.user.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IUserInformation;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IUserExtractionConnectionInterface {
	
	//Internally all the three will call the invoke methods of the connector.	
	//Accepts Set of input params, List of output params and returns single row map
	public Map getUsers (Map inputParams, List outputParams) throws ExtractorConnectionException;	
	//Accepts Set of input params, List of output params and returns multiple row map
	public Map getAllUsers (Map inputParams, List outputParams) throws ExtractorConnectionException;    
	//Accepts Set of input params, List of output params and returns void
	public void invokeUsers (Map inputParams, List outputParams) throws ExtractorConnectionException;
    
    // Both apis will be called till the returned map is empty or the returned map size is less than the fetch size.
	/*
	 * getAllUsers() : Method returns all the users from the external system with the pagination Support
	 * input Params : Map<String, ExtractorAttributes>: Key=>External System Attribute. Extractor Attribute will contain the name of the alert attribute with the 
	 * 				  type and format information. If the format is empty, default formatting will be applied.
	 * Output Params: IUserInformation
	 */
    public List<IUserInformation> getAllUsers(Map<String, List<ExtractorAttributes>> options, int lastRunIndex, int intFetchSize,Map<String,String> searchCriteria) throws ExtractorConnectionException;
    /*
	 * getIncrementalUsers() : Method returns all the incremental users from the external system with the pagination Support
	 * input Params : Map<String, ExtractorAttributes>: Key=>External System Attribute. Extractor Attribute will contain the name of the alert attribute wth the 
	 * 				  type and format information. If the format is empty, default formatting will be applied.
	 * Output Params: IUserInformation
	 */
    public List<IUserInformation> getIncrementalUsers(Date lastRunDate, Map<String, List<ExtractorAttributes>> options, int lastRunIndex, int intFetchSize,Map<String,String> searchCriteria) throws ExtractorConnectionException;
    
    public void getAllUsersWithCallback(Map<String, List<ExtractorAttributes>> options, int intFetchSize,Map<String,String> searchCriteria,ISearchCallback callback) throws ExtractorConnectionException;
    public void getIncrementalUsersWithCallback(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,  int intFetchSize,Map<String,String> searchCriteria,ISearchCallback callback) throws ExtractorConnectionException;
    //Supports callbacks
    public void getUsers(String searchString, ISearchCallback callback) throws  ExtractorConnectionException; 
    
}
