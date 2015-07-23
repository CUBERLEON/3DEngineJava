package com.base.game;

import com.base.engine.components.*;
import com.base.engine.core.*;
import com.base.engine.rendering.*;

import java.util.ArrayList;

public class TestGame extends Game {

    DirectionalLight m_directionalLight;
    PointLight m_pointLights[];
    SpotLight m_spotLights[];

    @Override
    public void init() {
        m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.5f, new Vector3f(0, -1, -1));
        m_pointLights = new PointLight[] { new PointLight(new Vector3f(1, 1, 0), 2.0f, new Vector3f(0, 0, 1)),
                                           new PointLight(new Vector3f(0, 1, 1), 2.0f, new Vector3f(0, 0, 1))};
        m_spotLights = new SpotLight[] { new SpotLight(new Vector3f(1, 1, 1), 2.0f, new Vector3f(0.5f, 0.15f, 0), (float)Math.cos(Math.toRadians(15.0f)))};

        Input.setCursor(false);

        //meshes
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                                            new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = { 0, 1, 2,
                          2, 1, 3 };

        Material planeMaterial = new Material(new Texture("models/cube/default.png"),
                                              new Vector3f(1, 1, 1), 0.4f, 8);
        Material angelMaterial = new Material(new Texture("models/cube/default.png"),
                                              new Vector3f(0, 1, 0), 1.0f, 8);

        GameObject planeObject = new GameObject().addComponent(new MeshRenderer(new Mesh(vertices, indices, true), planeMaterial));
        GameObject angelObject = new GameObject().addComponent(new MeshRenderer(new Mesh("models/angel/angel.obj"), angelMaterial));

        getRoot().addChild(planeObject).addChild(angelObject);

        //lights
        ArrayList<GameObject> pointLightObjects = new ArrayList<>();
        pointLightObjects.add(new GameObject().addComponent(m_pointLights[0]));
        pointLightObjects.get(0).getTransform().setPosition(new Vector3f(0.0f, 2.0f, -2.0f));

        pointLightObjects.add(new GameObject().addComponent(m_pointLights[1]));
        pointLightObjects.get(1).getTransform().setPosition(new Vector3f(2.0f, 2.0f, 0.0f));

        ArrayList<GameObject> spotLightObjects = new ArrayList<>();
        spotLightObjects.add(new GameObject().addComponent(m_spotLights[0]));
        spotLightObjects.get(0).getTransform().setPosition(new Vector3f(2.0f, 5.0f, 2.0f))
                                              .setRotation(new Quaternion().initRotationDeg(new Vector3f(1, 0, 0), -150));

        getRoot().addChildren(pointLightObjects);
        getRoot().addChildren(spotLightObjects);
        getRoot().addComponent(m_directionalLight);

        //camera
        GameObject cameraObject = new GameObject();
        cameraObject.getTransform().setPosition(new Vector3f(1, 1, 1))
                                   .setRotation(new Quaternion().initRotationDeg(new Vector3f(1, 0, -1), 0));
        getRoot().addChild(cameraObject.addComponent(new PerspectiveCamera((float)Math.toRadians(60), Window.getWidth() / (float) Window.getHeight(), 0.1f, 1000.0f)));
//        getRoot().addChild(cameraObject.addComponent(new OrthographicCamera(-10, 10, -10, 10, -100, 100)));
    }

//    private int k = 0;

//    @Override
//    public void input(float time) {
//        if (Input.getKeyDown(Input.KEY_C))
//            k = (k + 1) % m_pointLights.length;
//
//        if (Input.getKey(Input.KEY_UP))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, 0, -time * 3.0f));
//        if (Input.getKey(Input.KEY_DOWN))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, 0, time * 3.0f));
//        if (Input.getKey(Input.KEY_LEFT))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(-time * 3.0f, 0, 0));
//        if (Input.getKey(Input.KEY_RIGHT))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(time * 3.0f, 0, 0));
//        if (Input.getKey(Input.KEY_SPACE))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, time * 3.0f, 0));
//        if (Input.getKey(Input.KEY_LSHIFT))
//            m_pointLights[k].getTransform().getPosition().add(new Vector3f(0, -time * 3.0f, 0));
//        if (Input.getKey(Input.KEY_ADD))
//            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() + time * 3.0f);
//        if (Input.getKey(Input.KEY_SUBTRACT))
//            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() - time * 3.0f);
//    }
}