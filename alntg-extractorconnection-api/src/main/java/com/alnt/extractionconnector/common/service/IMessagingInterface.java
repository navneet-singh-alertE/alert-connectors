package com.alnt.extractionconnector.common.service;
import java.util.Map;

public interface IMessagingInterface {
		public void init();
		public void setParameters(Map params);
		public Object parseMessage(String message);
		public boolean processValues(Object values);
}