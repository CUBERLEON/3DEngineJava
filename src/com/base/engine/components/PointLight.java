package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.shaders.FPointShader;

public class PointLight extends Light {

    protected Vector3f m_attenuation;
    protected float m_range;

    public PointLight(PointLight r) {
        this(r.getColor(), r.getIntensity(), r.getAttenuation(), r.getRange());
    }

    public PointLight(Vector3f color, float intensity, Vector3f attenuation) {
        this(color, intensity, attenuation, 0);
        updateRange();
    }

    protected PointLight(Vector3f color, float intensity, Vector3f attenuation, float range) {
        super(color, intensity);
        m_attenuation = attenuation;
        m_range = range;
        setShader(FPointShader.getInstance());
    }

    private void updateRange() {
        float minIntensity = 1 / (m_intensity * m_color.max() * 256);
        float maxRange = 1000.0f;

        float range = maxRange;

        float c = getConstantAttenuation();
        float l = getLinearAttenuation();
        float e = getExponentAttenuation();
        if (m_intensity < minIntensity * c) {
            range = 0;
        } else {
            if (e > 0) {
                range = (-l + (float)Math.sqrt(l * l + 4.0 * e * (m_intensity/minIntensity - c))) / (2.0f * e);
            } else if (getLinearAttenuation() > 0)
                range = Math.max(0, (m_intensity - minIntensity * c) / (minIntensity * l));
        }

        m_range = Math.min(range, maxRange);
    }

    @Override public void setIntensity(float intensity) {
        m_intensity = Math.max(0, intensity);
        updateRange();
    }

    public Vector3f getAttenuation() {
        return m_attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        m_attenuation = attenuation;
    }

    public float getConstantAttenuation() {
        return m_attenuation.getX();
    }

    public void setConstantAttenuation(float constantAttenuation) {
        this.m_attenuation.setX(constantAttenuation);
    }

    public float getLinearAttenuation() {
        return m_attenuation.getY();
    }

    public void setLinearAttenuation(float linearAttenuation) {
        this.m_attenuation.setY(linearAttenuation);
    }

    public float getExponentAttenuation() {
        return m_attenuation.getZ();
    }

    public void setExponentAttenuation(float exponentAttenuation) {
        this.m_attenuation.setZ(exponentAttenuation);
    }

    public float getRange() {
        return m_range;
    }
}
