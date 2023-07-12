package com.alnt.access.provisioning.services;

import java.util.List;
import java.util.Map;

import com.alnt.access.provisioning.model.IProvisioningResult;

public abstract class BaseAbstractConnectionInterface implements
		IConnectionInterface {

	public IProvisioningResult create(Long requestNumber, List roles,
			Map parameters, List requestDetails, Map<String, String> attMapping, Map<String,String> validateAttr)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
