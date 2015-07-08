package com.base.engine.core;

import com.base.engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject {

    private ArrayList<GameObject> m_children;
    private ArrayList<GameComponent> m_components;
    private Transform m_transform;

    public GameObject() {
        m_children = new ArrayList<>();
        m_components = new ArrayList<>();
        m_transform = new Transform();
    }

    public GameObject addComponent(GameComponent component) {
        m_components.add(component);
        return this;
    }

    public GameObject addChild(GameObject child) {
        m_children.add(child);
        return this;
    }

    public void input() {
        for (GameComponent component : m_components)
            component.input(m_transform);

        for (GameObject child : m_children)
            child.input();
    }

    public void update() {
        for (GameComponent component : m_components)
            component.update(m_transform);

        for (GameObject child : m_children)
            child.update();
    }

    public void render(Shader shader) {
        for (GameComponent component : m_components)
            component.render(m_transform, shader);

        for (GameObject child : m_children)
            child.render(shader);
    }

    public Transform getTransform() {
        return m_transform;
    }
}
