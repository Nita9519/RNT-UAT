package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.model.User;
import ai.rnt.pins.dao.CustomerDao;
import ai.rnt.pins.dao.ProjectMasterDao;
import ai.rnt.pins.dao.ProjectPeopleSkillsDao;
import ai.rnt.pins.dao.ProjectTeamDao;
import ai.rnt.pins.model.Customer;
import ai.rnt.pins.model.CustomerMasterView;
import ai.rnt.pins.model.Project;
import ai.rnt.pins.model.ProjectPhase;
import ai.rnt.pins.model.ProjectTeam;
import ai.rnt.pins.model.ProjectTeamDetails;
import ai.rnt.pins.model.ProjectTeamSkills;
import ai.rnt.pins.model.ProjectView;

@Controller
public class ProjectTeamController {
	CustomerDao customerDao = new CustomerDao();
	ProjectMasterDao projectDao = new ProjectMasterDao();
	ProjectTeamDao projectTeamDao = new ProjectTeamDao();
	ProjectPeopleSkillsDao projectpeopleskillsDao = new ProjectPeopleSkillsDao();

	private static final Logger log = LogManager.getLogger(ProjectTeamController.class);

	@RequestMapping(value = "/displayteammanagement.do")
	public ModelAndView teamManagementScreen(HttpServletRequest req, HttpServletResponse res) {
		CustomerMasterView masterCustomerScreen = new CustomerMasterView();
		CustomerMasterView masterCustomer = new CustomerMasterView();
		ArrayList<Customer> customerList = null;
		try {
			customerList = customerDao.getlistOfCustomers();
			// System.out.println("after method call of customer");
			masterCustomerScreen.setCustomerDetails(customerList);

			// prepare projectlist
			ArrayList<Project> projectList = projectDao.getProjectNames();

			masterCustomerScreen.setProjectList(projectList);
			// set project list for customer

			ArrayList<ProjectTeamSkills> peopleSkills = projectpeopleskillsDao.getProjectTeamSkillsList(1);
			masterCustomer.setPeopleSkills(peopleSkills);
			return new ModelAndView("pins/teammanagementscreen", "masterCustomerScreen", masterCustomerScreen);

		} catch (Exception e) {
			log.error("Got Exception while fetching list of customer", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

		// return new ModelAndView("pins/teammanagementscreen", "masterCustomerScreen",
		// masterCustomerScreen);
	}

	@RequestMapping(value = "/teammanagement.do")
	public ModelAndView teamManagement(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		CustomerMasterView masterCustomer = new CustomerMasterView();

		String projectId = req.getParameter("projectID");
		int projectid = Integer.parseInt(projectId);
		try {
			masterCustomer.setProjectID(projectid);
			ArrayList<Project> projectDetailsList = projectDao.getProjectDetails(Integer.parseInt(projectId));

			masterCustomer.setProjectDetailsList(projectDetailsList);

			ArrayList<ProjectTeam> projectTeamList = projectTeamDao.getRole();
			masterCustomer.setProjectTeamList(projectTeamList);

			ArrayList<ProjectTeam> projectAssociateList = projectTeamDao.getAssociate();
			masterCustomer.setProjectAssociateList(projectAssociateList);

			ArrayList<ProjectTeamDetails> associateTeamList = projectTeamDao
					.getAllTeamDetails(Integer.parseInt(projectId));
			masterCustomer.setAssociateTeamList(associateTeamList);

			ArrayList<ProjectTeamSkills> peopleSkills = projectpeopleskillsDao
					.getProjectTeamSkillsList(Integer.parseInt(projectId));
			masterCustomer.setPeopleSkills(peopleSkills);

			ArrayList<ProjectTeamSkills> peopleSkillscount = projectpeopleskillsDao
					.getProjectTeamSkillsListforAllocationcount(Integer.parseInt(projectId));
			masterCustomer.setPeopleSkillscount(peopleSkillscount);

			return new ModelAndView("pins/teammanagement", "masterCustomer", masterCustomer);

		} catch (Exception e) {
			log.error("Got Exception while Team management Details ::", e);
			// e.printStackTrace();

			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
		// return new ModelAndView("pins/teammanagement", "masterCustomer",
		// masterCustomer);
	}

	@RequestMapping(value = "/insertprojectteam.do")
	public ModelAndView insertProjectTeam(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		int percentageAllocation = 0;
		java.sql.Date sqlAllocationDate = null;
		java.sql.Date sqlReleaseDate = null;
		String statusMsg = "";

		CustomerMasterView masterCustomer = new CustomerMasterView();
		String buttonAction = (String) req.getParameter("btnAction");

		/** INSERT LOGIC **/
		HttpSession session = req.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			ProjectTeamDetails projectTeamDetails = new ProjectTeamDetails();
			String projectID = req.getParameter("projectId");
			projectTeamDetails.setProjectID(Integer.parseInt(projectID));
			masterCustomer.setProjectID(Integer.parseInt(projectID));
			String associateName = req.getParameter("associateName");
			/* int projectteamID = Integer.parseInt(req.getParameter("projectteamID")); */
			String allocationDate = req.getParameter("startDate");
			String releaseDate = req.getParameter("releaseDate");
			String role = req.getParameter("role");
			int roleId1 = projectTeamDao.getRoleIdForRole(role);
			projectTeamDetails.setRoleID(roleId1);

			try {
				percentageAllocation = Integer.valueOf(req.getParameter("percentageAllocation"));
			} catch (NumberFormatException e) {
			}

			if (allocationDate != null) {
				java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocationDate);
				sqlAllocationDate = new java.sql.Date(uDate.getTime());

			}

			if (releaseDate != null) {
				java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
				sqlReleaseDate = new java.sql.Date(uDate.getTime());

			}
			projectTeamDetails.setAssociateName(associateName);
			projectTeamDetails.setRole(role);
			int roleId = projectTeamDao.getRoleIdForRole(role);
			projectTeamDetails.setRoleID(roleId);
			projectTeamDetails.setAllocationDate(sqlAllocationDate);
			projectTeamDetails.setReleaseDate(sqlReleaseDate);
			projectTeamDetails.setPercentageAllocation(percentageAllocation);

			if (buttonAction.equalsIgnoreCase("Update")) {
				projectTeamDetails.setStaffId(Integer.parseInt(req.getParameter("staffId")));
				int usedPercentageforupdate = projectTeamDao.checkAllocation(projectTeamDetails);
				boolean allocation = false;
				allocation = projectTeamDao.checkAllocationname(projectTeamDetails);
				if (!allocation) {
					int percent = projectTeamDao.checkAllocation(projectTeamDetails);
					log.info("percent=" + percent);
					if (percent <= 100) {
						if (percentageAllocation <= 100) {
							if (projectTeamDao.updateAssociate(projectTeamDetails, adminId))
								statusMsg = "Associate Team information updated successfully";
							else
								statusMsg = "Associate Team information not updated";
						} else {
							statusMsg = "You can not assign more than 100%";
						}
					}
				} else {
					if (usedPercentageforupdate < 100) {
						int remainingPercentage = 100 - usedPercentageforupdate;
						if (percentageAllocation <= remainingPercentage) {
							if (projectTeamDao.updateAssociate(projectTeamDetails, adminId))
								statusMsg = "Associate Team information updated successfully";
							else
								statusMsg = "Associate Team information not updated";
						} else {
							statusMsg = "You can allocate only " + remainingPercentage;
						}
					} else {
						statusMsg = "Associate already utilized 100%";
					}
				}
			} else if (buttonAction.equalsIgnoreCase("Delete")) {
				projectTeamDetails.setStaffId(Integer.parseInt(req.getParameter("staffId")));
				projectTeamDetails.setProjectTeamId(Integer.parseInt(req.getParameter("projectteamID")));
				if (projectTeamDao.deleteAssociate(projectTeamDetails, adminId))
					statusMsg = "Associate Team information deleted successfully";
				else
					statusMsg = "Associate Team information not deleted";

			} else if (buttonAction.equalsIgnoreCase("Save")) {
				ProjectTeamDetails insertprojectTeamDetails = new ProjectTeamDetails();
				String insertprojectId = req.getParameter("projectId");
				int projectIdForInsertion = Integer.parseInt(insertprojectId);
				String count = req.getParameter("peopleCount1");
				int i = 1;
				while (true) {
					if (req.getParameter("addSatffId" + i) == null && req.getParameter("role" + i) == null)
						break;
					int staffID = Integer.parseInt(req.getParameter("addSatffId" + i));
					String insertallocationDate = req.getParameter("startDate" + i);
					String insertreleaseDate = req.getParameter("releaseDate" + i);
					int roleIds = Integer.parseInt(req.getParameter("role" + i));
					try {
						percentageAllocation = Integer.valueOf(req.getParameter("percentageAllocation" + i));
					} catch (Exception e) {
						log.error("Got Exception while inserting Project Team Details :: ", e); //
						e.printStackTrace();
					}
					try {
						if (insertallocationDate != null) {
							java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(insertallocationDate);
							sqlAllocationDate = new java.sql.Date(uDate.getTime());
						}

						if (insertreleaseDate != null) {
							java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(insertreleaseDate);
							sqlReleaseDate = new java.sql.Date(uDate.getTime());

						}
						insertprojectTeamDetails.setStaffId(staffID);
						// insertprojectTeamDetails.setRole(insertrole);
						// int insertroleId = projectTeamDao.getRoleIdForRole(insertrole);
						insertprojectTeamDetails.setRoleID(roleIds);
						/* int roleId2 = projectTeamDao.getRoleIdForRole(role); */
						projectTeamDetails.setRoleID(roleId1);
						insertprojectTeamDetails.setAllocationDate(sqlAllocationDate);
						insertprojectTeamDetails.setReleaseDate(sqlReleaseDate);
						insertprojectTeamDetails.setPercentageAllocation(percentageAllocation);

						/* boolean sameid = false; */

						int usedPercentage = projectTeamDao.checkAllocation(insertprojectTeamDetails);
						if (projectTeamDao.checkAllocationInSameDate(insertprojectTeamDetails)) {
							statusMsg = "Associate Team information already available";
						} else {
							if (usedPercentage < 100) {
								int remainingPercentage = 100 - usedPercentage;
								if (percentageAllocation <= remainingPercentage) {
									if (projectTeamDao.insertAssociate(insertprojectTeamDetails, adminId,
											projectIdForInsertion)) {
										statusMsg = "Associate Team information saved successfully";
									} else {
										statusMsg = "Associate Team information not saved";
									}
								} else {
									statusMsg = "You can allocate only " + remainingPercentage;
								}
							} else {
								statusMsg = "Associate already utilized 100%";
							}
						}

					} catch (Exception e) {
						log.error("Got Exception while  inserting Project Team Details ::  ", e); //
						e.printStackTrace();
					}
					i++;
				}

			}
			/*
			 * log.info("projectid===="+Integer.parseInt(req.getParameter("projectId")));
			 * int insertprojectID = Integer.parseInt(req.getParameter("projectId"));
			 */

			ArrayList<Project> projectDetailsList = projectDao.getProjectDetails(Integer.parseInt(projectID));
			masterCustomer.setProjectDetailsList(projectDetailsList);

			ArrayList<ProjectTeam> projectTeamList = projectTeamDao.getRole();
			masterCustomer.setProjectTeamList(projectTeamList);

			ArrayList<ProjectTeam> projectAssociateList = projectTeamDao.getAssociate();
			masterCustomer.setProjectAssociateList(projectAssociateList);

			ArrayList<ProjectTeamDetails> associateTeamList = projectTeamDao
					.getAllTeamDetails(Integer.parseInt(projectID));
			masterCustomer.setAssociateTeamList(associateTeamList);

			ArrayList<ProjectTeamSkills> peopleSkills = projectpeopleskillsDao
					.getProjectTeamSkillsList(Integer.parseInt(projectID));
			masterCustomer.setPeopleSkills(peopleSkills);

			ArrayList<ProjectTeamSkills> peopleSkillscount = projectpeopleskillsDao
					.getProjectTeamSkillsListforAllocationcount(Integer.parseInt(projectID));
			masterCustomer.setPeopleSkillscount(peopleSkillscount);

			if (statusMsg != null && !statusMsg.isEmpty())
				masterCustomer.setErrorMsg(statusMsg);

			return new ModelAndView("pins/teammanagement", "masterCustomer", masterCustomer);
		} catch (Exception e) {
			log.info("Exception occured in CURD opertion" + e);
			e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!!Something went wrong. Contact Support Team.");
		}

	}

}