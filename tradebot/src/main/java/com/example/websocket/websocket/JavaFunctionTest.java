package com.example.websocket.websocket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class JavaFunctionTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	//	Date date = new Date();
		//long t1=date.getDate();
		//System.out.println(t1);
		System.out.println(System.currentTimeMillis());
		//System.out.println((t1 / 1000 / 60 / 60) % 24 + ":" + (t1 / 1000 / 60) % 60 + ":" + (t1 / 1000) % 60);
		 Calendar cal = Calendar.getInstance();
		    Date date=cal.getTime();
		    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		    String formattedDate1=dateFormat.format(date);
		    String formattedDate=formattedDate1.substring(0, 5).replace(":", "");
		    System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
		}
	/*	System.out.println(filepath);
		boolean dir = new File(filepath).mkdirs();
		
		FileWriter fw=new FileWriter(filepath+1,true);
		fw.append("hi");*/

	

	public static String filepath() {
		LocalDate currentDate = LocalDate.now(); // 2016-06-17
		DayOfWeek dow = currentDate.getDayOfWeek(); // FRIDAY
		int dom = currentDate.getDayOfMonth(); // 17
		int doy = currentDate.getDayOfYear(); // 169
		Month m = currentDate.getMonth(); // JUNE
		boolean dir = new File(dom+m.toString()).mkdirs();
		String filepath="D:\\TestReports\\"+dom+m.toString();
		return filepath;
	}
}
