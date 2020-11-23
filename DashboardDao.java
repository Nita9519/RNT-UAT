
package ai.rnt.tms.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pms.model.EmployeeKra;
import ai.rnt.tms.dao.DashboardDao;
import ai.rnt.tms.model.Attendance;
import ai.rnt.tms.model.Timesheet;

public class DashboardDao {

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(DashboardDao.class);

	// method for admin check
	public boolean isValidAdmin(int staffID, int applicationID) throws SQLException, PropertyVetoException {

		boolean isAdmin = false;
		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(*) from admin_info where staff_ID=?");
		queryString.append(" AND Application_ID=?");
		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setInt(2, applicationID);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					isAdmin = true;

				}
			}
		} finally {

			rs.close();
			statement.close();
			connection.close();
		}
		return isAdmin;

	}

	// method for valid Manager
	public boolean isValidManager(int staffID) throws SQLException, PropertyVetoException {
		// Manager check
		boolean isManager = false;
		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(*) from employee_master where manager_ID = ?");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					isManager = true;
				}
			}
		} finally {
			rs.close();

			statement.close();
			connection.close();
		}
		return isManager;
	}

	public ArrayList<Attendance> getAttendanceList(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Attendance attendance = null;
		ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		queryString.append(
				"SELECT em.f_Name, em.l_Name, ea.in_date, ea.in_time, ea.out_time FROM employee_attendance AS ea LEFT JOIN employee_master AS em ");
		queryString.append(" ON ea.staff_ID = em.staff_ID WHERE em.staff_ID= ?");
		queryString.append(" AND ea.in_date BETWEEN DATE_SUB(CURDATE(),INTERVAL 6 DAY) AND CURDATE() Order by ea.in_date DESC");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				attendance = new Attendance();
				attendance.setfName(rs.getString(1));
				attendance.setlName(rs.getString(2));
				attendance.setInDate(rs.getDate(3));
				attendance.setInTime(rs.getTime(4));
				attendance.setOutTime(rs.getTime(5));
				attendanceList.add(attendance);
			}

		} catch (NumberFormatException | NullPointerException e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return attendanceList;
	}

	// SACHIT
	public ArrayList<Timesheet> getTimesheetSubmittedList(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Timesheet timesheet = null;
		ArrayList<Timesheet> taskSubmittedList = new ArrayList<Timesheet>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		queryString.append(
				"SELECT em.f_Name, em.l_Name, ea.in_date, ea.in_time, ea.out_time FROM employee_attendance AS ea LEFT JOIN employee_master AS em ");
		queryString.append(" ON ea.staff_ID = em.staff_ID WHERE em.staff_ID= ?");
		queryString.append(" AND ea.in_date=CURDATE() Order by ea.in_date DESC");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				timesheet = new Timesheet();
				/*
				 * timesheet.setfName(rs.getString(1)); timesheet.setlName(rs.getString(2));
				 * timesheet.setInDate(rs.getDate(3));
				 */
				timesheet.setInTime(rs.getTime(4));
				timesheet.setOutTime(rs.getTime(5));
				taskSubmittedList.add(timesheet);
			}

		} catch (NumberFormatException | NullPointerException e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return taskSubmittedList;
	}

	// sachit
	public ArrayList<Timesheet> getTimesheetSubmittedListForUser(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Timesheet timesheet = null;
		ArrayList<Timesheet> taskSubmittedList = new ArrayList<Timesheet>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		queryString.append(" SELECT 'Submitted',COUNT(staff_ID) AS total");
		queryString.append(" FROM emp_timesheets");
		queryString.append(" WHERE timesheet_date BETWEEN");
		queryString.append(" DATE_SUB(CURDATE(),INTERVAL 7");
		queryString.append(" DAY) AND CURDATE() AND status='Submitted' AND staff_ID=?");
		queryString.append(" UNION ALL");
		queryString.append(" SELECT 'Approved',COUNT(staff_ID) AS total");
		queryString.append(" FROM emp_timesheets");
		queryString.append(" WHERE timesheet_date BETWEEN");
		queryString.append(" DATE_SUB(CURDATE(),INTERVAL 7");
		queryString.append(" DAY) AND CURDATE() AND status='Approved' AND staff_ID=" + staffID);
		log.info(queryString.toString());

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setStatus(rs.getString(1));

				if (rs.getInt(2) >= 0)
					timesheet.setCount(rs.getInt(2));
				taskSubmittedList.add(timesheet);
			}
		} catch (NullPointerException e) {
			log.error("Got Exception while Fetching team kra count ::  " + e);

		} finally {
			statement.close();
			connection.close();
		}
		return taskSubmittedList;
	}

	public ArrayList<Timesheet> getTimesheetSubmittedListForManager(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Timesheet timesheet = null;
		ArrayList<Timesheet> taskSubmittedList = new ArrayList<Timesheet>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		queryString.append(" SELECT et.staff_ID, et.status,COUNT(et.status)");
		queryString.append(" FROM employee_master AS e ");
		queryString.append(" LEFT JOIN emp_timesheets AS et ON e.Staff_ID=et.staff_ID ");
		queryString.append(" WHERE et.timesheet_date ");
		queryString.append(" BETWEEN DATE_SUB( CURDATE( ) , INTERVAL (dayofweek(CURDATE())+6)  DAY ) ");
		queryString.append(" AND DATE_SUB( CURDATE( ) , INTERVAL (dayofweek(CURDATE())-1)  DAY ) ");
		queryString.append(" AND e.Manager_ID=?");
		queryString.append(" GROUP BY et.staff_ID, et.status");
		log.info(queryString.toString());

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				timesheet = new Timesheet();
				timesheet.setStaffID(rs.getInt(1));
				timesheet.setStatus(rs.getString(2));

				if (rs.getInt(3) >= 0)
					timesheet.setCount(rs.getInt(3));
				taskSubmittedList.add(timesheet);
			}
		} catch (NullPointerException e) {
			log.error("Got Exception while Fetching team kra count ::  " + e);

		} finally {
			statement.close();
			connection.close();
		}
		return taskSubmittedList;
	}
	
	public ArrayList<Timesheet> getTimesheetSubmittedListForManagerCurrentWeek(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Timesheet timesheet = null;
		ArrayList<Timesheet> taskSubmittedList = new ArrayList<Timesheet>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		queryString.append(" SELECT 'Submitted',COUNT(et.staff_ID) AS total FROM employee_master AS e ");
		queryString.append(" LEFT JOIN emp_timesheets AS et ON e.Staff_ID=et.staff_ID ");
		queryString.append(" WHERE et.timesheet_date BETWEEN DATE_SUB(CURDATE(),INTERVAL 8 DAY) AND CURDATE() ");
		queryString.append(" AND et.status='Submitted' AND e.Manager_ID=?  ");
		queryString.append(" UNION ALL (SELECT 'Approved',");
		queryString.append(" COUNT(et.staff_ID) AS total FROM employee_master AS e ");
		queryString.append(" LEFT JOIN emp_timesheets AS et ON e.Staff_ID=et.staff_ID ");
		queryString.append(" WHERE et.timesheet_date BETWEEN DATE_SUB(CURDATE(),INTERVAL 8 DAY)");
		queryString.append(" AND CURDATE() AND et.status='Approved' AND e.Manager_ID=");
		queryString.append(staffID+")");
		log.info(queryString.toString());

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			while (rs.next()) {
				timesheet = new Timesheet();
				
				timesheet.setStatus(rs.getString(1));

				if (rs.getInt(2) >= 0)
					timesheet.setCount(rs.getInt(2));
				taskSubmittedList.add(timesheet);
			}
		} catch (NullPointerException e) {
			log.error("Got Exception while Fetching team kra count ::  " + e);

		} finally {
			statement.close();
			connection.close();
		}
		return taskSubmittedList;
	}
	
	public ArrayList<Attendance> getAttendanceListforEmp(int staffID)
			throws SQLException, PropertyVetoException, ParseException {
		Attendance attendance = null;
		ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT em.f_Name, em.l_Name, ea.in_date, ea.in_time, ea.out_time");
		queryString.append(" FROM employee_attendance AS ea");
		queryString.append(" LEFT JOIN employee_master AS em");
		queryString.append(" ON ea.staff_ID = em.staff_ID");
		queryString.append(" WHERE em.staff_ID=?");

		queryString.append(" AND ea.in_date=CURRENT_DATE()");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				attendance = new Attendance();
				attendance.setfName(rs.getString(1));
				attendance.setlName(rs.getString(2));
				attendance.setInDate(rs.getDate(3));
				attendance.setInTime(rs.getTime(4));
				attendance.setOutTime(rs.getTime(5));
				attendanceList.add(attendance);
			}

		} catch (NumberFormatException | NullPointerException e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return attendanceList;
	}

	public int[] insertAttendance(ArrayList<Attendance> attendanceList)
			throws SQLException, PropertyVetoException, ParseException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		int[] result = null;
		String queryString = "INSERT INTO employee_attendance (staff_id, staff_Name, in_date, in_time, out_time) values (?, ?, ?, ?, ?)";
		try {
			stmt = connection.prepareStatement(queryString);
			connection.setAutoCommit(false);
			for (Attendance attendance : attendanceList) {
				stmt.setInt(1, attendance.getStaffID());
				stmt.setString(2, attendance.getfName());
				stmt.setDate(3, attendance.getInDate());
				stmt.setTime(4, attendance.getInTime());
				stmt.setTime(5, attendance.getOutTime());
				stmt.addBatch();
			}
			result = stmt.executeBatch();
			System.out.println("The number of rows inserted: " + result.length);
			connection.commit();
		} catch (NumberFormatException | NullPointerException e) {
		} finally {

			stmt.close();
			connection.close();
		}
		return result;
	}

}