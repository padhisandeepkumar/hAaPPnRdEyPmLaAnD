package com.replad.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.replad.init.InitConfiguration;

public class DateUtils {

	public Timestamp getCurrentDate(){
		return (new Timestamp(new Date().getTime()));
	}

	/**
	 * Compare the given time stamps for equality.
	 * If all the timestamps are same/equals then it will return true else false;a
	 * 
	 * @param values
	 * @return
	 */
	public boolean equals(Timestamp... values){
		boolean flag = true;
		Timestamp ts1 = null;
		if(null!=values && values.length>1){
			for(Timestamp ts : values){
				if(null==ts1){
					ts1=ts;
					continue;
				}else{
					if(!ts1.equals(ts)){
						flag = false;
						break;
					}
				}
			}
		}
		return flag;
	}
	/**
	 * Converts the timestamp into the desired format defined in the common.properties file.
	 * 
	 * @param ts
	 * @return
	 */
	public String formatDateTime(Timestamp ts){
		String dateTimeFormat = new InitConfiguration().commonPropertiesMap.get("time.date.display.format");
		String convertedDateTime = new SimpleDateFormat(dateTimeFormat).format(ts);
		return convertedDateTime;
	}
	
	
	public Timestamp databaseFormats(String aValue) {
		if (StringUtilities.isEmpty(aValue))
			return null;
		try {
			SimpleDateFormat formats = new SimpleDateFormat(InitConfiguration.commonPropertiesMap.get("time.date.database.format"));
			Date myDate = formats.parse(aValue);
			if (myDate instanceof Date){
				if (myDate != null)
					return new java.sql.Timestamp(myDate.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String databaseFormats(String aValue, String format) {
		if (StringUtilities.isEmpty(aValue))
			return null;
		try {
			SimpleDateFormat aFormat = new SimpleDateFormat(InitConfiguration.commonPropertiesMap.get("time.date.database.format"));
			Date myDate = aFormat.parse(aValue);
			if (myDate instanceof Date){
				if (myDate != null)
					aFormat = new SimpleDateFormat(format);
					return aFormat.format(myDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return true if weekend else false for weekday.
	 * 
	 * @param userInputDate
	 * @return
	 */
	public boolean isWeekend(String userInputDate){
		Timestamp serviceReqDate = getDate(userInputDate);
		Calendar cal=GregorianCalendar.getInstance();
		cal.setTime(serviceReqDate);
		int dayOfweek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		// 1 is sunday, 7 is saturday
		boolean isWeekDay = false;
		if(dayOfweek==1 || dayOfweek==7 ){
			isWeekDay = true;
		}
		return isWeekDay;
	}
	/**
	 * Returns the Timestamp object of the user input date. 
	 * If user input is null OR blank then return the current date Timestamp Object.
	 * 
	 * @param userInputDate
	 * @return
	 */
	public Timestamp getDate(String userInputDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedTimeStamp = new Date();
		if(StringUtilities.isNotEmpty(userInputDate)){
			try {
				parsedTimeStamp = dateFormat.parse(userInputDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Timestamp serviceReqDate = new Timestamp(parsedTimeStamp.getTime());
		return serviceReqDate;
	}
	/**
	 * Increment day into the timestamp object. Pass negatrive number for decrement and positive number for increment.
	 * 
	 * @param ts
	 * @param increment
	 * @return
	 */
	public Timestamp incrementDay(Timestamp ts, int increment){
		Calendar cal=GregorianCalendar.getInstance();
		cal.setTime(ts);
		cal.add(Calendar.DAY_OF_WEEK, increment);
		ts = new Timestamp(cal.getTime().getTime());
		return ts;
	}
	
	/**
	 * String to Date Conversion
	 */
	public static java.sql.Date getDateFromString(String aStringDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		java.sql.Date date = null;
		try {
			date = (java.sql.Date) format.parse(aStringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Date to String Conversion
	 */
	public static String DateToStringConversion(java.util.Date aDate){
		String reportDate = null; 
		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm");
		reportDate = df.format(aDate);
		return reportDate; 
	}
}
