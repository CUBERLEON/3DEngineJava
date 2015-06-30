package com.base.engine;

import org.lwjgl.opengl.GL32;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderUtil {

    public static void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public static void setClearColor(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public static void initGraphics() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        Transform.setProjection(45.0f, 0.1f, 1000.0f);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public static void setTextures(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    public static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
