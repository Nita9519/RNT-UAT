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
import ai.rnt.pins.dao.RoleMasterDao;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.RoleMaster;
import ai.rnt.pins.model.RoleMasterView;


@Controller
public class RoleController {

	Dashboard dashboard = null;
	int staffID = 0;
	RoleMasterDao roleDao = new RoleMasterDao();
	private static final Logger log = LogManager.getLogger(RoleController.class);

	@RequestMapping(value="/editroles.do")
	public ModelAndView editRoles(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		RoleMasterView roleRecords = new RoleMasterView();
		RoleMaster role = new RoleMaster();
		ArrayList<RoleMaster> roleList = null;
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		
		try {
			String roleName = (String) request.getParameter("role");
			int roleId = 0;
			role.setRoleName(roleName);

			if (buttonAction.equals("Update") && roleName != null) {
				roleId = Integer.parseInt(request.getParameter("roleid"));
				role.setRoleId(roleId);
				
				if(roleDao.checkDuplicateRecForRole(roleName))
					roleRecords.setMessage("Role already exists");
				else {
					if(roleDao.updateRole(roleId, roleName, adminId))
						roleRecords.setMessage("Role updated successfully");
					else
						roleRecords.setMessage("Role not updated");
				}
				
			} else {
				if(roleDao.checkDuplicateRecForRole(roleName))
					roleRecords.setMessage("Role already exists");
				else {
					if(roleDao.addRole(roleName, adminId))
						roleRecords.setMessage("Role added successfully");
					else {
						if(roleDao.updateAddedRole(roleName, adminId))
							roleRecords.setMessage("Role added successfully");
						else
							roleRecords.setMessage("Role not added");
					}
				}
				
			}

			roleList = roleDao.getListOfRoles();
			roleRecords.setRole(roleList);
			return new ModelAndView("pins/rolemaster", "roleRecords", roleRecords);
		} catch (Exception e) {
			log.error("Got Exception while updating Role Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage","Error!!Something went wrong.Contact Support Team.");
		}
		
	}

	@RequestMapping(value="/deleteroles.do")
	public ModelAndView deleteRole(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		RoleMasterView roleRecords = new RoleMasterView();
		ArrayList<RoleMaster> roleList = null;

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		

		try {
			int roleId = Integer.parseInt(request.getParameter("roleid"));
			if(roleDao.deleteRole(roleId, adminId))
				roleRecords.setMessage("Role deleted successfully");
			else
				roleRecords.setMessage("Role not deleted");

			roleList = roleDao.getListOfRoles();
			roleRecords.setRole(roleList);
			
			return new ModelAndView("pins/rolemaster", "roleRecords", roleRecords);
		} catch (Exception e) {
			log.error("Got Exception while deleting Role Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Error!!Something went wrong.Contact Support Team.");
		}
		
	}

}
