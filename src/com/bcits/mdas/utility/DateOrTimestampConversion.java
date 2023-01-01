package com.bcits.mdas.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateOrTimestampConversion {
	
	public static String addDays(String date,String format,int addDays) throws ParseException{
		//date = "2019-01-24";  
		SimpleDateFormat s=new SimpleDateFormat(format);
		Date d=s.parse(date);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, addDays);
		return s.format(c.getTime()); 
	}
	public XMLGregorianCalendar stringToXMLGregorianCalendar(String s) 
		     throws ParseException, 
		            DatatypeConfigurationException
		 {
		 XMLGregorianCalendar result = null;
		 Date date;
		 SimpleDateFormat simpleDateFormat;
		 GregorianCalendar gregorianCalendar;
		 
		 simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		                date = simpleDateFormat.parse(s);        
		                gregorianCalendar = 
		                    (GregorianCalendar)GregorianCalendar.getInstance();
		                gregorianCalendar.setTime(date);
		                result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		                return result;
		 }
	 public static Timestamp convertStringToTimestamp(String str_date) {
		    try {
		      DateFormat formatter;
		      formatter = new SimpleDateFormat("dd/MM/yyyy");
		       // you can change format of date
		      Date date = formatter.parse(str_date);
		      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

		      return timeStampDate;
		    } catch (ParseException e) {
		      System.out.println("Exception :" + e);
		      return null;
		    }
		  }
}
