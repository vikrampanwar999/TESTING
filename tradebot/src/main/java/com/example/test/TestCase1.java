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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
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
		@Rule		
	    public ErrorCollector collector = new ErrorCollector();
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
			else{
				if(order!=null && er!=null) {
					try {
				assertThat(order.getOrderId().equals(er.getOrderId()));
				assertThat(er.getSymbol()).isEqualTo(symbol);
				
				assertThat(order.getSide().equals(er.getSide()));
				
				BigDecimal totalprice=tu.totalPrice(TestCase1.map, order);
				BigDecimal totalqty=tu.totalPrice(TestCase1.map, order);
				
				if(er.getExecutedQty()!=null)
				assertThat(totalqty).isLessThanOrEqualTo(er.getExecutedQty());
				if(er.getExecutedPrice()!=null)
				assertThat(totalprice).isLessThanOrEqualTo(er.getExecutedPrice());
				if(er.getQty()!=null&&order.getQty()!=null)
				assertThat(er.getQty()).isLessThanOrEqualTo(order.getQty());

					}
					catch (Throwable t) {					
			            collector.addError(t);}	
				}}}
		
		System.out.println("below is the order values : ");
		System.out.println(order);
		System.out.println("below is the corresponding execution report value");
		System.out.println(er);
	}
	
	

}
