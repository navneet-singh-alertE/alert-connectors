package com.alnt.access.provisioning.model;


public class AttrSearchResult implements IAttrSearchResult{
   private String keyFieldValue;
   private String valueFieldValue;
   
   public AttrSearchResult(String keyFieldValue, String valueFieldValue) {
		this.keyFieldValue = keyFieldValue;
		this.valueFieldValue = valueFieldValue;
	}
	public AttrSearchResult() {
		
	}

   	public String getKeyFieldValue() {
		return keyFieldValue;
	}
	public void setKeyFieldValue(String keyFieldValue) {
		this.keyFieldValue = keyFieldValue;
	}
	public String getValueFieldValue() {
		return valueFieldValue;
	}
	public void setValueFieldValue(String valueFieldValue) {
		this.valueFieldValue = valueFieldValue;
	}
   
}
