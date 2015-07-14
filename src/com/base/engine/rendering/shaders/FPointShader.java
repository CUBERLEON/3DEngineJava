package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.components.PointLight;
import com.base.engine.rendering.*;

public class FPointShader extends Shader {

    private static FPointShader m_instance = new FPointShader();

    public static FPointShader getInstance() {
        return m_instance;
    }

    protected FPointShader() {
        super();

        addVertexShaderFromFile("forward-point.vs");
        addFragmentShaderFromFile("forward-point.fs");

        compileShader();

        //transforms
        addUniform("v_mTransform");
        addUniform("v_mvpTransform");

        //additional variables
        addUniform("f_eyePosition");

        //lights
        addUniform("f_pointLight.color");
        addUniform("f_pointLight.intensity");
        addUniform("f_pointLight.attenuation.constant");
        addUniform("f_pointLight.attenuation.linear");
        addUniform("f_pointLight.attenuation.exponent");
        addUniform("f_pointLight.position");
        addUniform("f_pointLight.range");

        //material
        addUniform("f_specularIntensity");
        addUniform("f_specularPower");
    }

    @Override
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f mTransform = transform.getModelTransform();
        Matrix4f mvpTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        //transforms
        setUniformM4F("v_mTransform", mTransform);
        setUniformM4F("v_mvpTransform", mvpTransform);

        //additional variables
        setUniformV3F("f_eyePosition", getRenderingEngine().getMainCamera().getPosition());

        //lights
        setUniform("f_pointLight", (PointLight)getRenderingEngine().getActiveLight());

        //material
        material.getTexture().bind();
        setUniformF("f_specularIntensity", material.getSpecularIntensity());
        setUniformF("f_specularPower", material.getSpecularPower());
    }

    public void setUniform(String uniformName, PointLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformF(uniformName + ".attenuation.constant", value.getConstantAttenuation());
        setUniformF(uniformName + ".attenuation.linear", value.getLinearAttenuation());
        setUniformF(uniformName + ".attenuation.exponent", value.getExponentAttenuation());
        setUniformV3F(uniformName + ".position", value.getTransform().getPosition());
        setUniformF(uniformName + ".range", value.getRange());
    }
}
