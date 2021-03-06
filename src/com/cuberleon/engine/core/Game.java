package com.cuberleon.engine.core;

import com.cuberleon.engine.rendering.RenderingEngine;

public abstract class Game {

    public Node m_root;

    public Game() {
        m_root = new Node("root");
    }

    public void init() {}

    public void input(float time) {
        m_root.input(time);
    }

    public void update(float time) {
        m_root.update(time);
    }

    public void render(RenderingEngine renderingEngine) {
        renderingEngine.render(m_root);
    }

    public void dispose() {
        m_root.dispose();
    }

    public Node getRoot() {
        return m_root;
    }

    public void setEngine(CoreEngine engine) {
        m_root.setEngine(engine);
    }
}
