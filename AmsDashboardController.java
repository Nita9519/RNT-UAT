package ai.rnt.ams.controller;

import ai.rnt.ams.util.MailUtil;
import ai.rnt.lms.model.User;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ai.rnt.ams.dao.AdminDao;
import ai.rnt.ams.dao.AssetAllocationDao;
import ai.rnt.ams.dao.AssetMaintenanceDao;
import ai.rnt.ams.dao.AssetRegistrationDao;
import ai.rnt.ams.dao.DashboardDao;
import ai.rnt.ams.dao.RaiseRequestDao;
import ai.rnt.ams.model.AssetAllocation;
import ai.rnt.ams.model.AssetMaintenance;
import ai.rnt.ams.model.AssetRegistration;
import ai.rnt.ams.model.AssetSubCategoryMaster;
import ai.rnt.ams.model.Dashboard;
import ai.rnt.ams.model.EmployeeMaster;
import ai.rnt.ams.model.RaiseRequest;

@Controller
public class AmsDashboardController {
	DashboardDao dashboarddao = new DashboardDao();
	Dashboard dashboard = null;
	MailUtil mailUtil = new MailUtil();
	StackTraceElement l = new Exception().getStackTrace()[0];
	AssetRegistrationDao registrationDao = new AssetRegistrationDao();
	AssetAllocationDao assetAllocationDao = new AssetAllocationDao();
	AssetMaintenanceDao assetMaintenanceDao = new AssetMaintenanceDao();
	RaiseRequestDao raiseRequestDao = new RaiseRequestDao();
	AdminDao adminDao = new AdminDao();
	AssetMaintenanceDao maintenanceDao = new AssetMaintenanceDao();

	public static final Logger log = LogManager.getLogger(AmsDashboardController.class);

	@RequestMapping(value = "/amsdash.do")
	public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffId = user.getStaffID();
		String password = user.getPassword();

