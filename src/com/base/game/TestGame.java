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
        m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f, new Vector3f(0, -1, -1));
        m_pointLights = new PointLight[] { new PointLight(new Vector3f(1, 1, 0), 3.0f, new Vector3f(0, 0, 1)),
                                           new PointLight(new Vector3f(0, 1, 1), 3.0f, new Vector3f(0, 0, 1))};
        m_spotLights = new SpotLight[] { new SpotLight(new Vector3f(1, 1, 1), 2.0f, new Vector3f(0.5f, 0.15f, 0), (float)Math.cos(Math.toRadians(15.0f)))};

        Input.setCursor(false);

        //meshes
        float fieldDepth = 8.0f;
        float fieldWidth = 8.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                                            new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth), new Vector2f(0.0f, 1.0f)),
                                            new Vertex( new Vector3f(fieldWidth, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                                            new Vertex( new Vector3f(fieldWidth, 0.0f, fieldDepth), new Vector2f(1.0f, 1.0f))};
        int indices[] = { 0, 1, 2,
                          2, 1, 3 };
        Mesh planeMesh = new Mesh(vertices, indices, true);

        Material planeMaterial = new Material(new Texture("models/test/test.png"),
                                              new Vector3f(1, 1, 1), 0.7f, 8);
        Material material1 = new Material(new Texture("models/test/test.png"),
                                               new Vector3f(0, 1, 0), 1.0f, 8);

        GameObject planeObject = new GameObject().addComponent(new MeshRenderer(planeMesh, planeMaterial));
        GameObject object1 = new GameObject().addComponent(new MeshRenderer(new Mesh("models/test/angel.obj"), material1));
//        object1.getTransform().setScale(0.02f, 0.02f, 0.02f);
        object1.getTransform().setPosition(0, 0, 0);

        getRoot().addChild(planeObject).addChild(object1);

        GameObject test1 = new GameObject().addComponent(new MeshRenderer(planeMesh, planeMaterial));
        GameObject test2 = new GameObject().addComponent(new MeshRenderer(planeMesh, planeMaterial));
        test1.getTransform().setScale(0.1f, 0.1f, 0.1f).setPosition(5, 1, -2.0f).setRotation(new Quaternion().initRotationDeg(new Vector3f(1, 0, 0), 45));
        test2.getTransform().setPosition(5, 2, 3);
        test1.addChild(test2);

        getRoot().addChild(test1);

        //lights
        ArrayList<GameObject> pointLightObjects = new ArrayList<>();
        pointLightObjects.add(new GameObject().addComponent(m_pointLights[0]));
        pointLightObjects.get(0).getTransform().setPosition(new Vector3f(0.0f, 4.0f, -3.0f));

        pointLightObjects.add(new GameObject().addComponent(m_pointLights[1]));
        pointLightObjects.get(1).getTransform().setPosition(new Vector3f(3.0f, 4.0f, 0.0f));

        ArrayList<GameObject> spotLightObjects = new ArrayList<>();
        spotLightObjects.add(new GameObject().addComponent(m_spotLights[0]));
        spotLightObjects.get(0).getTransform().setPosition(new Vector3f(0, 3, -2))
                                              .setRotation(new Quaternion().initRotationDeg(new Vector3f(1, 0, 0), -150));

        getRoot().addChildren(pointLightObjects);
        getRoot().addChildren(spotLightObjects);
//        getRoot().addComponent(m_directionalLight);

        //camera
        GameObject cameraObject = new GameObject();
        cameraObject.getTransform().setPosition(new Vector3f(5, 1, 5))
                                   .setRotation(new Quaternion().initRotationDeg(new Vector3f(0, 1, 0), 45));
        getRoot().addChild(cameraObject.addComponent(new PerspectiveCamera((float) Math.toRadians(60), Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f)));
//        cameraObject.addChild(test1);
//        getRoot().addChild(cameraObject.addComponent(new OrthographicCamera(-10, 10, -10, 10, -100, 100)));


        //Math tests
//        Vector3f in = new Vector3f(23, 13, -17);
//        float angle = 100;
//        Vector3f axis = new Vector3f(0, 1, 0);
//
//        System.out.println("Quat>Angles>Mat4:" + new Matrix4f().initRotationDeg(new Quaternion().initRotationDeg(axis, angle).toEulerAnglesDeg()));
//        System.out.println("Quat>Mat4:" + new Matrix4f().initRotation(new Quaternion().initRotationDeg(axis, angle)));
//
//        System.out.println("Quat>Angles:" + new Quaternion().initRotationDeg(axis, angle).toEulerAnglesDeg());
//        System.out.println("Quat>Angles>Mat4>mul:" + in.getMul(new Matrix4f().initRotationDeg(new Quaternion().initRotationDeg(axis, angle).toEulerAnglesDeg())));
//        System.out.println("Quat>Mat4>mul:" + in.getMul(new Matrix4f().initRotation(new Quaternion().initRotationDeg(axis, angle))));
//        System.out.println("Quat>rot:" + in.getRotated(new Quaternion().initRotationDeg(axis, angle)));
//        System.out.println("Rodrig:" + in.getRotatedDeg(axis, angle));
//
//        System.out.println("Quat>rot:" + in.getRotated(new Quaternion().initRotationDeg(new Vector3f(1, 0, 1), angle)));
//        System.out.println("Quat>Mat4>mul:" + in.getMul(new Matrix4f().initRotation(new Quaternion().initRotationDeg(new Vector3f(1, 0, 1), angle))));
//        System.out.println("Mat4:" + new Matrix4f().initRotationDeg(53, 21, -60));
//        System.out.println("Mat4:" + new Matrix4f().initRotation(new Quaternion().initRotationDeg(53, 21, -60)));
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