package com.example.websocket.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
public class OrderDetailsMap {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String order_details;
	private String order_id;

	public String getOrder_details() {
		return order_details;
	}

	public void setOrder_details(String order_details) {
		this.order_details = order_details;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public OrderDetailsMap(String order_details, String order_id) {
		super();
		this.order_details = order_details;
		this.order_id = order_id;
	}
	public OrderDetailsMap() {
		super();
		this.order_details = " ";
		this.order_id = " ";
	}
	

}
