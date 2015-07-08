package com.base.engine.rendering;

import com.base.engine.core.Camera;
import com.base.engine.core.GameObject;
import com.base.engine.core.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine {

    private Camera m_mainCamera;

    public RenderingEngine() {
        System.out.println("INFO: OpenGL version " + getOpenGLVersion());

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        m_mainCamera = new Camera((float)Math.toRadians(60), Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000.0f);
    }

    public void render(GameObject gameObject) {
        clearScreen();

        gameObject.render(PhongShader.getInstance());

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

    public void setMainCamera(Camera mainCamera) {
        m_mainCamera = mainCamera;
    }
}
