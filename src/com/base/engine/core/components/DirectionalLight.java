package com.base.engine.core.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.shaders.FDirectionalShader;

public class DirectionalLight extends Light {

    protected Vector3f m_direction;

    public DirectionalLight(DirectionalLight r) {
        this(r.getColor(), r.getIntensity(), r.getDirection());
    }

    public DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
        super(color, intensity);
        m_direction = direction.getNormalized();
        setShader(FDirectionalShader.getInstance());
    }

    public Vector3f getDirection() {
        return m_direction;
    }

    public void setDirection(Vector3f direction) {
        this.m_direction = direction.getNormalized();
    }
}
