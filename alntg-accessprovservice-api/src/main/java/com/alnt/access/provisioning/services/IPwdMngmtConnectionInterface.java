package com.alnt.access.provisioning.services;

import com.alnt.access.provisioning.model.IProvisioningResult;

public interface IPwdMngmtConnectionInterface {
	
	public boolean isUserLockedOut(String userId) throws Exception;
	public Long isUserLockedOutWithStatus(String userId)	throws Exception;
	public IProvisioningResult unlockLockedAccount(String userId) throws Exception;

}
