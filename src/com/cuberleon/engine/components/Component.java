package com.cuberleon.engine.components;

import com.cuberleon.engine.core.CoreEngine;
import com.cuberleon.engine.core.Debug;
import com.cuberleon.engine.core.Node;
import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.rendering.RenderingEngine;
import com.cuberleon.engine.rendering.shaders.Shader;

public abstract class Component {

    private Node m_node;

    public void input(float time) {}
    public void update(float time) {}
    public void render(Shader shader, RenderingEngine renderingEngine) {}
    public void dispose() {}

    public Transform getTransform() {
        return getNode().getTransform();
    }

    public void setNode(Node node) {
        m_node = node;
    }

    protected Node getNode() {
        if (m_node == null)
            Debug.fatalError("calling Component.getNode() method when Node wasn't assigned");
        return m_node;
    }

    public void addToEngine(CoreEngine engine) {}
}
