package ai.rnt.main.dao;

import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.lms.model.User;
import ai.rnt.rms.model.Profile;

public class MainLogInDao {

	private static final Logger log = LogManager.getLogger(MainLogInDao.class);

	// method for valid employee
	/*
	 * public User getEmployeeDetails(int staffID) throws SQLException,
	 * PropertyVetoException { // Employee check User user = null; ResultSet rs =
	 * null; Connection connection = DBConnect.getConnection(); Statement stmt =
	 * connection.createStatement(); try { StringBuffer queryString = new
	 * StringBuffer(); queryString.append(
	 * "SELECT Staff_ID, password, F_name, M_Name, L_Name, Manager_ID, email_ID FROM employee_master WHERE Staff_ID= "
	 * ); queryString.append(staffID );
	 * 
	 * rs = stmt.executeQuery(queryString.toString());
	 * 
	 * // interacting and check in condition while (rs.next()) { user = new User();
	 * user.setStaffID(rs.getInt(1)); user.setPassword(rs.getString(2));
	 * user.setFirstName(rs.getString(3)); user.setMiddleName(rs.getString(4));
	 * user.setLastName(rs.getString(5)); user.setManagerID(rs.getInt(6));
	 * user.setEmailID(rs.getString(7));
	 * 
	 * } } finally { rs.close(); connection.close(); stmt.close(); } return user;
	 * 
	 * }
	 */

	public User getEmployeeDetails(String userID) throws SQLException, PropertyVetoException {
		// Employee check
		User user = null;
		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(
					"SELECT Staff_ID, password, F_name, M_Name, L_Name, Manager_ID, email_ID FROM employee_master WHERE User_ID='");
			queryString.append(userID + "'");

			rs = stmt.executeQuery(queryString.toString());

			// interacting and check in condition
			if (rs.next()) {
				user = new User();
				user.setStaffID(rs.getInt(1));
				user.setPassword(rs.getString(2));
				user.setFirstName(rs.getString(3));
				user.setMiddleName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setManagerID(rs.getInt(6));
				user.setEmailID(rs.getString(7));
				user.setUserID(userID);

			}
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return user;

	}

	// method for admin check
	public boolean isValidAdmin(String staffID) throws SQLException, PropertyVetoException {

		boolean isAdmin = false;
		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		Statement statement = connection.createStatement();
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("select count(*) from admin_info where User_ID='");
			queryString.append(staffID + "'");
			rs = statement.executeQuery(queryString.toString());
			while (rs.next()) {
				if (rs.getInt(1) > 0)
					isAdmin = true;
			}
		} finally {
			
			rs.close();
			statement.close();
			connection.close();
		}

		return isAdmin;
	}

	// method for admin check

	public boolean isValidAdmin1(int staffID) throws SQLException, PropertyVetoException {

		boolean isAdmin = false;
		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		Statement statement = connection.createStatement();
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("select count(*) from admin_info where Staff_ID= ");
			queryString.append(staffID);
			rs = statement.executeQuery(queryString.toString());
			while (rs.next()) {
				if (rs.getInt(1) > 0)
					isAdmin = true;

			}
		} finally {
			
			rs.close();
			statement.close();
			connection.close();
		}

		return isAdmin;
	}

	// method for valid Manager

	/*
	 * public boolean isValidManager(int staffID) throws SQLException,
	 * PropertyVetoException { // Manager check boolean isManager = false; ResultSet
	 * rs = null; StringBuffer queryString = new StringBuffer(); queryString.
	 * append("select count(*) from employee_master where manager_ID = ");
	 * queryString.append(staffID); Connection connection
	 * =DBConnect.getConnection(); Statement statement
	 * =connection.createStatement(); try { rs =
	 * statement.executeQuery(queryString.toString()); while (rs.next()) { if
	 * (rs.getInt(1) > 0) { isManager = true; } } } finally { rs.close();
	 * connection.close(); statement.close(); }
	 * 
	 * return isManager; }
	 */

//	 valid manager by user_id

	public boolean isValidManager(User user) throws SQLException, PropertyVetoException {
		// Manager check
		boolean isManager = false;

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(*) from employee_master where manager_ID =");
		queryString.append(user.getStaffID());
		Connection connection = DBConnect.getConnection();
		Statement statement = connection.createStatement();
		try {
			rs = statement.executeQuery(queryString.toString());
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					isManager = true;
					log.info("he is manager");
				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}

		return isManager;
	}

	public String getProfilePicture(int staffID, String path) throws SQLException, PropertyVetoException, IOException { // Employee
																														// //
																														// //
																														// //
																														// check

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		String imgPath = "";
		try {
			StringBuffer queryString = new StringBuffer();

			queryString.append(
					"SELECT ep.Profile_Picture FROM employee_master AS em INNER JOIN Employee_Profile AS ep ");
			queryString.append(" WHERE em.Staff_ID=ep.Profile_ID AND em.Staff_ID='");
			queryString.append(staffID);

			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				Blob blob = rs.getBlob(1);
				if (blob != null) {
					byte barr[] = blob.getBytes(1, (int) blob.length());// 1 means first image
					String URL = path + staffID + ".jpg";
					File file = new File(URL);
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(barr);
					fos.close();
					imgPath = "/rms/images/" + staffID + ".jpg";
				} else {
					imgPath = "/rms/images/avatar-1.jpg";
				}
			}
		} finally {
			try {
				rs.close();
			} catch (NullPointerException e) {

			}
			stmt.close();
			connection.close();
			
		}
		return imgPath;
	}

	/*
	 * public String getProfilePicture(String userID, String path) throws
	 * SQLException, PropertyVetoException, IOException { // Employee // // // check
	 * 
	 * ResultSet rs = null; Connection connection = DBConnect.getConnection();
	 * Statement stmt = connection.createStatement(); String imgPath =
	 * "/rms/images/profile.png "; try { StringBuffer queryString = new
	 * StringBuffer();
	 * 
	 * queryString.append(
	 * "SELECT ep.Profile_Picture FROM employee_master AS em INNER JOIN Employee_Profile AS ep "
	 * ); queryString.append(" WHERE em.Staff_ID=ep.Profile_ID AND em.User_ID='");
	 * queryString.append(userID+"'");
	 * 
	 * rs = stmt.executeQuery(queryString.toString()); while (rs.next()) { Blob
	 * blob= rs.getBlob(1); if (blob == null) { imgPath =
	 * "/rms/images/avatar-1.jpg"; } else { imgPath = "/rms/images/profile.png"; } }
	 * finally { try { } catch (NullPointerException e) {
	 * 
	 * } connection.close(); stmt.close();
	 * 
	 * log.info("imagepath................."+imgPath); return imgPath;
	 * 
	 * }
	 */

}
