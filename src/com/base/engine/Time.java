package com.base.engine;

public class Time {

    public static final long SECOND = 1000000000L;

    private static double m_delta;

    public static long getTime() {
        return System.nanoTime();
    }

    public static double getDelta() {
        return m_delta;
    }

    public static void setDelta(double delta) {
        Time.m_delta = delta;
    }
}
