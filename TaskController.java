/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	TIMESHEET MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	TaskController.java
 **	Description		:	The java Class TaskController is MultiActionController class that supports the 
 **						aggregation of multiple request-handling methods into one controller. This class is responsible 
 **						for handling a request and returning task model and view.
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
import ai.rnt.tms.dao.TaskDao;
import ai.rnt.tms.model.Task;

@Controller
public class TaskController {

	TaskDao taskDao = new TaskDao();
	private static final Logger log = LogManager.getLogger(TaskController.class);

	@RequestMapping(value = "/taskmaster.do")
	public ModelAndView taskMaster(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		ArrayList<Task> taskList = null;
		Task task = new Task();
		try {
			taskList = taskDao.getTaskDetails();
			task.setTaskList(taskDao.getTaskDetails());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			log.info("hi");
		}
		return new ModelAndView("tms/TaskMaster", "task", task);
	}

	@RequestMapping(value = "/edittask.do")
	public ModelAndView editTask(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		@SuppressWarnings("unused")
		boolean insertStatus = false;

		String taskName = request.getParameter("taskname");
		log.info("TaskName " + taskName);
		String taskID = request.getParameter("taskid");
		String updatedTaskName = request.getParameter("taskName");

		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("user");
		if (admin == null) {
			return new ModelAndView("tms/login", "message", "Session is Expired Please Login Again");
		}
		int adminID = admin.getStaffID();
		log.info("Admin staffID: " + adminID);

		Task task = new Task();
		task.setTaskName(taskName);

		if (buttonAction.equals("Update")) {
			task.setTaskID(Integer.parseInt(taskID));
			task.setTaskName(updatedTaskName);
			if (taskDao.updateTask(task, adminID)) {
				task.setMessage("Task name updated successfully");

			} else {
				if (taskDao.checkForDuplicacy(task)) {
					task.setMessage("Task name already exists");
				} else {
					task.setMessage("Task name not updated ");
				}
			}

		} /*
			 * else if (buttonAction.equals("Delete")) {
			 * 
			 * task.setTaskID(Integer.parseInt(taskID)); task.setTaskName(updatedTaskName);
			 * if (taskDao.deleteTask(task, adminID)) {
			 * task.setMessage("Task name deleted successfully"); } else {
			 * task.setMessage("Task name not deleted "); }
			 * 
			 * }
			 */ else if (buttonAction.equals("Add")) {

			if (taskDao.insertTask(task, adminID)) {
				task.setMessage("Task name inserted succssfully!");
			} else {}

		}
		task.setTaskList(taskDao.getTaskDetails());
		return new ModelAndView("tms/TaskMaster", "task", task);
	}

	@RequestMapping(value = "/deleteTaskName.do")
	public ModelAndView deleteTaskName(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		HttpSession session = request.getSession();
		User admin = (User) session.getAttribute("user");
		if (admin == null) {
			return new ModelAndView("tms/login", "message", "Session is Expired Please Login Again");
		}
		int adminID = admin.getStaffID();
		log.info("Admin staffID: " + adminID);

		Task task = new Task();
		try {
			String taskID = request.getParameter("taskid");
			log.info(" in task delete controller");
			task.setTaskID(Integer.parseInt(taskID));

			if (taskDao.deleteTask(task, adminID)) {
				task.setMessage("Task name deleted successfully");
			} else {
				task.setMessage("Task name not deleted ");
			}
		} catch (Exception e) {

		}

		task.setTaskList(taskDao.getTaskDetails());
		return new ModelAndView("tms/TaskMaster", "task", task);
	}
}
