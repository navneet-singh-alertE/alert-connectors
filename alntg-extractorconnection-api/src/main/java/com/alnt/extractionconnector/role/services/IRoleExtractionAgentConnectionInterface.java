package com.alnt.extractionconnector.role.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.service.IExtractionEventCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IRoleExtractionAgentConnectionInterface {

	public void getAllRoles(Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, IExtractionEventCallback callback) throws ExtractorConnectionException;

	public void getIncrementalRoles(Date lastRunDate, Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, IExtractionEventCallback callback) throws ExtractorConnectionException;

	/*public void getIncrementalRoles(Date lastRunDate, Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, IExtractionEventCallback callback,
			ReconExtensionInfo reconExtensionInfo) throws ExtractorConnectionException;*/
}
