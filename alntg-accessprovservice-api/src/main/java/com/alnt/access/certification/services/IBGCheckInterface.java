package com.alnt.access.certification.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alnt.access.certification.model.ICertificationInformation;
import com.alnt.access.certification.model.IUserCertificationRequest;
import com.alnt.access.certification.model.UserBGCheckData;
import com.alnt.access.certification.model.UserBGCheckRequest;
import com.alnt.extractionconnector.common.service.ISearchCallback;
import com.alnt.extractionconnector.exception.ExtractorConnectionException;
import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public interface IBGCheckInterface {
	public List<UserBGCheckData> performBGCheck(
			List<UserBGCheckRequest> userBGCheckRequests) throws Exception;
	
	public Map<String,String> performAsyncCheck(List<UserBGCheckRequest> userBGCheckRequests) throws Exception;
	
	public List<UserBGCheckData> checkStatus(Map<String,String> statusToken)throws Exception;
	
}
