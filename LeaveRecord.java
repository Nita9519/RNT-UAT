/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	LeaveRecord.java
 **	Description		:	The java Class LeaveRecord consists all the attributes for the model.
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
import java.util.ArrayList;

import ai.rnt.tms.model.Timesheet;

public class LeaveRecord {
	String message="";
	
	int staffID;
	String userID;
	String firstName;
	String middleName;
	String lastName;
	Date fromDate;
	Date toDate;
	int noOfDays;
	String leaveType;
	String reason;
	String status;
	int P_leave;
	int F_leave;
	int monthLeave;
	int sequenceNo;
	String managerComment;
	int createdBy;
	Timestamp createdDate;
	int updatedBy;
	Timestamp updatedDate;
	int managerID;
	String fromdateInString;
	String todateInString;
	int TotalPLeave;
	int TotalFLeave;
	int LeaveUsed;
	int TotalmonthLeave;
	int pLeaveBalance;
	int fLeaveBalance;
	int ohLeaveUsed;
	int monthLeaveBalance;
	int monthId;
	String monthName;
	ArrayList<LeaveRecord> monthLists;
	ArrayList<LeaveRecord> leaverecordsListForApproval;
	ArrayList<LeaveRecord> displayleaveshistoryofemployee;
	
	
	
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
	public ArrayList<LeaveRecord> getMonthLists() {
		return monthLists;
	}
	public void setMonthLists(ArrayList<LeaveRecord> monthLists) {
		this.monthLists = monthLists;
	}
	public int getTotalmonthLeave() {
		return TotalmonthLeave;
	}
	public void setTotalmonthLeave(int totalmonthLeave) {
		TotalmonthLeave = totalmonthLeave;
	}
	public String getFromdateInString() {
		return fromdateInString;
	}
	public void setFromdateInString(String fromdateInString) {
		this.fromdateInString = fromdateInString;
	}
	public String getTodateInString() {
		return todateInString;
	}
	public void setTodateInString(String todateInString) {
		this.todateInString = todateInString;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public ArrayList<LeaveRecord> getDisplayleaveshistoryofemployee() {
		return displayleaveshistoryofemployee;
	}
	public void setDisplayleaveshistoryofemployee(ArrayList<LeaveRecord> displayleaveshistoryofemployee) {
		this.displayleaveshistoryofemployee = displayleaveshistoryofemployee;
	}
	public ArrayList<LeaveRecord> getLeaverecordsListForApproval() {
		return leaverecordsListForApproval;
	}
	public void setLeaverecordsListForApproval(ArrayList<LeaveRecord> leaverecordsListForApproval) {
		this.leaverecordsListForApproval = leaverecordsListForApproval;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

	public int getMonthLeave() {
		return monthLeave;
	}
	public void setMonthLeave(int monthLeave) {
		this.monthLeave = monthLeave;
	}
	public int getMonthLeaveBalance() {
		return monthLeaveBalance;
	}
	public void setMonthLeaveBalance(int monthLeaveBalance) {
		this.monthLeaveBalance = monthLeaveBalance;
	}
	public int getOhLeaveUsed() {
		return ohLeaveUsed;
	}
	public void setOhLeaveUsed(int ohLeaveUsed) {
		this.ohLeaveUsed = ohLeaveUsed;
	}
	public int getpLeaveBalance() {
		return pLeaveBalance;
	}
	public void setpLeaveBalance(int pLeaveBalance) {
		this.pLeaveBalance = pLeaveBalance;
	}
	public int getfLeaveBalance() {
		return fLeaveBalance;
	}
	public void setfLeaveBalance(int fLeaveBalance) {
		this.fLeaveBalance = fLeaveBalance;
	}
	public int getTotalFLeave() {
		return TotalFLeave;
	}
	public void setTotalFLeave(int totalFLeave) {
		TotalFLeave = totalFLeave;
	}
	public int getTotalPLeave() {
		return TotalPLeave;
	}
	public void setTotalPLeave(int totalPLeave) {
		TotalPLeave = totalPLeave;
	}
	public int getLeaveUsed() {
		return LeaveUsed;
	}
	public void setLeaveUsed(int leaveUsed) {
		LeaveUsed = leaveUsed;
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
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
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
	public int getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getP_leave() {
		return P_leave;
	}
	public void setP_leave(int p_leave) {
		P_leave = p_leave;
	}
	public int getF_leave() {
		return F_leave;
	}
	public void setF_leave(int f_leave) {
		F_leave = f_leave;
	}
	
	
}
