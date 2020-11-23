package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.LocationMaster;

public class LocationMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(LocationMasterDao.class);

	public ArrayList<LocationMaster> getListOfLocations() throws SQLException, PropertyVetoException {
		LocationMaster location = new LocationMaster();

		ArrayList<LocationMaster> locationList = new ArrayList<LocationMaster>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT  location_id, location ");
		queryString.append("FROM location_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				location = new LocationMaster();
				location.setLocationId(rs.getInt(1));
				location.setLocationName(rs.getString(2));
				locationList.add(location);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching List OF Location :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return locationList;
	}

	public Boolean addLocation(String locationName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addRole = false;
		try {
			LocationMaster location = getLocation(locationName);
			 if (location == null) { 
			StringBuffer queryString = new StringBuffer();
			queryString.append("INSERT into location_master(location, created_by, created_date) ");
			queryString.append("VALUES (?,?,CURRENT_TIMESTAMP())");

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, locationName);
			stmt.setInt(2, adminId);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("location added successfully");
				addRole = true;
			}
			
			 } else { updateLocation(location.getLocationId(), location.getLocationName(),
			 adminId); }
			 
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addRole;
	}

	public Boolean deleteLocation(int locationId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteRole = false;
		StringBuffer queryString = new StringBuffer();
		//queryString.append("delete from location_master ");
		//queryString.append(" WHERE location_id=?");
		queryString.append("UPDATE location_master SET deleted_by=?");
		queryString.append(", ");
		queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by is NULL and location_id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			 stmt.setInt(1, adminId);
			stmt.setInt(2, locationId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("location deleted successfully");
				deleteRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return deleteRole;
	}

	public Boolean updateLocation(int locationId, String locationName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRole = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE location_master SET location=?");
		queryString.append(", ");
		queryString.append("updated_by=?");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE location_id=?");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, locationName);
			stmt.setInt(2, adminId);
			stmt.setInt(3, locationId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("location updated successfully");
				updateRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updateRole;
	}

	public Boolean updateAddedLocation(String locationName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRole = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE location_master SET ");
		queryString.append("updated_by=?");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE location=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setString(2, locationName);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("location updated successfully");
				updateRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateRole;
	}

	public LocationMaster getLocation(String locationName) throws SQLException, PropertyVetoException {
		LocationMaster location = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT location_id, location ");
		queryString.append("FROM location_master ");
		queryString.append("WHERE location=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, locationName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				location = new LocationMaster();
				location.setLocationId(rs.getInt(1));
				location.setLocationName(rs.getString(2));
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching Location :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return location;
	}

	public boolean checkDuplicateRecForLocation(String location) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		boolean status = false;
		queryString.append("SELECT COUNT(*)");
		queryString.append(" FROM location_master");
		queryString.append(" WHERE location=?");
		 queryString.append(" AND deleted_by IS NULL");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, location);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for location:", e);
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return status;

	}

}