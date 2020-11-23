package ai.rnt.pins.model;

import java.util.ArrayList;

public class LocationMasterView {
	ArrayList<LocationMaster> location = null;
	String message;

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<LocationMaster> getLocation() {
		return location;
	}

	public void setLocation(ArrayList<LocationMaster> location) {
		this.location = location;
	}
}