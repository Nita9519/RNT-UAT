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
import ai.rnt.pins.model.MilestoneMaster;

public class MilestoneMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(MilestoneMasterDao.class);

	public ArrayList<MilestoneMaster> getListOfMilestones() throws SQLException, PropertyVetoException {
		MilestoneMaster milestone = new MilestoneMaster();

		ArrayList<MilestoneMaster> milestoneList = new ArrayList<MilestoneMaster>();
		Connection connection = DBConnect.getConnection();
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT milestone_id, milestone ");
		queryString.append("FROM milestone_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				milestone = new MilestoneMaster();
				milestone.setMilestoneId(rs.getInt(1));
				milestone.setMilestoneName(rs.getString(2));
				milestoneList.add(milestone);
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching List Of Milestone :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return milestoneList;
	}

	public Boolean addMilestone(String milestoneName, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addMilestone = false;
		try {
			MilestoneMaster milestone = getMilestone(milestoneName);
			if (milestone == null) {
				StringBuffer queryString = new StringBuffer();
				queryString.append("INSERT into milestone_master(milestone, created_by, created_date) ");
				queryString.append("values (?,?,CURRENT_TIMESTAMP())");

				stmt = connection.prepareStatement(queryString.toString());
				stmt.setString(1, milestoneName);
				stmt.setInt(2, adminId);

				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("milestone added successfully");
					addMilestone = true;
				}
			} else {
				updateMilestone(milestone.getMilestoneId(), milestone.getMilestoneName(), adminId);
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addMilestone;
	}

	public Boolean deleteMilestone(int milestoneId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteMilestone = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE milestone_master SET deleted_by=?");
		queryString.append(", ");
		queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND milestone_id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setInt(2, milestoneId);
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
	}

	public Boolean updateMilestone(int milestoneId, String milestoneName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateMilestone = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE milestone_master SET milestone=?");
		queryString.append(", ");
		queryString.append("updated_by=?");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE milestone_id=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, milestoneName);
			stmt.setInt(2, adminId);
			stmt.setInt(3, milestoneId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("milestone updated successfully");
				updateMilestone = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateMilestone;
	}
	
	public Boolean updateAddedMilestone(String milestoneName, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateMilestone = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE milestone_master SET ");
		queryString.append("updated_by=?");
		queryString.append(", ");
		queryString.append("updated_date=CURRENT_TIMESTAMP(), deleted_by=NULL, deleted_date=NULL ");
		queryString.append("WHERE milestone=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setString(2, milestoneName);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("milestone updated successfully");
				updateMilestone = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateMilestone;
	}

	public MilestoneMaster getMilestone(String milestoneName) throws SQLException, PropertyVetoException {
		MilestoneMaster milestone = null;

		
		ResultSet rs = null;
		PreparedStatement stmt=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT milestone_id, milestone ");
		queryString.append("FROM milestone_master ");
		queryString.append("WHERE milestone =?");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, milestoneName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				milestone = new MilestoneMaster();
				milestone.setMilestoneId(rs.getInt(1));
				milestone.setMilestoneName(rs.getString(2));
			}
		} catch (Exception e) {
			log.error("Got Exception while fetching Milestone Master :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return milestone;
	}
	
	public boolean checkDuplicateRecForMilestone(String milestone)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;
		queryString.append("SELECT COUNT(*)");
		queryString.append(" FROM milestone_master");
		queryString.append(" WHERE milestone=?");
		queryString.append(" AND deleted_by IS NULL");
		try {
			
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, milestone);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for milestone:", e);
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return status;

	}
}