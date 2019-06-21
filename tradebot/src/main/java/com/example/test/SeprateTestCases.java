package com.example.test;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.Result;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;
@Component
public class SeprateTestCases {
	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	
	
	public void setKafkaConsumer() throws ClassNotFoundException {
		this.kc=new KafkaConsumer(this.kafkaConfig);
	}
	
	@Test
	public void validateSymbol(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		assertThat(er.getSymbol()).isEqualTo(symbol);
		assertThat(order.getSymbol()).isEqualTo(symbol);
		kc.setEr(null);
		kc.setOrder(null);
	}
	@Test
	public void validateOrderId(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		assertThat(er.getOrderId()).isEqualTo(order.getOrderId());
		//assertThat(order.getSymbol()).isEqualTo(symbol);
		kc.setEr(null);
		kc.setOrder(null);
	}
	@Test
	public void validateOrderSide(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		assertThat(er.getSide()).isEqualTo(order.getSide().toString()); 
		//assertThat(order.getSymbol()).isEqualTo(symbol);
		kc.setEr(null);
		kc.setOrder(null);
	}
	@Test
	public void validateExecutedQty(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		assertThat(er.getExecutedQty()).isEqualTo(order.getExecutedQty()); 
		//assertThat(order.getSymbol()).isEqualTo(symbol);
		kc.setEr(null);
		kc.setOrder(null);
	}
	@Test
	public void validateQty(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();//use last filled qty and last price
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		//BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
		BigDecimal totalqty=tu.totalQty(KafkaConsumer.ordermap, order);
		assertThat(totalqty).isLessThanOrEqualTo(er.getQty());
		//assertThat(er.getQty()).isEqualTo(order.getQty()); 
		//assertThat(order.getSymbol()).isEqualTo(symbol);
		kc.setEr(null);
		kc.setOrder(null);
	}
	@Test
	public void validatePrice(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		BigDecimal totalprice=tu.totalPrice(KafkaConsumer.ordermap, order);
		assertThat(totalprice).isLessThanOrEqualTo(er.getQty());
		kc.setEr(null);
		kc.setOrder(null);
	}
}
