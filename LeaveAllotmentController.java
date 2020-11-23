/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	LeaveAllotmentController.java
 **	Description		:	The java Class LeaveAllotmentController is MultiActionController class that supports the 
 **						aggregation of multiple request-handling methods into one controller. This class is responsible 
 **						for handling a request and returning an appropriate model and view.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/
package ai.rnt.lms.controller;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.dao.HolidayDao;
import ai.rnt.lms.dao.LeaveAllotmentDao;
import ai.rnt.lms.dao.LeaveRecordsDao;
import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.lms.model.LeaveAllotment;
import ai.rnt.lms.model.LeaveMaster;
import ai.rnt.lms.model.User;
import ai.rnt.lms.util.MailUtil;

@Controller
public class LeaveAllotmentController {

	User user = null;
	int staffID = 0;
	HolidayCalendar hc = null;
	MailUtil mailUtil = new MailUtil();
	LeaveRecordsDao leaverecordsdao = new LeaveRecordsDao();
	HolidayDao holidaydao = new HolidayDao();
	LeaveAllotmentDao leaveallotmentdao = new LeaveAllotmentDao();

	private static final Logger log = LogManager.getLogger(LeaveAllotmentController.class);

	@RequestMapping(value = "/leaveallotment.do")
	public ModelAndView displayAllottedLeaves(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		LeaveMaster leavemaster = new LeaveMaster();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ArrayList<LeaveAllotment> leaveList = null;
		ArrayList<User> userList = null;
		try {
			if (user.isAdmin()) {
				leaveList = leaveallotmentdao.leaveAllotment();
				userList = leaveallotmentdao.getUsersForLeaveRecords();
				leavemaster.setLeaveList(leaveList);
				leavemaster.setUserList(userList);
				log.info("hiiiii");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (PropertyVetoException e) {

			e.printStackTrace();
		}
		return new ModelAndView("lms/LeaveAllotment", "leavemaster", leavemaster);
	}

	@RequestMapping(value = "/editleaveallotment.do")
	public ModelAndView editLeaveAllotment(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		LeaveMaster leavemaster = new LeaveMaster();
		LeaveAllotment leaveAllotment = new LeaveAllotment();
		ArrayList<LeaveAllotment> leaveList = null;
		String buttonAction = (String) request.getParameter("btnAction");
		ArrayList<User> userList = null;
		boolean updateStatus = false;

		try {
			String ID = request.getParameter("staffId");
			String FL = request.getParameter("FL");
			String PL = request.getParameter("PL");

			int staffID = Integer.parseInt(ID);
			int flexiLeave = Integer.parseInt(FL);
			int priviledgeLeave = Integer.parseInt(PL);
			leaveAllotment.setStaffID(staffID);
			leaveAllotment.setFlexiLeave(flexiLeave);
			leaveAllotment.setPriviledgeLeave(priviledgeLeave);
			HttpSession session = request.getSession();
			User admin = (User) session.getAttribute("user");
			int adminID = admin.getStaffID();

			if (buttonAction.equals("Update")) {

				if (leaveallotmentdao.updateLeaveDetails(leaveAllotment, adminID)) {
					leavemaster.setMessage("Leave updated successfully");
				} else {
					leavemaster.setMessage("Leave not updated ");
				}
			} else {
				if (leaveallotmentdao.checkEmployeeAllotmentDup(leaveAllotment)) {
					leavemaster.setMessage("Employee already exists");
				} else {
					if (leaveallotmentdao.insertLeaveDetails(leaveAllotment, adminID)) {
						leavemaster.setMessage("Leave alloted successfully");
					} else {
						leavemaster.setMessage("Leave not alloted ");
					}
				}
			}
			leavemaster.setLeaveList(leaveallotmentdao.leaveAllotment());
			leavemaster.setUserList(leaveallotmentdao.getUsersForLeaveRecords());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
		}

		return new ModelAndView("lms/LeaveAllotment", "leavemaster", leavemaster);
	}

	@RequestMapping(value = "/deleteleaveallotment.do")
	public ModelAndView deleteleaveallotment(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		LeaveMaster leavemaster = new LeaveMaster();
		LeaveAllotment leaveAllotment = new LeaveAllotment();

		try {
			String ID = request.getParameter("staffId");
			int staffID = Integer.parseInt(ID);
			log.info("staffId in leave delete ::: " + staffID);

			leaveAllotment.setStaffID(staffID);

			HttpSession session = request.getSession();
			User admin = (User) session.getAttribute("user");
			int adminID = admin.getStaffID();
			if (leaveallotmentdao.deleteLeaveDetails(leaveAllotment, adminID)) {
				leavemaster.setMessage("Leave deleted successfully");
			} else {
				leavemaster.setMessage("Leave not  deleted ");
			}

		} catch (Exception e) {

		}
		leavemaster.setLeaveList(leaveallotmentdao.leaveAllotment());
		leavemaster.setUserList(leaveallotmentdao.getUsersForLeaveRecords());
		return new ModelAndView("lms/LeaveAllotment", "leavemaster", leavemaster);
	}
}
