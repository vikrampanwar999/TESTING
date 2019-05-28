package com.example.websocket.bean;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OrderType {

	NONE("NONE"),
	LIMIT("LIMIT");
	
    private String name;;

    private static final Map<String,OrderType> ENUM_MAP;

    OrderType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,OrderType> map = new ConcurrentHashMap<String, OrderType>();
        for (OrderType instance : OrderType.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static OrderType get (String name) {
        return ENUM_MAP.get(name);
    }
}
