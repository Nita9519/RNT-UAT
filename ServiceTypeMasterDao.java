package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.ServiceTypeMaster;

public class ServiceTypeMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	
	private static final Logger log = LogManager.getLogger(ServiceTypeMasterDao.class);

	public ArrayList<ServiceTypeMaster> getListOfServiceTypes() throws SQLException, PropertyVetoException {
		ServiceTypeMaster serviceType = new ServiceTypeMaster();

		ArrayList<ServiceTypeMaster> serviceList = new ArrayList<ServiceTypeMaster>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT service_id, service_type ");
		queryString.append("FROM service_type_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {

				serviceType = new ServiceTypeMaster();
				serviceType.setServiceTypeId(rs.getInt(1));
				serviceType.setServiceTypeName(rs.getString(2));
				serviceList.add(serviceType);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while List Of Service Type Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return serviceList;
	}

	public Boolean addServiceType(String serviceTypeName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addServiceType = false;
		try {
			ServiceTypeMaster serviceType = getServiceType(serviceTypeName);
			if (serviceType == null) {
				StringBuffer queryString = new StringBuffer();
				queryString.append("INSERT into service_type_master(service_type, created_by, created_date) ");
				queryString.append("VALUES (?,?,CURRENT_TIMESTAMP())");

				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, serviceTypeName);
				stmt.setInt(2, adminId);
				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("Service Type added successfully");
					addServiceType = true;
				}
			} else {
				updateServiceType(serviceType.getServiceTypeId(), serviceType.getServiceTypeName(), adminId);
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addServiceType;
	}

	public Boolean deleteServiceType(int serviceId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteServiceType = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE service_type_master SET deleted_by=?");
			queryString.append(" ,deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append(" WHERE service_id="+serviceId);
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Service Type deleted successfully");
				deleteServiceType = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteServiceType;
	}

	public Boolean updateServiceType(int serviceId, String serviceTypeName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateServiceType = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE service_type_master SET service_type=?");
			queryString.append(",updated_by=?");
			queryString.append(",updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE service_id="+serviceId);
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1,serviceTypeName);
			stmt.setInt(2,adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Service Type updated successfully");
				updateServiceType = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updateServiceType;
	}

	
	public Boolean updateAddedServiceType(String serviceTypeName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateServiceType = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE service_type_master SET ");
			queryString.append("updated_by=?");
			queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE service_type=?");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setString(2, serviceTypeName);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Service Type updated successfully");
				updateServiceType = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updateServiceType;
	}
	
	public ServiceTypeMaster getServiceType(String serviceTypeName) throws SQLException, PropertyVetoException {
		ServiceTypeMaster serviceType = null;

		

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT service_id, service_type ");
		queryString.append("FROM service_type_master ");
		queryString.append("WHERE service_type=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, serviceTypeName);
			rs = stmt.executeQuery();
			
			while (rs.next()) {

				serviceType = new ServiceTypeMaster();
				serviceType.setServiceTypeId(rs.getInt(1));
				serviceType.setServiceTypeName(rs.getString(2));

			}

		} catch (Exception e) {
			log.error("Got Exception while get service type Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return serviceType;
	}
	
	public boolean checkDuplicateRecForServiceTypeMaster(String serviceType)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		Statement statement = connection.createStatement();
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM service_type_master");
			queryString.append(" WHERE service_type=?");
			queryString.append(" AND deleted_by IS NULL");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, serviceType);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for service type:", e);
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return status;

	}
}