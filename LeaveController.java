package ai.rnt.lms.controller;

import java.beans.PropertyVetoException;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.dao.HolidayDao;
import ai.rnt.lms.dao.LeaveAllotmentDao;
import ai.rnt.lms.dao.LeaveRecordsDao;
import ai.rnt.lms.model.Dashboard;
import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.lms.model.LeaveRecord;
import ai.rnt.lms.model.User;
import ai.rnt.lms.util.MailUtil;

@Controller
public class LeaveController {

	User user = null;
	int staffID = 0;
	HolidayCalendar hc = null;
	MailUtil mailUtil = new MailUtil();
	LeaveRecord leaveRecord = null;
	LeaveRecordsDao leaverecordsdao = new LeaveRecordsDao();
	HolidayDao holidaydao = new HolidayDao();
	LeaveAllotmentDao leaveallotmentdao = new LeaveAllotmentDao();
	Dashboard dashboard = new Dashboard();
	float availableLeavesInDays;
	private static final Logger log = LogManager.getLogger(LeaveController.class);

	@RequestMapping(value = "/displayleavestobeapproved.do")
	public ModelAndView displayLeavesToBeApproved(HttpServletRequest request, HttpServletResponse res) // sending the
																										// approve leave
			// date
			throws SQLException, PropertyVetoException {

		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		ArrayList<LeaveRecord> leaverecordsListForApproval = null;
		LeaveRecord leaveRecord = new LeaveRecord();
		try {
			if (user.isManager()) {
				leaverecordsListForApproval = (ArrayList<LeaveRecord>) leaverecordsdao
						.getLeaveRecordsforApproval(user.getStaffID());
				leaveRecord.setLeaverecordsListForApproval(leaverecordsListForApproval);
				log.info(" list for approval size for admin :::::::"
						+ leaveRecord.getLeaverecordsListForApproval().size());
				log.info(leaveRecord.getLeaverecordsListForApproval());
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return new ModelAndView("lms/ApproveLeave", "leaveRecord", leaveRecord);

	}

	@RequestMapping(value = "/displayleaveshistoryofemployee.do")
	public ModelAndView displayleaveshistoryofemployee(HttpServletRequest request, HttpServletResponse res) // sending
																											// the
			// approve leave
			// date
			throws SQLException, PropertyVetoException {

		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		ArrayList<LeaveRecord> displayleaveshistoryofemployee = null;
		LeaveRecord leaveRecord = new LeaveRecord();
		try {
			if (user.isManager()) {
				displayleaveshistoryofemployee = (ArrayList<LeaveRecord>) leaverecordsdao
						.getdisplayleaveshistoryofemployee(user.getStaffID());
				leaveRecord.setMonthLists(leaverecordsdao.getmonthList());
				leaveRecord.setDisplayleaveshistoryofemployee(displayleaveshistoryofemployee);
				log.info(" list for approval size for admin :::::::"
						+ leaveRecord.getDisplayleaveshistoryofemployee().size());
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

		return new ModelAndView("lms/employeehistory", "leaveRecord", leaveRecord);

	}

	@RequestMapping(value = "/applyleave.do")
	public ModelAndView applyLeave(HttpServletRequest request, HttpServletResponse res) // sending apply page page
			throws SQLException, PropertyVetoException {

		return new ModelAndView("lms/ApplyLeave");
	}

	@RequestMapping(value = "/approveleave.do")
	public ModelAndView approveLeave(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, MessagingException {

		int sequenceNo = 0;
		int staffID = 0;

		String StaffID = request.getParameter("staffID");
		String sequenceid = request.getParameter("sequenceno");
		String buttonStatus = request.getParameter("approvebutton");
		String managerComment = request.getParameter("txtcomments");
		LeaveRecord leaveRecord = new LeaveRecord();

		HttpSession session = request.getSession();
		User manager = (User) session.getAttribute("user");
        manager.setUserID(StaffID);
		try {
			staffID = Integer.parseInt(StaffID);
			sequenceNo = Integer.parseInt(sequenceid);
		} catch (NumberFormatException e) {
		}
		leaveRecord.setStaffID(staffID);
		leaveRecord.setSequenceNo(sequenceNo);
		leaveRecord.setManagerComment(managerComment);
		leaveRecord.setStatus(buttonStatus);
		leaveRecord.setManagerID(manager.getStaffID());

		String status = leaverecordsdao.updateLeaveApproval(leaveRecord);

		if (status.equalsIgnoreCase("Approved")) {
			leaveRecord.setMessage("Leave Approved successfully");
			mailUtil.sendMailForApproveLeave(manager, user);
		} else {
			leaveRecord.setMessage("Leave Rejected successfully");
			mailUtil.sendMailForRejectLeave(manager, user);
		}
		User employee = leaverecordsdao.getEmployeeDetails(staffID);
		ArrayList<LeaveRecord> leaverecordsListForApproval = null;
		try {
			if (user.isManager()) {
				leaverecordsListForApproval = (ArrayList<LeaveRecord>) leaverecordsdao
						.getLeaveRecordsforApproval(manager.getStaffID());
				leaveRecord.setLeaverecordsListForApproval(leaverecordsListForApproval);
				log.info(" list for approval size ::::::::::" + leaveRecord.getLeaverecordsListForApproval().size());

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (PropertyVetoException e) {

			e.printStackTrace();
		}

		/*
		 * if (leaverecordsdao.updateLeaveApproval(leaveRecord)) {
		 * leaveRecord.setMessage("Leave processed successfully"); }else {
		 * leaveRecord.setMessage("Leave  not processed successfully"); }
		 */
		return new ModelAndView("lms/ApproveLeave", "leaveRecord", leaveRecord);

	}

	/*
	 * private int availableDay(int staffID2, float numberOfDays, float
	 * dayofLeaveSameMonth) { float availableLeaves; float availableLeavesInDays ;
	 * SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy"); String
	 * joiningDateString = "1 12 2019"; Date currentDate=new Date();
	 * 
	 * 
	 * try { Date joiningDate = (Date) myFormat.parse(joiningDateString); // String
	 * currentDate =myFormat.format(currentDateString); long difference =
	 * currentDate.getTime() - joiningDate.getTime(); float daysBetween =
	 * (difference / (1000*60*60*24));
	 * availableLeaves=(daysBetween*dayofLeaveSameMonth);
	 * 
	 * availableLeavesInDays = ( availableLeaves / 24 );
	 * 
	 * if(availableLeavesInDays > numberOfDays) {
	 * availableLeavesInDays=availableLeavesInDays-numberOfDays;
	 * 
	 * availableLeavesInDays=leaverecordsdao.applyLeave(staffID, fromDate, toDate,
	 * leaveType, noOfDays, leaveReason, emailID,availableLeavesInDays);
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * } catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 */

	public Dashboard getAllDetails(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		Dashboard dashboard = new Dashboard();

		leaveallotmentdao.getLeaveDetails(user.getStaffID(), dashboard); // calculate leave detail
		dashboard.setLeaveRecords(leaverecordsdao.getLeaveRecordsDashboard(user.getStaffID())); // assign(set) the list
																								// data
		dashboard.setHolidays((ArrayList<HolidayCalendar>) holidaydao.getHolidayCalender());
		leaverecordsdao.getUsedLeaves(user.getStaffID(), dashboard); // calculate used leave detail

		return dashboard;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calculateholidays.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject calculateholidays(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		JSONObject mainJson = new JSONObject();
		String fromDate = req.getParameter("fromDate");
		log.info("fromDate==" + fromDate);
		String toDate = req.getParameter("toDate");

		int isHolidayN = leaverecordsdao.isHolidayN(fromDate, toDate);
		log.info("isHolidayN===" + isHolidayN);

		mainJson.put("leavedays", isHolidayN);

		return mainJson;
	}

}
