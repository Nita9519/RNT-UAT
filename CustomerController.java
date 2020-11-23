package ai.rnt.pins.controller;

import java.beans.PropertyVetoException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.lms.model.User;
import ai.rnt.pins.dao.CustomerDao;
import ai.rnt.pins.dao.ProjectMasterDao;
import ai.rnt.pins.model.Customer;
import ai.rnt.pins.model.CustomerMasterView;
import ai.rnt.pins.model.Dashboard;
import ai.rnt.pins.model.MilestoneMaster;
import ai.rnt.pins.model.Project;
import ai.rnt.pins.model.SoftwareMaster;
import ai.rnt.pins.model.SoftwareMasterView;
import ai.rnt.pins.util.Util;
import ai.rnt.pms.model.AppraisalInfo;

@Controller
public class CustomerController {
	ProjectMasterDao projectMasterDao = new ProjectMasterDao();

	StackTraceElement l = new Exception().getStackTrace()[0];

	CustomerDao customerDao = new CustomerDao();
	private static final Logger log = LogManager.getLogger(CustomerController.class);
	Customer customer = null;
	int Customer_ID = 1001;

	@RequestMapping(value = "/listofcustomers.do")
	public ModelAndView listOfCustomers(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		CustomerMasterView listofCustomers = new CustomerMasterView();// obj of model class
		ArrayList<Customer> list = null;
		try {
			list = customerDao.getlistOfCustomers();
			// listofCustomers = null;
			listofCustomers.setCustomerDetails(list);
			return new ModelAndView("pins/listofcustomers", "listcofCustomer", listofCustomers);
		} catch (Exception e) {
			log.info("Exception occured in getting list of customer:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/getlistOfcustomersForactive.do")
	public ModelAndView listOfCustomersForactive(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		CustomerMasterView listofCustomers = new CustomerMasterView();// obj of model class
		ArrayList<Customer> list = null;
		try {
			list = customerDao.getlistOfCustomersForactivelist();
			// listofCustomers = null;
			listofCustomers.setCustomerDetails(list);
			return new ModelAndView("pins/listofactivecustomer", "listcofCustomer", listofCustomers);
		} catch (Exception e) {
			log.info("Exception occured in getting list of customer:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/listofproject.do")
	public ModelAndView listOfProject(HttpServletRequest req, HttpServletResponse res, int customerId)
			throws SQLException, PropertyVetoException {
		CustomerMasterView listOfProject = new CustomerMasterView();// obj of model class
		ArrayList<Project> list = null;
		try {
			list = projectMasterDao.getProjectList(customerId);
			listOfProject.setProjectDetails(list);

			return new ModelAndView("pins/customermanagement", "listOfProject", listOfProject);
		} catch (Exception e) {
			log.info("Exception occured at getting list of projects:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/listofactiveproject.do")
	public ModelAndView getProjectList(HttpServletRequest req, HttpServletResponse res/* , Integer customerId */)
			throws SQLException, PropertyVetoException {
		CustomerMasterView listOfProject = new CustomerMasterView();// obj of model class
		// int customerId = Integer.parseInt(req.getParameter("customerid"));
		ArrayList<Project> list = null;
		try {
			list = projectMasterDao.getProjectActiveList();
			listOfProject.setProjectDetails(list);
			return new ModelAndView("pins/listactiveprojectview", "listOfProject", listOfProject);
		} catch (Exception e) {
			log.info("Exception occured at getting list of projects:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	// add new customer
	@RequestMapping(value = "/loaddetailsfornewcustomer.do")
	public ModelAndView loadDetailsForNewCustomer(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		CustomerMasterView customer = new CustomerMasterView();
		// Customer customer = new Customer();

		customer.setLocationList(customerDao.getListOfLocations());
		try {
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setEmployeeList(projectMasterDao.getListOfEmployee());

			String addCustomer = (String) req.getParameter("addnew");
			customer.setAddNew(addCustomer);

			return new ModelAndView("pins/customermanagement", "customer", customer);
		} catch (Exception e) {
			log.info("Exception occured at get new customer details:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	// for only locn and service type and exist customer date=06jan2020
	@RequestMapping(value = "/loadDetailsForExistingCustomerviewpage.do")
	public ModelAndView loadDetailsForExistingCustomerviewpage(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Customer customer = new Customer();
		try {
			int customerID = Integer.parseInt(request.getParameter("customerid"));

			customer = customerDao.getCustomer(customerID);

			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setProjectManagerList(projectMasterDao.getListOfEmployee());
			customer.setCustomerProjectList(projectMasterDao.getProjectList(customerID));

			String edit = request.getParameter("edit");

			customer.setCustomerProjectList(projectMasterDao.getProjectList(customerID));
			customer.setEdit(edit);
			/* customer.setMsg("successfully updated"); */

			return new ModelAndView("pins/customermanagement", "customer", customer);
		} catch (Exception e) {
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	// for only locn and service type and exist customer
	@RequestMapping(value = "/loaddetailsforexistingcustomer.do")
	public ModelAndView loadDetailsForExistingCustomer(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Customer customer = new Customer();
		try {
			int customerID = Integer.parseInt(request.getParameter("customerid"));

			customer = customerDao.getCustomer(customerID);

			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setProjectManagerList(projectMasterDao.getListOfEmployee());
			customer.setCustomerProjectList(projectMasterDao.getProjectList(customerID));

			String edit = request.getParameter("edit");

			customer.setCustomerProjectList(projectMasterDao.getProjectList(customerID));
			customer.setEdit(edit);
			ModelAndView mv = new ModelAndView();
			mv.addObject("customer", customer);
			mv.addObject("custMngmtPage", true);
			mv.setViewName("pins/customermanagement");
			return mv;
		} catch (Exception e) {
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/submit.do")
	public ModelAndView insertCustomerDetails(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		Customer customer = new Customer();
		int locID = 0;
		Project project = new Project();
		CustomerMasterView listofCustomer = new CustomerMasterView();
		// ListOfProject listOfProject=new ListOfProject();

		String projectName = request.getParameter("projectname");
		String serviceType = request.getParameter("txtservicetype");
		String milestone = request.getParameter("txtmilestone");
		String milestoneStartDate = request.getParameter("milestonedate");
		// String date = request.getParameter("date");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		System.out.println("AdminId/staffId: " + adminId);

		try {

			locID = Integer.parseInt(request.getParameter("txtlocation"));

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("txtstartdate"));
			java.sql.Date sqlDate = new java.sql.Date(uDate.getTime());

			if (request.getParameter("customerStatus") != null)
				customer.setCustomerStatus(request.getParameter("customerStatus")); // checkbox

			customer.setCompanyName(request.getParameter("txtcompanyname"));
			customer.setDomainName(Util.getValueOrDefault(request.getParameter("txtdomainname"), ""));
			customer.setOfficeContactNumber(request.getParameter("officecontactnumber"));
			customer.setContactPersonName(Util.getValueOrDefault(request.getParameter("txtcontactpersonname"), ""));
			customer.setContactPersonMobileNumber(
					Util.getValueOrDefault(request.getParameter("contactpersonnumber"), ""));
			customer.setContactPersonNumber(Util.getValueOrDefault(request.getParameter("mobilenumber"), ""));
			customer.setEmailId(Util.getValueOrDefault(request.getParameter("emailid"), ""));
			customer.setWebsite(Util.getValueOrDefault(request.getParameter("webite"), ""));
			customer.setLocationId(locID);
			customer.setStartDate(sqlDate);
			customer.setTechnology(Util.getValueOrDefault(request.getParameter("txttechnology"), ""));
			customer.setMasterServiceAgreement(Util.getValueOrDefault(request.getParameter("inlineRadio1"), "N"));
			customer.setNonDisclosureAgreement(Util.getValueOrDefault(request.getParameter("inlineRadio"), "N"));
			customer.setAddress(Util.getValueOrDefault(request.getParameter("txtaddress"), ""));
			customer.setCustomerStatus(Util.getValueOrDefault(request.getParameter("customerstatus"), "A"));

			boolean customerInsertStatus = customerDao.insertCustomerDetails(customer, adminId);

			if (customerInsertStatus) {

				int customerID = customerDao.getCustomerId(request.getParameter("txtcompanyname"));

				if (projectName != "" && serviceType != "") {
					project = insertProjectDetails(request, res, customerID, adminId);

					int ProjectId = projectMasterDao.getprojectIdforMilestone(customerID, project);

					project.setProjectId(ProjectId);
					if (milestone != "" || milestoneStartDate != "")
						if (insertMilestoneDetails(request, res, ProjectId))
							listofCustomer.setErrorMsg("Customer Management information submitted successfully");
				}

			} else {
			}

			ArrayList<Customer> list = null;

			list = customerDao.getlistOfCustomers();
			listofCustomer.setCustomerDetails(list);

			return new ModelAndView("pins/listofcustomers", "listcofCustomer", listofCustomer);
		} // catch (NumberFormatException e)
		catch (Exception e) {
			log.error("Got Exception while inserting Customer Details :: ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	// @RequestMapping(value="/login.do")
	public Project insertProjectDetails(HttpServletRequest request, HttpServletResponse res, int customerId,
			int adminId) throws SQLException, PropertyVetoException, ParseException {

		Project project = new Project();
		int serviceTypeId, effortNo, percentAllocation, projectmanagerID = 0;
		java.sql.Date projectEndsqlDate = null;
		java.sql.Date projectStartsqlDate = null;

		// project details
		String projectName = request.getParameter("projectname");
		String serviceType = request.getParameter("txtservicetype");
		String engagementModel = request.getParameter("engegementmodel");
		String efforts = request.getParameter("efforts");
		String effortsUnit = request.getParameter("txteffortsunit");
		String projectStartDate = request.getParameter("startdate");
		String projectEndDate = request.getParameter("enddate");
		String executionModel = request.getParameter("executionmodel");
		String projectmanager = request.getParameter("projectmanager");
		// String projectmanagerId = request.getParameter("txtprojectmanagerId");
		String percentOfAllocation = request.getParameter("percentofallocation");
		String statusOfProject = request.getParameter("statusofproject");
		String statementOfWork = request.getParameter("sow");

		try {
			effortNo = Integer.parseInt(efforts);

			if (percentOfAllocation == "") {
				percentOfAllocation = "0";
			}

			percentAllocation = Integer.parseInt(percentOfAllocation);
			serviceTypeId = Integer.parseInt(serviceType);
			projectmanagerID = Integer.parseInt(projectmanager);

			if (request.getParameter("StatusfProject") != null)
				project.setStatusOfProject(statusOfProject);

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(projectStartDate);
			projectStartsqlDate = new java.sql.Date(uDate.getTime());

			if (request.getParameter("enddate") != "") {
				java.util.Date uDate11 = new SimpleDateFormat("yyyy-MM-dd").parse(projectEndDate);
				projectEndsqlDate = new java.sql.Date(uDate11.getTime());

			}

			// set project Details
			project.setProjectName(projectName);
			project.setServiceTypeId(serviceTypeId);
			project.setEngagementModel(Util.getValueOrDefault(engagementModel, ""));
			project.setEfforts(effortNo);
			project.setEffortsUnit(effortsUnit);
			log.info(project.getEffortsUnit());
			project.setStartDate(projectStartsqlDate);
			project.setEndDate(projectEndsqlDate);
			project.setExecutionModel(Util.getValueOrDefault(executionModel, ""));
			project.setProjectManagerId(projectmanagerID);
			project.setPercentOfAllocation(percentAllocation);
			project.setStatusOfProject(Util.getValueOrDefault(statusOfProject, "A"));
			project.setStatementOfWork(Util.getValueOrDefault(statementOfWork, "N"));

		} catch (Exception e) {
			log.error("Got Exception while inserting Project Details ::  ", e);
		}

		// Insert Pro details
		projectMasterDao.insertProjectDetails(project, customerId, adminId);

		return project;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatecustomerdetails.do", method = RequestMethod.POST, produces = "application/json")
	 public @ResponseBody JSONObject   updateCustomerDetails(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		
		JSONObject mainJson= new JSONObject();
		int locID = 0;
		String customerId = request.getParameter("customerId");
		String domainName = request.getParameter("txtdomainname");
		String officeContactNumber = request.getParameter("officecontactnumber");
		String contactPersonName = request.getParameter("txtcontactpersonname");
		String contactPersonNumber = request.getParameter("contactpersonnumber");
		String contactPersonMobileNumber = request.getParameter("mobilenumber");
		String emailId = request.getParameter("emailid");
		String website = request.getParameter("webite");
		String locationId = request.getParameter("txtlocation");
		String startDate = request.getParameter("txtstartdate");
		String technology = request.getParameter("txttechnology");
		String masterServiceAgreement = request.getParameter("inlineRadio1");
		String nonDisclosureAgreement = request.getParameter("inlineRadio");
		String address = request.getParameter("txtaddress");
		String customerStatus = request.getParameter("customerstatus");

		Customer customer = new Customer();

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
			/*return new ModelAndView("other/login", "message", "Session is expired");*/
		/*mainJson.put("Value", "Successfully Submitted");*/
		int adminId = userSession.getStaffID();


		try {
			int custId = 0;
			locID = 0;
			custId = Integer.parseInt(customerId);
			locID = Integer.parseInt(locationId);

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			java.sql.Date sqlDate = new java.sql.Date(uDate.getTime());

			if (request.getParameter("customerStatus") != null)
				customer.setCustomerStatus(request.getParameter("customerStatus")); // checkbox
			else
				customer.setCustomerStatus("N");

			customer.setCustomerID(custId);
			// log.info("custId ",+customerID);
			// customer.setCompanyName(companyName);

			customer.setDomainName(Util.getValueOrDefault(domainName, ""));
			customer.setOfficeContactNumber(officeContactNumber);
			customer.setContactPersonName(Util.getValueOrDefault(contactPersonName, ""));
			customer.setContactPersonMobileNumber(Util.getValueOrDefault(contactPersonMobileNumber, ""));
			customer.setContactPersonNumber(Util.getValueOrDefault(contactPersonNumber, ""));
			customer.setEmailId(Util.getValueOrDefault(emailId, ""));
			customer.setWebsite(Util.getValueOrDefault(website, ""));
			customer.setLocationId(locID);
			customer.setStartDate(sqlDate);
			customer.setTechnology(Util.getValueOrDefault(technology, ""));
			customer.setMasterServiceAgreement(Util.getValueOrDefault(masterServiceAgreement, "N"));
			customer.setNonDisclosureAgreement(Util.getValueOrDefault(nonDisclosureAgreement, "N"));
			customer.setAddress(Util.getValueOrDefault(address, ""));
			// customer.setCustomerStatus(Util.getValueOrDefault(customerStatus, "A"));
			customer.setCustomerStatus(customerStatus);

			 // error here

			ArrayList<Customer> list = null;
			CustomerMasterView listofCustomer = new CustomerMasterView();
			list = customerDao.getlistOfCustomers();
			// list = customerDao.getCustomer(customerID)
			listofCustomer.setCustomerDetails(list);
			listofCustomer.setErrorMsg("Customer Management information updated successfully");
			if (customerDao.updateCustomerDetails(customer, adminId)) {
				mainJson.put("Value", "Updated Successfully");
			}
			else {
				mainJson.put("Value", "Not Updated");
			}
		}
		 catch (Exception e) {
			log.error("Got Exception while Updating Customer Details :: ", e);
			// e.printStackTrace();
			mainJson.put("Value",
					"Error!!SOmething went wrong.COntact Support Team.");
		}
		return mainJson;
	
	}

	@RequestMapping(value = "/updateprojectdetails.do")
	public ModelAndView updateProjectDetails(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {
		Project project = new Project();
		CustomerMasterView listcofCustomer = new CustomerMasterView();

		int effortNo = 0;
		int projectid = 0;
		int customerid = 0;
		int serviceTypeId = 0;
		int projectmanagerID = 0;
		int percentAllocation = 0;
		java.sql.Date projectEndsqlDate = null;
		java.sql.Date projectStartsqlDate = null;

		String custId = request.getParameter("customerid");
		String projId = request.getParameter("projectid");
		String projectName = request.getParameter("projectname");
		String serviceType = request.getParameter("txtservicetype");
		String engagementModel = request.getParameter("engegementmodel");
		String efforts = request.getParameter("efforts");
		String effortsUnit = request.getParameter("txteffortsunit");
		String projectStartDate = request.getParameter("startdate");
		String projectEndDate = request.getParameter("enddate");
		String executionModel = request.getParameter("executionmodel");
		String projectmanager = request.getParameter("projectmanager");
		String percentOfAllocation = request.getParameter("percentofallocation");
		String statusOfProject = request.getParameter("statusofproject");
		String statementOfWork = request.getParameter("sow");
		/*
		 * String milestone = request.getParameter("txtmilestone"); String
		 * milestoneStartDate = request.getParameter("milestonedate");
		 */
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int adminId = user.getStaffID();

		try {
			// project = null;
			projectid = Integer.parseInt(projId);
			customerid = Integer.parseInt(custId);
			percentAllocation = Integer.parseInt(percentOfAllocation);
			serviceTypeId = Integer.parseInt(serviceType);
			effortNo = Integer.parseInt(efforts);
			projectmanagerID = Integer.parseInt(projectmanager);

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(projectStartDate);
			projectStartsqlDate = new java.sql.Date(uDate.getTime());

			if (request.getParameter("enddate") != "") {
				java.util.Date uDate11 = new SimpleDateFormat("yyyy-MM-dd").parse(projectEndDate);
				projectEndsqlDate = new java.sql.Date(uDate11.getTime());

			}

			if (request.getParameter("statusOfProject") != null)
				project.setStatusOfProject(request.getParameter("statusOfProject")); // checkbox

			project.setCustomerId(customerid);
			project.setProjectId(projectid);
			project.setProjectName(projectName);
			project.setServiceTypeId(serviceTypeId);
			project.setEngagementModel(engagementModel);
			project.setEfforts(effortNo);
			project.setEffortsUnit(effortsUnit);
			project.setStartDate(projectStartsqlDate);
			project.setEndDate(projectEndsqlDate);
			project.setExecutionModel(executionModel);
			project.setProjectManagerId(projectmanagerID);
			project.setPercentOfAllocation(percentAllocation);
			project.setStatusOfProject(statusOfProject);
			project.setStatementOfWork(statementOfWork);
			

			if (projectMasterDao.updateProjectDetails(project, adminId)) 
				insertMilestoneDetails(request, res, projectid);
			listcofCustomer.setErrorMsg("Customer Information updated successfully");
			
			ArrayList<Project> list = projectMasterDao.getProjectList(Customer_ID);
			listcofCustomer.setProjectDetails(list);

			ArrayList<Customer> customerList = customerDao.getlistOfCustomers();
			listcofCustomer.setCustomerDetails(customerList);

			return new ModelAndView("pins/listofcustomers", "listcofCustomer", listcofCustomer); // 1-jsp,2-name in
																									// jsp,3-obj,

		} catch (Exception e) {
			log.error("Got Exception while Updating project Details ::  ", e);
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
		/*
		 * projectMasterDao.updateProjectDetails(project, adminId); //
		 * insertMilestoneDetails(request, res, projectid);
		 * 
		 * CustomerMasterView listOfProject = new CustomerMasterView();
		 * ArrayList<Project> list = projectMasterDao.getProjectList(Customer_ID);
		 * listOfProject.setProjectDetails(list);
		 * 
		 * ArrayList<Customer> customerList = customerDao.getlistOfCustomers();
		 * listOfProject.setCustomerDetails(customerList); return new
		 * ModelAndView("pins/listofcustomers", "listcofCustomer", listOfProject); //
		 * 1-jsp,2-name in jsp,3-obj,
		 */
	}

	@RequestMapping(value = "/loaddetailsforexistingproject.do")
	public ModelAndView loadDetailsForExistingProject(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Customer customer = new Customer();

		int customerID = Integer.parseInt(request.getParameter("customerid"));
		String editProject = request.getParameter("editsingleproject");
		String projId = request.getParameter("projectid");
		int projectid = Integer.parseInt(projId);
		try {
			customer = customerDao.getCustomer(customerID);
			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setProjectManagerList(projectMasterDao.getListOfEmployee());
			customer.setMilestoneDetails(projectMasterDao.getAllMilestoneDetails(projectid));
			customer.setSingleProjectEdit(editProject);
			customer.setProject(projectMasterDao.getProject(projectid));

			// customer.setEffortsUnitList();
			customer.setEngagementModelList();
			customer.setExecutionModelList();
			customer.setPercentOfAllocationList();
			/* customer.setMsg("Successfully Updated"); */

			return new ModelAndView("pins/customermanagement", "customer", customer);
		} catch (Exception e) {
			log.info("Exception occured at load details for existingnproject:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/viewdetailsforexistingproject.do")
	public ModelAndView viewDetailsForExistingProject(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Customer customer = new Customer();

		int customerID = Integer.parseInt(request.getParameter("customerid"));
		String editProject = request.getParameter("editsingleproject");
		String projId = request.getParameter("projectid");
		int projectid = Integer.parseInt(projId);
		try {
			customer = customerDao.getCustomer(customerID);
			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setProjectManagerList(projectMasterDao.getListOfEmployee());
			customer.setMilestoneDetails(projectMasterDao.getAllMilestoneDetails(projectid));
			customer.setSingleProjectEdit(editProject);
			customer.setProject(projectMasterDao.getProject(projectid));

			// customer.setEffortsUnitList();
			customer.setEngagementModelList();
			customer.setExecutionModelList();
			customer.setPercentOfAllocationList();
			/* customer.setMsg("Successfully Updated"); */

			return new ModelAndView("pins/customermanagementview", "customer", customer);
		} catch (Exception e) {
			log.info("Exception occured at load details for existingnproject:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/viewDetailsForlistProject.do")
	public ModelAndView viewDetailsForlistProject(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		Customer customer = new Customer();

		int customerID = Integer.parseInt(request.getParameter("customerid"));
		String editProject = request.getParameter("editsingleproject");
		String projId = request.getParameter("projectid");

		int projectid = Integer.parseInt(projId);
		try {
			customer = customerDao.getCustomer(customerID);
			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setProjectManagerList(projectMasterDao.getListOfEmployee());
			customer.setMilestoneDetails(projectMasterDao.getAllMilestoneDetails(projectid));
			customer.setSingleProjectEdit(editProject);
			customer.setProject(projectMasterDao.getProject(projectid));

			// customer.setEffortsUnitList(); customer.setEngagementModelList();
			customer.setExecutionModelList();
			customer.setPercentOfAllocationList();
			customer.setMsg("Successfully Updated");

			return new ModelAndView("pins/customermanagementview", "customer", customer);
		} catch (Exception e) {
			log.info("Exception occured at load details for existingnproject:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage",
					"Error!!Something went wrong.Contact Support Team.");
		}
	}

	@RequestMapping(value = "/addnewproject.do")
	public ModelAndView addNewProject(HttpServletRequest request, HttpServletResponse res)
			throws SQLException, PropertyVetoException, ParseException {

		Project project = new Project();
		int customerId = 0;
		int serviceTypeId, effortNo, percentAllocation, projectmanagerID = 0;
		java.sql.Date projectEndsqlDate = null;
		java.sql.Date projectStartsqlDate = null;

		// project details
		String custId = request.getParameter("customerid");
		String projectName = request.getParameter("projectname");
		String serviceType = request.getParameter("txtservicetype");
		String engagementModel = request.getParameter("engegementmodel");
		String efforts = request.getParameter("efforts");
		String effortsUnit = request.getParameter("txteffortsunit");
		String projectStartDate = request.getParameter("startdate");
		String projectEndDate = request.getParameter("enddate");
		String executionModel = request.getParameter("executionmodel");
		String projectmanager = request.getParameter("projectmanager");
		String percentOfAllocation = request.getParameter("percentofallocation");
		String statusOfProject = request.getParameter("statusofproject");
		String statementOfWork = request.getParameter("sow");
		String milestoneStartDate = request.getParameter("milestonedate");
		String milestone = request.getParameter("txtmilestone");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			customerId = Integer.parseInt(custId);
			effortNo = Integer.parseInt(efforts);

			if (percentOfAllocation == "") {
				percentOfAllocation = "0";
			}

			percentAllocation = Integer.parseInt(percentOfAllocation);
			serviceTypeId = Integer.parseInt(serviceType);
			projectmanagerID = Integer.parseInt(projectmanager);

			if (request.getParameter("StatusfProject") != null)
				project.setStatusOfProject(statusOfProject);

			java.util.Date uDate = new SimpleDateFormat("yyyy-MM-dd").parse(projectStartDate);
			projectStartsqlDate = new java.sql.Date(uDate.getTime());

			if (request.getParameter("enddate") != "") {
				java.util.Date uDate11 = new SimpleDateFormat("yyyy-MM-dd").parse(projectEndDate);
				projectEndsqlDate = new java.sql.Date(uDate11.getTime());
			}

			// set project Details
			project.setCustomerId(customerId);
			project.setProjectName(projectName);
			project.setServiceTypeId(serviceTypeId);
			project.setEngagementModel(Util.getValueOrDefault(engagementModel, ""));
			project.setEfforts(effortNo);
			project.setEffortsUnit(effortsUnit);
			project.setStartDate(projectStartsqlDate);
			project.setEndDate(projectEndsqlDate);
			project.setExecutionModel(executionModel);
			project.setProjectManagerId(projectmanagerID);
			project.setPercentOfAllocation(percentAllocation);
			project.setStatusOfProject(Util.getValueOrDefault(statusOfProject, "A"));
			project.setStatementOfWork(Util.getValueOrDefault(statementOfWork, "N"));

			projectMasterDao.insertProjectDetails(project, customerId, adminId);

			int ProjectId = projectMasterDao.getprojectIdforMilestone(customerId, project);
			project.setProjectId(ProjectId);

			if (milestone != "" || milestoneStartDate != "") {
				insertMilestoneDetails(request, res, ProjectId);

			}
			ArrayList<Customer> list = null;
			CustomerMasterView listofCustomer = new CustomerMasterView();
			list = customerDao.getlistOfCustomers();
			listofCustomer.setCustomerDetails(list);
			/* listofCustomer.setErrorMsg("customer added successfully"); */

			return new ModelAndView("pins/listofcustomers", "listcofCustomer", listofCustomer);

		} catch (Exception e) {
			log.error("Got Exception while adding new project of Existing Customer ::  ", e);
			// log.error("Got annnn Exception "+e.getMessage());
			// e.printStackTrace();
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Something went wrong.Contact Support Team.");
		}

	}

	@RequestMapping(value = "/loaddetailsfornewproject.do")
	public ModelAndView loadDetailsForNewProject(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {
		Customer customer = new Customer();

		int customerID = Integer.parseInt(req.getParameter("customerid"));
		customer = customerDao.getCustomer(customerID);
		try {
			ArrayList<MilestoneMaster> milestoneList = projectMasterDao.getListOfMilestones();
			customer.setMilestoneList(milestoneList);

			customer.setLocationList(customerDao.getListOfLocations());
			customer.setServicetypeList(projectMasterDao.getListOfserviceTypes());
			customer.setMilestoneList(projectMasterDao.getListOfMilestones());
			customer.setEmployeeList(projectMasterDao.getListOfEmployee());

			String addProject = (String) req.getParameter("addnewproject");
			customer.setAddProject(addProject);
			/* customer.setMsg("added new projects"); */
			return new ModelAndView("pins/customermanagement", "customer", customer);
		} catch (Exception e) {
			log.info("Exception occured at load details for new project:" + e);
			return new ModelAndView("pins/ErrorPage", "ErrorMessage", "Something went wrong.Contact Support Team.");
		}
	}

	// @RequestMapping(value="/addnewproject.do")
	public boolean insertMilestoneDetails(HttpServletRequest request, HttpServletResponse res, int ProjectId)
			throws SQLException, PropertyVetoException, ParseException {

		int milestoneId = 0;
		MilestoneMaster milestoneMaster = new MilestoneMaster();
		int i = 1;
		boolean status = false;
		while (true) {

			if (request.getParameter("milestonedate" + i) == null && request.getParameter("txtmilestone" + i) == null)
				break;

			String milestoneStartDate = request.getParameter("milestonedate" + i);
			String milestone = request.getParameter("txtmilestone" + i);

			try {
				milestoneId = projectMasterDao.getMilestoneId(milestone);
				java.util.Date uDate1111 = new SimpleDateFormat("yyyy-MM-dd").parse(milestoneStartDate);
				java.sql.Date milestoneDate = new java.sql.Date(uDate1111.getTime());

				milestoneMaster.setMilestoneDate(milestoneDate);
				milestoneMaster.setMilestoneId(milestoneId);

				if (projectMasterDao.insertMilestoneDetails(milestoneMaster, ProjectId))
					status = true;

			} catch (Exception e) {
				log.error("Got Exception while inserting Milestone Details :: ", e);
				// e.printStackTrace();
			}

			i++;

		}
		return status;
	}

	@RequestMapping(value = "/checkcompanyname.do", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody JSONObject insertKra(HttpServletRequest request, HttpServletResponse res,
			@RequestBody Customer customer) throws SQLException, PropertyVetoException, ParseException {

		boolean staus = customerDao.checkCustomerDuplicateRecord(customer.getCompanyName());

		JSONObject mainJson = new JSONObject();
		mainJson.put("Value", staus);
		return mainJson;
	}

	/*
	 * @RequestMapping(value="/deletecustomer.do") public ModelAndView
	 * deleteSoftware(HttpServletRequest request, HttpServletResponse res) throws
	 * SQLException, PropertyVetoException {
	 * 
	 * ArrayList<Customer> list = null; CustomerMasterView listofCustomer = new
	 * CustomerMasterView(); list = customerDao.getlistOfCustomers(); // list =
	 * customerDao.getCustomer(customerID) listofCustomer.setCustomerDetails(list);
	 * HttpSession session = request.getSession(); User userSession = (User)
	 * session.getAttribute("user"); if (userSession == null) return new
	 * ModelAndView("other/login", "message", "Session is expired"); int adminId =
	 * userSession.getStaffID();
	 * 
	 * try { int customerId = Integer.parseInt(request.getParameter("customerid"));
	 * if(customerDao.deleteCustomer(customerId, adminId))
	 * listofCustomer.setMessage("Customer deleted successfully"); else
	 * listofCustomer.setMessage("Customer Details not deleted");
	 * 
	 * 
	 * list = customerDao.getlistOfCustomers();
	 * listofCustomer.setCustomerDetails(list); return new
	 * ModelAndView("pins/listofcustomers", "listcofCustomer", listofCustomer); }
	 * catch (Exception e) {
	 * log.error("Got Exception while deleting software Details :: ", e); //
	 * e.printStackTrace(); return new ModelAndView("pins/ErrorPage","ErrorMessage",
	 * "Error!!Something went wrong.Contact Support Team."); }
	 * 
	 * }
	 */
}