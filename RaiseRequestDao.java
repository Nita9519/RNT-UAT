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

import ai.rnt.ams.model.EmployeeMaster;
import ai.rnt.ams.model.RaiseRequest;
import ai.rnt.main.dao.DBConnect;

public class RaiseRequestDao {
	private static final Logger log = (Logger) LogManager.getLogger(RaiseRequestDao.class);
	AdminDao adminDao = new AdminDao();
	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;

	public ArrayList<RaiseRequest> getRequestList() throws SQLException, PropertyVetoException {
		
		RaiseRequest raiseRequset = null;
		ArrayList<RaiseRequest> requestList = new ArrayList<RaiseRequest>();
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" emp.Staff_Id,");
		queryString.append(" emp.F_Name,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name,");
		queryString.append(" rr.Description,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM employee_master AS emp");
		queryString.append(" INNER JOIN Raise_Request AS rr");
		queryString.append(" INNER JOIN Request_Type_Master AS rt");
		queryString.append(" INNER JOIN Asset_Category_Master AS ac");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc ");
		queryString.append(" WHERE emp.Staff_Id = rr.Staff_Id ");
		queryString.append(" AND rt.Request_Type_Id = rr.Request_Type_Id ");
		queryString.append(" AND ac.Asset_Cat_Id = rr.Asset_Cat_Id  ");
		queryString.append(" AND sc.Sub_Cat_Id = rr.Sub_Cat_Id ");
		queryString.append(" AND rr.Req_Status = 'Pending' ");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		
		try {
			rs = stmt.executeQuery(queryString.toString());
			
			while (rs.next()) {
				raiseRequset = new RaiseRequest();
				raiseRequset.setRequestId(rs.getInt(1));
				raiseRequset.setStaffId(rs.getInt(2));
				raiseRequset.setFirstName(rs.getString(3));
				raiseRequset.setRequestType(rs.getString(4));
				raiseRequset.setAssetCatName(rs.getString(5));
				raiseRequset.setSubCatName(rs.getString(6));
				raiseRequset.setConfigDescription(rs.getString(7));
				raiseRequset.setReqStatus(rs.getString(8));
				requestList.add(raiseRequset);
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
		return requestList;

	}

	public ArrayList<RaiseRequest> raisedRequestListforUser(int staffId) throws SQLException, PropertyVetoException {
		
		RaiseRequest raiseRequset = null;
		ArrayList<RaiseRequest> requestListforUser = new ArrayList<RaiseRequest>();
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" emp.Staff_Id,");
		queryString.append(" emp.F_Name,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name,");
		queryString.append(" rr.Description,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM employee_master AS emp ");
		queryString.append(" INNER JOIN Raise_Request AS rr ");
		queryString.append(" INNER JOIN Request_Type_Master AS rt  ");
		queryString.append(" INNER JOIN Asset_Category_Master AS ac ");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc ");
		queryString.append(" WHERE emp.Staff_Id = rr.Staff_Id ");
		queryString.append(" AND rt.Request_Type_Id = rr.Request_Type_Id ");
		queryString.append(" AND ac.Asset_Cat_Id = rr.Asset_Cat_Id  ");
		queryString.append(" AND sc.Sub_Cat_Id = rr.Sub_Cat_Id ");
		queryString.append(" AND emp.Staff_Id =" + staffId);
		// queryString.append(" AND rr.Req_Status = 'Pending' ");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		
		try {
			rs = stmt.executeQuery(queryString.toString());
			
			while (rs.next()) {
				raiseRequset = new RaiseRequest();
				raiseRequset.setRequestId(rs.getInt(1));
				raiseRequset.setStaffId(rs.getInt(2));
				raiseRequset.setFirstName(rs.getString(3));
				raiseRequset.setRequestType(rs.getString(4));
				raiseRequset.setAssetCatName(rs.getString(5));
				raiseRequset.setSubCatName(rs.getString(6));
				raiseRequset.setConfigDescription(rs.getString(7));
				raiseRequset.setReqStatus(rs.getString(8));
				requestListforUser.add(raiseRequset);
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
		return requestListforUser;

	}

	public ArrayList<RaiseRequest> getRequestListForUser(int staffID) throws SQLException, PropertyVetoException {
		
		RaiseRequest raiseRequset = null;
		int count = 0;
		ArrayList<RaiseRequest> requestList = new ArrayList<RaiseRequest>();
		StringBuffer queryString = new StringBuffer();
		
		queryString.append(" SELECT rr.Request_Id,");
		queryString.append(" emp.Staff_Id,");
		queryString.append(" emp.F_Name,");
		queryString.append(" rt.Request_Type_Name,");
		queryString.append(" ac.Asset_Cat_Name,");
		queryString.append(" sc.Sub_Cat_Name,");
		queryString.append(" rr.Description,");
		queryString.append(" rr.Req_Status");
		queryString.append(" FROM employee_master AS emp");
		queryString.append(" INNER JOIN Raise_Request AS rr");
		queryString.append(" INNER JOIN Request_Type_Master AS rt");
		queryString.append(" INNER JOIN Asset_Category_Master AS ac");
		queryString.append(" INNER JOIN Asset_Sub_Category_Master AS sc");
		queryString.append(" WHERE emp.Staff_Id = rr.Staff_Id");
		queryString.append(" AND rt.Request_Type_Id = rr.Request_Type_Id");
		queryString.append(" AND ac.Asset_Cat_Id = rr.Asset_Cat_Id");
		queryString.append(" AND sc.Sub_Cat_Id = rr.Sub_Cat_Id ");
		queryString.append(" AND rr.Req_Status = 'Pending'");
		queryString.append(" AND rr.Staff_Id = " + staffID);
		
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
	
		try {
			rs = stmt.executeQuery(queryString.toString());
			
			while (rs.next()) {
				raiseRequset = new RaiseRequest();
				raiseRequset.setRequestId(rs.getInt(1));
				raiseRequset.setStaffId(rs.getInt(2));
				raiseRequset.setFirstName(rs.getString(3));
				raiseRequset.setRequestType(rs.getString(4));
				raiseRequset.setAssetCatName(rs.getString(5));
				raiseRequset.setSubCatName(rs.getString(6));
				raiseRequset.setConfigDescription(rs.getString(7));
				raiseRequset.setReqStatus(rs.getString(8));
				requestList.add(raiseRequset);
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
		return requestList;
	}

	public boolean addassetRequest(int staffId, int assetCatId, int subCatId, RaiseRequest assetRequest)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addassetRequest = false;
		staffId = assetRequest.getStaffId();
		try {

			staffId = assetRequest.getStaffId();
			assetCatId = assetRequest.getAssetCatId();
			subCatId = assetRequest.getSubCatId();
			StringBuffer queryString = new StringBuffer();
			
			queryString.append(" INSERT into Raise_Request(");
			queryString.append(" Staff_Id,");
			queryString.append(" F_Name,");
			queryString.append(" Request_Type_Id,");
			queryString.append(" Asset_Cat_Id,");
			queryString.append(" Sub_Cat_Id,");
			queryString.append(" Description,");
			queryString.append(" Request_Raised_Date,");
			queryString.append(" Started_Date,");
			queryString.append(" Completion_Date,");
			queryString.append(" Req_Status)");
			queryString.append(" VALUES(?,?,?,?,?,?,");
			queryString.append(" CURRENT_TIMESTAMP(),");
			queryString.append(" CURRENT_TIMESTAMP(),");
			queryString.append(" CURRENT_TIMESTAMP()");
			queryString.append(",'Pending')");
			
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, assetRequest.getStaffId());
			stmt.setString(2, assetRequest.getFirstName());
			stmt.setInt(3, assetRequest.getRequestTypeId());
			stmt.setInt(4, assetRequest.getAssetCatId());
			stmt.setInt(5, assetRequest.getSubCatId());
			stmt.setString(6, assetRequest.getConfigDescription());
			
			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				log.info("A new asset request was  inserted successfully!");
				addassetRequest = true;
			}
		} catch (Exception e) {
			log.error("got exception while adding asset request...");
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
		return addassetRequest;

	}

	public int getRaiseReqCount() throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		ResultSet rs = null;
		Statement stmt = connection.createStatement();
		StringBuffer queryString = new StringBuffer();
		int newReqCount = 0;
		queryString.append(" SELECT count(*)");
		queryString.append(" FROM Raise_request");
		queryString.append(" WHERE Req_Status = 'Pending' ");
		try {
			rs = stmt.executeQuery(queryString.toString());
			while (rs.next()) {
				newReqCount = rs.getInt(1);
			}
		}catch (Exception e) {
			log.error("got exception while raise request...");
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
		return newReqCount;

	}
}
