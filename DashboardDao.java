package ai.rnt.pins.dao;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.Alerts;
import ai.rnt.pins.model.Customer;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.EffortsStatus;
import ai.rnt.pins.model.MilestoneMaster;
import ai.rnt.pins.model.ProjectHealth;
import ai.rnt.pins.model.Project;
import ai.rnt.pins.util.Util;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class DashboardDao {

	Customer Customer = new Customer();
	Project Project = new Project();

	ProjectHealth status = new ProjectHealth();

	Util util = new Util();

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(DashboardDao.class);

	public Dashboard getEmployeeDetails(int staffID, String password) throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();

		
		dashboard.setStaffID(staffID);
		
		ResultSet rs = null;
		PreparedStatement statement =null;
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		StringBuffer queryString = new StringBuffer();
		queryString.append("select Staff_ID,F_name,M_Name,L_Name,Manager_ID,email_ID,");
		queryString.append("(select email_ID from employee_master as em where em.Staff_ID = em1.Manager_ID)");
		queryString.append(" from  employee_master as em1 where staff_id =?");

		if (!password.equals("for email purpose")) {
			queryString.append(" and password =?");
		}
		try {
		
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			statement.setString(2, password);
			rs = statement.executeQuery();
			while (rs.next()) {
				dashboard.setFirstName(rs.getString(2));
				dashboard.setMiddleName(rs.getString(3));
				dashboard.setLastName(rs.getString(4));
				dashboard.setManagerID(rs.getInt(5));
				dashboard.setEmailId(rs.getString(6));
				dashboard.setManagerEmailID(rs.getString(7));
				
			}
		} finally {
			
			rs.close();
			statement.close();
			connection.close();
		}
		return dashboard;
	}

	
	public Boolean checkAdmin(int staffID) throws SQLException, PropertyVetoException {

		boolean isAdmin = false;
		ResultSet rs = null;
		PreparedStatement statement =null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(*) from admin_info where staff_ID=?");
		try { 
			
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					isAdmin = true;
				}
			}
		} finally {
			
			rs.close();
			statement.close();
			connection.close();
		}
		return isAdmin;
	}

	public boolean isManager(int staffID) throws SQLException, PropertyVetoException {

		boolean isManager = false;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("select count(*) from employee_master where manager_ID =?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, staffID);
			rs = statement.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					isManager = true;
				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return isManager;
	}

	public ArrayList<Project> getTopFourList() throws SQLException, PropertyVetoException {

		ArrayList<Project> projectlist = new ArrayList<Project>();
		Project project = null;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"SELECT c.customer_ID,c.company_name,p.project_ID,p.Project_name,p.efforts,p.efforts_unit FROM customer as c INNER JOIN project as p ");
		queryString.append("WHERE c.Customer_ID = p.Customer_ID ");
		queryString.append("AND Customer_status = 'A' ");
		queryString.append("AND project_status = 'A' ");
		Connection connection = DBConnect.getConnection();
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {
				project = new Project();
				project.setCustomerId(rs.getInt(1));
				project.setCustomerName(rs.getString(2));
				project.setProjectId(rs.getInt(3));
				project.setProjectName(rs.getString(4));
				if (rs.getString(6).charAt(0) == 'D')
					project.setEfforts(util.getFinalConverted(rs.getInt(5)));
				else
					project.setEfforts(rs.getInt(5));

				projectlist.add(project);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Top Four Customer and project :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}

		return projectlist;
	}

	public int getActiveCustomers() throws SQLException, PropertyVetoException {

		int customer = 0;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select count(*) ");
		queryString.append("from customer ");
		queryString.append("where Customer_Status = 'A' AND deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {
				customer = rs.getInt(1);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of  Active Customer :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return customer;
	}

	public int getActiveProjects() throws SQLException, PropertyVetoException {

		int activeProjects = 0;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select  count(*) ");
		queryString.append("from project ");
		queryString.append("WHERE Project_Status = 'A' AND deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery(queryString.toString());
			
			while (rs.next()) {
				activeProjects = rs.getInt(1);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Active Project ::  ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
	
		}
		return activeProjects;
	}

	public ArrayList<Project> getActiveProjectsList() throws SQLException, PropertyVetoException {

		ArrayList<Project> activeprojects = new ArrayList<Project>();
		Project project = null;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(
				" SELECT p.Project_ID,p.Project_Name,c.Company_Name,p.Start_Date,p.Project_Manager,c.Customer_ID ");
		queryString.append(" FROM project as p inner join customer as c ");
		queryString.append(" WHERE p.customer_ID=c.Customer_ID AND Project_Status = 'A' AND p.deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt(1));
				project.setProjectName(rs.getString(2));
				project.setCustomerName(rs.getString(3));
				project.setStartDate(rs.getDate(4));
				project.setProjectmanager(rs.getString(5));
				project.setCustomerId(rs.getInt(6));
				activeprojects.add(project);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Active Project :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return activeprojects;
	}

	public ArrayList<MilestoneMaster> getUpcomingMilestones() throws SQLException, PropertyVetoException {

		ArrayList<MilestoneMaster> upcomingMilestones = new ArrayList<MilestoneMaster>();
		MilestoneMaster project = null;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("select p.Project_Name,m1.milestone,m2.Milestone_Date ");
		queryString.append("from project as p inner join project_milestone as m2 inner join milestone_master as m1 ");
		queryString.append(
				"where m1.milestone_id = m2.milestone_id AND m2.Project_ID = p.Project_ID AND Milestone_Date > CURDATE() ");
		queryString.append("order by Milestone_Date LIMIT 5");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {
				project = new MilestoneMaster();
				project.setProjectName(rs.getString(1));
				project.setMilestoneName(rs.getString(2));
				project.setMilestoneDate(rs.getDate(3));

				upcomingMilestones.add(project);


			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of upcoming Milestone :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return upcomingMilestones;
	}

	public ArrayList<ProjectHealth> getProjectHealth() throws SQLException, PropertyVetoException {

		ArrayList<ProjectHealth> healthStatus = new ArrayList<ProjectHealth>();
		ProjectHealth projectHealth = null;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("select p.Project_Name,m.Milestone_Date,m.Milestone_met ");
		queryString.append("from project as p inner join project_milestone as m ");
		queryString.append("where p.project_ID=m.project_ID AND Milestone_met='N' AND Milestone_Date < CURDATE()");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {

				projectHealth = new ProjectHealth();
				projectHealth.setProjectName(rs.getString(1));
				projectHealth.setVariance(util.getduration(rs.getDate(2)));
				healthStatus.add(projectHealth);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching project Health :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return healthStatus;
	}

	public ArrayList<Alerts> getAlerts() throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Alerts alerts = null;
		PreparedStatement statement=null;
		ArrayList<Alerts> alertslist = new ArrayList<Alerts>();
		StringBuffer queryString = new StringBuffer();

		queryString.append("select p.Project_Name,c.Company_Name,c.MSA_signed,c.NDA_signed,p.SOW_signed ");
		queryString.append("from project as p inner join customer as c ");
		queryString.append("where p.Customer_id = c.Customer_id ");
		queryString.append("AND (NDA_signed='N' OR MSA_signed='N' OR SOW_signed='N')");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {

				alerts = new Alerts();

				if (rs.getString(3).charAt(0) == 'N' || rs.getString(4).charAt(0) == 'N'
						|| rs.getString(5).charAt(0) == 'N') {

					alerts.setProjectName(rs.getString(1));
					alerts.setCompanyName(rs.getString(2));

					if (rs.getString(3).charAt(0) == 'N') {
						alerts.setAlertMSA("MSA not Signed");
					}
					if (rs.getString(4).charAt(0) == 'N') {
						alerts.setAlertNDA("NDA not Signed");
					}
					if (rs.getString(5).charAt(0) == 'N') {
						alerts.setAlertSOW("SOW not Signed");
					}

				}
				alertslist.add(alerts);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching Alerts :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return alertslist;
	}

	public ArrayList<EffortsStatus> getEffortsStatus() throws SQLException, PropertyVetoException {

		ArrayList<EffortsStatus> healthStatus = new ArrayList<EffortsStatus>();
		EffortsStatus effortstatus = null;
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();

		queryString.append("select p.Project_Name, p.Efforts, p.efforts_unit, a.effort_hours, a.effort_minutes ");
		queryString.append("from project as p INNER JOIN emp_timesheets as a ");
		queryString.append("where p.Project_ID=a.Project_ID");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			
			while (rs.next()) {

				effortstatus = new EffortsStatus();

				effortstatus.setProjectName(rs.getString(1));
				double actual = rs.getInt(4) + (double) (rs.getInt(5)) / 60;

				if (rs.getString(3).charAt(0) == 'D')
					effortstatus.setFinalEfforts(util.getFinalConverted(rs.getInt(2)));

				if (actual > rs.getInt(2)) {
					effortstatus.setVariance(util.getefforts(actual, rs.getInt(2)));
					healthStatus.add(effortstatus);
				}

			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching Efforts Status ::  ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return healthStatus;

	}

	public ModelAndView loadforgotPasswordPage(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, MessagingException {

		return new ModelAndView("forgotpassword");
	}

	public String getPassword(String emailID) throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		PreparedStatement statement=null;
		Connection connection = DBConnect.getConnection();
		String password = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT Password FROM employee_master WHERE email_ID=?");
		try {

			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, emailID);
			rs = statement.executeQuery();
			
			while (rs.next()) {
				password = rs.getString(1);
			}
		} catch (Exception e) {
			log.error("Got Exception while Fetching  password :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return password;

	}

}