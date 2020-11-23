package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.ProjectTeamSkills;

public class ProjectPeopleSkillsDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;
	private static final Logger log = LogManager.getLogger(ProjectPeopleSkillsDao.class);

	public ArrayList<ProjectTeamSkills> getProjectTeamSkillsList(int projectId)
			throws SQLException, PropertyVetoException {

		ProjectTeamSkills projectTeamSkills;
		ArrayList<ProjectTeamSkills> projectTeamSkillsList = new ArrayList<ProjectTeamSkills>();

		ResultSet rs = null;
		PreparedStatement stmt = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(
				"SELECT pts.project_id, rm.role_ID, rm.role, pts.skills, pts.Experience, pts.location_ID, lm.location, pts.skill_count ");
		queryString.append("FROM role_master as rm ");
		queryString.append("INNER JOIN project_team_skills as pts ");
		queryString.append("INNER JOIN location_master as lm ");
		queryString
				.append("WHERE rm.role_id=pts.role_id AND lm.location_id=pts.location_id AND pts.deleted_by IS NULL ");
		queryString.append("AND pts.project_id=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				projectTeamSkills = new ProjectTeamSkills();
				projectTeamSkills.setProjectId(rs.getInt(1));
				projectTeamSkills.setRoleID(rs.getInt(2));
				projectTeamSkills.setRole(rs.getString(3));
				projectTeamSkills.setSkills(rs.getString(4));
				projectTeamSkills.setExperience(rs.getInt(5));
				projectTeamSkills.setLocationId(rs.getInt(6));
				projectTeamSkills.setLocation(rs.getString(7));
				projectTeamSkills.setSkillscount(rs.getInt(8));
				projectTeamSkillsList.add(projectTeamSkills);

			}
		} catch (Exception e) {
			log.error("Got Exception while selecting project team skills Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}
		return projectTeamSkillsList;
	}

	public ArrayList<ProjectTeamSkills> getProjectTeamSkillsListforAllocationcount(int projectId)
			throws SQLException, PropertyVetoException {

		ProjectTeamSkills projectTeamSkills;
		ArrayList<ProjectTeamSkills> projectTeamSkillsListforAllocationcount = new ArrayList<ProjectTeamSkills>();

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT pts.project_id,rm.role_ID, rm.role,pts.skill_count, t.allocated_count");
		queryString.append(" from project_team_skills as pts");
		queryString.append(" INNER JOIN role_master as rm on (pts.role_id = rm.role_id and rm.deleted_by IS NULL)");
		queryString.append(" left JOIN ");
		queryString.append(" (");
		queryString.append(" select pt.role_id, count(pt.role_id) as allocated_count");
		queryString.append(" from project_team as pt");
		queryString.append(" where pt.Project_ID = ?");
		queryString.append(" and pt.deleted_by IS NULL");
		queryString.append(" GROUP BY pt.role_ID");
		queryString.append(" ) as t on (pts.role_id = t.role_id)");
		queryString.append(" where pts.project_id=?");
		queryString.append(" AND pts.deleted_by IS NULL;");

		Connection connection = DBConnect.getConnection();
		log.info("query is " + queryString.toString());
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectId);
			stmt.setInt(2, projectId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				projectTeamSkills = new ProjectTeamSkills();
				projectTeamSkills.setProjectId(rs.getInt(1));
				projectTeamSkills.setRoleID(rs.getInt(2));
				projectTeamSkills.setRole(rs.getString(3));
				// projectTeamSkills.setSkills(rs.getString(4));
				// projectTeamSkills.setExperience(rs.getInt(5));
				// projectTeamSkills.setLocationId(rs.getInt(6));
				// projectTeamSkills.setLocation(rs.getString(7));
				projectTeamSkills.setSkillscount(rs.getInt(4));
				projectTeamSkills.setCountAllocated(rs.getInt(5));
				projectTeamSkillsListforAllocationcount.add(projectTeamSkills);

			}
		} catch (Exception e) {
			log.error("Got Exception while selecting project team skills Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}
		return projectTeamSkillsListforAllocationcount;
	}

	public Boolean AddedSkills(ProjectTeamSkills teamSkills, int pmId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSkills = false;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append("INSERT INTO project_team_skills( " + "project_id," + "role_id, " + "skills, "
					+ "experience, " + "location_ID, " + "skill_count, " + "created_by, " + "created_date) ");
			queryString.append("VALUES (");
			queryString.append("?,?,?,?,?,?,");
			queryString.append(pmId + ",");
			queryString.append("CURRENT_TIMESTAMP())");

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, teamSkills.getProjectId());
			stmt.setInt(2, teamSkills.getRoleID());
			stmt.setString(3, teamSkills.getSkills());
			stmt.setInt(4, teamSkills.getExperience());
			stmt.setInt(5, teamSkills.getLocationId());
			stmt.setInt(6, teamSkills.getSkillscount());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Project team skills added successfully");
				addSkills = true;
			}

		} catch (Exception e) {
			log.info("Exception while adding people skill : ", e);
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addSkills;

	}

	public boolean editSkills(ProjectTeamSkills teamSkills, int pmId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSkills = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE project_team_skills SET ");
		queryString.append("skills=?,");
		queryString.append("experience=?,");
		queryString.append("skill_count=?,");
		queryString.append("updated_by=?,");
		queryString.append("updated_date=CURRENT_TIMESTAMP() ");
		queryString.append(" , deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE role_id=" + teamSkills.getRoleID());
		queryString.append(" AND location_ID=" + teamSkills.getLocationId());
		queryString.append(" AND project_id=" + teamSkills.getProjectId());
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, teamSkills.getSkills());
			stmt.setInt(2, teamSkills.getExperience());
			stmt.setInt(3, teamSkills.getSkillscount());
			stmt.setInt(4, pmId);

			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateSkills = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateSkills;
	}

	public boolean addSkills(ProjectTeamSkills teamSkills, int pmId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSkills = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE project_team_skills SET ");
		queryString.append("updated_by=" + pmId);
		queryString.append(",updated_date=CURRENT_TIMESTAMP() ");
		queryString.append(" , deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE role_id=?");
		queryString.append(" AND project_id=?");
		queryString.append(" AND skills=?");
		queryString.append(" AND experience=?");
		queryString.append(" AND location_ID=?");
		// queryString.append(" AND skill_count=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, teamSkills.getRoleID());
			stmt.setInt(2, teamSkills.getProjectId());
			stmt.setString(3, teamSkills.getSkills());
			stmt.setInt(4, teamSkills.getExperience());
			stmt.setInt(5, teamSkills.getLocationId());
			// stmt.setInt(6, teamSkills.getSkillscount());

			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateSkills = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateSkills;
	}

	public Boolean deleteSkills(int roleID, int projectId, int locationId, int projectManagerID)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSkills = false;

		StringBuffer queryString = new StringBuffer();
		// queryString.append("UPDATE project_team_skills SET
		// deleted_date=CURRENT_TIMESTAMP(),deleted_by=?");
		queryString.append(" DELETE FROM project_team_skills");
		queryString.append(" WHERE role_ID=?");
		queryString.append(" AND project_id=?");
		queryString.append(" AND location_ID = ?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			// stmt.setInt(1, projectManagerID);
			stmt.setInt(1, roleID);
			stmt.setInt(2, projectId);
			stmt.setInt(3, locationId);
			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Project team skills deleted successfully");
				deleteSkills = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteSkills;
	}

	public ProjectTeamSkills getSkills(int roleId, int projectId) throws SQLException, PropertyVetoException {

		ProjectTeamSkills projectTeamSkills = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT Role_ID, Project_ID ");
		queryString.append("FROM project_team_skills ");
		queryString.append("WHERE Role_ID=?");
		queryString.append(" AND Project_ID=?");

		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, roleId);
			stmt.setInt(2, projectId);
			rs = stmt.executeQuery();

			while (rs.next()) {

				projectTeamSkills = new ProjectTeamSkills();
				projectTeamSkills.setRoleID(rs.getInt(1));
				projectTeamSkills.setProjectId(rs.getInt(2));

			}

		} catch (Exception e) {
			log.error("Got Exception while get Role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}

		return projectTeamSkills;

	}

	public boolean checkForPeopleSkillForDifferentLocation(ProjectTeamSkills teamSkills)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		queryString.append("SELECT *");
		queryString.append(" FROM project_team_skills");
		queryString.append(" WHERE Project_ID=?");
		queryString.append(" AND Role_ID=?");

		// queryString.append(" AND updated_by IS NULL");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, teamSkills.getProjectId());
			stmt.setInt(2, teamSkills.getRoleID());
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt("location_ID") == teamSkills.getLocationId()) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for people skill details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return status;
	}

	public boolean checkDuplicateRecForPeopleSkill(ProjectTeamSkills teamSkills)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		queryString.append("SELECT *");
		queryString.append(" FROM project_team_skills");
		queryString.append(" WHERE Project_ID=?");
		queryString.append(" AND Role_ID=?");
		queryString.append(" AND location_ID=?");
		queryString.append(" AND deleted_by IS NULL");
		// queryString.append(" AND updated_by IS NULL");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, teamSkills.getProjectId());
			stmt.setInt(2, teamSkills.getRoleID());
			stmt.setInt(3, teamSkills.getLocationId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				status = true;
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for people skill details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return status;
	}
}