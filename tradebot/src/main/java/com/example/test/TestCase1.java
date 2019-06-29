package com.example.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.websocket.bean.ExecutionReport;
import com.example.websocket.bean.Order;
import com.example.websocket.bean.OrderTransaction;
import com.example.websocket.bean.Result;
import com.example.websocket.conf.KafkaConfig;
import com.example.websocket.service.impl.KafkaConsumer;

@Component
public class TestCase1 {
	@Autowired
	KafkaConfig kafkaConfig;
	public KafkaConsumer kc;
	public Logger LOGGER;
	public static HashMap<String, List<Order>> map = new HashMap<>();
	public static HashMap<String, Order> ordermaptest = new HashMap<>();
	public static HashMap<String, List<String>> faultyorders = new HashMap<>();

	public void setKafkaConsumer() throws ClassNotFoundException {
		this.kc = new KafkaConsumer(this.kafkaConfig);
		this.LOGGER = LoggerFactory.getLogger(getClass());
		System.out.println("here is the kafka consumer " + this.kc);
	}

	@Test
	public void test2(String symbol, String orderSide, String limitPrice, String orderqty, String index)
			throws IOException, InterruptedException {
		System.out.println("this is test case " + index
				+ "_______________________________________________________________________________________________");

		LOGGER.debug("Starting running the test case No " + index);
		TestUtil tu = new TestUtil();
		Result result = tu.PostReq(symbol, orderSide, limitPrice, orderqty);
		if("REJECTED".equals(result.getOrderStatus())) {
			return;
		}
		Order orn = new Order();
		orn.setPrice(new BigDecimal(limitPrice));
		orn.setQty(new BigDecimal(orderqty));
		orn.setOrderId(result.getOrderId());
		orn.setCurrency(orderSide);// need to change just did it for quick output
		orn.setSymbol("index:" + index);// for test case index
		ordermaptest.put(result.getOrderId(), orn);
		// System.out.println("here is the converted result of post request\n "+result);
		if ("PENDING_SUBMIT".equals(result.getOrderStatus())) {
			if (!KafkaConsumer.flowReport.containsKey(result.getOrderId()))
				KafkaConsumer.flowReport.put(result.getOrderId(), new ArrayList<String>());
			String s = " order has been placed on the gateway ";
			KafkaConsumer.flowReport.get(result.getOrderId()).add(s);
			KafkaConsumer.flowReport.get(result.getOrderId()).add("test case no " + String.valueOf(index));
		} else {
			System.out.println("Not pending submit");
		}
		LOGGER.debug("\"here is the result of post request from Result object \\n", result);
		//Thread.sleep(30000); for regression testing
		System.out.println("here is the execution flow report " + KafkaConsumer.flowReport.get(result.getOrderId()));
	}

