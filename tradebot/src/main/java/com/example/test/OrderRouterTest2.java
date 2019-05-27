package com.example.test;

import java.io.IOException;
import java.io.StringReader;
import java.util.UUID;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.StreamUtils;

import com.example.websocket.bean.OrderLogBack;
import com.example.websocket.bean.Result;
import com.example.websocket.bean.ResultWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class OrderRouterTest2 {
	
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
	    		"			\"orderSide\": \"SELL\"\n" + 
	    		"		}]\n" + 
	    		"}";
	   
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    
	    System.out.println("here is the response ");
	   String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   System.out.println(jsonobj);
	  /* 	Gson gson = new Gson();
		JsonReader reader = new JsonReader(new StringReader(jsonobj));
		reader.setLenient(true);

		Result result = gson.fromJson(jsonobj, Result.class);*/
	   ObjectMapper mapper=new ObjectMapper();
	   mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

	  ResultWrapper result= mapper.readValue(jsonobj, ResultWrapper.class);
		client.close();
		return result.getResult();
	   

}
/*	public static void main(String args[]) throws IOException {
		OrderRouterTest2 test=new OrderRouterTest2();
		Result result=test.PostReq();
		System.out.println(result.getOrders().get(0).getSymbol());
		
		
	}*/
}
