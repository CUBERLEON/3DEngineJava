package com.cuberleon.engine.rendering.shaders;

import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.components.PointLight;
import com.cuberleon.engine.rendering.Material;
import com.cuberleon.engine.rendering.RenderingEngine;

public class FPointShader extends Shader {

    private static FPointShader m_instance = new FPointShader();

    public static FPointShader getInstance() {
        return m_instance;
    }

    protected FPointShader() {
        super("forward-point");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);

        //lights
        setUniform("l_point", (PointLight) renderingEngine.getActiveLight());
    }
}
