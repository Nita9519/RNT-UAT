package ai.rnt.pins.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ai.rnt.lms.model.User;
import ai.rnt.main.dao.DBConnect;
import ai.rnt.pins.model.ProjectPhase;
import ai.rnt.pins.model.ProjectView;

public class ProjectPhasesdao {
	DBConnect dbconnect = new DBConnect();
	ResultSet rs = null;

	private static final Logger log = LogManager.getLogger(ProjectPhasesdao.class);

	public Boolean deletePhases(ProjectView projectView, ProjectPhase projectPhase)
			throws SQLException, PropertyVetoException {

		Connection connection = DBConnect.getConnection();
		PreparedStatement stmt = null;
		boolean deletePhase = false;
		
			StringBuffer queryString = new StringBuffer();
			queryString.append("UPDATE project_phase SET deleted_by=?");
			queryString.append(",deleted_date=CURRENT_TIMESTAMP() ");
			queryString.append("WHERE deleted_by IS NULL AND phase_Id=");
			queryString.append(projectPhase.getProjectPhaseId());
			queryString.append(" AND project_id="+projectView.getProjectId());
			try {
				
			stmt = connection.prepareStatement(queryString.toString());
			stmt.setInt(1,projectView.getProjectManagerId());
			
			int rowUpdated = stmt.executeUpdate();
			if (rowUpdated > 0) {
				log.info("phase deleted successfully");
				deletePhase = true;
			}
		} finally {
			stmt.close();
			connection.close();
		}

		return deletePhase;
	}
}
