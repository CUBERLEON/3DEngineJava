package com.base.engine;

public class BaseLight {

    private Vector3f m_color;
    private float m_intensity;

    public BaseLight(Vector3f color, float intensity) {
        m_color = color;
        m_intensity = intensity;
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
