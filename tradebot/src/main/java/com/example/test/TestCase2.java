package com.example.test;



import java.io.IOException;
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TestCase2 {
	@Autowired
	KafkaConfig kafkaConfig;
	public  Result PostReq() throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://54.145.144.54:20004/exchange/openAPI?signature=12345");
	    String json="{\n" + 
	    		"	\"method\":\"exchange.insertOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"a\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": \""+UUID.randomUUID()+"\", \n" + 
	    		"			\"symbol\": \"BTCUSDT\", \n" + 
	    		"			\"orderType\": \"LIMIT\", \n" + 
	    		"			\"limitPrice\": 6940.32,\n" + 
	    		"			\"orderQuantity\": 0.001,\n" + 
	    		"			\"orderSide\": \"BUY\"\n" + 
	    		"		}]\n" + 
	    		"}";
	   
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    
	    System.out.println("here is the response ");
	   String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   System.out.println(jsonobj);
	   ObjectMapper mapper=new ObjectMapper();
	   mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	   ResultWrapper result= mapper.readValue(jsonobj, ResultWrapper.class);
		client.close();
		return result.getResult();
	   

}
	public void test() throws IOException, ClassNotFoundException, InterruptedException {
		KafkaConsumer kc= new KafkaConsumer(kafkaConfig);
		TestCase2 ort=new TestCase2();
		Result result=ort.PostReq();
		System.out.println(result.getStatus());

		
		Thread.sleep(22000);
		ExecutionReport er=kc.getEr();
		/*\"orderId\": \""+UUID.randomUUID()+"\", \n" + 
		"			\"symbol\": \"BTCUSDT\", \n" + 
		"			\"orderType\": \"LIMIT\", \n" + 
		"			\"limitPrice\": 6940.32,\n" + 
		"			\"orderQuantity\": 0.001,\n" + ORDER_REJECT_REPORT
		"			\"orderSide\": \"BUY\"\n" + */
		assertThat(er.getOrderStatus()).isEqualTo("REJECTED");
		assertThat(er.getOrderId()).isEqualTo(result.getOrderId());
		
		assertThat(result).extracting("orderId", "symbol", "orderType","limitPrice","quantity","orderSide")
        .doesNotContainNull()
        .containsExactly(er.getOrderId(), er.getSymbol(),er.getType());
		
		if(result.getOrderId().equals(er.getOrderId())) {
			System.out.println("order matched");
		}
		else {
			System.out.println("order id not matched");
			System.out.println(result.getOrderId());
			System.out.println(er.getOrderId());
		}
	}

}
