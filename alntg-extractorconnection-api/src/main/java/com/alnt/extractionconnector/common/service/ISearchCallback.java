package com.alnt.extractionconnector.common.service;

import java.util.List;

import com.alnt.extractionconnector.common.model.ReconExtensionInfo;

public interface ISearchCallback {
	public void processSearchResult(List details) throws Exception;
	public void processSearchResult(List details,ReconExtensionInfo reconExtensionInfo) throws Exception;
}
