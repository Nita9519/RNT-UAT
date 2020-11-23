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
import ai.rnt.pins.dao.ServiceTypeMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.ServiceTypeMaster;
import ai.rnt.pins.model.ServiceTypeMasterView;

@Controller
public class ServiceTypeController {

	Dashboard dashboard = null;
	int staffID = 0;
	ServiceTypeMasterDao serviceTypeDao = new ServiceTypeMasterDao();
	private static final Logger log = LogManager.getLogger(ServiceTypeController.class);

	@RequestMapping(value="/editservicetypes.do")
	public ModelAndView editServiceTypes(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ServiceTypeMasterView serviceRecords = new ServiceTypeMasterView();
		ServiceTypeMaster serviceType = new ServiceTypeMaster();
		ArrayList<ServiceTypeMaster> serviceList = null;
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {
			String serviceTypeName = (String) request.getParameter("servicetype");
			int serviceId = 0;
			serviceType.setServiceTypeName(serviceTypeName);

			if (buttonAction.equals("Update") && serviceTypeName != null) {
				serviceId = Integer.parseInt(request.getParameter("serviceid"));
				serviceType.setServiceTypeId(serviceId);
				
				if(serviceTypeDao.checkDuplicateRecForServiceTypeMaster(serviceTypeName))
					serviceRecords.setMessage("Service Type already exists");
				else {
					if(serviceTypeDao.updateServiceType(serviceId, serviceTypeName, adminId))
						serviceRecords.setMessage("Service Type updated successfully");
					else
						serviceRecords.setMessage("Service Type not updated");
				}
				
			} else {
				if(serviceTypeDao.checkDuplicateRecForServiceTypeMaster(serviceTypeName))
					serviceRecords.setMessage("Service Type already exists");
				else {
					if(serviceTypeDao.addServiceType(serviceTypeName, adminId))
						serviceRecords.setMessage("Service Type added successfully");
					else {
						if(serviceTypeDao.updateAddedServiceType(serviceTypeName, adminId))
							serviceRecords.setMessage("Service Type added successfully");
						else
							serviceRecords.setMessage("Service Type not added");
					}
				}
				
			}

			serviceList = serviceTypeDao.getListOfServiceTypes();
			serviceRecords.setServiceType(serviceList);
			return new ModelAndView("pins/servicetypesmaster", "serviceRecords", serviceRecords);
		} catch (Exception e) {
			log.error("Got Exception while updating software Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/deleteservicetype.do")
	public ModelAndView deleteServiceType(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ServiceTypeMasterView serviceRecords = new ServiceTypeMasterView();
		ArrayList<ServiceTypeMaster> serviceList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();


		try {
			int serviceId = Integer.parseInt(request.getParameter("serviceid"));
			if(serviceTypeDao.deleteServiceType(serviceId, adminId))
				serviceRecords.setMessage("Service Type deleted successfully");
			else
				serviceRecords.setMessage("Service Type not deleted");

			serviceList = serviceTypeDao.getListOfServiceTypes();
			serviceRecords.setServiceType(serviceList);
			return new ModelAndView("pins/servicetypesmaster", "serviceRecords", serviceRecords);
		} catch (Exception e) {
			log.error("Got Exception while deleting Software Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage","ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

}