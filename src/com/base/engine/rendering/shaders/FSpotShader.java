package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.rendering.Material;
import com.base.engine.components.SpotLight;

public class FSpotShader extends Shader {

    private static FSpotShader m_instance = new FSpotShader();

    public static FSpotShader getInstance() {
        return m_instance;
    }

    protected FSpotShader() {
        super();

        addVertexShaderFromFile("forward-spot.vs");
        addFragmentShaderFromFile("forward-spot.fs");

        compileShader();

        //transforms
        addUniform("v_mTransform");
        addUniform("v_mvpTransform");

        //additional variables
        addUniform("f_eyePosition");

        //lights
        addUniform("f_spotLight.pointLight.color");
        addUniform("f_spotLight.pointLight.intensity");
        addUniform("f_spotLight.pointLight.attenuation.constant");
        addUniform("f_spotLight.pointLight.attenuation.linear");
        addUniform("f_spotLight.pointLight.attenuation.exponent");
        addUniform("f_spotLight.pointLight.position");
        addUniform("f_spotLight.pointLight.range");
        addUniform("f_spotLight.direction");
        addUniform("f_spotLight.cutoff");

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
        setUniform("f_spotLight", (SpotLight)getRenderingEngine().getActiveLight());

        //material
        material.getTexture().bind();
        setUniformF("f_specularIntensity", material.getSpecularIntensity());
        setUniformF("f_specularPower", material.getSpecularPower());
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
