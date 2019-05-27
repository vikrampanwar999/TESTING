package com.example.websocket.stomp;

import com.sun.security.auth.UserPrincipal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author linyun
 * @date 2018/9/13 下午5:57
 */
@Component
public class WebSocketHandleInterceptor implements ChannelInterceptor {

    /**
     * 绑定user到websocket conn上
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("----------------------:");
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            // 获取username
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                System.out.println(raw);
            }
            String merberID = accessor.getFirstNativeHeader("merberID");
            String consumerID = accessor.getFirstNativeHeader("consumerID");

            System.out.println("merberID:" + merberID);

            if (StringUtils.isEmpty(merberID)) {
                return null;
            }
            if (StringUtils.isEmpty(consumerID)) {
                return null;
            }
            // 绑定user
            Principal principal = new UserPrincipal(merberID+"_"+consumerID);
            accessor.setUser(principal);
        }
        return message;
    }
}
