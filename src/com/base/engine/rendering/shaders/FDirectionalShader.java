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
        Matrix4f mTransform = transform.getRealModelTransform();
        Matrix4f mvpTransform = transform.getModelViewProjectionTransform(renderingEngine.getMainCamera());

        //transforms
        setUniformM4F("v_mTransform", mTransform);
        setUniformM4F("v_mvpTransform", mvpTransform);

        //additional variables
        setUniformV3F("f_eyePosition", renderingEngine.getMainCamera().getTransform().getRealPosition());

        //lights
        setUniform("f_directionalLight", (DirectionalLight) renderingEngine.getActiveLight());

        //material
        material.getTexture("diffuse").bind();
        setUniformF("f_specularIntensity", material.getFloat("specularIntensity"));
        setUniformF("f_specularPower", material.getFloat("specularPower"));
    }

    public void setUniform(String uniformName, DirectionalLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }
}
