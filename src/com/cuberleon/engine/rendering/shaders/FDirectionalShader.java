package com.cuberleon.engine.rendering.shaders;

import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.components.DirectionalLight;
import com.cuberleon.engine.rendering.Material;
import com.cuberleon.engine.rendering.RenderingEngine;

public class FDirectionalShader extends Shader {

    private static FDirectionalShader m_instance = new FDirectionalShader();

    public static FDirectionalShader getInstance() {
        return m_instance;
    }

    protected FDirectionalShader() {
        super("forward-directional");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);

        //lights
        setUniform("l_directional", (DirectionalLight) renderingEngine.getActiveLight());
    }
}
