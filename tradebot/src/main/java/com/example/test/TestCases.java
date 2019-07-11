package com.example.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.util.PrintFile;

@Component
public class TestCases {
	@Autowired
	TestCase1 tc1;
	private KafkaConfig kc;
	public void test(KafkaConfig kc1) throws ClassNotFoundException, InterruptedException, IOException {
		this.kc=kc1;
		tc1.setKafkaConsumer();
		Thread.sleep(5000);
		Thread.sleep(13000);
		PrintFile pf=PrintFile.getPrintFile();
		ExecutorService executor = Executors.newFixedThreadPool(1);
		List<Future<?>> lf=new ArrayList<Future<?>>();
		for (int i = 0; i<1; i++) {
			Future<?> p=executor.submit(new testcaseRunner());
			lf.add(p);
		}
		
		for(int i=0;i<lf.size();i++) {
			try {
				lf.get(i).get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		try{tc1.checkTransactionMap();
		tc1.checkExeMap();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			PrintFile pf1=PrintFile.getPrintFile();
			pf1.printReport(kc);
			System.exit(0);
		}
		//executor.awaitTermination(45,TimeUnit.MINUTES);
	}

	private class testcaseRunner implements Runnable {

		@Override
		public void run() {
			try {
				/*tc1.test2("BTCUSDT", "BUY", "14000.00", "-0.0007801", " 1 negative quantity on buy ");// pass rejected  by sor
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "-15000.00", "0.000087651", " 2 negative price on buy ");// pass rejected  by sor
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "9500.00", "-0.00000091", " 3 negative quantity on sell ");// pass rejected  by sor
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "SELL", "-14500.00", "0.019", " 4 negative price on sell ");// pass rejected  by sor
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "SELLing", "14500.00", "0.000031", "5 incorrect side");//  pass rejected  by sor
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "BUY45", "14400.00", "0.01000009", "6 incorrect side");//  pass rejected  by sor
				Thread.sleep(13000);
				
				tc1.test2("BTCUSDPP", "BUY", "14003.00", "0.0000345", "7 incorrect symbol");//pass rejected
				Thread.sleep(13000);
				tc1.test2("BTCUSD\\", "BUY", "14000.00", "0.00000020", "8 incorrect symbol");// failed internal server error
				Thread.sleep(13000);
				
				tc1.test2("BTCUSD", "SELL\\", "9070.00", "0.000000010", " 9 slash");//failed internal server error
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY\\", "14800.00", "0.0000041", " 10 slash");//failed internal server error
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14300.00", "0..0004010", " 11 double point");//failed internal server error
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14200..00", "0.0000810", " 12 double point");//failed internall server error
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.00000001", "13 low qty");//pass cancelled after trying on some venus
				Thread.sleep(13000);
				tc1.test2("LTCBTC", "SELL", "10070.00", "0.00000003450", "14 extreme low qty ");//pass rejected by post request
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "11000.00", "0.00000091", "15 low qty sell");//pass cancelled after placing on few venus
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "11000.00", "0.00900165", "16 medium qty sell");//some bug 1853flow 
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "11500.00", "0.10001230", "17 high qty sell");//cancelled
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "11500.00", "0.259","18 EXTREME HIGH QTY SELL");//cancelled 
				Thread.sleep(13000);*/
				/*
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.0007801", " 1 ");
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "15000.00", "0.000087651", " 2");
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "SELL", "9500.00", "0.00000091", " 3");
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "SELL", "14500.00", "0.019", " 4 ");
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "SELL", "14500.00", "0.000031", "5 ");//  
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "BUY", "14400.00", "0.01000009", "6 ");//  
				Thread.sleep(13000);                                                           
				                                                                               
				tc1.test2("BTCUSD", "BUY", "14003.00", "0.0000345", "7 ");//p
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "BUY", "14000.00", "0.00000020", "8");//
				Thread.sleep(13000);                                                           
				                                                                               
				tc1.test2("BTCUSD", "SELL", "9070.00", "0.000000010", " 9");//failed in
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14800.00", "0.0000041", " 10 ");//failed in
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14300.00", "0.0004010", " 11");//fai
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14200.00", "0.0000810", " 12 ");//fai
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.00000001", "13 low qty");//pass canc
				Thread.sleep(13000);                                                           
				tc1.test2("LTCBTC", "SELL", "10070.00", "0.00000003450", "14 extreme low qty ");
				Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "SELL", "11000.00", "0.00000091", "15 low qty sell");//pas
				Thread.sleep(13000);                                                           
				
				tc1.test2("BTCUSDT", "SELL", "11000.00", "0.00900165", "16 medium qty sell");//
				Thread.sleep(13000);                222combined txt
				*/
				
				tc1.test2("BTCUSDT", "SELL", "9000.00", "0.0012", " 1 ");
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "15000.00", "0.0012", " 2");
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "SELL", "9500.00", "0.0014", " 3");
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "SELL", "14500.00", "0.0019", " 4 ");
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "SELL", "14500.00", "0.0011", "5 ");//  
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "BUY", "14400.00", "0.0015", "6 ");//  
				//Thread.sleep(13000);                                                           
				                                                                               
