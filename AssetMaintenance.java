package ai.rnt.ams.model;

import java.sql.Date;
import java.util.ArrayList;

public class AssetMaintenance
{
   int requestId;
   String requestType;
   String assetTag;
   int assetId;
   int maintenanceStatusId;
   String maintenanceStatus;
   public String getMaintenanceStatus() {
	return maintenanceStatus;
}
public void setMaintenanceStatus(String maintenanceStatus) {
	this.maintenanceStatus = maintenanceStatus;
}

Date maintenanceDate;
   public ArrayList<AssetMaintenance> getChangedApproveStatusList() {
	return changedApproveStatusList;
}
public void setChangedApproveStatusList(ArrayList<AssetMaintenance> changedApproveStatusList) {
	this.changedApproveStatusList = changedApproveStatusList;
}

ArrayList<AssetMaintenance> changedApproveStatusList;
  
public Date getMaintenanceDate() {
	return maintenanceDate;
}
public void setMaintenanceDate(Date maintenanceDate) {
	this.maintenanceDate = maintenanceDate;
}

ArrayList<MaintenanceStatusMaster> maintenanceStatusList;
   public ArrayList<MaintenanceStatusMaster> getMaintenanceStatusList() {
	return maintenanceStatusList;
}
public void setMaintenanceStatusList(ArrayList<MaintenanceStatusMaster> maintenanceStatusList) {
	this.maintenanceStatusList = maintenanceStatusList;
}
public int getMaintenanceStatusId() {
	return maintenanceStatusId;
}
public void setMaintenanceStatusId(int maintenanceStatusId) {
	this.maintenanceStatusId = maintenanceStatusId;
}

ArrayList<AssetMaintenance> approvedMaintenanceList;
   ArrayList<MaintenanceTypeMaster> maintenanceTypeList;
   public ArrayList<MaintenanceTypeMaster> getMaintenanceTypeList() {
	return maintenanceTypeList;
}
public void setMaintenanceTypeList(ArrayList<MaintenanceTypeMaster> maintenanceTypeList) {
	this.maintenanceTypeList = maintenanceTypeList;
}
public ArrayList<AssetMaintenance> getApprovedMaintenanceList() {
	return approvedMaintenanceList;
}
public void setApprovedMaintenanceList(ArrayList<AssetMaintenance> approvedMaintenanceList) {
	this.approvedMaintenanceList = approvedMaintenanceList;
}
public int getAssetId() {
	return assetId;
}
public void setAssetId(int assetId) {
	this.assetId = assetId;
}

int staffId;
   String firstName;
   String configDescription;
     
public String getConfigDescription() {
	return configDescription;
}
public void setConfigDescription(String configDescription) {
	this.configDescription = configDescription;
}
public String getReqStatus() {
	return reqStatus;
}
public void setReqStatus(String reqStatus) {
	this.reqStatus = reqStatus;
}

String reqStatus;


   String warranty;
   String coverage;
   int claim;
   String problemDesc;
   String comment;
   int maintenanceTypeId;
   String requiredTime;
   public String getRequiredTime() {
	return requiredTime;
}
public void setRequiredTime(String requiredTime) {
	this.requiredTime = requiredTime;
}
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

String maintenanceType;
   public int getRequestId() {
	return requestId;
}
public void setRequestId(int requestId) {
	this.requestId = requestId;
}
public String getRequestType() {
	return requestType;
}
public void setRequestType(String requestType) {
	this.requestType = requestType;
}
public String getAssetTag() {
	return assetTag;
}
public void setAssetTag(String assetTag) {
	this.assetTag = assetTag;
}
public int getStaffId() {
	return staffId;
}
public void setStaffId(int staffId) {
	this.staffId = staffId;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getWarranty() {
	return warranty;
}
public void setWarranty(String warranty) {
	this.warranty = warranty;
}
public String getCoverage() {
	return coverage;
}
public void setCoverage(String coverage) {
	this.coverage = coverage;
}
public int getClaim() {
	return claim;
}
public void setClaim(int claim) {
	this.claim = claim;
}
public String getProblemDesc() {
	return problemDesc;
}
public void setProblemDesc(String problemDesc) {
	this.problemDesc = problemDesc;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}

RaiseRequest maintenanceReDetails;
public RaiseRequest getMaintenanceReDetails() {
	return maintenanceReDetails;
}
public void setMaintenanceReDetails(RaiseRequest maintenanceReDetails) {
	this.maintenanceReDetails = maintenanceReDetails;
}

}
