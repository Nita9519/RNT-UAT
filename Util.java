package ai.rnt.ams.util;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util 
{
	private static final Logger log = LogManager.getLogger(Util.class);

	public int getduration(Date fromDate) {

		LocalDate localDate = LocalDate.now();
		LocalDate fromdateLD = fromDate.toLocalDate();

		Date date = java.sql.Date.valueOf(localDate);
		Date date1 = java.sql.Date.valueOf(fromdateLD);

		long diff = date.getTime() - date1.getTime();
		log.info(" @@@@@@@@@@@@@@@@@@@@@@ " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

	}

	public double getefforts(double actual, double estimated) {
		double diff1 = actual - estimated;

		DecimalFormat df = new DecimalFormat("###.##");
		double diff = Double.parseDouble(df.format(diff1));

		return diff;

	}

	public int getFinalConverted(int days) {
		int hours = days * 24;
		return hours;

	}

	public static ArrayList<Integer> getPercentOfAllocation() {

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i <= 100; i = i + 10) {

			list.add(i);
		}
		return list;

	}

	public static ArrayList<String> getEngagementModel() {

		ArrayList<String> list = new ArrayList<String>();
		String[] engagementModelArray = { "TNM", "Fixed", "Cost Plus", "Managed Services" };

		for (int i = 0; i <= 3; i++) {

			list.add(engagementModelArray[i]);
		}
		return list;
	}
	public static ArrayList<String> getExecutionModel() {

		ArrayList<String> list = new ArrayList<String>();
		String[] executionModelArray = { "Dual Shore", "Onsite", "Offshore" };

		
			for (int i = 0; i <= 2; i++) {

				list.add(executionModelArray[i]);
			}
		
		return list;
	}

	public static ArrayList<String> getEffortsUnit() {

		ArrayList<String> list = new ArrayList<String>();
		String[] effortsUnitArray = { "days", "hours" };

		for (int i = effortsUnitArray.length; effortsUnitArray.length - 1 >= 0; i++) {
			list.add(effortsUnitArray[i]);
		}
		return list;
	}

	

	public static ArrayList<String> getStatusOfProject() {

		ArrayList<String> list = new ArrayList<String>();
		String[] statusOfProjectArray = { "Active", "On Hold", "Closed" };

		for (int i = 0; statusOfProjectArray.length - 1 >= 0; i++) {

			list.add(statusOfProjectArray[i]);
		}
		return list;

	}

	public static String getValueOrDefault(String value, String defaultValue) {
		return isNotNullOrEmpty(value) ? value : defaultValue;
	}

	private static boolean isNotNullOrEmpty(String str) {
		return (str != null && !str.isEmpty());
	}

}