				tc1.test2("BTCUSD", "BUY", "14003.00", "0.00145", "7 ");//p
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSD", "BUY", "14000.00", "0.0010", "8");//
				//Thread.sleep(13000);                                                           
				                                                                               
				tc1.test2("BTCUSD", "SELL", "9070.00", "0.0010", " 9");//failed in
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14800.00", "0.0041", " 10 ");//failed in
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14300.00", "0.001010", " 11");//fai
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14200.00", "0.00110", " 12 ");//fai
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.001", "13 low qty");//pass canc
				//Thread.sleep(13000);                                                           
				tc1.test2("LTCBTC", "SELL", "10070.00", "0.0003", "14 extreme low qty ");
				//Thread.sleep(13000);                                                           
				tc1.test2("BTCUSDT", "SELL", "11000.00", "0.0014", "15 low qty sell");//pas
				//Thread.sleep(13000);                                                          
				
				tc1.test2("BTCUSDT", "BUY", "14250.00", "0.0014", "16 medium qty buy");//
				//Thread.sleep(3000);
				//tc1.test2("BTCUSDT", "SELL", "11300.00", "0.0024", "16 medium qty sell");//
				//Thread.sleep(3000);
				
				tc1.test3("BTCUSDT", "SELL", "9000.00", "0.0012", " 1cancel ");
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "BUY", "15000.00", "0.0012", " 2cancel");
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "SELL", "9500.00", "0.0014", " 3cancel");
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSD", "SELL", "14500.00", "0.0019", " 4cancel");
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSD", "SELL", "14500.00", "0.0011", "5 cancel");//  
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSD", "BUY", "14400.00", "0.0015", "6 cancel");//  
				//Thread.sleep(13000);                                                           
				                                                                               
				tc1.test3("BTCUSD", "BUY", "14003.00", "0.00145", "7cancel ");//p
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSD", "BUY", "14000.00", "0.0010", "8cancel");//
				//Thread.sleep(13000);                                                           
				                                                                               
				tc1.test3("BTCUSD", "SELL", "9070.00", "0.0010", " 9cancel");//failed in
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "BUY", "14800.00", "0.0041", " 10cancel ");//failed in
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "BUY", "14300.00", "0.004010", " 11cancel");//fai
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "BUY", "14200.00", "0.00810", " 12 cancel");//fai
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "BUY", "14000.00", "0.001", "13 cancel");//pass canc
				//Thread.sleep(13000);                                                           
				tc1.test3("LTCBTC", "SELL", "10070.00", "0.0003", "14cancel ");
				//Thread.sleep(13000);                                                           
				tc1.test3("BTCUSDT", "SELL", "11000.00", "0.0014", "15 cancel");//pas
				//Thread.sleep(13000);                                                          
				
				tc1.test3("BTCUSDT", "BUY", "14250.00", "0.0024", "16 cancel");//
				//Thread.sleep(3000);
		tc1.test3("BTCUSDT", "BUY", "1300.00", "0.0024", "17 cancel");//
				//Thread.sleep(3000);
			
			} catch (Exception e) {
				
				//PrintFile.printReport(kc);
				e.printStackTrace();
			}
			

		}

	}
}
