package ai.rnt.pins.model;

import java.util.ArrayList;

public class Dashboard {

	int customerCount;	
	int projectCount;
	boolean isAdmin;
	boolean isManager;
	String firstName;
	String lastName;
	String emailId;
	String password;
	int StaffID;
	String Password;
	String managerEmailID;
	String middleName;
	int managerID;
	String errorMassage;
	
	String imagePath;
	
	ArrayList<Alerts> alerts;
	ArrayList<MilestoneMaster> project;
	ArrayList <ProjectHealth> projecthealth;
	
	ArrayList<Project> topfourprojects;
	ArrayList<Project> projectlist;
	ArrayList<Customer> topfourlist;
	ArrayList<Customer> customer;
	ArrayList<Project> projectListForOverview;
	ArrayList <EffortsStatus> effortsstatus;
	ArrayList<Project> getActiveProjectsList;
	
	
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public ArrayList<Project> getGetActiveProjectsList() {
		return getActiveProjectsList;
	}
	public void setGetActiveProjectsList(ArrayList<Project> getActiveProjectsList) {
		this.getActiveProjectsList = getActiveProjectsList;
	}
	public int getCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}
	public int getProjectCount() {
		return projectCount;
	}
	public void setProjectCount(int projectCount) {
		this.projectCount = projectCount;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	public int getStaffID() {
		return StaffID;
	}
	public void setStaffID(int staffID) {
		StaffID = staffID;
	}
	
	public String getManagerEmailID() {
		return managerEmailID;
	}
	public void setManagerEmailID(String managerEmailID) {
		this.managerEmailID = managerEmailID;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public int getManagerID() {
		return managerID;
	}
	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}
	public String getErrorMassage() {
		return errorMassage;
	}
	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}
	public ArrayList<Alerts> getAlerts() {
		return alerts;
	}
	public void setAlerts(ArrayList<Alerts> alerts) {
		this.alerts = alerts;
	}
	
	public ArrayList<MilestoneMaster> getProject() {
		return project;
	}
	public void setProject(ArrayList<MilestoneMaster> project) {
		this.project = project;
	}
	public ArrayList<ProjectHealth> getProjecthealth() {
		return projecthealth;
	}
	public void setProjecthealth(ArrayList<ProjectHealth> projecthealth) {
		this.projecthealth = projecthealth;
	}
	public ArrayList<Project> getTopfourprojects() {
		return topfourprojects;
	}
	public void setTopfourprojects(ArrayList<Project> topfourprojects) {
		this.topfourprojects = topfourprojects;
	}
	public ArrayList<Project> getProjectlist() {
		return projectlist;
	}
	public void setProjectlist(ArrayList<Project> projectlist) {
		this.projectlist = projectlist;
	}
	public ArrayList<Customer> getTopfourlist() {
		return topfourlist;
	}
	public void setTopfourlist(ArrayList<Customer> topfourlist) {
		this.topfourlist = topfourlist;
	}
	public ArrayList<Customer> getCustomer() {
		return customer;
	}
	public void setCustomer(ArrayList<Customer> customer) {
		this.customer = customer;
	}
	public ArrayList<Project> getProjectListForOverview() {
		return projectListForOverview;
	}
	public void setProjectListForOverview(ArrayList<Project> projectListForOverview) {
		this.projectListForOverview = projectListForOverview;
	}
	public ArrayList<EffortsStatus> getEffortsstatus() {
		return effortsstatus;
	}
	public void setEffortsstatus(ArrayList<EffortsStatus> effortsstatus) {
		this.effortsstatus = effortsstatus;
	}
	
	


}