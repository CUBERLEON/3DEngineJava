package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.components.DirectionalLight;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;

public class FDirectionalShader extends Shader {

    private static FDirectionalShader m_instance = new FDirectionalShader();

    public static FDirectionalShader getInstance() {
        return m_instance;
    }

    protected FDirectionalShader() {
        super("forward-directional");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);

        //lights
        setUniform("f_directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
    }

    public void setUniform(String uniformName, DirectionalLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }
}
