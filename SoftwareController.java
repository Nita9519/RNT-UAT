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
import ai.rnt.pins.dao.SoftwareMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.SoftwareMaster;
import ai.rnt.pins.model.SoftwareMasterView;

@Controller

public class SoftwareController {

	Dashboard dashboard = null;
	int staffID = 0;
	SoftwareMasterDao softwareDao = new SoftwareMasterDao();

	private static final Logger log = LogManager.getLogger(SoftwareController.class);

	@RequestMapping(value="/editsoftware.do")
	public ModelAndView editSoftware(HttpServletRequest request, HttpServletResponse res) {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		ArrayList<SoftwareMaster> softwareList = null;
		try {
			int softwareId = Integer.parseInt(request.getParameter("softid"));

			softwareList = softwareDao.getSoftware(softwareId);
			softwareRecords.setSoftware(softwareList);
			
			return new ModelAndView("pins/softwaremaster", "softwareRecords", softwareRecords);
		} catch (Exception e) {
			log.error("Got Exception while updating software Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/addsoftware.do")
	public ModelAndView addSoftware(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/softwaremaster");
	}

	@RequestMapping(value="/addsoftwaremaster.do")
	public ModelAndView addSoftwareMaster(HttpServletRequest request, HttpServletResponse res) {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		SoftwareMaster software = new SoftwareMaster();
		ArrayList<SoftwareMaster> softwareList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {

			software.setSoftwareType((String) request.getParameter("softwaretype"));
			software.setVendor(request.getParameter("vendor"));
			software.setProductName(request.getParameter("productname"));
			software.setProductVersion(request.getParameter("productversion"));
			
			if(softwareDao.checkDuplicateRecForSoftwareDetails(software))
				softwareRecords.setMessage("Software Details already exists");
			else {
				if(softwareDao.addSoftware(software, adminId)) 
					softwareRecords.setMessage("Software Details added successfully");
				else {
					if(softwareDao.updateAddedSoftware(software, adminId))
						softwareRecords.setMessage("Software Details added successfully");
					else
						softwareRecords.setMessage("Software Details not added");
				}
			}
			
			

			softwareList = softwareDao.getListOfSoftwares();
			softwareRecords.setSoftware(softwareList);
			
			return new ModelAndView("pins/softwarelist", "softwareRecords", softwareRecords);

		} catch (Exception e) {
			log.error("Got Exception while adding software Details ::  ", e);
			// e.printStackTrace();
			
			return new ModelAndView("pins/ErrorPage","ErrorMessage", "Error!!Something went wrong.Contact Support Team." );
		}

		
	}

	@RequestMapping(value="/editsoftwaremaster.do")
	public ModelAndView editSoftwareMaster(HttpServletRequest request, HttpServletResponse res) {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		SoftwareMaster software = new SoftwareMaster();
		ArrayList<SoftwareMaster> softwareList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {
			int softwareId = Integer.parseInt(request.getParameter("softwareid"));
			
			software.setSoftwareId(softwareId);
			software.setSoftwareType(request.getParameter("softwaretype"));
			software.setVendor(request.getParameter("vendor"));
			software.setProductName(request.getParameter("productname"));
			software.setProductVersion(request.getParameter("productversion"));
			
			if(softwareDao.checkDuplicateRecForSoftwareDetails(software))
				softwareRecords.setMessage("Software Details already exists");
			else {
				if(softwareDao.updateSoftware(software, adminId))
					softwareRecords.setMessage("Software Details updated successfully");
				else
					softwareRecords.setMessage("Software Details not updated");
			}
			
			softwareList = softwareDao.getListOfSoftwares();
			softwareRecords.setSoftware(softwareList);
			
			return new ModelAndView("pins/softwarelist", "softwareRecords", softwareRecords);

		} catch (Exception e) {
			log.error("Got Exception while updating software Details :: ", e);
			// e.printStackTrace();
			
			return new ModelAndView("pins/ErrorPage","ErrorMessage", "Error!!Something went wrong.Contact Support Team." );
		}

		
	}

	@RequestMapping(value="/deletesoftware.do")
	public ModelAndView deleteSoftware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		ArrayList<SoftwareMaster> softwareList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {
			int softwareId = Integer.parseInt(request.getParameter("softid"));
			if(softwareDao.deleteSoftware(softwareId, adminId))
				softwareRecords.setMessage("Software Details deleted successfully");
			else
				softwareRecords.setMessage("Software Details not deleted");
			
			softwareList = softwareDao.getListOfSoftwares();
			softwareRecords.setSoftware(softwareList);
			
			return new ModelAndView("pins/softwarelist", "softwareRecords", softwareRecords);
		} catch (Exception e) {
			log.error("Got Exception while deleting software Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage","ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/viewsoftwaremaster.do")
	public ModelAndView viewSoftwareMaster(HttpServletRequest req, HttpServletResponse res) {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		ArrayList<SoftwareMaster> softwareList = null;
		try {
			softwareList = softwareDao.getListOfSoftwares();
			softwareRecords.setSoftware(softwareList);
			return new ModelAndView("pins/softwarelist", "softwareRecords", softwareRecords);
		} catch (Exception e) {
			log.error("Got Exception while viewing software Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage","ErrorMessage", "Error!!Something went wrong.Contact Support Team." );
		}
		
	}
}