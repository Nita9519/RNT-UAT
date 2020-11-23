
package ai.rnt.tms.dao;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.lms.model.User;
import ai.rnt.main.dao.DBConnect;
import ai.rnt.rms.model.GovermentID;
import ai.rnt.tms.model.Project;
import ai.rnt.tms.model.Task;
import ai.rnt.tms.model.Timesheet;
import ai.rnt.tms.model.TimesheetRecords;

public class TimesheetDao {

	private static final Logger log = LogManager.getLogger(TimesheetDao.class);

	public ArrayList<Timesheet> getTimesheetsListForDahboard(int staffID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT  e.staff_ID, e.project_ID, e.timesheet_date, e.task_ID , e.effort_hours, ");
		queryString.append(" e.effort_minutes, e.status, e.comment, p.project_name, t.task_name  ");
		queryString.append(" FROM emp_timesheets AS e INNER JOIN project AS p ON e.project_ID = p.project_ID ");
		queryString.append(" INNER JOIN task_master AS t ON e.task_ID = t.task_ID  ");
		queryString.append(" WHERE yearweek(DATE(e.timesheet_date), 2) = yearweek(curdate(), 2) AND e.staff_id=?");
		queryString.append(" ORDER BY e.timesheet_date desc");

		Connection connection = DBConnect.getConnection();
		try {
			log.info(queryString.toString());
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDate(rs.getDate(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setStatus(rs.getString(7));
				timesheet.setComment(rs.getString(8));
				timesheet.setProjectName(rs.getString(9));
				timesheet.setTaskName(rs.getString(10));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public ArrayList<Timesheet> getTimesheetsListForViewHistory(int staffID)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		/*
		 * queryString.append(
		 * " SELECT  e.staff_ID, e.project_ID, DATE_FORMAT(e.timesheet_date,'%d-%b-%Y'), e.task_ID , e.effort_hours, e.effort_minutes,e.description, e.status, e.comment,"
		 * ); queryString.
		 * append(" p.project_name, t.task_name, ea.in_time, ea.out_time FROM emp_timesheets AS e , project  AS p , task_master AS t ,employee_attendance AS ea "
		 * ); queryString.
		 * append(" WHERE e.task_ID = t.task_ID AND e.project_ID = p.project_ID AND e.staff_ID= ?"
		 * ); queryString.
		 * append(" AND ea.in_date=e.timesheet_date AND ea.staff_ID= e.staff_ID ");
		 * queryString.append(" ORDER BY e.timesheet_date desc ");
		 */
		queryString.append(" SELECT  e.staff_ID, e.project_ID, DATE_FORMAT(e.timesheet_date,'%d-%b-%Y'), ");
		 queryString.append(" e.task_ID , e.effort_hours, e.effort_minutes,e.description, e.status, e.comment,");
		 queryString.append(" p.project_name, t.task_name, ea.in_time, ea.out_time ");
		 queryString.append(" FROM emp_timesheets AS e ");
		 queryString.append(" JOIN project  AS p on (e.project_ID = p.project_ID )");
		 queryString.append(" JOIN task_master AS t on ( e.task_ID = t.task_ID)");
		 queryString.append(" LEFT JOIN employee_attendance AS ea ON (ea.staff_ID= e.staff_ID and ea.in_date=e.timesheet_date )");
		 queryString.append(" WHERE e.staff_ID=?");
		 queryString.append(" ORDER BY e.timesheet_date desc");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDateString(rs.getString(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setDescription(rs.getString(7));
				timesheet.setStatus(rs.getString(8));
				timesheet.setComment(rs.getString(9));
				timesheet.setProjectName(rs.getString(10));
				timesheet.setTaskName(rs.getString(11));
				timesheet.setInTime(rs.getTime(12));
				timesheet.setOutTime(rs.getTime(13));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public ArrayList<Timesheet> getTimesheetsListForViewEmployeeHistory(int staffID)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT  e.staff_ID, e.project_ID, DATE_FORMAT(e.timesheet_date,'%d-%b-%Y'), e.task_ID , e.effort_hours, e.effort_minutes,e.description, e.status, e.comment,");
		queryString.append(" p.project_name, t.task_name, ea.in_time, ea.out_time ,ea.staff_Name FROM emp_timesheets AS e , project  AS p , task_master AS t ,employee_attendance AS ea ");
		queryString.append(" WHERE e.task_ID = t.task_ID AND e.project_ID = p.project_ID AND (e.status='Approved' OR e.status='Rejected')");
		queryString.append(" AND ea.in_date=e.timesheet_date AND ea.staff_ID= e.staff_ID ");
		queryString.append(" ORDER BY e.timesheet_date desc ");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDateString(rs.getString(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setDescription(rs.getString(7));
				timesheet.setStatus(rs.getString(8));
				timesheet.setComment(rs.getString(9));
				timesheet.setProjectName(rs.getString(10));
				timesheet.setTaskName(rs.getString(11));
				timesheet.setInTime(rs.getTime(12));
				timesheet.setOutTime(rs.getTime(13));
				timesheet.setStaffName(rs.getString(14));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	} 
	public Timesheet getTimesheets(int staffID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT  e.staff_ID, e.project_ID, e.timesheet_date, e.task_ID , e.effort_hours, e.effort_minutes, e.status, e.comment,");
		queryString.append(" p.project_name, t.task_name FROM emp_timesheets AS e , project  AS p , task_master AS t ");
		queryString.append(" WHERE e.task_ID = t.task_ID AND e.project_ID = p.project_ID AND e.staff_ID= ?");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDate(rs.getDate(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setStatus(rs.getString(7));
				timesheet.setComment(rs.getString(8));
				timesheet.setProjectName(rs.getString(9));
				timesheet.setTaskName(rs.getString(10));

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return timesheet;
	}

	public ArrayList<Timesheet> getTimesheetsForApproval(int staffID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT  e.staff_ID, e.project_ID, DATE_FORMAT(e.timesheet_date,'%d-%b-%Y'), e.task_ID , e.effort_hours, e.effort_minutes, e.description, e.status, e.comment,");
		queryString.append(
				" p.project_name, t.task_name, em.F_Name, em.L_Name, em.M_Name  FROM emp_timesheets AS e , project  AS p , task_master AS t, ");
		queryString.append(
				" employee_master AS em WHERE e.task_ID = t.task_ID AND e.staff_ID=em.staff_ID  AND e.project_ID = p.project_ID AND p.Project_Manager=?");
		queryString.append(
				" AND e.status='Submitted' ORDER BY e.timesheet_date desc, p.project_Name desc, t.task_name desc LIMIT 10 ");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDateString(rs.getString(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setDescription(rs.getString(7));
				timesheet.setStatus(rs.getString(8));
				timesheet.setComment(rs.getString(9));
				timesheet.setProjectName(rs.getString(10));
				timesheet.setTaskName(rs.getString(11));
				timesheet.setFirstName(rs.getString(12));
				timesheet.setLastName(rs.getString(13));
				timesheet.setMiddleName(rs.getString(14));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public ArrayList<Timesheet> getTimesheetsForEdit(int staffID, Date date)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT  e.staff_ID, e.project_ID, e.timesheet_date, e.task_ID , e.effort_hours, e.effort_minutes, e.inTime, e.outTime, e.description, e.status, e.comment,  p.project_name, t.task_name ");
		queryString
				.append("  FROM emp_timesheets AS e , project  AS p ,task_master AS t  WHERE e.task_ID = t.task_ID  ");
		queryString.append(" AND e.project_ID = p.project_ID  AND e.status='Saved' AND e.staff_ID=?");
		queryString.append(" AND e.timesheet_date=?");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setDate(2, date);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDate(rs.getDate(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setInTime(rs.getTime(7));
				timesheet.setOutTime(rs.getTime(8));
				timesheet.setDescription(rs.getString(9));
				timesheet.setStatus(rs.getString(10));
				timesheet.setDataStatus("false");
				timesheet.setComment(rs.getString(11));
				timesheet.setProjectName(rs.getString(12));
				timesheet.setTaskName(rs.getString(13));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public ArrayList<Timesheet> getTimesheetsefforts(int staffID, Date date)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT  e.staff_ID, e.timesheet_date, e.effort_hours, e.effort_minutes, e.status ");
		queryString.append("  FROM emp_timesheets AS e  WHERE ");
		queryString.append(" e.status='Saved' AND e.staff_ID=?");
		queryString.append(" AND e.timesheet_date=?");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setDate(2, date);
			rs = statement.executeQuery();
			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setTimesheetDate(rs.getDate(2));
				timesheet.setEffortHours(rs.getInt(3));
				timesheet.setEffortMinutes(rs.getInt(4));
				timesheet.setStatus(rs.getString(5));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public ArrayList<Timesheet> getTimesheetsForProcessing(int staffID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT  e.staff_ID, e.project_ID, e.timesheet_date, e.task_ID , e.effort_hours, e.effort_minutes, e.status, e.comment,  p.project_name, t.task_name ");
		queryString
				.append("  FROM emp_timesheets AS e , project  AS p ,task_master AS t  WHERE e.task_ID = t.task_ID  ");
		queryString.append(" AND e.project_ID = p.project_ID AND e.staff_ID=?");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				timesheet = new Timesheet();

				timesheet.setStaffID(rs.getInt(1));
				timesheet.setProjectID(rs.getInt(2));
				timesheet.setTimesheetDate(rs.getDate(3));
				timesheet.setTaskID(rs.getInt(4));
				timesheet.setEffortHours(rs.getInt(5));
				timesheet.setEffortMinutes(rs.getInt(6));
				timesheet.setStatus(rs.getString(7));
				timesheet.setDataStatus("false");
				timesheet.setComment(rs.getString(8));
				timesheet.setProjectName(rs.getString(9));
				timesheet.setTaskName(rs.getString(10));

				list.add(timesheet);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public TimesheetRecords getTimesheetsTotalsOfHourMin(int staffID, TimesheetRecords timesheetRecords)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		int hour = 0;
		int min = 0;
		PreparedStatement statement = null;

		queryString.append(
				" SELECT   e.effort_hours, e.effort_minutes FROM emp_timesheets AS e , project  AS p ,task_master AS t  WHERE e.task_ID = t.task_ID ");
		queryString.append(" AND e.project_ID = p.project_ID  AND e.status='Saved' AND e.staff_ID=?");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {

				hour = hour + rs.getInt(1);
				min = min + rs.getInt(2);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		if (min >= 60) {
			hour = hour + (int) (min / 60);
			min = min % 60;

			timesheetRecords.setTotalHour(hour);
			timesheetRecords.setTotalMin(min);
		} else {
			timesheetRecords.setTotalHour(hour);
			timesheetRecords.setTotalMin(min);
		}

		return timesheetRecords;
	}

	public ArrayList<Task> getTasks(int seqID) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<Task> list = new ArrayList<Task>();
		Task task = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT task_ID, GetTaskName(task_ID), effort_hours, effort_minutes FROM emp_ts_tasks WHERE seq_ID=?");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, seqID);
			rs = statement.executeQuery();
			while (rs.next()) {
				task = new Task();
				task.setTaskID(rs.getInt(1));
				task.setTaskName(rs.getString(2));
				task.setEffortHours(rs.getInt(3));
				task.setEffortMinutes(rs.getInt(4));
				list.add(task);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public ArrayList<Project> getProjectDetails(int staffID) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<Project> list = new ArrayList<Project>();
		Project project = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT Distinct pt.project_ID, p.project_name FROM project_team as pt, project as p  WHERE pt.project_ID = p.project_ID ");
		queryString.append("AND pt.staff_ID =  ?");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setProjectID(rs.getInt(1));
				project.setProjectName(rs.getString(2));
				list.add(project);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public int getManagerForProject(int projectID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		queryString.append("SELECT Project_Manager FROM project WHERE project_ID=?");
		int managerID = 0;

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectID);
			rs = statement.executeQuery();
			while (rs.next()) {
				managerID = rs.getInt(1);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return managerID;
	}

	public ArrayList<Task> getTaskDetails() throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<Task> list = new ArrayList<Task>();
		Task task = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT task_ID,task_name FROM task_master WHERE deleted_by IS NULL ");

		Connection connection = DBConnect.getConnection();

		try {

			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				task = new Task();
				task.setTaskID(rs.getInt(1));
				task.setTaskName(rs.getString(2));
				// task.setTaskID(rs.getInt(2));
				list.add(task);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
			

		}
		return list;
	}

	public User getUserDetails(int staffID, String password) throws SQLException, PropertyVetoException, IOException {
		// Employee check
		User user = new User();
		user.setStaffID(staffID);

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT Staff_ID, F_name, M_Name, L_Name, Manager_ID, email_ID, ");
		queryString.append("(SELECT email_ID from employee_master em WHERE em.Staff_ID = em1.Manager_ID)");
		queryString.append(" FROM  employee_master em1 WHERE staff_id = ?");

		if (!password.equals("for email purpose")) {
			queryString.append(" and password =?");
		}

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setString(2, password);
			rs = statement.executeQuery();
			// interacting and check in condition
			while (rs.next()) {
				user.setFirstName(rs.getString(2));
				user.setMiddleName(rs.getString(3));
				user.setLastName(rs.getString(4));
				user.setManagerID(rs.getInt(5));
				user.setEmailID(rs.getString(6));
				user.setManagerEmailID(rs.getString(7));

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return user;

	}

	public void getProfilePicture(int staffID, String path) throws SQLException, PropertyVetoException, IOException {
		// Employee check

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT ep.Profile_Picture FROM employee_master AS em INNER JOIN Employee_Profile AS ep ");
		queryString.append(" WHERE em.staff_ID=ep.Profile_ID AND staff_ID=?");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				Blob blob = rs.getBlob(1);
				if (blob != null) {
					byte barr[] = blob.getBytes(1, (int) blob.length());// 1 means first image
					String URL = path + staffID + ".jpg";
					File file = new File(URL);
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(barr);
					fos.close();
				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

	}

	public Boolean checkForDuplicacy(Timesheet timesheet) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Boolean duplicateStatus = false;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		queryString.append("SELECT count(*) FROM emp_timesheets WHERE timesheet_date=?");
		queryString.append(" AND project_ID=?");
		queryString.append(" AND staff_ID=?");
		queryString.append(" AND task_ID=?");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setDate(1, timesheet.getTimesheetDate());
			statement.setInt(2, timesheet.getProjectID());
			statement.setInt(3, timesheet.getStaffID());
			statement.setInt(4, timesheet.getTaskID());
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					duplicateStatus = true;

				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return duplicateStatus;
	}

	public String approveTimesheet(Date tDate, int adminID, int staffID, String action)
			throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		String insertStatus = action;

		queryString.append("UPDATE emp_timesheets SET status=? ,updated_by=?");
		queryString.append(",updated_date=CURRENT_TIMESTAMP() WHERE timesheet_date='"+tDate);
		queryString.append("' AND staff_ID="+staffID);

		try {
			statement = connection.prepareStatement(queryString.toString());

			statement.setString(1, action);
			statement.setInt(2, adminID);
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				/* insertStatus = true; */
				log.info("Timesheet was updated successfully!");
			}
		} finally {
			if (statement != null)
				statement.close();

			connection.close();
		}
		return insertStatus;
	}

	public Boolean rejectTimesheet(Date tsDate, int adminID, int staffID) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean insertStatus = false;

		queryString.append("UPDATE emp_timesheets SET status='Rejected', updated_by="+adminID);
		queryString.append(",updated_date=CURRENT_TIMESTAMP() WHERE timesheet_date=?");
		queryString.append(" AND staff_ID=?");

		try {
			statement = connection.prepareStatement(queryString.toString());
			
			statement.setDate(1, tsDate);
			statement.setInt(2, staffID);
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				insertStatus = true;
				log.info("Timesheet was updated successfully!");
			}
		} finally {
			if (statement != null)
				statement.close();

			connection.close();
		}
		return insertStatus;
	}

	public Boolean updateTaskStatus(Timesheet timesheet) throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;

		StringBuffer queryString = new StringBuffer();

		queryString.append(
				"UPDATE emp_timesheets SET status=?, updated_by=?, updated_date=CURRENT_TIMESTAMP() WHERE deleted_by IS NULL AND timesheet_date='"+timesheet.getTimesheetDate()+"'");
		queryString.append(" AND  staff_ID="+timesheet.getStaffID());

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, timesheet.getStatus());
			statement.setInt(2, timesheet.getUpdatedBy());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("Task was updated successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}

	public Boolean insertTimesheet(Timesheet timesheet) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean insertStatus = false;

		queryString.append(
				"INSERT INTO emp_timesheets(staff_ID, project_ID, timesheet_date, task_ID, effort_hours, effort_minutes,description, status, comment, created_by, created_date) ");
		queryString.append("VALUES (?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP())");
log.info("query=="+queryString.toString());
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, timesheet.getStaffID());
			
			statement.setInt(2, timesheet.getProjectID());
			statement.setDate(3, timesheet.getTimesheetDate());
			statement.setInt(4, timesheet.getTaskID());
			statement.setInt(5, timesheet.getEffortHours());
			statement.setInt(6, timesheet.getEffortMinutes());
			/*
			 * statement.setTime(7, timesheet.getInTime()); statement.setTime(8,
			 * timesheet.getOutTime());
			 */
			statement.setString(7, timesheet.getDescription());
			statement.setString(8, timesheet.getStatus());
			statement.setString(9, timesheet.getComment());
			statement.setInt(10, timesheet.getStaffID());
log.info("getStaffID=="+timesheet.getStaffID());
log.info("getProjectID=="+timesheet.getProjectID());
log.info("getTimesheetDate=="+timesheet.getTimesheetDate());
log.info("getTaskID=="+timesheet.getTaskID());
log.info("getEffortHours=="+timesheet.getEffortHours());
log.info("getEffortMinutes=="+timesheet.getEffortMinutes());
log.info("getDescription=="+timesheet.getDescription());
log.info("getStatus=="+timesheet.getStatus());
log.info("getComment=="+timesheet.getComment());
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				insertStatus = true;
				log.info("A new timesheet was inserted successfully!");
			}
		} finally {
			if (statement != null)
				statement.close();

			connection.close();
		}
		return insertStatus;
	}

	public Boolean deleteTaskSheet(Timesheet timesheet) throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;

		StringBuffer queryString = new StringBuffer();

		queryString.append(
				"DELETE FROM emp_timesheets WHERE staff_ID=? AND project_ID = ? AND task_ID = ? AND timesheet_date = ?  ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, timesheet.getStaffID());
			statement.setInt(2, timesheet.getProjectID());
			statement.setInt(3, timesheet.getTaskID());
			statement.setDate(4, timesheet.getTimesheetDate());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("Task was updated successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}

	public Boolean updateTaskSheet(Timesheet timesheet) throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;

		StringBuffer queryString = new StringBuffer();

		queryString.append(
				"UPDATE emp_timesheets SET effort_hours=?, effort_minutes=?,description=?, status=?, comment=?, updated_by=?, updated_date=CURRENT_TIMESTAMP() WHERE timesheet_date=? AND  staff_ID=? ");
		queryString.append(" AND project_ID=? AND task_ID=? ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, timesheet.getEffortHours());
			statement.setInt(2, timesheet.getEffortMinutes());
			statement.setString(3, timesheet.getDescription());
			statement.setString(4, timesheet.getStatus());
			statement.setString(5, timesheet.getComment());
			statement.setInt(6, timesheet.getStaffID());

			statement.setDate(7, timesheet.getTimesheetDate());
			statement.setInt(8, timesheet.getStaffID());
			statement.setInt(9, timesheet.getProjectID());
			statement.setInt(10, timesheet.getTaskID());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("Task was updated successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}

	public ArrayList<Timesheet> getSubmitTimesheetDate(int staffID) throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		ArrayList<Timesheet> list = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT timesheet_date FROM emp_timesheets WHERE status='Submitted' AND staff_id=?");

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setTimesheetDate(rs.getDate(1));
				list.add(timesheet);

			}

		} catch (NumberFormatException e) {
		} finally {
			rs.close();
			connection.close();
			stmt.close();
		}

		return list;
	}

	public ArrayList<Timesheet> getmonthList() throws SQLException, PropertyVetoException {

		ArrayList<Timesheet> monthLists = new ArrayList<Timesheet>();
		Timesheet timesheet = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT month_ID, month_name FROM month_list ");
		Connection connection = DBConnect.getConnection();
		Statement statement = connection.createStatement();
		try {

			rs = statement.executeQuery(queryString.toString());

			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setMonthId(rs.getInt(1));
				timesheet.setMonthName(rs.getString(2));
				monthLists.add(timesheet);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return monthLists;

	}
}
