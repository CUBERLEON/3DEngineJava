package com.base.engine;

public class BasicShader extends Shader {

    public BasicShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("basic.vs"));
        addFragmentShader(ResourceLoader.loadShader("basic.fs"));

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
