package com.alnt.extractionconnector.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.ReconExtensionInfo;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;
import com.alnt.extractionconnector.user.model.MultiValueAttrSearchResult;
import com.alnt.extractionconnector.user.model.MultiValuedAttrSearchInput;

public interface IExtractionExtensionInterface extends IExtractionInterface{
	public void getIncrementalUsersWithCallback(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,  int intFetchSize,Map<String,String> searchCriteria,ISearchCallback callback, ReconExtensionInfo reconExtensionInfo) throws ExtractorConnectionException;
	
	
	/**
	 * 
	 * @param options
	 * @param assetType
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public void getAllAssetsWithCallBack(Map<String, List<ExtractorAttributes>> options, String assetType, int intFetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;

	/**
	 * 
	 * @param lastRunDate
	 * @param options
	 * @param assetType
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public void getIncrementalAssetsWithCallBack( Date lastRunDate, Map<String, List<ExtractorAttributes>> options,String assetType,
			int intFetchSize, Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;
	/**
	 * 
	 * @param options
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @throws ExtractorConnectionException
	 */
	public void getAllLocationsWithCallBack(Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;
	
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
	public void getIncrementalLocationsWithCallBack( Date lastRunDate, Map<String, List<ExtractorAttributes>> options,
			int intFetchSize, Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException;
	
	/**
	 * 
	 * @param attrSearchInput
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public MultiValueAttrSearchResult getMultiValueAttributeVaues(MultiValuedAttrSearchInput attrSearchInput) throws ExtractorConnectionException;
	
	/**
	 * 
	 * @param options
	 * @param masterDataType
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	default void getMasterDataWithCallBack(Map<String, List<ExtractorAttributes>> options, String masterDataType, int intFetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException{
		
	}

	/**
	 * 
	 * @param lastRunDate
	 * @param options
	 * @param masterDataType
	 * @param intFetchSize
	 * @param searchCriteria
	 * @param callback
	 * @param recon
	 * @return
	 * @throws ExtractorConnectionException
	 */
	default void getIncrementalMasterDataWithCallBack( Date lastRunDate, Map<String, List<ExtractorAttributes>> options,String masterDataType,
			int intFetchSize, Map<String, String> searchCriteria, ISearchCallback callback, ReconExtensionInfo recon) throws ExtractorConnectionException{
		
	}
}
