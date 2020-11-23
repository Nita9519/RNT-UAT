package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.LocationMaster;
import ai.rnt.pins.model.RateCardMaster;
import ai.rnt.pins.model.RoleMaster;
import ai.rnt.pins.model.SoftwareMaster;

public class RateCardMasterDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(RateCardMasterDao.class);

	public ArrayList<RateCardMaster> getListOfRateCards() throws SQLException, PropertyVetoException {
		RateCardMaster rateCard = new RateCardMaster();

		ArrayList<RateCardMaster> rateCardList = new ArrayList<RateCardMaster>();

		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(" SELECT r.role_id, r.role, l.location_id, l.location,rt.on_site_rate_per_hour, ");
		queryString.append(" rt.on_site_rate_per_day, rt.off_shore_rate_per_hour, rt.off_shore_rate_per_day");
		queryString.append(" FROM rate_card_master AS rt");
		queryString.append(" INNER JOIN role_master AS r ON rt.role_id = r.role_id");
		queryString.append(" LEFT JOIN location_master AS l ON rt.location_id = l.location_id ");
		queryString.append(" WHERE rt.deleted_by IS NULL"); 
		queryString.append(" AND r.deleted_by IS NULL"); 
		queryString.append(" AND l.deleted_by IS NULL");
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();

		try {
			rs = stmt.executeQuery(queryString.toString());

			while (rs.next()) {

				rateCard = new RateCardMaster();
				rateCard.setRoleId(rs.getInt(1));
				rateCard.setRoleName(rs.getString(2));
				rateCard.setLocationId(rs.getInt(3));
				rateCard.setLocationName(rs.getString(4));
				rateCard.setOnSiteRatePerHour(rs.getFloat(5));
				rateCard.setOnSiteRatePerDay(rs.getFloat(6));
				rateCard.setOffShoreRatePerHour(rs.getFloat(7));
				rateCard.setOffShoreRatePerDay(rs.getFloat(8));
				rateCardList.add(rateCard);

			}

		} catch (Exception e) {
			log.error("Got Exception while List Of rate Card Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return rateCardList;
	}

	public boolean addRateCard(RateCardMaster rateCard, int adminId) throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean addRateCard = false;
	

			StringBuffer queryString = new StringBuffer();
			queryString.append("INSERT into rate_card_master (role_id, location_id, on_site_rate_per_hour, ");
			queryString.append(
					"on_site_rate_per_day, off_shore_rate_per_hour, off_shore_rate_per_day, created_by, created_date)");
			queryString.append("VALUES (?,?,?,?,?,?,");
			queryString.append(adminId + ",");
			queryString.append("CURRENT_TIMESTAMP())");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, rateCard.getRoleId());
			stmt.setInt(2, rateCard.getLocationId());
			stmt.setFloat(3, rateCard.getOnSiteRatePerHour());
			stmt.setFloat(4, rateCard.getOnSiteRatePerDay());
			stmt.setFloat(5, rateCard.getOffShoreRatePerHour());
			stmt.setFloat(6, rateCard.getOffShoreRatePerDay());
			rateCard.setCreatedBy(adminId);
			rateCard.setCreatedDate(timestamp);

			int rowsInserted = stmt.executeUpdate();
			if (rowsInserted > 0) {
				log.info("A new rate card was inserted successfully!");
				addRateCard = true;
			}
		} catch (Exception e) {
			log.error("Got Exception while inserting Rate Card Details :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();
			
		}

		return addRateCard;
	}

	public int getRoleId(String roleName) throws SQLException, PropertyVetoException {
		RoleMaster role = null;
		int roleId = 0;

		ResultSet rs = null;
		PreparedStatement stmt = null;
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT role_id, role ");
		queryString.append("FROM role_master ");
		queryString.append("WHERE role=?");
		Connection connection = DBConnect.getConnection();

		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, roleName);
		rs = stmt.executeQuery();

			while (rs.next()) {

				role = new RoleMaster();
				role.setRoleId(rs.getInt(1));
				role.setRoleName(rs.getString(2));

				roleId = role.getRoleId();

			}

		} catch (Exception e) {
			log.error("Got Exception while get role Id Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return roleId;
	}

	public int getLocationId(String locationName) throws SQLException, PropertyVetoException {
		LocationMaster location = null;
		int locationId = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT location_id, location ");
		queryString.append("FROM location_master ");
		queryString.append("WHERE location=?");
		Connection connection = DBConnect.getConnection();
		try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setString(1, locationName);
		rs = stmt.executeQuery();

			while (rs.next()) {

				location = new LocationMaster();
				location.setLocationId(rs.getInt(1));
				location.setLocationName(rs.getString(2));

				locationId = location.getLocationId();

			}

		} catch (Exception e) {
			log.error("Got Exception while get location Details :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}

		return locationId;
	}

	public boolean updateRateCard(RateCardMaster rateCard, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRateCard = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE rate_card_master SET ");
			queryString.append("on_site_rate_per_hour=?");
			queryString.append(",on_site_rate_per_day=?");
			queryString.append(",off_shore_rate_per_hour=?");
			queryString.append(",off_shore_rate_per_day=?");
			queryString.append(",updated_by=?");
			queryString.append(",updated_date=CURRENT_TIMESTAMP()");
			queryString.append(" WHERE role_id="+rateCard.getRoleId());
			queryString.append(" AND ");
			queryString.append(" location_id="+rateCard.getLocationId());
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setFloat(1, rateCard.getOnSiteRatePerHour() );
			stmt.setFloat(2, rateCard.getOnSiteRatePerDay());
			stmt.setFloat(3, rateCard.getOffShoreRatePerHour());
			stmt.setFloat(4,rateCard.getOffShoreRatePerDay());
			stmt.setInt(5, adminId);
			
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("rate card updated successfully");
				updateRateCard = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateRateCard;
	}

	public boolean updateAddedRateCard(RateCardMaster rateCard, int adminId)
			throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean updateRateCard = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE rate_card_master SET ");
			queryString.append("updated_by="+adminId);
			queryString.append(",updated_date=CURRENT_TIMESTAMP(), deleted_by = NULL, deleted_date = NULL ");
			queryString.append(" WHERE ");
			queryString.append(" on_site_rate_per_hour=?");
			queryString.append(" AND on_site_rate_per_day=?");
			queryString.append(" AND off_shore_rate_per_hour=?");
			queryString.append(" AND off_shore_rate_per_day=?");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setFloat(1, rateCard.getOnSiteRatePerHour() );
			stmt.setFloat(2, rateCard.getOnSiteRatePerDay());
			stmt.setFloat(3, rateCard.getOffShoreRatePerHour());
			stmt.setFloat(4,rateCard.getOffShoreRatePerDay());
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("rate card updated successfully");
				updateRateCard = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}
		return updateRateCard;
	}

	public boolean deleteRateCard(int roleId, int locationId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteRateCard = false;
	
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE rate_card_master SET ");
			queryString.append("deleted_by="+adminId);
			queryString.append(",deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND role_id=?");
			queryString.append( " AND ");
			queryString.append("location_id=?");
			try {
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, roleId);
			stmt.setInt(2, locationId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("rate card deleted successfully");
				deleteRateCard = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteRateCard;
	}

	public boolean checkDuplicateRecForRateCardDetails(RateCardMaster rateCard)
			throws SQLException, PropertyVetoException {

		ResultSet rs = null;
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		StringBuffer queryString = new StringBuffer();
		boolean status = false;

		/*	queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM rate_card_master");
			queryString.append(" WHERE location_id=?");
			queryString.append(" AND on_site_rate_per_hour=?");
			queryString.append(" AND on_site_rate_per_day=?");
			queryString.append(" AND off_shore_rate_per_hour=?");
			queryString.append(" AND off_shore_rate_per_day=?");
			queryString.append(" AND deleted_by IS NULL");*/
		
			queryString.append("SELECT COUNT(*)");
			queryString.append(" FROM rate_card_master");
			queryString.append(" WHERE location_id=?");
			queryString.append(" AND role_id=?");
			queryString.append(" AND deleted_by IS NULL");

			try {
				stmt = connection.prepareStatement(queryString.toString());
				/*stmt.setInt(1, rateCard.getLocationId());
				stmt.setFloat(2, rateCard.getOnSiteRatePerHour() );
				stmt.setFloat(3, rateCard.getOnSiteRatePerDay());
				stmt.setFloat(4, rateCard.getOffShoreRatePerHour());
				stmt.setFloat(5,rateCard.getOffShoreRatePerDay());*/
				stmt.setInt(1, rateCard.getLocationId());
				stmt.setInt(2, rateCard.getRoleId());
				rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					status = true;
				}
			}
		} catch (Exception e) {
			log.error("Got exception while finding duplicate record for rate card details:", e);
		} finally {
			rs.close();
			stmt.close();
			connection.close();
			
		}
		return status;

	}
}