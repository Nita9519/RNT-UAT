package ai.rnt.ams.model;

import java.sql.Date;
import java.util.ArrayList;

public class AssetRegistration {

	int staffId;
	int managerId;
	boolean isAdmin;
	String firstName;
	String middleName;
	String lastName;
	String emailId;
	String password;
	String errorMassage;
	int assetId;
	String assetTag;
	int assetCatId;
	String assetCatName;
	int claim;
	String assetAvailableStatus;
	int createdBy;
	Date createdDate;

	int subCatId;
	String subCatName;
	int supplierId;
	String supplier;

	String versionModel;
	int cost;
	String product;
	String serialNo;
	String processor;
	String ram;
	String hardDisk;
	String warranty;
	String warrantyCoverage;
	String accidentialDamage;
	String chargerSerialNo;
	int locationId;
	String location;


	String assetOwner;
	String productKey;

	
	
	
	
	
	public String getChargerSerialNo() {
		return chargerSerialNo;
	}

	public void setChargerSerialNo(String chargerSerialNo) {
		this.chargerSerialNo = chargerSerialNo;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getAssetAvailableStatus() {
		return assetAvailableStatus;
	}

	public void setAssetAvailableStatus(String assetAvailableStatus) {
		this.assetAvailableStatus = assetAvailableStatus;
	}

	public int getClaim() {
		return claim;
	}

	public void setClaim(int claim) {
		this.claim = claim;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date timestamp) {
		this.createdDate = timestamp;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getAssetCatName() {
		return assetCatName;
	}

	public void setAssetCatName(String assetCatName) {
		this.assetCatName = assetCatName;
	}


	public String getSubCatName() {
		return subCatName;
	}

	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}

	
	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getVersionModel() {
		return versionModel;
	}

	public void setVersionModel(String versionModel) {
		this.versionModel = versionModel;
	}

	

	public ArrayList<LocationMaster> getLocationList() {
		return locationList;
	}

	public void setLocationList(ArrayList<LocationMaster> locationList) {
		this.locationList = locationList;
	}

	ArrayList<LocationMaster> locationList;

	public String getAccidentialDamage() {
		return accidentialDamage;
	}

	public void setAccidentialDamage(String accidentialDamage) {
		this.accidentialDamage = accidentialDamage;
	}


	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrorMassage() {
		return errorMassage;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
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

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getWarrantyCoverage() {
		return warrantyCoverage;
	}

	public void setWarrantyCoverage(String warrantyCoverage) {
		this.warrantyCoverage = warrantyCoverage;
	}

	public String getAssetOwner() {
		return assetOwner;
	}

	public void setAssetOwner(String assetOwner) {
		this.assetOwner = assetOwner;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

}
