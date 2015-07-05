package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;

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

        addUniform("v_transform");
        addUniform("f_color");
    }

    public void updateUniforms(Matrix4f world, Matrix4f perspective, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniformM4F("v_transform", perspective);
        setUniformV3F("f_color", material.getColor());
    }
}
