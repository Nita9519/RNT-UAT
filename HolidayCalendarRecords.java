/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	HolidayCalendarRecords.java
 **	Description		:	The java Class HolidayCalendarRecords consists all the attributes for the model.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/


package ai.rnt.lms.model;

import java.util.ArrayList;

public class HolidayCalendarRecords {
	String message="";
	
	
	ArrayList<HolidayCalendar> holidayCalendar;
	String duplicateDateMessage="";
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getDuplicateDateMessage() {
		return duplicateDateMessage;
	}
	public void setDuplicateDateMessage(String duplicateDateMessage) {
		this.duplicateDateMessage = duplicateDateMessage;
	}
	public ArrayList<HolidayCalendar> getHolidayCalendar() {
		return holidayCalendar;
	}
	public void setHolidayCalendar(ArrayList<HolidayCalendar> holidayCalendar) {
		this.holidayCalendar = holidayCalendar;
	}
	
	
	
}
