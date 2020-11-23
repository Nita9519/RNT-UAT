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
import ai.rnt.pins.model.ProjectPhase;
import ai.rnt.pins.model.ProjectPhaseMaster;

public class ProjectPhaseDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(ProjectPhaseDao.class);

	public ArrayList<ProjectPhase> getProjectPhases(int projectId) throws SQLException, PropertyVetoException {
		ProjectPhase projectPhase;
		ArrayList<ProjectPhase> projectPhasesList = new ArrayList<ProjectPhase>();

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT  pp.project_id, pm.phase_id, pm.project_phase,pp.efforts, pp.unit, pp.start_date, pp.end_date ");
		queryString.append("FROM phase_master as pm ");
		queryString.append("INNER JOIN project_phase as pp ");
		queryString.append("WHERE pm.phase_id=pp.phase_id AND ");
		queryString.append("pp.project_id=?");
		queryString.append(" AND pp.deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1,projectId);
			rs=stmt.executeQuery();

			while (rs.next()) {
				projectPhase = new ProjectPhase();
				projectPhase.setProjectId(rs.getInt(1));
				projectPhase.setProjectPhaseName(rs.getString(3));
				projectPhase.setEfforts(rs.getInt(4));
				projectPhase.setUnit(rs.getString(5));
				projectPhase.setStartDate(rs.getDate(6));
				projectPhase.setEndDate(rs.getDate(7));
				projectPhasesList.add(projectPhase);

			}
		} catch (Exception e) {
			log.error("Got Exception in project Phase Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return projectPhasesList;
	}

	public ArrayList<ProjectPhase> getPhaseDetails(int project_ID, int phase_ID) {
		return null;

	}

	public Boolean addPhase(ProjectPhase projectPhase, int projectManagerId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addPhase = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("INSERT INTO project_phase( " + " project_id," + " phase_id, " + " efforts, " + " unit, "
					+ " start_date, " + " end_date, " + " created_by, " + " created_date) ");
			queryString.append("VALUES (?,?,?,?,?,?,?,CURRENT_TIMESTAMP())");

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectPhase.getProjectId());
			stmt.setInt(2, projectPhase.getProjectPhaseId());
			stmt.setInt(3, projectPhase.getEfforts());
			stmt.setString(4, projectPhase.getUnit());
			stmt.setDate(5, projectPhase.getStartDate());
			stmt.setDate(6, projectPhase.getEndDate());
			stmt.setInt(7, projectManagerId);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Project phase added successfully");
				addPhase = true;
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addPhase;

	}

	public Boolean deletePhase(int phaseId, int projectManagerId, int projectID)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deletePhase = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE project_phase SET deleted_by=?");
			queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND phase_Id=?");
			queryString.append(" AND project_id=?");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectManagerId);
			stmt.setInt(2, phaseId);
			stmt.setInt(3, projectID);
            rs=stmt.executeQuery();
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("phase deleted successfully");
				deletePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deletePhase;
	}

	public Boolean updatePhase(ProjectPhase projectPhase, int projectManagerId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePhase = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE project_phase SET ");
			queryString.append(" efforts=?,");
			queryString.append(" unit=?,");
			queryString.append(" start_date=?,");
			queryString.append(" end_date=?,");
			queryString.append(" updated_by=?,");
			queryString.append("updated_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE phase_id="+projectPhase.getProjectPhaseId());
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectPhase.getEfforts());
			stmt.setString(2, projectPhase.getUnit());
			stmt.setDate(3, projectPhase.getStartDate());
			stmt.setDate(4, projectPhase.getEndDate());
			stmt.setInt(5, projectManagerId);

			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updatePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updatePhase;

	}

	public boolean checkDuplicateRecForUpdatedProjectPhase(ProjectPhase projectPhase)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM project_phase");
			queryString.append(" WHERE phase_id=?");
			queryString.append(" AND Project_ID=?");
			queryString.append(" AND efforts=?");
			queryString.append(" AND unit=?");
			queryString.append(" AND start_date=?");
			queryString.append(" AND end_date=?");
			queryString.append(" AND deleted_by IS NULL");
			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setInt(1, projectPhase.getProjectPhaseId());
				stmt.setInt(2, projectPhase.getProjectId());
				stmt.setInt(3, projectPhase.getEfforts());
				stmt.setString(4, projectPhase.getUnit());
				stmt.setDate(5, projectPhase.getStartDate());
				stmt.setDate(6, projectPhase.getEndDate());
				
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

	public Boolean updateAddedPhase(ProjectPhase projectPhase, int projectManagerId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePhase = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE project_phase SET ");
			queryString.append(" updated_by=?,");
			queryString.append("updated_date=CURRENT_TIMESTAMP() ");
			queryString.append(" , deleted_by=NULL, deleted_date=NULL");
			queryString.append("WHERE phase_id=");
			queryString.append(" AND efforts=?");
			queryString.append(" AND unit=?");
			queryString.append(" AND start_date=?");
			queryString.append(" AND end_date=?");
			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setInt(1, projectManagerId);
				stmt.setInt(2, projectPhase.getProjectPhaseId());
				stmt.setInt(3, projectPhase.getEfforts());
				stmt.setString(4, projectPhase.getUnit());
				stmt.setDate(5, projectPhase.getStartDate());
				stmt.setDate(6, projectPhase.getEndDate());
				
			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updatePhase = true;
			}
		
		}finally {
			stmt.close();
			connection.close();
		}
		return updatePhase;

	}

	public int getPhaseId(String phaseName) throws SQLException, PropertyVetoException {

		int phaseId = 0;
		ProjectPhaseMaster phase = null;
		PreparedStatement stmt = null;

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT phase_id, project_phase ");
		queryString.append("FROM phase_master ");
		queryString.append("WHERE project_phase=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, phaseName);
			rs=stmt.executeQuery();
			while (rs.next()) {

				phase = new ProjectPhaseMaster();
				phase.setProjectPhaseId(rs.getInt(1));
				phase.setProjectPhaseName(rs.getString(2));

				phaseId = phase.getProjectPhaseId();

			}

		} catch (Exception e) {
			log.error("Got Exception while fetching project phase name :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return phaseId;
	}

	public boolean checkDuplicateRecForProjectPhase(int projectId, int phaseId)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM project_phase");
			queryString.append(" WHERE phase_id=?");
			queryString.append(" AND Project_ID=?");
			queryString.append(" AND deleted_by IS NULL");
			try {
				stmt = connection.prepareStatement(queryString.toString());
				stmt.setInt(1, phaseId);
				stmt.setInt(2, projectId);
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