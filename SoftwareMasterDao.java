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
import ai.rnt.pins.model.SoftwareMaster;

public class SoftwareMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	
	private static final Logger log = LogManager.getLogger(SoftwareMasterDao.class);

	public ArrayList<SoftwareMaster> getListOfSoftwares() throws SQLException, PropertyVetoException {
		SoftwareMaster software = new SoftwareMaster();

		ArrayList<SoftwareMaster> softwareList = new ArrayList<SoftwareMaster>();
		
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select software_id, product_name, software_type, software_vendor, product_version ");
		queryString.append("from software_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {

				software = new SoftwareMaster();
				software.setSoftwareId(rs.getInt(1));
				software.setProductName(rs.getString(2));
				software.setSoftwareType(rs.getString(3));
				software.setVendor(rs.getString(4));
				software.setProductVersion(rs.getString(5));
				softwareList.add(software);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while get list of software Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();			
		}

		return softwareList;
	}

	public ArrayList<SoftwareMaster> getSoftware(int softwareId) throws SQLException, PropertyVetoException {
		SoftwareMaster software = new SoftwareMaster();
		ArrayList<SoftwareMaster> softwareList = new ArrayList<SoftwareMaster>();

		

		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select software_id, software_type, software_vendor, product_name, product_version ");
		queryString.append("from software_master ");
		queryString.append("WHERE software_id=?");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, softwareId);
			rs = statement.executeQuery();
			while (rs.next()) {

				software = new SoftwareMaster();
				software.setSoftwareId(rs.getInt(1));
				software.setSoftwareType(rs.getString(2));
				software.setVendor(rs.getString(3));
				software.setProductName(rs.getString(4));
				software.setProductVersion(rs.getString(5));
				softwareList.add(software);
			}

		} catch (Exception e) {
			log.error("Got Exception while get software Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return softwareList;
	}

	public boolean addSoftware(SoftwareMaster software, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSoftware = false;
		

			StringBuffer queryString = new StringBuffer();
			queryString.append(
					"INSERT into software_master (software_type, software_vendor, product_name, product_version, created_by, created_date) ");
			queryString.append("VALUES (?,?,?,?,");
			queryString.append(adminId + ",");
			queryString.append("CURRENT_TIMESTAMP())");
			
			try {
				
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, software.getSoftwareType());
			stmt.setString(2, software.getVendor());
			stmt.setString(3, software.getProductName());
			stmt.setString(4, software.getProductVersion());
			software.setCreatedBy(adminId);
			software.setCreatedDate(timestamp);

			

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				log.info("A new software was inserted successfully!");
				addSoftware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while inserting software Details :: ", e);
			// e.printStackTrace();
		} finally {
			
			stmt.close();
			connection.close();
		}

		return addSoftware;
	}

	public Boolean deleteSoftware(int softwareId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSoftware = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE software_master SET ");
			queryString.append("deleted_by=");
			queryString.append(adminId + ", ");
			queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND software_id=");
			queryString.append(softwareId);

		
			stmt = connection.prepareStatement(queryString.toString());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("software deleted successfully");
				deleteSoftware = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteSoftware;
	}

	public boolean updateSoftware(SoftwareMaster software, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSoftware = false;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE software_master SET software_type='");
			queryString.append(software.getSoftwareType() + "'" + ", ");
			queryString.append("software_vendor='");
			queryString.append(software.getVendor() + "'" + ", ");
			queryString.append("product_name='");
			queryString.append(software.getProductName() + "'" + ", ");
			queryString.append("product_version='");
			queryString.append(software.getProductVersion() + "'" + ", ");
			queryString.append("updated_by=");
			queryString.append(adminId + ", ");
			queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE software_id=");
			queryString.append(software.getSoftwareId());

			
			stmt = connection.prepareStatement(queryString.toString());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("software updated successfully");
				updateSoftware = true;
			}

		} catch (Exception e) {
			log.error("Got Exception while updating Software Details :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
			
		}
		return updateSoftware;
	}
	
	public boolean updateAddedSoftware(SoftwareMaster software, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSoftware = false;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE software_master SET ");
			queryString.append("updated_by=");
			queryString.append(adminId + ", ");
			queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE software_type='");
			queryString.append(software.getSoftwareType() + "'");
			queryString.append(" AND software_vendor='");
			queryString.append(software.getVendor() + "'");
			queryString.append(" AND product_name='");
			queryString.append(software.getProductName() + "'");
			queryString.append("AND product_version='");
			queryString.append(software.getProductVersion() + "'");

			
			stmt = connection.prepareStatement(queryString.toString());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("software updated successfully");
				updateSoftware = true;
			}

		} catch (Exception e) {
			log.error("Got Exception while updating Software Details :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
			
		}
		return updateSoftware;
	}
	
	public boolean checkDuplicateRecForSoftwareDetails(SoftwareMaster software)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		try {
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM software_master");
			queryString.append(" WHERE software_type=?");
			queryString.append(" AND software_vendor=?");
			queryString.append(" AND product_name=?");
			queryString.append(" AND product_version=?");
			queryString.append(" AND deleted_by IS NULL");
			
			
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, software.getSoftwareType());
			stmt.setString(2, software.getVendor());
			stmt.setString(3, software.getProductName());
			stmt.setString(4, software.getProductVersion());
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for software details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return status;

	}
}