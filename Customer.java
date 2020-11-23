package ai.rnt.pins.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import ai.rnt.pins.util.Util;

public class Customer {
	int customerID;
	String customerName;
	String companyName;
	String domainName;
	String officeContactNumber;
	String contactPersonName;
	String contactPersonNumber;
	String contactPersonMobileNumber;
	String emailId;
	String website;
	int locationId;
	Date startDate;
	int projectId;
	String startDateInString;
	public String getStartDateInString() {
		return startDateInString;
	}
	public void setStartDateInString(String startDateInString) {
		this.startDateInString = startDateInString;
	}
	String technology;
	String masterServiceAgreement ;
	String nonDisclosureAgreement;
	String address;
	String customerStatus;	
	String edit;
	String singleProjectEdit;
	String addProject;
	String locationName;
	String addNew;
	String msg;
	
	ArrayList<Customer> customerList;
	
	Boolean readonly;
	
	Project project;
	
	
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}
	int createdBy;
	Timestamp createdDate;
	
	ArrayList<LocationMaster> locationList;
	ArrayList<ServiceTypeMaster> servicetypeList;
	ArrayList<Manager> projectManagerList;
	ArrayList<MilestoneMaster> milestoneList;
	ArrayList<Project> customerProjectList;
	ArrayList<MilestoneMaster> milestoneDetails;
	ArrayList<Customer> customerDetails;
	ArrayList<Project> singleProject;
	ArrayList<Manager> employeeList;
	
	
	
	ArrayList<Integer> percentOfAllocationList;
	ArrayList<String> engagementModelList;
	ArrayList<String> effortsUnitList;
	ArrayList<String> executionModelList;
	ArrayList<String> statusOfProjectList;
	
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setPercentOfAllocationList(ArrayList<Integer> percentOfAllocationList) {
		this.percentOfAllocationList = percentOfAllocationList;
	}
	public void setEngagementModelList(ArrayList<String> engagementModelList) {
		this.engagementModelList = engagementModelList;
	}
	public void setEffortsUnitList(ArrayList<String> effortsUnitList) {
		this.effortsUnitList = effortsUnitList;
	}
	public void setExecutionModelList(ArrayList<String> executionModelList) {
		this.executionModelList = executionModelList;
	}
	public void setStatusOfProjectList(ArrayList<String> statusOfProjectList) {
		this.statusOfProjectList = statusOfProjectList;
	}
	
	public ArrayList<Integer> getPercentOfAllocationList() {
		return percentOfAllocationList;
	}
	public void setPercentOfAllocationList() {
		this.percentOfAllocationList = Util.getPercentOfAllocation();
	}
	public ArrayList<String> getEngagementModelList() {
		return engagementModelList;
	}
	public void setEngagementModelList() {
		this.engagementModelList = Util.getEngagementModel();
	}
	public ArrayList<String> getEffortsUnitList() {
		return effortsUnitList;
	}
	public void setEffortsUnitList() {
		this.effortsUnitList = Util.getEffortsUnit();
	}
	public ArrayList<String> getExecutionModelList() {
		return executionModelList;
	}
	public void setExecutionModelList() {
		this.executionModelList = Util.getExecutionModel();
	}
	public ArrayList<String> getStatusOfProjectList() {
		return statusOfProjectList;
	}
	public void setStatusOfProjectList() {
		this.statusOfProjectList = Util.getStatusOfProject();
	}
	
	
	
	
	public ArrayList<Manager> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(ArrayList<Manager> employeeList) {
		this.employeeList = employeeList;
	}
	public Boolean getReadonly() {
		return readonly;
	}
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}
	public ArrayList<Project> getSingleProject() {
		return singleProject;
	}
	public void setSingleProject(ArrayList<Project> singleProject) {
		this.singleProject = singleProject;
	}
	public ArrayList<Customer> getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(ArrayList<Customer> customerDetails) {
		this.customerDetails = customerDetails;
	}
	public ArrayList<MilestoneMaster> getMilestoneDetails() {
		return milestoneDetails;
	}
	public void setMilestoneDetails(ArrayList<MilestoneMaster> milestoneDetails) {
		this.milestoneDetails = milestoneDetails;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getAddNew() {
		return addNew;
	}
	public void setAddNew(String addNew) {
		this.addNew = addNew;
	}
	public ArrayList<Project> getCustomerProjectList() {
		return customerProjectList;
	}
	public void setCustomerProjectList(ArrayList<Project> customerProjectList) {
		this.customerProjectList = customerProjectList;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
	
	public ArrayList<Manager> getProjectManagerList() {
		return projectManagerList;
	}
	public void setProjectManagerList(ArrayList<Manager> projectManagerList) {
		this.projectManagerList = projectManagerList;
	}
	
	public String getAddProject() {
		return addProject;
	}
	public void setAddProject(String addProject) {
		this.addProject = addProject;
	}
	public String getSingleProjectEdit() {
		return singleProjectEdit;
	}
	public void setSingleProjectEdit(String singleProjectEdit) {
		this.singleProjectEdit = singleProjectEdit;
	}
	public String getEdit() {
		return edit;
	}
	public void setEdit(String edit) {
		this.edit = edit;
	}
	
	
	public ArrayList<ServiceTypeMaster> getServicetypeList() {
		return servicetypeList;
	}
	public void setServicetypeList(ArrayList<ServiceTypeMaster> servicetypeList) {
		this.servicetypeList = servicetypeList;
		
	}
	
	public ArrayList<MilestoneMaster> getMilestoneList() {
		return milestoneList;
	}
	
	public void setMilestoneList(ArrayList<MilestoneMaster> milestoneList) {
		this.milestoneList = milestoneList;
		
	}

	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public ArrayList<LocationMaster> getLocationList() {
		return locationList;
	}
	public void setLocationList(ArrayList<LocationMaster> locationList) {
		this.locationList = locationList;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getOfficeContactNumber() {
		return officeContactNumber;
	}
	public void setOfficeContactNumber(String officeContactNumber) {
		this.officeContactNumber = officeContactNumber;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonNumber() {
		return contactPersonNumber;
	}
	public void setContactPersonNumber(String contactPersonNumber) {
		this.contactPersonNumber = contactPersonNumber;
	}
	public String getContactPersonMobileNumber() {
		return contactPersonMobileNumber;
	}
	public void setContactPersonMobileNumber(String contactPersonMobileNumber) {
		this.contactPersonMobileNumber = contactPersonMobileNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getMasterServiceAgreement() {
		return masterServiceAgreement;
	}
	public void setMasterServiceAgreement(String masterServiceAgreement) {
		this.masterServiceAgreement = masterServiceAgreement;
	}
	public String getNonDisclosureAgreement() {
		return nonDisclosureAgreement;
	}
	public void setNonDisclosureAgreement(String nonDisclosureAgreement) {
		this.nonDisclosureAgreement = nonDisclosureAgreement;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}