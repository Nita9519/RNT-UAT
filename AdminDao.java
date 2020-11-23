package ai.rnt.ams.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ai.rnt.ams.model.AssetCategoryMaster;
import ai.rnt.ams.model.MaintenanceStatusMaster;
import ai.rnt.ams.model.MaintenanceTypeMaster;
import ai.rnt.ams.model.AssetSubCategoryMaster;
import ai.rnt.ams.model.Dashboard;
import ai.rnt.ams.model.EmployeeMaster;
import ai.rnt.ams.model.LocationMaster;
import ai.rnt.ams.model.RequestTypeMaster;
import ai.rnt.ams.model.SupplierMaster;
import ai.rnt.ams.model.SupplierStatusMaster;
import ai.rnt.main.dao.DBConnect;

public class AdminDao {
	private static final Logger log = (Logger) LogManager.getLogger(AdminDao.class);
	Dashboard dashBoard = new Dashboard();

	public boolean addSupplierStatus(SupplierStatusMaster supplierStatusMaster, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSupplierStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" INSERT into Supplier_Status_Master (");
		queryString.append(" Supplier_Status,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date)");
		queryString.append("VALUES(?,");
		queryString.append(adminId + ",");
		queryString.append("CURRENT_TIMESTAMP())");
		try {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());

