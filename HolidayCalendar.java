/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	HolidayCalendar.java
 **	Description		:	The java Class HolidayCalendar consists all the attributes for the model.
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

import java.sql.Date;
import java.sql.Timestamp;

public class HolidayCalendar {

	Date date;
	String occasion;
	String optionalFlag="N";
	String weekDay;
	int holidayID;
	int createdBy;
	Timestamp createdDate;
	int updatedBy;
	Timestamp updatedDate;
	int deletedBy;
	Timestamp deletedDate;
	String dateInString;
	String holidayIDInString;
	
	
	public String getHolidayIDInString() {
		return holidayIDInString;
	}
	public void setHolidayIDInString(String holidayIDInString) {
		this.holidayIDInString = holidayIDInString;
	}
	public String getDateInString() {
		return dateInString;
	}
	public void setDateInString(String dateInString) {
		this.dateInString = dateInString;
	}
	public int getHolidayID() {
		return holidayID;
	}
	public void setHolidayID(int holidayID) {
		this.holidayID = holidayID;
	}
	public int getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}
	public Timestamp getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Timestamp deletedDate) {
		this.deletedDate = deletedDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOccasion() {
		return occasion;
	}
	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}
	public String getOptionalFlag() {
		return optionalFlag;
	}
	public void setOptionalFlag(String optionalFlag) {
		this.optionalFlag = optionalFlag;
	}
	

}
