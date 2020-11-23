package ai.rnt.ams.model;

import java.util.Date;

public class AssetCategoryMaster 
{
   int assetCatId;
   String assetCatName;
   int createdBy;
   Date createdDate;
   int updatedBy;
   Date updatedDate;
   int deletedBy;
   Date deletedDate;
   String message = "";
   
   public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public int getAssetCatId() {
	return assetCatId;
}
public void setAssetCatId(int assetCatId) {
	this.assetCatId = assetCatId;
}
public String getAssetCatName() {
	return assetCatName;
}
public void setAssetCatName(String assetCatName) {
	this.assetCatName = assetCatName;
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
