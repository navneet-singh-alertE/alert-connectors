package com.alnt.access.provisioning.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.access.provisioning.model.SuperUserProvisionResult;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface ISuperUserConnectionInterface {

	public List<SuperUserProvisionResult> assignUserToSuperUser(Long requestNumber, List roles,
			Map parameters,
			List requestDetails,Map<String,String> attMapping)
			throws Exception;
	public void getAllSuperUsersWithCallback(Map<String, List<ExtractorAttributes>> options, int intFetchSize,Map<String,String> searchCriteria,ISearchCallback callback) throws ExtractorConnectionException;
    public void getIncrementalSuperUsersWithCallback(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,  int intFetchSize,Map<String,String> searchCriteria,ISearchCallback callback) throws ExtractorConnectionException;
    
}
