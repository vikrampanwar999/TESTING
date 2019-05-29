package com.example.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Stcwrapper {
	@Autowired SeprateTestCases stc;
	private String symbol;
	private String orderSide;
	private String limitPrice;
	private String orderqty;
	//int index
	@Test
	public void runtcs(String symbol,String orderSide,String limitPrice,String orderqty,int index) throws IOException, InterruptedException {
		stc.validateExecutedQty(symbol, orderSide, limitPrice, orderqty, index);
		stc.validateOrderId(symbol, orderSide, limitPrice, orderqty, index);
		stc.validateOrderSide(symbol, orderSide, limitPrice, orderqty, index);
		stc.validatePrice(symbol, orderSide, limitPrice, orderqty, index);
		stc.validateQty(symbol, orderSide, limitPrice, orderqty, index);
		stc.validateSymbol(symbol, orderSide, limitPrice, orderqty, index);
	}
	
	

	public String getSymbol() {
		return symbol;
	}
	public String getOrderSide() {
		return orderSide;
	}
	public String getLimitPrice() {
		return limitPrice;
	}
	public String getOrderqty() {
		return orderqty;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public void setOrderSide(String orderSide) {
		this.orderSide = orderSide;
	}
	public void setLimitPrice(String limitPrice) {
		this.limitPrice = limitPrice;
	}
	public void setOrderqty(String orderqty) {
		this.orderqty = orderqty;
	}
	
}
