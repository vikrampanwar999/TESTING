package com.example.test;



import java.io.Console;
import java.io.IOException;
import java.math.BigDecimal;
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
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	//@Autowired KafkaConsumer kafkaConsumer;
	public void setKafkaConsumer() throws ClassNotFoundException {
		this.kc=new KafkaConsumer(this.kafkaConfig);
	}
	
	public  Result PostReq(String symbol,String orderSide,String limitPrice,String orderqty) throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://54.145.144.54:20004/exchange/openAPI?signature=12345");
	    String json="{\n" + 
	    		"	\"method\":\"exchange.insertOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"a\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": \""+UUID.randomUUID()+"\", \n" + 
	    		"			\"symbol\":\""+symbol+ "\" , \n" + 
	    		"			\"orderType\": \"LIMIT\", \n" + 
	    		"			\"limitPrice\":"+limitPrice+",\n" + 
	    /*		"           \"limitPrice\": 69400.32,\n" + */
	    		"			\"orderQuantity\":"+ orderqty+",\n" + 
	    		"			\"orderSide\":\""+orderSide+"\"\n"+
	/*    		"			\"orderSide\": \"BUY\"\n" +*/
	    		
	    		"		}]\n" + 
	    		"}";
	   
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    
	    System.out.println("here is the response of post request ");
	   String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   System.out.println(jsonobj);
	   ObjectMapper mapper=new ObjectMapper();
	   mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	   ResultWrapper result= mapper.readValue(jsonobj, ResultWrapper.class);
		client.close();
		return result.getResult();
	   

}
	
	public void test(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, ClassNotFoundException, InterruptedException {
		
		System.out.println("this is test case "+index+"_______________________________________________________________________________________________");
		TestCase1 ort=new TestCase1();
		Result result=ort.PostReq( symbol, orderSide, limitPrice, orderqty);
		System.out.println("here is the converted result of post request\n "+result);

		Thread.sleep(9000);
		ExecutionReport er=kc.getEr();
		
		System.out.println("here is the execution report "+er);
		//symbol quantity price orderid
		
		if("Unknown error".equals(result.getReason())||er.getSymbol()==null) {
			System.out.println("execution report is not generated");
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
