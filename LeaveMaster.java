/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	LeaveMaster.java
 **	Description		:	The java Class LeaveMaster consists all the attributes for the model.
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

public class LeaveMaster {
	String message = "";
	
	
	ArrayList<LeaveAllotment> leaveList = null;
	ArrayList<User> userList = null;
	
	public ArrayList<LeaveAllotment> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(ArrayList<LeaveAllotment> leaveList) {
		this.leaveList = leaveList;
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
