package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class BasicShader extends Shader {

    private static BasicShader m_instance = new BasicShader();

    public static BasicShader getInstance() {
        return m_instance;
    }

    public BasicShader() {
        super();

        addVertexShaderFromFile("basic.vs");
        addFragmentShaderFromFile("basic.fs");

        compileShader();

        addUniform("v_modelViewProjectionTransform");
        addUniform("f_color");
    }

    public void updateUniforms(Transform transform, Material material) {
//        Matrix4f modelTransform = transform.getModelTransform();
        Matrix4f modelViewProjectionTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        material.getTexture().bind();

        setUniformM4F("v_modelViewProjectionTransform", modelViewProjectionTransform);
        setUniformV3F("f_color", material.getColor());
    }
}
