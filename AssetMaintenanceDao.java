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
import ai.rnt.ams.model.AssetAllocation;
import ai.rnt.ams.model.AssetCategoryMaster;
import ai.rnt.ams.model.AssetMaintenance;
import ai.rnt.ams.model.AssetSubCategoryMaster;
import ai.rnt.ams.model.EmployeeMaster;
import ai.rnt.ams.model.RaiseRequest;
import ai.rnt.ams.model.RequestTypeMaster;
import ai.rnt.main.dao.DBConnect;

public class AssetMaintenanceDao {
	
	private static final Logger log = (Logger) LogManager.getLogger(AssetMaintenanceDao.class);
	AdminDao adminDao = new AdminDao();
	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;

	public ArrayList<AssetMaintenance> getMaintenanceList() throws SQLException, PropertyVetoException {

		AssetMaintenance assetMaintenance = null;
		ArrayList<AssetMaintenance> maintenanceRequestList = new ArrayList<AssetMaintenance>();
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		queryString.append(" SELECT DISTINCT rr.Request_Id,");
		queryString.append(" rt.Request_Type_Name ,");
		queryString.append(" aa.Asset_Id,");
		queryString.append(" aa.Asset_Tag,");
		queryString.append(" emp.Staff_Id ,");
		queryString.append(" emp.F_Name ,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM Raise_Request AS rr ,");
		queryString.append(" Request_Type_Master AS rt ,");
		queryString.append(" Asset_Allocation AS aa,");
		queryString.append(" Asset_Registration AS ar ,");
		queryString.append(" employee_master AS emp");
		queryString.append(" WHERE rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND emp.Staff_Id  = rr.Staff_Id");
		queryString.append(" AND rt.Request_Type_Name = 'Maintenance'");
		queryString.append(" AND rr.Req_Status ='Pending'");

		try {
			rs = stmt.executeQuery(queryString.toString());
		
			while (rs.next()) {
				assetMaintenance = new AssetMaintenance();
				assetMaintenance.setRequestId(rs.getInt(1));
				assetMaintenance.setRequestType(rs.getString(2));
				assetMaintenance.setAssetId(rs.getInt(3));
				assetMaintenance.setAssetTag(rs.getString(4));
				assetMaintenance.setStaffId(rs.getInt(5));
				assetMaintenance.setFirstName(rs.getString(6));
				assetMaintenance.setReqStatus(rs.getString(7));
				maintenanceRequestList.add(assetMaintenance);
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
		return maintenanceRequestList;
}

	public ArrayList<AssetMaintenance> getMaintenanceList1() throws SQLException, PropertyVetoException {
		
		AssetMaintenance assetMaintenance = null;
		ArrayList<AssetMaintenance> maintenanceRequestList = new ArrayList<AssetMaintenance>();
		StringBuffer queryString = new StringBuffer();
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rr.Description,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" rr.Staff_Id,");
		queryString.append(" rr.F_Name,");
		queryString.append(" aa.Asset_Id,");
		queryString.append(" aa.Asset_Tag,");
		queryString.append(" ar.Warranty_Coverage,");
		queryString.append(" ar.Warranty,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM Raise_Request AS rr,");
		queryString.append(" Asset_Allocation AS aa,");
		queryString.append(" Asset_Registration AS ar,");
		queryString.append(" Request_Type_Master AS rt");
		queryString.append(" WHERE aa.Staff_Id = rr.Staff_Id");
		queryString.append(" AND aa.Asset_Id = ar.Asset_Id");
		queryString.append(" AND rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND rr.Req_Status ='Pending'");
		queryString.append(" AND rt.Request_Type_Name = 'Maintenance'");

		
		try {
			rs = stmt.executeQuery(queryString.toString());
			log.info("query=="+queryString.toString());
			while (rs.next()) {
				assetMaintenance = new AssetMaintenance();
				assetMaintenance.setRequestId(rs.getInt(1));
				assetMaintenance.setConfigDescription(rs.getString(2));
				assetMaintenance.setRequestType(rs.getString(3));
				assetMaintenance.setStaffId(rs.getInt(4));
				assetMaintenance.setFirstName(rs.getString(5));
				assetMaintenance.setAssetId(rs.getInt(6));
				assetMaintenance.setAssetTag(rs.getString(7));
				assetMaintenance.setCoverage(rs.getString(8));
				assetMaintenance.setWarranty(rs.getString(9));
				assetMaintenance.setReqStatus(rs.getString(10));
				maintenanceRequestList.add(assetMaintenance);
				
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		finally {
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
		return maintenanceRequestList;
	}

	public RaiseRequest displayMaintenanceReqDetails(int requestId, RaiseRequest maintenanceRequest)
			throws SQLException, PropertyVetoException {
		
		AssetMaintenance assetMaintenance = new AssetMaintenance();
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rr.Request_Type_Id,");
		queryString.append(" rr.Staff_Id,");
		queryString.append(" rr.F_Name,");
		queryString.append(" aa.Asset_Id,");
		queryString.append(" aa.Asset_Tag,");
		queryString.append(" rr.Description,");
		queryString.append(" ar.Warranty,");
		queryString.append(" ar.Warranty_Coverage,");
		queryString.append(" ar.Claim");
		queryString.append(" FROM Raise_Request AS rr,");
		queryString.append(" Asset_Allocation AS aa,");
		queryString.append(" Asset_Registration AS ar");
		queryString.append(" WHERE aa.Staff_Id = rr.Staff_Id");
		queryString.append(" AND ar.Asset_Tag = aa.Asset_Tag");
		queryString.append(" AND ar.Asset_Id = aa.Asset_Id");
		queryString.append(" AND rr.Request_Type_Id = 2");
		queryString.append(" AND rr.Request_Id = ");
		queryString.append(requestId);
		
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				maintenanceRequest.setRequestId(rs.getInt(1));
			}
			assetMaintenance.setMaintenanceReDetails(maintenanceRequest);
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
		return maintenanceRequest;
	}

	public ArrayList<AssetMaintenance> getApprovedMaintenanceList() throws SQLException, PropertyVetoException {
		
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		AssetMaintenance assetMaintenance = null;
		ArrayList<AssetMaintenance> approvedMaintenanceList = new ArrayList<AssetMaintenance>();
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT am.Staff_Id,");
		queryString.append(" am.Request_Id,");
		queryString.append(" am.Asset_Id,");
		queryString.append(" am.Asset_Tag,");
		queryString.append(" mt.Maintenance_Type_Name,");
		queryString.append(" ms.Maintenance_Status, ");
		queryString.append(" am.Warranty,");
		queryString.append(" am.Warranty_Coverage,");
		queryString.append(" am.Claim,");
		queryString.append(" am.Problem_Description,");
		queryString.append(" am.Req_Status");
		queryString.append(" FROM Asset_Maintenance AS am,");
		queryString.append(" Maintenance_Type_Master AS mt,");
		queryString.append(" Maintenance_Status_Master AS ms");
		queryString.append(" WHERE am.Maintenance_Type_Id = mt.Maintenance_Type_Id ");
		queryString.append(" AND ms.Maintenance_Status_Id = am.Maintenance_Status_Id ");
		queryString.append(" AND Req_Status ='Approve'");
		try {
			rs = stmt.executeQuery(queryString.toString());
			
			while (rs.next()) {
				assetMaintenance = new AssetMaintenance();
				assetMaintenance.setStaffId(rs.getInt(1));
				assetMaintenance.setRequestId(rs.getInt(2));
				assetMaintenance.setAssetId(rs.getInt(3));
				assetMaintenance.setAssetTag(rs.getString(4));
				assetMaintenance.setMaintenanceType(rs.getString(5));
				assetMaintenance.setMaintenanceStatus(rs.getString(6));
				assetMaintenance.setWarranty(rs.getString(7));
				assetMaintenance.setCoverage(rs.getString(8));
				assetMaintenance.setClaim(rs.getInt(9));
				assetMaintenance.setProblemDesc(rs.getString(10));
				assetMaintenance.setReqStatus(rs.getString(11));
				approvedMaintenanceList.add(assetMaintenance);
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
		return approvedMaintenanceList;
	}

	public boolean changeStatusAsUnderMaintenance(int assetId) throws SQLException, PropertyVetoException {
		
		Connection connection = DBConnect.getConnection();
		boolean changeStatusAsUnderMaintenance = false;
		PreparedStatement stmt = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Asset_Registration ");
			queryString.append(" SET Asset_Available_Status = 'Under Maintenance' ");
			queryString.append(" WHERE Asset_Id = ? ");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, assetId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				changeStatusAsUnderMaintenance = true;
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
		return changeStatusAsUnderMaintenance;
	}

	public boolean changePendingToApprovedMaintenance(RaiseRequest raiseRequest, int requestId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		boolean updatePendingToFinalStatus = false;
		PreparedStatement stmt = null;
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Raise_Request ");
			queryString.append(" SET Req_Status = 'Approved'");
			queryString.append(" WHERE Request_Id = ? ");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, raiseRequest.getRequestId());
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				updatePendingToFinalStatus = true;
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
		return updatePendingToFinalStatus;
}

	public boolean changePendingToRejectedMaintenance(int requestId)
			throws SQLException, PropertyVetoException {
		
		Connection connection = DBConnect.getConnection();
		boolean updatePendingToRejected = false;
		PreparedStatement stmt = null;
		
		try {
			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Raise_Request");
			queryString.append(" SET Req_Status = 'Rejected'");
			queryString.append(" WHERE Request_Id = ? ");
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

	public boolean finalMaintenanceStatus(AssetMaintenance assetMaintenance, int adminId)
			throws SQLException, PropertyVetoException {
		
		Connection connection = DBConnect.getConnection();
		boolean finalMaintenanceStatus = false;
		PreparedStatement stmt = null;
		try {

			StringBuffer queryString = new StringBuffer();
			queryString.append(" UPDATE Asset_Maintenance ");
			queryString.append(" SET Maintenance_Date = ?,");
			queryString.append(" Required_Time = ?,");
			queryString.append(" Maintenance_Status_Id = ?");
			queryString.append(" WHERE Request_Id = ?");
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setDate(1, assetMaintenance.getMaintenanceDate());
			stmt.setString(2, assetMaintenance.getRequiredTime());
			stmt.setInt(3, assetMaintenance.getMaintenanceStatusId());
			stmt.setInt(4, assetMaintenance.getRequestId());
			
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				finalMaintenanceStatus = true;
				log.info("Maintenance Status changed successfully ...");
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
		return finalMaintenanceStatus;

	}

	public boolean approveRejectMaintenanceReq(AssetMaintenance assetMaintenance, int adminId)
			throws SQLException, PropertyVetoException {
		
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean approveRjMaintenaceReq = false;
		try {
			
			StringBuffer queryString = new StringBuffer();
			
			queryString.append(" INSERT into Asset_Maintenance(");
			queryString.append(" Request_Id,");
			queryString.append(" Staff_Id,");
			queryString.append(" Asset_Id,");
			queryString.append(" Asset_Tag,");
			queryString.append(" Warranty,");
			queryString.append(" Warranty_Coverage,");
			queryString.append(" Claim,");
			queryString.append(" Problem_Description,");
			queryString.append(" Maintenance_Type_Id,");
			queryString.append(" Comment,");
			queryString.append(" Req_Status,");
			queryString.append(" Maintenance_Status_Id,");
			queryString.append(" Approved_Rejected_By)");
			queryString.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,");
			queryString.append(adminId + ")");
			
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, assetMaintenance.getRequestId());
			stmt.setInt(2, assetMaintenance.getStaffId());
			stmt.setInt(3, assetMaintenance.getAssetId());
			stmt.setString(4, assetMaintenance.getAssetTag());
			stmt.setString(5, assetMaintenance.getWarranty());
			stmt.setString(6, assetMaintenance.getCoverage());
			stmt.setInt(7, assetMaintenance.getClaim());
			stmt.setString(8, assetMaintenance.getProblemDesc());
			stmt.setInt(9, assetMaintenance.getMaintenanceTypeId());
			stmt.setString(10, assetMaintenance.getComment());
			stmt.setString(11, assetMaintenance.getReqStatus());
			stmt.setInt(12, assetMaintenance.getMaintenanceStatusId());
			
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				approveRjMaintenaceReq = true;
				log.info("Status changed successfully!");
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
		return approveRjMaintenaceReq;
}

	
	public ArrayList<AssetMaintenance> getMaintenanceRequestListForUser(int staffId)
			throws SQLException, PropertyVetoException {
		AssetMaintenance assetMaintenance = null;

		ArrayList<AssetMaintenance> maintenanceRequestListForUser = new ArrayList<AssetMaintenance>();
		StringBuffer queryString = new StringBuffer();

		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" rr.Staff_Id,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" aa.Asset_Tag ,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM Raise_Request AS rr,");
		queryString.append(" Asset_Allocation AS aa,");
		queryString.append(" Asset_Registration AS ar,");
		queryString.append(" Request_Type_Master AS rt");
		queryString.append(" WHERE aa.Staff_Id = rr.Staff_Id");
		queryString.append(" AND aa.Asset_Id = ar.Asset_Id");
		queryString.append(" AND rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND rt.Request_Type_Name = 'Maintenance'");
		queryString.append(" AND rr.Staff_Id = ");
		queryString.append(staffId);
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				assetMaintenance = new AssetMaintenance();
				assetMaintenance.setRequestId(rs.getInt(1));
				assetMaintenance.setStaffId(rs.getInt(2));
				assetMaintenance.setRequestType(rs.getString(3));
				assetMaintenance.setAssetTag(rs.getString(4));
				assetMaintenance.setReqStatus(rs.getString(5));
				maintenanceRequestListForUser.add(assetMaintenance);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		finally {
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
		return maintenanceRequestListForUser;
	}

}
