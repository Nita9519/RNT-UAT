package ai.rnt.ams.model;

import java.sql.Date;
import java.util.ArrayList;

public class AssetAllocation {
	int requestId;
	int requestTypeId;
	String reqStatus;
	String comment;
	int assetCatId;
	String assetCatName;
	int subCatId;
	String subCatName;
	int staffId;
	String assetTag;
	String configuration;
	String processor;
	String ram;
	String hardDisk;
	int assetId;
	String firstName;
    Date allocationDate;
    Date estReleasedDate;
    Date actualReleasedDate;
    String assetAvailableStatus;
    String fName;  
    String lName;
String suplier;


	public String getSuplier() {
	return suplier;
}

public void setSuplier(String suplier) {
	this.suplier = suplier;
}

	ArrayList<AssetAllocation> availAssetListWithRequest;
	ArrayList<AssetAllocation> allocatedAssets;
	
	ArrayList<RaiseRequest> assetRequestList;
	ArrayList<AssetAllocation> availbleAssetforApproveStatus;
	
	
    
	public ArrayList<AssetAllocation> getAvailbleAssetforApproveStatus() {
		return availbleAssetforApproveStatus;
	}

	public void setAvailbleAssetforApproveStatus(ArrayList<AssetAllocation> availbleAssetforApproveStatus) {
		this.availbleAssetforApproveStatus = availbleAssetforApproveStatus;
	}

	public String getAssetAvailableStatus() {
		return assetAvailableStatus;
	}

	public void setAssetAvailableStatus(String assetAvailableStatus) {
		this.assetAvailableStatus = assetAvailableStatus;
	}


    public ArrayList<AssetAllocation> getAllocatedAssets() {
		return allocatedAssets;
	}

	public void setAllocatedAssets(ArrayList<AssetAllocation> allocatedAssets) {
		this.allocatedAssets = allocatedAssets;
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

	

	public ArrayList<AssetAllocation> getAvailAssetListWithRequest() {
		return availAssetListWithRequest;
	}

	public void setAvailAssetListWithRequest(ArrayList<AssetAllocation> availAssetListWithRequest) {
		this.availAssetListWithRequest = availAssetListWithRequest;
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

	RaiseRequest raiseRequestDetails = new RaiseRequest();

	public RaiseRequest getRaiseRequestDetails() {
		return raiseRequestDetails;
	}

	public void setRaiseRequestDetails(RaiseRequest raiseRequestDetails) {
		this.raiseRequestDetails = raiseRequestDetails;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	boolean isAdmin;

	public ArrayList<RaiseRequest> getAssetRequestList() {
		return assetRequestList;
	}

	public void setAssetRequestList(ArrayList<RaiseRequest> assetRequestList) {
		this.assetRequestList = assetRequestList;
	}

	

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	String requestType;

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

	public String getAssetTag() {
		return assetTag;
	}

	public void setAssetTag(String assetTag) {
		this.assetTag = assetTag;
	}

	public int getAssetId() {
		return assetId;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

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

}
