package com.example.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StcwrapperTest {
@Autowired Stcwrapper t;
	@Test
	public void test() throws InterruptedException, IOException {
		//fail("Not yet implemented");
		//t.runtcs(symbol, orderSide, limitPrice, orderqty, index);
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","BUY","8600.00","0.601",1);//trade account balance is not enough
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","BUY","8900.00","0.001",2);//invalid-amount
		Thread.sleep(13000);
		t.runtcs("BTCUSDT","SELL","8060.00","0.091",3);//Unknown error
		Thread.sleep(13000);
		t.runtcs("BTCUSD","SELL","8000.00","0.019",4);//CANCELLED->null reason`
		Thread.sleep(13000);
		t.runtcs("BTCUSD","BUY","8800.00","4.001",5);// Insufficient funds (HTTP status code: 400)
		Thread.sleep(13000);
		t.runtcs("BTCUSD","BUY","8070.00","0.010",6);
	}

}