			stmt.setString(1, supplierStatusMaster.getSupplierStatus());
			supplierStatusMaster.setCreatedBy(adminId);
			supplierStatusMaster.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addSupplierStatus = true;
				log.info("A new supplier status  inserted successfully!");
			}

		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addSupplierStatus;
	}

	public boolean addAssetCategory(AssetCategoryMaster assetCategory, int adminId)
			throws SQLException, PropertyVetoException, NullPointerException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addAssetCategory = false;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append(" INSERT INTO Asset_Category_Master(");
			queryString.append(" Asset_Cat_Name,");
			queryString.append(" Created_By,");
			queryString.append(" Created_Date)");
			queryString.append(" VALUES(?,");
			queryString.append(adminId + ",");
			queryString.append("CURRENT_TIMESTAMP())");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, assetCategory.getAssetCatName());
			assetCategory.setCreatedBy(adminId);
			assetCategory.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addAssetCategory = true;
				log.info("A new asset category  inserted successfully!");
			}
		}

		catch (NullPointerException e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addAssetCategory;
	}

	public int maintenanceDefaultStatusId() throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		int maintenanceDefaultStatusId = 0;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT Maintenance_Status_Id ");
		queryString.append(" FROM Maintenance_Status_Master");
		queryString.append(" WHERE Maintenance_Status = 'To be initiated' ");
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				maintenanceDefaultStatusId = rs.getInt(1);
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return maintenanceDefaultStatusId;
	}

	public int suppActiveStatusId() throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		int suppActiveStatusId = 0;

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT Supplier_Status_Id");
		queryString.append(" FROM Supplier_Status_Master");
		queryString.append(" WHERE Supplier_Status = 'Active'");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				suppActiveStatusId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return suppActiveStatusId;

	}

	public boolean addSupplier(SupplierMaster supplierMaster, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSupplier = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" INSERT into Asset_Supplier_Master(");
		queryString.append(" Supplier,");
		queryString.append(" Supplier_Name,");
		queryString.append(" Supplier_Contact,");
		queryString.append(" Supplier_Email,");
		queryString.append(" Supplier_Address1,");
		queryString.append(" Supplier_Address2,");
		queryString.append(" Supplier_City,");
		queryString.append(" Supplier_State,");
		queryString.append(" Supplier_Country,");
		queryString.append(" Supplier_Status_Id,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date)");
		queryString.append(" VALUES(?,?,?,?,?,?,?,?,?,");
		queryString.append(supplierMaster.getSupplierStatusId() + ",");
		queryString.append(adminId + ",");
		queryString.append(" CURRENT_TIMESTAMP())");
		try {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, supplierMaster.getSupplier());
			stmt.setString(2, supplierMaster.getSupplierName());
			stmt.setString(3, supplierMaster.getSupplierContact());
			stmt.setString(4, supplierMaster.getSupplierEmail());
			stmt.setString(5, supplierMaster.getSupplierAddress1());
			stmt.setString(6, supplierMaster.getSupplierAddress2());
			stmt.setString(7, supplierMaster.getSupplierCity());
			stmt.setString(8, supplierMaster.getSupplierState());
			stmt.setString(9, supplierMaster.getSupplierCountry());
			supplierMaster.setSupplierStatusId(supplierMaster.getSupplierStatusId());
			supplierMaster.setCreatedBy(adminId);
			supplierMaster.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addSupplier = true;
				log.info("A new supplier  inserted successfully!");
			}

		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addSupplier;

	}

	public boolean addSubCategory(AssetSubCategoryMaster subCategoryMaster, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addSubCategory = false;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" INSERT into Asset_Sub_Category_Master(");
		queryString.append(" Asset_Cat_Id,");
		queryString.append(" Sub_Cat_Name,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date)");
		queryString.append(" VALUES(?,?,");
		queryString.append(adminId + ",");
		queryString.append(" CURRENT_TIMESTAMP())");
		try {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, subCategoryMaster.getAssetCatId());
			stmt.setString(2, subCategoryMaster.getSubCatName());
			subCategoryMaster.setCreatedBy(adminId);
			subCategoryMaster.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addSubCategory = true;
				log.info("A new sub category  inserted successfully!");

			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addSubCategory;
	}

	public boolean addRequestType(RequestTypeMaster requestType, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addRequestType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" INSERT INTO Request_Type_Master(");
		queryString.append(" Request_Type_Name,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date) ");
		queryString.append(" VALUES(?,");
		queryString.append(adminId + ",");
		queryString.append(" CURRENT_TIMESTAMP())");
		try {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, requestType.getRequestType());
			requestType.setCreatedBy(adminId);
			requestType.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addRequestType = true;
				log.info(" Request type inserted successfully!");
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addRequestType;
	}

	public boolean addMaintenanceType(MaintenanceTypeMaster maintenanceType, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addMaintenanceType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" INSERT into Maintenance_Type_Master(");
		queryString.append(" Maintenance_Type_Name,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date)");
		queryString.append(" VALUES (?,");
		queryString.append(adminId + ",");
		queryString.append(" CURRENT_TIMESTAMP())");
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, maintenanceType.getMaintenanceType());
			maintenanceType.setCreatedBy(adminId);
			maintenanceType.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addMaintenanceType = true;
				log.info("maintenance type added successfully..");
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addMaintenanceType;
	}

	public boolean addMaintenanceStatus(MaintenanceStatusMaster maintenanceStatus, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addMaintenanceStatus = false;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" INSERT into Maintenance_Status_Master(");
		queryString.append(" Maintenance_Status,");
		queryString.append(" Created_By,");
		queryString.append(" Created_Date)");
		queryString.append(" VALUES (?,");
		queryString.append(adminId + ",");
		queryString.append(" CURRENT_TIMESTAMP())");
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, maintenanceStatus.getMaintenanceStatus());
			maintenanceStatus.setCreatedBy(adminId);
			maintenanceStatus.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				addMaintenanceStatus = true;
				log.info("maintenance status added successfully..");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return addMaintenanceStatus;
	}

	public ArrayList<SupplierStatusMaster> getSupplierstatusList() throws SQLException, PropertyVetoException {
		SupplierStatusMaster suppplierstatusmaster = new SupplierStatusMaster();
		ArrayList<SupplierStatusMaster> supplierstatusList = new ArrayList<SupplierStatusMaster>();

		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT Supplier_Status_Id,");
		queryString.append(" Supplier_Status");
		queryString.append(" FROM Supplier_Status_Master ");
		queryString.append(" WHERE Deleted_By is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				suppplierstatusmaster = new SupplierStatusMaster();
				suppplierstatusmaster.setSupplierStatusId(rs.getInt(1));
				suppplierstatusmaster.setSupplierStatus(rs.getString(2));
				supplierstatusList.add(suppplierstatusmaster);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			connection.close();
		}
		return supplierstatusList;
	}

	public ArrayList<LocationMaster> getLocationList() throws SQLException, PropertyVetoException {

		LocationMaster locationMaster = new LocationMaster();
		ArrayList<LocationMaster> locationList = new ArrayList<LocationMaster>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT Location_Id,");
		queryString.append(" Location");
		queryString.append(" FROM location_master");
		queryString.append(" WHERE deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				locationMaster = new LocationMaster();
				locationMaster.setLocationId(rs.getInt(1));
				locationMaster.setLocation(rs.getString(2));
				locationList.add(locationMaster);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			connection.close();
		}
		return locationList;
	}

	public ArrayList<EmployeeMaster> getEmployeeList() throws SQLException, PropertyVetoException {

		EmployeeMaster employeeMaster = new EmployeeMaster();
		ArrayList<EmployeeMaster> employeeList = new ArrayList<EmployeeMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Staff_Id,");
		queryString.append(" M_Name,");
		queryString.append(" L_Name ");
		queryString.append(" FROM employee_master");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				employeeMaster = new EmployeeMaster();
				employeeMaster.setStaffId(rs.getInt(1));
				employeeMaster.setFirstName(rs.getString(2));
				employeeMaster.setLastName(rs.getString(3));
				employeeList.add(employeeMaster);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return employeeList;

	}

	public ArrayList<AssetCategoryMaster> getAssetCategoryList() throws SQLException, PropertyVetoException {

		AssetCategoryMaster assetCategory = new AssetCategoryMaster();
		ArrayList<AssetCategoryMaster> assetCategoryList = new ArrayList<AssetCategoryMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Asset_Cat_Id,");
		queryString.append(" Asset_Cat_Name");
		queryString.append(" FROM Asset_Category_Master ");
		queryString.append(" WHERE Deleted_By is NULL ");

		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				assetCategory = new AssetCategoryMaster();
				assetCategory.setAssetCatId(rs.getInt(1));
				assetCategory.setAssetCatName(rs.getString(2));
				assetCategoryList.add(assetCategory);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return assetCategoryList;
	}

	public AssetCategoryMaster getcategory(int assetCatId) throws SQLException, PropertyVetoException {

		AssetCategoryMaster category = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Asset_Cat_Id,");
		queryString.append(" Asset_Cat_Name");
		queryString.append(" FROM Asset_Category_Master");
		queryString.append(" WHERE Asset_Cat_Id =");
		queryString.append(assetCatId);
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				category = new AssetCategoryMaster();
				category.setAssetCatId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return category;
	}

	public ArrayList<RequestTypeMaster> getRequestTypeList() throws SQLException, PropertyVetoException {

		RequestTypeMaster requestType = new RequestTypeMaster();
		ArrayList<RequestTypeMaster> requestTypeList = new ArrayList<RequestTypeMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Request_Type_Id,");
		queryString.append(" Request_Type_Name");
		queryString.append(" FROM Request_Type_Master");
		queryString.append(" WHERE Deleted_By is NULL");

		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				requestType = new RequestTypeMaster();
				requestType.setRequestTypeId(rs.getInt(1));
				requestType.setRequestType(rs.getString(2));
				requestTypeList.add(requestType);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return requestTypeList;
	}

	public ArrayList<MaintenanceStatusMaster> getMaintenanceStatusList() throws SQLException, PropertyVetoException {

		MaintenanceStatusMaster maintenanceStatus = new MaintenanceStatusMaster();
		ArrayList<MaintenanceStatusMaster> maintenanceStatusList = new ArrayList<MaintenanceStatusMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Maintenance_Status_Id,");
		queryString.append(" Maintenance_Status");
		queryString.append(" FROM Maintenance_Status_Master");
		queryString.append(" WHERE Deleted_By is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				maintenanceStatus = new MaintenanceStatusMaster();
				maintenanceStatus.setMaintenanceStatusId(rs.getInt(1));
				maintenanceStatus.setMaintenanceStatus(rs.getString(2));
				maintenanceStatusList.add(maintenanceStatus);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return maintenanceStatusList;
	}

	public ArrayList<AssetSubCategoryMaster> getSubCategoryList() throws SQLException, PropertyVetoException {

		AssetSubCategoryMaster subCategoryMaster = new AssetSubCategoryMaster();
		ArrayList<AssetSubCategoryMaster> subCategoryList = new ArrayList<AssetSubCategoryMaster>();
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT sc.Sub_Cat_Id,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name");
		queryString.append(" FROM Asset_Category_Master AS ac");
		queryString.append(" INNER JOIN");
		queryString.append(" Asset_Sub_Category_Master AS sc");
		queryString.append(" ON ac.Asset_Cat_Id = sc.Asset_Cat_Id ");
		queryString.append(" WHERE sc.Deleted_By is NULL ");

		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				subCategoryMaster = new AssetSubCategoryMaster();
				subCategoryMaster.setSubCatId(rs.getInt(1));
				subCategoryMaster.setAssetCatName(rs.getString(2));
				subCategoryMaster.setSubCatName(rs.getString(3));
				subCategoryList.add(subCategoryMaster);
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return subCategoryList;
	}

	public ArrayList<SupplierMaster> getSupplierList() throws SQLException, PropertyVetoException {

		SupplierMaster supplierMaster = new SupplierMaster();
		ArrayList<SupplierMaster> supplierList = new ArrayList<SupplierMaster>();
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT s.Supplier_Id,");
		queryString.append(" s.Supplier,");
		queryString.append(" s.Supplier_Name,");
		queryString.append(" s.Supplier_Contact,");
		queryString.append(" s.Supplier_Email,");
		queryString.append(" s.Supplier_Address1,");
		queryString.append(" s.Supplier_Address2,");
		queryString.append(" s.Supplier_City, ");
		queryString.append(" s.Supplier_State,");
		queryString.append(" s.Supplier_Country,");
		queryString.append(" ss.Supplier_Status");
		queryString.append(" FROM Supplier_Status_Master AS ss ");
		queryString.append(" INNER JOIN  ");
		queryString.append(" Asset_Supplier_Master AS s");
		queryString.append(" ON ss.Supplier_Status_Id = s.Supplier_Status_Id ");
		queryString.append(" WHERE s.Deletedby is NULL");

		Connection connection = DBConnect.getConnection();
		int count = 0;
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				++count;
				supplierMaster = new SupplierMaster();
				supplierMaster.setSupplierId(rs.getInt(1));
				supplierMaster.setSupplier(rs.getString(2));
				supplierMaster.setSupplierName(rs.getString(3));
				supplierMaster.setSupplierContact(rs.getString(4));
				supplierMaster.setSupplierEmail(rs.getString(5));
				supplierMaster.setSupplierAddress1(rs.getString(6));
				supplierMaster.setSupplierAddress2(rs.getString(7));
				supplierMaster.setSupplierCity(rs.getString(8));
				supplierMaster.setSupplierState(rs.getString(9));
				supplierMaster.setSupplierCountry(rs.getString(10));
				supplierMaster.setSupplierStatus(rs.getString(11));
				supplierList.add(supplierMaster);
			}

			if (count == 0) {
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return supplierList;
	}

	public ArrayList<MaintenanceTypeMaster> getMaintenanceTypeList() throws SQLException, PropertyVetoException {

		MaintenanceTypeMaster maintenancetype = new MaintenanceTypeMaster();
		ArrayList<MaintenanceTypeMaster> maintenancetypelist = new ArrayList<MaintenanceTypeMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement stmt = null;
		queryString.append(" SELECT Maintenance_Type_Id,");
		queryString.append(" Maintenance_Type_Name");
		queryString.append(" FROM Maintenance_Type_Master");
		queryString.append(" WHERE Deleted_By IS NULL");

		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();

			while (rs.next()) {
				maintenancetype = new MaintenanceTypeMaster();
				maintenancetype.setMaintenanceTypeId(rs.getInt(1));
				maintenancetype.setMaintenanceType(rs.getString(2));
				maintenancetypelist.add(maintenancetype);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return maintenancetypelist;
	}

	public boolean updateSupplierStatus(int supplierStatusId, SupplierStatusMaster supplierStatusMaster, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSupplierStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Supplier_Status_Master");
		queryString.append(" SET Supplier_Status =?");
		queryString.append(", ");
		queryString.append(" Updated_By =?");
		queryString.append(", ");
		queryString.append(" Updated_Date = CURRENT_TIMESTAMP(),");
		queryString.append(" Deleted_By = NULL,");
		queryString.append(" Deleted_Date = NULL");
		queryString.append(" WHERE Supplier_Status_Id =");
		queryString.append(supplierStatusId);
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, supplierStatusMaster.getSupplierStatus());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateSupplierStatus = true;
				log.info("supplier status updated successfully");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}

		return updateSupplierStatus;
	}

	public boolean updateAssetCategory(AssetCategoryMaster assetCategoryMaster, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateAssetCategory = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Category_Master");
		queryString.append(" SET Asset_Cat_Name =?");
		queryString.append(",");
		queryString.append(" Updated_By=?");
		queryString.append(", ");
		queryString.append(" Updated_Date = CURRENT_TIMESTAMP(),");
		queryString.append(" Deleted_By = NULL,");
		queryString.append(" Deleted_Date = NULL");
		queryString.append(" WHERE Asset_Cat_Id = ");
		queryString.append(assetCategoryMaster.getAssetCatId());
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, assetCategoryMaster.getAssetCatName());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateAssetCategory = true;
				log.info("asset category updated successfully");

			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return updateAssetCategory;

	}

	public boolean updateSupplier(SupplierStatusMaster supplierMaster, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSupplier = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Supplier_Master");
		queryString.append(" SET Supplier_Status_Id =?");
		queryString.append(",");
		queryString.append(" Updated_By =?");
		queryString.append(",");
		queryString.append(" Updated_Date=CURRENT_TIMESTAMP() ");
		queryString.append(" WHERE Supplier_Id = ");
		queryString.append(supplierMaster.getSupplierId());
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, supplierMaster.getSupplierStatusId());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateSupplier = true;
				log.info("updated supplier status ...");

			}
		} catch (NumberFormatException e) {
			log.error("Got Exception while updating supplier status ", e);
		} finally {
			if (connection != null) {
				try {
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {

						}
					}
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return updateSupplier;

	}

	public boolean updateSubCategory(AssetSubCategoryMaster subCategoryMaster, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateSubCategory = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Sub_Category_Master ");
		queryString.append(" SET Sub_Cat_Name =?");
		queryString.append(", ");
		queryString.append(" Updated_By =?");
		queryString.append(",");
		queryString.append(" Updated_Date=CURRENT_TIMESTAMP(), ");
		queryString.append(" Deleted_By = NULL,");
		queryString.append(" Deleted_Date = NULL");
		queryString.append(" WHERE Sub_Cat_Id = ");
		queryString.append(subCategoryMaster.getSubCatId());
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, subCategoryMaster.getSubCatName());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();

			if (rowUpdated > 0) {
				updateSubCategory = true;
				log.info(" sub category updated successfully ...");

			}
		} catch (NumberFormatException e) {
			log.error("Got Exception while updating sub category : ", e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return updateSubCategory;

	}

	public boolean updateMaintenanceType(int maintenanceTypeId, MaintenanceTypeMaster maintenanceTypeMaster,
			int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateMaintenanceType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Maintenance_Type_Master ");
		queryString.append(" SET Maintenance_Type_Name =?");
		queryString.append(", ");
		queryString.append(" Updated_By =?");
		queryString.append(",");
		queryString.append(" Updated_Date=CURRENT_TIMESTAMP(), ");
		queryString.append(" Deleted_By = NULL,");
		queryString.append(" Deleted_Date=NULL");
		queryString.append(" WHERE Maintenance_Type_Id = ");
		queryString.append(maintenanceTypeMaster.getMaintenanceTypeId());
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, maintenanceTypeMaster.getMaintenanceType());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateMaintenanceType = true;
				log.info(" maintenance type updated successfully ...");
			}

		} catch (NumberFormatException e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return updateMaintenanceType;
	}

	public boolean updateMaintenanceStatus(int maintenanceStatusId, MaintenanceStatusMaster maintenanceStatusMaster,
			int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateMaintenanceStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Maintenance_Status_Master ");
		queryString.append(" SET Maintenance_Status =?");
		queryString.append(",");
		queryString.append(" Updated_By =?");
		queryString.append(",");
		queryString.append(" Updated_Date = CURRENT_TIMESTAMP(), ");
		queryString.append(" Deleted_By = NULL");
		queryString.append(" WHERE Maintenance_Status_Id = ");
		queryString.append(maintenanceStatusMaster.getMaintenanceStatusId());
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, maintenanceStatusMaster.getMaintenanceStatus());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateMaintenanceStatus = true;
				log.info("maintenance status updated successfully..");
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return updateMaintenanceStatus;

	}

	public boolean updateRequestType(int requestTypeId, RequestTypeMaster requestTypeMaster, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRequestType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Request_Type_Master ");
		queryString.append(" SET Request_Type_Name =?");
		queryString.append(",");
		queryString.append(" Updated_By =?");
		queryString.append(", ");
		queryString.append(" Updated_Date = CURRENT_TIMESTAMP(),");
		queryString.append(" Deleted_By = NULL, ");
		queryString.append(" Deleted_Date = NULL");
		queryString.append(" WHERE Request_Type_Id = ");
		queryString.append(requestTypeMaster.getRequestTypeId());
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, requestTypeMaster.getRequestType());
			stmt.setInt(2, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateRequestType = true;
				log.info(" request type updated successfully ...");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return updateRequestType;
	}

	public Boolean deleteSupplierStatus(int supplierStatusId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSupplierStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Supplier_Status_Master");
		queryString.append(" SET Deleted_By =?");
		queryString.append(",");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		queryString.append(" WHERE Deleted_By IS NULL");
		queryString.append(" AND Supplier_Status_Id =");
		queryString.append(supplierStatusId);
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				deleteSupplierStatus = true;
				log.info("supplier status deleted successfully");
			}
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return deleteSupplierStatus;
	}

	public boolean deleteAssetCategory(int assetCatId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteAssetCategory = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Category_Master");
		queryString.append(" SET Deleted_By=?");
		queryString.append(",");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		queryString.append(" WHERE Deleted_By IS NULL");
		queryString.append(" AND Asset_Cat_Id =");
		queryString.append(assetCatId);
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteAssetCategory = true;
				log.info("assetcategory deleted successfully");
			}

		} catch (Exception e) {
			log.error("Got exception while deleting asset category ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}

		return deleteAssetCategory;

	}

	public boolean deleteSupplier(int supplierId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSupplier = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Supplier_Master");
		queryString.append(" SET Deletedby =?");
		queryString.append(",");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		 queryString.append(" WHERE Deletedby IS NULL"); 
		queryString.append(" AND Supplier_Id =");
		queryString.append(supplierId);
		try {

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteSupplier = true;
				log.info("supplier record deleted successfully ...");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteSupplier;

	}

	public boolean deleteSubCategory(int subCatId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteSubCategory = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Asset_Sub_Category_Master ");
		queryString.append(" SET Deleted_By =?");
		queryString.append(", ");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP() ");
		queryString.append(" WHERE Deleted_By IS NULL ");
		queryString.append(" AND Sub_Cat_Id =");
		queryString.append(subCatId);
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteSubCategory = true;
				log.info("asset sub category deleted successfully");
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteSubCategory;
	}

	public boolean deleteRequestType(int requestTypeId, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteRequestType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Request_Type_Master");
		queryString.append(" SET Deleted_By=?");
		queryString.append(", ");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		queryString.append(" WHERE Deleted_By IS NULL");
		queryString.append(" AND Request_Type_Id =");
		queryString.append(requestTypeId);
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteRequestType = true;
				log.info("request type deleted successfully..");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteRequestType;
	}

	public boolean deleteMaintenanceType(int maintenanceTypeId, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteMaintenanceType = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Maintenance_Type_Master");
		queryString.append(" SET Deleted_By=?");
		queryString.append(",");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		queryString.append(" WHERE Deleted_By IS NULL");
		queryString.append(" AND Maintenance_Type_Id =");
		queryString.append(maintenanceTypeId);
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteMaintenanceType = true;
				log.info(" Maintenance type deleted successfully..");
			}

		} catch (Exception e) {
			e.getStackTrace();

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteMaintenanceType;
	}

	public boolean deleteMaintenanceStatus(int maintenanceStatusId, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteMaintenanceStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" UPDATE Maintenance_Status_Master");
		queryString.append(" SET Deleted_By =?");
		queryString.append(",");
		queryString.append(" Deleted_Date = CURRENT_TIMESTAMP()");
		queryString.append(" WHERE Deleted_By IS NULL");
		queryString.append(" AND Maintenance_Status_Id =");
		queryString.append(maintenanceStatusId);
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			int rowDeleted = stmt.executeUpdate();
			if (rowDeleted > 0) {
				deleteMaintenanceStatus = true;
				log.info("Maintenance status deleted successfully..");
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}
		}
		return deleteMaintenanceStatus;
	}

	public SupplierStatusMaster getsupplierstatus(SupplierStatusMaster supplierStatus)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		SupplierStatusMaster supplierStatus1 = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT Supplier_Status_Id,");
		queryString.append(" Supplier_Status");
		queryString.append(" FROM Supplier_Status_Master");
		queryString.append(" WHERE Supplier_Status ='");
		queryString.append(supplierStatus + "'");

		try {
			stmt = connection.prepareStatement(queryString.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				supplierStatus1 = new SupplierStatusMaster();
				supplierStatus1.setSupplierStatusId(rs.getInt(1));
				supplierStatus1.setSupplierStatus(rs.getString(2));
			}
		} catch (Exception e) {
			log.error("got exception while fetching supplierStatus: ", e);
		} finally {
			rs.close();
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {

				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {

				}
			}

		}
		return supplierStatus1;
	}

	public Boolean checkAssetCategoryDup(String assetCategory) throws SQLException, PropertyVetoException {

		boolean assetCategoryFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Asset_Category_Master");
		queryString.append(" WHERE asset_cat_name = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, assetCategory);

			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					assetCategoryFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return assetCategoryFlag;
	}

	public Boolean checkSupplierStatusDup(String supplierStatus) throws SQLException, PropertyVetoException {

		boolean supplierStatusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Supplier_Status_Master");
		queryString.append(" WHERE supplier_status = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, supplierStatus);

			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					supplierStatusFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}

		return supplierStatusFlag;
	}

	public Boolean checkMaintenanceTypeDup(String maintenanceType) throws SQLException, PropertyVetoException {

		boolean maintenanceTypeFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Maintenance_Type_Master");
		queryString.append(" WHERE maintenance_type_name = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, maintenanceType);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					maintenanceTypeFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return maintenanceTypeFlag;
	}

	public Boolean checkRequestTypeDup(String requestType) throws SQLException, PropertyVetoException {

		boolean requestTypeFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Request_Type_Master");
		queryString.append(" WHERE request_type_name = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, requestType);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					requestTypeFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return requestTypeFlag;
	}

	public Boolean checkMaintenanceStatusDup(String maintenanceStatus) throws SQLException, PropertyVetoException {

		boolean maintenanceStatusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Maintenance_Status_Master");
		queryString.append(" WHERE maintenance_status = ?");
		queryString.append(" AND deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, maintenanceStatus);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					maintenanceStatusFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return maintenanceStatusFlag;
	}

	public Boolean checkSubCategoryDup(String subCategoryName) throws SQLException, PropertyVetoException {

		boolean subCategoryFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT COUNT(*)");
		queryString.append(" FROM Asset_Sub_Category_Master AS  sc,");
		queryString.append(" Asset_Category_Master AS ac");
		queryString.append(" WHERE sc.sub_cat_name = ?");
		queryString.append(" AND ac.asset_cat_id = sc.asset_cat_id");
		queryString.append(" AND sc.deleted_by is NULL");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, subCategoryName);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					subCategoryFlag = true;
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return subCategoryFlag;
	}

//added code on 9-dec-2019
	public ArrayList<AssetSubCategoryMaster> getAssetSubCategoryMasterlist(int assetCategory)
			throws SQLException, PropertyVetoException {
		AssetSubCategoryMaster subCategoryMaster = null;
		ArrayList<AssetSubCategoryMaster> assetSubCategoryMasterlist = new ArrayList<AssetSubCategoryMaster>();

		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT sc.Sub_Cat_Id,ac.Asset_Cat_Name,sc.Sub_Cat_Name");
		queryString.append(" FROM Asset_Category_Master as ac,Asset_Sub_Category_Master as sc ");
		queryString.append(" WHERE ac.Asset_Cat_Id=sc.Asset_Cat_Id and ac.Asset_Cat_Id=?");
		queryString.append(" AND sc.Deleted_By is NULL  ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, assetCategory);
			rs = statement.executeQuery();
			while (rs.next()) {
				subCategoryMaster = new AssetSubCategoryMaster();
				subCategoryMaster.setSubCatId(rs.getInt(1));
				subCategoryMaster.setAssetCatName(rs.getString(2));
				subCategoryMaster.setSubCatName(rs.getString(3));
				assetSubCategoryMasterlist.add(subCategoryMaster);

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return assetSubCategoryMasterlist;
	}

	public String getAssetNameToAllocated(String assetTag) throws SQLException, PropertyVetoException {

		String name = null;
		ResultSet rs = null;
		PreparedStatement statement = null;
		Dashboard dashBoardObj = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT em.F_Name FROM employee_master as em inner join Asset_Allocation as aa ");
		queryString.append(" INNER JOIN Asset_Registration as ar WHERE ");
		queryString.append(" aa.Asset_Tag=?");
		queryString.append(" and aa.Staff_Id=em.Staff_Id AND aa.Req_Status='Allocated' AND aa.Asset_Tag=ar.Asset_Tag ");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, assetTag);
			rs = statement.executeQuery();

			if (rs != null) {
				log.info("name==" + rs.getString(1));
				name = rs.getString(1);
			} else {
				log.info("not found");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return name;
	}
// added code on 6-sep-2019

	public Boolean checkSupplierStausInUse(String supplierStatus) throws SQLException, PropertyVetoException {

		log.info(" In Dao checkSupplierStausInUse");

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT count(ss.Supplier_Status),ss.Supplier_Status ");
		queryString.append(" FROM Supplier_Status_Master AS ss");
		queryString.append(" INNER JOIN ");
		queryString.append(" Asset_Supplier_Master AS s ");
		queryString.append(" ON ss.Supplier_Status_Id = s.Supplier_Status_Id ");
		queryString.append(" WHERE s.Deletedby is NULL ");
		queryString.append(" AND ss.Supplier_Status = ? ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, supplierStatus);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkAssetCategoryInUse(String assetCategory) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT count(ac.Asset_Cat_Name) ");
		queryString.append(" FROM Asset_Category_Master AS ac ");
		queryString.append(" INNER JOIN Asset_Registration AS ar ");
		queryString.append(" WHERE ac.Asset_Cat_Id = ar.Asset_Cat_Id ");
		queryString.append(" AND ar.Deleted_By is NULL  ");
		queryString.append(" AND ac.Asset_Cat_Name = ?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, assetCategory);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkSubCategoryInUse(String subCategory) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(sc.Sub_Cat_Name) ");
		queryString.append(" FROM Asset_Category_Master AS ac  ");
		queryString.append(" INNER JOIN Asset_Registration AS ar ");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc ");
		queryString.append(" WHERE ac.Asset_Cat_Id = ar.Asset_Cat_Id   ");
		queryString.append(" AND sc.Sub_Cat_Id = ar.Sub_Cat_Id ");
		queryString.append(" AND ar.Deleted_By is NULL  ");
		queryString.append(" AND sc.Sub_Cat_Name = ?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, subCategory);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkRequestTypeInUse(String requestType) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(rt.Request_Type_Name) ");
		queryString.append(" FROM Raise_Request AS rr , ");
		queryString.append(" Request_Type_Master AS rt");
		queryString.append(" WHERE rt.Request_Type_Id = rr.Request_Type_Id ");
		queryString.append(" AND rt.Request_Type_Name = ?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, requestType);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return statusFlag;
	}

	public Boolean checkMaintenanceTypeInUse(String maintenanceType) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT  COUNT(mt.Maintenance_Type_Name)  ");
		queryString.append(" FROM Asset_Maintenance AS am, ");
		queryString.append(" Maintenance_Type_Master AS mt ");
		queryString.append(" WHERE am.Maintenance_Type_Id = mt.Maintenance_Type_Id ");
		queryString.append(" AND mt.Maintenance_Type_Name = ?");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, maintenanceType);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();
		}
		return statusFlag;
	}

	public Boolean checkMaintenanceStatusInUse(String maintenanceStatus) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(ms.Maintenance_Status)  ");
		queryString.append(" FROM Asset_Maintenance AS am,");
		queryString.append(" Maintenance_Status_Master AS ms ");
		queryString.append(" WHERE ms.Maintenance_Status_Id = am.Maintenance_Status_Id ");
		queryString.append(" AND ms.Maintenance_Status = ? ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, maintenanceStatus);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkSupplierInUse(String supplier) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(sup.Supplier) ");
		queryString.append(" FROM  Asset_Supplier_Master AS sup ,");
		queryString.append(" Asset_Registration AS ar ");
		queryString.append(" WHERE sup.Supplier_Id = ar.Supplier_Id  ");
		queryString.append(" AND sup.Supplier = ?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, supplier);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkAssetInUse(String assetTag) throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(ar.Asset_Tag) ");
		queryString.append(" FROM Asset_Registration AS ar,");
		queryString.append(" Asset_Allocation AS al ");
		queryString.append(" WHERE ar.Asset_Id =  al.Asset_Id ");
		queryString.append(" AND ar.Asset_Tag = ? ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, assetTag);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}

	public Boolean checkISAssetAllocated(int assetCatID, int subCatID, int StaffID)
			throws SQLException, PropertyVetoException {

		boolean statusFlag = false;
		ResultSet rs = null;
		PreparedStatement statement = null;

		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT COUNT(*) FROM  Asset_Allocation AS aa,");
		queryString.append(" Asset_Registration AS ar  ");
		queryString.append(" WHERE ar.Asset_ID = aa.Asset_ID ");
		queryString.append(" AND ar.Asset_Cat_ID = ?  ");
		queryString.append(" AND ar.Sub_Cat_ID = ? ");
		queryString.append(" AND aa.Staff_ID = ? ");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setInt(1, assetCatID);
			statement.setInt(2, subCatID);
			statement.setInt(3, StaffID);

			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) < 1) {
					statusFlag = true;
					log.info("statusFlag = true ");
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		return statusFlag;
	}
}
