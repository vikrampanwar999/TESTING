package com.example.test;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.bean.Order;
import com.example.websocket.bean.OrderSide;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class KafkaClientTest {
	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	
	
	private void setKafkaConsumer() throws ClassNotFoundException, JsonProcessingException {
		this.kc=new KafkaConsumer(this.kafkaConfig);
		String topic="DEV.E55PRIME.ORDERS.COINBASEPRO";
		String symbol="BTCUSD";
		Order order= new Order();
		order.setChildOrderId(UUID.randomUUID().toString());
		order.setOrderId(symbol+":"+UUID.randomUUID().toString());
		order.setMarketExchange(com.example.websocket.bean.MarketExchangeEnum.COINBASEPRO);
		order.setSymbol(symbol);
		order.setPrice(new BigDecimal(9000));
		order.setSide(OrderSide.BUY);
		order.setQty(new BigDecimal(.10));
		//kc.sendkafka(topic, order);
	}
}
