package com.base.engine.core;

import com.base.engine.components.Component;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

import java.util.ArrayList;

public class Node {

    private ArrayList<Node> m_children;
    private ArrayList<Component> m_components;
    private Transform m_transform;

    public Node() {
        m_children = new ArrayList<>();
        m_components = new ArrayList<>();
        m_transform = new Transform();
    }

    public Node addComponent(Component component) {
        component.setObject(this);
        m_components.add(component);
        return this;
    }

    public Node addComponents(Component components[]) {
        for (Component component : components) {
            addComponent(component);
        }
        return this;
    }

    public Node addComponents(ArrayList<Component> components) {
        for (Component component : components) {
            addComponent(component);
        }
        return this;
    }

    public Node addChild(Node child) {
        child.getTransform().setParent(m_transform);
        m_children.add(child);
        return this;
    }

    public Node addChildren(Node children[]) {
        for (Node child : children) {
            addChild(child);
        }
        return this;
    }

    public Node addChildren(ArrayList<Node> children) {
        for (Node child : children) {
            m_children.add(child);
        }
        return this;
    }

    public void input(float time) {
        for (Component component : m_components)
            component.input(time);

        for (Node child : m_children)
            child.input(time);
    }

    public void update(float time) {
        getTransform().update();

        for (Component component : m_components)
            component.update(time);

        for (Node child : m_children)
            child.update(time);
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        for (Component component : m_components)
            component.render(shader, renderingEngine);

        for (Node child : m_children)
            child.render(shader, renderingEngine);
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        for (Component component : m_components)
            component.addToRenderingEngine(renderingEngine);

        for (Node child : m_children)
            child.addToRenderingEngine(renderingEngine);
    }

    public Transform getTransform() {
        return m_transform;
    }
}
