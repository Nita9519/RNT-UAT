/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	LoginDoa.java
 **	Description		:	The java interface LoginDoa is an interface which consists declarations of all the 
 *						abstract methods.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/


package ai.rnt.lms.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import ai.rnt.lms.model.LeaveApprovel;
import ai.rnt.lms.model.Dashboard;
import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.lms.model.LeaveAllotment;
import ai.rnt.lms.model.LeaveRecord;
import ai.rnt.lms.model.User;

@SuppressWarnings("unused")
public interface LoginDoa {

	public User getEmployeeDetails(int staffID, String passward) throws SQLException, PropertyVetoException;

	public boolean isValidAdmin(int staffID) throws SQLException, PropertyVetoException;

	public boolean isValidManager(int staffID) throws SQLException, PropertyVetoException;

	public List<LeaveRecord> getLeaveRecords(int staffID) throws SQLException, PropertyVetoException;

	public List<LeaveRecord> getLeaveRecordsforApproval(int staffID) throws SQLException, PropertyVetoException;

	public List<HolidayCalendar> getHolidayCalender() throws SQLException, PropertyVetoException;

	public Dashboard getLeaveDetails(int staffID, Dashboard dashboard) throws SQLException, PropertyVetoException;

	public void getUsedLeaves(int staffID, Dashboard dashboard) throws SQLException, PropertyVetoException;

	public ArrayList<LeaveAllotment> leaveAllotment() throws SQLException, PropertyVetoException;

	public ArrayList<HolidayCalendar> getHolidatDetails() throws SQLException, PropertyVetoException;

	public void updateLeaveApproval(LeaveRecord leaveRecord) throws SQLException, PropertyVetoException;

	public ArrayList<LeaveRecord> leaveRecordforPopup(int sequenceNo) throws SQLException, PropertyVetoException;

	public LeaveRecord applyLeave(int staffID, String fromDt, String toDt, String leaveType, int noOfDays,
			String comment, String emailID) throws SQLException, PropertyVetoException, ParseException, MessagingException;

	public LeaveAllotment addLeaveDetails(int staffID, int flexiLeave, int priviledgeLeave)
			throws SQLException, PropertyVetoException;

	public boolean isValidDate(int staffID, String fromDt, String toDt) throws SQLException, PropertyVetoException;

	public void insertHoliday(HolidayCalendar holidayCalendar, int staffID) throws SQLException, PropertyVetoException;

	public ArrayList<HolidayCalendar> getHolidayDetails() throws SQLException, PropertyVetoException;

	public void updateHoliday(HolidayCalendar holidayCalendar, int staffID) throws SQLException, PropertyVetoException;

	public void deleteHoliday(HolidayCalendar holidayCalendar, int adminID) throws SQLException, PropertyVetoException;
	
	public ArrayList<User> getUsersForLeaveRecords() throws SQLException, PropertyVetoException;
	
	public Boolean checkForDuplicateDate(Date sqlDate) throws SQLException, PropertyVetoException ;
	
	public User getEmployeeDetails(int staffID) throws SQLException, PropertyVetoException;
	
	public LeaveAllotment insertLeaveDetails(LeaveAllotment leaveAllotment, int adminID) throws SQLException, PropertyVetoException;

	public void updateLeaveDetails(LeaveAllotment leaveAllotment, int adminID) throws SQLException, PropertyVetoException;

	public void deleteLeaveDetails(LeaveAllotment leaveAllotment, int adminID) throws SQLException, PropertyVetoException;
	
	



}
