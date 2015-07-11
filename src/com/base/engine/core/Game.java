package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;

public abstract class Game {

    private GameObject m_root;
    private static RenderingEngine m_renderingEngine;

    public void init() {}

    public void input(float time) {
        getRootObject().input(time);
    }

    public void update(float time) {
        getRootObject().update(time);
    }

    public GameObject getRootObject() {
        if (m_root == null)
            m_root = new GameObject();

        return m_root;
    }

    protected static RenderingEngine getRenderingEngine() {
        return m_renderingEngine;
    }

    public static void setRenderingEngine(RenderingEngine renderingEngine) {
        m_renderingEngine = renderingEngine;
    }
}
