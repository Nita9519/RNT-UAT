package ai.rnt.tms.model;

import java.sql.Date;
import java.sql.Time;

public class Attendance {

	int staffID;

	String fName;
	String lName;

	Time inTime;
	Time outTime;
	Date inDate;

	long totalHour;

	public long getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(long totalHour) {
		this.totalHour = totalHour;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
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

	public Time getInTime() {
		return inTime;
	}

	public void setInTime(Time inTime) {
		this.inTime = inTime;
	}

	public Time getOutTime() {
		return outTime;
	}

	public void setOutTime(Time outTime) {
		this.outTime = outTime;
	}

	public int getStaffID() {
		return staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

}
