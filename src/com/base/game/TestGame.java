package com.base.game;

import com.base.engine.core.*;
import com.base.engine.core.components.*;
import com.base.engine.rendering.*;

public class TestGame extends Game {

    DirectionalLight m_directionalLight;
    PointLight m_pointLights[];
    SpotLight m_spotLights[];

    @Override
    public void init() {
        m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.5f, new Vector3f(0, -1, -1));
        m_pointLights = new PointLight[] { new PointLight(new Vector3f(1, 1, 0), 2.0f, new Vector3f(0, 0, 1)),
                                                        new PointLight(new Vector3f(0, 1, 1), 2.0f, new Vector3f(0, 0, 1))};
        m_spotLights = new SpotLight[] { new SpotLight(new Vector3f(1, 1, 1), 1.0f, new Vector3f(0.5f, 0.15f, 0),
                                                                   new Vector3f(-1, -1, 0), (float)Math.cos(Math.toRadians(15.0f)))};


        Input.setCursor(false);

        //meshes
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                                            new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = { 0, 1, 2,
                          2, 1, 3};

        Material material = new Material(new Texture("models/cube/default.png"),
                                         new Vector3f(1, 1, 1), 1.0f, 8);

        GameObject planeObject = new GameObject().addComponent(new MeshRenderer(new Mesh(vertices, indices, true), material));
        GameObject angelObject = new GameObject().addComponent(new MeshRenderer(new Mesh("models/angel/angel.obj"), material));

        getRootObject().addChild(planeObject).addChild(angelObject);

        //lights
        GameObject pointLightObjects[] = new GameObject[2];
        pointLightObjects[0] = new GameObject().addComponent(m_pointLights[0]);
        pointLightObjects[0].getTransform().setPosition(new Vector3f(0.0f, 2.0f, -2.0f));

        pointLightObjects[1] = new GameObject().addComponent(m_pointLights[1]);
        pointLightObjects[1].getTransform().setPosition(new Vector3f(2.0f, 2.0f, 0.0f));

        getRootObject().addChildren(pointLightObjects);
        getRootObject().addComponent(m_directionalLight);
    }

    private int k = 0;

    @Override
    public void input(float time) {
        getRenderingEngine().getMainCamera().input(time); //temporal!

        if (Input.getKeyDown(Input.KEY_C))
            k = (k + 1) % m_pointLights.length;

        if (Input.getKey(Input.KEY_UP))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, 0, -time * 3.0f));
        if (Input.getKey(Input.KEY_DOWN))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, 0, time * 3.0f));
        if (Input.getKey(Input.KEY_LEFT))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(-time * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_RIGHT))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(time * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_SPACE))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, time * 3.0f, 0));
        if (Input.getKey(Input.KEY_LSHIFT))
            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, -time * 3.0f, 0));
        if (Input.getKey(Input.KEY_ADD))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() + time * 3.0f);
        if (Input.getKey(Input.KEY_SUBTRACT))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() - time * 3.0f);
    }
//
//    public void update() {
//        m_spotLights[0].setDirection(m_camera.getForward());
//        m_spotLights[0].getPointLight().setPosition(m_camera.getPosition());
//    }
}