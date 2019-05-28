package com.example.test;



import java.io.Console;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import static org.assertj.core.api.Assertions.*;
import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.Result;
import com.example.websocket.bean.ResultWrapper;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;
import com.fasterxml.jackson.databind.DeserializationFeature;//\"symbol\": \"BTCUSDT\", \n" + 
import com.fasterxml.jackson.databind.ObjectMapper;//"BTCUSDT\"

@Component
public class TestCase1 {
	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	public static HashMap<String,List<Order>>map=new HashMap<>() ;
	public void setKafkaConsumer() throws ClassNotFoundException {
		this.kc=new KafkaConsumer(this.kafkaConfig);
	}
	

	
	public void test(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, ClassNotFoundException, InterruptedException {
		
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		//TestCase1 ort=new TestCase1();
		TestUtil tu=new TestUtil();
		Result result=tu.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);

		Thread.sleep(9000);
		ExecutionReport er=kc.getEr();
		
		System.out.println("here is the execution report "+er);
		//symbol quantity price orderid
		
		if("Unknown error".equals(result.getReason()))
			System.out.println("order is not placed on any venu for Unknown error");
			
		else {
			if(er.getSymbol()==null&& !"Unknown error".equals(result.getReason())) {
				System.out.println("order placed but order symbol is not seen in the execution report and this will show nothing on info.html page except order id");
		}
			
		
		else {
		assertThat(result.getOrderId().equals(er.getOrderId()));
		assertThat(er.getSymbol()).isEqualTo(symbol);
		assertThat(orderSide.equals(er.getSide()));
		if(orderSide.equalsIgnoreCase("SELL"))
		assertThat(er.getPrice()).isGreaterThanOrEqualTo(new BigDecimal(limitPrice));
		if(orderSide.equalsIgnoreCase("BUY"))
		assertThat(er.getPrice()).isLessThanOrEqualTo(new BigDecimal(limitPrice));}
		
		
		
		}
	}
	public void test2(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		//TestCase1 ort=new TestCase1();
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
			else{
				if(order!=null && er!=null) {
				assertThat(order.getOrderId().equals(er.getOrderId()));
				assertThat(er.getSymbol()).isEqualTo(symbol);
				
				assertThat(order.getSide().equals(er.getSide()));
				
				BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
				BigDecimal totalqty=tu.totalPrice(TestCase1.map, order);
				
			
				assertThat(totalqty).isLessThanOrEqualTo(er.getExecutedQty());
				assertThat(totalprice).isLessThanOrEqualTo(er.getExecutedQty());
				
				assertThat(er.getQty()).isLessThanOrEqualTo(order.getQty());

				}}}
		System.out.println("below is the order values : ");
		System.out.println(order);
		System.out.println("below is the corresponding execution report value");
		System.out.println(er);
	}
	
	

}
