package ai.rnt.pins.model;

import java.util.ArrayList;

public class HardwareMasterView {
	ArrayList<HardwareMaster> hardware = null;
	String message;

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<HardwareMaster> getHardware() {
		return hardware;
	}

	public void setHardware(ArrayList<HardwareMaster> hardware) {
		this.hardware = hardware;
	}
}