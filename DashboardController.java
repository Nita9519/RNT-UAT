
package ai.rnt.lms.controller;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.dao.DashboardDao;
import ai.rnt.lms.dao.HolidayDao;
import ai.rnt.lms.dao.LeaveAllotmentDao;
import ai.rnt.lms.dao.LeaveRecordsDao;
import ai.rnt.lms.model.Dashboard;
import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.lms.model.LeaveRecord;
import ai.rnt.lms.model.User;
import ai.rnt.lms.util.MailUtil;

@Controller
public class DashboardController {

	LeaveAllotmentDao leaveAllotmentDao = new LeaveAllotmentDao();
	User user = null;
	int staffID = 0;
	HolidayCalendar hc = null;
	MailUtil mailUtil = new MailUtil();
	LeaveRecordsDao leaverecordsdao = new LeaveRecordsDao();
	HolidayDao holidaydao = new HolidayDao();
	LeaveAllotmentDao leaveallotmentdao = new LeaveAllotmentDao();
	DashboardDao dashboarddao = new DashboardDao();

	private static final Logger log = LogManager.getLogger(DashboardController.class);

	@RequestMapping(value = "/lmsdash.do")
	public ModelAndView dashBoard(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Dashboard dashboard = null;
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffID = user.getStaffID();
		int totalLeaves = 24;
		String password = user.getPassword();

		log.info("AMS ----Staff ID = " + staffID + " password = " + password);
		try {
			user = leaverecordsdao.getEmployeeDetails(staffID, password);
			ArrayList<LeaveRecord> leaveRecords = (ArrayList<LeaveRecord>) leaverecordsdao
					.getLeaveRecordsDashboard(staffID); // get
			ArrayList<HolidayCalendar> holidayCalender = (ArrayList<HolidayCalendar>) holidaydao.getHolidayCalender();
			dashboard = new Dashboard();
			// leaveAllotmentDao.getLeaveDetails(staffID, dashboard); // calculate leave
			// detail
			dashboard.setLeaveRecords(leaveRecords); // assign(set) the list data
			dashboard.setHolidays(holidayCalender);
			dashboard.setTotalLeaves(24);

			leaverecordsdao.getUsedLeaves(staffID, dashboard);
			DecimalFormat df = new DecimalFormat("#.##");
			int consumeLeave = leaverecordsdao.getConsumeLeave(staffID);
			int consumeLeaveLWP = leaverecordsdao.getConsumeLeaveLWP(staffID);
			double earnLeave = getEarnLeave();
			double balanceLeave = earnLeave - consumeLeave;

			dashboard.setConsumeLeave(consumeLeave);
			dashboard.setConsumeLeaveLWP(consumeLeaveLWP);
			dashboard.setBalanceLeave(Double.parseDouble(df.format(balanceLeave)));
			dashboard.setEarnLeave(Double.parseDouble(df.format(earnLeave)));
			log.info(dashboard.getEarnLeave());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("lms/dashboard", "dashboard", dashboard);
	}

	
	@RequestMapping(value = "/insertleave.do")
	public ModelAndView addLeave(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException, MessagingException {
		boolean isValid = false;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Dashboard dashboard = new Dashboard();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffID = user.getStaffID();
		String emailID=leaverecordsdao.getManagerEmail(staffID);
		user.setManagerEmailID(emailID);
		String str = "";
		String leaveType = request.getParameter("leaveType");
		String leaveReason = request.getParameter("txtreason");
		int noOfDays = Integer.parseInt(request.getParameter("txtnodays"));
		String fromDate=request.getParameter("fromDate");
		String	toDate=request.getParameter("toDate");
		java.util.Date fromDt1 = new SimpleDateFormat("yyyy/MM/dd").parse(request.getParameter("fromDate"));
		java.util.Date toDt1 = new SimpleDateFormat("yyyy/MM/dd").parse(request.getParameter("toDate"));

		java.sql.Date fromDt = new java.sql.Date(fromDt1.getTime());
		java.sql.Date toDt = new java.sql.Date(toDt1.getTime());

		double consumeLeave = leaverecordsdao.getConsumeLeave(staffID);
		double earnLeave = getEarnLeave();
		double balanceLeave = earnLeave - consumeLeave;
		boolean status = false;
		isValid = leaverecordsdao.isValidDate(staffID, fromDate, toDate);

		if (isValid == false) {
		if (leaveType.equals("PL")) {
			if ((int) balanceLeave == 0) {
				dashboard.setMessage("Insufficient planned Leave balance! please apply for Leave Without Pay");
			} else {
				if ((int) balanceLeave >= noOfDays) {
					if (leaverecordsdao.applyLeave(staffID, fromDt, toDt, leaveType, noOfDays, leaveReason)) {
						dashboard.setMessage("Leave Applied successfully");
						/* mailUtil.sendMailForApproveLeave(user,user); */
						mailUtil.sendMailForApplyLeave(user);
					} else {
						dashboard.setMessage("Leave applied unsuccess");
					}
				} else {
					if (leaverecordsdao.applyLeave(staffID, fromDt, toDt, leaveType, (int) balanceLeave, leaveReason)) {
						if (leaverecordsdao.applyLeave(staffID, fromDt, toDt, "LWP", noOfDays - (int) balanceLeave,
								leaveReason)) {
							dashboard.setMessage("Leave applied successfully With Plan Leave" + (int) balanceLeave
									+ " and leave without pay" + (noOfDays - (int) balanceLeave));
							mailUtil.sendMailForApplyLeave(user);
							/* mailUtil.sendMailForApproveLeave(user,user); */
						}
					}
				}
			}
		} else if (leaveType.equals("LWP")) {
			if (leaverecordsdao.applyLeave(staffID, fromDt, toDt, leaveType, noOfDays, leaveReason)) {
				dashboard.setMessage( "Leave applied successfully");
				 mailUtil.sendMailForApplyLeave(user); 
				/* mailUtil.sendMailForApproveLeave(user,user); */
			} else {
				dashboard.setMessage("Leave applied unsuccess");
			}
		}
		return new ModelAndView("lms/ApplyLeave", "dashboard", dashboard);
		}
		else
			dashboard.setMessage("You have already applied leave for this period!");
			return new ModelAndView("lms/ApplyLeave", "dashboard",
					dashboard);
		
	}

	// get base URL
	public static String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

	public double getEarnLeave() {
		return (double) (2 * LocalDate.now().getDayOfYear()) / 30;
	}

}