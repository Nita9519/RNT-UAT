package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.HardwareMaster;
import ai.rnt.pins.model.SoftwareMaster;

public class HardwareMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(HardwareMasterDao.class);

	public ArrayList<HardwareMaster> getListOfHardwares() throws SQLException, PropertyVetoException {
		HardwareMaster hardware = new HardwareMaster();

		ArrayList<HardwareMaster> hardwareList = new ArrayList<HardwareMaster>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select hardware_id, hardware_model, hardware_type, Configuration ");
		queryString.append("from hardware_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery(queryString.toString());

			while (rs.next()) {

				hardware = new HardwareMaster();
				hardware.setHardwareId(rs.getInt(1));
				hardware.setModel(rs.getString(2));
				hardware.setHardwareType(rs.getString(3));
				hardware.setConfiguration(rs.getString(4));
				hardwareList.add(hardware);
			}
			log.info("hardwarelist==" + queryString.toString());

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return hardwareList;
	}

	public ArrayList<HardwareMaster> getHardware(int hardwareId) throws SQLException, PropertyVetoException {
		HardwareMaster hardware = new HardwareMaster();
		ArrayList<HardwareMaster> hardwareList = new ArrayList<HardwareMaster>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select hardware_id, hardware_type, hardware_model, Configuration ");
		queryString.append("from hardware_master ");
		queryString.append("WHERE hardware_id=?");

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, hardwareId);
			rs = statement.executeQuery();

			while (rs.next()) {

				hardware = new HardwareMaster();
				hardware.setHardwareId(rs.getInt(1));
				hardware.setHardwareType(rs.getString(2));
				hardware.setModel(rs.getString(3));
				hardware.setConfiguration(rs.getString(4));
				hardwareList.add(hardware);

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return hardwareList;
	}

	public boolean addHardware(HardwareMaster hardware, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"INSERT into hardware_master (hardware_type, hardware_model, Configuration, created_by, created_date) ");
		queryString.append("VALUES (?,?,?,");
		queryString.append(adminId + ",");
		queryString.append("CURRENT_TIMESTAMP())");
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, hardware.getHardwareType());
			stmt.setString(2, hardware.getModel());
			stmt.setString(3, hardware.getConfiguration());
			hardware.setCreatedBy(adminId);
			hardware.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				log.info("A new hardware was inserted successfully!");
				addHardware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while Adding  Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return addHardware;
	}

	public boolean updateHardware(HardwareMaster hardware, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE hardware_master SET hardware_type=?");
		queryString.append(", ");
		queryString.append("hardware_model=?");
		queryString.append(", ");
		queryString.append("Configuration=?");
		queryString.append(", ");
		queryString.append("updated_by=?");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE hardware_id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, hardware.getHardwareType());
			stmt.setString(2, hardware.getModel());
			stmt.setString(3, hardware.getConfiguration());
			stmt.setInt(4, adminId);
			stmt.setInt(5, hardware.getHardwareId());
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("hardware updated successfully");
				updateHardware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while updating  Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return updateHardware;
	}

	public boolean updateAddedHardware(HardwareMaster hardware, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE hardware_master SET ");
		queryString.append("updated_by=");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE hardware_type=?");
		queryString.append(" AND hardware_model=?");
		queryString.append(" AND Configuration=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setString(2, hardware.getHardwareType());
			stmt.setString(3, hardware.getModel());
			stmt.setString(4, hardware.getConfiguration());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("hardware updated successfully");
				updateHardware = true;
			}

		} catch (Exception e) {
			log.error("Got Exception while updating  Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return updateHardware;
	}

	public Boolean deleteHardware(int hardwareId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE hardware_master SET ");
		queryString.append("deleted_by=?");
		queryString.append(", ");
		queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND hardware_id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setInt(2, hardwareId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("hardware deleted successfully");
				deleteHardware = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return deleteHardware;
	}

	public boolean checkDuplicateRecForHardwareDetails(HardwareMaster hardware)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement=null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		boolean status = false;
		queryString.append("SELECT COUNT(*)");
		queryString.append(" FROM hardware_master");
		queryString.append(" WHERE hardware_type=?");
		queryString.append(" AND hardware_model=?");
		queryString.append(" AND Configuration=?");
		queryString.append(" AND deleted_by IS NULL");
		try {
			statement = connection.prepareStatement(queryString.toString());
			
			statement.setString(1, hardware.getHardwareType());
			statement.setString(2, hardware.getModel());
			statement.setString(3, hardware.getConfiguration());
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					log.info("count seccessfully");
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for hardware details:", e);
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return status;

	}
}