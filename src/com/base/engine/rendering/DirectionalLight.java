package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

public class DirectionalLight {

    private Vector3f m_color;
    private float m_intensity;
    private Vector3f m_direction;

    public DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
        m_color = color;
        m_intensity = intensity;
        m_direction = direction.getNormalized();
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

    public Vector3f getDirection() {
        return m_direction;
    }

    public void setDirection(Vector3f direction) {
        this.m_direction = direction.getNormalized();
    }
}
