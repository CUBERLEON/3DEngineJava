package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

public class SpotLight {

    PointLight m_pointLight;
    Vector3f m_direction;
    float m_cutoff;

    public SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
        this.m_pointLight = pointLight;
        this.m_direction = direction.getNormalized();
        this.m_cutoff = cutoff;
    }

    public PointLight getPointLight() {
        return m_pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.m_pointLight = pointLight;
    }

    public Vector3f getDirection() {
        return m_direction;
    }

    public void setDirection(Vector3f direction) {
        this.m_direction = direction.getNormalized();
    }

    public float getCutoff() {
        return m_cutoff;
    }

    public void setCutoff(float cutoff) {
        this.m_cutoff = cutoff;
    }
}
