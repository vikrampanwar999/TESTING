package com.example.websocket.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public class Order{
	public String currency;
	public String symbol;
	public BigDecimal qty;
	public BigDecimal price;
	public BigDecimal executedQty;
	public BigDecimal executedPrice;
	public BigDecimal cancelledQty;
	public BigDecimal quleavesQtyantity;
	public String side;
	public String type;
	public String marketExchange;
	public long transactiontime;
	public String orderId;
	public String parentOrderId;
	public String exchangeOrderId;
	public String orderStatus;
	public String exchangeAccountId;
	public boolean childOrder;
	public long pricePrecision;
	public long qtyPrecision;
	public String reportType;
	public String rejectReason;
	public int rejectCode;
	public boolean buyOrder ;
	public BigDecimal remainingQty;
	public boolean filled ;
	public String getCurrency() {
		return currency;
	}
	public String getSymbol() {
		return symbol;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public BigDecimal getExecutedQty() {
		return executedQty;
	}
	public BigDecimal getExecutedPrice() {
		return executedPrice;
	}
	public BigDecimal getCancelledQty() {
		return cancelledQty;
	}
	public BigDecimal getQuleavesQtyantity() {
		return quleavesQtyantity;
	}
	public String getSide() {
		return side;
	}
	public String getType() {
		return type;
	}
	public String getMarketExchange() {
		return marketExchange;
	}
	public long getTransactiontime() {
		return transactiontime;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public String getExchangeOrderId() {
		return exchangeOrderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public String getExchangeAccountId() {
		return exchangeAccountId;
	}
	public boolean isChildOrder() {
		return childOrder;
	}
	public long getPricePrecision() {
		return pricePrecision;
	}
	public long getQtyPrecision() {
		return qtyPrecision;
	}
	public String getReportType() {
		return reportType;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public int getRejectCode() {
		return rejectCode;
	}
	public boolean isBuyOrder() {
		return buyOrder;
	}
	public BigDecimal getRemainingQty() {
		return remainingQty;
	}
	public boolean isFilled() {
		return filled;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setExecutedQty(BigDecimal executedQty) {
		this.executedQty = executedQty;
	}
	public void setExecutedPrice(BigDecimal executedPrice) {
		this.executedPrice = executedPrice;
	}
	public void setCancelledQty(BigDecimal cancelledQty) {
		this.cancelledQty = cancelledQty;
	}
	public void setQuleavesQtyantity(BigDecimal quleavesQtyantity) {
		this.quleavesQtyantity = quleavesQtyantity;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setMarketExchange(String marketExchange) {
		this.marketExchange = marketExchange;
	}
	public void setTransactiontime(long transactiontime) {
		this.transactiontime = transactiontime;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}
	public void setExchangeOrderId(String exchangeOrderId) {
		this.exchangeOrderId = exchangeOrderId;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setExchangeAccountId(String exchangeAccountId) {
		this.exchangeAccountId = exchangeAccountId;
	}
	public void setChildOrder(boolean childOrder) {
		this.childOrder = childOrder;
	}
	public void setPricePrecision(long pricePrecision) {
		this.pricePrecision = pricePrecision;
	}
	public void setQtyPrecision(long qtyPrecision) {
		this.qtyPrecision = qtyPrecision;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public void setRejectCode(int rejectCode) {
		this.rejectCode = rejectCode;
	}
	public void setBuyOrder(boolean buyOrder) {
		this.buyOrder = buyOrder;
	}
	public void setRemainingQty(BigDecimal remainingQty) {
		this.remainingQty = remainingQty;
	}
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	

}
