package com.cuberleon.engine.core;

public class Time {

    private static final long SECOND = 1000000000L;

    public static double getTime() {
        return System.nanoTime() / (double)SECOND;
    }
}
