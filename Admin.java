package ai.rnt.ams.model;

import java.util.ArrayList;

public class Admin {
	String message = "";
	
	int supplierStatusId;
	String supplierStatus;
	public int getSupplierStatusId() {
		return supplierStatusId;
	}
	public void setSupplierStatusId(int supplierStatusId) {
		this.supplierStatusId = supplierStatusId;
	}
	public String getSupplierStatus() {
		return supplierStatus;
	}
	public void setSupplierStatus(String supplierStatus) {
		this.supplierStatus = supplierStatus;
	}
	ArrayList<SupplierStatusMaster> supplierStatusList;
	ArrayList<EmployeeMaster> employeeList;
	ArrayList<MaintenanceTypeMaster> maintenanceTypeList;
	ArrayList<MaintenanceStatusMaster> maintenanceStatusList;
	ArrayList<AssetCategoryMaster> assetCategoryList;
	ArrayList<AssetSubCategoryMaster> subCategoryList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
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

	
	public ArrayList<AssetSubCategoryMaster> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(ArrayList<AssetSubCategoryMaster> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	ArrayList<LocationMaster> locationList;
	public ArrayList<EmployeeMaster> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(ArrayList<EmployeeMaster> employeeList) {
		this.employeeList = employeeList;
	}

	public ArrayList<SupplierStatusMaster> getSupplierStatusList() {
		return supplierStatusList;
	}
	public void setSupplierStatusList(ArrayList<SupplierStatusMaster> supplierStatusList) {
		this.supplierStatusList = supplierStatusList;
	}
	
    public ArrayList<LocationMaster> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<LocationMaster> locationList) {
		this.locationList = locationList;
	}
	public ArrayList<SupplierMaster> getSupplierMaster() {
		return supplierMaster;
	}

	public void setSupplierMaster(ArrayList<SupplierMaster> supplierMaster) {
		this.supplierMaster = supplierMaster;
	}

	ArrayList<SupplierMaster> supplierMaster;
	

	public ArrayList<AssetCategoryMaster> getAssetCategoryList() {
		return assetCategoryList;
	}

	public void setAssetCategoryList(ArrayList<AssetCategoryMaster> assetCategoryList) {
		this.assetCategoryList = assetCategoryList;
	}

	ArrayList<RequestTypeMaster> requestTypeList;

	
	
	public ArrayList<RequestTypeMaster> getRequestTypeList() {
		return requestTypeList;
	}

	public void setRequestTypeList(ArrayList<RequestTypeMaster> requestTypeList) {
		this.requestTypeList = requestTypeList;
	}

	ArrayList<MaintenanceStatusMaster> maintenanceStatusMaster;

	public ArrayList<MaintenanceStatusMaster> getMaintenanceStatusMaster() {
		return maintenanceStatusMaster;
	}

	public void setMaintenanceStatusMaster(ArrayList<MaintenanceStatusMaster> maintenanceStatusMaster) {
		this.maintenanceStatusMaster = maintenanceStatusMaster;
	}
	
	
}
