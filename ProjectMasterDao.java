package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.Manager;

import ai.rnt.pins.model.MilestoneMaster;
import ai.rnt.pins.model.Project;
import ai.rnt.pins.model.ProjectView;
import ai.rnt.pins.model.ServiceTypeMaster;

public class ProjectMasterDao {
	private static final Logger log = LogManager.getLogger(ProjectMasterDao.class);

	// sachit
	public ArrayList<Project> getProjectNames() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		ArrayList<Project> list = new ArrayList<Project>();
		Project project = new Project();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT c.customer_id,project_id,company_name,project_name ");
		queryString.append("FROM customer c join project p ");
		queryString.append("ON c.customer_id=p.customer_id ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setProjectId(rs.getInt(2));
				project.setCompanyName(rs.getString(3));
				project.setProjectName(rs.getString(4));
				list.add(project);
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list;
	}

	public ProjectView getCustProjectDetails(int projectId) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement = null;
		ProjectView projectView = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT c.customer_id,project_id,company_name,project_name,domain_name,Project_Manager,p.Start_Date,p.end_date, technology,efforts, efforts_unit ");
		queryString.append("FROM customer c join project p ");
		queryString.append("ON c.customer_id=p.customer_id ");
		queryString.append("AND project_id=?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				projectView = new ProjectView();
				projectView.setCustomerId(rs.getInt(1));
				projectView.setProjectId(rs.getInt(2));
				projectView.setCompanyName(rs.getString(3));
				projectView.setProjectName(rs.getString(4));
				projectView.setDomainName(rs.getString(5));
				projectView.setProjectmanager(rs.getString(6));
				projectView.setStartDate(rs.getDate(7));
				projectView.setEndDate(rs.getDate(8));
				projectView.setTechnology(rs.getString(9));
				projectView.setEfforts(rs.getInt(10));
				projectView.setUnit(rs.getString(11));
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return projectView;
	}

	public ArrayList<Project> getProjectDetails(int projectId) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		System.out.println("inside get project ");
		PreparedStatement statement = null;
		ArrayList<Project> list2 = new ArrayList<Project>();
		StringBuffer queryString = new StringBuffer();
		Project project = null;
		queryString.append(
				"SELECT c.customer_id,project_id,c.company_name,p.project_name,p.Start_Date,efforts, efforts_unit ,p.End_Date,c.domain_name,p.Project_Manager ");
		queryString.append("FROM customer c join project p ");
		queryString.append("ON c.customer_id=p.customer_id ");
		queryString.append("AND project_id=?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setProjectId(rs.getInt(2));
				project.setCompanyName(rs.getString(3));
				project.setProjectName(rs.getString(4));
				project.setStartDate(rs.getDate(5));
				project.setEfforts(rs.getInt(6));
				project.setEffortsUnit(rs.getString(7));
				project.setEndDate(rs.getDate(8));
				project.setDomainName(rs.getString(9));
				//project.setLastName(rs.getString(9));
				project.setProjectManagerId(rs.getInt(10));
				list2.add(project);
				System.out.println("list is" + list2);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching Project Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return list2;
	}