		try {

			dashboard.setNetworkEngineer(dashboarddao.checkNetworkEngineer(staffId));
			dashboard.setAssetList(registrationDao.getAssetList());
			dashboard.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setAvailableAssetList(registrationDao.getAvailableAssetList());
			dashboard.setSupplierMaster(adminDao.getSupplierList());
			dashboard.setAllocationRequestsListForApproval(
					assetAllocationDao.getAssetAllocationRequestListForApproval());
			dashboard
					.setAllocationRequestsListForUser(assetAllocationDao.getAssetAllocationRequestListForUser(staffId));
			dashboard.setMaintenanceRequestListForUser(assetMaintenanceDao.getMaintenanceRequestListForUser(staffId));
			dashboard.setRequestListforUser(raiseRequestDao.raisedRequestListforUser(staffId));
			dashboard.setRequestList(raiseRequestDao.getRequestListForUser(staffId));
			/* count of new req */
			int newReqCount = raiseRequestDao.getRaiseReqCount();

			dashboard.setNewReqCount(newReqCount);

		} catch (Exception e) {
		}
		return new ModelAndView("ams/dashboard", "dashboard", dashboard);
	}

	@RequestMapping(value = "/assetdetails.do")
	public ModelAndView assetdetails(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		dashboard = new Dashboard();

		try {

			dashboard.setAssetList(registrationDao.getAssetList());
			dashboard.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setAvailableAssetList(registrationDao.getAvailableAssetList());

		} catch (Exception e) {
		}
		return new ModelAndView("ams/assetdetails", "dashboard", dashboard);
	}

	@RequestMapping(value = "/assetallocateddetails.do")
	public ModelAndView assetallocationdetails(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		dashboard = new Dashboard();

		try {
			dashboard.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			log.info("xcvxcvxcv" + dashboard.getAllocatedAssets());
		} catch (Exception e) {
		}
		return new ModelAndView("ams/assetallocateddetails", "dashboard", dashboard);
	}

	/* @RequestMapping(value = "/raiserequestdetails.do") */
	@RequestMapping(value = "/requestreport.do")
	public ModelAndView raiserequestdetails(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffId = user.getStaffID();
		String password = user.getPassword();

		try {

			dashboard.setRequestList(raiseRequestDao.getRequestList());

			dashboard.setAllocationRequestsListForApproval(
					assetAllocationDao.getAssetAllocationRequestListForApproval());
			dashboard
					.setAllocationRequestsListForUser(assetAllocationDao.getAssetAllocationRequestListForUser(staffId));
			dashboard.setMaintenanceRequestListForUser(assetMaintenanceDao.getMaintenanceRequestListForUser(staffId));
			dashboard.setRequestListforUser(raiseRequestDao.raisedRequestListforUser(staffId));
			dashboard.setRequestList(raiseRequestDao.getRequestListForUser(staffId));
			/* count of new req */
			int newReqCount = raiseRequestDao.getRaiseReqCount();

			dashboard.setNewReqCount(newReqCount);

		} catch (Exception e) {
		}
		return new ModelAndView("ams/requestreport", "dashboard", dashboard);
	}

	@RequestMapping(value = "/availabledetails.do")
	public ModelAndView availabledetails(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffId = user.getStaffID();
		String password = user.getPassword();

		try {

			dashboard.setAllocatedAssets(assetAllocationDao.allocatedAssets());

			dashboard.setAvailableAssetList(registrationDao.getAvailableAssetList());

		} catch (Exception e) {
		}
		return new ModelAndView("ams/availabledetails", "dashboard", dashboard);
	}

	@RequestMapping(value = "/supplierdetails.do")
	public ModelAndView supplierdetails(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int staffId = user.getStaffID();
		String password = user.getPassword();

		try {

			dashboard.setSupplierMaster(adminDao.getSupplierList());

		} catch (Exception e) {
		}
		return new ModelAndView("ams/supplierdetails", "dashboard", dashboard);
	}

	@RequestMapping(value = "/assetregister.do")
	public ModelAndView assetregister(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		AdminDao adminDao = new AdminDao();
		dashboard = new Dashboard();
		ArrayList<AssetRegistration> availableAssetList = null;
		AssetRegistrationDao registrationDao = new AssetRegistrationDao();

		try {
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setSupplierMaster(adminDao.getSupplierList());
			dashboard.setLocationList(adminDao.getLocationList());
			dashboard.setAssetList(registrationDao.getAssetList());
			availableAssetList = registrationDao.getAvailableAssetList();
			dashboard.setAvailableAssetList(availableAssetList);

		} catch (Exception e) {
			log.error("got error while fetching supplier records ", e);
		}

		return new ModelAndView("ams/assetregister", "dashboard", dashboard);
	}

	@RequestMapping(value = "/raiserequest.do")
	public ModelAndView raiserequest(HttpServletRequest request, HttpServletResponse response) {
		Dashboard dashboard = new Dashboard();

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			dashboard.setStaffId(userSession.getStaffID());
			dashboard.setFirstName(userSession.getFirstName());
			dashboard.setLastName(userSession.getLastName());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setRequestListforUser(raiseRequestDao.raisedRequestListforUser(userSession.getStaffID()));
			dashboard.setRequestList(raiseRequestDao.getRequestListForUser(userSession.getStaffID()));

		} catch (Exception e) {
			log.error("got error ", e);
		}
		return new ModelAndView("ams/raiserequest", "dashboard", dashboard);

	}

	@RequestMapping(value = "/addAsset.do")
	public ModelAndView addAsset(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		Dashboard dashboard = new Dashboard();
		AssetRegistration assets = new AssetRegistration();
		AssetRegistrationDao registrationDao = new AssetRegistrationDao();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		AdminDao adminDao = new AdminDao();
		dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
		dashboard.setSubCategoryList(adminDao.getSubCategoryList());
		dashboard.setSupplierMaster(adminDao.getSupplierList());
		dashboard.setLocationList(adminDao.getLocationList());

		try {
			int AssetCatId = Integer.parseInt(request.getParameter("assetCatId"));
			int SubCategoryId = Integer.parseInt(request.getParameter("subcatId"));
			int SupplierId = Integer.parseInt(request.getParameter("supplierid"));
			String assetTag = request.getParameter("assettag");
			String assetAvailableStatus = "Available";
			assets.setAssetCatId(AssetCatId);
			assets.setSubCatId(SubCategoryId);
			assets.setSupplierId(SupplierId);
			assets.setProduct(request.getParameter("product"));
			assets.setVersionModel(request.getParameter("modelversion"));
			assets.setSerialNo(request.getParameter("serialNumber"));
			assets.setCost(Integer.parseInt(request.getParameter("cost")));
			assets.setProductKey(request.getParameter("productkey"));
			assets.setAssetOwner(request.getParameter("owner"));
			assets.setLocationId(Integer.parseInt(request.getParameter("locationId")));
			assets.setProcessor(request.getParameter("processor"));
			assets.setRam(request.getParameter("ram"));
			assets.setHardDisk(request.getParameter("harddisk"));
			assets.setWarranty(request.getParameter("assetWarranty"));
			assets.setAccidentialDamage(request.getParameter("accidentialdamage"));
			assets.setWarrantyCoverage(request.getParameter("warrantycoverage"));
			assets.setAssetTag(assetTag);
			/* assets.setChargerSerialNo(request.getParameter("chargerSerialNo")); */
			assets.setAssetAvailableStatus(assetAvailableStatus);

			if (registrationDao.checkAssetTagDup(assetTag)) {
				dashboard.setMessage("Asset tag already exists ");
			}
			if (registrationDao.addAssets(AssetCatId, SubCategoryId, SupplierId, assets, adminId)) {
				dashboard.setMessage("Asset registered successfully");
			} else {
				dashboard.setMessage("Asset not registered successfully");
			}

			dashboard.setAssetList(registrationDao.getAssetList());

		} catch (Exception e) {
			log.error("got error while adding assets....", e);
		}

		return new ModelAndView("ams/assetregister", "dashboard", dashboard);

	}

	@RequestMapping(value = "/addAssetRequest.do")
	public ModelAndView addAssetRequest(HttpServletRequest request, HttpServletResponse response) {
		Dashboard dashboard = new Dashboard();
		RaiseRequest assetRequest = new RaiseRequest();

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		String buttonAction = request.getParameter("btnAction");
		int staffId = userSession.getStaffID();
		try {

			String firstName = userSession.getFirstName();
			int requestTypeId = Integer.parseInt(request.getParameter("requesttypeId"));
			int assetCatId = Integer.parseInt(request.getParameter("assetCAtId"));
			int subCatId = Integer.parseInt(request.getParameter("subcatId"));
			String configDescription = request.getParameter("configDEsc");
			assetRequest.setStaffId(staffId);
			assetRequest.setFirstName(firstName);
			assetRequest.setRequestTypeId(requestTypeId);
			assetRequest.setAssetCatId(assetCatId);
			assetRequest.setSubCatId(subCatId);
			assetRequest.setConfigDescription(configDescription);

			// raiseRequestDao.addassetRequest(staffId, assetCatId, subCatId, assetRequest);
			if (raiseRequestDao.addassetRequest(staffId, assetCatId, subCatId, assetRequest))
				dashboard.setMessage("Request raised successfully");
			else
				dashboard.setMessage("Request not raised successfully");
			dashboard.setStaffId(userSession.getStaffID());
			dashboard.setFirstName(userSession.getFirstName());
			dashboard.setLastName(userSession.getLastName());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setRequestList(raiseRequestDao.getRequestListForUser(staffId));

		} catch (Exception e) {
			log.error("got error while adding asset requests.......");
		}
		return new ModelAndView("ams/raiserequest", "dashboard", dashboard);

	}

	@RequestMapping(value = "/deleteAsset.do")
	public ModelAndView deleteAsset(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		dashboard = new Dashboard();
		AdminDao adminDao = new AdminDao();
		AssetRegistrationDao registrationDao = new AssetRegistrationDao();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		try {
			int assetId = Integer.parseInt(request.getParameter("aid"));
			// registrationDao.deleteAsset(assetId, adminId);
			if (registrationDao.deleteAsset(assetId, adminId))
				dashboard.setMessage("Asset deleted successfully");
			else
				dashboard.setMessage("Asset not deleted ");
			dashboard.setAssetList(registrationDao.getAssetList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setSupplierMaster(adminDao.getSupplierList());
			dashboard.setLocationList(adminDao.getLocationList());

		} catch (Exception e) {
			log.error("got error while deleting assets....", e);
		}
		return new ModelAndView("ams/assetregister", "dashboard", dashboard);

	}

	@RequestMapping(value = "/editAssetAllocation.do")
	public ModelAndView editAssetAllocation(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		AssetRegistration assetRegistration = new AssetRegistration();
		AssetAllocationDao assetAllocationDao = new AssetAllocationDao();
		int requestId = Integer.parseInt(request.getParameter("requeSTid"));
		RaiseRequest raiseRequest = new RaiseRequest();
		raiseRequest.setAllocatedAssets(assetAllocationDao.allocatedAssets());
		try {

			String buttonAction = request.getParameter("btnAction");
			String assetID = request.getParameter("assetidd");
			int assetId = Integer.parseInt(assetID);
			raiseRequest.setAssetId(assetId);
			String allocDate = request.getParameter("alloctionDate");
			java.util.Date allDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocDate);
			java.sql.Date sqlallocDate = new java.sql.Date(allDate.getTime());
			raiseRequest.setAllocationDate(sqlallocDate);

			String estReleasedDate = request.getParameter("estReleasedDate");
			java.util.Date estReDate = new SimpleDateFormat("yyyy-MM-dd").parse(estReleasedDate);
			java.sql.Date sqlestReDate = new java.sql.Date(estReDate.getTime());
			raiseRequest.setEstReleasedDate(sqlestReDate);
			String actualReleasedDate = request.getParameter("actualReleasedDate");
			java.util.Date actualReDate = new SimpleDateFormat("yyyy-MM-dd").parse(actualReleasedDate);
			java.sql.Date sqlactualReDate = new java.sql.Date(actualReDate.getTime());
			raiseRequest.setConfiguration(request.getParameter("AssetConfiguration"));
			raiseRequest.setAssetTag(request.getParameter("allAssetTag"));

			raiseRequest.setConfiguration(request.getParameter("AssetConfiguration"));
			raiseRequest.setAllocationDate(sqlallocDate);
			raiseRequest.setEstReleasedDate(sqlestReDate);
			raiseRequest.setActualReleasedDate(sqlactualReDate);
			raiseRequest.setReqStatus(buttonAction);

			if (buttonAction.equals("Allocate")) {
				if (assetAllocationDao.allocateAsset(raiseRequest, requestId, adminId))
					raiseRequest.setMessage("Asset allocated successfully");
				else
					raiseRequest.setMessage("Asset not allocated successfully");
				assetRegistration.setAssetId(assetId);
				assetAllocationDao.availabletoAllocated(assetRegistration, assetId);
				raiseRequest.setAllocatedAssets(assetAllocationDao.allocatedAssets());

			}
			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setEmployeelist(adminDao.getEmployeeList());
			dashboard.setAllocationRequestsListForApproval(
					assetAllocationDao.getAssetAllocationRequestListForApproval());

		} catch (Exception e) {
			log.error("got error", e);
		}

		return new ModelAndView("ams/assetallocationrequests", "raiseRequest", raiseRequest);
	}

	
	/*@RequestMapping(value = "/editAssetDetails.do")
	 * public ModelAndView editAssetDetails(HttpServletRequest request,
	 * HttpServletResponse response) { dashboard = new Dashboard();
	 * 
	 * AssetRegistration assetRegister = new AssetRegistration();
	 * ArrayList<AssetRegistration> assetList = new ArrayList<AssetRegistration>();
	 * AssetRegistrationDao registrationDao = new AssetRegistrationDao(); String
	 * buttonAction = (String) request.getParameter("btnAction");
	 * 
	 * HttpSession session = request.getSession(); User userSession = (User)
	 * session.getAttribute("user"); if (userSession == null) return new
	 * ModelAndView("other/login", "message", "Session is expired"); int adminId =
	 * userSession.getStaffID();
	 * 
	 * String AssetTag = request.getParameter("aSSetTag"); try { assetList = new
	 * ArrayList<AssetRegistration>(); int assetId; assetId =
	 * Integer.parseInt(request.getParameter("aSSetId")); int assetCatId; int
	 * subCatId; int supplierId; assetRegister.setAssetId(assetId); String Product =
	 * request.getParameter("proDuct"); String ModelVersion =
	 * request.getParameter("modelVErsion"); String SerialNumber =
	 * request.getParameter("serialNUmber"); int PurchaseCost =
	 * Integer.parseInt(request.getParameter("pcost")); String ProductKey =
	 * request.getParameter("Pkey"); String AssetOwner =
	 * request.getParameter("owner"); if (buttonAction.equals("Update") && AssetTag
	 * != null) { AssetTag = request.getParameter("aSSetTag"); assetId =
	 * Integer.parseInt(request.getParameter("aSSetId")); Product =
	 * request.getParameter("proDuct"); ModelVersion =
	 * request.getParameter("modelVErsion"); SerialNumber =
	 * request.getParameter("serialNUmber"); PurchaseCost =
	 * Integer.parseInt(request.getParameter("pcost")); ProductKey =
	 * request.getParameter("Pkey"); AssetOwner = request.getParameter("owner");
	 * assetRegister.setLocation(request.getParameter("LocationIDD"));
	 * assetRegister.setSupplier(request.getParameter("supId"));
	 * assetRegister.setAssetTag(AssetTag); assetRegister.setAssetId(assetId);
	 * assetRegister.setProduct(Product);
	 * assetRegister.setVersionModel(ModelVersion);
	 * assetRegister.setSerialNo(SerialNumber); assetRegister.setCost(PurchaseCost);
	 * assetRegister.setProductKey(ProductKey);
	 * assetRegister.setAssetOwner(AssetOwner);
	 * assetRegister.setChargerSerialNo(request.getParameter("chargerSrIDD")); if
	 * (registrationDao.updateAssetDetails(assetId, assetRegister, adminId))
	 * dashboard.setMessage("Asset details updated successfully"); else
	 * dashboard.setMessage("Asset details not updated ");
	 * dashboard.setAssetList(registrationDao.getAssetList());
	 * 
	 * } else { assetCatId = 0; subCatId = 0; supplierId = 0;
	 * registrationDao.addAssets(assetCatId, subCatId, supplierId, assetRegister,
	 * adminId); } } catch (Exception e) {
	 * log.error("Got error while updating asset details.......", e); } return new
	 * ModelAndView("ams/assetregister", "dashboard", dashboard); }
	 */
	@RequestMapping(value = "/editAssetDetails.do")
	public ModelAndView editAssetDetails(HttpServletRequest request, HttpServletResponse response) {
		dashboard = new Dashboard();

		AssetRegistration assetRegister = new AssetRegistration();
		ArrayList<AssetRegistration> assetList = new ArrayList<AssetRegistration>();
		AssetRegistrationDao registrationDao = new AssetRegistrationDao();
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		String AssetTag = request.getParameter("aSSetTag");
		try {
			assetList = new ArrayList<AssetRegistration>();
			int assetId;
			assetId = Integer.parseInt(request.getParameter("aSSetId"));
			int assetCatId;
			int subCatId;
			int supplierId;
			int PurchaseCost = Integer.parseInt(request.getParameter("pcost"));
			String AssetOwner = request.getParameter("owner");
			if (buttonAction.equals("Update") && AssetTag != null) {
				PurchaseCost = Integer.parseInt(request.getParameter("pcost"));
				AssetOwner = request.getParameter("owner");
				assetRegister.setAssetId(assetId);
				assetRegister.setCost(PurchaseCost);
				assetRegister.setAssetOwner(AssetOwner);
				if (registrationDao.updateAssetDetails(assetId, assetRegister, adminId))
				{
					dashboard.setMessage("Asset details updated successfully");
				}
				else
				{
					dashboard.setMessage("Asset details not updated ");
				}
				dashboard.setAssetList(registrationDao.getAssetList());
				
			} else {
				assetCatId = 0;
				subCatId = 0;
				supplierId = 0;
				registrationDao.addAssets(assetCatId, subCatId, supplierId, assetRegister, adminId);
			}
		} catch (Exception e) {
			log.error("Got error while updating asset details.......", e);
		}
		return new ModelAndView("ams/assetregister", "dashboard", dashboard);
	}
	@RequestMapping(value = "/approveRejectMaintenancerequest.do")
	public ModelAndView approveRejectMaintenancerequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {

		dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		Dashboard dashboard1 = new Dashboard();
		AdminDao adminDao = new AdminDao();
		AssetMaintenance assetMaintenance = new AssetMaintenance();
		AssetMaintenanceDao maintenanceDao = new AssetMaintenanceDao();
		RaiseRequest maintenanceRequest = new RaiseRequest();
		AssetRegistration assetRegistration = new AssetRegistration();
		String buttonAction = request.getParameter("btnAction");

		int requestId = Integer.parseInt(request.getParameter("reqIDD"));
		int staffId = Integer.parseInt(request.getParameter("stafffIDD"));
		int assetId = Integer.parseInt(request.getParameter("assetIDDDD"));
		String assetTag = request.getParameter("asseTTag");
		String warranty = request.getParameter("warrantyyyy");
		String coverage = request.getParameter("warrantyyyCoverage");
		int claim = Integer.parseInt(request.getParameter("claimm"));
		String probDescription = request.getParameter("probDesc");
		String comment = request.getParameter("mComment");
		dashboard1.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());
		assetMaintenance.setProblemDesc(probDescription);

		int maintenanceTypeId = Integer.parseInt(request.getParameter("MMaintTYpeId"));
		String maintenanceType = request.getParameter("MMaintTYpeId");

		int maintenanceStatusId = adminDao.maintenanceDefaultStatusId();
		if (buttonAction.equals("Approve")) {

			assetMaintenance.setRequestId(requestId);
			assetMaintenance.setStaffId(staffId);
			assetMaintenance.setAssetId(assetId);
			assetMaintenance.setAssetTag(assetTag);
			assetMaintenance.setMaintenanceType(maintenanceType);
			assetMaintenance.setMaintenanceTypeId(maintenanceTypeId);
			assetMaintenance.setMaintenanceStatusId(maintenanceStatusId);
			assetMaintenance.setWarranty(warranty);
			assetMaintenance.setCoverage(coverage);
			assetMaintenance.setClaim(claim);
			assetMaintenance.setProblemDesc(probDescription);

			assetMaintenance.setComment(comment);
			assetMaintenance.setReqStatus(buttonAction);
			maintenanceRequest.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
			/* for database change */

			if (maintenanceDao.approveRejectMaintenanceReq(assetMaintenance, adminId))
				maintenanceRequest.setMessage("Maintenance request approved successfully");
			else
				maintenanceRequest.setMessage("Maintenance request not approved");

			/* changing status pending to approved in raise request also */
			maintenanceRequest.setRequestId(requestId);
			maintenanceDao.changePendingToApprovedMaintenance(maintenanceRequest, requestId);

			/* change asset status as 'under maintenance' */
			assetRegistration.setAssetId(assetId);
			maintenanceDao.changeStatusAsUnderMaintenance(assetId);

			/* for displaying approved maintenance request list */
			maintenanceRequest.setApprovedMaintenanceList(maintenanceDao.getApprovedMaintenanceList());

			return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);
		} else if (buttonAction.equals("Reject")) {
			maintenanceDao.changePendingToRejectedMaintenance(requestId);
			maintenanceDao.approveRejectMaintenanceReq(assetMaintenance, adminId);
			maintenanceRequest.setApprovedMaintenanceList(maintenanceDao.getApprovedMaintenanceList());
			return new ModelAndView("ams/maintenancerequests", "maintenanceRequest", maintenanceRequest);
		} else {
			return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);
		}

	}

	@RequestMapping(value = "/approveRejectrequest.do")
	public ModelAndView approveRejectrequest(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {

		dashboard = new Dashboard();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();
		int staffId = Integer.parseInt(request.getParameter("staffId"));
		RaiseRequest raiseRequest = new RaiseRequest();
		AssetAllocation assetAllocation = new AssetAllocation();
		String buttonAction = request.getParameter("setBtnValue");
		int requestId = Integer.parseInt(request.getParameter("rEqID"));
		assetAllocation.setRequestId(requestId);
		assetAllocation.setReqStatus(buttonAction);
		assetAllocation.setRequestType(request.getParameter("reqTYpe"));
		assetAllocation.setStaffId(Integer.parseInt(request.getParameter("staffId")));
		assetAllocation.setFirstName(request.getParameter("firstName"));
		assetAllocation.setComment(request.getParameter("comment"));
		if (buttonAction.equals("Approve")) {
			// assetAllocationDao.updatePendingToApproved(requestId);
			if (assetAllocationDao.updatePendingToApproved(requestId))
				raiseRequest.setMessage("Request approved successfully");
			else
				raiseRequest.setMessage("Request not approved ");
			assetAllocationDao.approveAssetRequests(assetAllocation, adminId);
			raiseRequest.setRequestId(requestId);
			raiseRequest.setReqStatus(buttonAction);
			raiseRequest.setStaffId(staffId);
			raiseRequest.setRequestType(request.getParameter("reqTYpe"));
			raiseRequest.setFirstName(request.getParameter("firstName"));
			raiseRequest.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			raiseRequest.setAvailbleAsset(assetAllocationDao.availableAssets());
			raiseRequest.setAvailAssetListWithRequest(assetAllocationDao.availableAssetsForRequest(requestId));
			raiseRequest.setAvailbleAssetforApproveStatus(assetAllocationDao.availableAssetsforApproveStatus(staffId));
			return new ModelAndView("ams/assetallocation", "raiseRequest", raiseRequest);
		} else if (buttonAction.equals("Reject")) {
			log.info("buttonActionn " + buttonAction);
			raiseRequest.setRequestId(requestId);
			raiseRequest.setReqStatus(buttonAction);
			raiseRequest.setRequestType(request.getParameter("reqTYpe"));
			raiseRequest.setFirstName(request.getParameter("firstName"));
			raiseRequest.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			raiseRequest.setAvailbleAsset(assetAllocationDao.availableAssets());
			raiseRequest.setAvailAssetListWithRequest(assetAllocationDao.availableAssetsForRequest(requestId));
			raiseRequest.setAvailbleAssetforApproveStatus(assetAllocationDao.availableAssetsforApproveStatus(staffId));
			assetAllocationDao.approveAssetRequests(assetAllocation, adminId);

			if (assetAllocationDao.updatePendingToRejected(requestId))
				raiseRequest.setMessage("Request rejected successfully");
			else
				raiseRequest.setMessage("Request not rejected ");

			return new ModelAndView("ams/assetallocationrequests", "raiseRequest", raiseRequest);
		} else {
			return new ModelAndView("ams/assetallocation", "raiseRequest", raiseRequest);
		}

	}

	@RequestMapping(value = "/availableAssetsWithRespRequest.do")
	public ModelAndView availableAssetsWithRespRequest(HttpServletRequest request, HttpServletResponse response) {

		AssetAllocation assetAllocation = new AssetAllocation();
		int requestId = Integer.parseInt(request.getParameter("rEqID"));

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int staffId = userSession.getStaffID();

		try {

			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setAssetList(registrationDao.getAssetList());

			assetAllocation.setAvailAssetListWithRequest(assetAllocationDao.availableAssetsforApproveStatus(staffId));
		} catch (Exception e) {
			log.error("got error... ", e);
		}

		return new ModelAndView("ams/assetallocation", "dashboard", dashboard);

	}

	@RequestMapping(value = "/displayEmpReqDetails.do")
	public ModelAndView displayEmpReqDetails(HttpServletRequest request, HttpServletResponse response) {
		RaiseRequest raiseRequest = new RaiseRequest();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		// int staffId = userSession.getStaffID();
		int staffId = Integer.parseInt(request.getParameter("staffid"));
		try {
			int requestId = Integer.parseInt(request.getParameter("reqID"));
			String requestStatus = request.getParameter("reqStatus");
			String requestType = request.getParameter("reqType");
			String empName = request.getParameter("fname");
			raiseRequest.setStaffId(staffId);
			raiseRequest.setRequestType(requestType);
			raiseRequest.setFirstName(empName);
			raiseRequest.setReqStatus(requestStatus);
			raiseRequest.setRequestId(requestId);
			raiseRequest.setAllocatedAssets(assetAllocationDao.allocatedAssets());
			raiseRequest.setAvailbleAsset(assetAllocationDao.availableAssets());
			raiseRequest.setAvailAssetListWithRequest(assetAllocationDao.availableAssetsForRequest(requestId));
			raiseRequest.setAvailbleAssetforApproveStatus(assetAllocationDao.availableAssetsforApproveStatus(staffId));

			assetAllocationDao.displayEmpRequestDetails(requestId, raiseRequest);

		} catch (Exception e) {
			log.error("got error", e);
		}
		return new ModelAndView("ams/assetallocation", "raiseRequest", raiseRequest);

	}

	@RequestMapping(value = "/displayMaintenanceReqDetails.do")
	public ModelAndView displayMaintenanceReqDetails(HttpServletRequest request, HttpServletResponse response) {
		dashboard = new Dashboard();
		AdminDao adminDao = new AdminDao();
		RaiseRequest maintenanceRequest = new RaiseRequest();
		AssetMaintenanceDao maintenanceDao = new AssetMaintenanceDao();
		try {
			maintenanceRequest.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());
			maintenanceRequest.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
			maintenanceRequest.setStaffId(Integer.parseInt(request.getParameter("eMPId")));
			int requestId = Integer.parseInt(request.getParameter("rqID"));
			maintenanceRequest.setRequestId(requestId);
			maintenanceRequest.setRequestType(request.getParameter("rEQtype"));
			maintenanceRequest.setFirstName(request.getParameter("eMPName"));
			int assetId = Integer.parseInt(request.getParameter("assetIDD"));
			maintenanceRequest.setAssetId(assetId);
			maintenanceRequest.setAssetTag(request.getParameter("assEtTag"));
			maintenanceRequest.setWarranty(request.getParameter("warrntyy"));
			maintenanceRequest.setClaim(Integer.parseInt(request.getParameter("CLaim")));
			maintenanceRequest.setCoverage(request.getParameter("coveraggge"));
			maintenanceRequest.setReqStatus(request.getParameter("reqStatuS"));
			maintenanceRequest.setConfigDescription(request.getParameter("probDesc"));
			maintenanceDao.displayMaintenanceReqDetails(requestId, maintenanceRequest);
			maintenanceRequest.setApprovedMaintenanceList(maintenanceDao.getApprovedMaintenanceList());

		} catch (Exception e) {
			log.error("got error", e);
		}
		return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);

	}

	@RequestMapping(value = "/assetallocation.do")
	public ModelAndView assetallocation(HttpServletRequest request, HttpServletResponse response) {
		dashboard = new Dashboard();
		AdminDao adminDao = new AdminDao();
		AssetAllocationDao assetAllocationDao = new AssetAllocationDao();
		ArrayList<AssetAllocation> allocationRequestsList = null;
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int staffId = userSession.getStaffID();
		RaiseRequest raiseRequest = new RaiseRequest();

		ArrayList<EmployeeMaster> employeeList = null;
		try {
			employeeList = adminDao.getEmployeeList();
			dashboard.setEmployeelist(employeeList);
			allocationRequestsList = assetAllocationDao.getAssetAllocationRequestList(staffId);
			dashboard.setAllocationRequestsListForUser(allocationRequestsList);
			ArrayList<AssetAllocation> allocatedAssets = assetAllocationDao.allocatedAssets();
			raiseRequest.setAllocatedAssets(allocatedAssets);
		} catch (Exception e) {
			log.error("got exception ", e);
		}
		return new ModelAndView("ams/assetallocation", "raiseRequest", raiseRequest);

	}

	@RequestMapping(value = "/approvedmaintenancelist.do")
	public ModelAndView approvedmaintenancelist(HttpServletRequest request, HttpServletResponse response) {
		dashboard = new Dashboard();
		AssetMaintenanceDao assetMaintenanceDao = new AssetMaintenanceDao();
		RaiseRequest maintenanceRequest = new RaiseRequest();
		try {
			ArrayList<AssetMaintenance> approvedMaintenanceList = assetMaintenanceDao.getApprovedMaintenanceList();
			maintenanceRequest.setApprovedMaintenanceList(approvedMaintenanceList);
			dashboard.setMaintenanceRequestListForUser(assetMaintenanceDao.getApprovedMaintenanceList());
		} catch (Exception e) {
			log.error("got error ", e);
			e.printStackTrace();
		}

		return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);
	}

	@RequestMapping(value = "/changeMaintenanceStatus.do")
	public ModelAndView changeMaintenanceStatus(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException, ParseException {
		AssetMaintenance assetMaintenance = new AssetMaintenance();

		ArrayList<AssetMaintenance> approvedMaintenanceList = maintenanceDao.getApprovedMaintenanceList();
		assetMaintenance.setApprovedMaintenanceList(approvedMaintenanceList);
		String buttonAction = (String) request.getParameter("btnAction");

		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int adminId = userSession.getStaffID();

		AdminDao adminDao = new AdminDao();
		Dashboard dashboard = new Dashboard();

		RaiseRequest maintenanceRequest = new RaiseRequest();
		int requestId = Integer.parseInt(request.getParameter("RReqId"));

		String maintenanceDate = request.getParameter("mDate");
		java.util.Date javamaintDate = new SimpleDateFormat("yyyy-MM-dd").parse(maintenanceDate);
		java.sql.Date sqlMaintenanceDate = new java.sql.Date(javamaintDate.getTime());
		String requiredTime = request.getParameter("reqTime");
		String assetIdd = request.getParameter("asssetIId");
		int assetId = Integer.parseInt(assetIdd);
		int maintenanceStatusId = 0;
		try {
			if (buttonAction.equals("Update")) {
				maintenanceStatusId = Integer.parseInt(request.getParameter("MMaintStId"));
				assetMaintenance.setRequestId(requestId);
				assetMaintenance.setRequiredTime(requiredTime);
				assetMaintenance.setMaintenanceDate(sqlMaintenanceDate);
				assetMaintenance.setMaintenanceStatusId(maintenanceStatusId);

				maintenanceDao.finalMaintenanceStatus(assetMaintenance, adminId);
				maintenanceDao.changeStatusAsUnderMaintenance(assetId);

				approvedMaintenanceList = maintenanceDao.getApprovedMaintenanceList();

				maintenanceRequest.setApprovedMaintenanceList(approvedMaintenanceList);
				maintenanceRequest.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
				return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);

			} else {

				return new ModelAndView("ams/assetmaintenance", "maintenanceRequest", maintenanceRequest);
			}

		} catch (Exception e) {
			log.info(" got exception while displaying approved maintenance final list with status......");
		}
		return null;

	}

	@RequestMapping(value = "/allocatedAssets.do")
	public ModelAndView allocatedAssets(HttpServletRequest request, HttpServletResponse response) {

		dashboard = new Dashboard();
		AssetAllocationDao assetAllocationDao = new AssetAllocationDao();
		RaiseRequest raiseRequest = new RaiseRequest();
		try {
			ArrayList<AssetAllocation> allocatedAssets = assetAllocationDao.allocatedAssets();
			raiseRequest.setAllocatedAssets(allocatedAssets);
			dashboard.setAllocatedAssets(allocatedAssets);
		} catch (Exception e) {
			log.error(" got exception ", e);
		}
		return new ModelAndView("ams/assetallocation", "raiseRequest", raiseRequest);

	}

	@RequestMapping(value = "/assetallocationrequests.do")
	public ModelAndView assetallocationrequests(HttpServletRequest request, HttpServletResponse response) {
		Dashboard dashboard = new Dashboard();
		AdminDao adminDao = new AdminDao();
		HttpSession session = request.getSession();
		User userSession = (User) session.getAttribute("user");
		if (userSession == null)
			return new ModelAndView("other/login", "message", "Session is expired");
		int staffID = userSession.getStaffID();
		RaiseRequestDao raiseRequestDao = new RaiseRequestDao();

		try {
			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setEmployeelist(adminDao.getEmployeeList());
			dashboard.setAllocationRequestsListForApproval(
					assetAllocationDao.getAssetAllocationRequestListForApproval());

		} catch (Exception e) {
			log.error("got exception ", e);
		}
		return new ModelAndView("ams/assetallocationrequests", "dashboard", dashboard);

	}

	@RequestMapping(value = "/maintenancerequests.do")
	public ModelAndView maintenancerequests(HttpServletRequest request, HttpServletResponse response) {
		Dashboard dashboard = new Dashboard();

		AdminDao adminDao = new AdminDao();
		RaiseRequestDao raiseRequestDao = new RaiseRequestDao();
		AssetMaintenanceDao maintenanceDao = new AssetMaintenanceDao();
		HttpSession session = request.getSession();
		try {
			dashboard.setRequestList(raiseRequestDao.getRequestList());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setEmployeelist(adminDao.getEmployeeList());
			dashboard.setMaintenanceRequestList(maintenanceDao.getMaintenanceList1());

		} catch (Exception e) {
			log.error("got error while displaying maintenance request list....");
		}

		return new ModelAndView("ams/maintenancerequests", "dashboard", dashboard);

	}

	/*
	 * @RequestMapping(value = "/requestreport.do") public ModelAndView
	 * requestreport(HttpServletRequest request, HttpServletResponse response) {
	 * Dashboard dashboard = new Dashboard(); return new
	 * ModelAndView("ams/requestreport", "requestreport", dashboard);
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/maintenancereport.do") public ModelAndView
	 * maintenancereport(HttpServletRequest request, HttpServletResponse response) {
	 * Dashboard dashboard = new Dashboard(); return new
	 * ModelAndView("ams/maintenancereport", "dashboard", dashboard); }
	 * 
	 * @RequestMapping(value = "/allocationreport.do") public ModelAndView
	 * allocationreport(HttpServletRequest request, HttpServletResponse response) {
	 * Dashboard dashboard = new Dashboard(); return new
	 * ModelAndView("ams/allocationreport", "dashboard", dashboard); }
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkIsAssetAllocated.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkIsAssetAllocated(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "assetCatID") int assetCatID,
			@RequestParam(value = "assetSubCatID") int assetSubCatID)
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		int staffId = user.getStaffID();
		JSONObject mainJson = new JSONObject();

		if (adminDao.checkISAssetAllocated(assetCatID, assetSubCatID, staffId)) {
			mainJson.put("success", true);
			mainJson.put("message", "Yet you have not been allocated this type asset!");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/AssetSubCategory.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject AssetSubCategory(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		JSONObject mainJson = new JSONObject();
		AdminDao adminDao = new AdminDao();

		mainJson.put("assetSubCategoryMasterlist",
				adminDao.getAssetSubCategoryMasterlist(Integer.parseInt(req.getParameter("AssetCategory"))));

		for (AssetSubCategoryMaster assetSubCategoryMaster : adminDao
				.getAssetSubCategoryMasterlist(Integer.parseInt(req.getParameter("AssetCategory")))) {
			log.info("id=" + assetSubCategoryMaster.getSubCatId());
			log.info("name=" + assetSubCategoryMaster.getSubCatName());
		}

		return mainJson;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/assetdetailsaa.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject getNameforAsset(HttpServletRequest req, HttpServletResponse res)
			throws SQLException, PropertyVetoException {

		JSONObject mainJson = new JSONObject();
		AdminDao adminDao = new AdminDao();
		String tag = req.getParameter("assettag");
		log.info("assetTag===" + tag);
		log.info("dfdfdfsd");
		String assetallocatedname = adminDao.getAssetNameToAllocated(tag);
		mainJson.put("assetallocatednamea", assetallocatedname);
		log.info("nameof employee==" + assetallocatedname);
		return mainJson;
	}

}