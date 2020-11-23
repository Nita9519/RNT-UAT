package ai.rnt.ams.model;

import java.util.ArrayList;
import java.util.Date;

public class AssetSubCategoryMaster

{
	   int subCatId;
	   String subCatName;
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
	int assetCatId;
	  String assetCatName;
	 
	   int createdBy;
	   Date createdDate;
	   int updatedBy;
	   Date updatedDate;
	   int deletedBy;
	   Date deletedDate;
	  
	  ArrayList<AssetSubCategoryMaster> assetSubCategoryMasterlist;
	  
	  
  
public ArrayList<AssetSubCategoryMaster> getAssetSubCategoryMasterlist() {
		return assetSubCategoryMasterlist;
	}
	public void setAssetSubCategoryMasterlist(ArrayList<AssetSubCategoryMaster> assetSubCategoryMasterlist) {
		this.assetSubCategoryMasterlist = assetSubCategoryMasterlist;
	}
public int getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}
	public String getSubCatName() {
		return subCatName;
	}
	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
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
