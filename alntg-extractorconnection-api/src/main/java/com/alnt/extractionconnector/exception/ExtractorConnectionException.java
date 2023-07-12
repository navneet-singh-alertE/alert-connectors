package com.alnt.extractionconnector.exception;

public class ExtractorConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1430151035293366201L;
	
	private String errorCode;
	private String message;
	private String[] dynamicValues;
	private Boolean retriable = Boolean.FALSE;
	
	public ExtractorConnectionException(){
		super();
	}

	public ExtractorConnectionException(Throwable cause){
		super(cause);
	}
	
	public ExtractorConnectionException( String message) {
		super(message);
		this.message = message;
	}

	public ExtractorConnectionException(String errorCode, String message) {
		super(message);
		this.message = message;
		this.errorCode =  errorCode;		
	}

	public ExtractorConnectionException(String errorCode, String message, String[] dynamicValues) {
		super(message);
		this.errorCode =  errorCode;
		this.message = message;
		this.dynamicValues = dynamicValues;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getDynamicValues() {
		return dynamicValues;
	}
	public void setDynamicValues(String[] dynamicValues) {
		this.dynamicValues = dynamicValues;
	}
	public Boolean isRetriable() {
		return retriable;
	}

	public void setRetriable(Boolean retriable) {
		this.retriable = retriable;
	}
}
