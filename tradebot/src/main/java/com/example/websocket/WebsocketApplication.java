package com.example.websocket;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.test.KafkaClientTest;
import com.example.test.Stcwrapper;
import com.example.test.TestCases;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

@SpringBootApplication
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.repository")
@ComponentScan({"com.example"})
public class WebsocketApplication implements CommandLineRunner{
	@Autowired TestCases tcases;
	@Autowired Stcwrapper t;
	//@Autowired KafkaClientTest kct;
	//new changes
	@Autowired KafkaConfig kc;
	
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(WebsocketApplication.class, args);
       
        
    }
 
	@Override
	public void run(String... args) throws Exception {
		 // org.junit.runner.JUnitCore.main(t.runtcs("BTCUSDT","BUY","8600.00","0.601",1));
		//t.runtcs(symbol, orderSide, limitPrice, orderqty, index);
		tcases.test(); //uncomment this to run the test cases
		//kct.setKafkaConsumer();
		
		try {
		FileWriter fw=new FileWriter("D:\\TestReports\\"+getDate()+".txt",true);
		fw.append(getDate()+"#############################################################################\n");
		KafkaConsumer kconsumer=new KafkaConsumer(kc);
		Set<Entry<String, List<String>>> records=kconsumer.flowReport.entrySet();
		System.out.println("########################################### Total executed test cases "+records.size()+"################################################################");
		int index=1;
		for(Map.Entry<String, List<String>> record:records) {
			fw.append(index++ +") order_id :"+record.getKey()+"\n");
			fw.append(record.getValue()+"\n");
			fw.append("******************************************************************\n");
			System.out.println("order_id :"+record.getKey());
			System.out.println(record.getValue().toString());
			
		}
		fw.close();}
		catch(Exception e) {
			System.out.println("exception in writing the file ");
			e.printStackTrace();
		}
	}
	public String getDate() {
		Date date = new Date();
	    return date.toString();
	}
}