	// for all projectlist
	public ArrayList<Project> getProjectList(int customerId) throws SQLException, PropertyVetoException {
		Project project = null; // obj of model class

		ArrayList<Project> listOfProject = new ArrayList<Project>();
		PreparedStatement statement = null;
		ResultSet rs = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append("Select p.Customer_ID, p.Project_ID, p.Project_Name, p.Service_Id, p.Engagement_Model,"
				+ "p.Efforts, p.Efforts_Unit, p.Start_Date,p.End_Date, p.Execution_Model, p.Project_Manager,"
				+ "p.Percent_Of_Allocation_Of_Manager, p.Project_Status, p.SOW_signed,e.F_name,e.L_name,e.Staff_Id, "
				+ "c.Company_Name FROM project AS p INNER JOIN employee_master AS e INNER JOIN customer AS c  ");
		queryString.append("WHERE p.project_manager=e.Staff_Id AND c.Customer_ID = p.Customer_ID AND  p.customer_Id =");
		queryString.append(customerId);

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setProjectId(rs.getInt(2));
				project.setProjectName(rs.getString(3));
				project.setServiceTypeId(rs.getInt(4));
				project.setEngagementModel(rs.getString(5));
				project.setEfforts(rs.getInt(6));
				project.setEffortsUnit(rs.getString(7));
				project.setStartDate(rs.getDate(8));
				project.setEndDate(rs.getDate(9));
				project.setExecutionModel(rs.getString(10));
				project.setProjectManagerId(rs.getInt(11));
				project.setPercentOfAllocation(rs.getInt(12));
				project.setStatusOfProject(rs.getString(13));
				project.setStatementOfWork(rs.getString(14));
				project.setFirstName(rs.getString(15));
				project.setLastName(rs.getString(16));
				project.setStaffID(rs.getInt(17));

				project.setCompanyName(rs.getString(18));
				listOfProject.add(project);

			}

		} catch (Exception e) {
			log.error("Got Exception while Project List :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return listOfProject;
	}

	public ArrayList<Project> getProjectActiveList() throws SQLException, PropertyVetoException {
		Project project = null; // obj of model class

		ArrayList<Project> listOfProject = new ArrayList<Project>();
		PreparedStatement statement = null;
		ResultSet rs = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append("Select c.Company_Name,p.Customer_ID, p.Project_ID, p.Project_Name, ");
		queryString.append(" p.Start_Date, p.Project_Manager,");
		queryString.append(" p.Project_Status ");
		queryString.append(" FROM project AS p INNER JOIN employee_master AS e INNER JOIN customer AS c ");
		queryString.append(
				" WHERE p.project_manager=e.Staff_Id AND c.Customer_ID = p.Customer_ID AND p.Project_Status = 'A' AND p.deleted_by IS NULL");

		Connection connection = DBConnect.getConnection();
//p.Project_Manager=e.Staff_ID AND 
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setCustomerName(rs.getString(1));
				project.setCustomerId(rs.getInt(2));
				project.setProjectId(rs.getInt(3));
				project.setProjectName(rs.getString(4));
				project.setStartDate(rs.getDate(5));
				project.setProjectmanager(rs.getString(6));
				project.setStatusOfProject(rs.getString(7));
				listOfProject.add(project);
			}

		} catch (Exception e) {
			log.error("Got Exception while Project List :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return listOfProject;
	}

	public ArrayList<ServiceTypeMaster> getListOfserviceTypes() throws SQLException, PropertyVetoException {
		ServiceTypeMaster serviceType = new ServiceTypeMaster();
		ArrayList<ServiceTypeMaster> serviceList = new ArrayList<ServiceTypeMaster>();
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT service_id, service_type ");
		queryString.append("FROM service_type_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				serviceType = new ServiceTypeMaster();
				serviceType.setServiceTypeId(rs.getInt(1));
				serviceType.setServiceTypeName(rs.getString(2));
				serviceList.add(serviceType);
			}
		} catch (Exception e) {
			log.error("Got Exception while List of Service Type ::  ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return serviceList;
	}

	public Project insertProjectDetails(Project project, int customerId, int adminId)
			throws SQLException, PropertyVetoException {

		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			queryString.append(
					" INSERT INTO project( Customer_ID, Project_Name, Service_Id, Engagement_Model, Efforts, Efforts_unit, Start_Date,");
			queryString.append(
					"End_Date, Execution_Model, Project_Manager, Percent_Of_Allocation_Of_Manager, Project_Status, SOW_Signed");
			queryString.append(",created_by,created_date)");
			queryString.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?, ");
			queryString.append(adminId);
			queryString.append(",CURRENT_TIMESTAMP() ");
			queryString.append(")");

			statement = connection.prepareStatement(queryString.toString());

			statement.setInt(1, customerId);

			statement.setString(2, project.getProjectName());
			statement.setInt(3, project.getServiceTypeId());
			statement.setString(4, project.getEngagementModel());
			statement.setInt(5, project.getEfforts());

			if (project.getEffortsUnit().equals("Days"))
				statement.setString(6, "Days");
			else
				statement.setString(6, "Hours");
			statement.setDate(7, (Date) project.getStartDate());
			statement.setDate(8, (Date) project.getEndDate());
			statement.setString(9, project.getExecutionModel());
			statement.setInt(10, project.getProjectManagerId());
			statement.setInt(11, project.getPercentOfAllocation());

			if (project.getStatusOfProject().equals("Active"))
				statement.setString(12, "A");
			else if (project.getStatusOfProject().equals("OnHold"))
				statement.setString(12, "H");
			else
				statement.setString(12, "C");

			if (project.getStatementOfWork().equals("Yes"))
				statement.setString(13, "Y");
			else
				statement.setString(13, "N");
			// statement.setInt(14, customerId);
			project.setCreatedBy(adminId);
			project.setCreatedDate(timestamp);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				log.info("Project DETAILS inserted successfully!");
			}

		} catch (Exception e) {
			log.error("Got Exception while inserting project Details :: ", e);
			// log.error("Got an Exception "+e.getMessage());
			// e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return project;

	}

	public ArrayList<MilestoneMaster> getListOfMilestones() throws SQLException, PropertyVetoException {
		MilestoneMaster milestone = new MilestoneMaster();

		ArrayList<MilestoneMaster> milestoneList = new ArrayList<MilestoneMaster>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT milestone_id, milestone ");
		queryString.append("FROM milestone_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery(queryString.toString());
			while (rs.next()) {
				milestone = new MilestoneMaster();
				milestone.setMilestoneId(rs.getInt(1));
				milestone.setMilestoneName(rs.getString(2));
				milestoneList.add(milestone);
			}
		} catch (Exception e) {
			log.error("Got Exception while fetching Milestone Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return milestoneList;
	}

	public ArrayList<MilestoneMaster> getAllMilestoneDetails(int projectid) throws SQLException, PropertyVetoException {

		ResultSet rs = null;

		ArrayList<MilestoneMaster> allMilestoneDetails = new ArrayList<MilestoneMaster>();
		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		MilestoneMaster milestone = null;

		queryString.append("select pm.Milestone_Date, mm.Milestone ,pm.milestone_ID from milestone_master as mm "
				+ "INNER JOIN project_milestone as pm where mm.milestone_id=pm.milestone_ID and pm.project_Id=?");
		queryString.append(" AND pm.deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectid);
			rs = statement.executeQuery();
			while (rs.next()) {
				milestone = new MilestoneMaster();
				milestone.setMilestoneDate(rs.getDate(1));
				milestone.setMilestoneName(rs.getString(2));
				milestone.setMilestoneId(rs.getInt(3));
				allMilestoneDetails.add(milestone);
			}
		} catch (Exception e) {
			log.error("Got Exception while fetching All Milestone Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return allMilestoneDetails;

	}

	public boolean updateProjectDetails(Project project, int adminId) throws SQLException, PropertyVetoException {

		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		boolean status = false;
		Connection connection = DBConnect.getConnection();
		queryString.append(" UPDATE project  SET Project_Name = ?, Service_Id = ?, Engagement_Model = ? ,");
		queryString.append(" Efforts = ? , Efforts_unit = ? , Start_Date = ? , End_Date = ? ,");
		queryString.append(" Execution_Model = ? , Project_Manager = ? ,");
		queryString.append(" Percent_Of_Allocation_Of_Manager = ? , Project_Status = ? , Sow_Signed = ? ,");
		queryString.append(" updated_date = CURRENT_TIMESTAMP(), updated_by =  ");
		queryString.append(adminId);
		queryString.append(" WHERE project_ID= ");
		queryString.append(project.getProjectId());
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, project.getProjectName());
			statement.setInt(2, project.getServiceTypeId());
			statement.setString(3, project.getEngagementModel());
			statement.setInt(4, project.getEfforts());

			if (project.getEffortsUnit().equals("Days"))
				statement.setString(5, "Days");
			else
				statement.setString(5, "Hours");
			statement.setDate(6, project.getStartDate());
			statement.setDate(7, project.getEndDate());

			statement.setString(8, project.getExecutionModel());
			// statement.setString(7, project.getProjectmanager());
			statement.setInt(9, project.getProjectManagerId());
			statement.setInt(10, project.getPercentOfAllocation());

			if (project.getStatusOfProject().equals("Active"))
				statement.setString(11, "A");
			else if (project.getStatusOfProject().equals("OnHold"))
				statement.setString(11, "H");
			else
				statement.setString(11, "C");

			if (project.getStatementOfWork().equals("Y")) { // error here

				statement.setString(12, "Y");
			} else {

				statement.setString(12, "N");
			}

			project.setCreatedBy(adminId);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0)
				status = true;

		} catch (Exception e) {
			log.error("Got Exception while updating project Details :: ", e);
			// e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return status;
	}

	public int getprojectIdforMilestone(int customerId, Project project) throws SQLException, PropertyVetoException {

		int ProjectId = 0;
PreparedStatement statement=null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select Customer_ID,Project_ID,Project_Name ");
		queryString.append("from project ");
		queryString.append(" WHERE Project_Name =?");
		queryString.append(" AND CUSTOMER_ID=?");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, project.getProjectName());
			statement.setInt(2, customerId);
			rs = statement.executeQuery();

			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setProjectId(rs.getInt(2));
				project.setProjectName(rs.getString(3));
				ProjectId = project.getProjectId();

			}

		} catch (Exception e) {
			log.error("Got Exception while fetching projectID  for Milestone  :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return ProjectId;
	}

	public boolean insertMilestoneDetails(MilestoneMaster milestoneMaster, int ProjectId)
			throws SQLException, PropertyVetoException {
		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		boolean status = false;
		queryString.append(" INSERT INTO project_milestone( Milestone_ID, Milestone_Date, Project_ID )");
		queryString.append(" VALUES (?,?,?)");
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, milestoneMaster.getMilestoneId());
			statement.setDate(2, milestoneMaster.getMilestoneDate());
			statement.setInt(3, ProjectId);
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				status = true;
				log.info(" DETAILS inserted successfully!");
			}

		} catch (Exception e) {
			log.error("Got Exception while inserting Milestone Details :: ", e);
			// e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return status;
	}

	// for display only single project
	public Project getProject(int projectId) throws SQLException, PropertyVetoException {
		Project project = new Project();
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("SELECT p.Customer_ID, p.Project_ID,p.Project_Name, p.Engagement_Model,");
		queryString.append(
				"p.Efforts, p.Efforts_Unit, p.Start_Date,p.End_Date, p.Execution_Model, p.Percent_Of_Allocation_Of_Manager, p.Project_Status, ");
		queryString.append("p.SOW_signed,e.F_name, e.L_name,e.staff_id, s.Service_Id, s.service_type ");
		queryString.append(
				"from project AS p INNER JOIN service_type_master AS s INNER JOIN employee_master AS e WHERE p.service_Id=s.service_Id AND ");
		queryString.append("p.project_manager=e.staff_id AND project_id=?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectId);
			rs = statement.executeQuery();
			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setProjectId(rs.getInt(2));
				project.setProjectName(rs.getString(3));
				project.setEngagementModel(rs.getString(4));
				project.setEfforts(rs.getInt(5));
				project.setEffortsUnit(rs.getString(6));
				log.info("effort==="+project.getEffortsUnit());
				project.setStartDate(rs.getDate(7));
				project.setEndDate(rs.getDate(8));
				project.setExecutionModel(rs.getString(9));
				project.setPercentOfAllocation(rs.getInt(10));
				project.setStatusOfProject(rs.getString(11));
				project.setStatementOfWork(rs.getString(12));
				project.setFirstName(rs.getString(13));
				project.setLastName(rs.getString(14));
				project.setStaffID(rs.getInt(15));
				project.setServiceTypeId(rs.getInt(16));
				project.setServiceTypeName(rs.getString(17));
			}

			project.setMilestoneList(getAllMilestoneDetails(projectId));

		} catch (Exception e) {
			log.error("Got Exception while Fetching project Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return project;
	}

	// only manager not staff
	public ArrayList<Manager> getListOfEmployee() throws SQLException, PropertyVetoException {
		// Location location = null;
		Manager manager = null;

		ArrayList<Manager> employeeList = new ArrayList<Manager>();
		// ArrayList<Location> locationList = new ArrayList<Location>();
		log.info("Inside list of manager getListOfemployee()");
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(
				" Select F_Name, L_name, Staff_ID from employee_master ");
		queryString.append( "WHERE deleted_by IS NULL AND staff_ID In (Select Manager_ID from employee_master) ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				manager = new Manager();
				manager.setFirstName(rs.getString(1));
				manager.setLastName(rs.getString(2));
				manager.setStaffID(rs.getInt(3));
				employeeList.add(manager);
			}

		} catch (Exception e) {
			log.error("Got Exception while List Of Employee Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return employeeList;
	}
	// for insert milestone
	public int getMilestoneId(String milestoneName) throws SQLException, PropertyVetoException {

		int milestoneId = 0;
		MilestoneMaster milestone = null;
PreparedStatement statement=null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT milestone_id,milestone FROM milestone_master ");
		queryString.append("WHERE milestone =?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, milestoneName);
			rs = statement.executeQuery();
			while (rs.next()) {
				milestone = new MilestoneMaster();
				milestone.setMilestoneId(rs.getInt(1));
				milestone.setMilestoneName(rs.getString(2));
				milestoneId = milestone.getMilestoneId();

			}

		} catch (Exception e) {
			log.error("Got Exception while Milestone Name Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return milestoneId;
	}

	public ServiceTypeMaster getServiceTypeName(int serviceTypeId) throws SQLException, PropertyVetoException {

		ServiceTypeMaster serviceType = null;
PreparedStatement statement=null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT service_type FROM service_type_master ");
		queryString.append("WHERE service_id =?");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, serviceTypeId);
			rs = statement.executeQuery();
			while (rs.next()) {
				serviceType = new ServiceTypeMaster();
				serviceType.setServiceTypeName(rs.getString(1));
			}
		} catch (Exception e) {
			log.error("Got Exception while service type name Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return serviceType;
	}

	public Manager getManagerName(int projectManagerId) throws SQLException, PropertyVetoException {

		Manager manager = null;
PreparedStatement statement=null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT F_name, L_name, Staff_Id FROM Employee_master ");
		queryString.append("WHERE Staff_id =?");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, projectManagerId);
			rs = statement.executeQuery();

			while (rs.next()) {
				manager = new Manager();

				manager.setFirstName(rs.getString(1));
				manager.setLastName(rs.getString(2));

			}

		} catch (Exception e) {
			log.error("Got Exception while fetching Manager Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return manager;
	}

}