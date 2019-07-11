package com.example.websocket.bean;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum OrderReportType {

    ORDER_ACK_REPORT ("ORDER_ACK_REPORT"),
    ORDER_CANCEL_REPORT ("ORDER_CANCEL_REPORT"),
    ORDER_EXEC_REPORT ("ORDER_EXEC_REPORT"),
    ORDER_REJECT_REPORT ("ORDER_REJECT_REPORT");

    private String name;

    private static final Map<String,OrderReportType> ENUM_MAP;

    OrderReportType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,OrderReportType> map = new ConcurrentHashMap<String,OrderReportType>();
        for (OrderReportType instance : OrderReportType.values()) {
            map.put(instance.getName(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static OrderReportType get (String name) {
        return ENUM_MAP.get(name);
    }

}
