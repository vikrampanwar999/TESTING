package com.example.websocket.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.conf.KafkaConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumer {
	private   ExecutionReport er;
	private 	Order order;
	
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
		
		
	}
	private  synchronized void Orderconsumer(ConsumerRecord<String, String> cr)  {
		System.out.println("here is message of order consumer "+cr.value());
		String er=cr.value();
		ObjectMapper mapper=new ObjectMapper();
		try {
			Order order= mapper.readValue(er, Order.class);
			setOrder(order);
			System.out.println("-- order is place on the venu"+order.getMarketExchange()+"  --------------------------------------+++++++++++++++++++++++++++++++++++++++++++"+order.getOrderStatus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void setOrder(Order order) {
		this.order=order;
		// TODO Auto-generated method stub
		
	}
	public Order getOrder() {
		// TODO Auto-generated method stub
		return this.order;
		
	}
	public KafkaConsumer(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		KafkaConsumerEr( kafkaConfig);
		KafkaConsumerOv( kafkaConfig);
	}
	
	public void KafkaConsumerEr(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		
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
public void KafkaConsumerOv(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		List<String> venus= new ArrayList<>();
		ConsumerFactory<String, String> consumerFactory = kafkaConfig.consumerFactory();
		String[] suffix=kafkaConfig.getorderTopicSuffix().split(",");
		for(int i=0;i<suffix.length;i++) {
		 venus.add(kafkaConfig.getProducerTopicPrefix()+"."+suffix[i].trim());
		}
		//now i have to pass venus list in container props how??
		String[] orderVenus=venus.stream().toArray(String[]::new);
		System.out.println("orderVenus : ");
		Stream.of(orderVenus).forEach(i->System.out.print(i+" "));
		ContainerProperties containerProperties = new ContainerProperties(orderVenus);
		containerProperties.setMessageListener(
                (MessageListener<String, String>) record -> {
                		this.Orderconsumer(record);
                	}); 
		ConcurrentMessageListenerContainer<String, String> container =
		        new ConcurrentMessageListenerContainer<>(
		                consumerFactory,
		                containerProperties);
		container.start();
	}
	
	

}
