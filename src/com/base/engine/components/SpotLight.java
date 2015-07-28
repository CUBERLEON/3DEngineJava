package com.base.engine.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.shaders.FSpotShader;

public class SpotLight extends PointLight {

    protected float m_cutoff;

    public SpotLight(SpotLight r) {
        this(r.getColor(), r.getIntensity(), r.getAttenuation(), r.getRange(), r.getCutoff());
    }

    public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutoff) {
        super(color, intensity, attenuation);
        this.m_cutoff = cutoff;
        setShader(FSpotShader.getInstance());
    }

    public SpotLight(PointLight pointLight, float cutoff) {
        this(pointLight.getColor(), pointLight.getIntensity(), pointLight.getAttenuation(), pointLight.getRange(), cutoff);
    }

    protected SpotLight(Vector3f color, float intensity, Attenuation attenuation, float range, float cutoff) {
        super(color, intensity, attenuation, range);
        this.m_cutoff = cutoff;
        setShader(FSpotShader.getInstance());
    }

    public Vector3f getDirection() {
        return getTransform().getRealRotation().getForward();
    }

    public float getCutoff() {
        return m_cutoff;
    }

    public void setCutoff(float cutoff) {
        this.m_cutoff = cutoff;
    }
}
