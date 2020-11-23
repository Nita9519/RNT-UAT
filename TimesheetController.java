/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	TimesheetController.java
 **	Description		:	The java Class TimesheetController is MultiActionController class that supports the 
 **						aggregation of multiple request-handling methods into one controller. This class is responsible 
 **						for handling a request and returning timesheet model and timesheet list view.
 **	Author			:	Chinmay Wyawahare
 **	Created Date	:	Friday October 30, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  	Version 		Description:
 **	-------			-----------------	--------		------------
 ** 06/10/2017      Chinmay Wyawahare	1.0           	Created
 *********************************************************************************************************************/

package ai.rnt.tms.controller;

import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ai.rnt.tms.dao.DashboardDao;
import ai.rnt.tms.dao.TimesheetDao;
import ai.rnt.tms.model.Dashboard;
import ai.rnt.tms.model.Timesheet;
import ai.rnt.tms.model.TimesheetRecords;
import ai.rnt.lms.model.User;
import ai.rnt.tms.util.Util;

@Controller
public class TimesheetController {

	User user = null;
	int staffID = 0;
	TimesheetDao timesheetDao = new TimesheetDao();
	DashboardDao dashboarddao = new DashboardDao();

	private static final Logger log = LogManager.getLogger(TimesheetController.class);

	@RequestMapping(value = "/filltimesheet.do")
	public ModelAndView fillTimesheet(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		TimesheetRecords timesheetRecords = new TimesheetRecords();
		// get staffID from session

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		timesheetRecords.setEffortsMinutesList();
		timesheetRecords.setEffortsHoursList();
		timesheetRecords.setStaffID(staffID);

		timesheetRecords.setTimesheetList(timesheetDao.getTimesheetsForProcessing(staffID));
		timesheetRecords.setProjects(timesheetDao.getProjectDetails(staffID));
		timesheetRecords.setTasks(timesheetDao.getTaskDetails());

		timesheetRecords.setDateList();
		timesheetRecords.setTsSubmitDateList(Util.gettsSubmitListDate(timesheetDao.getSubmitTimesheetDate(staffID)));
		timesheetDao.getTimesheetsTotalsOfHourMin(staffID, timesheetRecords);

		return new ModelAndView("tms/addnewtimesheet", "timesheetRecords", timesheetRecords);
	}

	@RequestMapping(value = "/edittimesheetfordatepage.do")
	public ModelAndView editTimesheet(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		TimesheetRecords timesheetRecords = new TimesheetRecords();
		// get staffID from session

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		String date = request.getParameter("timesheetdate");

		java.util.Date uDate = new SimpleDateFormat("dd-MMM-yyyy").parse(date);
		java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(uDate);
		timesheetRecords.setDateforedit(strDate);
		/* timesheetRecords.setDateForEdit(tsDate); */
		timesheetRecords.setEffortsMinutesList();
		timesheetRecords.setEffortsHoursList();
		timesheetRecords.setStaffID(staffID);

		timesheetRecords.setTimesheetList(timesheetDao.getTimesheetsForEdit(staffID, tsDate));
		timesheetRecords.setProjects(timesheetDao.getProjectDetails(staffID));
		timesheetRecords.setTasks(timesheetDao.getTaskDetails());

		timesheetRecords.setDateList();
		timesheetRecords.setTsSubmitDateList(Util.gettsSubmitListDate(timesheetDao.getSubmitTimesheetDate(staffID)));
		timesheetDao.getTimesheetsTotalsOfHourMin(staffID, timesheetRecords);

		return new ModelAndView("tms/edittimesheet", "timesheetRecords", timesheetRecords);
	}

