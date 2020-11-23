package ai.rnt.lms.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import ai.rnt.lms.model.Dashboard;
import ai.rnt.lms.model.LeaveAllotment;
import ai.rnt.lms.model.LeaveRecord;
import ai.rnt.lms.model.User;
import ai.rnt.main.dao.DBConnect;

public class LeaveAllotmentDao {

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(LeaveAllotmentDao.class);

	// method for valid getleave details pl,fl,pl
	public Dashboard getLeaveDetails(int staffID, Dashboard dashboard) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT P_Leave, F_Leave from  emp_leave_master where deleted_by IS NULL AND staff_id =? ");
		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			// interating and cheack in condition
			while (rs.next()) {
				dashboard.setTotalPL(rs.getInt(1));
				dashboard.setTotalFL(rs.getInt(2));
			}
			dashboard.setTotalLeaves(dashboard.getTotalPL() + dashboard.getTotalFL());
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return dashboard;
	}

	// method for leave allotment
	public ArrayList<LeaveAllotment> leaveAllotment() throws SQLException, PropertyVetoException {

		LeaveAllotment leave = new LeaveAllotment();
		ArrayList<LeaveAllotment> leaveList = new ArrayList<LeaveAllotment>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT em.Staff_ID, e.User_ID,em.F_Leave, em.P_Leave,em.total_leave,e.F_Name,e.L_Name ");
		queryString.append(
				" FROM emp_leave_master as em ,employee_master as e WHERE em.Staff_ID=e.Staff_ID AND em.deleted_by IS NULL ORDER BY Staff_ID asc");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				leave = new LeaveAllotment();
				leave.setStaffID(rs.getInt(1));
				leave.setUserID(rs.getString(2));
				leave.setFlexiLeave(rs.getInt(3));
				leave.setPriviledgeLeave(rs.getInt(4));
				leave.setTotalLeave(rs.getInt(5));
				leave.setfName(rs.getString(6));
				leave.setlName(rs.getString(7));
				leaveList.add(leave);

			}

		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return leaveList;
	}

	public LeaveAllotment addLeaveDetails(int staffID, int flexiLeave, int priviledgeLeave)
			throws SQLException, PropertyVetoException {
		LeaveAllotment leaveAllotment = new LeaveAllotment();

		Connection connection = DBConnect.getConnection();
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("INSERT INTO emp_leave_master(staff_id,p_leave,f_leave,created_by,created_date) values (");
		queryString.append(staffID);
		queryString.append(",");
		queryString.append(flexiLeave);
		queryString.append(",");
		queryString.append(priviledgeLeave);
		queryString.append(",");
		queryString.append(staffID);
		queryString.append(",");
		queryString.append("CURRENT_TIMESTAMP())");
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			statement = connection.prepareStatement(queryString.toString());
			statement.executeUpdate();

			leaveAllotment.setStaffID(staffID);
			leaveAllotment.setFlexiLeave(flexiLeave);
			leaveAllotment.setPriviledgeLeave(priviledgeLeave);
			leaveAllotment.setCreatedBy(staffID);
			leaveAllotment.setCreatedDate(timestamp);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return leaveAllotment;
	}

	public ArrayList<User> getStaffID() throws SQLException, PropertyVetoException {

		User user = null;
		ArrayList<User> userList = new ArrayList<User>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select staff_ID from employee_master");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setStaffID(rs.getInt(1));
				userList.add(user);

			}

		} finally {

			rs.close();
			statement.close();
			connection.close();

		}
		return userList;
	}

	public Boolean insertLeaveDetails(LeaveAllotment leaveAllotment, int adminID)
			throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean insertStatus = false;

		queryString.append(
				" insert into emp_leave_master (staff_id,p_leave,f_leave,total_leave,created_by,created_date) ");
		queryString.append("values (?,?,?,?,");
		queryString.append(adminID);
		queryString.append(",CURRENT_TIMESTAMP()) ");

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, leaveAllotment.getStaffID());

			statement.setInt(2, leaveAllotment.getPriviledgeLeave());

			statement.setInt(3, leaveAllotment.getFlexiLeave());

			statement.setInt(4, leaveAllotment.getTotalLeave());
			leaveAllotment.setCreatedBy(adminID);
			leaveAllotment.setCreatedDate(timestamp);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				insertStatus = true;
				log.info("A new leave was inserted successfully!");
			}

		} finally {
			if (statement != null)
				statement.close();

			connection.close();
		}
		return insertStatus;

	}

	public Boolean deleteLeaveDetails(LeaveAllotment leaveAllotment, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean deleteLeaveStatus = false;
		queryString.append("UPDATE emp_leave_master ");
		queryString.append("SET deleted_by=");
		queryString.append(adminID);
		queryString.append(",deleted_date=CURRENT_TIMESTAMP()");
		queryString.append(" WHERE deleted_by IS NULL");
		queryString.append(" AND staff_id=? ");
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			statement = connection.prepareStatement(queryString.toString());

			statement.setInt(1, leaveAllotment.getStaffID());
			leaveAllotment.setDeletedBy(adminID);
			leaveAllotment.setDeletedDate(timestamp);

			int rowDeleted = statement.executeUpdate();
			if (rowDeleted > 0)
				log.info("leave deleted successfully");
			deleteLeaveStatus = true;

		} finally {
			statement.close();
			connection.close();
		}
		return deleteLeaveStatus;
	}

	public Boolean updateLeaveDetails(LeaveAllotment leaveAllotment, int adminID)
			throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean updateStatus = false;
		queryString.append("UPDATE emp_leave_master ");
		queryString.append("SET P_Leave=?, F_Leave=?,total_Leave=?,updated_by=");
		queryString.append(adminID);
		queryString.append(",updated_date=CURRENT_TIMESTAMP()");
		queryString.append("  WHERE deleted_by IS NULL");
		queryString.append(" AND staff_id="+leaveAllotment.getStaffID());
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, leaveAllotment.getPriviledgeLeave());
			statement.setInt(2, leaveAllotment.getFlexiLeave());
			statement.setInt(3, leaveAllotment.getTotalLeave());
			leaveAllotment.setUpdatedBy(adminID);
			leaveAllotment.setUpdatedDate(timestamp);

			int rowUpdated = statement.executeUpdate();
			if (rowUpdated > 0) {
				log.info("leave updated successfully");
				updateStatus = true;
			}
		} catch (Exception e) {
			log.info("duplicate value is not allow");
		} finally {
			statement.close();
			connection.close();
		}
		return updateStatus;
	}

	public ArrayList<User> getUsersForLeaveRecords() throws SQLException, PropertyVetoException {

		User user = null;
		ArrayList<User> userList = new ArrayList<User>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				" SELECT staff_id FROM employee_master WHERE staff_id NOT IN (SELECT staff_id FROM emp_leave_master WHERE deleted_by IS NULL ) ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				user = new User();
				user.setStaffID(rs.getInt(1));
				userList.add(user);

			}

		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return userList;
	}

	// method for getting the leave record for approve leave page manager
	public List<LeaveRecord> getLeaveRecordsforApproval(int staffID) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<LeaveRecord> list = new ArrayList<LeaveRecord>();
		LeaveRecord leaverecord = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT e.staff_id, l.From_date, l.to_date,l.no_of_days,l.leave_type, l.reason, l.Status,l.sequence_no ");
		queryString.append("FROM employee_master AS e ");
		queryString.append("INNER JOIN emp_leave_records AS l ");
		queryString.append("WHERE e.staff_id = l.staff_id ");
		queryString.append("AND l.status =  'pending'");
		queryString.append("AND e.Manager_ID = ");
		queryString.append(staffID);
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				leaverecord = new LeaveRecord();
				leaverecord.setStaffID(rs.getInt(1));
				leaverecord.setFromDate(rs.getDate(2));
				leaverecord.setToDate(rs.getDate(3));
				leaverecord.setNoOfDays(rs.getInt(4));
				leaverecord.setLeaveType(rs.getString(5));
				leaverecord.setReason(rs.getString(5));
				leaverecord.setStatus(rs.getString(6));
				leaverecord.setSequenceNo(rs.getInt(8));

				list.add(leaverecord);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public Boolean checkEmployeeAllotmentDup(LeaveAllotment leaveAllotment) throws SQLException, PropertyVetoException {

		boolean allotmentFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM emp_leave_master");
		queryString.append(" WHERE staff_id =?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, leaveAllotment.getStaffID());

			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					allotmentFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}

		return allotmentFlag;
	}

}
