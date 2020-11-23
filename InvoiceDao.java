package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.Invoice;

public class InvoiceDao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(InvoiceDao.class);

	public ArrayList<Invoice> getListOfInvoice() throws SQLException, PropertyVetoException {
		Invoice invoice = new Invoice();

		ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
		
		ResultSet rs = null;
		PreparedStatement statement=null;
		StringBuffer queryString = new StringBuffer();
		queryString.append(
				"Select invoice_id, customer_id, company_name, address, invoice_date, due_date," + "billing_period,  ");
		queryString.append("from rate_card_master ");
		queryString.append("WHERE deleted_by IS NULL ");
		Connection connection = DBConnect.getConnection();

		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
		

			while (rs.next()) {

				invoice = new Invoice();
				invoice.setInvoiceId(rs.getInt(1));
				invoice.setCustomerId(rs.getInt(2));
				invoice.setCompany(rs.getString(3));
				invoice.setCompanyAddress(rs.getString(4));
				invoice.setInvoiceDate(rs.getDate(5));
				invoice.setDueDate(rs.getDate(6));
				invoice.setBillingPeriod(rs.getInt(7));
				invoiceList.add(invoice);
				
			}

		} catch (Exception e) {
			log.error("Got Exception while Fetching Invoice :: ", e);
			// e.printStackTrace();
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}

		return invoiceList;
	}
}