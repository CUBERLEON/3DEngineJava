package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.components.DirectionalLight;
import com.base.engine.rendering.Material;

public class FDirectionalShader extends Shader {

    private static FDirectionalShader m_instance = new FDirectionalShader();

    public static FDirectionalShader getInstance() {
        return m_instance;
    }

    protected FDirectionalShader() {
        super();

        addVertexShaderFromFile("forward-directional.vs");
        addFragmentShaderFromFile("forward-directional.fs");

        compileShader();

        //transforms
        addUniform("v_mTransform");
        addUniform("v_mvpTransform");

        //additional variables
        addUniform("f_eyePosition");

        //lights
        addUniform("f_directionalLight.color");
        addUniform("f_directionalLight.intensity");
        addUniform("f_directionalLight.direction");

        //material
        addUniform("f_specularIntensity");
        addUniform("f_specularPower");
    }

    @Override
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f mTransform = transform.getRealModelTransform();
        Matrix4f mvpTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        //transforms
        setUniformM4F("v_mTransform", mTransform);
        setUniformM4F("v_mvpTransform", mvpTransform);

        //additional variables
        setUniformV3F("f_eyePosition", getRenderingEngine().getMainCamera().getTransform().getRealPosition());

        //lights
        setUniform("f_directionalLight", (DirectionalLight)getRenderingEngine().getActiveLight());

        //material
        material.getTexture().bind();
        setUniformF("f_specularIntensity", material.getSpecularIntensity());
        setUniformF("f_specularPower", material.getSpecularPower());
    }

    public void setUniform(String uniformName, DirectionalLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }
}
