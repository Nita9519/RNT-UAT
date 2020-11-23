package ai.rnt.ams.model;

import java.util.Date;
public class RequestTypeMaster  
{
	
	   public int getRequestTypeId() {
		return requestTypeId;
	}
	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	int requestTypeId;
	   String requestType;
	   int createdBy;
	   Date createdDate;
	   int updatedBy;
	   Date updatedDate;
	   int deletedBy;
	   Date deletedDate;
	  
	  
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
