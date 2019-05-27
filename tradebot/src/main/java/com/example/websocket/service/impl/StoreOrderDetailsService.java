package com.example.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.OrderDetailsMapRepository;
import com.example.websocket.bean.OrderDetailsMap;

@Service
public class StoreOrderDetailsService {
	
	@Autowired OrderDetailsMapRepository odmr;
	
	public Object saveDetails(OrderDetailsMap details) {
		return odmr.save(details);
	}

}
