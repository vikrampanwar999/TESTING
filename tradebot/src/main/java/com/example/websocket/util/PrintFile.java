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
import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.OrderTransaction;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

public class PrintFile {
	public static void printReport(KafkaConfig kc) {

		try {

			String filename = filepath() + getTime() + "err.txt";
			String fileflow = filepath() + getTime() + "flow.txt";
			String fileExe = filepath() + getTime() + "exe.txt";
			String fileOT = filepath() + getTime() + "OT.txt";
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
			Set<Entry<String, List<ExecutionReport>>> exerecords = kconsumer.ExecutionReportmap.entrySet();
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
			for (Entry<String, List<ExecutionReport>> record : exerecords) {
				fw3.append(index++ + ") order_id :" + record.getKey() + "\n");
				fw3.append(record.getValue() + "\n");
				fw3.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			index = 1;
			for (Entry<String, List<OrderTransaction>> record : OTrecords) {
				fw4.append(index++ + ") order_id :" + record.getKey() + "\n");
				fw4.append(record.getValue() + "\n");
				fw4.append("******************************************************************\n");
				System.out.println("order_id :" + record.getKey());
				System.out.println(record.getValue().toString());

			}
			combinedRecord(flowrecords, records, exerecords, OTrecords);
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

	public static String filepath() {
		LocalDate currentDate = LocalDate.now(); // 2016-06-17
		DayOfWeek dow = currentDate.getDayOfWeek(); // FRIDAY
		int dom = currentDate.getDayOfMonth(); // 17
		int doy = currentDate.getDayOfYear(); // 169
		Month m = currentDate.getMonth(); // JUNE
		boolean dir = new File(dom + m.toString()).mkdirs();
		String filepath = "D:\\TestReports\\" + dom + m.toString() + "\\";
		return filepath;
	}

	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate1 = dateFormat.format(date);
		String formattedDate = formattedDate1.substring(0, 5).replace(":", "");
		return formattedDate;

	}

	public static void combinedRecord(Set<Entry<String, List<String>>> flowrecords,
			Set<Entry<String, List<String>>> records, Set<Entry<String, List<ExecutionReport>>> exerecords,
			Set<Entry<String, List<OrderTransaction>>> OTrecords) throws IOException {
		int index=1;
		String fileComb = filepath() + getTime() + "combined.txt";
		FileWriter fw5 = new FileWriter(fileComb, true);
		for (Entry<String, List<ExecutionReport>> record : exerecords) {
			fw5.append(index++ + ") order_id :" + record.getKey() + "\n");
			fw5.append(record.getValue() + "\n");
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
				fw5.append(otvalues.toString());
            
			
			
			fw5.append("\n******************************************************************\n");
		}
			fw5.close();
		}
		
	}

