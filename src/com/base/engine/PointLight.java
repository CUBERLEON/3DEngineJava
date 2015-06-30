package com.base.engine;

public class PointLight {

    private BaseLight m_base;
    private Attenuation m_attenuation;
    private Vector3f m_position;
    private float m_range;

    PointLight(BaseLight base, Attenuation attenuation, Vector3f position) {
        float k = 0.03f;
        float range = 10.0f;
        if (base.getIntensity() < k * attenuation.getConstant())
            range = 0;
        else {
            if (attenuation.getExponent() > 0) {
                range = ((float)Math.sqrt(attenuation.getLinear() * attenuation.getLinear() + 4.0*attenuation.getExponent()*(base.getIntensity()/k - attenuation.getConstant())) - attenuation.getLinear()) / (2.0f * attenuation.getExponent());
            } else if (attenuation.getLinear() > 0)
                range = Math.max(0, (base.getIntensity() - k * attenuation.getConstant()) / (k * attenuation.getLinear()));
        }
        m_base = base;
        m_attenuation = attenuation;
        m_position = position;
        m_range = range;
    }

    PointLight(BaseLight base, Attenuation attenuation, Vector3f position, float range) {
        m_base = base;
        m_attenuation = attenuation;
        m_position = position;
        m_range = range;
    }

    public BaseLight getBase() {
        return m_base;
    }

    public void setBase(BaseLight base) {
        this.m_base = base;
    }

    public Attenuation getAttenuation() {
        return m_attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.m_attenuation = attenuation;
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
