package com.example.websocket.service.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.websocket.service.SendMessageService;






/**
 *
 */
@Service("SendMessageService")
public class SendMessageServiceImpl implements SendMessageService {
	
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;
	@Override
	public void sendMessageToConsumer(Object payload, String merberID, String consumerID) {
		
		 try {
			simpMessagingTemplate.convertAndSend("/message/public/" + merberID + "_" + consumerID, payload);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}