package ai.rnt.main.controller;
/*
 * package ai.rnt.main.controller; import java.beans.PropertyVetoException;
 * import java.io.IOException; import java.sql.SQLException; import
 * java.text.ParseException;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse; import
 * javax.servlet.http.HttpSession;
 * 
 * import org.apache.logging.log4j.LogManager; import
 * org.apache.logging.log4j.Logger; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * import ai.rnt.lms.model.User; import ai.rnt.rms.dao.confirmpassDao; import
 * ai.rnt.rms.model.Profile;
 * 
 * @Controller
 * 
 * @RequestMapping("/") public class forgotpassController {
 * 
 * 
 * private static final Logger log =
 * LogManager.getLogger(forgotpassController.class);
 * 
 * @RequestMapping(value = "/forgotPass.do") public ModelAndView
 * confirmpass(HttpServletRequest req, HttpServletResponse res) throws
 * SQLException, PropertyVetoException, ParseException, IOException,
 * NullPointerException { log.info("forgotPass....................");
 * 
 * Profile profile = new Profile(); User user =new User(); HttpSession session =
 * req.getSession(); User userSession = (User) session.getAttribute("user");
 * String userID = userSession.getUserID(); log.info("session======"+userID);
 * return new ModelAndView("rms/forgotpass"); }
 * 
 * @RequestMapping(value = "/forgotpass.do") public ModelAndView
 * confirmpassword(HttpServletRequest req, HttpServletResponse res) throws
 * SQLException, PropertyVetoException, ParseException, IOException,
 * NullPointerException { log.info("updateprofile...................."); boolean
 * status = false; String userID=null; confirmpassDao confirmpassdao = new
 * confirmpassDao();
 * 
 * // Session get Profile ID Profile profile = new Profile(); User user =new
 * User(); HttpSession session = req.getSession(); User userSession =
 * (User)session.getAttribute("user"); log.info(userSession);
 * log.info(session.getAttribute("user")); userID = userSession.getUserID();
 * log.info("session======"+userID);
 * 
 * user.setUserID(userID); session.setAttribute("user", user);
 * 
 * profile.setPassword(req.getParameter("password1"));
 * 
 * log.info("passssssssss===" + req.getParameter("password1"));
 * 
 * status = confirmpassdao.insertConfirmPassword(profile,userID);
 * 
 * if (status) { return new ModelAndView("other/login", "message",
 * "password inserted successfully."); } else
 * 
 * return new ModelAndView("rms/forgotpass", "message", "plz try again"); } }
 * 
 * 
 */