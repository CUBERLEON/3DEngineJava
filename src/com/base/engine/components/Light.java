package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

public abstract class Light extends Component {

    protected Vector3f m_color;
    protected float m_intensity;
    protected Shader m_shader;

    public Light(Vector3f color, float intensity) {
        m_color = color;
        m_intensity = intensity;
    }

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.addLight(this);
    }

    public Shader getShader() {
        return m_shader;
    }

    protected void setShader(Shader shader) {
        this.m_shader = shader;
    }

    public Vector3f getColor() {
        return m_color;
    }

    public void setColor(Vector3f color) {
        this.m_color = color;
    }

    public float getIntensity() {
        return m_intensity;
    }

    public void setIntensity(float intensity) {
        this.m_intensity = intensity;
    }
}
