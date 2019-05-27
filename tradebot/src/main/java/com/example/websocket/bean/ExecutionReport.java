package com.example.websocket.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExecutionReport {

	private String currency;
	private String symbol;
	private BigDecimal qty;
	private BigDecimal price;
	private BigDecimal executedQty;
	private BigDecimal executedPrice;
	private BigDecimal cancelledQty;
	private BigDecimal leavesQty;
	private String side;
	private String type;
	private String marketExchange;
	private BigDecimal transactTime;
	private String orderId;
	private String parentOrderId;
	private String exchangeOrderId;
	private String orderStatus;
	private String exchangeAccountId;
	private boolean childOrder;
	private Long pricePrecision;
	private Long qtyPrecision;
	private String reportType;
	private String rejectReason;
	private int rejectCode;
	private boolean buyOrder;
	private BigDecimal remainingQty;
	private boolean filled;
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
	public BigDecimal getLeavesQty() {
		return leavesQty;
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
	public BigDecimal getTransactTime() {
		return transactTime;
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
	public Long getPricePrecision() {
		return pricePrecision;
	}
	public Long getQtyPrecision() {
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
	public void setLeavesQty(BigDecimal leavesQty) {
		this.leavesQty = leavesQty;
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
	public void setTransactTime(BigDecimal transactTime) {
		this.transactTime = transactTime;
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
	public void setPricePrecision(Long pricePrecision) {
		this.pricePrecision = pricePrecision;
	}
	public void setQtyPrecision(Long qtyPrecision) {
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
	@Override
	public String toString() {
		return "ExecutionReport [currency=" + currency + ", symbol=" + symbol + ", qty=" + qty + ", price=" + price
				+ ", executedQty=" + executedQty + ", executedPrice=" + executedPrice + ", cancelledQty=" + cancelledQty
				+ ", leavesQty=" + leavesQty + ", side=" + side + ", type=" + type + ", marketExchange="
				+ marketExchange + ", transactTime=" + transactTime + ", orderId=" + orderId + ", parentOrderId="
				+ parentOrderId + ", exchangeOrderId=" + exchangeOrderId + ", orderStatus=" + orderStatus
				+ ", exchangeAccountId=" + exchangeAccountId + ", childOrder=" + childOrder + ", pricePrecision="
				+ pricePrecision + ", qtyPrecision=" + qtyPrecision + ", reportType=" + reportType + ", rejectReason="
				+ rejectReason + ", rejectCode=" + rejectCode + ", buyOrder=" + buyOrder + ", remainingQty="
				+ remainingQty + ", filled=" + filled + "]";
	}

}
