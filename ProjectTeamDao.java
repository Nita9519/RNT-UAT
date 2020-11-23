package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.CustomerMasterView;
import ai.rnt.pins.model.ProjectTeam;
import ai.rnt.pins.model.ProjectTeamDetails;
import ai.rnt.pins.model.RoleMaster;

public class ProjectTeamDao {
	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(ProjectTeamDao.class);

	public ArrayList<ProjectTeam> getRole() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<ProjectTeam> projectTeamList = new ArrayList<ProjectTeam>();
		StringBuffer queryString = new StringBuffer();
		ProjectTeam projectTeam = null;
		queryString.append("SELECT role,role_id FROM role_master WHERE deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				projectTeam = new ProjectTeam();
				projectTeam.setRoleID(rs.getInt(2));
				projectTeam.setRole(rs.getString(1));
				projectTeamList.add(projectTeam);

			}
		} catch (Exception e) {
			log.error("Got Exception get role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return projectTeamList;

	}

	public int getRoleIdForRole(String role) throws SQLException, PropertyVetoException {
		ProjectTeamDetails role1 = null;
		int roleID = 0;

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT role_id, role ");
		queryString.append("FROM role_master ");
		queryString.append("WHERE role=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, role);
			rs = stmt.executeQuery();

			while (rs.next()) {

				role1 = new ProjectTeamDetails();
				role1.setRoleID(rs.getInt(1));
				role1.setRole(rs.getString(2));

				roleID = role1.getRoleID();

			}

		} catch (Exception e) {
			log.error("Got Exception while get role ID for Role :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return roleID;
	}

	public ArrayList<ProjectTeam> getAssociate() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<ProjectTeam> projectAssociateList = new ArrayList<ProjectTeam>();
		StringBuffer queryString = new StringBuffer();
		ProjectTeam projectTeam1 = null;
		queryString
				.append(" select Staff_id ,F_Name,L_Name from employee_master where staff_id AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				projectTeam1 = new ProjectTeam();
				projectTeam1.setStaffID(rs.getInt(1));
				projectTeam1.setAssociateName(rs.getString(2) + " " + rs.getString(3));
				projectAssociateList.add(projectTeam1);

			}
		}

		catch (Exception e) {
			log.error("Got Exception while get Associate :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return projectAssociateList;

	}

	public int getStaffIdForAssociate(String associateName) throws SQLException, PropertyVetoException {
		ProjectTeamDetails staffName = null;
		int staffID = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT staff_ID,F_Name ");
		queryString.append("FROM employee_master ");
		queryString.append("WHERE F_Name=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, associateName);
			rs = stmt.executeQuery();

			while (rs.next()) {

				staffName = new ProjectTeamDetails();
				staffName.setStaffId(rs.getInt(1));
				staffName.setAssociateName(rs.getString(2));

				staffID = staffName.getStaffId();

			}

		} catch (Exception e) {
			log.error("Got Exception while get Staff ID for Associate :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return staffID;
	}

	public ArrayList<ProjectTeamDetails> getAllTeamDetails(int projectId) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<ProjectTeamDetails> associateTeamList = new ArrayList<ProjectTeamDetails>();
		StringBuffer queryString = new StringBuffer();
		ProjectTeamDetails projectTeamDetails = null;
		PreparedStatement stmt = null;
		queryString.append(
				"select e1.F_name,e1.L_name,r1.role,p.percent_allocation,p.allocation_date,p.release_date,p.project_team_ID, e1.Staff_ID from project_team as p INNER JOIN role_master as r1 inner join employee_master as e1 where p.role_id=r1.role_id and p.staff_ID=e1.Staff_ID and p.Project_ID=?");
		queryString.append(" AND p.deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				projectTeamDetails = new ProjectTeamDetails();
				// projectTeamDetails.setProjectID(rs.getInt(1));
				projectTeamDetails.setAssociateName(rs.getString(1) + " " + rs.getString(2));
				projectTeamDetails.setRole(rs.getString(3));
				projectTeamDetails.setPercentageAllocation(rs.getInt(4));
				projectTeamDetails.setAllocationDate(rs.getDate(5));
				projectTeamDetails.setReleaseDate(rs.getDate(6));
				projectTeamDetails.setProjectTeamId(rs.getInt(7));
				projectTeamDetails.setStaffId(rs.getInt(8));

				associateTeamList.add(projectTeamDetails);

			}
		} catch (Exception e) {
			log.error("Got Exception while get All team Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return associateTeamList;

	}

	public int checkAllocation(ProjectTeamDetails projectTeamDetails) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		int allocationStatus = 0;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT SUM(percent_allocation) FROM project_team");
		queryString.append(" WHERE staff_ID = ? AND deleted_by IS NULL");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectTeamDetails.getStaffId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				allocationStatus = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return allocationStatus;
	}
	
	public ArrayList<CustomerMasterView> getAllocatedCount(	ProjectTeamDetails projectteamdetail) throws SQLException, PropertyVetoException {
		ArrayList<CustomerMasterView> countallocation =new ArrayList<CustomerMasterView>();
		StringBuffer queryString = new StringBuffer();
		CustomerMasterView masterCustomer = null;
		PreparedStatement statement = null;
		RoleMaster rm = new RoleMaster();
		queryString.append(" select rm.role,count(rm.role) from project_team pt,role_master rm "
				+ "where pt.role_id=rm.role_id and "
				+ "rm.role=? and project_ID=1 \r\n" + 
						"and pt.deleted_by is null\r\n" + 
						"and rm.deleted_by is null\r\n" + 
						"group by rm.role");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery(queryString.toString());
			statement.setString(1, projectteamdetail.getRole());
			log.info("query is " + queryString.toString());
			while (rs.next()) {
				
				masterCustomer = new CustomerMasterView();
				masterCustomer.setRolename(rs.getString(1));
				masterCustomer.setCount(rs.getInt(2));
				log.info("count is  " + rs.getInt(2));
				countallocation.add(masterCustomer);
				log.info(" the count is  " + rs.getInt(1));
			}

		} catch (Exception e) {
			log.error("Got Exception while fetching value ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return countallocation;
	}
	
	public int checkAllocationperperson(ProjectTeamDetails projectTeamDetails) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		int allocationStatus = 0;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT percent_allocation FROM project_team");
		queryString.append(" WHERE staff_ID = ? AND project_ID= ? AND deleted_by IS NULL");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectTeamDetails.getStaffId());
			statement.setInt(2, projectTeamDetails.getProjectID());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				allocationStatus = resultSet.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return allocationStatus;
	}
	
	public boolean checkAllocationname(ProjectTeamDetails projectTeamDetails) throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		boolean allocationStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*) FROM project_team");
		queryString.append(" WHERE staff_ID = ? AND deleted_by IS NULL");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectTeamDetails.getStaffId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
					if (resultSet.getInt(1) > 1) {
						allocationStatus = true;
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return allocationStatus;
	}
	public boolean checkAllocationInSameDate(ProjectTeamDetails projectTeamDetails)
			throws SQLException, PropertyVetoException {
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		boolean allocationStatusDate = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*) FROM project_team ");
		queryString.append(" where staff_ID= ");
		queryString.append(projectTeamDetails.getStaffId());
		queryString.append(" AND allocation_date= '");
		queryString.append(projectTeamDetails.getAllocationDate() + "'");
		queryString.append(" AND release_date= '");
		queryString.append(projectTeamDetails.getReleaseDate() + "'");
		queryString.append(" AND deleted_by IS NULL");

		Connection connection = DBConnect.getConnection();
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			resultSet = statement.executeQuery();
			log.info("query===" + queryString.toString());

			while (resultSet.next()) {
				if (resultSet.getInt(1) > 0) {
					allocationStatusDate = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		}
		return allocationStatusDate;
	}

	public boolean insertAssociate(ProjectTeamDetails projectTeamDetails, int adminId, int projectID)
			throws SQLException, PropertyVetoException {

		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean insertStatus = false;

		queryString.append("insert into project_team(staff_ID,role_id,release_date,allocation_date, percent_allocation,created_by,created_date,Project_ID) ");
		queryString.append("values (?,?,?,?,?,");
		queryString.append(adminId);
		queryString.append(",CURRENT_TIMESTAMP(),");
		queryString.append(projectID);
		queryString.append(")");

		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			statement = connection.prepareStatement(queryString.toString());
			// statement.setInt(1, projectTeamDetails.getProjectID());
			statement.setInt(1, projectTeamDetails.getStaffId());
			statement.setInt(2, projectTeamDetails.getRoleID());
			statement.setDate(3, (Date) projectTeamDetails.getReleaseDate());
			statement.setDate(4, (Date) projectTeamDetails.getAllocationDate());
			statement.setInt(5, projectTeamDetails.getPercentageAllocation());
			projectTeamDetails.setCreatedBy(adminId);
			projectTeamDetails.setCreatedDate(timestamp);
			projectTeamDetails.setProjectID(projectID);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				insertStatus = true;
				log.info("A new member inserted successfully!............");
			}

		} catch (Exception e) {
			log.error("Got Exception while inserting Associate Details :: ", e);
			// e.printStackTrace();
		} finally {
			if (statement != null)
				statement.close();

			connection.close();
		}
		return insertStatus;
	}

	public Boolean deleteAssociate(ProjectTeamDetails projectTeamDetails, int adminId)
			throws SQLException, PropertyVetoException {
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean deleteAssociate = false;

		
			queryString.append("UPDATE project_team ");
			queryString.append("SET deleted_by=?");
			queryString.append(",deleted_date = CURRENT_TIMESTAMP()");
			queryString.append(" where staff_ID=?");
			queryString.append(" AND project_team_ID=?");
			
			try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, adminId);
			statement.setInt(2, projectTeamDetails.getStaffId());
			statement.setInt(3, projectTeamDetails.getProjectTeamId());
            log.info("query===="+queryString.toString());
			int rowDeleted = statement.executeUpdate();
			if (rowDeleted > 0)
				deleteAssociate = true;
				log.info("associate deleted successfully");
			
		} finally {
			statement.close();
			connection.close();
		}
		return deleteAssociate;

	}

	public boolean updateAssociate(ProjectTeamDetails projectTeamDetails, int adminId)
			throws SQLException, PropertyVetoException {
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean updateStatus = false;

	
			queryString.append("UPDATE project_team SET ");
			queryString.append(" role_id=");
			queryString.append(projectTeamDetails.getRoleID() + ",");
			queryString.append(" percent_allocation=");
			queryString.append(projectTeamDetails.getPercentageAllocation() + ",");
			queryString.append(" allocation_date='");
			queryString.append(projectTeamDetails.getAllocationDate() + "'" + ",");
			queryString.append("release_date='");
			queryString.append(projectTeamDetails.getReleaseDate() + "'" + ",");
			queryString.append("updated_by=");
			queryString.append(adminId);
			queryString.append(",updated_date=CURRENT_TIMESTAMP() ");
			queryString.append(" WHERE staff_ID=");
			queryString.append(projectTeamDetails.getStaffId());
			queryString.append(" AND Project_ID=");
			queryString.append(projectTeamDetails.getProjectID());
			queryString.append(" AND allocation_date='");
			queryString.append(projectTeamDetails.getAllocationDate() + "'");
			queryString.append(" AND release_date='");
			queryString.append(projectTeamDetails.getReleaseDate() + "'");
			queryString.append(" AND deleted_by IS NULL");
			try {
			log.info("query=========" + queryString.toString());
			statement = connection.prepareStatement(queryString.toString());

			int rowUpdated = statement.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateStatus = true;
			}
		}

		catch (Exception e) {
			log.error("Got Exception while update Associate Details :: ", e);
			e.printStackTrace();
		}

		finally {
			statement.close();
			connection.close();
		}
		return updateStatus;
	}

	public boolean updateAddedAssociate(ProjectTeamDetails projectTeamDetails, int adminId)
			throws SQLException, PropertyVetoException {
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		boolean updateStatus = false;

		
			queryString.append("UPDATE project_team SET ");
			queryString.append("updated_by=");
			queryString.append(adminId);
			queryString.append(",updated_date=CURRENT_TIMESTAMP(),deleted_by=NULL,deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append(" WHERE staff_ID=");
			queryString.append(projectTeamDetails.getStaffId());
			queryString.append(" AND role_id=");
			queryString.append(projectTeamDetails.getRoleID());
			queryString.append("  AND percent_allocation=");
			queryString.append(projectTeamDetails.getPercentageAllocation());
			queryString.append("  AND allocation_date='");
			queryString.append(projectTeamDetails.getAllocationDate() + "'");
			queryString.append(" AND release_date='");
			queryString.append(projectTeamDetails.getReleaseDate() + "'");
			try {
			statement = connection.prepareStatement(queryString.toString());

			int rowUpdated = statement.executeUpdate();

			/*
			 * if (rowUpdated == false) { log.info("leave updated successfully");
			 * updateStatus = true; }
			 */

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateStatus = true;
			}
		}

		catch (Exception e) {
			log.error("Got Exception while update Associate Details :: ", e);
			e.printStackTrace();
		}

		finally {
			statement.close();
			connection.close();
		}
		return updateStatus;
	}
	public int getAllocatedCounts(CustomerMasterView masterCustomer, ProjectTeamDetails projectteamdetail)
			throws SQLException, PropertyVetoException {
		int count = 0;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT COUNT(*) from project_team pt,role_master rm ");
		queryString.append(" WHERE pt.role_id=rm.role_id and rm.role=?");
		queryString.append(" AND pt.deleted_by IS NULL ");
		
		
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, projectteamdetail.getRole());
			rs = statement.executeQuery();
			log.info("query is " + queryString.toString());
			while (rs.next()) {
				masterCustomer.setCount(rs.getInt(1));
				log.info("count is  " + rs.getInt(1));
				count = masterCustomer.getCount();
				log.info(" the count is  " + rs.getInt(1));
			}

		} catch (Exception e) {
			log.error("Got Exception while fetching value ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return count;
	}


}