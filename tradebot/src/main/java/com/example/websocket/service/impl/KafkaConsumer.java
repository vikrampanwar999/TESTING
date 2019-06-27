package com.example.websocket.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.OrderTransaction;
import com.example.websocket.bean.OrderTransactionWrapper;
import com.example.websocket.conf.KafkaConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumer {
	private ExecutionReport er;
	private Order order;
	private OrderTransaction ot;
	public static HashMap<String, List<Order>> ordermap = new HashMap<>();
	public static HashMap<String, List<OrderTransaction>> transactionmap = new HashMap<>();
	public static HashMap<String, List<ExecutionReport>> ExecutionReportmap = new HashMap<>();
	public static HashMap<String, List<String>> flowReport = new HashMap<>();
	// private KafkaTemplate<String, String> kt;
	// private ObjectMapper om=new ObjectMapper();

	private synchronized void OrderInternalconsumer(ConsumerRecord<String, String> cr) {
		System.out.println("here is message of consumer " + cr.value());
		String er = cr.value();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("record return from venus " + er);

		try {
			ExecutionReport report = mapper.readValue(er, ExecutionReport.class);
			setEr(report);
			if (!ExecutionReportmap.containsKey(report.getOrderId()))
				ExecutionReportmap.put(report.getOrderId(), new ArrayList<ExecutionReport>());
			// String s="DEV.E55PRIME.ORDERS.INTERNAL channel recieved order with status
			// "+report.getOrderStatus();
			ExecutionReportmap.get(report.getOrderId()).add(report);
			if (!flowReport.containsKey(report.getOrderId()))
				flowReport.put(report.getOrderId(), new ArrayList<String>());
			String s = "DEV.E55PRIME.ORDERS.INTERNAL channel recieved order with status " + report.getOrderStatus();
			flowReport.get(report.getOrderId()).add(s);
			System.out.println("here is the order status from execution report" + report.getOrderStatus());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void transactionconsumer(ConsumerRecord<String, String> cr) {
		System.out.println(
				"here is message of transaction *****************************************************************************"
						+ cr.value());
		String tr = cr.value().substring(10);
		System.out.println("tr " + tr);
		ObjectMapper mapper = new ObjectMapper();
		try {

			// OrderTransactionWrapper reportwrapper= mapper.readValue(tr,
			// OrderTransactionWrapper.class);
			OrderTransaction report = mapper.readValue(tr, OrderTransaction.class);
			System.out.println("report  at transaction channel status " + report.getStatus());

			if (!transactionmap.containsKey(report.getOrderId())) {
				transactionmap.put(report.getOrderId(), new ArrayList<OrderTransaction>());
			}
			transactionmap.get(report.getOrderId()).add(report);
			System.out.println("report  at transaction channel transformed " + report.toString());
			System.out.println("report  at transaction channel raw " + tr);
			// System.out.println("report at transaction channel raw ttr "+ttr);

			if (this.getOt() != null && this.getOt().getOrderId() != null
					&& this.getOt().getOrderId().equals(report.getOrderId())
					&& (this.getOt().getStatus().equals("PENDING_SUBMIT")
							|| this.getOt().getStatus().equals("FILLED"))) {
				System.out.println("BUG : giving PENDING_SUBMIT or FILLED for a single order multiple times");
			}
			setOt(report);
			System.out.println("here is the order id from transaction consumer  " + report.getOrderId()
					+ "  here is the stauts of this order " + report.getStatus());
			// if("PENDING_SUBMIT".equals(report.getStatus())||"SUBMITTED".equals(report.getStatus()))
			// {
			if (!flowReport.containsKey(report.getOrderId()))
				flowReport.put(report.getOrderId(), new ArrayList<String>());
			String s = "DEV.E55.ORDERTRANSACTIONS channel recieved order with status " + report.getStatus();
			flowReport.get(report.getOrderId()).add(s);
			/*
			 * } else {
			 * 
			 * }
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void Orderconsumer(ConsumerRecord<String, String> cr) {
		System.out.println("here is message of order consumer " + cr.value());
		String er = cr.value();
		ObjectMapper mapper = new ObjectMapper();
		try {
			Order order = mapper.readValue(er, Order.class);
			if (!ordermap.containsKey(order.getOrderId()))
				ordermap.put(order.getOrderId(), new ArrayList<Order>());

			ordermap.get(order.getOrderId()).add(order);

			setOrder(order);
			System.out.println("-- order is place on the venu" + order.getMarketExchange()
					+ "  --------------------------------------+++++++++++++++++++++++++++++++++++++++++++ here is the status "
					+ order.getOrderStatus());
			if (!flowReport.containsKey(order.getOrderId()))
				flowReport.put(order.getOrderId(), new ArrayList<String>());
			String s = "order has been placed on " + order.getMarketExchange();
			flowReport.get(order.getOrderId()).add(s);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void Sorconsumer(ConsumerRecord<String, String> cr) {
		System.out.println("here is message of Sor consumer " + cr.value());
		String er = cr.value();
		System.out.println(er);
		if (er != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				ExecutionReport report = mapper.readValue(er, ExecutionReport.class);
				if (!flowReport.containsKey(report.getOrderId()))
					flowReport.put(report.getOrderId(), new ArrayList<String>());
				if (report.getOrderStatus() != null) {
					String s = "SOR has sent an " + report.getOrderStatus().toString()
							+ " on ExecutionReportInternal Channel ";
					flowReport.get(report.getOrderId()).add(s);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setOrder(Order order) {
		this.order = order;
		// TODO Auto-generated method stub

	}

	public Order getOrder() {
		// TODO Auto-generated method stub
		return this.order;

	}

	public KafkaConsumer(KafkaConfig kafkaConfig) throws ClassNotFoundException {
		String[] topics = getListTopics(kafkaConfig);
		ContainerProperties containerProperties = new ContainerProperties(topics);
		containerProperties.setMessageListener((MessageListener<String, String>) record -> {
			System.out.println("recieved Records " + record);
			if (record.topic().equals(kafkaConfig.getOrderTransaction())) {
				// need to invoke diffrent methods based on this
				this.transactionconsumer(record);
			} else if (record.topic().equals(kafkaConfig.getExeInternal())) {
				// need to invoke diffrent methods based on this
				this.Sorconsumer(record);
			} else if (record.topic().equals(kafkaConfig.getOrInternal())) {
				// need to invoke diffrent methods based on this
				this.OrderInternalconsumer(record);
			} else if (record.topic().equals(kafkaConfig.getProducerTopicPrefix())) {
				// need to invoke diffrent methods based on this
				this.Orderconsumer(record);
			} else if (record.topic().startsWith(kafkaConfig.getProducerTopicPrefix())
					&& endsWithAnyOFString(record.topic(), kafkaConfig.getorderTopicSuffix().split(","))) {
				System.out.println("inside the venu");
				this.Orderconsumer(record);
			}
		});
		ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(
				kafkaConfig.consumerFactory(), containerProperties);
		container.start();
	}

	private String[] getListTopics(KafkaConfig kafkaConfig) {

		String csv = kafkaConfig.getOrInternal() + "," + kafkaConfig.getExeInternal() + ","
				+ kafkaConfig.getOrderTransaction() + "," + kafkaConfig.getConsumerTopicPrefix();
		List<String> venus = new ArrayList<>();
		String[] suffix = kafkaConfig.getorderTopicSuffix().split(",");
		for (int i = 0; i < suffix.length; i++) {
			venus.add(kafkaConfig.getProducerTopicPrefix() + "." + suffix[i].trim());
		}

		String orderVenus = venus.stream().collect(Collectors.joining(","));
		csv = csv + "," + orderVenus;
		System.out.println("here is csv " + csv);
		return csv.split(",");
	}

	public void KafkaConsumer(KafkaConfig kafkaConfig) throws ClassNotFoundException {

		ConsumerFactory<String, String> consumerFactory = kafkaConfig.consumerFactory();
		ContainerProperties containerProperties = new ContainerProperties(kafkaConfig.getConsumerTopicPrefix());
		containerProperties.setMessageListener((MessageListener<String, String>) record -> {
			// this.OrderInternalconsumer(record);
		});
		ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(
				consumerFactory, containerProperties);
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

	private boolean endsWithAnyOFString(String s, String arr[]) {
		for (String o : arr) {
			if (s.contains(o))
				return true;
		}
		return false;
	}

}
