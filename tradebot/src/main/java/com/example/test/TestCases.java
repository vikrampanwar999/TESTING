package com.example.test;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TestCases {
	@Autowired TestCase1 tc1;
	

	public void test() throws ClassNotFoundException, InterruptedException, IOException {
		tc1.setKafkaConsumer();
		Thread.sleep(5000);
/*Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","8600.00","0.601",1);//trade account balance is not enough
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","8900.00","0.001",2);//invalid-amount
Thread.sleep(13000);
tc1.test2("BTCUSDT","SELL","8060.00","0.091",3);//Unknown error
Thread.sleep(13000);
tc1.test2("BTCUSD","SELL","8000.00","0.019",4);//CANCELLED->null reason`
Thread.sleep(13000);
tc1.test2("BTCUSD","BUY","8800.00","4.001",5);// Insufficient funds (HTTP status code: 400)
Thread.sleep(13000);
tc1.test2("BTCUSD","BUY","8070.00","0.010",6);//partial filled -> ORDER_EXEC_REPORT
Thread.sleep(13000);
tc1.test2("BTCUSD","BUY","8370.00","1.010",7);
Thread.sleep(13000);
tc1.test2("BTCUSD","BUY","9770.00","0.020",8);
Thread.sleep(13000);
tc1.test2("BTCUSD","SELL","5070.00","0.00010",9);
Thread.sleep(13000);

tc1.test2("BTCUSDT","BUY","8800.00","4.001",10);// Insufficient funds (HTTP status code: 400)
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","8070.00","0.010",11);//partial filled -> ORDER_EXEC_REPORT
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","8370.00","1.010",12);
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","9770.00","0.0015",13);
Thread.sleep(13000);
tc1.test2("LTCBTC","SELL","5070.00","0.00010",14);
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","8370.00","1.010",15);
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","9770.00","0.0015",16);
Thread.sleep(13000);
tc1.test2("BTCUSDT","BUY","830.00","1.010",17);
Thread.sleep(13000);*/
tc1.test2("BTCUSDT","BUY","94770.00","0.0015",18);
Thread.sleep(13000);
}}
