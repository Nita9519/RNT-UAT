package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import ai.rnt.pins.dao.HardwareMasterDao;
import ai.rnt.pins.dao.LocationMasterDao;
import ai.rnt.pins.dao.ProjectHardwareDao;
import ai.rnt.pins.dao.ProjectMasterDao;
import ai.rnt.pins.dao.ProjectMilestoneDao;
import ai.rnt.pins.dao.ProjectPeopleSkillsDao;
import ai.rnt.pins.dao.ProjectPhaseDao;
import ai.rnt.pins.dao.ProjectPhaseMasterDao;
import ai.rnt.pins.dao.ProjectPhasesdao;
import ai.rnt.pins.dao.ProjectSoftwareDao;
import ai.rnt.pins.dao.RateCardMasterDao;
import ai.rnt.pins.dao.RoleMasterDao;
import ai.rnt.pins.dao.SoftwareMasterDao;
import ai.rnt.pins.model.CustomerMasterView;
import ai.rnt.pins.model.Customer;
import ai.rnt.pins.model.Project;
import ai.rnt.pins.model.ProjectHardware;
import ai.rnt.pins.model.ProjectMilestone;
import ai.rnt.pins.model.ProjectPhase;
import ai.rnt.pins.model.ProjectSoftware;
import ai.rnt.pins.model.ProjectTeamSkills;
import ai.rnt.pins.model.ProjectView;

@Controller
public class ProjectController {

	CustomerDao customerDao = new CustomerDao();
	ProjectMasterDao projectDao = new ProjectMasterDao();
	SoftwareMasterDao softwareDao = new SoftwareMasterDao();
	HardwareMasterDao hardwareMasterDao = new HardwareMasterDao();
	ProjectSoftwareDao projectSoftwareDao = new ProjectSoftwareDao();
	ProjectHardwareDao projectHardwareDao = new ProjectHardwareDao();
	ProjectPeopleSkillsDao projectpeopleskillsDao = new ProjectPeopleSkillsDao();
	RoleMasterDao roleMasterDao = new RoleMasterDao();
	LocationMasterDao locationMasterDao = new LocationMasterDao();
	ProjectPhaseMasterDao projectPhaseMasterDao = new ProjectPhaseMasterDao();
	ProjectPhaseDao projectPhaseDao = new ProjectPhaseDao();
	ProjectPhasesdao projectPhasesDao = new ProjectPhasesdao();
	ProjectMilestoneDao projectMilestoneDao = new ProjectMilestoneDao();
	RateCardMasterDao rateCardMasterDao = new RateCardMasterDao();

	private static final Logger log = LogManager.getLogger(ProjectController.class);

