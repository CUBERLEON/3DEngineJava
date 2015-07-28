package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.shaders.FPointShader;

public class PointLight extends Light {

    protected Attenuation m_attenuation;
    protected float m_range;

    public PointLight(PointLight r) {
        this(r.getColor(), r.getIntensity(), r.getAttenuation(), r.getRange());
    }

    public PointLight(Vector3f color, float intensity, Attenuation attenuation) {
        this(color, intensity, attenuation, 0);
        updateRange();
    }

    protected PointLight(Vector3f color, float intensity, Attenuation attenuation, float range) {
        super(color, intensity);
        m_attenuation = attenuation;
        m_range = range;
        setShader(FPointShader.getInstance());
    }

    private void updateRange() {
        float minIntensity = 1 / (m_intensity * m_color.max() * 256);
        float maxRange = 1000.0f;

        float range = maxRange;

        float c = getAttenuation().getConstant();
        float l = getAttenuation().getLinear();
        float e = getAttenuation().getExponent();
        if (m_intensity < minIntensity * c) {
            range = 0;
        } else {
            if (e > 0) {
                range = (-l + (float)Math.sqrt(l * l + 4.0 * e * (m_intensity/minIntensity - c))) / (2.0f * e);
            } else if (l > 0)
                range = Math.max(0, (m_intensity - minIntensity * c) / (minIntensity * l));
        }

        m_range = Math.min(range, maxRange);
    }

    @Override public void setIntensity(float intensity) {
        m_intensity = Math.max(0, intensity);
        updateRange();
    }

    public Attenuation getAttenuation() {
        return m_attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        m_attenuation = attenuation;
    }

    public float getRange() {
        return m_range;
    }
}
