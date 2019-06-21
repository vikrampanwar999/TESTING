package com.example.websocket.quartz;


import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.websocket.conf.OrderConfiguration;
import com.example.websocket.stomp.StompClient;





@Component  
public class Scheduler {  
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  

    @Autowired private OrderConfiguration orderConfiguration;
     
    
    @Scheduled(fixedRate=30000) 
    public void autoConn() throws InterruptedException {   
   
   
   	System.out.println(orderConfiguration.getBuy_url());
	System.out.println(orderConfiguration.getSymbol());
    	 StompClient s =new StompClient();
    	// String url = "http://52.68.13.17:8090/xchange/marketdata";
    	 String url = "http://52.68.13.17:8092/xchange/marketdata";
   	   //  s.connect(url,orderConfiguration);
   	}
 
 }    
