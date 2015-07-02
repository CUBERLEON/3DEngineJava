package com.base.engine;

public class PointLight {

    private Vector3f m_color;
    private float m_intensity;
    private Attenuation m_attenuation;
    private Vector3f m_position;
    private float m_range;

    PointLight(Vector3f color, float intensity, Attenuation attenuation, Vector3f position) {
        m_color = color;
        m_intensity = intensity;
        m_attenuation = attenuation;
        m_position = position;
        updateRange();
    }

    PointLight(Vector3f color, float intensity, Attenuation attenuation, Vector3f position, float range) {
        m_color = color;
        m_intensity = intensity;
        m_attenuation = attenuation;
        m_position = position;
        m_range = range;
    }

    private void updateRange() {
        float minIntensity = 0.01f;
        float maxRange = 1000.0f;

        float range = maxRange;
        if (m_intensity < minIntensity * m_attenuation.getConstant()) {
            range = 0;
        } else {
            if (m_attenuation.getExponent() > 0) {
                range = ((float)Math.sqrt(m_attenuation.getLinear() * m_attenuation.getLinear() + 4.0*m_attenuation.getExponent()*(m_intensity/minIntensity - m_attenuation.getConstant())) - m_attenuation.getLinear()) / (2.0f * m_attenuation.getExponent());
            } else if (m_attenuation.getLinear() > 0)
                range = Math.max(0, (m_intensity - minIntensity * m_attenuation.getConstant()) / (minIntensity * m_attenuation.getLinear()));
        }
        m_range = Math.min(range, maxRange);
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
        updateRange();
    }

    public Attenuation getAttenuation() {
        return m_attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.m_attenuation = attenuation;
        updateRange();
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public void setPosition(Vector3f position) {
        this.m_position = position;
    }

    public float getRange() {
        return m_range;
    }

    public void setRange(float range) {
        this.m_range = range;
    }
}
