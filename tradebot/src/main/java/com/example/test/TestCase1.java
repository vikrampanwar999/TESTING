package com.example.test;



import static org.assertj.core.api.Assertions.assertThat;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.Result;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

@Component
public class TestCase1 {
	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	public static HashMap<String,List<Order>>map=new HashMap<>() ;
	
	public void setKafkaConsumer() throws ClassNotFoundException {
		this.kc=new KafkaConsumer(this.kafkaConfig);
	}
	
	@Test
	public void test2(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
	
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);
		Thread.sleep(9000);
		Order order=kc.getOrder();
		ExecutionReport er=kc.getEr();
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
			
		else {
			if(er!=null&&result!=null&&er.getSymbol()==null&& !"Unknown error".equals(result.getReason())) {
				System.out.println("order placed but order symbol is not seen in the execution report and this will show nothing on info.html page except order id");
					}
			else{Object a=null;
			try {
				assertThat(er).isNotNull();}
			catch(AssertionError e) {
				System.out.println("AssertionError occured ... execution report is null "+e.toString());
			}try {
				assertThat(a).isNotNull();}
			catch(AssertionError e) {
				System.out.println("AssertionError occured ... object report is null");
			}
			try {
				assertThat(order).isNotNull();}
			catch(AssertionError e) {
				System.out.println("AssertionError occured ... order we are processing  is null");
			}
				if(order!=null && er!=null) {
					try {
				assertThat(order.getOrderId().equals(er.getOrderId()));}
					catch(AssertionError e) {
						System.out.println("AssertionError occured ... order ids does'nt matched");
					}
					try {
				assertThat(er.getSymbol()).isEqualTo(symbol);}
					catch(AssertionError e) {
						System.out.println("AssertionError occured ...  symbol does'nt matched");
					}
				try {
				assertThat(order.getSide().toString().equals(er.getSide()));}
				catch(AssertionError e) {
					System.out.println("AssertionError occured ...  order side does'nt matched");
				}
				
				BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
				BigDecimal totalqty=tu.totalQty(TestCase1.map, order);
				/*assertThat(er.getExecutedQty()).isNotNull();
				assertThat(er.getExecutedPrice()).isNotNull();
				if(er.getExecutedQty()!=null)*/
				try {
					if(totalqty!=null)
				assertThat(totalqty).isLessThanOrEqualTo(er.getExecutedQty());}
				catch(AssertionError e) {
					System.out.println("AssertionError occured ...  totalqty and executed qty does'nt matched");
				}
				//if(er.getExecutedPrice()!=null)
				try {
				assertThat(totalprice).isLessThanOrEqualTo(er.getExecutedPrice());}
				catch(AssertionError e) {
					System.out.println("AssertionError occured ...  total price and executed price does'nt matched");
				}
				//if(er.getQty()!=null&&order.getQty()!=null)
				try {
				assertThat(er.getQty()).isLessThanOrEqualTo(order.getQty());}
				catch(AssertionError e) {
					System.out.println("AssertionError occured ...  total qty ordered and total qty reported does'nt matched");
				}

					}
	
				}}}
		
		/*System.out.println("below is the order values : ");
		System.out.println(order);
		System.out.println("below is the corresponding execution report value");
		System.out.println(er);*/
	}
	
	


