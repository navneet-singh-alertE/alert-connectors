package com.alnt.extractionconnector.common.constants;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alnt.extractionconnector.user.model.ExtractorAttributes;

public class ExtractionConnectorUtil {

	public static final Set<String> getExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,null,null,null,null,null);
	}
	
	public static final Set<String> getUserExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,TRUE, FALSE,FALSE,FALSE,FALSE);
	}

	public static final Set<String> getRoleExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,FALSE, TRUE,FALSE,FALSE,FALSE);
	}

	public static final Set<String> getBadgeExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,FALSE, FALSE,TRUE,FALSE,FALSE);
	}
	
	public static final Set<String> getTokenExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,FALSE, FALSE,FALSE,TRUE,FALSE);
	}
	
	public static final Set<String> getUserCertificationExternalAttributes(Map<String, List<ExtractorAttributes>> options) {
		return getExternalAttributes(options,FALSE, FALSE,FALSE,FALSE,TRUE);
	}
	
	private static final Set<String> getExternalAttributes(Map<String, List<ExtractorAttributes>> options,
			Boolean userAttr, Boolean roleAttr,Boolean badgeAttr,Boolean tokenAttr,Boolean userCertAttr) {
		Set<String> returnParams = new HashSet<String>();
		String extAttrKey = null;
		if(options != null) {
			Iterator<String> keys = options.keySet().iterator();
			ExtractorAttributes extractorAttr = null;
	    	while(keys.hasNext()) {
	    		extAttrKey = keys.next();
	    		List<ExtractorAttributes> extractorAttrs = options.get(keys.next());
	    		Iterator<ExtractorAttributes> iter = extractorAttrs.iterator();
	    		while(iter.hasNext()) {
	    			extractorAttr = iter.next();
	    			if(!returnParams.contains(extractorAttr.getAttributeName())){
		    			if(userAttr != null && userAttr && extractorAttr.isUserAttr()) {
			    			returnParams.add(extAttrKey);
			    		} else if(roleAttr != null && roleAttr && extractorAttr.isRoleAttr()) {
			    			returnParams.add(extAttrKey);
			    		} else if(userCertAttr != null && userCertAttr && extractorAttr.isUserCertificationAttr()) {
			    			returnParams.add(extAttrKey);
			    		} else if(badgeAttr != null && badgeAttr && extractorAttr.isBadgeAttr()) {
			    			returnParams.add(extractorAttr.getAttributeName());
			    		} else if(tokenAttr != null && tokenAttr && extractorAttr.isTokenAttr()) {
			    			returnParams.add(extAttrKey);
			    		} else if(userAttr == null && roleAttr == null && badgeAttr == null && tokenAttr == null){
			    			returnParams.add(extAttrKey);
			    		}
	    			}
	    		}
	    		
	    	}
		}
    	return returnParams;
	}
	
	private static final Map<String, List<ExtractorAttributes>> getExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options,
			Boolean userAttr, Boolean roleAttr,Boolean badgeAttr,Boolean tokenAttr,Boolean userCertAttr ,Boolean enterpriseRoleAttr) {
		Map<String,List<ExtractorAttributes>> retExtAtrVsAlntAttrMap = new HashMap<String, List<ExtractorAttributes>>();
		if(options != null) {
			Iterator<String> keys = options.keySet().iterator();
			String extAttr = null;
			List<ExtractorAttributes> extAttrList = null;
	    	while(keys.hasNext()) {
	    		extAttr = keys.next();
	    		extAttrList = options.get(extAttr);
	    		for (ExtractorAttributes extractorAttr : extAttrList) {
	    			if(userAttr != null && userAttr && extractorAttr.isUserAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		} else if(roleAttr != null && roleAttr && extractorAttr.isRoleAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		}  else if(userCertAttr != null && userCertAttr && extractorAttr.isUserCertificationAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		} else if(badgeAttr != null && badgeAttr && extractorAttr.isBadgeAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		} else if(tokenAttr != null && tokenAttr && extractorAttr.isTokenAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		} else if(enterpriseRoleAttr != null && enterpriseRoleAttr && extractorAttr.isEntRoleAttr()) {
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		} else if(userAttr == null && roleAttr == null && badgeAttr == null && tokenAttr == null && enterpriseRoleAttr == null){
		    			processExtAttr(retExtAtrVsAlntAttrMap, extAttrList, extAttr, extractorAttr);
		    		}
				}
	    	}
		}
		return retExtAtrVsAlntAttrMap;
	}
	
	private static final void processExtAttr(Map<String,List<ExtractorAttributes>> retExtAtrVsAlntAttrMap, 
			List<ExtractorAttributes> extAttrList, String extAttrName,ExtractorAttributes extractorAttributes) {
		if(!retExtAtrVsAlntAttrMap.containsKey(extAttrName)) {
			extAttrList = new ArrayList<ExtractorAttributes>();
			retExtAtrVsAlntAttrMap.put(extAttrName,extAttrList);
		} else {
			extAttrList = retExtAtrVsAlntAttrMap.get(extAttrName);
		}
		extAttrList.add(extractorAttributes);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getUserExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, TRUE, FALSE,FALSE,FALSE,FALSE,FALSE);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getRoleExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, FALSE, TRUE,FALSE,FALSE,FALSE,FALSE);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getBadgeExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, FALSE, FALSE,TRUE,FALSE,FALSE,FALSE);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getTokenExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, FALSE, FALSE,FALSE,TRUE,FALSE,FALSE);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getUserCertExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, FALSE, FALSE,FALSE,FALSE,TRUE,FALSE);
	}
	
	public static final Map<String, List<ExtractorAttributes>> getEntRoleExtrAttrVsAlntAttrMap(Map<String, List<ExtractorAttributes>> options) {
		return getExtrAttrVsAlntAttrMap(options, FALSE, FALSE,FALSE,FALSE,FALSE,TRUE);
	}
} 
