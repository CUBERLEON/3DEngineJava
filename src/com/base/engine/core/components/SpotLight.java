package com.base.engine.core.components;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.shaders.FSpotShader;

public class SpotLight extends PointLight {

    protected Vector3f m_direction;
    protected float m_cutoff;

    public SpotLight(SpotLight r) {
        this(r.getColor(), r.getIntensity(), r.getAttenuation(), r.getRange(), r.getDirection(), r.getCutoff());
    }

    public SpotLight(Vector3f color, float intensity, Vector3f attenuation, Vector3f direction, float cutoff) {
        super(color, intensity, attenuation);
        this.m_direction = direction.getNormalized();
        this.m_cutoff = cutoff;
        setShader(FSpotShader.getInstance());
    }

    public SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
        this(pointLight.getColor(), pointLight.getIntensity(), pointLight.getAttenuation(), pointLight.getRange(), direction, cutoff);
    }

    protected SpotLight(Vector3f color, float intensity, Vector3f attenuation, float range, Vector3f direction, float cutoff) {
        super(color, intensity, attenuation, range);
        this.m_direction = direction.getNormalized();
        this.m_cutoff = cutoff;
        setShader(FSpotShader.getInstance());
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
