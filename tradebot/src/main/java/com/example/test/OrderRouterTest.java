package com.example.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

@Configuration
@Component
public class OrderRouterTest {
	//when orderid is same it's showing unknown
	//ordrs volume should be same as requested
	//limitprice should be less than reques limit price vwap algo depends on buy or sell
	private String status;
	private String reason;
	private JsonArray orders;
	private String orderId;
	private String orderStatus;
	private String createdAt;
	private String symbol;
	private String orderType;
	private String orderSide;
	private String quantity;
	private String limitPrice;
	private String product;
	private ArrayList arrlist;
	

	
	public  void PostReq() throws  IOException {
	    CloseableHttpClient client = HttpClients.createDefault();
	    HttpPost httpPost = new HttpPost("http://54.145.144.54:20004/exchange/openAPI?signature=12345");
	    String json="{\n" + 
	    		"	\"method\":\"exchange.insertOrder\",\n" + 
	    		"	\"params\":[\n" + 
	    		"		\"a\", \n" + 
	    		"		{\n" + 
	    		"			\"orderId\": \"0191100224\", \n" + 
	    		"			\"symbol\": \"BTCUSD\", \n" + 
	    		"			\"orderType\": \"LIMIT\", \n" + 
	    		"			\"limitPrice\": 6940.32,\n" + 
	    		"			\"orderQuantity\": 0.0001,\n" + 
	    		"			\"orderSide\": \"SELL\"\n" + 
	    		"		}]\n" + 
	    		"}";
	   
	    StringEntity entity = new StringEntity(json);
	    httpPost.setEntity(entity);
	  //  httpPost.setHeader("Accept", "application/json");
	  //  httpPost.setHeader("Content-type", "application/json");
	    
	    CloseableHttpResponse response = client.execute(httpPost);
	   
	    //arrlist//add the object in the list once it is recieved
	    
	    System.out.println("here is the response ");
	   String result= StreamUtils.copyToString(response.getEntity().getContent(), Charsets.UTF_8);
	   // assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
	   System.out.println(result);
	   
	   String yourJson = result.substring(10, result.length()-1);
	   System.out.println("-----"+yourJson+"---------");
	  JsonParser parser = new JsonParser();
	   JsonElement element = parser.parse(yourJson);
	   JsonObject obj = element.getAsJsonObject(); //since you know it's a JsonObject
	   Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
	   for (Map.Entry<String, JsonElement> entry: entries) {
		  // System.out.println("-----------------------------------------------------");
	       System.out.println(entry.getKey());
	       if(entry.getKey().equals("status")) {
	    	  status=entry.getValue().getAsString();
	    	  
	       }
	       else if(entry.getKey().equals("reason")) {
		    	 // reason=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("orders")) {
	    	   orders=entry.getValue().getAsJsonArray();
	    	   Gson gson = new Gson();
	    	 //  String jsonOutput = orders.getAsString();
	    	  // System.out.println("json array to json string "+jsonOutput);
	    	   Iterator<JsonElement> iterator = orders.iterator();
	    	   ArrayList al=new ArrayList<>();

	           while(iterator.hasNext()){
	               JsonElement json2 = (JsonElement)iterator.next();
	               String object = (String) gson.fromJson(json2, String.class);
	               al.add(object);
	           }
	    	 //  Type listType = new TypeToken<List<Post>>(){}.getType();
	    	 //  List<Post> posts = gson.fromJson(jsonOutput, listType);
	    	   
		       }
	       else if(entry.getKey().equals("orderId")) {
	    	   orderId=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("orderStatus")) {
	    	 //  orderStatus=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("createdAt")) {
	    	 //  createdAt=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("symbol")) {
	    	   symbol=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("orderType")) {
	    	  // orderType=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("orderSide")) {
	    	 //  orderSide=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("quantity")) {
	    	   quantity=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("limitPrice")) {
	    	   limitPrice=entry.getValue().getAsString();
		       }
	       else if(entry.getKey().equals("product")) {
	    	 //  product=entry.getValue().getAsString();
		       }
	   }

	   
	   System.out.println("here is orders "+orders );
	   System.out.println("here is orderId "+orderId );
	   System.out.println("here is orderStatus "+orderStatus );
	   System.out.println("here is symbol "+ symbol);
	   System.out.println("here is orderType "+orderType );
	   System.out.println("here is quantity "+quantity );
	   System.out.println("here is limitPrice "+limitPrice );
	   System.out.println("here is product "+ product);
	   //System.out.println("here is "+ );
	   
	   
	    client.close();
	}
/*	public static void main(String args[]) {
		OrderRouterTest ort=new OrderRouterTest();
		try {
			ort.PostReq();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public JsonArray getOrders() {
		return orders;
	}
	public void setOrders(JsonArray orders) {
		this.orders = orders;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderSide() {
		return orderSide;
	}
	public void setOrderSide(String orderSide) {
		this.orderSide = orderSide;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(String limitPrice) {
		this.limitPrice = limitPrice;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public ArrayList getArrlist() {
		return arrlist;
	}
	public void setArrlist(ArrayList arrlist) {
		this.arrlist = arrlist;
	}
	

}
