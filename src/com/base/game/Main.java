package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(new TestGame(), 60.0f, true);
        engine.createWindow(800, 600, "3D Engine");
        engine.start();
    }
}
