package ai.rnt.ams.model;

import java.util.Date;

public class MaintenanceStatusMaster
{
	String message = "";
	int createdBy;
    Date createdDate;
	int updatedBy;
	Date updatedDate;
	int deletedBy;
	Date deletedDate;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	int maintenanceStatusId;
    String maintenanceStatus;
    
	public String getMaintenanceStatus() {
		return maintenanceStatus;
	}
	public void setMaintenanceStatus(String maintenanceStatus) {
		this.maintenanceStatus = maintenanceStatus;
	}
	    public int getMaintenanceStatusId() {
		return maintenanceStatusId;
	}
	public void setMaintenanceStatusId(int maintenanceStatusId) {
		this.maintenanceStatusId = maintenanceStatusId;
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
