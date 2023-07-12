package com.alnt.access.certification.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.access.certification.model.ICertificationInformation;
import com.alnt.access.certification.model.IUserCertificationRequest;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface ICertificationInterface {
	public List<ICertificationInformation> evaluateUserCertifications(
			IUserCertificationRequest userCertResult) throws Exception;

	public void getAllUserCertifications(
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
			throws ExtractorConnectionException;

	public void getIncrementalUserCertifications(Date lastRunDate,
			Map<String, List<ExtractorAttributes>> options, int fetchSize,
			Map<String, String> searchCriteria, ISearchCallback callback)
			throws ExtractorConnectionException;
}
