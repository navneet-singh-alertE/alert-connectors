package com.alnt.access.provisioning.utils;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Kalyan, Amit.Ghildiyal
 * 
 *         This is the Logger component for Logging and debugging. This class is
 *         using Apache Log4J for Logging. It wraps the Log4J methods.
 */

public class ALNTConnectorLogger {
	
	/**
	 * -----------------------------------------------------------------------
	 * Constants (category names)
	 * -----------------------------------------------------------------------
	 * This is the constant that stores category name for user create
	 */
	public static final String USER_CREATE = "com.application.user.create";

	/**
	 * -----------------------------------------------------------------------
	 * Constants (flags used for compile time optimization)
	 * -----------------------------------------------------------------------
	 * This is the constant used for compile time optimization for logging with
	 * debug severity. In typical usage <code>if( Logger.DEBUG_ON){
	 * Logger.debug(this, "Message"); }</code>, if DEBUG_ON is set to false, the
	 * compiler removes this block of code from compiled code else if it is true
	 * this block of code will be part of the compiled code.
	 */
	public static final boolean DEBUG_ON = true;

	/**
	 * This is the constant used for compile time optimization for logging with
	 * info severity. In typical usage <code>if( Logger.INFO_ON){
	 * Logger.info(this, "Message"); }</code>, if INFO_ON is set to false, the
	 * compiler removes this block of code from compiled code else if it is true
	 * this block of code will be part of the compiled code.
	 */
	public static final boolean INFO_ON = true;

	/**
	 * This is the constant used for compile time optimization for logging with
	 * warn severity. In typical usage <code>if( Logger.WARN_ON){
	 * Logger.warn(this, "Message"); }</code>, if WARN_ON is set to false, the
	 * compiler removes this block of code from compiled code else if it is true
	 * this block of code will be part of the compiled code.
	 */
	public static final boolean WARN_ON = true;

	/**
	 * This is the constant used for compile time optimization for logging with
	 * error severity. In typical usage <code>if( Logger.ERROR_ON){
	 * Logger.error(this, "Message"); }</code>, if ERROR_ON is set to false, the
	 * compiler removes this block of code from compiled code else if it is true
	 * this block of code will be part of the compiled code.
	 */
	public static final boolean ERROR_ON = true;

	/**
	 * This is the constant used for compile time optimization for logging with
	 * fatal severity. In typical usage <code>if( Logger.FATAL_ON){
	 * Logger.fatal(this, "Message"); }</code>, if FATAL_ON is set to false, the
	 * compiler removes this block of code from compiled code else if it is true
	 * this block of code will be part of the compiled code.
	 */
	public static final boolean FATAL_ON = true;

	/**
	 * -----------------------------------------------------------------------
	 * Constants (standard prefixes)
	 * -----------------------------------------------------------------------
	 */
	public static final String EXCEPTION_PREFIX = "Exception: ";// prefix for exception name
	public static final String ERROR_NO_PREFIX = "Error #: ";	// prefix for logging error numbers.
	public static final String TEXT_PREFIX = "Text: ";			// prefix for logging messages.
	public static final String DETAILS_PREFIX = "Details: ";	// prefix for logging additional details.
	//public static final String CLASS_NAME = ALNTConnectorLogger.class.getName();// name of the class
	public static final String KEY_LOGGER_PROPERTY = "app.logconfig";// the key name of the logger property.
	public static final String EMPTY_STRING = "";
	private Properties properties = null;
	private static ALNTConnectorLogger alntConnectorLogger=null;

	public static final String CONNECTOR_CATEGORY_NAME="connectors";
	private static Logger logger=LogManager.getLogger(CONNECTOR_CATEGORY_NAME);
	


	/**
	 * ----------------------------------------------------------------------------------------
	 *  	ALL - 	All levels including custom levels.
	 *  	DEBUG - Designates fine-grained informational events that are most
	 * 				useful to debug an application. ERROR Designates error events that might
	 * 				still allow the application to continue running. 
	 * 		FATAL - Designates very severe error events that will presumably lead
	 * 				the application to abort.
	 * 		INFO - Designates informational messages that highlight the progress of the
	 * 				application at coarse-grained level. OFF The highest possible rank and is
	 * 				intended to turn off logging.
	 * 		TRACE - Designates finer-grained informational events than the DEBUG.
	 * 		WARN - Designates potentially harmful situations. 
	 * 		
	 * 		A log request of level p in a logger with level q, is enabled
	 * 		if p >= q. This rule is of log4j. It assumes that levels are ordered. For
	 * 		the standard levels, we have ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF.
	 * ------------------------------------------------------------------------------------------
	 */
	//private static Logger logger=null;;
	
	
	private ALNTConnectorLogger() {
	}

	public static ALNTConnectorLogger getInstance(){
		if(alntConnectorLogger==null){
			alntConnectorLogger=new ALNTConnectorLogger();
			//alntConnectorLogger.init();
		}
		return alntConnectorLogger;
	}
	

}
