package com.example.websocket.bean;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class Result {
	private List<Order2> orders;
	private String orderId ;
	private String orderStatus;
	private String status;//it's returned when rejected
	private String createdAt;
	private String symbol;
	private String orderType;
	private String orderSide;
	private BigDecimal quantity;
	private BigDecimal limitPrice;
	private List<Product> product;
	private E55Product e55Product ;
	private String reason;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Order2> getOrders() {
		return orders;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public String getSymbol() {
		return symbol;
	}
	public String getOrderType() {
		return orderType;
	}
	public String getOrderSide() {
		return orderSide;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getLimitPrice() {
		return limitPrice;
	}
	public List<Product> getProduct() {
		return product;
	}
	public E55Product getE55Product() {
		return e55Product;
	}
	public void setOrders(List<Order2> orders) {
		this.orders = orders;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public void setOrderSide(String orderSide) {
		this.orderSide = orderSide;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	public void setProduct(List<Product> product) {
		this.product = product;
	}
	public void setE55Product(E55Product e55Product) {
		this.e55Product = e55Product;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public String toString() {
		return "Result [orders=" + orders + ", orderId=" + orderId + ", orderStatus=" + orderStatus + ", status="
				+ status + ", createdAt=" + createdAt + ", symbol=" + symbol + ", orderType=" + orderType
				+ ", orderSide=" + orderSide + ", quantity=" + quantity + ", limitPrice=" + limitPrice + ", product="
				+ product + ", e55Product=" + e55Product + "]";
	}
	
	
	
}
