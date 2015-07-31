package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;

import java.util.ArrayList;

public abstract class Game {

    private Node m_root;

    public Game() {
        m_root = new Node();
    }

    public void init() {}

    public void input(float time) {
        m_root.input(time);
    }

    public void update(float time) {
        m_root.update(time);
    }

    public void render(RenderingEngine renderingEngine) {
        renderingEngine.render(m_root);
    }

    public void addObject(Node object) {
        m_root.addChild(object);
    }

    public void addObjects(Node objects[]) {
        m_root.addChildren(objects);
    }

    public void addObjects(ArrayList<Node> objects) {
        m_root.addChildren(objects);
    }
}
