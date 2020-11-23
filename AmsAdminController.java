package ai.rnt.ams.controller;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ai.rnt.ams.dao.AdminDao;
import ai.rnt.ams.model.Admin;
import ai.rnt.ams.model.AssetCategoryMaster;
import ai.rnt.ams.model.AssetMaintenance;
import ai.rnt.ams.model.MaintenanceStatusMaster;
import ai.rnt.ams.model.MaintenanceTypeMaster;
import ai.rnt.ams.model.AssetSubCategoryMaster;
import ai.rnt.ams.model.Dashboard;

import ai.rnt.ams.model.RequestTypeMaster;
import ai.rnt.ams.model.SupplierMaster;
import ai.rnt.ams.model.SupplierStatusMaster;
import ai.rnt.lms.model.User;


@Controller
public class AmsAdminController {
	AdminDao adminDao = new AdminDao();
	SupplierStatusMaster supplier = new SupplierStatusMaster();
	private static final Logger log = (Logger) LogManager.getLogger(AmsAdminController.class);

	@RequestMapping(value = "/admin.do")
	public ModelAndView admin(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("admin");
	}
	@RequestMapping(value = "/report.do")
	public ModelAndView report(HttpServletRequest req, HttpServletResponse res) {
		return new ModelAndView("report");
	}

