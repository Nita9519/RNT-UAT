package ai.rnt.tms.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ai.rnt.tms.model.Timesheet;

public class Util {

	private static final Logger log = LogManager.getLogger(Util.class);

	public static ArrayList<Integer> getEffortsInMinuts() {

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i <= 45; i = i + 15) {
			list.add(i);
		}
		return list;

	}

	public static ArrayList<Integer> getEffortsInHours() {

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i <= 15; i++) {
			list.add(i);
		}
		return list;

	}

	public static ArrayList<Timesheet> timesheetsListPreproccessing(ArrayList<Timesheet> list) {

		Timesheet timesheetNew = null;
		Timesheet timesheet = null;
		ArrayList<Timesheet> newTimesheetList = new ArrayList<Timesheet>();
		ArrayList<String> projectNames = new ArrayList<String>();
		ArrayList<String> taskNames = new ArrayList<String>();
		ArrayList<String> efforts = new ArrayList<String>();

		ListIterator<Timesheet> itr = list.listIterator();

		while (itr.hasNext()) {

			timesheet = itr.next();

			if (timesheetNew == null) {
				timesheetNew = timesheet;
				projectNames.add(timesheet.getProjectName());
				taskNames.add(timesheet.getTaskName());
				efforts.add(timesheet.getEffortHours() + ":" + timesheet.getEffortMinutes());

				timesheetNew.setProjectNames(projectNames);
				timesheetNew.setTaskNames(taskNames);
				timesheetNew.setEfforts(efforts);

				newTimesheetList.add(timesheetNew);
				// continue;
			} else {

				if (timesheetNew.getTimesheetDateString().equals(timesheet.getTimesheetDateString())) {

					if (timesheetNew.getProjectName().equals(timesheet.getProjectName())) {

						// projectNames.add(timesheet.getProjectName());
						taskNames.add(timesheet.getTaskName());
						efforts.add(timesheet.getEffortHours() + ":" + timesheet.getEffortMinutes());

						// timesheetNew.setProjectNames(projectNames);
						timesheetNew.setTaskNames(taskNames);
						timesheetNew.setEfforts(efforts);

					} else {

						projectNames.add(timesheet.getProjectName());
						taskNames.add(timesheet.getTaskName());
						efforts.add(timesheet.getEffortHours() + ":" + timesheet.getEffortMinutes());

						timesheetNew.setProjectNames(projectNames);
						timesheetNew.setTaskNames(taskNames);
						timesheetNew.setEfforts(efforts);
					}

				} else {

					projectNames = new ArrayList<String>();
					taskNames = new ArrayList<String>();
					efforts = new ArrayList<String>();

					timesheetNew = timesheet;
					projectNames.add(timesheet.getProjectName());
					taskNames.add(timesheet.getTaskName());
					efforts.add(timesheet.getEffortHours() + ":" + timesheet.getEffortMinutes());
					timesheetNew.setProjectNames(projectNames);
					timesheetNew.setTaskNames(taskNames);
					timesheetNew.setEfforts(efforts);

					newTimesheetList.add(timesheetNew);
				}
			}
		}
		return newTimesheetList;
	}

	/*
	 * public static ArrayList<String> getDate() { GregorianCalendar cal = new
	 * GregorianCalendar(); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); ArrayList<String> list = new
	 * ArrayList<String>(); int day = cal.get(GregorianCalendar.DAY_OF_MONTH); for
	 * (int i = day; i > (day - 6); i--) { cal.set(GregorianCalendar.DAY_OF_MONTH,
	 * i); Date date = cal.getTime(); list.add(sdf.format(date)); } return list; }
	 */

	public static ArrayList<String> getLastSevenDates() {
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> list = new ArrayList<String>();
		cal.add(GregorianCalendar.DATE, 1);
		for (int i = 1; i < 6; i++) {
			cal.add(GregorianCalendar.DATE, -1);
			list.add(sdf.format(cal.getTime()));
		}
		return list;
	}

	public static ArrayList<String> gettsSubmitListDate(ArrayList<Timesheet> listDate) {

		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> list = new ArrayList<String>();
		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		for (int i = day; i > (day - 6); i--) {
			boolean flag = false;
			cal.set(GregorianCalendar.DAY_OF_MONTH, i);
			Date date = cal.getTime();

			for (Timesheet var : listDate) {

				if (sdf.format(date).toString().equals(var.getTimesheetDate().toString()))
					flag = true;

			}
			if (flag)
				continue;
			else
				list.add(sdf.format(date));

		}
		return list;
	}

	/****
	 * Method #1 - This Method Is Used To Retrieve The File Path From The Server
	 ****/
	public static String getFilePath(HttpServletRequest req) throws FileNotFoundException {
		String appPath = "", fullPath = "", downloadPath = "tms/images";

		/**** Retrieve The Absolute Path Of The Application ****/
		appPath = req.getSession().getServletContext().getRealPath("");
		fullPath = appPath + File.separator + downloadPath;

		return fullPath;
	}

	/****
	 * Method #2 - This Method Is Used To Get The No. Of Columns In The ResultSet
	 ****/
	public static int getColumnCount(ResultSet res) throws SQLException {
		int totalColumns = res.getMetaData().getColumnCount();
		return totalColumns;
	}

	/**** Method #3 - This Method Is Used To Set The Download File Properties ****/
	public static void downloadFileProperties(HttpServletRequest req, HttpServletResponse resp,
			String toBeDownloadedFile, File downloadFile) {
		try {

			/****
			 * Get The Mime Type Of The File & Setting The Binary Type If The Mime Mapping
			 * Is Not Found
			 ****/
			String mimeType = req.getSession().getServletContext().getMimeType(toBeDownloadedFile);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			/**** Setting The Content Attributes For The Response Object ****/
			resp.setContentType(mimeType);
			resp.setContentLength((int) downloadFile.length());

			/**** Setting The Headers For The Response Object ****/
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			resp.setHeader(headerKey, headerValue);

			/**** Get The Output Stream Of The Response ****/
			OutputStream outStream = resp.getOutputStream();
			FileInputStream inputStream = new FileInputStream(downloadFile);
			byte[] buffer = new byte[60000];
			int bytesRead = -1;

			/****
			 * Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data
			 * Read From The Input Stream Into The Output Stream
			 ****/
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (IOException ioExObj) {
			log.error("Exception While Performing The I/O Operation?= " + ioExObj);
		}
	}
}