	@RequestMapping(value = "/submitnewtimesheet.do")
	public ModelAndView submitTimesheet(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		String action = request.getParameter("action");

		Timesheet timesheet = null;
		Dashboard dashboard = new Dashboard();
		int totalHours = 0;
		int i = 1;
		while (true) {

			if (request.getParameter("projectID" + i) == null)
				break;

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("timesheetdate" + i));
			java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

			timesheet = new Timesheet();
			totalHours = totalHours + Integer.parseInt(request.getParameter("efforthours" + i));
			timesheet.setStaffID(staffID);
			timesheet.setTimesheetDate(tsDate);
			timesheet.setTaskID(Integer.parseInt(request.getParameter("taskID" + i)));
			timesheet.setProjectID(Integer.parseInt(request.getParameter("projectID" + i)));
			timesheet.setDescription(request.getParameter("description" + i));
			timesheet.setComment(request.getParameter("comment"));
			timesheet.setEffortHours(Integer.parseInt(request.getParameter("efforthours" + i)));
			timesheet.setEffortMinutes(Integer.parseInt(request.getParameter("effortminutes" + i)));
			timesheet.setStatus(request.getParameter("action"));
			timesheet.setDataStatus(request.getParameter("datestatus" + i));

			if (timesheet.getDataStatus().equals("true")) {

				if (timesheet.getStatus().equals("Submitted")) {

					if (totalHours > 24) {
						dashboard.setMessage("Efforts hours can't be more than 24 hrs");
						log.info("submitnewtimesheet ::" + dashboard.getMessage());
					} else {
						if (timesheetDao.checkForDuplicacy(timesheet)) {
							if (timesheetDao.updateTaskSheet(timesheet)) {
								dashboard.setMessage("Tasksheet submitted successfully");
								log.info("submitnewtimesheet ::" + dashboard.getMessage());
							}

						} else {
							if (timesheetDao.insertTimesheet(timesheet)) {
								dashboard.setMessage("TaskSheet inserted successfully");
								log.info("submitnewtimesheet ::" + dashboard.getMessage());
							}

						}

					}
				} else if (timesheet.getStatus().equals("Saved")) {

					if (timesheetDao.checkForDuplicacy(timesheet)) {
						if (timesheetDao.updateTaskSheet(timesheet)) {
							dashboard.setMessage("Tasksheet submitted successfully");
							log.info("submitnewtimesheet ::" + dashboard.getMessage());
						}

					} else {
						if (timesheetDao.insertTimesheet(timesheet)) {
							dashboard.setMessage("TaskSheet saved successfully");
							log.info("submitnewtimesheet ::" + dashboard.getMessage());
						}

					}

				}

			}

			i++;

		}

		// valid employee check method is call

		dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(staffID));
		dashboard.setTimesheetList(
				Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setEffortsMinutesList();
		dashboard.setEffortsHoursList();
		dashboard.setDateList();
		dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
		dashboard.setTasks(timesheetDao.getTaskDetails());

		return new ModelAndView("tms/Dashboard", "dashboard", dashboard);

	}

	@RequestMapping(value = "/subtimesheetfordate.do")
	public ModelAndView submitForDateTimesheet(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		String action = request.getParameter("action");
		int total = 0;
		Timesheet timesheet = null;
		Dashboard dashboard = new Dashboard();
		int i = 1;
		while (true) {

			if (request.getParameter("projectID" + i) == null)
				break;

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("timesheetdate" + i));
			java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

			timesheet = new Timesheet();
			total = total + Integer.parseInt(request.getParameter("efforthours" + i));

			timesheet.setStaffID(staffID);
			timesheet.setTimesheetDate(tsDate);
			timesheet.setTaskID(Integer.parseInt(request.getParameter("taskID" + i)));
			timesheet.setProjectID(Integer.parseInt(request.getParameter("projectID" + i)));
			timesheet.setDescription(request.getParameter("description" + i));
			timesheet.setComment(request.getParameter("comment"));
			timesheet.setEffortHours(Integer.parseInt(request.getParameter("efforthours" + i)));
			timesheet.setEffortMinutes(Integer.parseInt(request.getParameter("effortminutes" + i)));

			timesheet.setStatus(request.getParameter("action"));
			timesheet.setDataStatus(request.getParameter("datestatus" + i));

			if (timesheet.getStatus().equals("Submitted")) {

				if (total > 24) {
					dashboard.setMessage("Efforts hours can't be more than 24 hrs");
				} else {
					if (timesheetDao.checkForDuplicacy(timesheet)) {
						if (timesheetDao.updateTaskSheet(timesheet)) {
							dashboard.setMessage("Timesheet updated successfully");
							log.info("subtimesheetfordate::" + dashboard.getMessage());
						}

					} else {
						if (timesheetDao.insertTimesheet(timesheet)) {
							dashboard.setMessage("Timesheet inserted");
							log.info("subtimesheetfordate ::" + dashboard.getMessage());
						}

					}
				}

			} else if (timesheet.getStatus().equals("Saved")) {

				if (timesheetDao.checkForDuplicacy(timesheet)) {
					if (timesheetDao.updateTaskSheet(timesheet)) {
						dashboard.setMessage("Timesheet updated successfully");
						log.info("subtimesheetfordate ::" + dashboard.getMessage());
					}

				}

				else {
					if (timesheetDao.insertTimesheet(timesheet)) {
						dashboard.setMessage("Timesheet inserted");
						log.info("subtimesheetfordate ::" + dashboard.getMessage());
					}

				}
			}

			i++;
		}

		// valid employee check method is call

		dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(staffID));
		dashboard.setTimesheetList(
				Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setEffortsMinutesList();
		dashboard.setEffortsHoursList();
		dashboard.setDateList();
		dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
		dashboard.setTasks(timesheetDao.getTaskDetails());

		return new ModelAndView("tms/Dashboard", "dashboard", dashboard);

	}

	@RequestMapping(value = "/deletetimesheetrecord.do")
	public ModelAndView deleteTimeSheetRecord(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		Timesheet timesheet = null;
		java.sql.Date tsDate = null;

		// get staffID from session
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		if (request.getParameter("timesheetdate") != null) {
			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("timesheetdate"));
			tsDate = new java.sql.Date(uDate.getTime());
		}
		Dashboard dashboard = new Dashboard();
		timesheet = new Timesheet();
		timesheet.setStaffID(staffID);
		timesheet.setProjectID(Integer.parseInt(request.getParameter("projectID")));
		timesheet.setTaskID(Integer.parseInt(request.getParameter("taskID")));
		timesheet.setTimesheetDate(tsDate);

		// timesheetDao.deleteTaskSheet(timesheet);
		if (timesheetDao.deleteTaskSheet(timesheet)) {
			dashboard.setMessage("TimeSheet deleted successfully");
			log.info("deletetimesheetrecord ::" + dashboard.getMessage());
		} else {
			dashboard.setMessage("TimeSheet not deleted ");
			log.info("deletetimesheetrecord ::" + dashboard.getMessage());
		}

		dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(staffID));
		dashboard.setTimesheetList(
				Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setEffortsMinutesList();
		dashboard.setEffortsHoursList();
		dashboard.setDateList();
		dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
		dashboard.setTasks(timesheetDao.getTaskDetails());

		return new ModelAndView("tms/Dashboard", "dashboard", dashboard);

	}

	@RequestMapping(value = "/submittimesheetforapproval.do")
	public ModelAndView submitTimesheetForApproval(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		Timesheet timesheet = new Timesheet();
		Dashboard dashboard = new Dashboard();

		// get staffID from session
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		String timesheetDate = request.getParameter("timesheetDate");
		String status = request.getParameter("status");

		java.util.Date uDate = new SimpleDateFormat("dd-MMM-yyyy").parse(timesheetDate);
		java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

		timesheet.setTimesheetDate(tsDate);
		timesheet.setStatus(status);
		timesheet.setStaffID(staffID);

		timesheetDao.updateTaskStatus(timesheet);
		// valid employee check method is call
		try {

			dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(userSession.getStaffID()));
			dashboard.setTimesheetList(
					Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
			dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
			dashboard.setEffortsMinutesList();
			dashboard.setEffortsHoursList();
			dashboard.setDateList();
			dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
			dashboard.setTasks(timesheetDao.getTaskDetails());

		} catch (SQLException e) {

		}
		return new ModelAndView("tms/Dashboard", "dashboard", dashboard);
	}

	@RequestMapping(value = "/displaytimesheetsforapproval.do")
	public ModelAndView displayTimesheetsForApproval(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Dashboard dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "ession is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		dashboard.setTimesheetList(Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsForApproval(staffID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsForApproval(staffID));

		return new ModelAndView("tms/ApproveTimesheet", "dashboard", dashboard);
	}

	@RequestMapping(value = "/approvetimesheet.do")
	public ModelAndView approveTimesheet(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		Timesheet timesheet = new Timesheet();
		int staffID = 0;
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "ession is Expired Please Login Again");
		int adminID = userSession.getStaffID();
		Dashboard dashboard = new Dashboard();

		String tDate = request.getParameter("timedate");
		String staffid = request.getParameter("staffid");
		String action = request.getParameter("action");
		java.util.Date uDate = new SimpleDateFormat("dd-MMM-yyyy").parse(tDate);
		java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

		log.info(tsDate);
		try {
			staffID = Integer.parseInt(staffid);
		} catch (NumberFormatException e) {
		}

		String status = timesheetDao.approveTimesheet(tsDate, adminID, staffID, action);
		if (status.equalsIgnoreCase("Approved")) {
			dashboard.setMessage("Timesheet approved succesfully");

		} else {
			dashboard.setMessage("Timesheet rejected succesfully");
		}

		dashboard.setTimesheetList(Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsForApproval(adminID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsForApproval(adminID));

		return new ModelAndView("tms/ApproveTimesheet", "dashboard", dashboard);
	}

	@RequestMapping(value = "/displayhistory.do")
	public ModelAndView displayHistory(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		// dashboard.setTimesheetList(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setMonthLists(timesheetDao.getmonthList());
		;
		dashboard.setTimesheetList(
				Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));

		return new ModelAndView("tms/ViewHistory", "dashboard", dashboard);
	}

	@RequestMapping(value = "/displayEmployeehistory.do")
	public ModelAndView displayEmployeeHistory(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "session is Expired Please Login Again");
		int staffID = userSession.getStaffID();

		// dashboard.setTimesheetList(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setMonthLists(timesheetDao.getmonthList());
		;
		dashboard.setTimesheetList(
				Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewEmployeeHistory(staffID)));

		return new ModelAndView("tms/ViewEmployeeHistory", "dashboard", dashboard);
	}

}