	@RequestMapping(value = "/supplierstatus.do")
	public ModelAndView supplierstatus(HttpServletRequest request, HttpServletResponse response) {
		Admin admin = new Admin();
		try {
			admin.setSupplierStatusList(adminDao.getSupplierstatusList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/supplierstatus", "admin", admin);
	}

	@RequestMapping(value = "/maintenancetype.do")
	public ModelAndView maintenancetype(HttpServletRequest request, HttpServletResponse response) {
		Admin admin = new Admin();
		Dashboard dashboard = new Dashboard();
		try {
			admin.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());
			dashboard.setMaintenanceTypeMaster(adminDao.getMaintenanceTypeList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/maintenancetype", "admin", admin);

	}

	@RequestMapping(value = "/maintenancestatus.do")
	public ModelAndView maintenancestatus(HttpServletRequest request, HttpServletResponse response) {

		Admin admin = new Admin();
		Dashboard dashboard = new Dashboard();
		AssetMaintenance assetMaintenance = new AssetMaintenance();
		try {
			dashboard.setMaintenanceStatusMaster(adminDao.getMaintenanceStatusList());
			assetMaintenance.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
			admin.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/maintenancestatus", "admin", admin);
	}

	@RequestMapping(value = "/employee.do")
	public ModelAndView employee(HttpServletRequest request, HttpServletResponse response) {
		Dashboard dashboard = new Dashboard();
		Admin admin = new Admin();
		try {
			admin.setEmployeeList(adminDao.getEmployeeList());
			dashboard.setEmployeeList(adminDao.getEmployeeList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/employee", "admin", admin);
	}

	@RequestMapping(value = "/assetcategory.do")
	public ModelAndView assetcategory(HttpServletRequest request, HttpServletResponse response) {

		Dashboard dashboard = new Dashboard();
		Admin admin = new Admin();
		try {
			dashboard.setLocationList(adminDao.getLocationList());
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());
			dashboard.setAssetCategoryList(adminDao.getAssetCategoryList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/assetcategory", "admin", admin);

	}

	@RequestMapping(value = "/assetsubcategory.do")
	public ModelAndView assetsubcategory(HttpServletRequest request, HttpServletResponse response) {

		Admin admin = new Admin();
		Dashboard dashboard = new Dashboard();
		try {
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());
			admin.setSubCategoryList(adminDao.getSubCategoryList());
			dashboard.setSubCategoryList(adminDao.getSubCategoryList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}
		return new ModelAndView("ams/assetsubcategory", "admin", admin);
	}

	@RequestMapping(value = "/supplier.do")
	public ModelAndView supplier(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {

		Admin admin = new Admin();
		Dashboard dashboard = new Dashboard();
		try {
			admin.setSupplierStatusList(adminDao.getSupplierstatusList());
			admin.setSupplierMaster(adminDao.getSupplierList());
			dashboard.setSupplierMaster(adminDao.getSupplierList());
		} catch (Exception e) {
			log.error("Got exception:", e);
		}

		return new ModelAndView("ams/supplier", "admin", admin);
	}

	@RequestMapping(value = "/requesttype.do")
	public ModelAndView requesttype(HttpServletRequest request, HttpServletResponse response) {

		Dashboard dashboard = new Dashboard();
		Admin admin = new Admin();
		try {
			admin.setRequestTypeList(adminDao.getRequestTypeList());
			dashboard.setRequestTypeList(adminDao.getRequestTypeList());
		} catch (Exception e) {
			log.error(" Got exception : ", e);
		}
		return new ModelAndView("ams/requesttype", "admin", admin);
	}

	@RequestMapping(value = "/addAssetCategory.do")
	public ModelAndView addAssetCategory(HttpServletRequest request, HttpServletResponse response)
			throws NullPointerException {
		AssetCategoryMaster assetCategory = new AssetCategoryMaster();
		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();

		try {
			String assetCategoryName = request.getParameter("assetcategory");
			assetCategory.setAssetCatName(request.getParameter("assetcategory"));
			if (adminDao.checkAssetCategoryDup(assetCategoryName)) {
				admin.setMessage(" Asset category already exists ");
			} else {
				if (adminDao.addAssetCategory(assetCategory, adminId)) {
					admin.setMessage(" Asset category added successfully");
				} else {
					admin.setMessage(" Asset category not added successfully");
				}
			}
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());

		} catch (Exception e) {
			log.error("Got Exception while adding asset category  : ", e);
		}
		return new ModelAndView("ams/assetcategory", "admin", admin);
	}

	@RequestMapping(value = "/addSupplierStatus.do")
	public ModelAndView addSupplierStatus(HttpServletRequest request, HttpServletResponse res) {
		SupplierStatusMaster supplierStatus = new SupplierStatusMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String supplierStatusN = request.getParameter("supplierStatus");
			supplierStatus.setSupplierStatus(request.getParameter("supplierStatus"));
log.info("dfsdfsdfsdfsdfsd===="+supplierStatus.getSupplierStatus());
			if (adminDao.checkSupplierStatusDup(supplierStatusN)) 
				admin.setMessage(" Supplier status already exists");

				if (adminDao.addSupplierStatus(supplierStatus, adminId)) 
					admin.setMessage(" Supplier status added successfully ");
 else 
					admin.setMessage(" Supplier status not added ");
				

			admin.setSupplierStatusList(adminDao.getSupplierstatusList());
		} catch (Exception e) {
			log.error("Got Exception while adding supplier status ::  ", e);
		}
		return new ModelAndView("ams/supplierstatus", "admin", admin);
	}

	@RequestMapping(value = "/addsupplier.do")
	public ModelAndView addsupplier(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {

		SupplierMaster supplierMaster = new SupplierMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		admin.setSupplierStatusList(adminDao.getSupplierstatusList());
		int supplierStatusId = adminDao.suppActiveStatusId();

		try {
			supplierMaster.setSupplier(request.getParameter("supplier"));
			supplierMaster.setSupplierName(request.getParameter("suppliername"));
			supplierMaster.setSupplierContact(request.getParameter("contactnumber"));
			supplierMaster.setSupplierEmail(request.getParameter("emailid"));
			supplierMaster.setSupplierAddress1(request.getParameter("addressline1"));
			supplierMaster.setSupplierAddress2(request.getParameter("addressline2"));
			supplierMaster.setSupplierCity(request.getParameter("city"));
			supplierMaster.setSupplierState(request.getParameter("state"));
			supplierMaster.setSupplierCountry(request.getParameter("country"));
			supplierMaster.setSupplierStatusId(supplierStatusId);

			if (adminDao.addSupplier(supplierMaster, adminId))
				admin.setMessage("Supplier added successfully");
			else
				admin.setMessage("Supplier not added ");
			admin.setSupplierMaster(adminDao.getSupplierList());

		} catch (Exception e) {
			log.error("Got error while adding supplier", e);
		}
		return new ModelAndView("ams/supplier", "admin", admin);

	}

	@RequestMapping(value = "/addSubCategory.do")
	public ModelAndView addSubCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, PropertyVetoException {
		AssetSubCategoryMaster subCategoryMaster = new AssetSubCategoryMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();

		try {
			int AssetCatId = Integer.parseInt(request.getParameter("assetCatId"));
			String subCategoryName = request.getParameter("subCategory");
			subCategoryMaster.setAssetCatId(AssetCatId);
			subCategoryMaster.setSubCatName(request.getParameter("subCategory"));
			if (adminDao.checkSubCategoryDup(subCategoryName)) {
				admin.setMessage(" Sub category already exists");
			} else {
				if (adminDao.addSubCategory(subCategoryMaster, adminId)) {
					admin.setMessage("Sub category added successfully");
				} else {
					admin.setMessage("Sub category not added");
				}
			}
			admin.setSubCategoryList(adminDao.getSubCategoryList());
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());
		} catch (Exception e) {
			log.error("Got exception while adding sub category", e);
		}
		return new ModelAndView("ams/assetsubcategory", "admin", admin);
	}

	@RequestMapping(value = "/addrequesttype.do")
	public ModelAndView addrequesttype(HttpServletRequest request, HttpServletResponse response) {

		RequestTypeMaster requestTypeMaster = new RequestTypeMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String requestType = request.getParameter("requesttype");
			requestTypeMaster.setRequestType(request.getParameter("requesttype"));
			
			if (adminDao.checkRequestTypeDup(requestType)) {
				admin.setMessage("Request type already exists");
			} else {
				if (adminDao.addRequestType(requestTypeMaster, adminId)) {
					admin.setMessage("Request Type added successfully");
				} else {
					admin.setMessage("Request type not added");
				}
			}
			admin.setRequestTypeList(adminDao.getRequestTypeList());

		} catch (Exception e) {
			log.error("Got Exception while adding request type :: ", e);
		}
		return new ModelAndView("ams/requesttype", "admin", admin);
	}

	@RequestMapping(value = "/addmaintenancetype.do")

	public ModelAndView addmaintenancetype(HttpServletRequest request, HttpServletResponse response) {
		MaintenanceTypeMaster maintenanceTypeMaster = new MaintenanceTypeMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		Dashboard dashboard = new Dashboard();

		try {
			String maintenanceType = request.getParameter("maintenancetype");
			maintenanceTypeMaster.setMaintenanceType(request.getParameter("maintenancetype"));
			if (adminDao.checkMaintenanceTypeDup(maintenanceType)) {
				admin.setMessage(" Maintenance type  already exists");
			} else {
				if (adminDao.addMaintenanceType(maintenanceTypeMaster, adminId)) {
					admin.setMessage("Maintenance type added successfully");
				} else {
					admin.setMessage("Maintenance type not added ");

				}
			}
			admin.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());
			dashboard.setMaintenanceTypeMaster(adminDao.getMaintenanceTypeList());
		} catch (Exception e) {
			log.error("Got Exception while adding maintenance type:: ", e);
		}
		return new ModelAndView("ams/maintenancetype", "admin", admin);
	}

	@RequestMapping(value = "/addmaintenancestatus.do")
	public ModelAndView addmaintenancestatus(HttpServletRequest request, HttpServletResponse response) {

		MaintenanceStatusMaster maintenanceStatus = new MaintenanceStatusMaster();

		Admin admin = new Admin();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String maintenanceStatusName = request.getParameter("maintenancestatus");
			maintenanceStatus.setMaintenanceStatus(request.getParameter("maintenancestatus"));
			if (adminDao.checkMaintenanceStatusDup(maintenanceStatusName)) {
				admin.setMessage(" Maintenance status already exists");
			} else {
				if (adminDao.addMaintenanceStatus(maintenanceStatus, adminId)) {
					admin.setMessage(" Maintenance status added successfully ");

				} else {
					admin.setMessage(" Maintenance status not added ");
				}
			}
			admin.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
		} catch (Exception e) {
			log.error("Got Exception while adding maintenance status: ", e);
		}

		return new ModelAndView("ams/maintenancestatus", "admin", admin);
	}

	@RequestMapping(value = "/editAssetCategory.do")
	public ModelAndView editAssetCategory(HttpServletRequest request, HttpServletResponse res) {

		AssetCategoryMaster assetCategoryMaster = new AssetCategoryMaster();

		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {

			String assetCatName = (String) request.getParameter("assetcategory");
			int assetCatId = 0;
			String assetCategory = request.getParameter("assetcategory");
			assetCategoryMaster.setAssetCatName(request.getParameter("assetcategory"));
			if (buttonAction.equals("Update") && assetCatName != null) {
				assetCatId = Integer.parseInt(request.getParameter("assetcategoryId"));
				assetCategoryMaster.setAssetCatId(assetCatId);
				if (adminDao.checkAssetCategoryDup(assetCategory)) {
					admin.setMessage(" Asset category already exists");
				} else {
					if (adminDao.updateAssetCategory(assetCategoryMaster, adminId)) {
						admin.setMessage(" Asset category updated successfully");

					} else {
						admin.setMessage(" Asset category not updated");
					}
				}
				admin.setAssetCategoryList(adminDao.getAssetCategoryList());
			} else {
				adminDao.addAssetCategory(assetCategoryMaster, adminId);
			}
		} catch (Exception e) {
			log.error("Got Exception while updating asset category : : ", e);
		}
		return new ModelAndView("ams/assetcategory", "admin", admin);
	}

	@RequestMapping(value = "/editSupplier.do")
	public ModelAndView editSupplier(HttpServletRequest request, HttpServletResponse response) {

		SupplierStatusMaster supplierMaster = new SupplierStatusMaster();
		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int supplierStatusId = Integer.parseInt(request.getParameter("supStatusId"));
			String supplierName = request.getParameter("supplierName");
			if (buttonAction.equals("Update") && supplierName != null) {
				int supplierId = Integer.parseInt(request.getParameter("suppId"));
				supplierMaster.setSupplierId(supplierId);
				supplierMaster.setSupplierStatusId(supplierStatusId);

				if (adminDao.updateSupplier(supplierMaster, adminId))
					admin.setMessage("Supplier details updated successfully");
				else
					admin.setMessage("Supplier details not updated ");
				admin.setSupplierMaster(adminDao.getSupplierList());
				admin.setSupplierStatusList(adminDao.getSupplierstatusList());
			}
		} catch (Exception e) {
			log.error("Got error while updating supplier", e);
		}
		return new ModelAndView("ams/supplier", "admin", admin);
	}

	@RequestMapping(value = "/editSubCategory.do")
	public ModelAndView editSubCategory(HttpServletRequest request, HttpServletResponse response) {

		AssetSubCategoryMaster subCategoryMaster = new AssetSubCategoryMaster();

		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String subCatName = (String) request.getParameter("subcategory");
			int assetCatId = 0;
			int subCatId = 0;
			subCategoryMaster.setAssetCatId(assetCatId);
			subCategoryMaster.setSubCatId(subCatId);
			subCategoryMaster.setSubCatName(subCatName);
			if (buttonAction.equals("Update") && subCatName != null) {
				subCatId = Integer.parseInt(request.getParameter("subcategoryId"));
				subCategoryMaster.setAssetCatId(assetCatId);
				subCategoryMaster.setSubCatId(subCatId);
				if (adminDao.checkSubCategoryDup(subCatName)) {
					admin.setMessage("Sub category already exists");
				} else {
					if (adminDao.updateSubCategory(subCategoryMaster, adminId)) {
						admin.setMessage("Sub category updated successfully");
					} else {
						admin.setMessage("Sub category not updated");
					}
				}
				admin.setSubCategoryList(adminDao.getSubCategoryList());
				admin.setAssetCategoryList(adminDao.getAssetCategoryList());
			} else {
				adminDao.addSubCategory(subCategoryMaster, adminId);

			}
		} catch (Exception e) {
			log.error("Got error while updating subcategory", e);
		}
		return new ModelAndView("ams/assetsubcategory", "admin", admin);
	}

	@RequestMapping(value = "/editMaintenanceType.do")
	public ModelAndView editMaintenanceType(HttpServletRequest request, HttpServletResponse response) {

		MaintenanceTypeMaster maintenanceTypeMaster = new MaintenanceTypeMaster();
		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String maintenanceType = (String) request.getParameter("mTypeName");
			int maintenanceTypeId = 0;
			maintenanceTypeMaster.setMaintenanceType(request.getParameter("mTypeName"));
			if (buttonAction.equals("Update") && maintenanceType != null) {
				String id = request.getParameter("mTypeId");
				maintenanceTypeId = Integer.parseInt(id);
				maintenanceTypeMaster.setMaintenanceTypeId(maintenanceTypeId);

				if (adminDao.checkMaintenanceTypeDup(maintenanceType))
					admin.setMessage("Maintenance type already exists");
				else if (adminDao.updateMaintenanceType(maintenanceTypeId, maintenanceTypeMaster, adminId))
					admin.setMessage("Maintenance type updated successfully ");
				else
					admin.setMessage("Maintenance type not updated successfully ");

				admin.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());
			} else {
				adminDao.addMaintenanceType(maintenanceTypeMaster, adminId);
			}

		} catch (Exception e) {
			log.error("Got Exception while updating maintenance type : ", e);
		}
		return new ModelAndView("ams/maintenancetype", "admin", admin);
	}

	@RequestMapping(value = "/editMaintenanceStatus.do")
	public ModelAndView editMaintenanceStatus(HttpServletRequest request, HttpServletResponse response) {

		MaintenanceStatusMaster maintenanceStatusMaster = new MaintenanceStatusMaster();
		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String maintenanceStatus = (String) request.getParameter("maintenanceStatus");
			int maintenanceStatusId = 0;
			maintenanceStatusMaster.setMaintenanceStatus(request.getParameter("maintenanceStatus"));
			if (buttonAction.equals("Update") && maintenanceStatus != null) {
				maintenanceStatusId = Integer.parseInt(request.getParameter("maintenanceStatusId"));
				maintenanceStatusMaster.setMaintenanceStatusId(maintenanceStatusId);

				if (adminDao.checkMaintenanceStatusDup(maintenanceStatus)) {
					admin.setMessage(" Maintenance status already exists");
				} else {
					if (adminDao.updateMaintenanceStatus(maintenanceStatusId, maintenanceStatusMaster, adminId)) {
						admin.setMessage(" Maintenance status updated successfully ");
					} else {
						admin.setMessage(" Maintenance status not updated ");
					}
				}

				admin.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());
			} else {
				adminDao.addMaintenanceStatus(maintenanceStatusMaster, adminId);
			}

		} catch (Exception e) {
			log.error("got exception");
		}
		return new ModelAndView("ams/maintenancestatus", "admin", admin);
	}

	// changed method
	@RequestMapping(value = "/editSupplierStatus.do")
	public ModelAndView editSupplierStatus(HttpServletRequest request, HttpServletResponse res) {
		SupplierStatusMaster supplierStatusMaster = new SupplierStatusMaster();
		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {

			String supplierStatus = (String) request.getParameter("supplierStatus");
			int supplierStatusId = 0;
			supplierStatusMaster.setSupplierStatus(request.getParameter("supplierStatus"));
			if (buttonAction.equals("Update") && supplierStatus != null) {
				supplierStatusId = Integer.parseInt(request.getParameter("supplierStatusId"));
				supplierStatusMaster.setSupplierStatusId(supplierStatusId);

				if (adminDao.checkSupplierStatusDup(supplierStatus)) {
					admin.setMessage("Supplier status alredy exists");
				} else {

					if (adminDao.updateSupplierStatus(supplierStatusId, supplierStatusMaster, adminId)) {
						admin.setMessage("Supplier status updated successfully");
					} else {
						admin.setMessage("Supplier status not updated ");
					}

				}
				admin.setSupplierStatusList(adminDao.getSupplierstatusList());
			} else {
				adminDao.addSupplierStatus(supplierStatusMaster, adminId);
			}
		} catch (Exception e) {
			log.error("Got Exception while updating supplier status : : ", e);
		}
		return new ModelAndView("ams/supplierstatus", "admin", admin);
	}

	@RequestMapping(value = "/editRequestType.do")
	public ModelAndView editRequestType(HttpServletRequest request, HttpServletResponse response) {
		RequestTypeMaster requestTypeMaster = new RequestTypeMaster();

		String buttonAction = (String) request.getParameter("btnAction");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			String requestTypeName = (String) request.getParameter("requesttype");
			int requestTypeId = 0;
			requestTypeMaster.setRequestType(request.getParameter("requesttype"));
			if (buttonAction.equals("Update") && requestTypeName != null) {
				requestTypeId = Integer.parseInt(request.getParameter("requesttypeId"));
				requestTypeMaster.setRequestTypeId(requestTypeId);

				if (adminDao.checkRequestTypeDup(requestTypeName)) {
					admin.setMessage("Request type already exists");
				} else {
					if (adminDao.updateRequestType(requestTypeId, requestTypeMaster, adminId)) {
						admin.setMessage("Request type updated successfully ");
					} else {
						admin.setMessage("Request type not updated");
					}
				}
				admin.setRequestTypeList(adminDao.getRequestTypeList());

			} else {
				adminDao.addRequestType(requestTypeMaster, adminId);
			}
		} catch (Exception e) {
			log.error("Got Exception while updating request type : : ", e);
		}
		return new ModelAndView("ams/requesttype", "admin", admin);
	}

	@RequestMapping(value = "/deleteSupplierStatus.do")
	public ModelAndView deleteSupplierStatus(HttpServletRequest request, HttpServletResponse res) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int supplierStatusId = Integer.parseInt(request.getParameter("supStatusId"));

			if (adminDao.deleteSupplierStatus(supplierStatusId, adminId))
				admin.setMessage("Supplier status deleted successfully");
			else
				admin.setMessage("Supplier not status deleted ");
			admin.setSupplierStatusList(adminDao.getSupplierstatusList());
		} catch (Exception e) {
			log.error("Got exception while deleting supplier status :::", e);
		}
		return new ModelAndView("ams/supplierstatus", "admin", admin);
	}

	@RequestMapping(value = "/deleteAssetCategory.do")
	public ModelAndView deleteAssetCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int assetCategoryId = Integer.parseInt(request.getParameter("acid"));
			if (adminDao.deleteAssetCategory(assetCategoryId, adminId))
				admin.setMessage("Asset category deleted successfully");
			else
				admin.setMessage("Asset category not deleted ");
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());

		} catch (Exception e) {
			log.error("Got exception while deleting asset category ::", e);
		}
		return new ModelAndView("ams/assetcategory", "admin", admin);
	}

	@RequestMapping(value = "/deleteSupplier.do")
	public ModelAndView deleteSupplier(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int supplierId = Integer.parseInt(request.getParameter("supId"));
			if (adminDao.deleteSupplier(supplierId, adminId))
				admin.setMessage("Supplier deleted successfully");
			else
				admin.setMessage("Supplier not deleted ");
			admin.setSupplierMaster(adminDao.getSupplierList());
			admin.setSupplierStatusList(adminDao.getSupplierstatusList());

		} catch (Exception e) {
			log.error("Got error while deleting supplier :", e);
		}
		return new ModelAndView("ams/supplier", "admin", admin);
	}

	@RequestMapping(value = "/deleteSubCategory.do")
	public ModelAndView deleteSubCategory(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int subCatId = Integer.parseInt(request.getParameter("scid"));

			if (adminDao.deleteSubCategory(subCatId, adminId))
				admin.setMessage("Sub category deleted successfully");
			else
				admin.setMessage("Sub category not deleted ");
			admin.setSubCategoryList(adminDao.getSubCategoryList());
			admin.setAssetCategoryList(adminDao.getAssetCategoryList());

		} catch (Exception e) {
			log.error("Got error while deleting sub category :", e);
		}
		return new ModelAndView("ams/assetsubcategory", "admin", admin);
	}

	@RequestMapping(value = "/deleteRequestType.do")
	public ModelAndView deleteRequestType(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int requestTypeId = Integer.parseInt(request.getParameter("rtid"));

			if (adminDao.deleteRequestType(requestTypeId, adminId))
				admin.setMessage("Request type deleted successfully");
			else
				admin.setMessage("Request type not deleted ");
			admin.setRequestTypeList(adminDao.getRequestTypeList());
		} catch (Exception e) {
			log.error("Got exception while deleting request type::", e);

		}
		return new ModelAndView("ams/requesttype", "admin", admin);
	}

	@RequestMapping(value = "/deletemaintenancetype.do")
	public ModelAndView deletemaintenancetype(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int maintenanceTypeId = Integer.parseInt(request.getParameter("mtid"));

			if (adminDao.deleteMaintenanceType(maintenanceTypeId, adminId))
				admin.setMessage("Maintenance type deleted successfully ");
			else
				admin.setMessage("Maintenance type not deleted  ");
			admin.setMaintenanceTypeList(adminDao.getMaintenanceTypeList());

		} catch (Exception e) {
			log.error("Got exception while deleting maintenance type::", e);
		}
		return new ModelAndView("ams/maintenancetype", "admin", admin);
	}

	@RequestMapping(value = "/deleteMaintenanceStatus.do")
	public ModelAndView deleteMaintenanceStatus(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Admin admin = new Admin();
		if (user == null)
			return new ModelAndView("other/login", "message", "Session is expired");

		int adminId = user.getStaffID();
		try {
			int maintenanceStatusId = Integer.parseInt(request.getParameter("msid"));
			// adminDao.deleteMaintenanceStatus(maintenanceStatusId, adminId);
			if (adminDao.deleteMaintenanceStatus(maintenanceStatusId, adminId))
				admin.setMessage("Maintenance status deleted successfully");
			else
				admin.setMessage("Maintenance status not deleted ");

			admin.setMaintenanceStatusMaster(adminDao.getMaintenanceStatusList());
			admin.setMaintenanceStatusList(adminDao.getMaintenanceStatusList());

		} catch (Exception e) {
			log.error("Got exception while deleting maintenance status:", e);
		}
		return new ModelAndView("ams/maintenancestatus", "admin", admin);
	}
