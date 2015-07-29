package com.base.engine.components;

import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

public abstract class GameComponent {

    private GameObject m_object;

    public void input(float time) {}
    public void update(float time) {}
    public void render(Shader shader, RenderingEngine renderingEngine) {}

    public Transform getTransform() {
        if (m_object == null) {
            System.err.println("Fatal ERROR: calling GameComponent.getTransform() method when GameObject wasn't assigned");
            new Exception().printStackTrace();
            System.exit(1);
        }
        return m_object.getTransform();
    }

    public void setObject(GameObject object) {
        m_object = object;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}
