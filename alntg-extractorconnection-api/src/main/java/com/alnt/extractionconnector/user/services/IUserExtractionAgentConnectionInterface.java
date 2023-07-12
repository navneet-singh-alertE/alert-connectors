package com.alnt.extractionconnector.user.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.ReconExtensionInfo;
import com.alnt.extractionconnector.common.service.IExtractionEventCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IUserExtractionAgentConnectionInterface {

	public void getAllUsersWithCallback(Map<String, List<ExtractorAttributes>> options, int intFetchSize,
			Map<String, String> searchCriteria, IExtractionEventCallback callback) throws ExtractorConnectionException;

	public void getIncrementalUsersWithCallback(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,
			int intFetchSize, Map<String, String> searchCriteria, IExtractionEventCallback callback)
					throws ExtractorConnectionException;

	public void getIncrementalUsersWithCallback(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,
			int intFetchSize, Map<String, String> searchCriteria, IExtractionEventCallback callback,
			ReconExtensionInfo reconExtensionInfo) throws ExtractorConnectionException;
}
