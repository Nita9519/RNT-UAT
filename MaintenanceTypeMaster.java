package ai.rnt.ams.model;

import java.util.Date;

public class MaintenanceTypeMaster
{
	
	
	   int maintenanceTypeId;
	   String maintenanceType;
	   int createdBy;
	   Date createdDate;
	   int updatedBy;
	   Date updatedDate;
	   int deletedBy;
	   Date deletedDate;
	  
	   public int getMaintenanceTypeId() {
		return maintenanceTypeId;
	}
	public void setMaintenanceTypeId(int maintenanceTypeId) {
		this.maintenanceTypeId = maintenanceTypeId;
	}
	public String getMaintenanceType() {
		return maintenanceType;
	}
	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
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
