/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	DashboardController.java
 **	Description		:	The java Class DashboardController is MultiActionController class that supports the 
 **						aggregation of multiple request-handling methods into one controller. This class is responsible 
 **						for handling a request and returning dashboard model and dashboard list view.
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ai.rnt.lms.model.User;
import ai.rnt.tms.dao.DashboardDao;
import ai.rnt.tms.dao.TimesheetDao;
import ai.rnt.tms.model.Attendance;
import ai.rnt.tms.model.Dashboard;
import ai.rnt.tms.model.Timesheet;
import ai.rnt.tms.model.TimesheetRecords;
import ai.rnt.tms.util.Util;

@Controller
public class TmsDashboardController {

	User user = null;
	int staffID = 0;
	static DashboardDao dashboarddao = new DashboardDao();
	TimesheetDao timesheetDao = new TimesheetDao();
	int applicationID = 3;

	private static final Logger log = LogManager.getLogger(TmsDashboardController.class);

	@RequestMapping(value = "/tmsdash.do")
	public ModelAndView dashBoard(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException, ParseException {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffID = user.getStaffID();

		// valid employee check method is call
		try {
			dashboard.setEmpAttendanceListForEmp(dashboarddao.getAttendanceList(user.getStaffID()));
			
			 
			  dashboard.setTimesheetLastWeekSubmited(dashboarddao.getTimesheetSubmittedList(user.
			  getStaffID()));
			  dashboard.setTimesheetSubmitedForUser(dashboarddao.getTimesheetSubmittedListForUser(user.getStaffID()));
			  dashboard.setTimesheetLastWeekManager(dashboarddao.getTimesheetSubmittedListForManager(user.getStaffID()));
			  dashboard.setTimesheetCurrentWeekManager(dashboarddao.getTimesheetSubmittedListForManagerCurrentWeek(user.getStaffID()));
			  dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(user.getStaffID()));
			  /*
			 * 
			 * getTimesheetSubmittedListForManager 
			 * dashboard.setTimesheetList( Util.timesheetsListPreproccessing(timesheetDao.
			 * getTimesheetsListForViewHistory(staffID)));
			 * 
			 * dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(
			 * staffID)); dashboard.setEffortsMinutesList();
			 * dashboard.setEffortsHoursList(); dashboard.setDateList();
			 * dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
			 * dashboard.setTasks(timesheetDao.getTaskDetails());
			 */
		} catch (SQLException e) {
		}
		return new ModelAndView("tms/MainDashboard", "dashboard", dashboard);
	}

