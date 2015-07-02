package com.base.engine;

public class PhongShader extends Shader {

    private static PhongShader m_instance = new PhongShader();

    public static PhongShader getInstance() {
        return m_instance;
    }

    public static final int MAX_POINT_LIGHTS = 4;

    private static Vector3f m_ambientLight = new Vector3f(0.02f, 0.02f, 0.02f);
    private static DirectionalLight m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0, new Vector3f(-1, -1, 0));
    private static PointLight[] m_pointLights = new PointLight[0];

    public PhongShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("phong.vs"));
        addFragmentShader(ResourceLoader.loadShader("phong.fs"));

        compileShader();

        addUniform("v_transform");
        addUniform("v_projectedTransform");
        addUniform("f_eyePosition");

        addUniform("f_baseColor");

        addUniform("f_ambientLight");

        addUniform("f_directionalLight.color");
        addUniform("f_directionalLight.intensity");
        addUniform("f_directionalLight.direction");

        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            addUniform("f_pointLights[" + i + "].color");
            addUniform("f_pointLights[" + i + "].intensity");
            addUniform("f_pointLights[" + i + "].attenuation.constant");
            addUniform("f_pointLights[" + i + "].attenuation.linear");
            addUniform("f_pointLights[" + i + "].attenuation.exponent");
            addUniform("f_pointLights[" + i + "].position");
            addUniform("f_pointLights[" + i + "].range");
        }
        addUniform("f_pointLightsCount");

        addUniform("f_specularIntensity");
        addUniform("f_specularPower");
    }

    public void updateUniforms(Matrix4f world, Matrix4f projected, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniformM4F("v_transform", world);
        setUniformM4F("v_projectedTransform", projected);
        setUniformV3F("f_eyePosition", Transform.getCamera().getPosition());

        setUniformV3F("f_baseColor", material.getColor());

        setUniformV3F("f_ambientLight", m_ambientLight);
        setUniform("f_directionalLight", m_directionalLight);

        for (int i = 0; i < m_pointLights.length; i++)
            setUniform("f_pointLights[" + i + "]", m_pointLights[i]);
        setUniformI("f_pointLightsCount", m_pointLights.length);

        setUniformF("f_specularIntensity", material.getSpecularIntensity());
        setUniformF("f_specularPower", material.getSpecularPower());
    }

    public void setUniform(String uniformName, DirectionalLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }

    public void setUniform(String uniformName, PointLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformF(uniformName + ".attenuation.constant", value.getAttenuation().getConstant());
        setUniformF(uniformName + ".attenuation.linear", value.getAttenuation().getLinear());
        setUniformF(uniformName + ".attenuation.exponent", value.getAttenuation().getExponent());
        setUniformV3F(uniformName + ".position", value.getPosition());
        setUniformF(uniformName + ".range", value.getRange());
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

    public static PointLight[] getPointLights() {
        return m_pointLights;
    }

    public static void setPointLights(PointLight[] pointLights) {
        if (pointLights.length > MAX_POINT_LIGHTS) {
            System.err.println("Fatal ERROR: Phong shader cannot handle " + pointLights.length + " Point Lights (max = " + MAX_POINT_LIGHTS + ")");
            new Exception().printStackTrace();
            System.exit(1);
        }

        m_pointLights = pointLights;
    }

    public static int getPointLightsCount() {
        return m_pointLights.length;
    }
}
