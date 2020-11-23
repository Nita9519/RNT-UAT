/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	UserLeaveRecord.java
 **	Description		:	The java Class UserLeaveRecord consists all the attributes for the model.
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

public class UserLeaveRecord {
	
	String FName;
	String MName;
	String LName;
	Date FromDate;
	Date ToDate;
	String leaveType;
	String reason;
	String Status;
	
	
	public String getFName() {
		return FName;
	}
	public void setFName(String FName) {
		this.FName = FName;
	}
	public String getMName() {
		return MName;
	}
	public void setMName(String MName) {
		this.MName = MName;
	}
	public String getLName() {
		return LName;
	}
	public void setLName(String LName) {
		this.LName = LName;
	}
	public Date getFromDate() {
		return FromDate;
	}
	public void setFromDate(Date FromDate) {
		this.FromDate = FromDate;
	}
	public Date getToDate() {
		return ToDate;
	}
	public void setToDate(Date ToDate) {
		this.ToDate = ToDate;
	}
	
	public String getleaveType() {
		return leaveType;
	}
	public void setleaveType(String leaveType) {
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
	
	
	
	
	
}