	@RequestMapping(value = "/displaycustomers.do")
	public ModelAndView displayCustomers(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		CustomerMasterView customers = new CustomerMasterView();
		ArrayList<Customer> customerList = customerDao.getlistOfCustomers();
		ArrayList<Project> projectList = projectDao.getProjectNames();
		try {
			customers.setCustomerDetails(customerList);
			customers.setProjectList(projectList);
			return new ModelAndView("pins/projectmanagementscreen", "customers", customers);
		} catch (Exception e) {
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/displayprojectdetails1.do")
	public ProjectView displayProjectDetails(ProjectView projectView) throws SQLException, PropertyVetoException {

		int projectID = projectView.getProjectId();
		projectView = projectDao.getCustProjectDetails(projectID);

		projectView.setSoftwareList(projectSoftwareDao.getSoftwareMasterList());
		projectView.setSoftwareMap(getSoftwareMap(projectView.getSoftwareList()));
		projectView.setProjectSoftwareList(projectSoftwareDao.getProjectSoftwareList(projectID));
		projectView.setProjectSoftwareList1(projectSoftwareDao.getProjectSoftwareList1());
		projectView.setProjectHardwareList1(projectHardwareDao.getProjectHardwareList1());
		projectView.setHardwareList(projectHardwareDao.getHardwareMasterList());
		projectView.setHardwareMap(getHardwareMap(projectView.getHardwareList()));
		projectView.setProjectHardwareList(projectHardwareDao.getProjectHardwareList(projectID));
		projectView.setPeopleSkills(projectpeopleskillsDao.getProjectTeamSkillsList(projectID));
		projectView.setRoleList(roleMasterDao.getListOfRoles());
		projectView.setLocation(locationMasterDao.getListOfLocations());
		projectView.setProjectPhase(projectPhaseMasterDao.getListOfProjectPhases());
		projectView.setProjectPhasesList(projectPhaseDao.getProjectPhases(projectID));
		projectView.setProjectMilestone(projectMilestoneDao.getProjectMilestoneList(projectID));

		return projectView;

	}

	@RequestMapping(value = "/displayprojectdetails.do")
	public ModelAndView displayProjectDetails(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		try {
			ProjectView projectView = new ProjectView();
			int projectID = Integer.parseInt(request.getParameter("projectId"));
			/* int customerID=Integer.parseInt(request.getParameter("customerId")); */
			projectView.setProjectId(projectID);
			/* projectView.setCustomerId(customerID); */
			projectView = displayProjectDetails(projectView);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support team.");
		}
	}

	@RequestMapping(value = "/setprojectsoftware.do")
	public ModelAndView setProjectSoftware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, NumberFormatException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectId"));
		projectView.setProjectId(projectID);
		ProjectSoftware projectSoftware = new ProjectSoftware();
		String buttonAction = (String) request.getParameter("btnSave");
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerId = userSession.getStaffID();

		int softwareId = Integer.parseInt(request.getParameter("softwareid"));
		int noOfLicense = Integer.parseInt(request.getParameter("license"));
		try {

			projectSoftware.setProjectId(projectID);
			projectSoftware.setSoftwareId(softwareId);
			projectSoftware.setNo_of_license(noOfLicense);

			if (buttonAction.equals("Update")) {

				projectSoftwareDao.updateSoftware(projectManagerId, projectSoftware);
				status = "Software details updated successfully";
			} else {
				if (projectSoftwareDao.checkDuplicateRecForProjectSoftware(projectID, softwareId, noOfLicense))
					status = "Software details already exist";
				else {
					if (projectSoftwareDao.addSoftware(projectSoftware, projectManagerId))
						status = "Software details added successfully";
					else {
						if (projectSoftwareDao.updateAddedSoftware(projectManagerId, projectSoftware))
							status = "Software details updated successfully";
						else
							status = "Software details not updated";
					}
				}

			}

			projectView = displayProjectDetails(projectView);
			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while Adding Software Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/setprojecthardware.do")
	public ModelAndView setProjectHardware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, NumberFormatException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectId"));
		projectView.setProjectId(projectID);
		ProjectHardware projectHardware = new ProjectHardware();
		String buttonAction = (String) request.getParameter("btnHardware");
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerId = userSession.getStaffID();
		int units = Integer.parseInt(request.getParameter("unit"));

		try {
			projectHardware.setProjectId(projectID);
			projectHardware.setHardwareId(Integer.parseInt(request.getParameter("hardwareid")));
			projectHardware.setUnit(units);

			if (buttonAction.equals("Update")) {
				projectHardwareDao.updateHardware(projectHardware, projectManagerId);
				status = "Hardware details updated successfully";

			}

			else {
				if (projectHardwareDao.checkDuplicateRecForProjectHardware(projectID, projectHardware.getHardwareId(),
						units))
					status = "Hardware details already exist";
				else {
					if (projectHardwareDao.addHardware(projectHardware, projectManagerId))
						status = "Hardware details added successfully";
					else {
						if (projectHardwareDao.updateAddedHardware(projectHardware, projectManagerId))
							status = "Hardware details added successfully";
						else
							status = "Hardware details not updated";
					}
				}
			}

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);

		} catch (Exception e) {
			log.error("Got Exception while Adding Hardware Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}
	// projectView = displayProjectDetails(projectView);

	@RequestMapping(value = "/setprojectpeopleskills.do")
	public ModelAndView setProjectPeopleSkills(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, NumberFormatException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectId"));
		projectView.setProjectId(projectID);
		String status = "";

		ProjectTeamSkills teamSkills = new ProjectTeamSkills();

		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerId = userSession.getStaffID();
		try {

			teamSkills.setProjectId(projectID);

			int roleID = Integer.parseInt(request.getParameter("roleId"));

			teamSkills.setRoleID(roleID);
			teamSkills.setSkills(request.getParameter("Skills"));

			int experience = Integer.parseInt(request.getParameter("experience"));
			teamSkills.setExperience(experience);

			int locationID = Integer.parseInt(request.getParameter("locationId"));

			teamSkills.setLocationId(locationID);
			teamSkills.setSkillscount(Integer.parseInt(request.getParameter("Count")));

			/*
			 * if (buttonAction.equals("Update Skills")) { if
			 * (projectpeopleskillsDao.checkDuplicateRecForPeopleSkill(teamSkills))
			 * 
			 * status = "People skill details already exist";
			 * 
			 * else if (projectpeopleskillsDao.editSkills(teamSkills, projectManagerId))
			 * status = "People skill details updated successfully"; else status =
			 * "People skill details updated"; } else { if
			 * (projectpeopleskillsDao.checkDuplicateRecForPeopleSkill(teamSkills)) status =
			 * "People skill details already exist"; else { if
			 * (projectpeopleskillsDao.editAddedSkills(teamSkills, projectManagerId)) status
			 * = "People skill details added successfully"; else {
			 * 
			 * if (projectpeopleskillsDao.editAddedSkills(teamSkills, projectManagerId))
			 * status = "People skill details  added successfully"; else
			 * 
			 * status = "People skill details added"; } }
			 * 
			 * }
			 */
			if (buttonAction.equals("Update")) {
				if(projectpeopleskillsDao.editSkills(teamSkills, projectManagerId)) {
					status = "People skills updated successfully";
				} else {
					status = "People skills not updated";
				}
			} else {
				if (projectpeopleskillsDao.checkDuplicateRecForPeopleSkill(teamSkills))
					status = "People skills details already exist";
				else {
					if (projectpeopleskillsDao.AddedSkills(teamSkills, projectManagerId))
						status = "People skills details added successfully";
					else {
						status = "People skills details not addedd";
					}
				}
			}

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while Adding People Skills Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMesssage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/setprojectphase.do")
	public ModelAndView setProjectPhase(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, NumberFormatException, ParseException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectId"));
		projectView.setProjectId(projectID);
		ProjectPhase projectPhase = new ProjectPhase();
		String status = "";

		String buttonAction = (String) request.getParameter("btnAddPhase");
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerId = userSession.getStaffID();

		try {

			projectPhase.setProjectId(projectID);

			String phase = request.getParameter("phase");
			projectPhase.setProjectPhaseName(phase);
			int phaseId = projectPhaseDao.getPhaseId(phase);

			projectPhase.setProjectPhaseId(phaseId);
			projectPhase.setEfforts(Integer.parseInt(request.getParameter("efforts")));

			projectPhase.setUnit(request.getParameter("unit"));

			String startDate = request.getParameter("startDate");
			java.util.Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date sqlStartDate = new java.sql.Date(sDate.getTime());
			projectPhase.setStartDate(sqlStartDate);

			String endDate = request.getParameter("endDate");

			java.util.Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
			Date sqlEndDate = new java.sql.Date(eDate.getTime());
			projectPhase.setEndDate(sqlEndDate);

			if (buttonAction.equals("Update")) {

				if (projectPhaseDao.checkDuplicateRecForProjectPhase(projectID, phaseId))
					/*
					 * if (projectPhaseDao.checkDuplicateRecForUpdatedProjectPhase(projectPhase))
					 * status = "Project Phase already exist"; else {
					 */
					if (projectPhaseDao.updatePhase(projectPhase, projectManagerId))
						status = "Project Phase details updated successfully";
					else
						status = "Project Phase details not updated";
			} else {
				if (projectPhaseDao.checkDuplicateRecForProjectPhase(projectID, phaseId))
					status = "Project Phase already exist";
				else {
					if (projectPhaseDao.addPhase(projectPhase, projectManagerId))
						status = "Project Phase details added successfully";
					else {
						if (projectPhaseDao.updateAddedPhase(projectPhase, projectManagerId))
							status = "Project Phase details updated successfully";
						else
							status = "Project Phase details not updated";
					}
				}
			}

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);
			return new ModelAndView("pins/projectmanagement", "project", projectView);

		} catch (Exception e) {
			log.error("Got Exception while Adding Project Phase Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMesssage",
					"Error!!Something went wrong.Contact Support Team.");
		}
		// projectView = displayProjectDetails(projectView);

		// return new ModelAndView("pins/projectmanagement", "project", projectView);
	}

	@RequestMapping(value = "/updateprojectmilestone.do")
	public ModelAndView updateProjectMilestone(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, NumberFormatException, ParseException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectId"));
		projectView.setProjectId(projectID);
		ProjectMilestone projectMilestone = null;
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerId = userSession.getStaffID();

		int milestoneCount = Integer.parseInt(request.getParameter("milestoneCount"));
		try {
			for (int i = 1; i <= milestoneCount; i++) {
				projectMilestone = new ProjectMilestone();
				projectMilestone.setProjectId(projectID);
				projectMilestone.setMilestoneId(Integer.parseInt(request.getParameter("milestoneId" + i)));
				String metDate = request.getParameter("metDate" + i);
				if (metDate == null || metDate.equals("")) {
				} else {
					java.util.Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(metDate);
					Date sqlmetDate = new java.sql.Date(sDate.getTime());
					projectMilestone.setMetDate(sqlmetDate);
				}
				projectMilestone.setMilestoneMet(request.getParameter("MilestoneMet" + i));
				if (projectMilestoneDao.metORnot(projectMilestone, projectManagerId))
					status = "Project Milstone details updated successfully";
				else
					status = "Project Milstone details not updated";
			}
			projectView = displayProjectDetails(projectView);
			if (status != null && !status.isEmpty())
				projectView.setMessage(status);
			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while updating project Milestone Details :: ", e);
			return new ModelAndView("pins/ErrorPage", "ErrorMesssage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/deletepeopleskills.do")
	public ModelAndView deletePeopleSkills(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectid"));
		projectView.setProjectId(projectID);
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerID = userSession.getStaffID();

		try {
			int roleID = Integer.parseInt(request.getParameter("deleteId"));
			int locationId = Integer.parseInt(request.getParameter("deleteLocationId"));
			if (projectpeopleskillsDao.deleteSkills(roleID, projectID,locationId, projectManagerID))
				status = "People Skill details deleted successfully";
			else
				status = "People Skill details not deleted";

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);
			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while Deleting People Skills Details :: ", e);
			// e.printStackTrace();

			return new ModelAndView("pins/ErrorPage", "ErrorMesssage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/deleteprojectsoftware.do")
	public ModelAndView deleteProjectSoftware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectid"));
		projectView.setProjectId(projectID);
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerID = userSession.getStaffID();

		try {
			int softwareId = Integer.parseInt(request.getParameter("deleteId"));
			if (projectSoftwareDao.deleteSoftware(softwareId, projectManagerID, projectID))
				status = "Software details deleted successfully";
			else
				status = "Software details not deleted";

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while deleting Project Software Details :: ", e);
			// e.printStackTrace();
			// projectView = displayProjectDetails(projectView);
			return new ModelAndView("pins/ErrorPage", "project", projectView);
		}

	}

	@RequestMapping(value = "/deleteprojecthardware.do")
	public ModelAndView deleteProjectHardware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectid"));
		projectView.setProjectId(projectID);
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerID = userSession.getStaffID();

		try {
			int hardwareId = Integer.parseInt(request.getParameter("deleteId"));
			if (projectHardwareDao.deleteHardware(hardwareId, projectManagerID, projectID))
				status = "Hardware details deleted successfully";
			else
				status = "Hardware details not deleted";

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while deleting Project Hardware Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMesssage",
					"Error!!Something went wrong.Contact Support Team.");
		}
		// projectView = displayProjectDetails(projectView);
		// return new ModelAndView("pins/projectmanagement", "project", projectView);
	}

	@RequestMapping(value = "/deleteprojectphaselist.do")
	public ModelAndView deleteprojectphaselist(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectView projectView = new ProjectView();
		int projectID = Integer.parseInt(request.getParameter("projectid"));
		projectView.setProjectId(projectID);
		ProjectPhase projectPhase = new ProjectPhase();
		String status = "";

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int projectManagerID = userSession.getStaffID();
		projectView.setProjectManagerId(projectManagerID);

		try {
			String phase = request.getParameter("deleteId");
			/* String phase = request.getParameter("phase"); */
			projectPhase.setProjectPhaseName(phase);
			int phaseId = projectPhaseDao.getPhaseId(phase);

			projectPhase.setProjectPhaseId(phaseId);
			log.info("phaseId===" + phaseId);
			if (projectPhasesDao.deletePhases(projectView, projectPhase))
				status = "Phase details deleted successfully";
			else
				status = "Phase details not deleted";

			projectView = displayProjectDetails(projectView);

			if (status != null && !status.isEmpty())
				projectView.setMessage(status);

			return new ModelAndView("pins/projectmanagement", "project", projectView);
		} catch (Exception e) {
			log.error("Got Exception while deleting Project Software Details :: ", e);
			// e.printStackTrace();
			// projectView = displayProjectDetails(projectView);
			return new ModelAndView("pins/ErrorPage", "project", projectView);
		}

	}

	/*
	 * public HashMap<String, Integer> getPhaseMap(ArrayList<ProjectPhase> list) {
	 * 
	 * 
	 * HashMap<String, Integer, Date> map = new HashMap<String, Integer, Date>();
	 * 
	 * Iterator<ProjectPhase> itr = list.iterator(); while (itr.hasNext()) {
	 * 
	 * ProjectPhase pp = itr.next(); map.put(pp.getProjectPhaseName() + "::" +
	 * pp.getEfforts() + "::" + pp.getUnit() + "::" + pp.getStartDate(),
	 * pp.getEndDate()); }
	 * 
	 * return map; }
	 */

	public HashMap<String, Integer> getSoftwareMap(ArrayList<ProjectSoftware> list) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		Iterator<ProjectSoftware> itr = list.iterator();
		while (itr.hasNext()) {

			ProjectSoftware sw = itr.next();
			map.put(sw.getType() + "::" + sw.getVendor() + "::" + sw.getProduct() + "::" + sw.getVersion(),
					sw.getSoftwareId());
		}

		return map;
	}

	public HashMap<String, Integer> getHardwareMap(ArrayList<ProjectHardware> hList) {

		HashMap<String, Integer> hardmap = new HashMap<String, Integer>();
		Iterator<ProjectHardware> iterator = hList.iterator();

		while (iterator.hasNext()) {

			ProjectHardware hw = iterator.next();
			hardmap.put(hw.getType() + "::" + hw.getModel() + "::" + hw.getConfiguration(), hw.getHardwareId());
		}
		return hardmap;
	}
}