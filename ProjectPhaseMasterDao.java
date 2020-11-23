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
import ai.rnt.pins.model.ProjectPhaseMaster;
import ai.rnt.pins.model.SoftwareMaster;

public class ProjectPhaseMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(ProjectPhaseMasterDao.class);

	public ArrayList<ProjectPhaseMaster> getListOfProjectPhases() throws SQLException, PropertyVetoException {
		ProjectPhaseMaster projectPhase = new ProjectPhaseMaster();

		ArrayList<ProjectPhaseMaster> phaseList = new ArrayList<ProjectPhaseMaster>();
		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT phase_id, project_phase ");
		queryString.append("FROM phase_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			
			while (rs.next()) {

				projectPhase = new ProjectPhaseMaster();
				projectPhase.setProjectPhaseId(rs.getInt(1));
				projectPhase.setProjectPhaseName(rs.getString(2));
				phaseList.add(projectPhase);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while list project phase Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return phaseList;
	}

	public Boolean addProjectPhase(String projectPhaseName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addPhase = false;
		try {
			ProjectPhaseMaster projectPhase = getProjectPhase(projectPhaseName);
			if (projectPhase == null) {
				StringBuffer queryString = new StringBuffer();
				queryString.append("INSERT into phase_master(project_phase, created_by, created_date) ");
				queryString.append("VALUES (?,?,CURRENT_TIMESTAMP())");

				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, projectPhaseName);
				stmt.setInt(2, adminId);

				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("Project Phase added successfully");
					addPhase = true;
				}
			} else {
				updateProjectPhase(projectPhase.getProjectPhaseId(), projectPhase.getProjectPhaseName(), adminId);
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addPhase;
	}

	public Boolean deleteProjectPhase(int phaseId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deletePhase = false;
	
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE phase_master SET deleted_by="+adminId);
			queryString.append(",deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE phase_id=?");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, phaseId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Project Phase deleted successfully");
				deletePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deletePhase;
	}

	public Boolean updateProjectPhase(int phaseId, String projectPhaseName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePhase = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE phase_master SET project_phase=?");
			queryString.append(",updated_by=?");
			queryString.append(",updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE phase_id="+phaseId);
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1,projectPhaseName);
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Project Phase updated successfully");
				updatePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updatePhase;
	}

	
	public Boolean updateAddedProjectPhase(String projectPhaseName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePhase = false;
	
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE phase_master SET ");
			queryString.append("updated_by="+adminId);
			queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
			queryString.append("WHERE project_phase=?");

			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1,projectPhaseName);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Project Phase updated successfully");
				updatePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return updatePhase;
	}

	public ProjectPhaseMaster getProjectPhase(String projectPhaseName) throws SQLException, PropertyVetoException {
		ProjectPhaseMaster projectPhase = null;

		
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT phase_id, project_phase ");
		queryString.append("FROM phase_master ");
		queryString.append("WHERE project_phase=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1,projectPhaseName);
			rs=stmt.executeQuery();
			
			while (rs.next()) {

				projectPhase = new ProjectPhaseMaster();
				projectPhase.setProjectPhaseId(rs.getInt(1));
				projectPhase.setProjectPhaseName(rs.getString(2));

			}

		} catch (Exception e) {
			log.error("Got Exception get project phase Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return projectPhase;
	}
	
	public boolean checkDuplicateRecForProjectPhaseDetails(String projectPhaseName)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM phase_master");
			queryString.append(" WHERE project_phase=?");
			queryString.append(" AND deleted_by IS NULL");
			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1,projectPhaseName);
				rs=stmt.executeQuery();
				
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for project phase details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return status;

	}
}