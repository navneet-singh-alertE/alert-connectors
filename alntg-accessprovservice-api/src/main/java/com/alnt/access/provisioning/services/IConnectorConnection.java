package com.alnt.access.provisioning.services;

public interface IConnectorConnection {
	/**
	 * Method to initialize external connection 
	 * @return
	 * @throws Exception
	 */
	public boolean initializeConnection() throws Exception;
	
	/**
	 * Method to close external connection established as part of initializeConnection()
	 * @return
	 * @throws Exception
	 */
	public boolean closeConnection() throws Exception;
	
	

}
