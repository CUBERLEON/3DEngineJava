package com.base.game;

import com.base.engine.core.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(new TestGame(), 60.0f, false);
        engine.createWindow(400, 300, "3D Engine");
        engine.start();
    }
}
