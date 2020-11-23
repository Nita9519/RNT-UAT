package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ai.rnt.lms.model.User;
import ai.rnt.pins.dao.DashboardDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.util.MailUtil;

@Controller
public class PinsDashboardController {

	DashboardDao dashboarddao = new DashboardDao();
	Dashboard dashboard = null;
	MailUtil mailUtil = new MailUtil();

	StackTraceElement l = new Exception().getStackTrace()[0];

	private static final Logger log = LogManager.getLogger(DashboardDao.class);

	@RequestMapping(value = "/pinsdash.do")
	public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException {

		Dashboard dashboard = null;
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		
		int staffID = userSession.getStaffID();
		String password = userSession.getPassword();
		// valid employee check method is call

		try {
			dashboard = dashboarddao.getEmployeeDetails(staffID, password);			
			dashboard.setProjectCount(dashboarddao.getActiveProjects());
			dashboard.setCustomerCount(dashboarddao.getActiveCustomers());
			dashboard.setGetActiveProjectsList(dashboarddao.getActiveProjectsList());
			dashboard.setProject(dashboarddao.getUpcomingMilestones());
			dashboard.setAlerts(dashboarddao.getAlerts());
			dashboard.setProjecthealth(dashboarddao.getProjectHealth());
			dashboard.setEffortsstatus(dashboarddao.getEffortsStatus());
			dashboard.setProjectListForOverview(dashboarddao.getTopFourList());
			
			return new ModelAndView("pins/dashboard", "dashboard", dashboard);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new ModelAndView("pins/ErrorPage","ErrorMessage","Error!!Something went wrong.Contact Support Team");
		}
		
	}


	// get base URL
	public static String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}