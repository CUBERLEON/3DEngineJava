package com.base.engine.components;

import com.base.engine.core.Node;
import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

public abstract class Component {

    private Node m_node;

    public void input(float time) {}
    public void update(float time) {}
    public void render(Shader shader, RenderingEngine renderingEngine) {}

    public Transform getTransform() {
        return getNode().getTransform();
    }

    public void setNode(Node node) {
        m_node = node;
    }

    public Node getNode() {
        if (m_node == null) {
            System.err.println("Fatal ERROR: calling Component.getNode() method when Node wasn't assigned");
            new Exception().printStackTrace();
            System.exit(1);
        }
        return m_node;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}
