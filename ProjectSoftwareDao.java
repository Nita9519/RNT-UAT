package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.ProjectSoftware;
import ai.rnt.pins.model.SoftwareMaster;

public class ProjectSoftwareDao {

	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;
	private static final Logger log = LogManager.getLogger(ProjectSoftware.class);

	public ArrayList<ProjectSoftware> getSoftwareMasterList() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement= null;
		ArrayList<ProjectSoftware> list = new ArrayList<ProjectSoftware>();
		ProjectSoftware projectSoftware = new ProjectSoftware();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT software_id,software_type,software_vendor,product_name,product_version ");
		queryString.append("FROM software_master ");
		queryString.append("WHERE deleted_by IS NULL ");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				projectSoftware = new ProjectSoftware();
				projectSoftware.setSoftwareId(rs.getInt(1));
				projectSoftware.setType(rs.getString(2));
				projectSoftware.setVendor(rs.getString(3));
				projectSoftware.setProduct(rs.getString(4));
				projectSoftware.setVersion(rs.getString(5));

				list.add(projectSoftware);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		

		}
		return list;
	}

	public ArrayList<ProjectSoftware> getProjectSoftwareList(int projectId) throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<ProjectSoftware> list = new ArrayList<ProjectSoftware>();
		ProjectSoftware projectSoftware = new ProjectSoftware();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT ps.project_id, sm.software_id, sm.software_type,sm.software_vendor,sm.product_name, sm.product_version, ps.no_of_license ");
		queryString.append("FROM software_master as sm ");
		queryString.append("INNER JOIN Project_Software as ps ");
		queryString.append("where ps.software_id=sm.software_id AND ps.deleted_by is NULL ");
		queryString.append("AND ps.project_id=?");
		Connection connection = DBConnect.getConnection();
		Statement statement = connection.createStatement();
		System.out.println("***************************");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, projectId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				projectSoftware = new ProjectSoftware();
				projectSoftware.setProjectId(rs.getInt(1));
				projectSoftware.setSoftwareId(rs.getInt(2));
				projectSoftware.setType(rs.getString(3));
				projectSoftware.setVendor(rs.getString(4));
				projectSoftware.setProduct(rs.getString(5));
				projectSoftware.setVersion(rs.getString(6));
				projectSoftware.setNo_of_license(rs.getInt(7));
				list.add(projectSoftware);

			}

		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return list;
	}

	public Boolean addSoftware(ProjectSoftware projectSoftware, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSoftware = false;
		try {

			ProjectSoftware projectSoft = getSoftware(projectSoftware.getSoftwareId(), projectSoftware.getProjectId());
			if (projectSoft == null) {
				Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
				StringBuffer queryString = new StringBuffer();
				queryString.append(
						"INSERT INTO Project_Software(project_id, software_id,no_of_license, created_by, created_date) VALUES (");
				queryString.append("?,?,?,");
				queryString.append(adminId + ",");
				queryString.append("CURRENT_TIMESTAMP())");

				stmt = connection.prepareStatement(queryString.toString());
				stmt.setInt(1, projectSoftware.getProjectId());
				stmt.setInt(2, projectSoftware.getSoftwareId());
				stmt.setInt(3, projectSoftware.getNo_of_license());
				projectSoftware.setCreatedBy(adminId);
				projectSoftware.setCreatedDate(timeStamp);
				int rowUpdated = stmt.executeUpdate();
				if (rowUpdated > 0) {
					log.info("Project software added successfully");
					addSoftware = true;
				}
			} else {
				updateSoftware(adminId, projectSoftware);
			}
		} finally {
			if (stmt != null)
				stmt.close();
			if (connection != null)
				connection.close();
		}

		return addSoftware;

	}

	public Boolean deleteSoftware(int softwareId, int projectManagerId, int projectId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSoftware = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append("DELETE FROM Project_Software  ");
		queryString.append("WHERE software_id=?");
		queryString.append(" AND Project_ID=?");
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, softwareId);
			stmt.setInt(2, projectId);
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

	public Boolean updateSoftware(int adminId, ProjectSoftware projectSoftware)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSoftware = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE Project_Software SET ");
		queryString.append(" No_of_license=?");
		queryString.append(",updated_by=?");
		queryString.append(",updated_date=CURRENT_TIMESTAMP(),");
		queryString.append("deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE software_id="+projectSoftware.getSoftwareId());
		queryString.append(" AND Project_ID="+projectSoftware.getProjectId());

		try {
			stmt = connection.prepareStatement(queryString.toString());

			stmt.setInt(1, projectSoftware.getNo_of_license());
			stmt.setInt(2, adminId);
			
			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateSoftware = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateSoftware;
	}

	public Boolean updateAddedSoftware(int adminId, ProjectSoftware projectSoftware)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSoftware = false;

		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE Project_Software SET ");
		queryString.append("updated_by="+adminId);
		queryString.append(",updated_date=CURRENT_TIMESTAMP()" + ", ");
		queryString.append("deleted_by=NULL, deleted_date=NULL");
		queryString.append(" WHERE software_id=?");
		queryString.append(" AND Project_ID=?");

		try {
			stmt = connection.prepareStatement(queryString.toString());
		
			stmt.setInt(1, projectSoftware.getSoftwareId());
			stmt.setInt(2, projectSoftware.getProjectId());
			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				log.info("Udpate Successfully");
				updateSoftware = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateSoftware;
	}

	public int getSoftwareId(String softwareName) throws SQLException, PropertyVetoException {

		int softwareId = 0;
		SoftwareMaster software = null;
		PreparedStatement stmt = null;

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT software_id, software_type ");
		queryString.append("FROM software_master ");
		queryString.append("WHERE software_type=?");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, softwareName);
			rs = stmt.executeQuery();

			while (rs.next()) {

				software = new SoftwareMaster();
				software.setSoftwareId(rs.getInt(1));
				software.setSoftwareType(rs.getString(2));

				softwareId = software.getSoftwareId();

			}

		} catch (Exception e) {
			log.error("Got Exception get software Id Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}

		return softwareId;
	}

	public ProjectSoftware getSoftware(int softwareId, int projectId) throws SQLException, PropertyVetoException {

		ProjectSoftware projectSoftware = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;

		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT Software_ID, Project_ID ");
		queryString.append("FROM Project_Software ");
		queryString.append("WHERE Software_ID=?");
		queryString.append(" AND Project_ID=?");

		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, softwareId);
			stmt.setInt(2, projectId);
			rs = stmt.executeQuery();

			while (rs.next()) {

				projectSoftware = new ProjectSoftware();
				projectSoftware.setSoftwareId(rs.getInt(1));
				projectSoftware.setProjectId(rs.getInt(2));

			}

		} catch (Exception e) {
			log.error("Got Exception while get Role Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}

		return projectSoftware;

	}

	public boolean checkDuplicateRecForProjectSoftware(int projectId, int softwareId, int noOfLicense)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		queryString.append("SELECT COUNT(*)");
		queryString.append(" FROM Project_Software");
		queryString.append(" WHERE Software_ID=?");
		queryString.append(" AND Project_ID=?");
		queryString.append(" AND deleted_by IS NULL");
		//queryString.append(" AND updated_by IS NULL AND No_of_license=?");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, softwareId);
			stmt.setInt(2, projectId);
			/* stmt.setInt(3, noOfLicense); */
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for project software details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}
		return status;

	}

	public ArrayList<ProjectSoftware> getProjectSoftwareList1() throws SQLException, PropertyVetoException {
		ResultSet rs = null;
		ArrayList<ProjectSoftware> list = new ArrayList<ProjectSoftware>();
		ProjectSoftware projectSoftware = new ProjectSoftware();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		
		queryString.append(" SELECT DISTINCT software_type");
		queryString.append(" from software_master");
		queryString.append(" WHERE deleted_by IS NULL");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			
			rs = stmt.executeQuery();

			while (rs.next()) {
				projectSoftware = new ProjectSoftware();	
				projectSoftware.setType(rs.getString(1));
				list.add(projectSoftware);

			}

		}catch (Exception e) {
			log.info("got exception while fecthing software list :" + e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();

		}
		return list;
	}
}