// added methods
	

	
	@RequestMapping(value = "/checkSupplierStausInUSe.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkSupplierStausInUSe(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "status") String supplierStatus )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkSupplierStausInUse(supplierStatus)) {
			mainJson.put("success", true);
			mainJson.put("message", "Supplier Status in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	
	@RequestMapping(value = "/checkAssetCategoryStausInUSe.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkAssetCategoryStausInUSe(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "assetCategory") String assetCategory )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkAssetCategoryInUse(assetCategory)) {
			mainJson.put("success", true);
			mainJson.put("message", "Asset Category in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	
	@RequestMapping(value = "/checkSubCategoryStausInUSe.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkSubCategoryStausInUSe(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "subCatName") String subCategory )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkSubCategoryInUse(subCategory)) {
			mainJson.put("success", true);
			mainJson.put("message", "Sub Category in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	@RequestMapping(value = "/checkReuestTypeInUSe.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkReuestTypeInUSe(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "reqType") String requestType )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkRequestTypeInUse(requestType)) {
			mainJson.put("success", true);
			mainJson.put("message", "Request Type in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	
	@RequestMapping(value = "/checkMaintenanceTypeInUse.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkMaintenanceTypeInUse(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "maintenanceType") String maintenanceType )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkMaintenanceTypeInUse(maintenanceType)) {
			mainJson.put("success", true);
			mainJson.put("message", "Maintenance Type in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	@RequestMapping(value = "/checkMaintenanceStatusInUse.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkMaintenanceStatusInUSe(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "maintenanceStatus") String maintenanceStatus )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkMaintenanceStatusInUse(maintenanceStatus)) {
			mainJson.put("success", true);
			mainJson.put("message", "Maintenance Status in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	@RequestMapping(value = "/checkSupplierInUse.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkSupplierInUse(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "supplier") String supplier )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkSupplierInUse(supplier)) {
			mainJson.put("success", true);
			mainJson.put("message", "Supplier in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	
	@RequestMapping(value = "/checkAssetInUse.do", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject checkAssetInUse(HttpServletRequest request, HttpServletResponse res,
			@RequestParam(value = "assetTag") String assetTag )
			throws SQLException, PropertyVetoException, ParseException {

		HttpSession session = request.getSession();
		JSONObject mainJson = new JSONObject();
		
		if (adminDao.checkAssetInUse(assetTag)) {
			mainJson.put("success", true);
			mainJson.put("message", "Asset in use ,You can't update/delete");
		} else {
			mainJson.put("success", false);
			mainJson.put("message", "Invalid input provided");
		}

		return mainJson;
	}
	
}
