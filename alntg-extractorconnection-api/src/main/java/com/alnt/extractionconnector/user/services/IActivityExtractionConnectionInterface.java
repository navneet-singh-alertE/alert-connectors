package com.alnt.extractionconnector.user.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.ReconExtensionInfo;
import com.alnt.extractionconnector.common.service.IExtractionEventCallback;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;


public interface IActivityExtractionConnectionInterface {
	
	/**
	 * 
	 * @param options
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public void getAllActivitiesWithCallBack(Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria, IExtractionEventCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;

	/**
	 * 
	 * @param lastRunDate
	 * @param options
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public void getIncrementalActivitiesWithCallBack( Date lastRunDate, Map<String, List<ExtractorAttributes>> options,
			int intFetchSize, Map<String, String> searchCriteria, IExtractionEventCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;
		
	/**
	 * 
	 * @param userActivityFilter
	 * @return
	 */
	public List<Map<String,String>> getUserActivity(Map<String,Object> userActivityFilter,int fetchSize,int top);
}
