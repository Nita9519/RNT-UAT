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
import ai.rnt.pins.model.RoleMaster;

public class RoleMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;
	

	private static final Logger log = LogManager.getLogger(RoleMasterDao.class);

	public ArrayList<RoleMaster> getListOfRoles() throws SQLException, PropertyVetoException {
		RoleMaster role = new RoleMaster();
PreparedStatement stmt = null;
		ArrayList<RoleMaster> roleList = new ArrayList<RoleMaster>();
		
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT role_id, role ");
		queryString.append("FROM role_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {

				role = new RoleMaster();
				role.setRoleId(rs.getInt(1));
				role.setRoleName(rs.getString(2));
				roleList.add(role);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while list of role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return roleList;
	}

	public Boolean addRole(String roleName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addRole = false;
		try {
			RoleMaster role = getRole(roleName);
			if (role == null) {
				StringBuffer queryString = new StringBuffer();
				queryString.append(
						"INSERT into role_master(role, created_by, created_date) VALUES (?,?,CURRENT_TIMESTAMP())");

				
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, roleName);
				stmt.setInt(2, adminId);

				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("role added successfully");
					addRole = true;
				}
			} else {
				updateRole(role.getRoleId(), role.getRoleName(), adminId);
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addRole;
	}

	public Boolean deleteRole(int roleId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteRole = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE role_master SET ");
			queryString.append("deleted_by=?");
			queryString.append(",deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND role_id="+roleId);
			try {
			
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("role deleted successfully");
				deleteRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteRole;
	}

	public Boolean updateRole(int roleId, String roleName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRole = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE role_master SET role=?");
			queryString.append(",updated_by=?");
			queryString.append(",updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL WHERE role_id="+roleId);

			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, roleName);
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("role updated successfully");
				updateRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updateRole;
	}
	
	public Boolean updateAddedRole(String roleName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRole = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE role_master SET ");
			queryString.append("updated_by="+adminId);
			queryString.append(",updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL WHERE role=?");
			try {
			
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, roleName);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("role updated successfully");
				updateRole = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updateRole;
	}

	public RoleMaster getRole(String roleName) throws SQLException, PropertyVetoException {
		RoleMaster role = null;

		
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append("SELECT role_id, role ");
		queryString.append("FROM role_master ");
		queryString.append("WHERE role=?");
		Connection connection = DBConnect.getConnection();

			try {
				
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, roleName);
				rs = stmt.executeQuery();
			while (rs.next()) {
				role = new RoleMaster();
				role.setRoleId(rs.getInt(1));
				role.setRoleName(rs.getString(2));
			}

		} catch (Exception e) {
			log.error("Got Exception while get Role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return role;
	}
	
	public boolean checkDuplicateRecForRole(String role)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM role_master");
			queryString.append(" WHERE role=?");
			queryString.append(" AND deleted_by IS NULL");
			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, role);
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for role:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return status;

	}
}