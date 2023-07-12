package com.alnt.extractionconnector.common.service;

import java.io.IOException;

public interface ISearchCallbackExtension  extends ISearchCallback {
	public void postProcess() throws Exception;
	public void close(boolean success) throws IOException;
	public void refresh() throws IOException;
}