	@RequestMapping(value = "/tmsdash2.do")
	public ModelAndView dashBoardrecord(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException, ParseException {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		ModelAndView mv = new ModelAndView();
		int staffID = user.getStaffID();

		// valid employee check method is call
		try {
			dashboard.setEmpAttendanceListForEmp(dashboarddao.getAttendanceList(user.getStaffID()));
			dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(user.getStaffID()));

			dashboard.setTimesheetList(
					Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));

			dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
			dashboard.setEffortsMinutesList();
			dashboard.setEffortsHoursList();
			dashboard.setDateList();
			dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
			dashboard.setTasks(timesheetDao.getTaskDetails());
			TimesheetRecords timesheetRecords = new TimesheetRecords();
			// get staffID from session

		

			timesheetRecords.setEffortsMinutesList();
			timesheetRecords.setEffortsHoursList();
			timesheetRecords.setStaffID(staffID);

			timesheetRecords.setTimesheetList(timesheetDao.getTimesheetsForProcessing(staffID));
			timesheetRecords.setProjects(timesheetDao.getProjectDetails(staffID));
			timesheetRecords.setTasks(timesheetDao.getTaskDetails());

			timesheetRecords.setDateList();
			timesheetRecords.setTsSubmitDateList(Util.gettsSubmitListDate(timesheetDao.getSubmitTimesheetDate(staffID)));
			timesheetDao.getTimesheetsTotalsOfHourMin(staffID, timesheetRecords);

			mv.addObject("dashboard", dashboard);
			mv.addObject("timesheetRecords", timesheetRecords);
			mv.setViewName("tms/Dashboard");
		} catch (SQLException e) {
			
		}
		return mv;
	}
	@PostMapping(value = "/upload.do")
	public ModelAndView uploadExcel(@RequestParam("excelFile") MultipartFile file, HttpServletRequest request,RedirectAttributes ra)
			throws IOException, SQLException, PropertyVetoException, ParseException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ArrayList<Attendance> attendanceList = null;
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		if (!file.isEmpty()) {
			InputStream inputStream = new ByteArrayInputStream(file.getBytes());
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			attendanceList = readxlsFile(br);
		}
		Dashboard dashboard = new Dashboard();
		String message =null;
		int[] arr = dashboarddao.insertAttendance(attendanceList);
		if(arr.length == 0) {
			dashboard.setMessage("data not inserted");
			message = "data not inserted";
		}else {
			//dashboard.setMessage(arr.length+" data inserted");
			message = arr.length+ " data inserted";
		}
		ra.addFlashAttribute("message",message);
		
		/*
		 * try {
		 * dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(user.getStaffID
		 * ()));
		 * dashboard.setTimesheetList(Util.timesheetsListPreproccessing(timesheetDao.
		 * getTimesheetsListForViewHistory(staffID)));
		 * dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(
		 * staffID)); dashboard.setEffortsMinutesList();
		 * dashboard.setEffortsHoursList(); dashboard.setDateList();
		 * dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
		 * dashboard.setTasks(timesheetDao.getTaskDetails()); } catch (SQLException e) {
		 * }
		 */
		return new ModelAndView("redirect:/upload.do");
		
	}
	
	@GetMapping(value = "/upload.do")
	public ModelAndView uploadExcelForMapping(HttpServletRequest request)
			throws IOException, SQLException, PropertyVetoException, ParseException {
		
		Dashboard dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(user.getStaffID()));
		dashboard.setTimesheetList(Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
		dashboard.setTimesheetListDisp(timesheetDao.getTimesheetsListForViewHistory(staffID));
		dashboard.setEffortsMinutesList();
		dashboard.setEffortsHoursList();
		dashboard.setDateList();
		dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
		dashboard.setTasks(timesheetDao.getTaskDetails());
		return new ModelAndView("tms/MainDashboard", "dashboard", dashboard);
	}
	

	private static ArrayList<Attendance> readxlsFile(BufferedReader br)
			throws IOException, SQLException, PropertyVetoException, ParseException {

		ArrayList<Attendance> attendanceList = new ArrayList<Attendance>();
		Attendance attendance = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String line = "";
		String cvsSplitBy = ",";
		try {
			// br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] country = line.split(cvsSplitBy);
				attendance = new Attendance();

				if (isInteger(country[0])) {
					if (country.length == 4) {
						attendance.setStaffID(Integer.parseInt(country[0].trim()));
						attendance.setfName(country[1].trim());		
						attendance.setInDate(java.sql.Date.valueOf(LocalDate.parse(country[2].trim(), formatter)));
						attendance.setInTime(java.sql.Time.valueOf(country[3].trim()));
					} else if (country.length == 5) {
						attendance.setStaffID(Integer.parseInt(country[0].trim()));
						attendance.setfName(country[1].trim());

						attendance.setInDate(java.sql.Date.valueOf(LocalDate.parse(country[2].trim(), formatter)));
						attendance.setInTime(java.sql.Time.valueOf(country[3].trim()));
						attendance.setOutTime(java.sql.Time.valueOf(country[4].trim()));
					}
					attendanceList.add(attendance);
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return attendanceList;
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	@RequestMapping(value = "/updtsdash.do")
	public ModelAndView updateTtimesheetFromDash(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException, ParseException {
		Dashboard dashboard = new Dashboard();
		Timesheet timesheet = new Timesheet();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int staffID = user.getStaffID();

		java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("timesheetdate"));
		java.sql.Date tsDate = new java.sql.Date(uDate.getTime());

		timesheet = new Timesheet();
		timesheet.setStaffID(staffID);
		timesheet.setTimesheetDate(tsDate);
		timesheet.setTaskID(Integer.parseInt(request.getParameter("taskID")));
		timesheet.setProjectID(Integer.parseInt(request.getParameter("projectID")));
		timesheet.setComment(request.getParameter("comment"));
		timesheet.setEffortHours(Integer.parseInt(request.getParameter("effortHour")));
		timesheet.setEffortMinutes(Integer.parseInt(request.getParameter("effortMinute")));
		timesheet.setStatus(request.getParameter("action"));
		if (timesheet.getStatus().equals("Submitted")) {
			timesheetDao.updateTaskSheet(timesheet);
		} else if (timesheet.getStatus().equals("Saved")) {
			if (timesheetDao.checkForDuplicacy(timesheet)) {
				timesheetDao.updateTaskSheet(timesheet);
			} else {
				timesheetDao.insertTimesheet(timesheet);
			}
		}
		// valid employee check method is call
		try {
			dashboard.setEmpAttendanceList(dashboarddao.getAttendanceList(user.getStaffID()));
			dashboard.setTimesheetList(
					Util.timesheetsListPreproccessing(timesheetDao.getTimesheetsListForViewHistory(staffID)));
			dashboard.setEffortsMinutesList();
			dashboard.setEffortsHoursList();
			dashboard.setDateList();
			dashboard.setProjects(timesheetDao.getProjectDetails(staffID));
			dashboard.setTasks(timesheetDao.getTaskDetails());

		} catch (SQLException e) {
			log.info("");
		}
		return new ModelAndView("tms/MainDashboard", "dashboard", dashboard);
	}

	// get base URL
	public static String getBaseURL(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}
