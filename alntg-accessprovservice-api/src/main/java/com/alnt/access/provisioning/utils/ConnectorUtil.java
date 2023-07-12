package com.alnt.access.provisioning.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Logger;

public class ConnectorUtil {
	
	public static void logInputParamters(Map parameters, Set<String> exclusions,Logger logger){
		StringBuffer parameterValues  = new StringBuffer();
		try{
		Iterator iter = parameters.keySet().iterator();		
		while(iter.hasNext()){
			String paramName = (String)iter.next();
			Object paramValue = parameters.get(paramName);
			if(parameterValues.length()>0){
				parameterValues.append(", ");
			}
			parameterValues.append(paramName);
			parameterValues.append("=");
			if(paramValue!=null){					
				if(paramValue instanceof String){
					if(exclusions!=null && exclusions.contains(paramName)&&!((String)paramValue).isEmpty()){
						parameterValues.append("************");	
						
					}else{
						parameterValues.append(paramValue);				
					}
				}
				else{
					logger.debug("Parameter Name==>"+ paramName +" Not a String and Value is provided");					
				}
			}else{
				parameterValues.append("NULL");				
			}
		}
		}catch (Exception e) {
			logger.error("Exception logging parameters"+e.getMessage());
		}
		logger.info("Parameter Values ==> "+parameterValues.toString());
		System.out.println("Input Parameter Values ==>"+parameterValues.toString());
	}
	
	public static void logDBQuery(String sql,Map parameters, Set<String> exclusions,Logger logger, String prefix){
		if(prefix==null)
			prefix="SQL statement ==> ";
		String sqlStatement  = new String(sql);
		try{
			Iterator iter = parameters.keySet().iterator();		
			while(iter.hasNext()){
				String paramName = (String)iter.next();
				Object paramValue = parameters.get(paramName);
				if(paramValue!=null){					
					if(paramValue instanceof String){
						if(exclusions!=null && exclusions.contains(paramName)&&!((String)paramValue).isEmpty())
							sqlStatement.replace(paramValue.toString(), "************");
					
						}
					}
				}
		}catch (Exception e) {
		logger.error("Exception logging parameters"+e.getMessage());
		}
		
		logger.debug(prefix+" "+sqlStatement);
	}
	
	public static Set<String> convertStringToList(String strToSplit, String delimiter){
		Set<String> returnItems = new HashSet<String>();
		try{
			if(strToSplit==null || strToSplit.isEmpty() || delimiter==null || delimiter.isEmpty()){
				return returnItems;
			}
			StringTokenizer st = new StringTokenizer(strToSplit, delimiter);			
			while(st.hasMoreTokens()){
				returnItems.add(st.nextToken());	
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnItems;
	}

}
