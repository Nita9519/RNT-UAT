package ai.rnt.pins.model;


import java.util.ArrayList;

public class CustomerView {
	ArrayList<Customer> customer = null;
	ArrayList<LocationMaster> locationList;
	ArrayList<ServiceTypeMaster> servicetypeList;
	ArrayList<MilestoneMaster> milestoneList;
	
	public ArrayList<Customer> getCustomer() {
		return customer;
	}
	public void setCustomer(ArrayList<Customer> customer) {
		this.customer = customer;
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
	
	public ArrayList<LocationMaster> getLocationList() {
		return locationList;
	}
	public void setLocationList(ArrayList<LocationMaster> locationList) {
		this.locationList = locationList;
	}
}