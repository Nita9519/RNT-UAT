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
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.LocationMaster;
import ai.rnt.pins.model.LocationMasterView;
import ai.rnt.lms.model.User;
import ai.rnt.pins.dao.LocationMasterDao;

@Controller
public class LocationController{

	Dashboard dashboard = null;
	int staffID = 0;
	LocationMasterDao locationDao = new LocationMasterDao();
	private static final Logger log = LogManager.getLogger(RoleController.class);

	@RequestMapping(value="/editlocations.do")
	public ModelAndView editLocations(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		LocationMasterView locationRecords = new LocationMasterView();
		LocationMaster location = new LocationMaster();
		ArrayList<LocationMaster> locationList = null;
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {
			String locationName = (String) request.getParameter("location");
			int locationId = 0;

			location.setLocationName(locationName);
			location.setLocationId(locationId);

			if (buttonAction.equals("Update") && locationName != null) {
				locationId = Integer.parseInt(request.getParameter("locationid"));
				location.setLocationId(locationId);
				
				if(locationDao.checkDuplicateRecForLocation(locationName))
					locationRecords.setMessage("Location already exists");
				else {
					if(locationDao.updateLocation(locationId, locationName, adminId))
						locationRecords.setMessage("Location updated successfully");
					else
						locationRecords.setMessage("Location not updated");
				}
				
			} else {
				if(locationDao.checkDuplicateRecForLocation(locationName))
					locationRecords.setMessage("Location already exists");
				else {
					if(locationDao.addLocation(locationName, adminId))
						locationRecords.setMessage("Location added successfully");
					else {
						if(locationDao.updateAddedLocation(locationName, adminId))
							locationRecords.setMessage("Location added successfully");
						else
							locationRecords.setMessage("Location Phase not added");
					}
				}
				
			}

			locationList = locationDao.getListOfLocations();
			locationRecords.setLocation(locationList);
			
			return new ModelAndView("pins/locationmaster", "locationRecords", locationRecords);
			
		} catch (Exception e) {
			log.error("Got Exception while Updating Location Details ::  ", e);
			// e.printStackTrace();
			
			return new ModelAndView("pins/ErrorPage", "ErrorMessage","Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/deletelocation.do")
	public ModelAndView deleteLocation(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		LocationMasterView locationRecords = new LocationMasterView();
		ArrayList<LocationMaster> locationList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		

		try {
			int locationId = Integer.parseInt(request.getParameter("locationid"));
			if(locationDao.deleteLocation(locationId, adminId))
				locationRecords.setMessage("Location deleted successfully");
			else
				locationRecords.setMessage("Location not deleted");

			locationList = locationDao.getListOfLocations();
			locationRecords.setLocation(locationList);
			
			return new ModelAndView("pins/locationmaster", "locationRecords", locationRecords);
		} catch (Exception e) {
			log.error("Got Exception while delete Location Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

}