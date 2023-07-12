package com.alnt.extractionconnector.role.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IRoleInformation;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IRoleExtractionConnectionInterface {

	public List getRoles(String searchString) throws ExtractorConnectionException;

	public List getAllRoles(String searchString) throws ExtractorConnectionException;

	public List getRolesForUser(String user) throws ExtractorConnectionException;

	public boolean supportsProvisioning();

	/**
	 * This is a safe alternative to search for large number of roles. Instead
	 * of return a list which potentially can hold millions of records, the
	 * caller have to pass in the logic to be called for each matched role.
	 *
	 * @param searchString
	 * @param callback
	 * @throws Exception
	 */
	public void searchRoles(String searchString, ISearchCallback callback) throws ExtractorConnectionException;

	// Both apis will be called till the returned map is empty or the returned
	// map size is less than the fetch size.
	/*
	 * getAllRoles() : Method returns all the users from the external system
	 * with the pagination Support input Params : Map<String,
	 * ExtractorAttributes>: Key=>External System Attribute. Extractor Attribute
	 * will contain the name of the alert attribute with the type and format
	 * information. If the format is empty, default formatting will be applied.
	 * Output Params: IRoleInformation
	 */
	public List<IRoleInformation> getAllRoles(Map<String, List<ExtractorAttributes>> options, int lastRunIndex,
			int intFetchSize, Map<String, String> searchCriteria) throws ExtractorConnectionException;

	public List<IRoleInformation> getIncrementalRoles(Date lastRunDate, Map<String, List<ExtractorAttributes>> options,
			int lastRunIndex, int intFetchSize, Map<String, String> searchCriteria) throws ExtractorConnectionException;

	public void getAllRoles(Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback) throws ExtractorConnectionException;

	public void getIncrementalRoles(Date lastRunDate, Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback) throws ExtractorConnectionException;
}
