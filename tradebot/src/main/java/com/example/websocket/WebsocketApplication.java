package com.example.websocket;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.test.Stcwrapper;
import com.example.test.TestCases;
import com.example.websocket.conf.KafkaConfig;

@SpringBootApplication
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.repository")
@ComponentScan({ "com.example" })
public class WebsocketApplication implements CommandLineRunner {
	@Autowired
	TestCases tcases;
	@Autowired
	Stcwrapper t;
	// @Autowired KafkaClientTest kct;
	// new changes
	@Autowired
	KafkaConfig kc;

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(WebsocketApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		long a=System.currentTimeMillis();
		// org.junit.runner.JUnitCore.main(t.runtcs("BTCUSDT","BUY","8600.00","0.601",1));
		// t.runtcs(symbol, orderSide, limitPrice, orderqty, index);
    	PrintStream out = new PrintStream(new FileOutputStream("D:\\TestReports2\\prod\\consols\\output.txt", false), true);
		System.setOut(out);
		tcases.test(kc); // uncomment this to run the test cases
		// kct.setKafkaConsumer();
		//PrintFile.printReport(kc);
		long b=System.currentTimeMillis();
		b=b-a;
		long c=TimeUnit.MILLISECONDS.toSeconds(b);
		long d=TimeUnit.MILLISECONDS.toMinutes(b);
		System.out.println("total time taken in seconds "+c);//40 seconds for 16 orders
		System.out.println("total time taken in Minutes "+d);//13 min for 150*16 orders
		

	}
}
