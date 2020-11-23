/**********************************************************************************************************************
 **	Copyright 2017-18 RnT.AI.  All rights reserved.
 **
 **	No Part of this file should be copied or distributed without the permission of RnT.AI.
 **	Application		:	LEAVE MANAGEMENT SYSTEM
 **	Module			:	LMS.war
 ** Version			:	1.0
 **	File			:	DBConnect.java
 **	Description		:	The java Class DBConnect is a class that creates a connection with the underlying  database.
 **	Author			:	Jayesh Patil
 **	Created Date	:	Friday October 06, 2017
 **********************************************************************************************************************
 **	Change History Header:
 **********************************************************************************************************************
 **	Date			Author    		  Version 		Description:
 **	-------			--------   		  --------		------------
 ** 06/10/2017      Jayesh Patil		  1.0           Created
 *********************************************************************************************************************/
package ai.rnt.main.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
/*import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;*/
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DBConnect {

	public static DataSource dataSource = null;
	private static final Logger log = LogManager.getLogger(DBConnect.class);

	public static CommonDataSource getDataSource() throws PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
	cpds.setDriverClass("com.mysql.jdbc.Driver");
		/*
		  cpds.setJdbcUrl("jdbc:mysql://18.191.28.50:3306/rntdbuat");
		  cpds.setUser("rnt_user"); cpds.setPassword("RnT@LMS");*/
		
		  cpds.setJdbcUrl("jdbc:mysql://localhost:3306/rntdbuat"); cpds.setUser("root");
		  cpds.setPassword("MyNewPass");
		 
		
		/*
		 * cpds.setJdbcUrl(
		 * "jdbc:mysql://rntdbuatprod.cywt4ngax3hu.us-east-2.rds.amazonaws.com:3306/rntproddb"
		 * ); cpds.setUser("rnt_user_prod"); cpds.setPassword("pr0du$erpwd");
		 */
			 
		// Optional Settings
		cpds.setInitialPoolSize(10);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		cpds.setMaxStatements(100);
		cpds.setMaxIdleTime(2);
		dataSource = cpds;
		return cpds;
	}

	public static Connection getConnection() throws SQLException, PropertyVetoException {
		// ComboPooledDataSource dataSource=(ComboPooledDataSource)
		// DBConnect.getDataSource();

		if (dataSource == null) {
			dataSource = (ComboPooledDataSource) DBConnect.getDataSource();
		}
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(true);
		return connection;
	}

	/*
	 * public static Connection getConnection() throws SQLServerException {
	 * 
	 * SQLServerDataSource ds = new SQLServerDataSource();
	 * ds.setUser("masterrntuser"); ds.setPassword("rnt27012345");
	 * ds.setServerName("rntdbinstance.cywt4ngax3hu.us-east-2.rds.amazonaws.com");
	 * ds.setPortNumber(1433); ds.setDatabaseName("test_db");
	 * 
	 * Connection connection2 = ds.getConnection(); return connection2; }
	 */
}
