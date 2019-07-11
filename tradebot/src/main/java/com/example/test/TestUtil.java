package com.example.test;

import java.io.FileWriter;
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
import org.springframework.util.StreamUtils;

import com.example.websocket.bean.Order;
import com.example.websocket.bean.Result;
import com.example.websocket.bean.ResultWrapper;
import com.example.websocket.util.PrintFile;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

	
	
	
	public BigDecimal totalQty(HashMap<String,List<Order>>map,Order order) {
		BigDecimal qty=new BigDecimal(0);
		if(map.containsKey(order.getOrderId())) {
			List<Order>list=map.get(order.getOrderId());
			for(Order o:list) {
				if(o.getExecutedQty()!=null)
				//qty=qty.add(o.getExecutedQty());
					qty=qty.add(o.getLeavesQty());
			}
			
			return qty;
		}
		else {//though it shouldn't get executed
			return order.getExecutedQty();
		}
		
	
}
	public BigDecimal totalPrice(HashMap<String,List<Order>>map,Order order) {
		BigDecimal price=new BigDecimal(0);
		if(map.containsKey(order.getOrderId())) {
			List<Order>list=map.get(order.getOrderId());
			for(Order o:list) {
				if(o.getExecutedPrice()!=null)
				price=price.add(o.getExecutedPrice());
			}
			return price;
		}
		else {//though it shouldn't get executed
			return order.getExecutedPrice();
		}
		
	
}
	
	public  Result PostReq(String symbol,String orderSide,String limitPrice,String orderqty) throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	  //  HttpPost httpPost = new HttpPost("http://3.93.103.201:20006/exchange/openAPI?signature=12345");
	    /*prod*/   HttpPost httpPost = new HttpPost("http://52.68.13.17:22011/exchange/openAPI?signature=12345");
	    String id=symbol+":"+UUID.randomUUID();
	    String json="{\n" + 
	    		"	\"method\":\"exchange.insertOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"A\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": \""+id+"\", \n" + 
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
	
	public static void PostReqCancel(String orderid) throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	   // HttpPost httpPost = new HttpPost("http://3.93.103.201:20006/exchange/openAPI?signature=12345");
	    /*prod*/   HttpPost httpPost = new HttpPost("http://52.68.13.17:22011/exchange/openAPI?signature=12345");
	    String req="{\n" + 
	    		"	\"method\":\"exchange.cancelOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"A\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": "+"\""+orderid+"\""+"\n" + 
	    		"			\n" + 
	    		"		}]\n" + 
	    		"}";
	   // System.out.println("cancel request : "+req);
	   
	    StringEntity entity = new StringEntity(req);
	    httpPost.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	    String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
		  // System.out.println(jsonobj);
	    String fileOT = PrintFile.path+"cancelled.txt";
		FileWriter fw = new FileWriter(fileOT, true);
		fw.append(jsonobj);
		fw.append("\n");
		fw.close();
	    
	    System.out.println(orderid+" cancel request "+jsonobj);
	   /*String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   System.out.println(jsonobj);
	   ObjectMapper mapper=new ObjectMapper();
	   mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	   ResultWrapper result= mapper.readValue(jsonobj, ResultWrapper.class);
		client.close();
		return result.getResult();*/
	   

}
	public  Result PostReqWithCancel(String symbol,String orderSide,String limitPrice,String orderqty) throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	/*dev*/ //  HttpPost httpPost = new HttpPost("http://3.93.103.201:20006/exchange/openAPI?signature=12345");
	    /*prod*/   HttpPost httpPost = new HttpPost("http://52.68.13.17:22011/exchange/openAPI?signature=12345");
	    String id=symbol+":"+UUID.randomUUID();
	    String json="{\n" + 
	    		"	\"method\":\"exchange.insertOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"A\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": \""+id+"\", \n" + 
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
	    PostReqCancel(id);
	    
	    System.out.println("here is the response of post request ");
	   String jsonobj= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   System.out.println(jsonobj);
		
	   ObjectMapper mapper=new ObjectMapper();
	   mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	   ResultWrapper result= mapper.readValue(jsonobj, ResultWrapper.class);
		client.close();
		return result.getResult();
	   

}

}
