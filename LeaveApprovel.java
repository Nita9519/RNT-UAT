/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	LeaveApprovel.java
 **	Description		:	The java Class LeaveApprovel consists all the attributes for the model.
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

public class LeaveApprovel {
	int staffID;
	int total_leave;
	String FName;
	String MName;
	String LName;
	Date FromDate;
	Date ToDate;
	String leaveType;
	String reason;
	String Status;
	int noOfDays;
	int updatedBy;
	Timestamp updatedDate;
	
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
	
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	
	public String getFName() {
		return FName;
	}
	public void setFName(String fName) {
		FName = fName;
	}
	public String getMName() {
		return MName;
	}
	public void setMName(String mName) {
		MName = mName;
	}
	public String getLName() {
		return LName;
	}
	public void setLName(String lName) {
		LName = lName;
	}
	public Date getFromDate() {
		return FromDate;
	}
	public void setFromDate(Date fromDate) {
		FromDate = fromDate;
	}
	public Date getToDate() {
		return ToDate;
	}
	public void setToDate(Date toDate) {
		ToDate = toDate;
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
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public int getTotal_leave() {
		return total_leave;
	}
	public void setTotal_leave(int total_leave) {
		this.total_leave = total_leave;
	}
	
	
	
	
	

}
