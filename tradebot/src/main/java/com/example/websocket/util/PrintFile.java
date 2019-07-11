package com.example.websocket.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map.Entry;

import com.example.test.TestCase1;
import com.example.websocket.bean.OrderExecutionReport;
import com.example.websocket.bean.OrderTransaction;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

public class PrintFile {
	public static final String path="D:\\TestReports2\\prod\\";
	private static PrintFile pf=null;
	private PrintFile() {
		
		
	}
	public static synchronized PrintFile getPrintFile() {
		if(pf==null) {
			pf=new PrintFile();
		}
		if(pf.path==null) {
			//filepath();
		}
		return pf;
	}
	public synchronized void printReport(KafkaConfig kc) {
			//path=filepath();
		try {

			String filename = path + "err.txt";
			String fileflow = path  + "flow.txt";
			String fileExe = path  + "exe.txt";
			String fileOT = path + "OT.txt";
			System.out.println("file path:"+filename);
			FileWriter fw = new FileWriter(filename, true);
			FileWriter fw2 = new FileWriter(fileflow, true);
			FileWriter fw3 = new FileWriter(fileExe, true);
			FileWriter fw4 = new FileWriter(fileOT, true);
			fw.append(getTime() + "#############################################################################\n");
			fw2.append(getTime() + "#############################################################################\n");
			fw3.append(getTime() + "################################################################\n");
			fw4.append(getTime() + "#############################################################################\n");
			KafkaConsumer kconsumer = new KafkaConsumer(kc);
			Set<Entry<String, List<String>>> flowrecords = kconsumer.flowReport.entrySet();
			Set<Entry<String, List<String>>> records = TestCase1.faultyorders.entrySet();
			Set<Entry<String, List<OrderExecutionReport>>> exerecords = kconsumer.ExecutionReportmap.entrySet();
			Set<Entry<String, List<OrderTransaction>>> OTrecords = kconsumer.transactionmap.entrySet();

			System.out.println("########################################### Total executed test cases " + records.size()
					+ "################################################################");
			int index = 1;
			for (Map.Entry<String, List<String>> record : records) {
				fw.append(index++ + ") order_id :" + record.getKey() + "\n");
				fw.append(record.getValue() + "\n");
				fw.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			index = 1;
			for (Map.Entry<String, List<String>> record : flowrecords) {
				fw2.append(index++ + ") order_id :" + record.getKey() + "\n");
				fw2.append(record.getValue() + "\n");
				fw2.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			index = 1;
			for (Entry<String, List<OrderExecutionReport>> record : exerecords) {
				fw3.append(index++ + ") order_id :" + record.getKey() + "\n");
				List<OrderExecutionReport> a=record.getValue();
				for(OrderExecutionReport b:a) {
					fw3.append(b.toString());
					fw3.append("\n");
				}
				//fw3.append(record.getValue() + "\n");
				fw3.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			index = 1;
			for (Entry<String, List<OrderTransaction>> record : OTrecords) {
				fw4.append(index++ + ") order_id :" + record.getKey() + "\n");
				List<OrderTransaction> a=record.getValue();
				for(OrderTransaction b:a) {
					fw4.append(b.toString()+"..\n");
				}
				fw4.append(record.getValue() + "\n");
				fw4.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			combinedRecord(flowrecords, records, exerecords, OTrecords);
			combinedFilledRecord("FILLED",flowrecords, records, exerecords, OTrecords);
			combinedFilledRecord("NOTFILLED",flowrecords, records, exerecords, OTrecords);
			fw.close();
			fw2.close();
			fw3.close();
			fw4.close();
		}

		catch (Exception e) {
			System.out.println("exception in writing the file ");
			e.printStackTrace();
		} finally {
			System.out.println("-----------------------------------FINISHED----------------------------");
		}
	}

	public static synchronized String filepath() {
		LocalDate currentDate = LocalDate.now(); // 2016-06-17
		DayOfWeek dow = currentDate.getDayOfWeek(); // FRIDAY
		int dom = currentDate.getDayOfMonth(); // 17
		int doy = currentDate.getDayOfYear(); // 169
		Month m = currentDate.getMonth(); // JUNE
		String path="D:\\TestReports2\\"+dom+m.toString();
		boolean dir = new File(path).mkdirs();
		//if(dir) {
		//String filepath = "D:\\TestReports\\"  + dom+m.toString()+getTime()+"\\";
		//return path+"\\";
		//}
		return "D:\\TestReports2\\july\\";
	}

	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate1 = dateFormat.format(date);
		String formattedDate = formattedDate1.substring(0, 5).replace(":", "");
		return formattedDate;

	}

	public static synchronized void combinedRecord(Set<Entry<String, List<String>>> flowrecords,
			Set<Entry<String, List<String>>> records, Set<Entry<String, List<OrderExecutionReport>>> exerecords,
			Set<Entry<String, List<OrderTransaction>>> OTrecords) throws IOException {
		int index=1;
		String fileComb = path  + "combined.txt";
		System.out.println("file path:"+fileComb);
		FileWriter fw5 = new FileWriter(fileComb, true);
		for (Entry<String, List<OrderExecutionReport>> record : exerecords) {
			fw5.append(index++ + ") order_id :" + record.getKey() + "\n");
			List<OrderExecutionReport> a1=record.getValue();
			for(OrderExecutionReport a2:a1) {
				fw5.append(a2 + "\n");
			}
			
			List<String>flowvalues=new ArrayList<>();
			List<OrderTransaction>otvalues=new ArrayList<>();
			
				flowrecords.stream()
				.filter(a->a.getKey().equals(record.getKey()))
				.forEach(e->flowvalues.addAll(e.getValue()));
				fw5.append("\nflow values\n");
				fw5.append(flowvalues.toString());
       
				OTrecords.stream()
				.filter(a->a.getKey().equals(record.getKey()))
				.forEach(e->otvalues.addAll(e.getValue()));
			
				fw5.append("\nordertransaction values\n");
				for(OrderTransaction b:otvalues)
				fw5.append(b+"\n");
            
			
			
			fw5.append("\n******************************************************************\n");
		}
			fw5.close();
		}
	
	public static synchronized void combinedFilledRecord(String status,Set<Entry<String, List<String>>> flowrecords,
			Set<Entry<String, List<String>>> records, Set<Entry<String, List<OrderExecutionReport>>> exerecords,
			Set<Entry<String, List<OrderTransaction>>> OTrecords) throws IOException {
		int index=1;
		String fileComb = path +getTime()+ status +".txt";
		FileWriter fw5 = new FileWriter(fileComb, true);
		for (Entry<String, List<OrderExecutionReport>> record : exerecords) {
			List<String>flowvalues=new ArrayList<>();
			List<OrderTransaction>otvalues=new ArrayList<>();
			flowrecords.stream()
			.filter(a->a.getKey().equals(record.getKey()))
			.forEach(e->flowvalues.addAll(e.getValue()));
			long filledcount=flowvalues.stream().filter(e->e.contains("FILLED")).count();
			
			
			if(status.equals("NOTFILLED")) {
				//System.out.println("nonfilled count:"+nonfilledcount);
				if(filledcount>0) {continue;}
			}
			else {
			if(filledcount<=0) {continue;}}
			/*String fill="DEV.E55PRIME.EXECUTIONREPORTS channel recieved order with status FILLED";
			if(!flowvalues.contains(fill)) {continue;}*/
			fw5.append(index++ + ") order_id :" + record.getKey() + "\n");
			List<OrderExecutionReport> a1=record.getValue();
			for(OrderExecutionReport a2:a1) {
				fw5.append(a2 + "\n");
			}
			
		
			
				fw5.append("\nflow values\n");
				fw5.append(flowvalues.toString());
       
				OTrecords.stream()
				.filter(a->a.getKey().equals(record.getKey()))
				.forEach(e->otvalues.addAll(e.getValue()));
			
				fw5.append("\nordertransaction values\n");
				for(OrderTransaction b:otvalues)
				fw5.append(b+"\n");
            
			
			
			fw5.append("\n******************************************************************\n");
		}
			fw5.close();
		}
		
	}

