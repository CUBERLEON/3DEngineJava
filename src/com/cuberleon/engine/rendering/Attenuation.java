package com.cuberleon.engine.rendering;

import com.cuberleon.engine.core.Vector3f;

public class Attenuation extends Vector3f {

    public Attenuation(float constant, float linear, float exponent) {
        super(constant, linear, exponent);
    }

    public float getConstant() {
        return getX();
    }

    public void setConstant(float constantAttenuation) {
        setX(constantAttenuation);
    }

    public float getLinear() {
        return getY();
    }

    public void setLinearn(float linearAttenuation) {
        setY(linearAttenuation);
    }

    public float getExponent() {
        return getZ();
    }

    public void setExponent(float exponentAttenuation) {
        setZ(exponentAttenuation);
    }
}
