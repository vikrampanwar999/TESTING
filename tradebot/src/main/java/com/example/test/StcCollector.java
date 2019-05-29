package com.example.test;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.Result;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

public class StcCollector {

	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	public static HashMap<String,List<Order>>map=new HashMap<>() ;
	@Rule
    public ErrorCollector collector = new ErrorCollector();
	
	
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
		collector.checkThat(order.getSymbol(),equals("b"));
		collector.checkThat(order.getSymbol()).isEqualTo(symbol);
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
		collector.checkThat(er.getOrderId()).isEqualTo(order.getOrderId());
		//collector.checkThat(order.getSymbol()).isEqualTo(symbol);
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
		collector.checkThat(er.getSide()).isEqualTo(order.getSide().toString()); 
		//collector.checkThat(order.getSymbol()).isEqualTo(symbol);
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
		collector.checkThat(er.getExecutedQty()).isEqualTo(order.getExecutedQty()); 
		//collector.checkThat(order.getSymbol()).isEqualTo(symbol);
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
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
		//BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
		BigDecimal totalqty=tu.totalQty(TestCase1.map, order);
		collector.checkThat(totalqty).isLessThanOrEqualTo(er.getQty());
		//collector.checkThat(er.getQty()).isEqualTo(order.getQty()); 
		//collector.checkThat(order.getSymbol()).isEqualTo(symbol);
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
		BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
		collector.checkThat(totalprice).isLessThanOrEqualTo(er.getQty());
		kc.setEr(null);
		kc.setOrder(null);
	}
}

