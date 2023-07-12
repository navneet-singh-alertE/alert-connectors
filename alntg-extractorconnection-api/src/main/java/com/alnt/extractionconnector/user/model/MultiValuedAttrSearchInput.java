package com.alnt.extractionconnector.user.model;

import java.util.List;

public class MultiValuedAttrSearchInput {
	private String attrName;
	private List<String> attrProperties;
	private AttrSearchCriteria searchCriteria;
	private String pageSize;
	private String pageNumber;
	public enum SearchOrder {
		ASC,
        DESC;
    }
	private String orderByField;
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public List<String> getAttrProperties() {
		return attrProperties;
	}
	public void setAttrProperties(List<String> attrProperties) {
		this.attrProperties = attrProperties;
	}
	public AttrSearchCriteria getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(AttrSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getOrderByField() {
		return orderByField;
	}
	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	

}
