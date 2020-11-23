package ai.rnt.lms.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.lms.model.Dashboard;
import ai.rnt.lms.model.LeaveAllotment;
import ai.rnt.lms.model.LeaveRecord;
import ai.rnt.lms.model.User;
import ai.rnt.main.dao.DBConnect;
import ai.rnt.rms.model.GovermentID;

public class LeaveRecordsDao {

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(LeaveRecordsDao.class);

	// method for valid employee
	public User getEmployeeDetails(int staffID, String password) throws SQLException, PropertyVetoException {
		// Employee check
		User user = new User();

		user.setStaffID(staffID);

		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT Staff_ID,F_name,M_Name,L_Name,Manager_ID,email_ID,");
		queryString.append("(SELECT email_ID FROM employee_master em WHERE em.Staff_ID = em1.Manager_ID)");
		queryString.append(" FROM  employee_master em1 WHERE staff_id =?");
		queryString.append(" AND password =?");
		/*
		 * if (!password.equals("for email purpose")) {
		 * queryString.append(" and password =?"); }
		 */
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

	public List<LeaveRecord> getLeaveRecordsforApproval(int staffID) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<LeaveRecord> list = new ArrayList<LeaveRecord>();
		LeaveRecord leaverecord = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT e.staff_id, e.f_name, e.m_name, e.l_name,DATE_FORMAT(l.From_date,'%d-%b-%Y'), DATE_FORMAT(l.to_date,'%d-%b-%Y'),l.no_of_days,l.leave_type, l.reason, l.Status,l.sequence_no ");
		queryString.append("FROM employee_master AS e ");
		queryString.append("INNER JOIN emp_leave_records AS l ");
		queryString.append("WHERE e.staff_id = l.staff_id ");
		queryString.append("AND l.status =  'Pending'");
		queryString.append(" AND e.Manager_ID = ?");
		queryString.append(" ORDER BY l.staff_id,l.From_date desc");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				leaverecord = new LeaveRecord();
				leaverecord.setStaffID(rs.getInt(1));
				leaverecord.setFirstName(rs.getString(2));
				leaverecord.setMiddleName(rs.getString(3));
				leaverecord.setLastName(rs.getString(4));
				leaverecord.setFromdateInString(rs.getString(5));
				leaverecord.setTodateInString(rs.getString(6));
				leaverecord.setNoOfDays(rs.getInt(7));
				leaverecord.setLeaveType(rs.getString(8));
				leaverecord.setReason(rs.getString(9));
				leaverecord.setStatus(rs.getString(10));
				leaverecord.setSequenceNo(rs.getInt(11));

				list.add(leaverecord);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public List<LeaveRecord> getdisplayleaveshistoryofemployee(int staffID) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		String sqlDateOfBirth = null;
		PreparedStatement statement = null;
		ArrayList<LeaveRecord> list = new ArrayList<LeaveRecord>();
		LeaveRecord leaverecord = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT e.Staff_id,e.Manager_ID, e.f_name, e.m_name, e.l_name,DATE_FORMAT(l.From_date,'%d-%b-%Y'), DATE_FORMAT(l.to_date,'%d-%b-%Y'),l.no_of_days,l.leave_type, l.reason, l.Status,l.sequence_no ");
		queryString.append(" FROM employee_master AS e,");
		queryString.append(" emp_leave_records AS l ");
		queryString.append(" WHERE e.staff_id = l.staff_id ");
		queryString.append(" AND (l.status =  'Approved' OR l.status = 'Rejected')");
		queryString.append(" AND e.Manager_ID = ?");
		queryString.append(
				" UNION SELECT e.Staff_id,e.Manager_ID, e.f_name, e.m_name, e.l_name,DATE_FORMAT(l.From_date,'%d-%b-%Y'), DATE_FORMAT(l.to_date,'%d-%b-%Y'),l.no_of_days,l.leave_type, l.reason, l.Status,l.sequence_no from employee_master AS e,");
		queryString.append(" emp_leave_records AS l ");
		queryString.append(" WHERE e.staff_id = l.staff_id ");
		queryString.append(" AND (l.status =  'Approved' OR l.status = 'Rejected')");
		queryString.append(" AND e.Manager_ID IN ");
		queryString.append(" (Select Staff_ID from employee_master where Manager_ID=? ");
		queryString.append(" ) ");
		queryString.append(" AND l.status IS NOT NULL");
		/* queryString.append(" ORDER BY e.staff_id asc"); */
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setInt(2, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				leaverecord = new LeaveRecord();
				leaverecord.setStaffID(rs.getInt(1));
				leaverecord.setManagerID(rs.getInt(2));
				leaverecord.setFirstName(rs.getString(3));
				leaverecord.setMiddleName(rs.getString(4));
				leaverecord.setLastName(rs.getString(5));
				leaverecord.setFromdateInString(rs.getString(6));
				leaverecord.setTodateInString(rs.getString(7));
				leaverecord.setNoOfDays(rs.getInt(8));
				leaverecord.setLeaveType(rs.getString(9));
				leaverecord.setReason(rs.getString(10));
				leaverecord.setStatus(rs.getString(11));
				leaverecord.setSequenceNo(rs.getInt(12));

				list.add(leaverecord);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	// method for valid used leave for dashboard
	public Boolean getUsedLeaves(int staffID, Dashboard dashboard) throws SQLException, PropertyVetoException {

		HashMap<String, String> hashMap = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT Leave_type, SUM(no_of_days) FROM emp_leave_records WHERE status='Approved' and staff_ID=?");
		queryString.append(" GROUP BY Leave_type");

		boolean usesdLeaveStatus = false;
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			usesdLeaveStatus = true;
			hashMap = new HashMap<String, String>();
			// interating and cheack in condition
			while (rs.next()) {
				hashMap.put(rs.getString(1), rs.getString(2));
			}
			Iterator<String> iterator = hashMap.keySet().iterator();
			while (iterator.hasNext()) {

				String key = iterator.next();
				if (key.equals("FL")) {
					dashboard.setUsedFL(Integer.parseInt(hashMap.get(key).toString()));

				} else if (key.equals("PL")) {
					dashboard.setUsedPL(Integer.parseInt(hashMap.get(key).toString()));

				}
			}
		} catch (Exception e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return usesdLeaveStatus;
	}

	// month list
	public ArrayList<LeaveRecord> getmonthList() throws SQLException, PropertyVetoException {

		ArrayList<LeaveRecord> monthLists = new ArrayList<LeaveRecord>();
		LeaveRecord leaveRecord = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT month_id, month_name FROM month_list ");
		Connection connection = DBConnect.getConnection();
		try {

			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				leaveRecord = new LeaveRecord();
				leaveRecord.setMonthId(rs.getInt(1));
				leaveRecord.setMonthName(rs.getString(2));
				monthLists.add(leaveRecord);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return monthLists;

	}

	public String updateLeaveApproval(LeaveRecord leaveRecord) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		String status = leaveRecord.getStatus();
		String managerComment = leaveRecord.getManagerComment();
		int sequenceNo = leaveRecord.getSequenceNo();
		if (status.equals("Reject")) {
			status = "Rejected";
		} else {
			status = "Approved";
		}
		queryString.append("update emp_leave_records set Status=?");
		queryString.append(" , manager_comments=?");
		queryString.append(" ,updated_by=?");
		queryString.append(", updated_date=CURRENT_TIMESTAMP() where sequence_no=?");

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, status);
			statement.setString(2, managerComment);
			statement.setInt(3, leaveRecord.getManagerID());
			statement.setInt(4, sequenceNo);
			leaveRecord.setStatus(status);
			int rowUpdated = statement.executeUpdate();

			if (rowUpdated > 0)
				log.info("Status updated successfully");

		} catch (Exception e) {
			log.info(e.getMessage());

		} finally {
			statement.close();
			connection.close();
		}
		return status;
	}

