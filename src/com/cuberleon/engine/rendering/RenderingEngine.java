package com.cuberleon.engine.rendering;

import com.cuberleon.engine.components.Camera;
import com.cuberleon.engine.components.Light;
import com.cuberleon.engine.core.Debug;
import com.cuberleon.engine.core.Node;
import com.cuberleon.engine.core.Vector3f;
import com.cuberleon.engine.rendering.shaders.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL32.*;

public class RenderingEngine extends MappedValues {

    private Camera m_mainCamera;

    private ArrayList<Light> m_lights;
    private Light m_activeLight;

    private Shader m_ambientShader;

    public RenderingEngine() {
        m_lights = new ArrayList<>();

        Debug.info("OpenGL version " + getOpenGLVersion());

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glFrontFace(GL_CCW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);
//        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
//        glEnable(GL_DITHER);

        //sampler2D GLSL locations
        addInteger("diffuse", 0);
        addInteger("normalMap", 1);

        m_ambientShader = new Shader("forward-ambient");
        addVector3f("ambientLight", new Vector3f(0.03f, 0.03f, 0.03f));
    }

    public void render(Node root) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        root.render(m_ambientShader, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (Light light : m_lights) {
            m_activeLight = light;
            root.render(light.getShader(), this);
        }

        glDepthMask(true);
        glDepthFunc(GL_LESS);
        glDisable(GL_BLEND);

        Window.render();
    }

    public void dispose() {
        if (m_ambientShader != null)
            m_ambientShader.dispose();
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public Camera getMainCamera() {
        if (m_mainCamera == null)
            Debug.fatalError("there is no Camera in the scene graph");

        return m_mainCamera;
    }

//    public void setMainCamera(Camera mainCamera) {
//        m_mainCamera = mainCamera;
//    }

    public void addCamera(Camera mainCamera) {
        m_mainCamera = mainCamera;
    }

    public Light getActiveLight() {
        return m_activeLight;
    }

    public void addLight(Light light) {
        m_lights.add(light);
    }
}
