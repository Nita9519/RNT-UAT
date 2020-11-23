package ai.rnt.main.controller;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ai.rnt.lms.model.User;
import ai.rnt.main.dao.MainLogInDao;
//import ai.rnt.rms.controller.FileUploadController;
import ai.rnt.rms.dao.LogInDao;
import ai.rnt.rms.model.Profile;
import ai.rnt.rms.util.MailUtil;

@Controller
public class MainController {

	private static final Logger log = LogManager.getLogger(MainController.class);

	LogInDao logInDao = new LogInDao();
	User user = null;
	Profile profile = new Profile();
	MainLogInDao mainLogInDao = new MainLogInDao();
	MailUtil mailUtil = new MailUtil();

	/*
	 * @RequestMapping(value = "/rntlogin.do") public ModelAndView
	 * logIn(HttpServletRequest req, HttpServletResponse res) throws SQLException,
	 * PropertyVetoException, IOException { int staffID = 0; String userID = null;
	 * String password = null; boolean validUserStatus = false;
	 * 
	 * String username = req.getParameter("username"); password =
	 * req.getParameter("password");
	 * 
	 * HttpSession session = req.getSession(); if (username == null || password ==
	 * null) { User SessionObjProfile = (User) session.getAttribute("user"); if
	 * (SessionObjProfile == null) return new ModelAndView("other/login", "message",
	 * "Session is Expired Please Login Again");
	 * 
	 * // Taking The Value Form Session userID = SessionObjProfile.getUserID();
	 * staffID = SessionObjProfile.getStaffID(); password =
	 * SessionObjProfile.getPassword(); validUserStatus =
	 * logInDao.isvalidUserByStaffID(staffID, password); validUserStatus =
	 * logInDao.isvalidUserByUserID(userID, password); } else { try { staffID =
	 * Integer.parseInt(username); userID = username; validUserStatus =
	 * logInDao.isvalidUserByUserID(username, password);
	 * 
	 * validUserStatus = logInDao.isvalidUserByStaffID(staffID, password); } catch
	 * (NumberFormatException e) {
	 * log.info("Number Formate Exception from login Page for username: ", e);
	 * validUserStatus = logInDao.isvalidUserByEmailID(username, password); } }
	 * 
	 * // Checking the log in info if (validUserStatus) { User user =
	 * mainLogInDao.getEmployeeDetails(staffID);
	 * 
	 * // Image SET AND GET
	 * 
	 * String rootPath =
	 * req.getSession().getServletContext().getRealPath("/rms/images/");
	 * 
	 * log.info("pth====" + rootPath); String imgPath=
	 * mainLogInDao.getProfilePicture(staffID, rootPath);
	 * user.setImagePath(getBaseURL(req)+imgPath);
	 * 
	 * user.setAdmin(mainLogInDao.isValidAdmin(staffID));
	 * user.setManager(mainLogInDao.isValidManager(staffID));
	 * 
	 * session.setAttribute("user", user); return new ModelAndView("other/maindash",
	 * "user", user); } else {
	 * 
	 * return new ModelAndView("other/login", "message",
	 * "Invalid Username or Password "); } }
	 */

	@RequestMapping(value = "/rntlogin.do")
	public ModelAndView logIn(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException, IOException {
		/* int staffID = 0; */
		String userID = null;
		String password = null;
		boolean validUserStatus = false;

		String username = req.getParameter("username");
		password = req.getParameter("password");

		HttpSession session = req.getSession();
		if (username == null || password == null) {
			User SessionObjProfile = (User) session.getAttribute("user");
			if (SessionObjProfile == null)
				return new ModelAndView("other/login", "message", "Session is Expired Please Login Again");

			// Taking The Value Form Session
			userID = SessionObjProfile.getUserID();

			password = SessionObjProfile.getPassword();
			/* validUserStatus = logInDao.isvalidUserByStaffID(staffID, password); */
			validUserStatus = logInDao.isvalidUserByUserID(username, password);

		} else {
			try {

				// staffID = Integer.parseInt(username);
				userID = username;
				validUserStatus = logInDao.isvalidUserByUserID(username, password);
			
			} catch (NumberFormatException e) {
				log.info("Number Formate Exception from login Page for username: ", e);
				validUserStatus = logInDao.isvalidUserByEmailID(username, password);
			}

		}
		// Checking the log in info
		if (validUserStatus) {
			User user = mainLogInDao.getEmployeeDetails(userID); // Image SET AND GET

			String rootPath = req.getSession().getServletContext().getRealPath("/rms/images/profile.png");
if(rootPath!=null) {
	user.setImagePath(rootPath);		
}
log.info("pth====" + rootPath); //
/* String imgPath = mainLogInDao.getProfilePicture(userID, rootPath); */

			user.setAdmin(mainLogInDao.isValidAdmin(username));
			user.setManager(mainLogInDao.isValidManager(user));

			session.setAttribute("user", user);
			log.info("....................." + session);

			return new ModelAndView("other/maindash", "user", user);
		} else {
			return new ModelAndView("other/login", "message", "Invalid Username or Password ");
		}
	}

	@RequestMapping(value = "/logout.do")
	public ModelAndView logOut(HttpServletRequest req, HttpServletResponse res) {

		HttpSession session = req.getSession();
		 session.invalidate();
		 return new ModelAndView("other/login");
	}

	@RequestMapping(value = "/home.do")
	public ModelAndView home(HttpServletRequest req, HttpServletResponse res) {

		HttpSession session = req.getSession();

		
		return new ModelAndView("other/maindash");
	}
	/*
	 * @RequestMapping("/forgotpassword.do") public ModelAndView
	 * loadforgotPasswordPage(HttpServletRequest req, HttpServletResponse res)
	 * throws SQLException, PropertyVetoException, MessagingException {
	 * 
	 * return new ModelAndView("other/forgotpassword"); }
	 */
	/*
	 * @RequestMapping("/forgotpass.do") public ModelAndView
	 * loadforgotPasswordPage(HttpServletRequest req, HttpServletResponse res)
	 * throws SQLException, PropertyVetoException, MessagingException {
	 * 
	 * return new ModelAndView("other/forgotpass"); }
	 */
	/*
	 * @RequestMapping(value = "/resettologin.do") public ModelAndView
	 * resetToLogin(HttpServletRequest req, HttpServletResponse res) throws
	 * SQLException, PropertyVetoException, MessagingException {
	 * 
	 * String emailID = req.getParameter("emailID");
	 * 
	 * mailUtil.sendMailForResetPasword(emailID);
	 * 
	 * return new ModelAndView("login"); }
	 * 
	 * // get base URL
	 */ public static String getBaseURL(HttpServletRequest request) {
		log.info("//////==========" + request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath());
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}

}
