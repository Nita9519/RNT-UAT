/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	TMS.war
 ** Version			:	1.0
 **	File			:	Dashboard.java
 **	Description		:	The java Class Dashboard is model class that defines attributes of dashboard and includes getters  
 **                 :   and setters of the attributes.
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

import java.util.ArrayList;

import ai.rnt.tms.util.Util;

public class Dashboard {

	ArrayList<Timesheet> timesheetList;
	ArrayList<Timesheet> timesheetListDisp;

	ArrayList<Timesheet> monthLists;
	ArrayList<Attendance> empAttendanceList;
	ArrayList<Attendance> empAttendanceListForEmp;
	ArrayList<Integer> effortsMinutesList;
	ArrayList<Integer> effortsHoursList;
	ArrayList<String> dateList;

	ArrayList<Project> projects;
	ArrayList<Task> tasks;
	String message = "";
	String inTime;
	String outTime;
	int staffID;
	ArrayList<Timesheet> timesheetLastWeekSubmited;
	ArrayList<Timesheet> timesheetSubmitedForUser;
	ArrayList<Timesheet> timesheetLastWeekManager;
	ArrayList<Timesheet> timesheetCurrentWeekManager;
	ArrayList<String> tsSubmitDateList;
	int totalHour;
	int totalMin;
	
	int effortsMinutes;
	int effortsHours;
	
	

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

	public int getStaffID() {
		return staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public ArrayList<String> getTsSubmitDateList() {
		return tsSubmitDateList;
	}

	public void setTsSubmitDateList(ArrayList<String> tsSubmitDateList) {
		this.tsSubmitDateList = tsSubmitDateList;
	}

	public ArrayList<Timesheet> getTimesheetCurrentWeekManager() {
		return timesheetCurrentWeekManager;
	}

	public void setTimesheetCurrentWeekManager(ArrayList<Timesheet> timesheetCurrentWeekManager) {
		this.timesheetCurrentWeekManager = timesheetCurrentWeekManager;
	}

	public ArrayList<Timesheet> getTimesheetLastWeekManager() {
		return timesheetLastWeekManager;
	}

	public void setTimesheetLastWeekManager(ArrayList<Timesheet> timesheetLastWeekManager) {
		this.timesheetLastWeekManager = timesheetLastWeekManager;
	}

	public ArrayList<Timesheet> getTimesheetSubmitedForUser() {
		return timesheetSubmitedForUser;
	}

	public void setTimesheetSubmitedForUser(ArrayList<Timesheet> timesheetSubmitedForUser) {
		this.timesheetSubmitedForUser = timesheetSubmitedForUser;
	}

	public ArrayList<Timesheet> getTimesheetLastWeekSubmited() {
		return timesheetLastWeekSubmited;
	}

	public void setTimesheetLastWeekSubmited(ArrayList<Timesheet> timesheetLastWeekSubmited) {
		this.timesheetLastWeekSubmited = timesheetLastWeekSubmited;
	}

	public ArrayList<Attendance> getEmpAttendanceListForEmp() {
		return empAttendanceListForEmp;
	}

	public void setEmpAttendanceListForEmp(ArrayList<Attendance> empAttendanceListForEmp) {
		this.empAttendanceListForEmp = empAttendanceListForEmp;
	}

	public ArrayList<Timesheet> getMonthLists() {
		return monthLists;
	}

	public void setMonthLists(ArrayList<Timesheet> monthLists) {
		this.monthLists = monthLists;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<Timesheet> getTimesheetListDisp() {
		return timesheetListDisp;
	}

	public void setTimesheetListDisp(ArrayList<Timesheet> timesheetListDisp) {
		this.timesheetListDisp = timesheetListDisp;
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

	public void setDateList(ArrayList<String> dateList) {
		this.dateList = dateList;
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

	public ArrayList<String> getDateList() {
		return dateList;
	}

	public void setDateList() {
		this.dateList = Util.getLastSevenDates();
	}

	public ArrayList<Timesheet> getTimesheetList() {
		return timesheetList;
	}

	public void setTimesheetList(ArrayList<Timesheet> timesheetList) {
		this.timesheetList = timesheetList;
	}

	public ArrayList<Attendance> getEmpAttendanceList() {
		return empAttendanceList;
	}

	public void setEmpAttendanceList(ArrayList<Attendance> empAttendanceList) {
		this.empAttendanceList = empAttendanceList;
	}

}