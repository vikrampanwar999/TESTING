package com.example.websocket;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.test.OrderRouterTest;
import com.example.test.OrderRouterTest3;
import com.example.test.TestCase1;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

@SpringBootApplication
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.repository")
@ComponentScan({"com.example"})
public class WebsocketApplication implements CommandLineRunner{
	@Autowired OrderRouterTest3 ort3;
	@Autowired TestCase1 tc1;
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(WebsocketApplication.class, args);
        
        
    }
  /*  @EventListener(ApplicationReadyEvent.class)
    public void fun() throws ClassNotFoundException, InterruptedException, IOException {
    	
    	//OrderRouterTest3 ort3=new OrderRouterTest3();
    	ort3.test();
    
    }*/
	@Override
	public void run(String... args) throws Exception {
		tc1.setKafkaConsumer();
				//ort3.test();
		Thread.sleep(13000);
		tc1.test("BTCUSDT","BUY","8600.00","0.601",1);//trade account balance is not enough
		Thread.sleep(13000);
		tc1.test("BTCUSDT","BUY","8900.00","0.001",2);//invalid-amount
		Thread.sleep(13000);
		tc1.test("BTCUSDT","SELL","8060.00","0.091",3);//Unknown error
		Thread.sleep(13000);
		tc1.test("BTCUSD","SELL","8000.00","0.019",4);//CANCELLED->null reason
		Thread.sleep(13000);
		tc1.test("BTCUSD","BUY","8800.00","4.001",5);// Insufficient funds (HTTP status code: 400)
		Thread.sleep(13000);
		tc1.test("BTCUSD","BUY","8070.00","0.010",6);//partial filled -> ORDER_EXEC_REPORT
		Thread.sleep(13000);
		tc1.test("BTCUSD","BUY","8370.00","1.010",7);
		Thread.sleep(13000);
		tc1.test("BTCUSD","BUY","8770.00","0.0020",8);
		Thread.sleep(13000);
		tc1.test("BTCUSD","SELL","8070.00","0.00010",9);
		Thread.sleep(13000);
		
	}
}
