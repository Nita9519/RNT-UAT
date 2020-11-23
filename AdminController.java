package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import ai.rnt.pins.dao.RoleMasterDao;
import ai.rnt.pins.model.RoleMaster;
import ai.rnt.pins.model.RoleMasterView;
import ai.rnt.pins.dao.ServiceTypeMasterDao;
import ai.rnt.pins.dao.SoftwareMasterDao;
import ai.rnt.pins.model.ServiceTypeMaster;
import ai.rnt.pins.model.ServiceTypeMasterView;
import ai.rnt.pins.model.SoftwareMaster;
import ai.rnt.pins.model.SoftwareMasterView;
import ai.rnt.pins.dao.HardwareMasterDao;
import ai.rnt.pins.dao.LocationMasterDao;
import ai.rnt.pins.model.HardwareMaster;
import ai.rnt.pins.model.HardwareMasterView;
import ai.rnt.pins.model.LocationMaster;
import ai.rnt.pins.model.LocationMasterView;
import ai.rnt.pins.dao.MilestoneMasterDao;
import ai.rnt.pins.model.MilestoneMaster;
import ai.rnt.pins.model.MilestoneMasterView;
import ai.rnt.pins.dao.ProjectPhaseMasterDao;
import ai.rnt.pins.model.ProjectPhaseMaster;
import ai.rnt.pins.model.ProjectPhaseMasterView;
import ai.rnt.pins.dao.RateCardMasterDao;
import ai.rnt.pins.model.RateCardMaster;
import ai.rnt.pins.model.RateCardMasterView;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/")
public class AdminController extends MultiActionController {

	RoleMaster role = null;
	ServiceTypeMaster serviceType = null;
	LocationMaster location = null;
	MilestoneMaster milestone = null;
	ProjectPhaseMaster projectPhase = null;
	HardwareMaster hardware = null;
	SoftwareMaster software = null;
	RateCardMaster rateCard = null;
	RoleMasterDao roleDao = new RoleMasterDao();
	ServiceTypeMasterDao serviceTypeDao = new ServiceTypeMasterDao();
	LocationMasterDao locationDao = new LocationMasterDao();
	MilestoneMasterDao milestoneDao = new MilestoneMasterDao();
	ProjectPhaseMasterDao projectPhaseDao = new ProjectPhaseMasterDao();
	HardwareMasterDao hardwareDao = new HardwareMasterDao();
	SoftwareMasterDao softwareDao = new SoftwareMasterDao();
	RateCardMasterDao rateCardDao = new RateCardMasterDao();

	private static final Logger log = LogManager.getLogger(AdminController.class);

	@RequestMapping(value = "/adminview.do")
	public ModelAndView adminView(HttpServletRequest req, HttpServletResponse res) {

		return new ModelAndView("pins/admin");
	}

	@RequestMapping(value = "/viewlistofsoftwares.do")
	public ModelAndView viewListOfSoftware(HttpServletRequest req, HttpServletResponse res) {

		SoftwareMasterView softwareRecords = new SoftwareMasterView();
		ArrayList<SoftwareMaster> softwareList = null;
		try {
			softwareList = softwareDao.getListOfSoftwares();
			softwareRecords.setSoftware(softwareList);
			/* softwareRecords.setMessage(message); */

			return new ModelAndView("pins/softwarelist", "softwareRecords", softwareRecords);
		} // catch (SQLException|PropertyVetoException e)
		catch (Exception e) {
			log.error("Got Exception :: ", e);

			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
			// e.printStackTrace();
		}

	}

	@RequestMapping(value = "/viewlistofhardwares.do")
	public ModelAndView viewListOfHardwares(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		HardwareMasterView hardwareRecords = new HardwareMasterView();
		ArrayList<HardwareMaster> hardwareList = null;
		try {
			hardwareList = hardwareDao.getListOfHardwares();
			hardwareRecords.setHardware(hardwareList);
			return new ModelAndView("pins/hardwarelist", "hardwareRecords", hardwareRecords);
		} // catch (SQLException|PropertyVetoException e)
		catch (Exception e) {
			log.error("Got Exception while viewing List Of Hardwares:: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewroleratecard.do")
	public ModelAndView viewRoleRateCard(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		RateCardMasterView rateCardObject = new RateCardMasterView();
		ArrayList<RoleMaster> roleList = null;
		ArrayList<LocationMaster> locationList = null;
		ArrayList<RateCardMaster> rateCardList = null;
		try {
			roleList = roleDao.getListOfRoles();
			locationList = locationDao.getListOfLocations();
			rateCardList = rateCardDao.getListOfRateCards();
			rateCardObject.setRoleDropdown(roleList);
			rateCardObject.setLocationDropdown(locationList);
			rateCardObject.setRateCard(rateCardList);

			return new ModelAndView("pins/roleratecardmaster", "rateCard", rateCardObject);
		} // catch (SQLException|PropertyVetoException e)
		catch (Exception e) {
			log.error("Got Exception while viewing Role Rate Card :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewlistofprojectphases.do")
	public ModelAndView viewListOfProjectPhases(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		ProjectPhaseMasterView phaseRecords = new ProjectPhaseMasterView();
		ArrayList<ProjectPhaseMaster> phaseList = null;
		try {
			phaseList = projectPhaseDao.getListOfProjectPhases();
			phaseRecords.setProjectPhase(phaseList);

			return new ModelAndView("pins/projectphasemaster", "phaseRecords", phaseRecords);

		} // catch (SQLException|PropertyVetoException e)
		catch (Exception e) {
			log.error("Got Exception while viewing List Of Project Phases :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewlistofmilestones.do")
	public ModelAndView viewListOfMilestones(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		MilestoneMasterView milestoneRecords = new MilestoneMasterView();
		ArrayList<MilestoneMaster> milestoneList = null;
		try {
			milestoneList = milestoneDao.getListOfMilestones();
			milestoneRecords.setMilestone(milestoneList);

			return new ModelAndView("pins/milestonemaster", "milestoneRecords", milestoneRecords);
		} // catch (SQLException| PropertyVetoException e)
		catch (Exception e) {
			log.error("Got Exception while viewing List Of Milestones:: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewlistofservicetypes.do")
	public ModelAndView viewListOfServiceTypes(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		ServiceTypeMasterView serviceRecords = new ServiceTypeMasterView();
		ArrayList<ServiceTypeMaster> serviceList = null;
		try {
			serviceList = serviceTypeDao.getListOfServiceTypes();
			serviceRecords.setServiceType(serviceList);

			return new ModelAndView("pins/servicetypesmaster", "serviceRecords", serviceRecords);
		} catch (Exception e) {
			log.error("Got Exception while viewing Service Type :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewlistofroles.do")
	public ModelAndView viewListOfRoles(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		RoleMasterView roleRecords = new RoleMasterView();
		ArrayList<RoleMaster> roleList = null;
		try {
			roleList = roleDao.getListOfRoles();
			roleRecords.setRole(roleList);
			return new ModelAndView("pins/rolemaster", "roleRecords", roleRecords);
		}

		catch (Exception e) {
			log.error("Got Exception while viewing List Roles :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/viewlistoflocations.do")
	public ModelAndView viewListOfLocations(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		LocationMasterView locationRecords = new LocationMasterView();
		ArrayList<LocationMaster> locationList = null;
		try {
			locationList = locationDao.getListOfLocations();
			locationRecords.setLocation(locationList);
			return new ModelAndView("pins/locationmaster", "locationRecords", locationRecords);
		}

		catch (Exception e) {
			log.error("Got Exception while viewing List Of Locations :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}
}