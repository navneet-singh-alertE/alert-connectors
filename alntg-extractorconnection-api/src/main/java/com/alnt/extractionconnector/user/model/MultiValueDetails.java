package com.alnt.extractionconnector.user.model;

import java.util.List;

import com.alnt.extractionconnector.common.constants.IExtractionConstants;

public class MultiValueDetails {

	private IExtractionConstants.MULTI_VALUES_OPERATION operation = IExtractionConstants.MULTI_VALUES_OPERATION.REPLACE;
	private List<MultiValue> multiValues;
	public IExtractionConstants.MULTI_VALUES_OPERATION getOperation() {
		return operation;
	}
	public void setOperation(IExtractionConstants.MULTI_VALUES_OPERATION operation) {
		this.operation = operation;
	}	
	public List<MultiValue> getMultiValues() {
		return multiValues;
	}
	public void setMultiValues(List<MultiValue> multiValues) {
		this.multiValues = multiValues;
	}	
}
