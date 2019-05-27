package com.example.websocket.conf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component 
@ConfigurationProperties(prefix = "props", ignoreUnknownFields = true)
public class OrderConfiguration {

    @Value("${props.symbol}")
    private String symbol;

    @Value("${props.orderQuantityBuy}")
    private String orderQuantityBuy;
    
    @Value("${props.orderQuantitySell}")
    private String orderQuantitySell;

    @Value("${props.fixedTime}")
    private long fixedTime;
    
    @Value("${props.buy_url}")
    private String buy_url;
    
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getOrderQuantityBuy() {
		return orderQuantityBuy;
	}

	public void setOrderQuantityBuy(String orderQuantityBuy) {
		this.orderQuantityBuy = orderQuantityBuy;
	}

	public String getOrderQuantitySell() {
		return orderQuantitySell;
	}

	public void setOrderQuantitySell(String orderQuantitySell) {
		this.orderQuantitySell = orderQuantitySell;
	}

	public long getFixedTime() {
		return fixedTime;
	}

	public void setFixedTime(long fixedTime) {
		this.fixedTime = fixedTime;
	}

	public String getBuy_url() {
		return buy_url;
	}

	public void setBuy_url(String buy_url) {
		this.buy_url = buy_url;
	}

}