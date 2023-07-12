package com.alnt.restconnector.provisioning.services;

import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpMethod;

public interface IConnector {
	public String getSystemName();
	public void setAccessToken(String token);
	public void setTokenBased(boolean value);
	public boolean getTokenBased();
	public String getAccessToken();
	public boolean checkTokenValidity(String token);
	public void setConnectorParams(Map connectorParams);
	public Map getConnectorParams();
	public String getNewAccessToken();
	public String getLockedAttributeName();
	public String getUserIdentifierKey();
	public String getInstanceUrl();
	public String getFormat();
	public boolean getSSL();
	public String getImageAttributeName();
	public void releaseConnection(HttpMethod method);
	public String getReconRolesTag();
	public String getReconUsersTag();
	public String getReconBadgesTag();
	public String getReconImagesTag();
	public String getRoleIdentifierKey();
	public String getDomain();
	public String getCustomerId();
	public String getUserLastRunTimeField();
	public String getRoleLastRunTimeField();
	public void executeRequest(HttpMethod method, boolean showLog, boolean showResponse) throws Exception;
	public String getReconDateFormat();
	public String getAlertApplicationDateFormat();
	public String getProvisioningDateFormat();
	public String getCreatedUserIdentifierKey();
	public String getReconCertificationsTag();
	public String getBadgeIdentifierKey();
	public String getBadgeLastRunTimeField();
	public Set<String> getExcludeLogAttributesList();
}
