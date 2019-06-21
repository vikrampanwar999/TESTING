package com.example.websocket.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.OrderTransaction;
import com.example.websocket.conf.KafkaConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumer {
	private   ExecutionReport er;
	private 	Order order;
	private OrderTransaction ot;
	public static HashMap<String,List<Order>>ordermap=new HashMap<>() ;
	public static HashMap<String,List<String>>transactionmap=new HashMap<>() ;
	//private KafkaTemplate<String, String> kt;
	//private ObjectMapper om=new ObjectMapper();
	
	
		
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
	
	private  synchronized void transactionconsumer(ConsumerRecord<String, String> cr)  {
		System.out.println("here is message of transaction *****************************************************************************"+cr.value());
		String tr=cr.value();
		ObjectMapper mapper=new ObjectMapper();
		try {
			OrderTransaction report= mapper.readValue(tr, OrderTransaction.class);
			if(!transactionmap.containsKey(report.getOrderId()))
			transactionmap.put(report.getOrderId(),new ArrayList<String>());
			
			transactionmap.get(report.getOrderId()).add(report.getStatus().toString());
			
			
			
			if(this.getOt()!=null&&this.getOt().getOrderId()!=null&&this.getOt().getOrderId().equals(report.getOrderId())&&(this.getOt().getStatus().equals("PENDING_SUBMIT")||this.getOt().getStatus().equals("FILLED"))) {
				System.out.println("BUG : giving PENDING_SUBMIT or FILLED for a single order multiple times");
			}
			setOt(report);
			System.out.println("here is the order id from transaction consumer  "+report.getOrderId()+"  here is the stauts of this order "+report.getStatus());
			
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
			if(!ordermap.containsKey(order.getOrderId()))
			ordermap.put(order.getOrderId(),new ArrayList<Order>());
			
				ordermap.get(order.getOrderId()).add(order);
			
			setOrder(order);
			System.out.println("-- order is place on the venu"+order.getMarketExchange()+"  --------------------------------------+++++++++++++++++++++++++++++++++++++++++++ here is the status "+order.getOrderStatus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private  synchronized void Sorconsumer(ConsumerRecord<String, String> cr)  {
		System.out.println("here is message of Sor consumer "+cr.value());
		String er=cr.value();
		System.out.println(er);
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
		String[] topics=getListTopics(kafkaConfig);
		ContainerProperties containerProperties = new ContainerProperties(topics);
		containerProperties.setMessageListener(
                (MessageListener<String, String>) record -> {
                		System.out.println("recieved Records "+record);
                		if(record.topic().equals(kafkaConfig.getOrderTransaction())) {
                			//need to invoke diffrent methods based on this
                		}
                	}); 
		ConcurrentMessageListenerContainer<String, String> container =
		        new ConcurrentMessageListenerContainer<>(
		                kafkaConfig.consumerFactory(),
		                containerProperties);
		container.start();
	}
	

	
	private String[] getListTopics(KafkaConfig kafkaConfig) {
		
		String csv =kafkaConfig.getOrInternal()+","+kafkaConfig.getExeInternal()+","+kafkaConfig.getOrderTransaction()+","+kafkaConfig.getConsumerTopicPrefix();
		List<String> venus= new ArrayList<>();
		String[] suffix=kafkaConfig.getorderTopicSuffix().split(",");
		for(int i=0;i<suffix.length;i++) {
		 venus.add(kafkaConfig.getProducerTopicPrefix()+"."+suffix[i].trim());
		}
		
		String orderVenus=venus.stream().collect(Collectors.joining(","));
		csv=csv+","+orderVenus;
		System.out.println("here is csv "+csv);
		return csv.split(",");
	}

	public void KafkaConsumer(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		
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


public OrderTransaction getOt() {
	return ot;
}

public void setOt(OrderTransaction ot) {
	this.ot = ot;
}



	
	

}
