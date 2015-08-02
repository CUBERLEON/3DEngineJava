package com.base.engine.core;

import com.base.engine.components.Component;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.shaders.Shader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class Node {

    private Node m_parent;

    private ArrayList<Node> m_children;
    private HashMap<String, WeakReference<Node>> m_childrenMap;

    private ArrayList<Component> m_components;
    private HashMap<String, WeakReference<Component>> m_componentsMap;

    private Transform m_transform;

    private String m_name;

    public Node(String name) {
        m_children = new ArrayList<>();
        m_childrenMap = new HashMap<>();

        m_components = new ArrayList<>();
        m_componentsMap = new HashMap<>();

        m_transform = new Transform();

        m_name = name;
    }

    public Node addComponent(Component component) {
        if (m_componentsMap.containsKey(component.getClass().getName())) {
            System.err.println("ERROR: cannot add second "+component.getClass().getName()+" Component to the Node('"+m_name+"')");
        } else {
            component.setNode(this);
            m_componentsMap.put(component.getClass().getName(), new WeakReference(component));
            m_components.add(component);
        }

        return this;
    }

    public Node addComponents(Component... components) {
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

    public <Type> Type getComponent(Class<Type> componentClass) {
        if (componentClass == null)
            return null;

        return componentClass.cast(m_componentsMap.get(componentClass.getName()).get());
    }

    public ArrayList<Component> getComponents() {
        return m_components;
    }

    public <Type> Node removeComponent(Class<Type> componentClass) {
        if (m_componentsMap.containsKey(componentClass.getName())) {
            m_components.remove(m_componentsMap.get(componentClass.getName()).get());
            m_componentsMap.remove(componentClass.getName());
        }

        return this;
    }

    public Node removeComponent(Component component) {
        if (m_componentsMap.containsKey(component.getClass().getName())) {
            m_components.remove(component);
            m_componentsMap.remove(component.getClass().getName());
        }

        return this;
    }

    public Node addChild(Node child) {
        if (m_childrenMap.containsKey(child.getName())) {
            System.err.println("ERROR: cannot add second Node('"+child.getName()+"') to the Node('"+m_name+"')");
        } else {
            child.setParent(this);
            child.getTransform().setParent(m_transform);

            m_childrenMap.put(child.getName(), new WeakReference(child));
            m_children.add(child);
        }

        return this;
    }

    public Node addChildren(Node... children) {
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

    public Node getChildByName(String name) {
        return m_childrenMap.get(name).get();
    }

    public ArrayList<Node> getChildren() {
        return m_children;
    }

    public Node removeChildByName(String name) {
        if (m_childrenMap.containsKey(name)) {
            m_children.remove(m_childrenMap.get(name).get());
            m_childrenMap.remove(name);
        }

        return this;
    }

    public Node removeChild(Node child) {
        if (m_childrenMap.containsKey(child.getName())) {
            m_children.remove(child);
            m_childrenMap.remove(child.getName());
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
        m_transform.update();

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

    public void setParent(Node parent) {
        m_parent = parent;
    }

    protected Node getParent() {
        return m_parent;
    }

    public String getName() {
        return m_name;
    }

    public Transform getTransform() {
        return m_transform;
    }
}
