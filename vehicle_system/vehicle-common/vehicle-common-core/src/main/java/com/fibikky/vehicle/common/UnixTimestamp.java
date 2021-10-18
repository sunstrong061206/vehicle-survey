package com.fibikky.vehicle.common;

public class UnixTimestamp {
    public static String getCurrent() {
        return Long.toString(System.currentTimeMillis() / 1000L);
    }
}
