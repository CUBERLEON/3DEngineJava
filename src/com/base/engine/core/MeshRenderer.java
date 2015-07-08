package com.base.engine.core;

import com.base.engine.rendering.*;

public class MeshRenderer implements GameComponent {

    private Mesh m_mesh;
    private Material m_material;

    public MeshRenderer(Mesh mesh, Material material) {
        m_mesh = mesh;
        m_material = material;
    }

    public void input(Transform transform) {

    }

    public void update(Transform transform) {

    }

    public void render(Transform transform, Shader shader) {
        shader.bind();
        shader.updateUniforms(transform, m_material);
        m_mesh.draw();
    }
}
