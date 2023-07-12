package com.alnt.fabric.appointment.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAppointmentInterface {
	
	public final static String SYS_CON_ATTR_ENDPOINT_URL="endPointUrl";
	public final static String SYS_CON_ATTR_USER="userId";
	public final static String SYS_CON_ATTR_PSWD="userPass";
	public final static String SYS_CON_ATTR_TZ="timeZone";
	
	public Map<String,Object> createAppointment(String userId, Map<String,Object> appointmentDetails) throws Exception;
	
	public Map<String,Object> cancelAppointment(String processId, String reasonText) throws Exception;
	
	public Map<String,Object> rescheduleAppointment(String ProcessId, Map<String,Object> appointmentDetails) throws Exception;
	
	public List<Map<String, Object>> getAllSlotsForDate(String serviceId, Date appointmentDate) throws Exception;
	
	public List<String> getAppointmentTypes() throws Exception;

}
