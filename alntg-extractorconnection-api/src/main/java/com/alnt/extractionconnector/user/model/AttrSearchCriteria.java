package com.alnt.extractionconnector.user.model;

import java.util.Map;

public class AttrSearchCriteria {
	private Map<String,Object> searchFilters;
	public Map<String, Object> getSearchFilters() {
		return searchFilters;
	}
	public void setSearchFilters(Map<String, Object> searchFilters) {
		this.searchFilters = searchFilters;
	}
	public String getOperand() {
		return operand;
	}
	public void setOperand(String operand) {
		this.operand = operand;
	}
	private String operand;

}
