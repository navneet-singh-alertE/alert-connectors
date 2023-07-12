package com.alnt.extractionconnector.user.calendar.event.services;

import java.util.List;
import java.util.Map;

import com.alnt.extractionconnector.common.model.IUserInformation;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IUserCalendarEventExtractionInterface {
	/**
	 * 
	 * @param options
	 * @param deltaLinkMap
	 * @param fetchSize
	 * @param searchCriteria
	 * @return
	 * @throws ExtractorConnectionException
	 */
	public List<IUserInformation> getAllUsersCalEvents(Map<String, List<ExtractorAttributes>> options, Map<String,Object> deltaLinkMap, int fetchSize,
			Map<String, String> searchCriteria) throws ExtractorConnectionException ;
}
