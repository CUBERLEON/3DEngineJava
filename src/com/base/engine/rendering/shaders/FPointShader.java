package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.components.PointLight;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;

public class FPointShader extends Shader {

    private static FPointShader m_instance = new FPointShader();

    public static FPointShader getInstance() {
        return m_instance;
    }

    protected FPointShader() {
        super("forward-point");
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
        setUniform("f_pointLight", (PointLight)renderingEngine.getActiveLight());

        //material
        material.getTexture("diffuse").bind();
        setUniformF("f_specularIntensity", material.getFloat("specularIntensity"));
        setUniformF("f_specularPower", material.getFloat("specularPower"));
    }

    public void setUniform(String uniformName, PointLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformF(uniformName + ".attenuation.constant", value.getAttenuation().getConstant());
        setUniformF(uniformName + ".attenuation.linear", value.getAttenuation().getLinear());
        setUniformF(uniformName + ".attenuation.exponent", value.getAttenuation().getExponent());
        setUniformV3F(uniformName + ".position", value.getTransform().getPosition());
        setUniformF(uniformName + ".range", value.getRange());
    }
}
