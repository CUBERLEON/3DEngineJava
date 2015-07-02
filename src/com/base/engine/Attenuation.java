package com.base.engine;

public class Attenuation {

    private float m_constant;
    private float m_linear;
    private float m_exponent;

    public Attenuation(float constant, float linear, float exponent) {
        m_constant = Math.max(0, constant);
        m_linear = Math.max(0, linear);
        m_exponent = Math.max(0, exponent);
    }

    public float getConstant() {
        return m_constant;
    }

    public void setConstant(float constant) {
        this.m_constant = Math.max(0, constant);
    }

    public float getLinear() {
        return m_linear;
    }

    public void setLinear(float linear) {
        this.m_linear = Math.max(0, linear);
    }

    public float getExponent() {
        return m_exponent;
    }

    public void setExponent(float exponent) {
        this.m_exponent = Math.max(0, exponent);
    }
}
