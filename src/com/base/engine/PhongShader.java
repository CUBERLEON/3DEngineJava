package com.base.engine;

public class PhongShader extends Shader {

    private static PhongShader m_instance = new PhongShader();

    public static PhongShader getInstance() {
        return m_instance;
    }

    private static Vector3f m_ambientLight = new Vector3f(0.05f, 0.05f, 0.05f);
    private static DirectionalLight m_directionalLight = new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0), new Vector3f(0, 0, 0));

    public PhongShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("phong.vs"));
        addFragmentShader(ResourceLoader.loadShader("phong.fs"));

        compileShader();

        addUniform("v_transform");
        addUniform("v_projectedTransform");
        addUniform("f_baseColor");
        addUniform("f_ambientLight");
        addUniform("f_directionalLight.base.color");
        addUniform("f_directionalLight.base.intensity");
        addUniform("f_directionalLight.direction");
    }

    public void updateUniforms(Matrix4f world, Matrix4f projected, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniformM4F("v_transform", world);
        setUniformM4F("v_projectedTransform", projected);
        setUniformV3F("f_baseColor", material.getColor());
        setUniformV3F("f_ambientLight", m_ambientLight);
        setUniform("f_directionalLight", m_directionalLight);
    }

    public void setUniform(String uniformName, DirectionalLight value) {
        setUniform(uniformName + ".base", value.getBase());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }

    public void setUniform(String uniformName, BaseLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
    }

    public static Vector3f getAmbientLight() {
        return m_ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.m_ambientLight = ambientLight;
    }

    public static DirectionalLight getDirectionalLight() {
        return m_directionalLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        m_directionalLight = directionalLight;
    }
}
