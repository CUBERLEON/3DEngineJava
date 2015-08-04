package com.cuberleon.engine.components;

import com.cuberleon.engine.rendering.*;
import com.cuberleon.engine.rendering.shaders.Shader;

public class MeshRenderer extends Component {

    private Mesh m_mesh;
    private Material m_material;

    public MeshRenderer(Mesh mesh, Material material) {
        m_mesh = mesh;
        m_material = material;
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        shader.bind();
        shader.updateUniforms(getTransform(), m_material, renderingEngine);
        m_mesh.draw();
    }
}
