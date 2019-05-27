package com.example.websocket.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.websocket.util.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linyun
 * @date 2018/9/13 下午3:12
 */
@Slf4j
@Service
public class MyHandshake implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            // 获取请求连接之前的token参数.
            Enumeration enu = servletRequest.getParameterNames();
            Map<String, Object> map = new HashMap<String,Object>();
            while (enu.hasMoreElements()) {
                String paraName = (String) enu.nextElement();
                map.put(paraName, servletRequest.getParameter(paraName));
            }

            // 从session中获取到当前登录的用户信息. 作为socket的账号信息. session的的WEBSOCKET_USERNAME信息,在用户打开页面的时候设置.
        //    String userName = (String) servletRequest.getSession().getAttribute("WEBSOCKET_USERNAME");
            attributes.put("WEBSOCKET_USERNAME", JsonHelper.toJson(map).toString());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception e) {
        System.out.println("........afterHandshake.......");
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
            if (!StringUtils.isEmpty(req.getHeader("sec-websocket-protocol"))) {
                try {
                    System.out.println(URLDecoder.decode(req.getHeader("sec-websocket-protocol"),"utf-8"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                resp.addHeader("sec-websocket-protocol", req.getHeader("sec-websocket-protocol"));
            }
        }
    }
}
