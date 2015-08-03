package com.base.engine.rendering.shaders;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;

public class FAmbientShader extends Shader {

    private static FAmbientShader m_instance = new FAmbientShader();

    public static FAmbientShader getInstance() {
        return m_instance;
    }

    protected FAmbientShader() {
        super("forward-ambient");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);
    }
}
