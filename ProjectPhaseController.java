package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.dao.ProjectPhaseMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.ProjectPhaseMaster;
import ai.rnt.pins.model.ProjectPhaseMasterView;
import ai.rnt.pins.model.SoftwareMaster;


@Controller
public class ProjectPhaseController  {

	Dashboard dashboard = null;
	int staffID = 0;
	ProjectPhaseMasterDao projectPhaseDao = new ProjectPhaseMasterDao();
	private static final Logger log = LogManager.getLogger(ProjectPhaseController.class);

	@RequestMapping(value="/editprojectphases.do")
	public ModelAndView editProjectPhases(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectPhaseMasterView phaseRecords = new ProjectPhaseMasterView();
		ProjectPhaseMaster projectPhase = new ProjectPhaseMaster();
		ArrayList<ProjectPhaseMaster> phaseList = null;
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		
		try {
			String projectPhaseName = (String) request.getParameter("projectPhase");
			int phaseId = 0;
			projectPhase.setProjectPhaseName(projectPhaseName);

			if (buttonAction.equals("Update") && projectPhaseName != null) {
				phaseId = Integer.parseInt(request.getParameter("phaseId"));
				projectPhase.setProjectPhaseId(phaseId);
				
				if(projectPhaseDao.checkDuplicateRecForProjectPhaseDetails(projectPhaseName))
					phaseRecords.setMessage("Project Phase already exists");
				else {
					if(projectPhaseDao.updateProjectPhase(phaseId, projectPhaseName, adminId))
						phaseRecords.setMessage("Project Phase updated successfully");
					else
						phaseRecords.setMessage("Project Phase not updated");
				}
				
				
			} else {
				if(projectPhaseDao.checkDuplicateRecForProjectPhaseDetails(projectPhaseName)) 
					phaseRecords.setMessage("Project Phase already exists");
				else {
					if(projectPhaseDao.addProjectPhase(projectPhaseName, adminId))
						phaseRecords.setMessage("Project Phase added successfully");
					else {
						if(projectPhaseDao.updateAddedProjectPhase(projectPhaseName, adminId))
							phaseRecords.setMessage("Project Phase added successfully");
						else
							phaseRecords.setMessage("Project Phase not added");
					}
						
				}
				
			}

			phaseList = projectPhaseDao.getListOfProjectPhases();
			phaseRecords.setProjectPhase(phaseList);
			
			return new ModelAndView("pins/projectphasemaster", "phaseRecords", phaseRecords);
		} catch (Exception e) {
			log.error("Got Exception while updating Project phases Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage","Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/deleteprojectphase.do")
	public ModelAndView deleteProjectPhase(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ProjectPhaseMasterView phaseRecords = new ProjectPhaseMasterView();
		ArrayList<ProjectPhaseMaster> phaseList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		

		try {
			int phaseId = Integer.parseInt(request.getParameter("phaseid"));
			if(projectPhaseDao.deleteProjectPhase(phaseId, adminId))
				phaseRecords.setMessage("Project Phase deleted successfully");
			else
				phaseRecords.setMessage("Project Phase not deleted");

			phaseList = projectPhaseDao.getListOfProjectPhases();
			phaseRecords.setProjectPhase(phaseList);
			
			return new ModelAndView("pins/projectphasemaster", "phaseRecords", phaseRecords);
		} catch (Exception e) {
			log.error("Got Exception while deleting Project phases Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage","ErrorMessage","Error!!Something went wrong.Contact Support Team.");
		}
		
	}
	
	

}