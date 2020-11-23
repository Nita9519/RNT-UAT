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
import ai.rnt.pins.model.ProjectMilestone;

public class ProjectMilestoneDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;
	private static final Logger log = LogManager.getLogger(ProjectMilestoneDao.class);

	public ArrayList<ProjectMilestone> getProjectMilestoneList(int projectId)
			throws SQLException, PropertyVetoException {

		ProjectMilestone projectMilestones;
		ArrayList<ProjectMilestone> projectMilestonesList = new ArrayList<ProjectMilestone>();
		PreparedStatement statement=null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT pm.project_id, mm.milestone_id, mm.milestone, pm.milestone_date, pm.milestone_met_on,pm.milestone_met ");
		queryString.append("FROM milestone_master as mm ");
		queryString.append("INNER JOIN project_milestone as pm ");
		queryString.append("WHERE mm.milestone_id=pm.milestone_id AND ");
		queryString.append("pm.project_id=?");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectMilestones = new ProjectMilestone();
				projectMilestones.setProjectId(rs.getInt(1));
				projectMilestones.setMilestoneId(rs.getInt(2));
				projectMilestones.setMilestoneName(rs.getString(3));
				projectMilestones.setMilestoneDate(rs.getDate(4));
				projectMilestones.setMetDate(rs.getDate(5));
				projectMilestones.setMilestoneMet(rs.getString(6));
				projectMilestonesList.add(projectMilestones);
			}
		} catch (Exception e) {
			log.error("Got Exception while fetching Milestone Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return projectMilestonesList;
	}

	public Boolean metORnot(ProjectMilestone projectMilestone, int projectManagerId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean metORnot = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE project_milestone SET ");
		queryString.append(" milestone_met_on=?");
		queryString.append(", ");
		queryString.append(" milestone_met=?");
		queryString.append(", ");
		queryString.append(" updated_by=?");
		queryString.append(" ,updated_date=CURRENT_TIMESTAMP() ");
		queryString.append(" WHERE milestone_id=?");
		queryString.append(" AND project_Id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			if(projectMilestone.getMetDate() != null ) {
				stmt.setDate(1, projectMilestone.getMetDate());
			} else {
				stmt.setDate(1, null);
			}
			stmt.setString(2, projectMilestone.getMilestoneMet());
			stmt.setInt(3, projectManagerId);
			stmt.setInt(4, projectMilestone.getMilestoneId());
			stmt.setInt(5, projectMilestone.getProjectId());
			
			int rowUpdated = stmt.executeUpdate();
			
			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				metORnot = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return metORnot;

	}

	/*public Boolean deleteMilestone(int milestoneId, int projectManagerId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteMilestone = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE project_milestone SET deleted_by=");
			queryString.append(projectManagerId + ", ");
			queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND milestone_id=");
			queryString.append(milestoneId);
			log.info("query we are firing " + queryString.toString());

			stmt = connection.prepareStatement(queryString.toString());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("milestone deleted successfully");
				deleteMilestone = true;
			}	
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteMilestone;
	}*/
}