
package com.example.websocket.stomp;
 
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.example.repository.OrderDetailsMapRepository;
import com.example.websocket.bean.OrderDetailsMap;
import com.example.websocket.conf.OrderConfiguration;
import com.example.websocket.service.impl.StoreOrderDetailsService;
import com.example.websocket.util.HttpTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
 
public class StompClient {
	
public  void connect(String url,OrderConfiguration orderConfiguration) {
	System.out.println(JSONObject.fromObject(orderConfiguration));
	 final CountDownLatch latch = new CountDownLatch(1);
     List<Transport> transports = new ArrayList<>(1);
     transports.add(new WebSocketTransport(new StandardWebSocketClient()));
     WebSocketClient transport = new SockJsClient(transports);
     WebSocketStompClient stompClient = new WebSocketStompClient(transport);
     stompClient.setDefaultHeartbeat(new long[] { 1000, 0 });
     stompClient.setMessageConverter(new MappingJackson2MessageConverter());
     ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
     taskScheduler.afterPropertiesSet();
     stompClient.setTaskScheduler(taskScheduler); // for heartbeats
     StompSessionHandler myHandler = new MyStompSessionHandler(latch,orderConfiguration);
     
     stompClient.connect(url, myHandler);
     try {
     	 Thread.sleep(3000);
         latch.await(1, TimeUnit.SECONDS);
      
     }
     catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
     }
}


class MyStompSessionHandler extends StompSessionHandlerAdapter {
	// @Autowired private OrderConfiguration orderConfiguration;
	
    private CountDownLatch latch;
    private String symbol;
    private String orderQuantityBuy;
    private String orderQuantitySell;
    private String buy_url;
    @Autowired private StoreOrderDetailsService sods;
    
    
    public MyStompSessionHandler(CountDownLatch latch,OrderConfiguration orderConfiguration) {
    	this.symbol=orderConfiguration.getSymbol();
    	this.orderQuantityBuy=orderConfiguration.getOrderQuantityBuy();
    	this.orderQuantitySell=orderConfiguration.getOrderQuantitySell();
    	this.buy_url=orderConfiguration.getBuy_url();
        this.latch = latch;
    }
 
    @Override
    public void afterConnected(StompSession session,
            StompHeaders connectedHeaders) {
    	//System.out.println("orderConfiguration"+orderConfiguration.getSymbol());
        System.out.println("StompHeaders: " + connectedHeaders.toString());
        session.subscribe("/topic/bbo/"+symbol, new StompFrameHandler() {
        	int i = 0;
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Object.class;
            }
 
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
            	
            	
            	JSONObject json = JSONObject.fromObject(payload);
            	JSONObject jsonbidLevel =JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(json.get("providerBBOMap")).get("BINANCE")).get("bidLevel"));
            	Double price = Double.valueOf(jsonbidLevel.getString("price"));
            	
            	 JSONObject jsonString = new JSONObject();
            	 jsonString.put("method", "exchange.insertOrder");
            	 
            	 JSONObject jsonparams = new JSONObject();
            	 String orderId = UUID.randomUUID().toString();
            	 jsonparams.put("orderId", symbol+":"+orderId);
            	 jsonparams.put("symbol", symbol);
            	 jsonparams.put("orderType", "LIMIT");
            	 jsonparams.put("limitPrice", price);
            	 //logic to store the info at the end on order 
            	 //orderid and complete json
            	 //listen to kafka bus then update the order status.
            	 //kafka
            	 
            	 
            	 
            	 JSONArray jsonarray = new JSONArray();
            	 
            	 
            	
            	try {
            	//buy
            	if(i==0){
            		   jsonparams.put("orderQuantity", Double.valueOf(orderQuantityBuy));
            		   jsonparams.put("orderSide", "BUY");
            		   jsonarray.add("A");
                  	   jsonarray.add(jsonparams);
            		   
            		   jsonString.put("params", jsonarray);
            		   System.out.println("websocket发送订单req:"+jsonString.toString());
					   String respo = HttpTool.sendPost(jsonString.toString(),buy_url, "");
					  //store in database
					   
		          		 OrderDetailsMap odm=new OrderDetailsMap();
		          		 odm.setOrder_details(jsonString.toString());
		          		 odm.setOrder_id(orderId);
		          		 sods.saveDetails(odm);
					   
					   System.out.println("websocket返回订单respo:"+respo);
            	}
            	//sell
            	else if(i==1){
            		 jsonparams.put("orderQuantity", Double.valueOf(orderQuantitySell));
	          		 jsonparams.put("orderSide", "SELL");
	          		 jsonarray.add("A");
                	 jsonarray.add(jsonparams);
	          		 jsonString.put("params", jsonarray);
	          		 System.out.println("websocket发送订单req:"+jsonString.toString());
	          		 String respo = HttpTool.sendPost(jsonString.toString(),buy_url, "");
	          	    //store in database

	          		 OrderDetailsMap odm=new OrderDetailsMap();
	          		 odm.setOrder_details(jsonString.toString());
	          		 odm.setOrder_id(orderId);
	          		 sods.saveDetails(odm);
	          		 
	          		 System.out.println("websocket返回订单respo:"+respo);
            		 session.disconnect();
            	}
            	
            	
            	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	i++;
            	latch.countDown();
              
            }
        });
       // session.send("/app/hello", new HelloMessage("Dave"));
    }
 
    @Override
    public void handleException(StompSession session, StompCommand command,
            StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println(exception.getMessage());
    }
 
    @Override
    public void handleTransportError(StompSession session,
            Throwable exception) {
        System.out.println("transport error.");
    }
}
}