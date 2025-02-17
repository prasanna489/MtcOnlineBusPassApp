package com.mtc.online.buspass.useful.functions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateFunctions {

	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public String getToday() {
		Date date=new Date();
		return dateFormat.format(date);
	}
	
	
//	public Date afterFivedays(Date date) {
//		Calendar c = Calendar.getInstance();
//	    c.setTime(date);
//	    c.add(Calendar.DAY_OF_MONTH, 5);
//	    Date lastDate=c.getTime();
//	    return lastDate;
//	}
	
	
	public long diffInday(Date date) {  
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int year = cal.get(Calendar.YEAR);
	    int month=cal.get(Calendar.MONTH)+1;
	    int day=cal.get(Calendar.DAY_OF_MONTH);
	 	LocalDate dateOfapply = LocalDate.of(year,month,day);
	    LocalDate currentDate = LocalDate.now();
	    long diffInDays = ChronoUnit.DAYS.between(dateOfapply,currentDate); 
	   // System.out.println("diff in days"+diffInDays);
	    return diffInDays;
	}
}
