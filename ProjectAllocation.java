/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	TMS.war
 ** Version			:	1.0
 **	File			:	ProjectAllocation.java
 **	Description		:	The java Class ProjectAllocation is model class that defines attributes of project and includes   
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

public class ProjectAllocation {
	
	int staffID;
	int projectID;
	int managerID;
	Date allocationDate;
	Date releaseDate;
	int createdBy;
	Date createdDate;
	int updatedBy;
	Date updatedDate;
	int deletedBy;
	Date deletedDate;
	
	public int getStaffID() {
		return staffID;
	}
	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public Date getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}
	public Date getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}
