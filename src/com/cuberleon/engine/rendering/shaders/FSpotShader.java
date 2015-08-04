package com.cuberleon.engine.rendering.shaders;

import com.cuberleon.engine.core.Transform;
import com.cuberleon.engine.rendering.Material;
import com.cuberleon.engine.components.SpotLight;
import com.cuberleon.engine.rendering.RenderingEngine;

public class FSpotShader extends Shader {

    private static FSpotShader m_instance = new FSpotShader();

    public static FSpotShader getInstance() {
        return m_instance;
    }

    protected FSpotShader() {
        super("forward-spot");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        super.updateUniforms(transform, material, renderingEngine);

        //lights
        setUniform("l_spot", (SpotLight) renderingEngine.getActiveLight());
    }
}
