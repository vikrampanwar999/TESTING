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

@Component
public class TestCases {
	@Autowired
	TestCase1 tc1;

	public void test() throws ClassNotFoundException, InterruptedException, IOException {
		tc1.setKafkaConsumer();
		Thread.sleep(5000);
		Thread.sleep(13000);

		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<?>> lf=new ArrayList<Future<?>>();
		for (int i = 0; i <5; i++) {
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
		//executor.awaitTermination(45,TimeUnit.MINUTES);
	}

	private class testcaseRunner implements Runnable {

		@Override
		public void run() {
			try {
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.0007801", 1);// trade account balance is not enough
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "15000.00", "0.000087651", 2);// invalid-amount
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "9500.00", "0.00000091", 3);// Unknown error
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "SELL", "14500.00", "0.019", 4);// CANCELLED->null reason`
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "BUY", "14500.00", "0.000031", 5);// Insufficient funds (HTTP status code: 400)
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "BUY", "14400.00", "0.01000009", 6);// partial filled -> ORDER_EXEC_REPORT
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "BUY", "14003.00", "0.0000345", 7);
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "BUY", "14000.00", "0.00000020", 8);
				Thread.sleep(13000);
				tc1.test2("BTCUSD", "SELL", "9070.00", "0.000000010", 9);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14800.00", "0.0000041", 10);// Insufficient funds (HTTP status code: 400)
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14300.00", "0.0004010", 11);// partial filled -> ORDER_EXEC_REPORT
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14200.00", "0.0000810", 12);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "14000.00", "0.015", 13);
				Thread.sleep(13000);
				tc1.test2("LTCBTC", "SELL", "10070.00", "0.00000010", 14);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "15000.00", "0.001000090", 15);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "15000.00", "0.00000165", 16);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "BUY", "15500.00", "0.00001230", 17);
				Thread.sleep(13000);
				tc1.test2("BTCUSDT", "SELL", "12500.00", "0.00900015", 18);
				Thread.sleep(13000);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
