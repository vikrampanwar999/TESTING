package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.example.test.TestCase1;
import com.example.test.TestCases;

@SpringBootApplication
@EnableScheduling
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.repository")
@ComponentScan({"com.example"})
public class WebsocketApplication implements CommandLineRunner{
	@Autowired TestCases tcases;
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(WebsocketApplication.class, args);
        
        
    }
 
	@Override
	public void run(String... args) throws Exception {
		
		tcases.test();
	}
}
