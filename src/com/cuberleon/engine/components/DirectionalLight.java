package com.cuberleon.engine.components;

import com.cuberleon.engine.core.Vector3f;
import com.cuberleon.engine.rendering.shaders.FDirectionalShader;

public class DirectionalLight extends Light {

    public DirectionalLight(DirectionalLight r) {
        this(r.getColor(), r.getIntensity());
    }

    public DirectionalLight(Vector3f color, float intensity) {
        super(color, intensity);
        setShader(FDirectionalShader.getInstance());
    }

    public Vector3f getDirection() {
        return getTransform().getRealRotation().getForward();
    }
}
