package com.bcits.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class CalenderClass {
	public int getPresentMonth(){
		
		String month;
		
		int calenderMonth=Calendar.getInstance().get(Calendar.MONTH)+1;		
		int calenderYear=Calendar.getInstance().get(Calendar.YEAR);
		
		if(calenderMonth<10)
			month=calenderYear+"0"+calenderMonth;
		else
			month=calenderYear+""+calenderMonth;	
		
		return Integer.parseInt(month);
	}
	
public int getPrevMonth(){
		
		String month;
		
		int calenderMonth=Calendar.getInstance().get(Calendar.MONTH);		
		int calenderYear=Calendar.getInstance().get(Calendar.YEAR);
		
		if(calenderMonth<10)
			month=calenderYear+"0"+calenderMonth;
		else
			month=calenderYear+""+calenderMonth;	
		
		return Integer.parseInt(month);
	}
	public Timestamp convetStringToTimeStamp(String rdngDate) throws ParseException {
		DateFormat formatter;
	      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = (Date) formatter.parse(rdngDate);
	      Timestamp readTime = new Timestamp(date.getTime());
	      //System.err.println(readTime+"  "+rdngDate);
		return readTime;
	}

}
