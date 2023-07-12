package com.alnt.restconnector.provisioning.commons;

public class JSONProvisioningParserWithoutTemplate {

static JSONProvisioningParserWithoutTemplate SingleObj=null;

	private JSONProvisioningParserWithoutTemplate(){
		
	}
	
	synchronized public static JSONProvisioningParserWithoutTemplate getObject(){
		if(SingleObj==null)
		{
			SingleObj= new JSONProvisioningParserWithoutTemplate();
		}
			return SingleObj;
	}

//	synchronized public String mapToJSON(Map<String,String> map) throws Exception
//	{	ObjectMapper mapper = new ObjectMapper();
//	String jsonFromMap;
//	try {
//		jsonFromMap = mapper.writeValueAsString(map);
//	} catch (JsonGenerationException e) {
//		e.printStackTrace();
//		throw e;
//	} catch (JsonMappingException e) {
//		e.printStackTrace();
//	throw e;
//	} catch (IOException e) {
//		e.printStackTrace();
//		throw e;
//	}
//	return jsonFromMap;
//	}
	
//	synchronized public Map<String,Object> JSONStringToMap(String jsonString) throws Exception
//	{	ObjectMapper mapper = new ObjectMapper();
//	Map<String,Object> map=new HashMap<String,Object>();
//	try {
//		map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
//		} catch (JsonGenerationException e) {
//		e.printStackTrace();
//		throw e;
//	} catch (JsonMappingException e) {
//		e.printStackTrace();
//	throw e;
//	} catch (IOException e) {
//		e.printStackTrace();
//		throw e;
//	}
//	return map;
//	}
	
	
}
