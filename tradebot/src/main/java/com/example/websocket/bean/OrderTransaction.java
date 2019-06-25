package com.example.websocket.bean;

import java.math.BigDecimal;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTransaction {
	
	private String matchingEngineLogId;
	private String globalMatchingEngineLogId;
	private String matchingEngineId;
	private long logCreatedAt;
	private long createdAt;
	private long updatedAt;
	private long refreshedAt;
	private String memberId;
	private String orderId;
	private String status;
	private OrderType orderType;
	private OrderSide orderSide;
	private BigDecimal quantity;
	private BigDecimal limitPrice;
	private BigDecimal openQuantity;
	private BigDecimal filledCumulativeQuantity;
	private String dealtCurrency;
	private String quoteCurrency;
	private BigDecimal dealtAmount;
	private BigDecimal quoteAmount;
	private BigDecimal lastFilledPrice;
	private BigDecimal lastFilledQty;
	private BigDecimal cancelledQty;
	private String linkedHash;
	private String matchId;
	private long lastFilledCreatedAt;
	private boolean lastFilledIsTaker;
	
	public BigDecimal getCancelledQty() {
		return cancelledQty;
	}
	public void setCancelledQty(BigDecimal cancelledQty) {
		this.cancelledQty = cancelledQty;
	}
	public String getMatchingEngineLogId() {
		return matchingEngineLogId;
	}
	public void setMatchingEngineLogId(String matchingEngineLogId) {
		this.matchingEngineLogId = matchingEngineLogId;
	}
	public String getGlobalMatchingEngineLogId() {
		return globalMatchingEngineLogId;
	}
	public void setGlobalMatchingEngineLogId(String globalMatchingEngineLogId) {
		this.globalMatchingEngineLogId = globalMatchingEngineLogId;
	}
	public String getMatchingEngineId() {
		return matchingEngineId;
	}
	public void setMatchingEngineId(String matchingEngineId) {
		this.matchingEngineId = matchingEngineId;
	}
	public long getLogCreatedAt() {
		return logCreatedAt;
	}
	public void setLogCreatedAt(long logCreatedAt) {
		this.logCreatedAt = logCreatedAt;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public long getRefreshedAt() {
		return refreshedAt;
	}
	public void setRefreshedAt(long refreshedAt) {
		this.refreshedAt = refreshedAt;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public OrderSide getOrderSide() {
		return orderSide;
	}
	public void setOrderSide(OrderSide orderSide) {
		this.orderSide = orderSide;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	public BigDecimal getOpenQuantity() {
		return openQuantity;
	}
	public void setOpenQuantity(BigDecimal openQuantity) {
		this.openQuantity = openQuantity;
	}
	public BigDecimal getFilledCumulativeQuantity() {
		return filledCumulativeQuantity;
	}
	public void setFilledCumulativeQuantity(BigDecimal filledCumulativeQuantity) {
		this.filledCumulativeQuantity = filledCumulativeQuantity;
	}
	public String getDealtCurrency() {
		return dealtCurrency;
	}
	public void setDealtCurrency(String dealtCurrency) {
		this.dealtCurrency = dealtCurrency;
	}
	public String getQuoteCurrency() {
		return quoteCurrency;
	}
	public void setQuoteCurrency(String quoteCurrency) {
		this.quoteCurrency = quoteCurrency;
	}
	public BigDecimal getDealtAmount() {
		return dealtAmount;
	}
	public void setDealtAmount(BigDecimal dealtAmount) {
		this.dealtAmount = dealtAmount;
	}
	public BigDecimal getQuoteAmount() {
		return quoteAmount;
	}
	public void setQuoteAmount(BigDecimal quoteAmount) {
		this.quoteAmount = quoteAmount;
	}
	public BigDecimal getLastFilledPrice() {
		return lastFilledPrice;
	}
	public void setLastFilledPrice(BigDecimal lastFilledPrice) {
		this.lastFilledPrice = lastFilledPrice;
	}
	public BigDecimal getLastFilledQty() {
		return lastFilledQty;
	}
	public void setLastFilledQty(BigDecimal lastFilledQty) {
		this.lastFilledQty = lastFilledQty;
	}
	public String getLinkedHash() {
		return linkedHash;
	}
	public void setLinkedHash(String linkedHash) {
		this.linkedHash = linkedHash;
	}
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public long getLastFilledCreatedAt() {
		return lastFilledCreatedAt;
	}
	public void setLastFilledCreatedAt(long lastFilledCreatedAt) {
		this.lastFilledCreatedAt = lastFilledCreatedAt;
	}
	public boolean isLastFilledIsTaker() {
		return lastFilledIsTaker;
	}
	public void setLastFilledIsTaker(boolean lastFilledIsTaker) {
		this.lastFilledIsTaker = lastFilledIsTaker;
	}
	@Override
	public String toString() {
		return "OrderTransaction [matchingEngineLogId=" + matchingEngineLogId + ", globalMatchingEngineLogId="
				+ globalMatchingEngineLogId + ", matchingEngineId=" + matchingEngineId + ", logCreatedAt="
				+ logCreatedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", refreshedAt="
				+ refreshedAt + ", memberId=" + memberId + ", orderId=" + orderId + ", status=" + status
				+ ", orderType=" + orderType + ", orderSide=" + orderSide + ", quantity=" + quantity + ", limitPrice="
				+ limitPrice + ", openQuantity=" + openQuantity + ", filledCumulativeQuantity="
				+ filledCumulativeQuantity + ", dealtCurrency=" + dealtCurrency + ", quoteCurrency=" + quoteCurrency
				+ ", dealtAmount=" + dealtAmount + ", quoteAmount=" + quoteAmount + ", lastFilledPrice="
				+ lastFilledPrice + ", lastFilledQty=" + lastFilledQty + ", cancelledQty=" + cancelledQty
				+ ", linkedHash=" + linkedHash + ", matchId=" + matchId + ", lastFilledCreatedAt=" + lastFilledCreatedAt
				+ ", lastFilledIsTaker=" + lastFilledIsTaker + "]";
	}
}