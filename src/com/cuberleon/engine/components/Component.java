package com.cuberleon.engine.components;

import com.cuberleon.engine.core.Node;
import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.rendering.RenderingEngine;
import com.cuberleon.engine.rendering.shaders.Shader;

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

    protected Node getNode() {
        if (m_node == null) {
            System.err.println("Fatal ERROR: calling Component.getNode() method when Node wasn't assigned");
            new Exception().printStackTrace();
            System.exit(1);
        }
        return m_node;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}
