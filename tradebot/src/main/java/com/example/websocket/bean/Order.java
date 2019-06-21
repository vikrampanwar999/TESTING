package com.example.websocket.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

public enum OrderReportType {
	ORDER_ACK_REPORT, ORDER_CANCEL_REPORT, ORDER_EXEC_REPORT, ORDER_REJECT_REPORT
}
	
	public Order() {

	}

	public Order(OrderType type, MarketExchangeEnum market, BigDecimal quantity, BigDecimal price) {
		this.type = type;
		this.marketExchange = market;
		this.qty = quantity;
		this.price = price;
	}

	/**
	 * 
	 */

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}
	public OrderSide getSide() {
		return side;
	}
	public void setSide(OrderSide side) {
		this.side = side;
	}
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public MarketExchangeEnum getMarketExchange() {
		return marketExchange;
	}
	public void setMarketExchange(MarketExchangeEnum marketExchange) {
		this.marketExchange = marketExchange;
	}

	public long getTransactTime() {
		return transactTime;
	}

	public void setTransactTime(long transactTime) {
		this.transactTime = transactTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExchangeOrderId() {
		return exchangeOrderId;
	}

	public void setExchangeOrderId(String exchangeOrderId) {
		this.exchangeOrderId = exchangeOrderId;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
//	public OrderStatus getDerivedOrderStatus() {
//		if(executedQty.equals(qty))
//		{
//			return OrderStatus.FILLED;
//		} else if(!executedQty.equals(qty)) {
//			if(cancelledQty!=null && executedPrice!=null) {
//				return OrderStatus.PARTIALLY_FILLED;
//			} else {
//				return OrderStatus.CANCELLED;
//			}
//		} else {
//			return orderStatus;
//		}
//	}
	
	public BigDecimal getExecutedQty() {
		return executedQty;
	}

	public void setExecutedQty(BigDecimal executedQty) {
		if(executedQty!=null) {
			this.executedQty = executedQty;
		} else {
			this.executedQty = BigDecimal.ZERO;
		}
	}

	public BigDecimal getCancelledQty() {
		return cancelledQty;
	}

	public void setCancelledQty(BigDecimal cancelledQty) {
		if(cancelledQty!=null) {
			this.cancelledQty = cancelledQty;
		} else {
			this.cancelledQty = BigDecimal.ZERO;
		}
	}

	public BigDecimal getLeavesQty() {
		return (leavesQty==null)?BigDecimal.ZERO : leavesQty;
	}

	public void setLeavesQty(BigDecimal leavesQty) {
		if(leavesQty!=null) {
			this.leavesQty = leavesQty;
		} else {
			this.leavesQty = BigDecimal.ZERO;
		}
	}

	public boolean isBuyOrder()	{
		return this.getSide() == OrderSide.BUY;
	}

	public String getExchangeAccountId() {
		return exchangeAccountId;
	}

	public void setExchangeAccountId(String exchangeAccountId) {
		this.exchangeAccountId = exchangeAccountId;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}

	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
	}

	public boolean isChildOrder() {
		return childOrder;
	}

	public void setChildOrder(boolean childOrder) {
		this.childOrder = childOrder;
	}
	
	public void setPricePrecision(int pricePrecision) {
		this.pricePrecision = pricePrecision;
	}
	
	public int getPricePrecision() {
		return this.pricePrecision;
	}
	
	public void setQtyPrecision(int qtyPrecision) {
		this.qtyPrecision = qtyPrecision;
	}
	
	public int getQtyPrecision() {
		return this.qtyPrecision;
	}


	// Note: This is for child orders only
	public BigDecimal getRemainingQty()
	{
		if (qty != null && executedQty != null)
		    return (qty.subtract(executedQty));
		return qty;
	}
	
	public boolean isFilled() {
		if(leavesQty!=null)
			return leavesQty.compareTo(BigDecimal.ZERO) == 0;
		else
			return false;
	}

	public OrderReportType getReportType() {
		return reportType;
	}

	public void setReportType(OrderReportType reportType) {
		this.reportType = reportType;
	}

	public void update(Order origOrder)
	{
		this.setCurrency(origOrder.getCurrency());
		this.setSymbol(origOrder.getSymbol());
		this.setQty(origOrder.getQty());
		this.setOrderId(origOrder.getOrderId());
		this.setSide(origOrder.getSide());
	}
	
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public int getRejectCode() {
		return rejectCode;
	}

	public void setRejectCode(int rejectCode) {
		this.rejectCode = rejectCode;
	}

	public BigDecimal getExecutedPrice() {
		return executedPrice;
	}

	public void setExecutedPrice(BigDecimal executedPrice) {
		this.executedPrice = executedPrice;
	}

	private String               currency;
	private String               symbol;
	private BigDecimal           qty;
	private BigDecimal           price;
	private BigDecimal           executedQty; // total executed qty
	private BigDecimal			 executedPrice;
	private BigDecimal           cancelledQty;
	private BigDecimal           leavesQty;
	private OrderSide            side;
	private OrderType            type;
	private MarketExchangeEnum   marketExchange;
	private long                 transactTime;
	private String               orderId; // our order id, generated by
	private String               parentOrderId; //
	private String               exchangeOrderId; // the venue gives to us
	private OrderStatus          orderStatus;
	private String               exchangeAccountId; // keep track of accounts on their venue
	private boolean              childOrder; // indicates wether it is a child or not
	private String               childOrderId;
	public String getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(String childOrderId) {
		this.childOrderId = childOrderId;
	}

	// for different symbols in different exchanges, the precision may be different
	private int					 pricePrecision; 
	private int					 qtyPrecision; // quantity precision
	private OrderReportType 	 reportType;

	private String				 rejectReason;
	private int 				 rejectCode;
	@Override
	public String toString() {
		return "Order [currency=" + currency + ", symbol=" + symbol + ", qty=" + qty + ", price=" + price
				+ ", executedQty=" + executedQty + ", executedPrice=" + executedPrice + ", cancelledQty=" + cancelledQty
				+ ", leavesQty=" + leavesQty + ", side=" + side + ", type=" + type + ", marketExchange="
				+ marketExchange + ", transactTime=" + transactTime + ", orderId=" + orderId + ", parentOrderId="
				+ parentOrderId + ", exchangeOrderId=" + exchangeOrderId + ", orderStatus=" + orderStatus
				+ ", exchangeAccountId=" + exchangeAccountId + ", childOrder=" + childOrder + ", pricePrecision="
				+ pricePrecision + ", qtyPrecision=" + qtyPrecision + ", reportType=" + reportType + ", rejectReason="
				+ rejectReason + ", rejectCode=" + rejectCode + "]";
	}
}
