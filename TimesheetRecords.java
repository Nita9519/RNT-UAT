/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	TMS.war
 ** Version			:	1.0
 **	File			:	TimesheetRecords.java
 **	Description		:	The java Class TimesheetRecords is model class that defines lists of projects and tasks and includes   
 **                 :   getters and setters of the attributes.
 **	Author			:	Chinmay Wyawahare
 **	Created Date	:	Monday October 30, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  		Version 		Description:
 **	-------			--------   		  		--------		------------
 ** 30/10/2017      Chinmay Wyawahare 		1.0           	Created
 *********************************************************************************************************************/

package ai.rnt.tms.model;

import java.sql.Date;
import java.util.ArrayList;

import ai.rnt.tms.util.Util;

public class TimesheetRecords {

	int staffID;
	ArrayList<Project> projects;
	ArrayList<Task> tasks;
	String message;
	
	Timesheet timesheet;
	
	Date dateForEdit;
	String dateforedit;
	ArrayList<Timesheet> timesheetList;
	ArrayList<Timesheet> timesheetsefforts;
	ArrayList<String> tsSubmitDateList;
	
	ArrayList<Integer> effortsMinutesList;	
	ArrayList<Integer> effortsHoursList;	
	ArrayList<String> dateList;
	
	int totalHour;
	int totalMin;
	
	int effortsMinutes;
	int effortsHours;
	

	public String getDateforedit() {
		return dateforedit;
	}

	public void setDateforedit(String dateforedit) {
		this.dateforedit = dateforedit;
	}

	public int getEffortsMinutes() {
		return effortsMinutes;
	}

	public void setEffortsMinutes(int effortsMinutes) {
		this.effortsMinutes = effortsMinutes;
	}

	public int getEffortsHours() {
		return effortsHours;
	}

	public void setEffortsHours(int effortsHours) {
		this.effortsHours = effortsHours;
	}

	public ArrayList<Timesheet> getTimesheetsefforts() {
		return timesheetsefforts;
	}

	public void setTimesheetsefforts(ArrayList<Timesheet> timesheetsefforts) {
		this.timesheetsefforts = timesheetsefforts;
	}

	public void setDateList(ArrayList<String> dateList) {
		this.dateList = dateList;
	}

	public Date getDateForEdit() {
		return dateForEdit;
	}

	public void setDateForEdit(Date dateForEdit) {
		this.dateForEdit = dateForEdit;
	}

	public int getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(int totalHour) {
		this.totalHour = totalHour;
	}

	public int getTotalMin() {
		return totalMin;
	}

	public void setTotalMin(int totalMin) {
		this.totalMin = totalMin;
	}

	public ArrayList<String> getTsSubmitDateList() {
		return tsSubmitDateList;
	}

	public void setTsSubmitDateList(ArrayList<String> tsSubmitDateList) {
		this.tsSubmitDateList = tsSubmitDateList;
	}

	public ArrayList<String> getDateList() {
		return dateList;
	}

	public void setDateList() {
		this.dateList = Util.getLastSevenDates();
	}

	public int getStaffID() {
		return staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public ArrayList<Timesheet> getTimesheetList() {
		return timesheetList;
	}

	public void setTimesheetList(ArrayList<Timesheet> timesheetList) {
		this.timesheetList = timesheetList;
	}

	public void setEffortsHoursList(ArrayList<Integer> effortsHoursList) {
		this.effortsHoursList = effortsHoursList;
	}

	public ArrayList<Integer> getEffortsHoursList() {
		return effortsHoursList;
	}
	
	public void setEffortsHoursList() {
		this.effortsHoursList = Util.getEffortsInHours();
	}
	
	
	public void setEffortsMinutesList(ArrayList<Integer> effortsMinutesList) {
		this.effortsMinutesList = effortsMinutesList;
	}
	
	public ArrayList<Integer> getEffortsMinutesList() {
		return effortsMinutesList;
	}
	
	public void setEffortsMinutesList() {
		this.effortsMinutesList = Util.getEffortsInMinuts();
	}
	
	public Timesheet getTimesheet() {
		return timesheet;
	}
	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}
	public ArrayList<Project> getProjects() {
		return projects;
	}
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