	public boolean applyLeave(int staffID, Date fromDt, Date toDt, String leaveType, int noOfDays, String leaveReason)
			throws SQLException, PropertyVetoException, MessagingException {// NEW 31/12/2019

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean insertFlag = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"INSERT INTO emp_leave_records (staff_id, from_date, to_date, leave_type, reason, status, no_of_days, created_by, created_date)");
		queryString.append("VALUES (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP())");
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, staffID);
			stmt.setDate(2, fromDt);
			stmt.setDate(3, toDt);
			stmt.setString(4, leaveType);
			stmt.setString(5, leaveReason);
			stmt.setString(6, "Pending");
			stmt.setInt(7, noOfDays);
			stmt.setInt(8, staffID);

			int rowInserted = stmt.executeUpdate();

			if (rowInserted > 0) {
				insertFlag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}

		return insertFlag;

	}

	public LeaveRecord balLeave(int staffID, String leaveType) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = null;
		connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		LeaveRecord leaveRecord = new LeaveRecord();
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select emp_leave_master.p_leave, emp_leave_master.f_leave,");
		queryString
				.append("IFNULL(( select SUM(emp_leave_records.no_of_days) from emp_leave_records where leave_type=?");
		queryString.append(" and staff_id=?");
		queryString.append(" and status IN('Approved' , 'Pending') group by leave_type  ),0) ");
		queryString.append(" FROM emp_leave_master ");
		queryString.append(" where emp_leave_master.staff_id=?");
		queryString.append(" and emp_leave_master.deleted_by IS NULL limit 1");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, leaveType);
			statement.setInt(2, staffID);
			statement.setInt(3, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {

				leaveRecord.setTotalPLeave(rs.getInt(1));
				leaveRecord.setTotalFLeave(rs.getInt(2));
				leaveRecord.setLeaveUsed(rs.getInt(3));

				leaveRecord.setpLeaveBalance(leaveRecord.getTotalPLeave() - leaveRecord.getLeaveUsed());
				leaveRecord.setfLeaveBalance(leaveRecord.getTotalFLeave() - leaveRecord.getLeaveUsed());
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return leaveRecord;
	}

	// method for getting the leave record for dashboard
	public ArrayList<LeaveRecord> getLeaveRecordsDashboard(int staffID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		ArrayList<LeaveRecord> list = new ArrayList<LeaveRecord>();
		LeaveRecord leaverecord = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"select Staff_ID, DATE_FORMAT(From_date,'%d-%b-%Y'), DATE_FORMAT(to_date,'%d-%b-%Y'), No_of_Days, Leave_Type, Reason, Status,sequence_no,manager_comments from emp_leave_records where staff_id =?");
		queryString.append(" ORDER BY From_date desc");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				leaverecord = new LeaveRecord();
				leaverecord.setStaffID(rs.getInt(1));
				leaverecord.setFromdateInString(rs.getString(2));
				log.info(leaverecord.getFromdateInString());
				leaverecord.setTodateInString(rs.getString(3));
				log.info(leaverecord.getTodateInString());
				leaverecord.setNoOfDays(rs.getInt(4));
				leaverecord.setLeaveType(rs.getString(5));
				leaverecord.setReason(rs.getString(6));
				leaverecord.setStatus(rs.getString(7));
				leaverecord.setSequenceNo(rs.getInt(8));
				leaverecord.setManagerComment(rs.getString(9));
				list.add(leaverecord);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	// valid date for apply Leave
	public boolean isValidDate(int staffID, String fromDt, String toDt) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		boolean isValid = false;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		// DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date1 = null;
		try {
			date1 = df.parse(fromDt);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		java.sql.Date sqlFromDt = new java.sql.Date(date1.getTime());

		java.util.Date date2 = null;
		try {
			date2 = df.parse(toDt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlToDt = new java.sql.Date(date2.getTime());

		queryString.append("select count(*) from emp_leave_records where ((?");
		queryString.append(" BETWEEN from_date AND To_date ) OR (?");
		queryString.append(" BETWEEN from_date AND To_date ) OR ");
		queryString.append(" (from_date =?");
		queryString.append(" AND to_date =?");
		queryString.append(" )) AND staff_id =?");
		queryString.append(" AND (status='Approved' OR status='Pending')");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setDate(1, sqlFromDt);
			statement.setDate(2, sqlToDt);
			statement.setDate(3, sqlFromDt);
			statement.setDate(4, sqlToDt);
			statement.setInt(5, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0)
					isValid = true;
			}

		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		log.info("Is Applicable Flag" + isValid);

		return isValid;
	}

	@SuppressWarnings("static-access")
	public Boolean isDateNull(int staffID, String fromDt, String toDt) {

		boolean isDateNull = false;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String dateF = null;
		String dateT = null;

		java.util.Date date1 = null;
		try {
			date1 = df.parse(fromDt);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		java.util.Date date2 = null;
		try {
			date2 = df.parse(toDt);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date1 == null) {
			isDateNull = true;

		} else if (date2 == null) {
			isDateNull = true;

		} else
			isDateNull = false;

		return isDateNull;

	}

	public User getEmployeeDetails(int staffID) throws SQLException, PropertyVetoException {

		User user = getEmployeeDetails(staffID, "for email purpose");
		return user;

	}

	public Boolean isBirthday(int staffID, String fromDt, String toDt) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT em.date_of_birth ");
		queryString.append(" FROM employee_master as em INNER JOIN emp_leave_records as el");
		queryString.append(" where em.staff_ID = el.staff_ID");
		queryString.append(" and el.staff_ID=?");
		queryString.append(" and MONTH(em.date_of_birth) = MONTH(?");
		queryString.append(")");
		queryString.append(" and DAY(em.date_of_birth) = DAY(?");
		queryString.append(")");
		boolean isBirthday = false;

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setString(2, fromDt);
			statement.setString(3, toDt);
			rs = statement.executeQuery();

			while (rs.next()) {

				if (rs != null)
					isBirthday = true;
			}

		}

		finally {
			rs.close();
			statement.close();
			connection.close();

		}

		return isBirthday;

	}

	public Boolean isHoliday(int staffID, String fromDt, String toDt) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;

		boolean isHoliday = false;
		connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT hc.date ,el.from_date,el.to_date ");
		queryString.append(" FROM holiday_calendar as hc, emp_leave_records as el");
		queryString.append(" WHERE  hc.date =?");
		queryString.append(" and hc.date=?");
		queryString.append(" and hc.optional_flag='Y' ");
		queryString.append(" and el.staff_id=" + staffID);
		queryString.append(" and hc.deleted_by IS NULL ");
		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, fromDt);
			statement.setString(2, toDt);
			rs = statement.executeQuery();

			while (rs.next()) {

				if (rs != null)
					isHoliday = true;
			}

		}

		finally {
			rs.close();
			statement.close();
			connection.close();

		}

		return isHoliday;

	}

	public Boolean isLeaveAlloted(int staffID) throws SQLException, PropertyVetoException {

		LeaveAllotment leave = new LeaveAllotment();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		boolean isAlloted = false;

		queryString.append(" Select Staff_ID, F_Leave, P_Leave,total_leave ");
		queryString.append(" from emp_leave_master as em WHERE staff_id =?");
		queryString.append(" and deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {

				if (rs != null)
					isAlloted = true;

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return isAlloted;
	}

	public Boolean isLeaveRecords(int staffID) throws SQLException, PropertyVetoException {

		LeaveAllotment leave = new LeaveAllotment();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		boolean isAlloted = false;

		queryString.append(" Select Staff_ID, F_Leave, P_Leave,total_leave ");
		queryString.append(" from emp_leave_records as er WHERE staff_id =?");
		queryString.append(" AND F_Leave IS 0");
		queryString.append(" and deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				leave = new LeaveAllotment();
				leave.setStaffID(rs.getInt(1));
				leave.setFlexiLeave((rs.getInt(2)));
				leave.setPriviledgeLeave((rs.getInt(3)));
				if (rs.getInt(2) > 0) {
					isAlloted = true;

				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return isAlloted;
	}

	public Boolean isLeaveAvailable(int staffID) throws SQLException, PropertyVetoException {

		LeaveAllotment leave = new LeaveAllotment();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		boolean isAlloted = false;

		queryString.append(" Select Staff_ID, status ");
		queryString.append(" from emp_leave_records WHERE staff_id =?");
		queryString.append(" AND status='Rejected'");
		queryString.append(" and deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();

			while (rs.next()) {
				leave = new LeaveAllotment();
				leave.setStaffID(rs.getInt(1));
				leave.setFlexiLeave((rs.getInt(2)));
				leave.setPriviledgeLeave((rs.getInt(3)));
				isAlloted = true;

			}

		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return isAlloted;
	}

	public int isHolidayN(String fromDt, String toDt) throws SQLException, PropertyVetoException {

		int noOfdays = 0;
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;
		connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT count(*) as totalholidays");
		queryString.append(" FROM holiday_calendar as hc");
		queryString.append(" WHERE  hc.Date >=?");
		queryString.append(" AND hc.Date <= ?");
		queryString.append(" AND hc.optional_flag='N' ");
		queryString.append(" AND hc.weekday!='Sunday' ");
		queryString.append(" AND hc.deleted_by IS NULL ");
		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, fromDt);
			statement.setString(2, toDt);
			rs = statement.executeQuery();
			if (rs.next()) {
				if (rs != null)
					noOfdays = rs.getInt(1);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return noOfdays;
	}

	public int getConsumeLeave(int staffID) throws SQLException, PropertyVetoException {// NEW 31/2019

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		int consumeLeave = 0;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT sum(no_of_days)");
		queryString.append(" FROM emp_leave_records");
		queryString.append(" WHERE leave_type = 'PL'");
		queryString.append(" AND Status='Approved'");
		queryString.append(" AND staff_id = ?");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			// log.info(statement.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				consumeLeave = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return consumeLeave;
	}

	
	/////
	public String getManagerEmail(int staffID) throws SQLException, PropertyVetoException {// NEW 31/2019

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		String managerEmailID = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT Email_ID FROM employee_master ");
		queryString.append(" WHERE Staff_ID = (");
		queryString.append(" SELECT Manager_ID");
		queryString.append(" FROM employee_master");
		queryString.append(" WHERE Staff_ID = ?)");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			// log.info(statement.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				managerEmailID = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return managerEmailID;
	}
	public int getConsumeLeaveLWP(int staffID) throws SQLException, PropertyVetoException {// NEW 31/2019

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		int consumeLeave = 0;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT sum(no_of_days)");
		queryString.append(" FROM emp_leave_records");
		queryString.append(" WHERE leave_type = 'LWP'");
		queryString.append(" AND Status = 'Approved'");
		queryString.append(" AND staff_id = ?");
		queryString.append(" AND deleted_by IS NULL");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			// log.info(statement.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				consumeLeave = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			connection.close();
			statement.close();

		}
		return consumeLeave;
	}
}
