package com.cuberleon.engine.core;

import java.io.PrintStream;

public class Debug {

    private static PrintStream streamOut = System.out;
    private static PrintStream streamErr = System.err;

    public static void info(String text) {
        streamOut.println("INFO: " + text);
    }

    public static void info(String format, Object ... args) {
        streamOut.printf("INFO: " + format + "\n", args);
    }

    public static void error(String text) {
        streamErr.println("ERROR: " + text);
        new Exception().printStackTrace(streamErr);
    }

    public static void error(String format, Object ... args) {
        streamErr.printf("ERROR: " + format + "\n", args);
        new Exception().printStackTrace(streamErr);
    }

    public static void fatalError(String text) {
        streamErr.println("Fatal ERROR: " + text);
        new Exception().printStackTrace(streamErr);
        System.exit(1);
    }

    public static void fatalError(String format, Object ... args) {
        streamErr.printf("Fatal ERROR: " + format + "\n", args);
        new Exception().printStackTrace(streamErr);
        System.exit(1);
    }
}
