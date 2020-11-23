package ai.rnt.ams.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import ai.rnt.ams.model.AssetAllocation;
import ai.rnt.ams.model.AssetRegistration;
import ai.rnt.ams.model.RaiseRequest;
import ai.rnt.main.dao.DBConnect;

public class AssetAllocationDao {
	private static final Logger log = (Logger) LogManager.getLogger(AssetAllocationDao.class);
	AdminDao adminDao = new AdminDao();
	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;

	public ArrayList<AssetAllocation> getAssetAllocationRequestList(int staffid)
			throws SQLException, PropertyVetoException {

		AssetAllocation assetAllocation = null;
		ArrayList<AssetAllocation> allocationRequestList = new ArrayList<AssetAllocation>();
		StringBuffer queryString = new StringBuffer();

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" emp.Staff_Id,");
		queryString.append(" emp.F_Name,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM Raise_Request AS rr,");
		queryString.append(" Request_Type_Master AS rt,");
		queryString.append(" employee_master AS emp ");
		queryString.append(" WHERE rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND emp.Staff_Id  = rr.Staff_Id");
		queryString.append(" AND rt.Request_Type_Name = 'New Request'");
		queryString.append(" AND rr.Req_Status =' Approved'");
		queryString.append(" AND emp.Staff_Id =");
		queryString.append(staffid);
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				assetAllocation = new AssetAllocation();
				assetAllocation.setRequestId(rs.getInt(1));
				assetAllocation.setRequestType(rs.getString(2));
				assetAllocation.setStaffId(rs.getInt(3));
				assetAllocation.setFirstName(rs.getString(4));
				assetAllocation.setReqStatus(rs.getString(5));
				allocationRequestList.add(assetAllocation);

			}
		} catch (Exception e) {
			log.error("got exception", e);
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
		return allocationRequestList;
	}

	public ArrayList<AssetAllocation> getAssetAllocationRequestListForApproval()
			throws SQLException, PropertyVetoException {

			AssetAllocation assetAllocation = null;

			ArrayList<AssetAllocation> allocationRequestList = new ArrayList<AssetAllocation>();
			StringBuffer queryString = new StringBuffer();
			Connection connection = DBConnect.getConnection();
			Statement stmt = connection.createStatement();

			queryString.append("  SELECT rr.Request_Id,  ");
			queryString.append("  rt.Request_Type_Name , ");
			queryString.append("  emp.Staff_Id , ");
			queryString.append("  emp.F_Name , ");
			queryString.append("  rr.Req_Status,sc.Sub_Cat_Name ");
			queryString.append("  FROM Raise_Request AS rr , ");
			queryString.append("  Request_Type_Master AS rt ,  ");
			queryString.append("  Asset_Category_Master AS ac, ");
			queryString.append("  Asset_Sub_Category_Master AS sc, ");
			queryString.append("  employee_master AS emp ");
			queryString.append("  WHERE rt.Request_Type_Id = rr.Request_Type_Id ");
			queryString.append("  AND emp.Staff_Id  = rr.Staff_Id  ");
			queryString.append("  AND ac.Asset_Cat_Id = sc.Asset_Cat_Id  ");
			queryString.append("  AND ac.Asset_Cat_Id = rr.Asset_Cat_Id  ");
			queryString.append("  AND sc.Sub_Cat_Id = rr.Sub_Cat_Id  ");
			queryString.append("  AND rt.Request_Type_Name = 'New Request' ");
			queryString.append("  AND rr.Req_Status ='Pending' ");


			try {
			rs = stmt.executeQuery(queryString.toString());

			while (rs.next()) {
			assetAllocation = new AssetAllocation();
			assetAllocation.setRequestId(rs.getInt(1));
			assetAllocation.setRequestType(rs.getString(2));
			assetAllocation.setStaffId(rs.getInt(3));
			assetAllocation.setFirstName(rs.getString(4));
			assetAllocation.setReqStatus(rs.getString(5));
			assetAllocation.setSubCatName(rs.getString(6));
			allocationRequestList.add(assetAllocation);
			}
			} catch (Exception e) {
			log.error("got exception", e);
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
			return allocationRequestList;
			}

	public ArrayList<AssetAllocation> getAssetAllocationRequestListForUser(int staffID)
			throws SQLException, PropertyVetoException {

		AssetAllocation assetAllocation = null;
		ArrayList<AssetAllocation> allocationRequestList = new ArrayList<AssetAllocation>();
		StringBuffer queryString = new StringBuffer();

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" emp.Staff_Id,");
		queryString.append(" emp.F_Name,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM Raise_Request AS rr ,");
		queryString.append(" Request_Type_Master AS rt ,");
		queryString.append(" employee_master AS emp");
		queryString.append(" WHERE rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND emp.Staff_Id  = rr.Staff_Id");
		queryString.append(" AND rt.Request_Type_Name = 'New Request'");
		queryString.append(" AND emp.Staff_Id= ");
		queryString.append(staffID);
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				assetAllocation = new AssetAllocation();
				assetAllocation.setRequestId(rs.getInt(1));
				assetAllocation.setRequestType(rs.getString(2));
				assetAllocation.setStaffId(rs.getInt(3));
				assetAllocation.setFirstName(rs.getString(4));
				assetAllocation.setReqStatus(rs.getString(5));
				allocationRequestList.add(assetAllocation);
			}
		} catch (Exception e) {
			log.error("got exception", e);
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
		return allocationRequestList;
	}

	public boolean updatePendingToApproved(int requestId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePendingToApproved = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Raise_Request");
			queryString.append(" SET Req_Status = 'Approved'");
			queryString.append(" WHERE Request_Id = ?");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, requestId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updatePendingToApproved = true;
				log.info("pending status changed successfully ...");
			}

		} catch (Exception e) {
			log.error("Got exception while updating status pending to allocated ...");
			e.printStackTrace();
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
		return updatePendingToApproved;
	}

	public boolean updatePendingToRejected(int requestId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updatePendingToRejected = false;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Raise_Request");
			queryString.append(" SET Req_Status = 'Rejected'");
			queryString.append(" WHERE Request_Id = ?");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, requestId);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updatePendingToRejected = true;
				log.info("pending status changed successfully ...");
			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return updatePendingToRejected;
	}

	public boolean availabletoAllocated(AssetRegistration assetRegistration, int assetId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		boolean availabletoAllocated = false;
		PreparedStatement stmt = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Asset_Registration ");
			queryString.append(" SET Asset_Available_Status = 'Allocated'");
			queryString.append(" WHERE Asset_Id = ? ");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, assetRegistration.getAssetId());

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				availabletoAllocated = true;
				log.info("pending status changed successfully ...");
			}
		} catch (Exception e) {
			log.error("Got exception while updating status available to allocated ...");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}
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
		return availabletoAllocated;
	}

	public boolean allocateAsset(RaiseRequest raiseRequest, int requestId, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		boolean updateAssetAllocation = false;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" UPDATE Asset_Allocation");
		queryString.append(" SET Asset_Id = ?,");
		queryString.append(" Asset_Tag = ?,");
		queryString.append(" Configuration = ?,");
		queryString.append(" Allocation_Date = ?,");
		queryString.append(" Est_Release_Date = ?,");
		queryString.append(" Actual_Release_Date = ?,");
		queryString.append(" Req_Status = ?");
		queryString.append(" WHERE Request_Id = ?");

		PreparedStatement stmt = connection.prepareStatement(queryString.toString());
		try {
			stmt.setInt(1, raiseRequest.getAssetId());
			stmt.setString(2, raiseRequest.getAssetTag());
			stmt.setString(3, raiseRequest.getConfiguration());
			stmt.setDate(4, raiseRequest.getAllocationDate());
			stmt.setDate(5, raiseRequest.getEstReleasedDate());
			stmt.setDate(6, raiseRequest.getActualReleasedDate());
			stmt.setString(7, "Allocated");
			stmt.setInt(8, requestId);

			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updateAssetAllocation = true;
				log.info("Asset is allocated successfully ...");
			}

		} catch (Exception e) {
			log.error("Got exception while allocating asset ...");
			e.printStackTrace();
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
		return updateAssetAllocation;
	}

	public boolean approveAssetRequests(AssetAllocation assetallocation, int adminId)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean approveAssetRequests = false;

		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" INSERT into Asset_Allocation(");
			queryString.append(" Request_Id,");
			queryString.append(" Staff_Id,");
			queryString.append(" F_Name,");
			queryString.append(" Req_Status,");
			queryString.append(" Comment,");
			queryString.append(" Approved_By )");
			queryString.append(" VALUES(?,?,?,?,?,");
			queryString.append(adminId);
			queryString.append(")");

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, assetallocation.getRequestId());
			stmt.setInt(2, assetallocation.getStaffId());
			stmt.setString(3, assetallocation.getFirstName());
			stmt.setString(4, assetallocation.getReqStatus());
			stmt.setString(5, assetallocation.getComment());

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				approveAssetRequests = true;
				log.info("Status changed successfully!");
			}
		} catch (Exception e) {
			log.info("Got exception while approving asset request...", e);
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
		return approveAssetRequests;

	}

	public ArrayList<AssetAllocation> availableAssetsforApproveStatus(int staffId)
			throws SQLException, PropertyVetoException {
		AssetAllocation assetAllocation = null;

		ArrayList<AssetAllocation> availAssetListWithRequest = new ArrayList<AssetAllocation>();
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT ar.Asset_Id,");
		queryString.append(" ar.Asset_Tag,");
		queryString.append(" CONCAT(ar.Processor, ar.Ram, ar.Hard_Disk) AS Configuration,");
		queryString.append(" ar.Asset_Available_Status,");
		queryString.append(" rr.Request_Id,");
		queryString.append(" rr.Staff_Id,");
		queryString.append(" rr.F_Name");
		queryString.append(" FROM Asset_Registration AS ar,");
		queryString.append(" Raise_Request As rr");
		queryString.append(" WHERE rr.Asset_Cat_Id = ar.Asset_Cat_Id ");
		queryString.append(" AND rr.Sub_Cat_Id = ar.Sub_Cat_Id ");
		queryString.append(" AND ar.Asset_Available_Status = 'Available'");
		queryString.append(" AND rr.Req_Status = 'Pending'");
		queryString.append(" AND rr.Staff_Id = ");
		queryString.append(staffId);

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			rs = stmt.executeQuery(queryString.toString());

			while (rs.next()) {
				assetAllocation = new AssetAllocation();
				assetAllocation.setAssetId(rs.getInt(1));
				assetAllocation.setAssetTag(rs.getString(2));
				assetAllocation.setConfiguration(rs.getString(3));
				assetAllocation.setAssetAvailableStatus(rs.getString(4));
				assetAllocation.setRequestId(rs.getInt(5));
				assetAllocation.setStaffId(rs.getInt(6));
				assetAllocation.setFirstName(rs.getString(7));
				availAssetListWithRequest.add(assetAllocation);
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
		return availAssetListWithRequest;
	}

	public ArrayList<AssetRegistration> availableAssets() throws SQLException, PropertyVetoException {

		AssetRegistration assetReg = null;
		ArrayList<AssetRegistration> availAssetList = new ArrayList<AssetRegistration>();
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT Asset_Id,");
		queryString.append(" Asset_Cat_Id,");
		queryString.append(" Sub_Cat_Id");
		queryString.append(" FROM Asset_Registration");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			rs = stmt.executeQuery(queryString.toString());

			while (rs.next()) {
				assetReg = new AssetRegistration();
				assetReg.setAssetId(rs.getInt(1));
				assetReg.setAssetCatId(rs.getInt(2));
				assetReg.setSubCatId(rs.getInt(3));
				availAssetList.add(assetReg);
			}
		} catch (Exception e) {
			log.error("Got exception , while displaying available asset list with respect to request");
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
		return availAssetList;
	}

	public ArrayList<AssetAllocation> availableAssetsForRequest(int requestId)
			throws SQLException, PropertyVetoException {

		AssetAllocation assetAllocation = null;
		ArrayList<AssetAllocation> availAssetListWithRequest = new ArrayList<AssetAllocation>();
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT ar.Asset_Id,");
		queryString.append(" ar.Asset_Tag,");
		queryString.append(" CONCAT(ar.Processor, ar.Ram, ar.Hard_Disk) AS Configuration,");
		queryString.append(" ar.Asset_Available_Status,");
		queryString.append(" rr.Request_Id,");
		queryString.append(" rr.Staff_Id,");
		queryString.append(" rr.F_Name,");
		queryString.append(" ar.Asset_Cat_Id ,");
		queryString.append(" ar.Sub_Cat_Id ");
		queryString.append(" FROM Asset_Registration AS ar,");
		queryString.append(" Raise_Request As rr");
		queryString.append(" WHERE ar.Asset_Cat_Id = rr.Asset_Cat_Id");
		queryString.append(" AND ar.Sub_Cat_Id = rr.Sub_Cat_Id");
		queryString.append(" AND ar.Asset_Available_Status = 'Available'");
		queryString.append(" AND rr.Request_Id =");
		queryString.append(requestId);
		log.info(queryString.toString());
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				assetAllocation = new AssetAllocation();
				assetAllocation.setAssetId(rs.getInt(1));
				assetAllocation.setAssetTag(rs.getString(2));
				assetAllocation.setConfiguration(rs.getString(3));
				assetAllocation.setAssetAvailableStatus(rs.getString(4));
				assetAllocation.setRequestId(rs.getInt(5));
				assetAllocation.setStaffId(rs.getInt(6));
				assetAllocation.setFirstName(rs.getString(7));
				assetAllocation.setAssetCatId(rs.getInt(8));
				assetAllocation.setSubCatId(rs.getInt(9));
				availAssetListWithRequest.add(assetAllocation);

			}
		} catch (Exception e) {
			log.error("Got exception , while displaying available asset list with respect to request");
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
		return availAssetListWithRequest;
	}

	public ArrayList<AssetAllocation> allocatedAssets() throws SQLException, PropertyVetoException {

		ArrayList<AssetAllocation> allocatedAssets = new ArrayList<AssetAllocation>();
		AssetAllocation assetAllocation = null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT aa.Request_Id,                   ");
		queryString.append(" ar.Asset_Id,                            ");
		queryString.append(" ar.Asset_Tag,                           ");
		queryString.append(" ac.Asset_Cat_Name,                      ");
		queryString.append(" sc.Sub_Cat_Name,                        ");
		queryString.append(" aa.Configuration,                       ");
		queryString.append(" aa.Allocation_Date,                     ");
		queryString.append(" aa.Est_Release_Date,                    ");
		queryString.append(" aa.Actual_Release_Date,                 ");
		queryString.append(" aa.Req_Status,                          ");
		queryString.append(" aa.Staff_Id ,                           ");
		 queryString.append(" sup.Supplier,                          ");
		queryString.append(" em.F_Name,                              ");
		 queryString.append(" em.L_Name                               "); 
		queryString.append(" FROM Asset_Category_Master AS ac,       ");
		queryString.append(" Asset_Sub_Category_Master AS sc,        ");
		queryString.append(" Asset_Registration AS ar,               ");
		queryString.append(" Asset_Allocation AS aa ,                ");
		queryString.append(" Raise_Request AS rr,                  ");
		queryString.append(" Asset_Supplier_Master AS sup,           "); 
		queryString.append(" employee_master AS em                   ");
		queryString.append(" WHERE ac.Asset_Cat_Id = rr.Asset_Cat_Id ");
		queryString.append(" AND ar.Asset_Id = aa.Asset_Id           ");
		queryString.append(" AND sc.Sub_Cat_Id = rr.Sub_Cat_Id       ");
		queryString.append(" AND aa.Request_Id = rr.Request_Id       ");
		queryString.append(" AND aa.Staff_Id = rr.Staff_Id           ");
		 queryString.append(" AND sup.Supplier_Id = ar.Supplier_Id    "); 
		queryString.append(" AND aa.Req_Status = 'Allocated'         ");
		/* queryString.append(" AND ar.Deleted_By is null 				"); */
		queryString.append(" AND aa.Staff_Id = em.Staff_Id  ");

		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
log.info("query==="+queryString.toString());
		try {
		rs = stmt.executeQuery(queryString.toString());

		while (rs.next()) {
		assetAllocation = new AssetAllocation();
		assetAllocation.setRequestId(rs.getInt(1));
		assetAllocation.setAssetId(rs.getInt(2));
		assetAllocation.setAssetTag(rs.getString(3));
		assetAllocation.setAssetCatName(rs.getString(4));
		assetAllocation.setSubCatName(rs.getString(5));
		assetAllocation.setConfiguration(rs.getString(6));
		assetAllocation.setAllocationDate(rs.getDate(7));
		assetAllocation.setEstReleasedDate(rs.getDate(8));
		assetAllocation.setActualReleasedDate(rs.getDate(9));
		assetAllocation.setReqStatus(rs.getString(10));
		assetAllocation.setStaffId(rs.getInt(11));
		 assetAllocation.setSuplier(rs.getString(12)); 
		assetAllocation.setfName(rs.getString(13));
		assetAllocation.setlName(rs.getString(14));
		log.info(rs.getString(12));
		log.info(rs.getString(13));
		allocatedAssets.add(assetAllocation);
		}
		} catch (Exception e) {
		e.printStackTrace();
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
		return allocatedAssets;
		}


	public RaiseRequest displayEmpRequestDetails(int requestId, RaiseRequest raiseRequest)
			throws SQLException, PropertyVetoException {

		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rr.Asset_Cat_Id,");
		queryString.append(" rr.Sub_Cat_Id");
		queryString.append(" FROM Raise_Request AS rr ,");
		queryString.append(" Request_Type_Master AS rt ,");
		queryString.append(" employee_master AS emp");
		queryString.append(" WHERE rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND emp.Staff_Id  = rr.Staff_Id");
		queryString.append(" AND rr.Request_Id =");
		queryString.append(raiseRequest.getRequestId());
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			rs = stmt.executeQuery(queryString.toString());

			while (rs.next()) {
				raiseRequest.setRequestId(rs.getInt(1));
				raiseRequest.setAssetCatId(rs.getInt(2));
				raiseRequest.setSubCatId(rs.getInt(3));
			}
		}

		catch (Exception e) {
			log.error("got exception", e);
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
		return raiseRequest;

	}
}
