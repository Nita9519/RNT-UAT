package ai.rnt.pins.model;

import java.util.ArrayList;

public class CustomerMasterView {
	
	ArrayList<Customer> customerDetails;
	ArrayList<Customer> customer = null;
	ArrayList<Project> customerProjectDetails;
	ArrayList<LocationMaster> locationList;
	ArrayList<ServiceTypeMaster> servicetypeList;
	ArrayList<MilestoneMaster> milestoneList;
	ArrayList<Project> singleProject;
	ArrayList<Manager> employeeList;
	ArrayList<MilestoneMaster> milestoneDetails;
	ArrayList<Project> projectDetails;
	String message;
	String addNew;
	String edit;
	String singleProjectEdit;
	String addProject;
	int projectID;
	int associate;
	ArrayList<Project> projectList=null;
	ArrayList<Customer> customerList;
	int allocatedcount;
	int count;
	String rolename;
	ArrayList<CustomerMasterView> countallocation;
	
	
	
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public ArrayList<CustomerMasterView> getCountallocation() {
		return countallocation;
	}

	public void setCountallocation(ArrayList<CustomerMasterView> countallocation) {
		this.countallocation = countallocation;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getAllocatedcount() {
		return allocatedcount;
	}

	public void setAllocatedcount(int allocatedcount) {
		this.allocatedcount = allocatedcount;
	}

	ArrayList<ProjectTeamDetails> associateTeamList = null;
	ArrayList<Project> projectDetailsList = null;
	ArrayList<ProjectTeam> projectAssociateList = null;
	ArrayList<ProjectTeam> projectTeamList = null;
	ArrayList<ProjectTeamSkills> peopleSkills=null;
	ArrayList<ProjectTeamSkills> peopleSkillscount=null;
	String errorMsg;
	
	
	
	public ArrayList<ProjectTeamSkills> getPeopleSkillscount() {
		return peopleSkillscount;
	}

	public void setPeopleSkillscount(ArrayList<ProjectTeamSkills> peopleSkillscount) {
		this.peopleSkillscount = peopleSkillscount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	

	public int getAssociate() {
		return associate;
	}

	public void setAssociate(int associate) {
		this.associate = associate;
	}

	public ArrayList<ProjectTeamSkills> getPeopleSkills() {
		return peopleSkills;
	}

	public void setPeopleSkills(ArrayList<ProjectTeamSkills> peopleSkills) {
		this.peopleSkills = peopleSkills;
	}

	public ArrayList<Project> getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(ArrayList<Project> projectDetails) {
		this.projectDetails = projectDetails;
	}

	public ArrayList<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(ArrayList<Customer> customer) {
		this.customer = customer;
	}
	public ArrayList<Project> getCustomerProjectDetails() {
		return customerProjectDetails;
	}

	public void setCustomerProjectDetails(ArrayList<Project> customerProjectDetails) {
		this.customerProjectDetails = customerProjectDetails;
	}

	public ArrayList<Project> getSingleProject() {
		return singleProject;
	}

	public void setSingleProject(ArrayList<Project> singleProject) {
		this.singleProject = singleProject;
	}

	public ArrayList<Manager> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(ArrayList<Manager> employeeList) {
		this.employeeList = employeeList;
	}

	public ArrayList<MilestoneMaster> getMilestoneDetails() {
		return milestoneDetails;
	}

	public void setMilestoneDetails(ArrayList<MilestoneMaster> milestoneDetails) {
		this.milestoneDetails = milestoneDetails;
	}

	public String getAddNew() {
		return addNew;
	}

	public void setAddNew(String addNew) {
		this.addNew = addNew;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getSingleProjectEdit() {
		return singleProjectEdit;
	}

	public void setSingleProjectEdit(String singleProjectEdit) {
		this.singleProjectEdit = singleProjectEdit;
	}

	public String getAddProject() {
		return addProject;
	}

	public void setAddProject(String addProject) {
		this.addProject = addProject;
	}
	public ArrayList<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<Project> projectList) {
		this.projectList = projectList;
	}

	public ArrayList<Customer> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(ArrayList<Customer> customerDetails) {
		this.customerDetails = customerDetails;
	}
	
	public ArrayList<ProjectTeamDetails> getAssociateTeamList() {
		return associateTeamList;
	}

	public ArrayList<Project> getProjectDetailsList() {
		return projectDetailsList;
	}

	public ArrayList<ProjectTeam> getProjectAssociateList() {
		return projectAssociateList;
	}

	public ArrayList<ProjectTeam> getProjectTeamList() {
		return projectTeamList;
	}

	public void setAssociateTeamList(ArrayList<ProjectTeamDetails> associateTeamList) {
		this.associateTeamList = associateTeamList;
	}

	public void setProjectDetailsList(ArrayList<Project> projectDetailsList) {
		this.projectDetailsList = projectDetailsList;
	}

	public void setProjectAssociateList(ArrayList<ProjectTeam> projectAssociateList) {
		this.projectAssociateList = projectAssociateList;
	}

	public void setProjectTeamList(ArrayList<ProjectTeam> projectTeamList) {
		this.projectTeamList = projectTeamList;
	}
	public ArrayList<LocationMaster> getLocationList() {
		return locationList;
	}

	public ArrayList<ServiceTypeMaster> getServicetypeList() {
		return servicetypeList;
	}

	public ArrayList<MilestoneMaster> getMilestoneList() {
		return milestoneList;
	}

	public void setLocationList(ArrayList<LocationMaster> locationList) {
		this.locationList = locationList;
	}

	public void setServicetypeList(ArrayList<ServiceTypeMaster> servicetypeList) {
		this.servicetypeList = servicetypeList;
	}

	public void setMilestoneList(ArrayList<MilestoneMaster> milestoneList) {
		this.milestoneList = milestoneList;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	
}