package com.base.engine.components;

import com.base.engine.core.Node;
import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

public abstract class Component {

    private Node m_object;

    public void input(float time) {}
    public void update(float time) {}
    public void render(Shader shader, RenderingEngine renderingEngine) {}

    public Transform getTransform() {
        if (m_object == null) {
            System.err.println("Fatal ERROR: calling Component.getTransform() method when Node wasn't assigned");
            new Exception().printStackTrace();
            System.exit(1);
        }
        return m_object.getTransform();
    }

    public void setObject(Node object) {
        m_object = object;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}
