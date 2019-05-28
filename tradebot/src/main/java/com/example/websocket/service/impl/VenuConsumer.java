package com.example.websocket.service.impl;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.example.test.OrderRouterTest;
import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.ResultWrapper;
import com.example.websocket.conf.KafkaConfig;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VenuConsumer {
	private   ExecutionReport er;
	//private 	OrderRouterTest ort;
	private  synchronized void consumer(ConsumerRecord<String, String> cr)  {
		System.out.println("here is message of consumer "+cr.value());
		String er=cr.value();
		ObjectMapper mapper=new ObjectMapper();
		try {
			ExecutionReport report= mapper.readValue(er, ExecutionReport.class);
			setEr(report);
			System.out.println("here is the order status from execution report"+report.getOrderStatus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//store that object in execution report bean by orderid
		//store it in a singloneton instace and then orderid
		//execution report
		//add cr.value()
		
	}

	
	public VenuConsumer(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		/*this.ort=ort;*/
		ConsumerFactory<String, String> consumerFactory = kafkaConfig.consumerFactory();
		ContainerProperties containerProperties = new ContainerProperties(kafkaConfig.getConsumerTopicPrefix());
		containerProperties.setMessageListener(
                (MessageListener<String, String>) record -> {
                		this.consumer(record);
                	}); 
		ConcurrentMessageListenerContainer<String, String> container =
		        new ConcurrentMessageListenerContainer<>(
		                consumerFactory,
		                containerProperties);
		container.start();
	}


	public ExecutionReport getEr() {
		return er;
	}


	public void setEr(ExecutionReport er) {
		
		this.er = er;
	}
	
	

}
