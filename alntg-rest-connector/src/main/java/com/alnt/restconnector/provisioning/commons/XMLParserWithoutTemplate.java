package com.alnt.restconnector.provisioning.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParserWithoutTemplate {

	static XMLParserWithoutTemplate singleObj=null;
	
	private XMLParserWithoutTemplate(){
		
	}
	
	synchronized public static XMLParserWithoutTemplate getObject(){
		if(singleObj==null)
		{
			singleObj= new XMLParserWithoutTemplate();
		}
			return singleObj;
	}

   
    
	synchronized  public String MapToXMLString(Object map) {
    	String writer="";
    	if(map instanceof HashMap ){
	    	Map<String,Object> params=(Map<String,Object>)map;
	        for (Map.Entry<String, Object> entry : params.entrySet()) {
	        	String key=entry.getKey();
	        	Object value=entry.getValue();
	        	if(value instanceof HashMap){
	        		writer+="<"+key+">"+MapToXMLString(params.get(key))+"</"+key+">";
	        	}else if(value instanceof ArrayList){
	        		writer+="<"+key+">"+MapToXMLString(params.get(key))+"</"+key+">";
	        	}else if(value instanceof String){
	        		writer+="<"+key+">"+value+"</"+key+">";
	        	}
	        }
    	}
    	if(map instanceof ArrayList){
    		List list=(ArrayList) map;
    		for(int i=0;i<list.size();i++)
        	{
    			Object value=list.get(i);
    			writer+=MapToXMLString(value);
    	    	
        	}
    	}
        return writer;
    }

    
//    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
//        Map<String, String> map = new HashMap<String, String>();
//        for (int i = 0; i < reader.getAttributeCount(); i++) {
//            String key = reader.getAttributeName(i);
//            String value = reader.getAttribute(key);
//            map.put(key, value);
//        }
//        return map;
//    }
    
    
    
}