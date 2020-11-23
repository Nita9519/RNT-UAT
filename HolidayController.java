/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	HolidayController.java
 **	Description		:	The java Class HolidayController is MultiActionController class that supports the 
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.dao.HolidayDao;
import ai.rnt.lms.dao.LeaveAllotmentDao;
import ai.rnt.lms.dao.LeaveRecordsDao;
import ai.rnt.lms.model.HolidayCalendar;
import ai.rnt.lms.model.HolidayCalendarRecords;
import ai.rnt.lms.model.LeaveAllotment;
import ai.rnt.lms.model.User;
import ai.rnt.lms.util.MailUtil;
import ai.rnt.main.dao.DBConnect;

@Controller
public class HolidayController {

	User user = null;
	int staffID = 0;
	HolidayCalendar hc = null;
	MailUtil mailUtil = new MailUtil();
	LeaveRecordsDao leaverecordsdao = new LeaveRecordsDao();
	HolidayDao holidaydao = new HolidayDao();
	LeaveAllotmentDao leaveallotmentdao = new LeaveAllotmentDao();
	private static final Logger log = LogManager.getLogger(HolidayController.class);

	@RequestMapping(value = "/holidaycalendar.do")
	public ModelAndView displayHolidayCalendar(HttpServletRequest request, HttpServletResponse res) { // sending holiday
		HolidayCalendarRecords holidayCalendarRecords = new HolidayCalendarRecords();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		ArrayList<HolidayCalendar> holidayList = null;
		try {
			if (user.isAdmin()) {
				holidayList = holidaydao.getHolidayDetails();
				holidayCalendarRecords.setHolidayCalendar(holidayList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			log.info("hi");
		}
		return new ModelAndView("lms/HolidayCalendar", "holidayCalendarRecords", holidayCalendarRecords);
	}

	@RequestMapping(value = "/holidaycalendarforEmp.do")
	public ModelAndView displayHolidayCalendarforEmp(HttpServletRequest request, HttpServletResponse res) { // sending
		HolidayCalendarRecords holidayCalendarRecords = new HolidayCalendarRecords();
		HttpSession session = request.getSession();
		user = (User) session.getAttribute("user");
		ArrayList<HolidayCalendar> holidayList = null;
		try {
			
				holidayList = holidaydao.getHolidayDetails();
				holidayCalendarRecords.setHolidayCalendar(holidayList);
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			log.info("hi");
		}
		return new ModelAndView("lms/HolidayCalendarforEmp", "holidayCalendarRecords", holidayCalendarRecords);
	}

	@RequestMapping(value = "/editholiday.do")
	public ModelAndView editHoliday(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException, ParseException {

		HolidayCalendar holidayCalendar = new HolidayCalendar();
		Boolean duplicateDateStatus = false;
		HolidayCalendarRecords holidayCalendarRecords = new HolidayCalendarRecords();
		String buttonAction = (String) request.getParameter("btnAction");
		ArrayList<HolidayCalendar> holidayList = null;

		// Retrieve admin staffID from session
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("user");
		int adminID = admin.getStaffID();
		String holidayNo = request.getParameter("holidayID");
		String occasion = request.getParameter("occassion");
		int holidayID = 0;

		try {

			String date = request.getParameter("date");
			if (!holidayNo.equals("") && holidayNo != null) {
				holidayID = Integer.parseInt(holidayNo);
				holidayCalendar.setHolidayID(holidayID);
			}
			java.util.Date uDate = new SimpleDateFormat("yyyy/MM/dd").parse(date);
			java.sql.Date sqlDate = new java.sql.Date(uDate.getTime());
			holidayCalendar.setDate(sqlDate);
			holidayCalendar.setOccasion(occasion);

			if (request.getParameter("optionalFlag") != null)
				holidayCalendar.setOptionalFlag(request.getParameter("optionalFlag"));

			holidayCalendar.setWeekDay(request.getParameter("weekday"));

			if (buttonAction.equals("Update")) {

				duplicateDateStatus = holidaydao.checkForDuplicateDate(sqlDate, occasion);
				if (!duplicateDateStatus) {

					holidaydao.updateHoliday(holidayCalendar, adminID);
					holidayCalendarRecords.setMessage("Holiday updated successfully");

				} else {
					holidayCalendarRecords.setDuplicateDateMessage(
							"There is an Holiday for the given Date:" + sqlDate + ", Please select new date.");
				}
			}
			/*
			 * else if (buttonAction.equals("Delete")) {
			 * 
			 * holidaydao.deleteHoliday(holidayCalendar, adminID);
			 * holidayCalendarRecords.setMessage("Holiday deleted successfully"); }
			 */else {

				duplicateDateStatus = holidaydao.checkForDuplicateDate(sqlDate, occasion);
				if (!duplicateDateStatus) {

					holidaydao.insertHoliday(holidayCalendar, adminID);

					holidayCalendarRecords.setMessage("Holiday added successfully");

				} else {
					holidayCalendarRecords.setDuplicateDateMessage(
							"There is an Holiday for the given Date:" + sqlDate + ", Please select new date.");
				}
			}
			holidayList = holidaydao.getHolidayDetails();
			holidayCalendarRecords.setHolidayCalendar(holidayList);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("lms/HolidayCalendar", "holidayCalendarRecords", holidayCalendarRecords);
	}

	@RequestMapping(value = "/deleteHolidayCalender.do")
	public ModelAndView deleteHolidayCalender(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException, ParseException {
		HolidayCalendar holidayCalendar = new HolidayCalendar();

		HolidayCalendarRecords holidayCalendarRecords = new HolidayCalendarRecords();
		log.info("in delete holiday controller...");
		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("user");
		int adminID = admin.getStaffID();
		String holidayNo = request.getParameter("holidayID");
		log.info("holidayNo..." + holidayNo);
		int holidayID = 0;
		try {

			if (!holidayNo.equals("") && holidayNo != null) {
				holidayID = Integer.parseInt(holidayNo);
				log.info("holidayID..." + holidayID);
				holidayCalendar.setHolidayID(holidayID);
				if (holidaydao.deleteHoliday(holidayCalendar, adminID)) {
					holidayCalendarRecords.setMessage("Holiday deleted successfully");
				} else {
					holidayCalendarRecords.setMessage("Holiday not deleted ");
				}
			}

			holidayCalendarRecords.setHolidayCalendar(holidaydao.getHolidayDetails());
		} catch (Exception e) {

		}

		return new ModelAndView("lms/HolidayCalendar", "holidayCalendarRecords", holidayCalendarRecords);
	}
}
