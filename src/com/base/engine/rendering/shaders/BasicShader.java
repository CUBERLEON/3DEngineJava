package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.rendering.*;

public class BasicShader extends Shader {

    private static BasicShader m_instance = new BasicShader();

    public static BasicShader getInstance() {
        return m_instance;
    }

    protected BasicShader() {
        super();

        addVertexShaderFromFile("basic.vs");
        addFragmentShaderFromFile("basic.fs");

        compileShader();

        addUniform("v_mvpTransform");
        addUniform("f_color");
    }

    @Override
    public void updateUniforms(Transform transform, Material material) {
//        Matrix4f mTransform = transform.getModelTransform();
        Matrix4f mvpTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        setUniformM4F("v_mvpTransform", mvpTransform);

        material.getTexture().bind();
        setUniformV3F("f_color", material.getColor());
    }
}
