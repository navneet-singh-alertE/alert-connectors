package com.alnt.access.provisioning.model;

import java.util.Date;

public interface IAssetInformation {
	
	public Long getAssetId();
	public void setAssetId(Long assetId);
	public String getAssetName();
	public void setAssetName(String assetName);
	public Date getAssetValidFrom();
	public void setAssetValidFrom(Date assetValidFrom);
	public Date getAssetValidTo();
	public void setAssetValidTo(Date assetValidTo);

}
