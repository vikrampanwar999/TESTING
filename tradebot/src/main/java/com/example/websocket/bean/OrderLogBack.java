package com.example.websocket.bean;


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 订单推送的信息
 */
@Entity
public class OrderLogBack {
    /**
     * 会员id
     */
	@Id
	public int i;
    public String memberId;
    /**
     * 订单ID
     */
    public String orderId;

    /**
     * 订单状态
     */
    public String orderStatus;

    /**
     * 订单创建时间
     */
    public long createdAt;

    /**
     *
     */
    public long updatedAt;

    /**
     * 订单撤销时间
     */
    public Date cancelledAt;

    /**
     * 撮合动作的id
     */
    public String matchId;

    /**
     * 日志id
     */
    public String logId;
    /**
     * 日志创建时间
     */
    public long logCreatedAt;

    /**
     * 交易对
     */
    public String dealtCurrency;
    public String quoteCurrency;


    /**
     * 订单类型
     */
    public String orderType;

    /**
     * 订单方向
     */
    public String orderSide;

    /**
     * 订单数量
     */
    public BigDecimal quantity;

    /**
     * 订单限价单
     */
    public BigDecimal limitPrice;

    /**
     * 订单待成交的数量
     */
    public BigDecimal openQuantity;

    /**
     * 订单累计成交数量
     */
    public BigDecimal filledCumulativeQuantity;


    //成交的价格
    public BigDecimal lastFilledPrice;
    //成交数量
    public BigDecimal lastFilledQuantity;
    //成交记录创建时间
    public long lastFilledCreatedAt;

    /**
     * 订单是否是作为taker成交，先挂单的为maker，后挂单成交的是taker
     */
    public Boolean lastFilledIsTaker;

    // hash256(上一个linkedHash + 当前LogId)
    //第一个 值为 hash256(当前LogId)
    public String linkedHash;
    
    public Boolean routable;
    public String lastFilledVenue;
    public String lastFilledSymbol;
}
