package com.base.engine;

public class DirectionalLight {

    BaseLight m_base;
    Vector3f m_direction;

    public DirectionalLight(BaseLight base, Vector3f direction) {
        m_base = base;
        m_direction = direction.getNormalized();
    }

    public BaseLight getBase() {
        return m_base;
    }

    public void setBase(BaseLight base) {
        this.m_base = base;
    }

    public Vector3f getDirection() {
        return m_direction;
    }

    public void setDirection(Vector3f direction) {
        this.m_direction = direction.getNormalized();
    }
}
