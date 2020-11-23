/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	TMS.war
 ** Version			:	1.0
 **	File			:	Timesheet.java
 **	Description		:	The java Class Timesheet is model class that defines attributes of timesheet and includes   
 **                 :   getters and setters of the attributes.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Monday October 30, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  		Version 		Description:
 **	-------			--------   		  		--------		------------
 ** 30/10/2017      Jayesh Patil 		1.0           	Created
 *********************************************************************************************************************/

package ai.rnt.tms.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

public class Timesheet {
	
	ArrayList<String>  projectNames;
	ArrayList<String>  taskNames;
	ArrayList<String>  efforts;
	ArrayList<String>  firstNames;
	ArrayList<String>  lastNames;
	ArrayList<Integer>  staffIDs;
	String firstName;
	String middleName;
	String lastName;
	int count;
	int staffID;
	Date timesheetDate;
	String timesheetDateString;
	int taskID;
	String taskName;
	String description;
	int projectID;
	String projectName;
	String staffName;
	int effortHours;
	int effortMinutes;
	String status;
	String comment;
	
	String dataStatus;
	Time inTime;
	Time outTime;
	
	int createdBy;
	Timestamp createdDate;
	int updatedBy;
	Timestamp updatedDate;
	int deletedBy;
	Timestamp deletedDate;
	
	int monthId;
	String monthName;
	ArrayList<Timesheet> monthList;
	ArrayList<Timesheet> taskList;
	ArrayList<Timesheet> taskSubmittedList;
	

	
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public ArrayList<Timesheet> getTaskSubmittedList() {
		return taskSubmittedList;
	}
	public void setTaskSubmittedList(ArrayList<Timesheet> taskSubmittedList) {
		this.taskSubmittedList = taskSubmittedList;
	}
	
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getMonthId() {
		return monthId;
	}
	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public ArrayList<Timesheet> getMonthList() {
		return monthList;
	}
	public void setMonthList(ArrayList<Timesheet> monthList) {
		this.monthList = monthList;
	}
	
	public Time getInTime() {
		return inTime;
	}
	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}
	public Time getOutTime() {
		return outTime;
	}
	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}
	public String getTimesheetDateString() {
		return timesheetDateString;
	}
	public void setTimesheetDateString(String timesheetDateString) {
		this.timesheetDateString = timesheetDateString;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public ArrayList<String> getProjectNames() {
		return projectNames;
	}
	public void setProjectNames(ArrayList<String> projectNames) {
		this.projectNames = projectNames;
	}
	public ArrayList<String> getTaskNames() {
		return taskNames;
	}
	public void setTaskNames(ArrayList<String> taskNames) {
		this.taskNames = taskNames;
	}
	public ArrayList<String> getEfforts() {
		return efforts;
	}
	public void setEfforts(ArrayList<String> efforts) {
		this.efforts = efforts;
	}
	
	
	
	public ArrayList<Integer> getStaffIDs() {
		return staffIDs;
	}
	public void setStaffIDs(ArrayList<Integer> staffIDs) {
		this.staffIDs = staffIDs;
	}
	public ArrayList<String> getFirstNames() {
		return firstNames;
	}
	public void setFirstNames(ArrayList<String> firstNames) {
		this.firstNames = firstNames;
	}
	public ArrayList<String> getLastNames() {
		return lastNames;
	}
	public void setLastNames(ArrayList<String> lastNames) {
		this.lastNames = lastNames;
	}
	public ArrayList<Timesheet> getTaskList() {
		return taskList;
	}
	public void setTaskList(ArrayList<Timesheet> taskList) {
		this.taskList = taskList;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public Date getTimesheetDate() {
		return timesheetDate;
	}
	public void setTimesheetDate(Date timesheetDate) {
		this.timesheetDate = timesheetDate;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getEffortHours() {
		return effortHours;
	}
	public void setEffortHours(int effortHours) {
		this.effortHours = effortHours;
	}
	public int getEffortMinutes() {
		return effortMinutes;
	}
	public void setEffortMinutes(int effortMinutes) {
		this.effortMinutes = effortMinutes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}