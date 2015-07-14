package com.base.engine.core.components;

import com.base.engine.core.GameObject;
import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

public abstract class GameComponent {

    private GameObject m_object;

    public void input(float time) {}
    public void update(float time) {}
    public void render(Shader shader) {}

    public Transform getTransform() {
        return m_object.getTransform();
    }

    public void setObject(GameObject object) {
        m_object = object;
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {}
}
