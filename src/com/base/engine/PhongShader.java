package com.base.engine;

public class PhongShader extends Shader {

    private static PhongShader m_instance = new PhongShader();

    public static PhongShader getInstance() {
        return m_instance;
    }

    private static Vector3f m_ambientLight;

    public PhongShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("phong.vs"));
        addFragmentShader(ResourceLoader.loadShader("phong.fs"));

        compileShader();

        addUniform("v_transform");
        addUniform("f_baseColor");
        addUniform("f_ambientLight");
    }

    public void updateUniforms(Matrix4f world, Matrix4f perspective, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniformM4F("v_transform", perspective);
        setUniformV3F("f_baseColor", material.getColor());
        setUniformV3F("f_ambientLight", m_ambientLight);
    }

    public static Vector3f getAmbientLight() {
        return m_ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.m_ambientLight = ambientLight;
    }
}