	public void checkTransactionMap() {
		List<String> lsj = new ArrayList<>();
		lsj.add("inside checktransaction map");

		faultyorders.put("000000", lsj);
		HashMap<String, List<OrderTransaction>> otmap = this.kc.transactionmap;
		for (Entry<String, List<OrderTransaction>> entry : otmap.entrySet()) {
			String orderid = entry.getKey();
			Order a = ordermaptest.get(orderid);
			if(a==null) {
				return;
			}
			BigDecimal priceExpected = a.getPrice();
			BigDecimal qtyExpected = a.getQty();
			List<OrderTransaction> orderTransactionList = entry.getValue();
			BigDecimal totalexeQty = new BigDecimal(0);
			BigDecimal totalExePrice = new BigDecimal(0);
			for (OrderTransaction ot : orderTransactionList) {
				System.out.println("orderTransactionIN test:" + ot.toString());
				if (ot.getLastFilledQty() != null)
					totalexeQty.add(ot.getLastFilledQty());
				if (ot.getLastFilledPrice() != null)
					totalExePrice.add(ot.getLastFilledPrice());
			}

			if (totalexeQty.compareTo(qtyExpected) == 0) {
				System.out.println("Executed qty is  matching for order id " + orderid);
				System.out.println("sum of total last filled qty:" + totalexeQty + " expected qty:" + qtyExpected);
				String s1 = " sum of total last filled qty:" + totalexeQty + " expected qty:" + qtyExpected;
				String s = "Executed qty is matching for order id " + orderid;
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s);
					ls.add(s1);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s);
					faultyorders.get(a.getOrderId()).add(s1);
				}
			} else {
				System.out.println("sum of total last filled qty is not matching for order id " + orderid);
				System.out.println("sum of total last filled qty::" + totalexeQty + " expected qty:" + qtyExpected);
				String s1 = " sum of total last filled qty:" + totalexeQty + " expected qty:" + qtyExpected;
				String s = "sum of total last filled qty is not matching for order id " + orderid;
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s);
					ls.add(s1);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s);
					faultyorders.get(a.getOrderId()).add(s1);
				}
			}
			if (totalExePrice.compareTo(priceExpected) == -1 && a.getCurrency().equals("SELL")) {
				String s1 = "Executed price is less than the price limit set in SELL";
				String s2 = " EXE price:" + totalExePrice + " expected price:" + priceExpected;
				System.out.println("Executed price is less than the price limit set in SELL");
				System.out.println("EXE price:" + totalExePrice + " expected qty:" + priceExpected);
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}

			}
			if (totalExePrice.compareTo(priceExpected) == 1 && a.getCurrency().equals("BUY")) {
				String s1 = "Executed price is GREATER than the price limit set in BUY";
				String s2 = "EXE price:" + totalExePrice + " expected price:" + priceExpected;
				System.out.println("Executed price is GREATER than the price limit set in BUY");
				System.out.println("EXE price:" + totalExePrice + " expected qty:" + priceExpected);
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}
			} else {
				String s2 = "EXE price:" + totalExePrice + " expected qty:" + priceExpected;
				String s1 = " EXE qty:" + totalexeQty + " expected qty:" + qtyExpected;
				String s0 = "EverThing looks correct";

				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s0);
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}
			}
		}
	}

	public void checkExeMap() {
		List<String> lsj = new ArrayList<>();
		lsj.add("inside execution Report map");

		faultyorders.put("000000", lsj);
		HashMap<String, List<OrderTransaction>> otmap = this.kc.transactionmap;
		for (Entry<String, List<OrderTransaction>> entry : otmap.entrySet()) {
			String orderid = entry.getKey();
			Order a = ordermaptest.get(orderid);
			if(a==null) {
				return;
			}
			BigDecimal priceExpected = a.getPrice();
			BigDecimal qtyExpected = a.getQty();
			List<OrderTransaction> orderTransactionList = entry.getValue();
			BigDecimal totalexeQty = new BigDecimal(0);
			BigDecimal totalExePrice = new BigDecimal(0);
			for (OrderTransaction ot : orderTransactionList) {
				if (ot.getLastFilledQty() != null)
					totalexeQty.add(ot.getLastFilledQty());
				if (ot.getLastFilledQty() != null)
					totalExePrice.add(ot.getLastFilledPrice());
			}

			if (totalexeQty.compareTo(qtyExpected) == 0) {
				System.out.println("Executed qty is not matching for order id " + orderid);
				System.out.println("EXE qty:" + totalexeQty + " expected qty:" + qtyExpected);
				String s1 = " EXE qty:" + totalexeQty + " expected qty:" + qtyExpected;
				String s = "Executed qty is not matching for order id " + orderid;
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s);
					ls.add(s1);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s);
					faultyorders.get(a.getOrderId()).add(s1);
				}
			}
			if (totalExePrice.compareTo(priceExpected) == -1 && a.getCurrency().equals("SELL")) {
				String s1 = "Executed price is less than the price limit set in SELL";
				String s2 = " EXE price:" + totalExePrice + " expected price:" + priceExpected;
				System.out.println("Executed price is less than the price limit set in SELL");
				System.out.println("EXE price:" + totalExePrice + " expected qty:" + priceExpected);
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}

			}
			if (totalExePrice.compareTo(priceExpected) == 1 && a.getCurrency().equals("BUY")) {
				String s1 = "Executed price is GREATER than the price limit set in BUY";
				String s2 = "EXE price:" + totalExePrice + " expected price:" + priceExpected;
				System.out.println("Executed price is GREATER than the price limit set in BUY");
				System.out.println("EXE price:" + totalExePrice + " expected qty:" + priceExpected);
				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}
			} else {
				String s2 = "EXE price:" + totalExePrice + " expected qty:" + priceExpected;
				String s1 = " EXE qty:" + totalexeQty + " expected qty:" + qtyExpected;
				String s0 = "EverThing looks correct";

				if (!faultyorders.containsKey(a.getOrderId())) {
					List<String> ls = new ArrayList<>();
					ls.add(s0);
					ls.add(s1);
					ls.add(s2);
					faultyorders.put(a.getOrderId(), ls);
				} else {
					faultyorders.get(a.getOrderId()).add(s1);
					faultyorders.get(a.getOrderId()).add(s2);
				}
			}
		}
	}

}
