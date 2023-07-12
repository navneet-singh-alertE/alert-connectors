package com.alnt.restconnector.provisioning.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.json.JSONObject;

import com.alnt.restconnector.provisioning.commons.IResponse;
import com.alnt.restconnector.provisioning.services.RestConnectionInterface;

/**
 * Executes Rest URL and displays the result/response. Useful
 * in cases one wants to know some information from dependent
 * entities on which user and roles are dependent. eg Ids 
 * 
 * @author amit.ghildiyal
 *
 */
public class ExecuteRestURL {
	private Map connectorParams=new HashMap();
	private RestConnectionInterface connectionInterface=null;
	
	public ExecuteRestURL(String connectorParamsFileName){
		try {
			Properties properties=new Properties();
			File propertyFile=new File(connectorParamsFileName);
			if(propertyFile.exists()){
				Reader reader = new FileReader(propertyFile);
				properties.load(reader);
				Enumeration keys=properties.keys();
				while(keys.hasMoreElements()){
					String key=(String)keys.nextElement();
					String value=(String)properties.getProperty(key);
					connectorParams.put((String)key, value);
				}
				connectionInterface=new RestConnectionInterface(connectorParams);
				connectionInterface.testConnection();
			}
		}catch (Exception exception) {
				exception.printStackTrace();
			
		}
	}
	
	public void execute(String url, String outputFileName){
		Map parameters=null;
		try {
			
			String response=execute(url, parameters);
			if(response==null){
				System.out.println("Response is not valid. Exiting...");
				System.exit(0);
			}
			
			PrintWriter pw=new PrintWriter(new File(outputFileName));
			pw.write(response);
			pw.flush();
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String execute( String uri ,
			Map<String,Object> params) throws Exception {
		
		
		HttpMethod method =null;
		String response=null;
		try {
			System.out.println("Execute URL operation starts...");
			
			String url=connectionInterface.getConnector().getInstanceUrl()+"/"
							+uri;
			
			method = connectionInterface.getHttpMethodFromURL(params,url);
			System.out.println("Executing URL "+method.getURI());
			method.addRequestHeader("Authorization","Bearer "+ connectionInterface.getConnector().getAccessToken());
			
			connectionInterface.getConnector().executeRequest(method,true,true);
			
			if (method.getStatusCode() == HttpStatus.SC_OK) {
					System.out.println( "Operation performed successfully...");
					response=method.getResponseBodyAsString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Execute URL operation not successful");
		} finally {
			connectionInterface.getConnector().releaseConnection(method);
		}
		System.out.println("Execute URL operation ends...");
		return response;
	}
	
	
	public static void main(String[] args) {
				
		// c:\Program Files\Tomcat 6.0\alertlib>java -cp
		// ALNTRestConnector.jar;accessprovserviceapi.jar;json-20090211.jar;
		// slf4j-api-1.5.11.jar;slf4j-log4j12-1.5.11.jar;commons-codec-1.3.jar;commons-httpclient-3.1.jar;
		// log4j-1.2.15.jar;commons-logging.jar;ALNTExtractionConnectorAPI.jar
		// com.alnt.restconnector.provisioning.utils.ExecuteRestURL
		// d:\connectorparams.txt
		// "GET:services/data/v29.0/query?q=select%20Id%2Cname%2CCreatedDate%2CModifiedDate%20from%20User"
		// d:\\user.txt
		// };
		// args=new String[]{"d:\\connectorparams.txt","GET:services/data/v29.0/query?q=select%20Id%2Cname%20from%20Contact","d:\\Results.txt"
		
		if (args.length!=3){
			System.out.println("Usage: java -cp alertlib\\*.jar com.alnt.restconnector.provisioning.utils.ExecuteRestURL <Connector Params FileName> " +
					"<URL To Execute> <Output File name>");
			System.out.println("Example: java com.alnt.restconnector.provisioning.utils.ExecuteRestURL C:\\ConnectorParams.txt " +
					"GET:services/data/v29.0/query?q=select%20Id%2CUsername%20from%20User C:\\Results.txt");
			return ;
		}
		String connectorParamsFileName=args[0];
		String url=args[1];
		String outputFileName=args[2];
		ExecuteRestURL executeRestURL=new ExecuteRestURL(connectorParamsFileName);
		executeRestURL.execute(url, outputFileName);
	}
}
