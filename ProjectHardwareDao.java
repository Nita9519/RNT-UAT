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
import ai.rnt.pins.model.HardwareMaster;
import ai.rnt.pins.model.ProjectHardware;

public class ProjectHardwareDao {
	private static final Logger log = LogManager.getLogger(ProjectHardware.class);

	public ArrayList<ProjectHardware> getHardwareMasterList() throws SQLException, PropertyVetoException {
		ProjectHardware hardware = new ProjectHardware();
		ArrayList<ProjectHardware> hardwareList = new ArrayList<ProjectHardware>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select hardware_id, hardware_type, hardware_model, Configuration ");
		queryString.append("from hardware_master");
		queryString.append(" WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				hardware = new ProjectHardware();
				hardware.setHardwareId(rs.getInt(1));
				hardware.setType(rs.getString(2));
				hardware.setModel(rs.getString(3));
				hardware.setConfiguration(rs.getString(4));
				hardwareList.add(hardware);
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching List Of Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return hardwareList;
	}

	public ArrayList<ProjectHardware> getProjectHardwareList(int projectId) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		ArrayList<ProjectHardware> hardwareList = new ArrayList<ProjectHardware>();
		ProjectHardware projectHardware = new ProjectHardware();
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT ph.project_id, hm.hardware_id, hm.hardware_type, hm.hardware_model,hm.configuration, ph.No_of_units ");
		queryString.append("FROM hardware_master as hm ");
		queryString.append("INNER JOIN Project_Hardware as ph ");
		queryString.append("WHERE ph.hardware_id=hm.hardware_id AND ph.deleted_by is NULL AND ph.project_id=?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectHardware = new ProjectHardware();
				projectHardware.setProjectId(rs.getInt(1));
				projectHardware.setHardwareId(rs.getInt(2));
				projectHardware.setType(rs.getString(3));
				projectHardware.setModel(rs.getString(4));
				projectHardware.setConfiguration(rs.getString(5));
				projectHardware.setUnit(rs.getInt(6));
				hardwareList.add(projectHardware);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching List Of Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return hardwareList;
	}

	public Boolean addHardware(ProjectHardware projectHardware, int pmId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addHardware = false;
		try {
			ProjectHardware projectHard = getHardware(projectHardware.getHardwareId(), projectHardware.getProjectId());

			if (projectHard == null) {
				StringBuffer queryString = new StringBuffer();
				queryString.append("INSERT INTO Project_Hardware( " + "project_id, " + "hardware_id, " + "no_of_units, "
						+ "created_by, " + "created_date) ");
				queryString.append("VALUES (?,?,?,");
				queryString.append(pmId + ",");
				queryString.append("CURRENT_TIMESTAMP())");

				stmt = connection.prepareStatement(queryString.toString());
				stmt.setInt(1, projectHardware.getProjectId());
				stmt.setInt(2, projectHardware.getHardwareId());
				stmt.setInt(3, projectHardware.getUnit());
				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("Project hardware added successfully");
					addHardware = true;
				}
			} else {
				updateHardware(projectHardware, pmId);
			}
		} catch (Exception e) {
			log.error("Got Exception while inserting Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addHardware;

	}

	public Boolean deleteHardware(int hardwareId, int projectManagerId, int project_Id)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE Project_Hardware SET ");
		queryString.append("deleted_by=" + projectManagerId);
		queryString.append(", ");
		queryString.append("deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND hardware_id=?");
		queryString.append(" AND Project_ID=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());

			stmt.setInt(1, hardwareId);
			stmt.setInt(2, project_Id);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("hardware deleted successfully");
				deleteHardware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while Deleting Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return deleteHardware;
	}

	public Boolean updateHardware(ProjectHardware projectHardware, int projectManagerId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE Project_Hardware SET ");
		queryString.append("no_of_units=?");
		queryString.append(" , updated_by=?");
		queryString.append(" , updated_date=CURRENT_TIMESTAMP()");
		queryString.append(" , deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE hardware_id=?");
		queryString.append(" AND project_ID=?");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectHardware.getUnit());
			stmt.setInt(2, projectManagerId);
			stmt.setInt(3, projectHardware.getHardwareId());
			stmt.setInt(4, projectHardware.getProjectId());
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateHardware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while updating Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return updateHardware;

	}

	public Boolean updateAddedHardware(ProjectHardware projectHardware, int projectManagerId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateHardware = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE Project_Hardware SET ");
		queryString.append(" updated_by=?");
		queryString.append(" , updated_date=CURRENT_TIMESTAMP()");
		queryString.append(" , deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE hardware_id=");
		queryString.append(projectHardware.getHardwareId());
		queryString.append(" AND project_ID=");
		queryString.append(projectHardware.getProjectId());
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectManagerId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateHardware = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while updating Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
		}
		return updateHardware;

	}

	public int getHardwareId(String hardwareName) throws SQLException, PropertyVetoException {

		int hardwareId = 0;
		HardwareMaster hardware = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT hardware_id, hardware_type ");
		queryString.append("FROM hardware_master ");
		queryString.append("WHERE hardware_type=?");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, hardwareName);
			rs = stmt.executeQuery();
			while (rs.next()) {
				hardware = new HardwareMaster();
				hardware.setHardwareId(rs.getInt(1));
				hardware.setHardwareType(rs.getString(2));
				hardwareId = hardware.getHardwareId();
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching Name of Hardware :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return hardwareId;
	}

	public ProjectHardware getHardware(int hardwareId) {

		return null;
	}

	public ProjectHardware getHardware(int hardwareId, int projectId) throws SQLException, PropertyVetoException {

		ProjectHardware projectHardware = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT Hardware_ID, Project_ID ");
		queryString.append("FROM Project_Hardware ");
		queryString.append("WHERE Hardware_ID=?");
		queryString.append(" AND Project_ID=?");

		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, hardwareId);
			stmt.setInt(2, projectId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				projectHardware = new ProjectHardware();
				projectHardware.setHardwareId(rs.getInt(1));
				projectHardware.setProjectId(rs.getInt(2));
			}
		} catch (Exception e) {
			log.error("Got Exception while get Role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return projectHardware;
	}

	public boolean checkDuplicateRecForProjectHardware(int projectId, int hardwareId, int units)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		boolean status = false;
		queryString.append("SELECT COUNT(*)");
		queryString.append(" FROM Project_Hardware");
		queryString.append(" WHERE Hardware_ID=?");
		queryString.append(" AND Project_ID=?");
		queryString.append(" AND deleted_by IS NULL");
		// queryString.append(" AND updated_by IS NULL");queryString.append(" AND
		// no_of_units=?");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, hardwareId);
			statement.setInt(2, projectId);
			/* statement.setInt(3, units); */
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for project hardware details:", e);
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return status;

	}

	public ArrayList<ProjectHardware> getProjectHardwareList1() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		ArrayList<ProjectHardware> list = new ArrayList<ProjectHardware>();
		ProjectHardware projectHardware = new ProjectHardware();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		queryString.append(" SELECT DISTINCT hardware_type");
		queryString.append(" FROM hardware_master");
		queryString.append(" WHERE deleted_by IS NULL ");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				projectHardware = new ProjectHardware();
				projectHardware.setType(rs.getString(1));
				list.add(projectHardware);
			}
		} catch (Exception e) {
			log.info("got exception while fecthing hardware list :" + e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
		}
		return list;
	}
}
