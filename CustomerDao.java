package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.Customer;

import ai.rnt.pins.model.LocationMaster;

public class CustomerDao {
	// Customer customer = new Customer();

	private static final Logger log = LogManager.getLogger(CustomerDao.class);

	// edit this apply innj
	public ArrayList<Customer> getlistOfCustomers() throws SQLException, PropertyVetoException {
		Customer customer = null;

		ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select  Customer_ID,Company_Name,Domain_Name,Office_Contact_Number,"
				+ "Contact_Person_Name,Contact_Person_Number,Contact_Person_Mobile_Number,"
				+ "Email_ID,Website,Location_Id,DATE_FORMAT(Start_date,'%d-%b-%Y'),Technology, MSA_signed,NDA_signed,Address,Customer_Status ");
		queryString.append("from customer ");
		queryString.append("WHERE deleted_by IS NULL order by Customer_Status ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {

				customer = new Customer();
				customer.setCustomerID(rs.getInt(1));
				customer.setCompanyName(rs.getString(2));
				customer.setDomainName(rs.getString(3));
				customer.setOfficeContactNumber(rs.getString(4));
				customer.setContactPersonName(rs.getString(5));
				customer.setContactPersonMobileNumber(rs.getString(6));
				customer.setContactPersonNumber(rs.getString(7));
				customer.setEmailId(rs.getString(8));
				customer.setWebsite(rs.getString(9));
				customer.setLocationId(rs.getInt(10));
				customer.setStartDateInString(rs.getString(11));
				customer.setTechnology(rs.getString(12));
				customer.setMasterServiceAgreement(rs.getString(13));
				customer.setNonDisclosureAgreement(rs.getString(14));
				customer.setAddress(rs.getString(15));
				customer.setCustomerStatus(rs.getString(16));

				listOfCustomers.add(customer);

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Customer :: ", e);
			// e.printStackTrace();
		} finally {

			rs.close();
			statement.close();
			connection.close();

		}
		return listOfCustomers;
	}

	public ArrayList<Customer> getlistOfCustomersForactivelist() throws SQLException, PropertyVetoException {
		Customer customer = null;

		ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select  Customer_ID,Company_Name,Domain_Name,Office_Contact_Number,"
				+ "Contact_Person_Name,Contact_Person_Number,Contact_Person_Mobile_Number,"
				+ "Email_ID,Website,Location_Id,DATE_FORMAT(Start_date,'%d-%b-%Y'),Technology, MSA_signed,NDA_signed,Address,Customer_Status ");
		queryString.append("from customer ");
		queryString.append("WHERE Customer_Status='A' AND deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {

				customer = new Customer();
				customer.setCustomerID(rs.getInt(1));
				customer.setCompanyName(rs.getString(2));
				customer.setDomainName(rs.getString(3));
				customer.setOfficeContactNumber(rs.getString(4));
				customer.setContactPersonName(rs.getString(5));
				customer.setContactPersonMobileNumber(rs.getString(6));
				customer.setContactPersonNumber(rs.getString(7));
				customer.setEmailId(rs.getString(8));
				customer.setWebsite(rs.getString(9));
				customer.setLocationId(rs.getInt(10));
				/* customer.setStartDate(rs.getDate(11)); */
				customer.setStartDateInString(rs.getString(11));
				customer.setTechnology(rs.getString(12));
				customer.setMasterServiceAgreement(rs.getString(13));
				customer.setNonDisclosureAgreement(rs.getString(14));
				customer.setAddress(rs.getString(15));
				customer.setCustomerStatus(rs.getString(16));

				listOfCustomers.add(customer);

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Customer :: ", e);
			// e.printStackTrace();
		} finally {

			rs.close();
			statement.close();
			connection.close();

		}
		return listOfCustomers;
	}

	public boolean getlistOfCustomersForCheck() throws SQLException, PropertyVetoException {
		Customer customer = null;

		ResultSet rs = null;
		PreparedStatement statement = null;
		boolean status = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("Select COUNT(*) from customer WHARE Company_Name='");
		queryString.append("' ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0)
					status = true;

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Customer :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return status;
	}

	public ArrayList<LocationMaster> getListOfLocations() throws SQLException, PropertyVetoException {
		LocationMaster location = null;

		ArrayList<LocationMaster> locationList = new ArrayList<LocationMaster>();

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT  location_id, location FROM location_master WHERE deleted_by IS NULL ");

		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {

				location = new LocationMaster();
				location.setLocationId(rs.getInt(1));
				location.setLocationName(rs.getString(2));
				locationList.add(location);

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching list Of Milestone :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();

		}
		return locationList;
	}
//////////////////
	public Boolean insertCustomerDetails(Customer customer, int adminId) throws SQLException, PropertyVetoException {

		boolean insertStatus = false;

		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		Statement stmt = connection.createStatement();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		queryString.append(
				" INSERT INTO customer( Company_Name, Domain_Name, Office_Contact_Number, Contact_Person_Name,");
		queryString.append(" Contact_Person_Number, Contact_Person_Mobile_Number, Email_ID, Website, Location_Id,");
		queryString.append(" Start_date,Technology, MSA_signed, NDA_signed,");
		queryString.append(" Address, customer_Status,created_by,created_date) ");
		queryString.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ");
		queryString.append(adminId);
		queryString.append(",CURRENT_TIMESTAMP() ");
		queryString.append(")");
		try {
			

			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, customer.getCompanyName());
			statement.setString(2, customer.getDomainName());
			statement.setString(3, customer.getOfficeContactNumber());
			statement.setString(4, customer.getContactPersonName());
			statement.setString(5, customer.getContactPersonNumber());
			statement.setString(6, customer.getContactPersonMobileNumber());
			statement.setString(7, customer.getEmailId());
			statement.setString(8, customer.getWebsite());
			statement.setInt(9, customer.getLocationId());
			statement.setDate(10, customer.getStartDate());
			statement.setString(11, customer.getTechnology());
			statement.setString(12, customer.getMasterServiceAgreement());
			statement.setString(13, customer.getNonDisclosureAgreement());
			statement.setString(14, customer.getAddress());
			statement.setString(15, customer.getCustomerStatus());

			if (customer.getCustomerStatus() == null)
				statement.setString(15, "N");
			else
				statement.setString(15, "A");

			customer.setCreatedBy(adminId);
			customer.setCreatedDate(timestamp);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				insertStatus = true;
				log.info(" customer DETAILS inserted successfully!");
			}

		} catch (Exception e) {
			log.error("Got Exception while inserting Customer Details :: ", e);
			// e.printStackTrace();
		} finally {
			stmt.close();
			connection.close();

		}

		return insertStatus;

	}

	public int getCustomerId(String companyName) throws SQLException, PropertyVetoException {

		int customerId = 0;

		ResultSet rs = null;
		PreparedStatement statement = null;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT customer_id FROM customer WHERE Company_name =?");
		Connection connection = DBConnect.getConnection();
	
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, companyName);
			rs = statement.executeQuery();

			while (rs.next()) {
				customerId = rs.getInt(1);
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching Company Name :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return customerId;
	}

	public boolean checkCustomerDuplicateRecord(String companyName) throws SQLException, PropertyVetoException {

		int customerId = 0;

		ResultSet rs = null;
		PreparedStatement statement =null;
		boolean status = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT COUNT(*) FROM customer WHERE Company_name =?");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, companyName);
			rs = statement.executeQuery();

			while (rs.next()) {
				if (rs.getInt(1) > 0)
					status = true;

			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching Company Name :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
			
		}
		return status;
	}

	public boolean updateCustomerDetails(Customer customer, int adminId) throws SQLException, PropertyVetoException {
        boolean insertcustomer=false;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();

		try {
			queryString.append(" UPDATE customer  SET  Domain_Name = ?, Office_Contact_Number = ? ,"
					+ " Contact_Person_Name = ? , Contact_Person_Number = ? , Contact_Person_Mobile_Number = ? ,"
					+ " Email_ID = ? ,Website = ?, Location_Id = ? , Start_Date = ?,  Technology = ? ,"
					+ " MSA_signed = ? , NDA_signed = ? , Address = ? , Customer_Status = ? ,"
					+ " updated_date = CURRENT_TIMESTAMP(), updated_by = ");
			queryString.append(adminId);
			queryString.append(" WHERE Customer_ID= ");
			queryString.append(customer.getCustomerID());

			try {

				statement = connection.prepareStatement(queryString.toString());

				statement.setString(1, customer.getDomainName());
				statement.setString(2, customer.getOfficeContactNumber());
				statement.setString(3, customer.getContactPersonName());
				statement.setString(4, customer.getContactPersonNumber());
				statement.setString(5, customer.getContactPersonMobileNumber());
				statement.setString(6, customer.getEmailId());
				statement.setString(7, customer.getWebsite());
				statement.setInt(8, customer.getLocationId());
				statement.setDate(9, customer.getStartDate());
				statement.setString(10, customer.getTechnology());
				statement.setString(11, customer.getMasterServiceAgreement());
				statement.setString(12, customer.getNonDisclosureAgreement());
				statement.setString(13, customer.getAddress());

				if (customer.getCustomerStatus() == null)
					statement.setString(14, "N");
				else
					statement.setString(14, "A");

				customer.setCreatedBy(adminId);

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0) {
					insertcustomer=true;
					log.info(" DETAILS inserted successfully!");
				}

			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			} finally {
				statement.close();
				connection.close();
			}
			return insertcustomer;

		} catch (Exception e) {
			log.error("Got Exception while updating Customer Details :: ", e);
			// e.printStackTrace();
		} finally {
			statement.close();
			connection.close();

		}
		new CustomerDao();
		return insertcustomer;

	}
	// get single customer innerj
	public Customer getCustomer(int customerID) throws SQLException, PropertyVetoException {
		Customer customer = new Customer(); // for single Customer

		ResultSet rs = null;
		PreparedStatement statement =null;
		StringBuffer queryString = new StringBuffer();

		queryString.append(
				"SELECT c.Company_Name, c.Domain_Name, c.Office_Contact_Number, c.Contact_Person_Name, c.Contact_Person_Number ");
		queryString.append(", c.Contact_Person_Mobile_Number, c.Email_ID, c.Website, c.Start_date, c.Technology ");
		queryString.append(
				", c.MSA_signed, c.NDA_signed, c.Address, c.Customer_Status, l.location_id, l.location FROM customer AS c ");
		queryString.append("INNER JOIN location_master as l WHERE c.location_id = l.location_id AND customer_id=");
		queryString.append(customerID);

		Connection connection = DBConnect.getConnection();


		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();

			while (rs.next()) {

				customer = new Customer();
				customer.setCustomerID(customerID);
				customer.setCompanyName(rs.getString(1));
				customer.setDomainName(rs.getString(2));
				customer.setOfficeContactNumber(rs.getString(3));
				customer.setContactPersonName(rs.getString(4));
				customer.setContactPersonMobileNumber(rs.getString(5));
				customer.setContactPersonNumber(rs.getString(6));
				customer.setEmailId(rs.getString(7));
				customer.setWebsite(rs.getString(8));
				customer.setStartDate(rs.getDate(9));
				customer.setTechnology(rs.getString(10));
				customer.setMasterServiceAgreement(rs.getString(11));
				customer.setNonDisclosureAgreement(rs.getString(12));
				customer.setAddress(rs.getString(13));
				customer.setCustomerStatus(rs.getString(14));
				customer.setLocationId(rs.getInt(15));
				customer.setLocationName(rs.getString(16));
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching details Of Customer :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return customer;
	}

	public Boolean deleteCustomer(int customerId, int adminId) throws SQLException, PropertyVetoException {
		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deleteCustomer = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("UPDATE customer SET ");
		queryString.append("deleted_by=?");
		queryString.append(" ,deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND Customer_ID=?");
		try {
			

			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1, adminId);
			stmt.setInt(2, customerId);
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("customer deleted successfully");
				deleteCustomer = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deleteCustomer;
	}
}