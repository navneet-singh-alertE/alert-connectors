package com.alnt.restconnector.provisioning.utils;

import org.apache.commons.codec.binary.Base64;

public class Utils {
	
	/**
	 * Decode the  data using Base64 decoder.
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] decodeValue(String value){
		//The slash (/) character is replaced with the underscore (_) character.
		//The plus sign (+) character is replaced with the hyphen (-) character.
		//The equals sign (=) character is replaced with the asterisk (*).
		value=value.replaceAll("_","/");
		value=value.replaceAll("-","+");
		value=value.replaceAll("\\*","=");
		return Base64.decodeBase64(value.getBytes());
	}
	
	/**
	 * Encode the  data using Base64 encoder.
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeValue(byte[] data){
		//The slash (/) character is replaced with the underscore (_) character.
		//The plus sign (+) character is replaced with the hyphen (-) character.
		if(data==null || data.length<0) return null;
		
		String value=new String(Base64.encodeBase64(data));
		value=value.replaceAll("/","_");
		value=value.replaceAll("\\+","-");
		//value=value.replaceAll("=","\\*");
		return value;
	}
}
