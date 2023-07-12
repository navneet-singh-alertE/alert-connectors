package com.alnt.extractionconnector.user.model;

import java.util.List;
import java.util.Map;

public class MultiValueAttrSearchResult {
	private List<Map<String,Object>> attrValues;
	private Long totalRecordCount;
	public List<Map<String, Object>> getAttrValues() {
		return attrValues;
	}
	public void setAttrValues(List<Map<String, Object>> attrValues) {
		this.attrValues = attrValues;
	}
	public Long getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(Long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}



}
