package ai.rnt.pins.controller;

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
import ai.rnt.pins.dao.LocationMasterDao;
import ai.rnt.pins.dao.RateCardMasterDao;
import ai.rnt.pins.dao.RoleMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.LocationMaster;
import ai.rnt.pins.model.RateCardMaster;
import ai.rnt.pins.model.RateCardMasterView;
import ai.rnt.pins.model.RoleMaster;

@Controller
public class RateCardController {

	Dashboard dashboard = null;
	int staffID = 0;
	RateCardMasterDao rateCardDao = new RateCardMasterDao();
	RoleMasterDao roleDao = new RoleMasterDao();
	LocationMasterDao locationDao = new LocationMasterDao();

	private static final Logger log = LogManager.getLogger(RateCardController.class);

	@RequestMapping(value = "/addroleratecard.do")
	public ModelAndView addRoleRateCard(HttpServletRequest request, HttpServletResponse res) {

		RateCardMasterView rateCardObject = new RateCardMasterView();
		RateCardMaster rateCard = new RateCardMaster();
		ArrayList<RoleMaster> roleList = null;
		ArrayList<LocationMaster> locationList = null;
		ArrayList<RateCardMaster> rateCardList = null;
		String buttonAction = request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			String roleName = (String) request.getParameter("role");
			rateCard.setRoleName(roleName);
			int roleId = rateCardDao.getRoleId(roleName);
			rateCard.setRoleId(roleId);
			String locationName = (String) request.getParameter("location");
			rateCard.setLocationName(locationName);
			int locationId = rateCardDao.getLocationId(locationName);
			rateCard.setLocationId(locationId);
			rateCard.setOnSiteRatePerHour(Float.parseFloat(request.getParameter("onsiterateperhour")));
			rateCard.setOnSiteRatePerDay(Float.parseFloat(request.getParameter("onsiterateperday")));
			rateCard.setOffShoreRatePerHour(Float.parseFloat(request.getParameter("offshorerateperhour")));
			rateCard.setOffShoreRatePerDay(Float.parseFloat(request.getParameter("offshorerateperday")));

			if (buttonAction.equals("Update")) {
				if (rateCardDao.updateRateCard(rateCard, adminId))
					rateCardObject.setMessage("Rate Card Details updated successfully");
				else
					rateCardObject.setMessage("Rate Card Details not updated");
			} else if (buttonAction.equals("Add")) {
				if (rateCardDao.checkDuplicateRecForRateCardDetails(rateCard))
					rateCardObject.setMessage("Rate Card Details already exists");
				else {
					if (rateCardDao.addRateCard(rateCard, adminId))
						rateCardObject.setMessage("Rate Card Details added successfully");
					else {
						//if (rateCardDao.updateAddedRateCard(rateCard, adminId))
							//rateCardObject.setMessage("Rate Card added successfully");
						//else
							rateCardObject.setMessage("Rate Card not added");
					}
				}
			}

			roleList = roleDao.getListOfRoles();
			locationList = locationDao.getListOfLocations();
			rateCardList = rateCardDao.getListOfRateCards();
			rateCardObject.setRoleDropdown(roleList);
			rateCardObject.setLocationDropdown(locationList);
			rateCardObject.setRateCard(rateCardList);
			return new ModelAndView("pins/roleratecardmaster", "rateCard", rateCardObject);
		} catch (Exception e) {
			log.error("Got Exception while inserting Role Rate Crad Details ::  ", e);
			// e.printStackTrace();

			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/deletetroleratecard.do")
	public ModelAndView deleteRoleRateCard(HttpServletRequest request, HttpServletResponse res) {

		RateCardMasterView rateCardObject = new RateCardMasterView();
		ArrayList<RoleMaster> roleList = null;
		ArrayList<LocationMaster> locationList = null;
		ArrayList<RateCardMaster> rateCardList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			int roleId = Integer.parseInt(request.getParameter("roleid"));
			int locationId = Integer.parseInt(request.getParameter("locationid"));
			if (rateCardDao.deleteRateCard(roleId, locationId, adminId))
				rateCardObject.setMessage("Rate Card Details deleted successfully");
			else
				rateCardObject.setMessage("Rate Card Details not deleted");

			roleList = roleDao.getListOfRoles();
			locationList = locationDao.getListOfLocations();
			rateCardList = rateCardDao.getListOfRateCards();
			rateCardObject.setRoleDropdown(roleList);
			rateCardObject.setLocationDropdown(locationList);
			rateCardObject.setRateCard(rateCardList);
			return new ModelAndView("pins/roleratecardmaster", "rateCard", rateCardObject);
		} catch (Exception e) {
			log.error("Got Exception while deleting Role Rate Card Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}

	}
}