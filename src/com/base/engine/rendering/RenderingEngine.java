package com.base.engine.rendering;

import com.base.engine.components.Camera;
import com.base.engine.components.Light;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.shaders.FAmbientShader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderingEngine {

    private Camera m_mainCamera;

    private Vector3f m_ambientLight;

    private ArrayList<Light> m_lights;
    private Light m_activeLight;

    public RenderingEngine() {
        System.out.println("INFO: OpenGL version " + getOpenGLVersion());

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        m_lights = new ArrayList<>();
        m_ambientLight = new Vector3f(0.03f, 0.03f, 0.03f);
    }

    public void render(GameObject gameObject) {
        clearScreen();
        m_lights.clear();

        gameObject.addToRenderingEngine(this);

        gameObject.render(FAmbientShader.getInstance());

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (Light light : m_lights) {
            m_activeLight = light;
            gameObject.render(light.getShader());
        }

        glDepthMask(true);
        glDepthFunc(GL_LESS);
        glDisable(GL_BLEND);

        Window.render();
    }

    private void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    private static void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    private static void setTextures(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    private static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public Camera getMainCamera() {
        return m_mainCamera;
    }

//    public void setMainCamera(Camera mainCamera) {
//        m_mainCamera = mainCamera;
//    }

    public void addCamera(Camera mainCamera) {
        m_mainCamera = mainCamera;
    }

    public Vector3f getAmbientLight() {
        return m_ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        m_ambientLight = ambientLight;
    }

    public Light getActiveLight() {
        return m_activeLight;
    }

    public void addLight(Light light) {
        m_lights.add(light);
    }
}
