package com.base.engine.rendering;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector3f;

public class PhongShader extends Shader {

    private static PhongShader m_instance = new PhongShader();

    public static PhongShader getInstance() {
        return m_instance;
    }

    public static final int MAX_POINT_LIGHTS = 4;
    public static final int MAX_SPOT_LIGHTS = 4;

    private static Vector3f m_ambientLight = new Vector3f(0.0f, 0.0f, 0.0f);
    private static DirectionalLight m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0, new Vector3f(-1, -1, -1));
    private static PointLight[] m_pointLights = new PointLight[0];
    private static SpotLight[] m_spotLights = new SpotLight[0];

    public PhongShader() {
        super();

        addVertexShaderFromFile("phong.vs");
        addFragmentShaderFromFile("phong.fs");

        compileShader();

        addUniform("v_modelTransform");
        addUniform("v_modelViewProjectionTransform");
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

        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            addUniform("f_spotLights[" + i + "].pointLight.color");
            addUniform("f_spotLights[" + i + "].pointLight.intensity");
            addUniform("f_spotLights[" + i + "].pointLight.attenuation.constant");
            addUniform("f_spotLights[" + i + "].pointLight.attenuation.linear");
            addUniform("f_spotLights[" + i + "].pointLight.attenuation.exponent");
            addUniform("f_spotLights[" + i + "].pointLight.position");
            addUniform("f_spotLights[" + i + "].pointLight.range");

            addUniform("f_spotLights[" + i + "].direction");
            addUniform("f_spotLights[" + i + "].cutoff");
        }
        addUniform("f_spotLightsCount");

        addUniform("f_specularIntensity");
        addUniform("f_specularPower");
    }

    public void updateUniforms(Transform transform, Material material) {
        Matrix4f modelTransform = transform.getModelTransform();
        Matrix4f modelViewProjectionTransform = transform.getModelViewProjectionTransform(getRenderingEngine().getMainCamera());

        material.getTexture().bind();

        setUniformM4F("v_modelTransform", modelTransform);
        setUniformM4F("v_modelViewProjectionTransform", modelViewProjectionTransform);
        setUniformV3F("f_eyePosition", getRenderingEngine().getMainCamera().getPosition());

        setUniformV3F("f_baseColor", material.getColor());

        setUniformV3F("f_ambientLight", m_ambientLight);
        setUniform("f_directionalLight", m_directionalLight);

        for (int i = 0; i < m_pointLights.length; i++)
            setUniform("f_pointLights[" + i + "]", m_pointLights[i]);
        setUniformI("f_pointLightsCount", m_pointLights.length);

        for (int i = 0; i < m_spotLights.length; i++)
            setUniform("f_spotLights[" + i + "]", m_spotLights[i]);
        setUniformI("f_spotLightsCount", m_spotLights.length);

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

    public void setUniform(String uniformName, SpotLight value) {
        setUniform(uniformName + ".pointLight", value.getPointLight());
        setUniformV3F(uniformName + ".direction", value.getDirection());
        setUniformF(uniformName + ".cutoff", value.getCutoff());
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

    public static SpotLight[] getSpotLights() {
        return m_spotLights;
    }

    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_POINT_LIGHTS) {
            System.err.println("Fatal ERROR: Phong shader cannot handle " + spotLights.length + " Spot Lights (max = " + MAX_SPOT_LIGHTS + ")");
            new Exception().printStackTrace();
            System.exit(1);
        }

        m_spotLights = spotLights;
    }

    public static int getSpotLightsCount() {
        return m_spotLights.length;
    }
}
