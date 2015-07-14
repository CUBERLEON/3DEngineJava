package com.base.engine.core;

import com.base.engine.core.components.GameComponent;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

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
        component.setObject(this);
        return this;
    }

    public GameObject addComponents(GameComponent components[]) {
        for (GameComponent component : components) {
            component.setObject(this);
            m_components.add(component);
        }
        return this;
    }

    public GameObject addChild(GameObject child) {
        m_children.add(child);
        return this;
    }

    public GameObject addChildren(GameObject children[]) {
        for (GameObject child : children) {
            m_children.add(child);
        }
        return this;
    }

    public void input(float time) {
        for (GameComponent component : m_components)
            component.input(time);

        for (GameObject child : m_children)
            child.input(time);
    }

    public void update(float time) {
        for (GameComponent component : m_components)
            component.update(time);

        for (GameObject child : m_children)
            child.update(time);
    }

    public void render(Shader shader) {
        for (GameComponent component : m_components)
            component.render(shader);

        for (GameObject child : m_children)
            child.render(shader);
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        for (GameComponent component : m_components)
            component.addToRenderingEngine(renderingEngine);

        for (GameObject child : m_children)
            child.addToRenderingEngine(renderingEngine);
    }

    public Transform getTransform() {
        return m_transform;
    }
}
