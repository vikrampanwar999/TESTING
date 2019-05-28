package com.example.websocket.bean;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OrderSide {
	 NONE("NONE"),
	 BUY ("BUY"),
	 SELL ("SELL");

    private String name;

    private static final Map<String,OrderSide> ENUM_MAP;

    OrderSide(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,OrderSide> map = new ConcurrentHashMap<String, OrderSide>();
        for (OrderSide instance : OrderSide.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static OrderSide get (String name) {
        return ENUM_MAP.get(name);
    }
}
