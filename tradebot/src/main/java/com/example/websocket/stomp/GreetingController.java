package com.example.websocket.stomp;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.websocket.service.SendMessageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lishuai
 * @date 2019年4月18日15:55:12
 */
@Slf4j
@Controller
public class GreetingController {
	private static Logger log = LoggerFactory.getLogger(GreetingController.class);

    @Autowired 
    
    private SendMessageService sendMessageService;
 
    /**
     * 测试页面2
     * @return
     */
    @RequestMapping("/chat5")
    public String chat5() {
        return "chat5";
    }
    @RequestMapping("/chat6")
    public String chat6() {
        return "chat6";
    }
    @RequestMapping("/chat7")
    public String chat7(HttpServletRequest request,HttpServletResponse response) {
    	response.setHeader("Access-Control-Allow-Origin", "*"); 
        return "chat7";
    }
    @RequestMapping("/chat8")
    public String chat8() {
        return "chat8";
    }
    
    @ResponseBody
    @RequestMapping("/chat9")
    public void chat9() {
    //	ReceiveTextStompClient.connect();
    }
    
    
    /**
     * 模拟请求消息
     * @param message
     */
    @ResponseBody
    @RequestMapping(value = "/message/send", produces = "application/json;charset=UTF-8",method =RequestMethod.GET)
    public void hello(String payload,String merberID,String consumerID) {
    	
    	 
        	sendMessageService.sendMessageToConsumer(payload, merberID, consumerID);
    }

}
