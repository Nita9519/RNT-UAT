/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	HolidayDao.java
 **	Description		:	The java Class HolidayDao is a DAO class that performs CRUD operations for the 
 *						Holiday calendar module.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/
package ai.rnt.lms.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.main.dao.DBConnect;

public class HolidayDao {

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(HolidayDao.class);

	// method for holidays details for page
	public ArrayList<HolidayCalendar> getHolidayDetails() throws SQLException, PropertyVetoException {
		HolidayCalendar hDetails = null;

		ArrayList<HolidayCalendar> holidayList = new ArrayList<HolidayCalendar>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" Select  Holiday_ID, DATE_FORMAT(Date,'%d-%b-%Y'), Ocassion, Optional_flag, weekday ");
		queryString.append(" from holiday_calendar ");
		queryString.append(" WHERE Ocassion != 'Alternate Saturday'");
		queryString.append(" AND deleted_by IS NULL ");
		queryString.append(" ORDER BY MONTH(Date) asc,DAY(Date) asc ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {

				hDetails = new HolidayCalendar();
				hDetails.setHolidayID(rs.getInt(1));
				
				  hDetails.setDateInString(rs.getString(2));
				 
				 
				// DATE_FORMAT(Date,'%d-%m-%Y')
				//hDetails.setDate(rs.getDate(2));
				hDetails.setOccasion(rs.getString(3));
				hDetails.setOptionalFlag(rs.getString(4));
				hDetails.setWeekDay(rs.getString(5));
				holidayList.add(hDetails);

			}

		} catch (NumberFormatException e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return holidayList;
	}

	// method for Holiday Calendar
	public List<HolidayCalendar> getHolidayCalender() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		ArrayList<HolidayCalendar> list = new ArrayList<HolidayCalendar>();
		HolidayCalendar holidaycalender = null;
		StringBuffer queryString = new StringBuffer();
		queryString
				.append("SELECT Date, ocassion, Optional_flag,weekday from holiday_calendar WHERE deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				holidaycalender = new HolidayCalendar();
				holidaycalender.setDate(rs.getDate(1));
				holidaycalender.setOccasion(rs.getString(2));
				holidaycalender.setOptionalFlag(rs.getString(3));
				holidaycalender.setWeekDay(rs.getString(4));
				list.add(holidaycalender);

			}
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public Boolean checkForDuplicateDate(Date sqlDate, String occasion) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		Boolean duplicateStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT count(*) FROM holiday_calendar WHERE Date=?");
		queryString.append(" AND Ocassion=?");
		queryString.append(" AND deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setDate(1, sqlDate);
			statement.setString(2, occasion);
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

	public Boolean insertHoliday(HolidayCalendar holidayCalendar, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertHolidayStatus = false;

		StringBuffer queryString = new StringBuffer();
		queryString
				.append("insert into holiday_calendar (Date,ocassion,Optional_flag,weekday,created_by,created_date)");
		queryString.append("values (?,?,?,?,");
		queryString.append(adminID);
		queryString.append(",CURRENT_TIMESTAMP())");

		Connection connection = DBConnect.getConnection();
		// statement = connection.createStatement();
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			statement = connection.prepareStatement(queryString.toString());

			statement.setDate(1, holidayCalendar.getDate());
			statement.setString(2, holidayCalendar.getOccasion());
			statement.setString(3, holidayCalendar.getOptionalFlag());
			statement.setString(4, holidayCalendar.getWeekDay());
			holidayCalendar.setCreatedBy(adminID);
			holidayCalendar.setCreatedDate(timestamp);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {

				insertHolidayStatus = true;
			}

		} catch (Exception e) {

		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertHolidayStatus;
	}

	public Boolean updateHoliday(HolidayCalendar holidayCalendar, int adminID)
			throws SQLException, PropertyVetoException {
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean updateHolidayStatus = false;
		int holidayID = holidayCalendar.getHolidayID();
		String occassion = holidayCalendar.getOccasion();
		queryString.append("update holiday_calendar set Date=?, Ocassion=?, Optional_flag=?, weekday=?,");
		queryString.append("updated_by=");
		queryString.append(adminID);
		queryString.append(",updated_date=CURRENT_TIMESTAMP()");
		queryString.append("  where deleted_by IS NULL AND Holiday_ID = ");
		queryString.append(holidayID);
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			statement = connection.prepareStatement(queryString.toString());
			statement.setDate(1, holidayCalendar.getDate());
			statement.setString(2, holidayCalendar.getOccasion());
			statement.setString(3, holidayCalendar.getOptionalFlag());
			statement.setString(4, holidayCalendar.getWeekDay());
			holidayCalendar.setUpdatedBy(adminID);
			holidayCalendar.setUpdatedDate(timestamp);
			// statement.setDate(5, holidayCalendar.getDate());

			int rowUpdated = statement.executeUpdate();
			if (rowUpdated > 0)
				log.info("Holiday updated successfully");
			updateHolidayStatus = true;

		} catch (Exception e) {
			log.info("holiday is not updatded ");
		} finally {
			statement.close();
			connection.close();
		}
		return updateHolidayStatus;
	}

	public Boolean deleteHoliday(HolidayCalendar holidayCalendar, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean deleteHolidayStatus = false;
		int holidayID = holidayCalendar.getHolidayID();

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			queryString.append("UPDATE holiday_calendar SET deleted_by=");
			queryString.append(adminID);
			queryString.append(",deleted_date=CURRENT_TIMESTAMP()");
			queryString.append("where deleted_by IS NULL AND Holiday_ID =  ");
			queryString.append(holidayID);
			statement = connection.prepareStatement(queryString.toString());
			// statement.setInt(1, holidayCalendar.getHolidayID());
			holidayCalendar.setDeletedBy(adminID);
			holidayCalendar.setDeletedDate(timestamp);

			int rowDeleted = statement.executeUpdate();
			if (rowDeleted > 0)
				log.info("Holiday deleted successfully");
			deleteHolidayStatus = true;

		} finally {
			statement.close();
			connection.close();
		}
		return deleteHolidayStatus;
	}

}
