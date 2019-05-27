package com.example.test;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Result;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;
@Component
public class OrderRouterTest3 {
	@Autowired
	 KafkaConfig kafkaConfig;

	public void test() throws IOException, ClassNotFoundException, InterruptedException {
		KafkaConsumer kc= new KafkaConsumer(kafkaConfig);
		OrderRouterTest2 ort=new OrderRouterTest2();
		Result result=ort.PostReq();
		System.out.println(result.getOrderStatus());

		
		Thread.sleep(15000);
		ExecutionReport er=kc.getEr();
		if(result.getOrderId().equals(er.getOrderId())) {
			System.out.println("order matched");
		}
		else {
			System.out.println("order id not matched");
			System.out.println(result.getOrderId());
			System.out.println(er.getOrderId());
		}
	}
	
}
