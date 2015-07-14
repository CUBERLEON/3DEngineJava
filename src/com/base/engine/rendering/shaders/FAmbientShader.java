package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.rendering.*;

public class FAmbientShader extends Shader {

    private static FAmbientShader m_instance = new FAmbientShader();

    public static FAmbientShader getInstance() {
        return m_instance;
    }

    protected FAmbientShader() {
        super();

        addVertexShaderFromFile("forward-ambient.vs");
        addFragmentShaderFromFile("forward-ambient.fs");

        compileShader();

        //transforms
        addUniform("v_mvpTransform");

        //lights
        addUniform("f_ambientIntensity");
    }

    @Override
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f mvpTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        //transforms
        setUniformM4F("v_mvpTransform", mvpTransform);

        //lights
        setUniformV3F("f_ambientIntensity", getRenderingEngine().getAmbientLight());

        //material
        material.getTexture().bind();
    }
}
