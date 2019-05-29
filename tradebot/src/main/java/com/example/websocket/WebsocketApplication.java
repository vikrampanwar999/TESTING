package com.example.websocket;

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

import com.example.test.Stcwrapper;
import com.example.test.TestCases;

@SpringBootApplication
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.repository")
@ComponentScan({"com.example"})
public class WebsocketApplication implements CommandLineRunner{
	//@Autowired TestCases tcases;
	@Autowired Stcwrapper t;
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(WebsocketApplication.class, args);
        
        
    }
 
	@Override
	public void run(String... args) throws Exception {
		 // org.junit.runner.JUnitCore.main(t.runtcs("BTCUSDT","BUY","8600.00","0.601",1));
		//t.runtcs(symbol, orderSide, limitPrice, orderqty, index);
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","BUY","8600.00","0.601",1);//trade account balance is not enough
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","BUY","8900.00","0.001",2);//invalid-amount
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","SELL","8060.00","0.091",3);//Unknown error
		Thread.sleep(13000);
		t.runtcs("BTCUSD","SELL","8000.00","0.019",4);//CANCELLED->null reason`
		Thread.sleep(13000);
		t.runtcs("BTCUSD","BUY","8800.00","4.001",5);// Insufficient funds (HTTP status code: 400)
		Thread.sleep(13000);
		t.runtcs("BTCUSD","BUY","8070.00","0.010",6);
		
	}
}
