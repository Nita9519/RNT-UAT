package ai.rnt.ams.model;

import java.sql.Date;
import java.util.ArrayList;

public class RaiseRequest {
	String message = "";
	int requestId;
	
	String reqStatus;
	String assetTag;
	String warranty;
	int claim;
	Date allocationDate;
	Date estReleasedDate;
	Date actualReleasedDate;
	String assetAvailableStatus;
	String configuration;
	String processor;
	String ram;
	String hardDisk;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public Date getEstReleasedDate() {
		return estReleasedDate;
	}

	public void setEstReleasedDate(Date estReleasedDate) {
		this.estReleasedDate = estReleasedDate;
	}

	public Date getActualReleasedDate() {
		return actualReleasedDate;
	}

	public void setActualReleasedDate(Date actualReleasedDate) {
		this.actualReleasedDate = actualReleasedDate;
	}

	public String getAssetAvailableStatus() {
		return assetAvailableStatus;
	}

	public void setAssetAvailableStatus(String assetAvailableStatus) {
		this.assetAvailableStatus = assetAvailableStatus;
	}

	ArrayList<AssetRegistration> availbleAsset;
	ArrayList<AssetMaintenance> approvedMaintenanceList;
	ArrayList<AssetAllocation> availAssetListWithRequest;
	ArrayList<MaintenanceTypeMaster> maintenanceTypeList;
	ArrayList<MaintenanceStatusMaster> maintenanceStatusList;

	public ArrayList<MaintenanceStatusMaster> getMaintenanceStatusList() {
		return maintenanceStatusList;
	}

	public void setMaintenanceStatusList(ArrayList<MaintenanceStatusMaster> maintenanceStatusList) {
		this.maintenanceStatusList = maintenanceStatusList;
	}

	public ArrayList<MaintenanceTypeMaster> getMaintenanceTypeList() {
		return maintenanceTypeList;
	}

	public void setMaintenanceTypeList(ArrayList<MaintenanceTypeMaster> maintenanceTypeList) {
		this.maintenanceTypeList = maintenanceTypeList;
	}

	public ArrayList<AssetAllocation> getAvailAssetListWithRequest() {
		return availAssetListWithRequest;
	}

	public void setAvailAssetListWithRequest(ArrayList<AssetAllocation> availAssetListWithRequest) {
		this.availAssetListWithRequest = availAssetListWithRequest;
	}

	ArrayList<AssetAllocation> availbleAssetforApproveStatus;

	public ArrayList<AssetAllocation> getAvailbleAssetforApproveStatus() {
		return availbleAssetforApproveStatus;
	}

	public void setAvailbleAssetforApproveStatus(ArrayList<AssetAllocation> availbleAssetforApproveStatus) {
		this.availbleAssetforApproveStatus = availbleAssetforApproveStatus;
	}

	public ArrayList<AssetRegistration> getAvailbleAsset() {
		return availbleAsset;
	}

	public void setAvailbleAsset(ArrayList<AssetRegistration> availbleAsset) {
		this.availbleAsset = availbleAsset;
	}

	public ArrayList<AssetMaintenance> getApprovedMaintenanceList() {
		return approvedMaintenanceList;
	}

	public void setApprovedMaintenanceList(ArrayList<AssetMaintenance> approvedMaintenanceList) {
		this.approvedMaintenanceList = approvedMaintenanceList;
	}

	ArrayList<AssetAllocation> allocatedAssets;

	public ArrayList<AssetAllocation> getAllocatedAssets() {
		return allocatedAssets;
	}

	public void setAllocatedAssets(ArrayList<AssetAllocation> allocatedAssets) {
		this.allocatedAssets = allocatedAssets;
	}

	public int getClaim() {
		return claim;
	}

	public void setClaim(int claim) {
		this.claim = claim;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getAccidentialDamage() {
		return accidentialDamage;
	}

	public void setAccidentialDamage(String accidentialDamage) {
		this.accidentialDamage = accidentialDamage;
	}

	String coverage;

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	String accidentialDamage;

	public String getAssetTag() {
		return assetTag;
	}

	public void setAssetTag(String assetTag) {
		this.assetTag = assetTag;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	int assetCatId;

	public int getAssetCatId() {
		return assetCatId;
	}

	public void setAssetCatId(int assetCatId) {
		this.assetCatId = assetCatId;
	}

	public int getSubCatId() {
		return subCatId;
	}

	public void setSubCatId(int subCatId) {
		this.subCatId = subCatId;
	}

	int subCatId;

	String requestType;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	int requestTypeId;

	public int getRequestTypeId() {
		return requestTypeId;
	}

	public void setRequestTypeId(int requestTypeId) {
		this.requestTypeId = requestTypeId;
	}

	int staffId;
	int assetId;
	String configDescription;

	public String getConfigDescription() {
		return configDescription;
	}

	public void setConfigDescription(String configDescription) {
		this.configDescription = configDescription;
	}

	String firstName;
	String assetCatName;

	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	String subCatName;

	public String getAssetCatName() {
		return assetCatName;
	}

	public void setAssetCatName(String assetCatName) {
		this.assetCatName = assetCatName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	Date requestRaisedDate;
	int serviceId;
	Date startedDate;
	Date completionDate;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public Date getRequestRaisedDate() {
		return requestRaisedDate;
	}

	public void setRequestRaisedDate(Date requestRaisedDate) {
		this.requestRaisedDate = requestRaisedDate;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public Date getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
}
