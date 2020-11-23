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
import ai.rnt.pins.dao.HardwareMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.HardwareMaster;
import ai.rnt.pins.model.HardwareMasterView;

@Controller
public class HardwareController {

	Dashboard dashboard = null;
	int staffID = 0;

	HardwareMasterDao hardwareDao = new HardwareMasterDao();

	private static final Logger log = LogManager.getLogger(HardwareController.class);

	@RequestMapping(value = "/edithardware.do")
	public ModelAndView editHardware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		HardwareMasterView hardwareRecords = new HardwareMasterView();
		ArrayList<HardwareMaster> hardwareList = null;
		try {
			int hardwareId = Integer.parseInt(request.getParameter("hardid"));
			log.info("hardwareId==" + hardwareId);
			hardwareList = hardwareDao.getHardware(hardwareId);
			hardwareRecords.setHardware(hardwareList);
			return new ModelAndView("pins/hardwaremaster", "hardwareRecords", hardwareRecords);
		} catch (Exception e) {
			log.error("Got Exception while Updating Hardware Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/addhardware.do")
	public ModelAndView addHardware(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/hardwaremaster");
	}

	@RequestMapping(value = "/addhardwaremaster.do")
	public ModelAndView addHardwareMaster(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		HardwareMasterView hardwareRecords = new HardwareMasterView();
		HardwareMaster hardware = new HardwareMaster();
		ArrayList<HardwareMaster> hardwareList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {

			hardware.setHardwareType(request.getParameter("hardwaretype"));
			hardware.setModel(request.getParameter("model"));
			hardware.setConfiguration(request.getParameter("configuration"));

			if (hardwareDao.checkDuplicateRecForHardwareDetails(hardware))
				hardwareRecords.setMessage("Hardware Details already exists");
			else {
				if (hardwareDao.addHardware(hardware, adminId))
					hardwareRecords.setMessage("Hardware Details addedd successfully");
				else {
					if (hardwareDao.updateAddedHardware(hardware, adminId))
						hardwareRecords.setMessage("Hardware added successfully");
					else
						hardwareRecords.setMessage("Hardware not added");
				}
			}

			hardwareList = hardwareDao.getListOfHardwares();
			hardwareRecords.setHardware(hardwareList);

			return new ModelAndView("pins/hardwarelist", "hardwareRecords", hardwareRecords);

		} catch (Exception e) {
			log.error("Got Exception while Adding Hardware Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/edithardwaremaster.do")
	public ModelAndView editHardwareMaster(HttpServletRequest request, HttpServletResponse res) {
		HardwareMasterView hardwareRecords = new HardwareMasterView();
		HardwareMaster hardware = new HardwareMaster();
		ArrayList<HardwareMaster> hardwareList = null;
		log.info("edithardwareMaster==");
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {

			int hardwareID = Integer.parseInt(request.getParameter("hardwareid"));
			hardware.setHardwareId(hardwareID);
			hardware.setHardwareType(request.getParameter("hardwaretype"));
			hardware.setModel(request.getParameter("model"));
			hardware.setConfiguration(request.getParameter("configuration"));

			if (hardwareDao.checkDuplicateRecForHardwareDetails(hardware))
				hardwareRecords.setMessage("Hardware Details already exists");
			else {
				if (hardwareDao.updateHardware(hardware, adminId))
					hardwareRecords.setMessage("Hardware Details updated successfully");
				else
					hardwareRecords.setMessage("Hardware Details not updated");
			}

			hardwareList = hardwareDao.getListOfHardwares();
			hardwareRecords.setHardware(hardwareList);

			return new ModelAndView("pins/hardwarelist", "hardwareRecords", hardwareRecords);

		} catch (Exception e) {
			log.error("Got Exception while Updating Hardware Master Details ::  ", e);
			// e.printStackTrace();

			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/deletehardware.do")
	public ModelAndView deleteHardware(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		HardwareMasterView hardwareRecords = new HardwareMasterView();
		ArrayList<HardwareMaster> hardwareList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			int hardwareId = Integer.parseInt(request.getParameter("hardid"));
			if (hardwareDao.deleteHardware(hardwareId, adminId))
				hardwareRecords.setMessage("Hardware Details deleted successfully");
			else
				hardwareRecords.setMessage("Hardware Details not deleted");

			hardwareList = hardwareDao.getListOfHardwares();
			hardwareRecords.setHardware(hardwareList);

			return new ModelAndView("pins/hardwarelist", "hardwareRecords", hardwareRecords);
		} catch (Exception e) {
			log.error("Got Exception while deleting Hardware Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewhardwaremaster.do")
	public ModelAndView viewHardwareMaster(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		log.info("viewhardwaremaster");
		HardwareMasterView hardwareRecords = new HardwareMasterView();
		ArrayList<HardwareMaster> hardwareList = null;
		try {
			hardwareList = hardwareDao.getListOfHardwares();
			hardwareRecords.setHardware(hardwareList);

			return new ModelAndView("pins/hardwarelist", "hardwareRecords", hardwareRecords);

		} catch (Exception e) {
			log.error("Got Exception while viewing Hardware Details ::  ", e);
			// e.printStackTrace();

			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}
}