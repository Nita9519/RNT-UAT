

package ai.rnt.tms.dao;

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
import ai.rnt.tms.model.Task;

public class TaskDao {

	DBConnect dbConnect = new DBConnect();
	ResultSet rs = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	boolean managerFlag = false;

	private static final Logger log = LogManager.getLogger(TaskDao.class);
	

	public ArrayList<Task> getTaskDetails() throws SQLException, PropertyVetoException {
		Task taskDetails = null;
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		ResultSet rs = null;
		StringBuffer queryString = new StringBuffer();
		PreparedStatement statement=null;
		Connection connection = DBConnect.getConnection();
		queryString.append("SELECT task_ID,task_name FROM task_master WHERE deleted_by IS NULL");
	
		
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			while (rs.next()) {
				taskDetails = new Task();
				taskDetails.setTaskID(rs.getInt(1));
				taskDetails.setTaskName(rs.getString(2));
				taskList.add(taskDetails);
				
			}

		} catch (NumberFormatException e) {
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return taskList;
	}

	public Boolean insertTask(Task task, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;
		StringBuffer queryString = new StringBuffer();
		queryString.append("INSERT INTO task_master (task_name,created_by,created_date) VALUES (?,?,CURRENT_TIMESTAMP()) ");
		
		Connection connection = DBConnect.getConnection();
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, task.getTaskName());
			statement.setInt(2, adminID);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("A new task was inserted successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}
	
	public Boolean updateTask(Task task, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;

		StringBuffer queryString = new StringBuffer();
		
		queryString.append("UPDATE task_master SET task_name=?");
		queryString.append(",updated_by=?");
		queryString.append(",updated_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND task_ID=?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, task.getTaskName());
			statement.setLong(2, task.getUpdatedBy());
			statement.setInt(3, task.getTaskID());
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				log.info("Task was updated successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}
	
	public Boolean deleteTask(Task task, int adminID)
			throws SQLException, PropertyVetoException {
		// TODO Auto-generated method stub
		PreparedStatement statement = null;
		boolean insertTaskStatus = false;

		StringBuffer queryString = new StringBuffer();
		
		queryString.append("UPDATE task_master SET deleted_by=?");
		queryString.append(",deleted_date=CURRENT_TIMESTAMP() ");
		queryString.append("WHERE deleted_by IS NULL AND task_ID=?");

		Connection connection = DBConnect.getConnection();
		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setLong(1, task.getDeletedBy());
			statement.setInt(2,task.getTaskID());
			int rowsInserted = statement.executeUpdate();
			
			if (rowsInserted > 0) {
				log.info("Task was deleted successfully!");
				insertTaskStatus = true;
			}

		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			connection.close();
		}
		return insertTaskStatus;
	}
	
	public Boolean checkForDuplicacy(Task task) throws SQLException, PropertyVetoException {
		
		ResultSet rs = null;
		Boolean duplicateStatus = false;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT count(*) FROM task_master WHERE task_name=?");
		queryString.append(" AND deleted_by IS NULL");
		
		try {
			statement = connection.prepareStatement(queryString.toString());
			rs = statement.executeQuery();
			statement.setString(1, task.getTaskName());
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					duplicateStatus = true;
					
				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
			

		}
		return duplicateStatus;
	}
	
	public int getTaskID(Task task) throws SQLException, PropertyVetoException {
		
		ResultSet rs = null;
		int seqID=0;
		PreparedStatement statement = null;
		Connection connection = DBConnect.getConnection();
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT task_id FROM task_master WHERE task_name=?");
		queryString.append(" AND deleted_by IS NULL");

		try {
			statement = connection.prepareStatement(queryString.toString());
			statement.setString(1, task.getTaskName());
			rs = statement.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					seqID = rs.getInt(1);
					
				}
			}
		} finally {
			rs.close();
			statement.close();
			connection.close();
		}
		return seqID;
	}
	
}