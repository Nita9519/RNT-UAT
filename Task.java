package ai.rnt.tms.model;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Task {
	String message ="";
	
	int projectID;
	int taskID;
	String taskName;
	int effortHours;
	int effortMinutes;
	int createdBy;
	Timestamp createdDate;
	int updatedBy;
	Timestamp updatedDate;
	int deletedBy;
	Timestamp deletedDate;
	ArrayList<Task> taskList;
	public ArrayList<Task> getTaskList() {
		return taskList;
	}
	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getEffortHours() {
		return effortHours;
	}
	public void setEffortHours(int effortHours) {
		this.effortHours = effortHours;
	}
	public int getEffortMinutes() {
		return effortMinutes;
	}
	public void setEffortMinutes(int effortMinutes) {
		this.effortMinutes = effortMinutes;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getDeletedBy() {
		return deletedBy;
	}
	public void setDeletedBy(int deletedBy) {
		this.deletedBy = deletedBy;
	}
	public Timestamp getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(Timestamp deletedDate) {
		this.deletedDate = deletedDate;
	}
}