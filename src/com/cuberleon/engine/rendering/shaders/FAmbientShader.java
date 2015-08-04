package com.cuberleon.engine.rendering.shaders;

import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.rendering.Material;
import com.cuberleon.engine.rendering.RenderingEngine;

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
