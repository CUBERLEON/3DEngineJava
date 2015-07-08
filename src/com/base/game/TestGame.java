package com.base.game;

import com.base.engine.core.*;
import com.base.engine.rendering.*;

public class TestGame extends Game {

    private DirectionalLight m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.3f, new Vector3f(0, -1, -1));
    private PointLight m_pointLights[] = new PointLight[] { new PointLight(new Vector3f(1, 1, 0), 2.0f, new Attenuation(0, 0, 1), new Vector3f(0.0f, 2.0f, -2.0f)),
                                                            new PointLight(new Vector3f(0, 1, 1), 2.0f, new Attenuation(0, 0, 1), new Vector3f(2.0f, 2.0f, 0.0f))};
    private SpotLight m_spotLights[] = new SpotLight[] { new SpotLight(new PointLight(new Vector3f(1, 1, 1), 1.0f, new Attenuation(0.5f, 0.15f, 0), new Vector3f(3, 3, 5)),
                                                                       new Vector3f(-1, -1, 0), (float)Math.cos(Math.toRadians(15.0f)))};

    public void init() {
        Input.setCursor(false);

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                                            new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = { 0, 1, 2,
                          2, 1, 3};

        Mesh plane = new Mesh(vertices, indices, true);
        Mesh angel = new Mesh("models/angel/angel.obj");

        Material material = new Material(new Texture("models/cube/default.png"),
                                         new Vector3f(1, 1, 1), 1.0f, 8);

        GameObject planeObject = new GameObject().addComponent(new MeshRenderer(plane, material));
        GameObject angelObject = new GameObject().addComponent(new MeshRenderer(angel, material));

        getRootObject().addChild(planeObject).addChild(angelObject);

        PhongShader.setAmbientLight(new Vector3f(0.02f, 0.02f, 0.02f));
        PhongShader.setDirectionalLight(m_directionalLight);
        //PhongShader.setPointLights(m_pointLights);
        //PhongShader.setSpotLights(m_spotLights);
    }

    private int k = 0;

    public void input() {
        getRenderingEngine().getMainCamera().input();

        if (Input.getKeyDown(Input.KEY_C))
            k = (k + 1) % m_pointLights.length;

        if (Input.getKey(Input.KEY_UP))
            m_pointLights[k].getPosition().add(new Vector3f(0, 0, (float) -Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_DOWN))
            m_pointLights[k].getPosition().add(new Vector3f(0, 0, (float) Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_LEFT))
            m_pointLights[k].getPosition().add(new Vector3f((float) -Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_RIGHT))
            m_pointLights[k].getPosition().add(new Vector3f((float) Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_SPACE))
            m_pointLights[k].getPosition().add(new Vector3f(0, (float) Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_LSHIFT))
            m_pointLights[k].getPosition().add(new Vector3f(0, (float) -Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_ADD))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() + (float) Time.getDelta() * 3.0f);
        if (Input.getKey(Input.KEY_SUBTRACT))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() - (float) Time.getDelta() * 3.0f);
    }
//
//    public void update() {
//        m_spotLights[0].setDirection(m_camera.getForward());
//        m_spotLights[0].getPointLight().setPosition(m_camera.getPosition());
//    }
}