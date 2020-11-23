package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
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
import ai.rnt.pins.dao.MilestoneMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.MilestoneMaster;
import ai.rnt.pins.model.MilestoneMasterView;
import ai.rnt.pins.model.SoftwareMaster;
import ai.rnt.pins.model.SoftwareMasterView;

@Controller
public class MilestoneController {

	Dashboard dashboard = null;
	int staffID = 0;
	MilestoneMasterDao milestoneDao = new MilestoneMasterDao();
	private static final Logger log = LogManager.getLogger(MilestoneController.class);

	@RequestMapping(value="/editmilestones.do")
	public ModelAndView editMilestones(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		MilestoneMasterView milestoneRecords = new MilestoneMasterView();
		MilestoneMaster milestone = new MilestoneMaster();
		ArrayList<MilestoneMaster> milestoneList = null;
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		

		try {
			String milestoneName = (String) request.getParameter("milestone");
			int milestoneId = 0;
			milestone.setMilestoneName(milestoneName);

			if (buttonAction.equals("Update") && milestoneName != null) {
				milestoneId = Integer.parseInt(request.getParameter("milestoneId"));
				milestone.setMilestoneId(milestoneId);
				
				if(milestoneDao.checkDuplicateRecForMilestone(milestoneName))
					milestoneRecords.setMessage("Milestone already exists");
				else {
					if(milestoneDao.updateMilestone(milestoneId, milestoneName, adminId))
						milestoneRecords.setMessage("Milestone updated successfully");
					else
						milestoneRecords.setMessage("Milestone not updated");
				}
				
			} else {
				if(milestoneDao.checkDuplicateRecForMilestone(milestoneName))
					milestoneRecords.setMessage("Milestone already exists");
				else {
					if(milestoneDao.addMilestone(milestoneName, adminId))
						milestoneRecords.setMessage("Milestone added successfully");
					else {
						if(milestoneDao.updateAddedMilestone(milestoneName, adminId))
							milestoneRecords.setMessage("Milestone added successfully");
						else
							milestoneRecords.setMessage("Milestone not added");
					}
				}
				
			}

			milestoneList = milestoneDao.getListOfMilestones();
			milestoneRecords.setMilestone(milestoneList);
			return new ModelAndView("pins/milestonemaster", "milestoneRecords", milestoneRecords);
			
		} catch (Exception e) {
			log.error("Got Exception while Updating Milestone Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage","Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/deletemilestone.do")
	public ModelAndView deleteMilestone(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		MilestoneMasterView milestoneRecords = new MilestoneMasterView();
		ArrayList<MilestoneMaster> milestoneList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		

		try {
			int milestoneId = Integer.parseInt(request.getParameter("milestoneid"));
			if(milestoneDao.deleteMilestone(milestoneId, adminId))
				milestoneRecords.setMessage("Milestone deleted successfully");
			else
				milestoneRecords.setMessage("Milestone not deleted");

			milestoneList = milestoneDao.getListOfMilestones();
			milestoneRecords.setMilestone(milestoneList);
			
			return new ModelAndView("pins/milestonemaster", "milestoneRecords", milestoneRecords);
		} catch (Exception e) {
			log.error("Got Exception while Deleting Milestone Details :: ", e);
			// e.printStackTrace();
			
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}
	
	/*
	 * @RequestMapping(value="/viewlistofmilestones.do") public ModelAndView
	 * viewMilestoneMaster(HttpServletRequest req, HttpServletResponse res) {
	 * 
	 * MilestoneMasterView milestoneRecords = new MilestoneMasterView();
	 * ArrayList<MilestoneMaster> milestoneList = null; try { milestoneList =
	 * milestoneDao.getListOfMilestones();
	 * milestoneRecords.setMilestone(milestoneList); return new
	 * ModelAndView("pins/milestonemaster", "milestoneRecords", milestoneRecords); }
	 * catch (Exception e) {
	 * log.error("Got Exception while Deleting Milestone Details :: ", e); //
	 * e.printStackTrace();
	 * 
	 * return new ModelAndView("pins/ErrorPage", "ErrorMessage",
	 * "Error!!Something went wrong.Contact Support Team."); }
	 * 
	 * }
	 */
}