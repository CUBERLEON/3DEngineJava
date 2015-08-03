package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.rendering.Material;
import com.base.engine.components.SpotLight;
import com.base.engine.rendering.RenderingEngine;

public class FSpotShader extends Shader {

    private static FSpotShader m_instance = new FSpotShader();

    public static FSpotShader getInstance() {
        return m_instance;
    }

    protected FSpotShader() {
        super("forward-spot");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);

        //lights
        setUniform("f_spotLight", (SpotLight)renderingEngine.getActiveLight());
    }

    public void setUniform(String uniformName, SpotLight value) {
        setUniformV3F(uniformName + ".pointLight.color", value.getColor());
        setUniformF(uniformName + ".pointLight.intensity", value.getIntensity());
        setUniformF(uniformName + ".pointLight.attenuation.constant", value.getAttenuation().getConstant());
        setUniformF(uniformName + ".pointLight.attenuation.linear", value.getAttenuation().getLinear());
        setUniformF(uniformName + ".pointLight.attenuation.exponent", value.getAttenuation().getExponent());
        setUniformV3F(uniformName + ".pointLight.position", value.getTransform().getPosition());
        setUniformF(uniformName + ".pointLight.range", value.getRange());
        setUniformV3F(uniformName + ".direction", value.getDirection());
        setUniformF(uniformName + ".cutoff", value.getCutoff());
    }
}
