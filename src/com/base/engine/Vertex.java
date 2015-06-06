package com.base.engine;

public class Vertex {

    public static final int SIZE = 3;

    private Vector3f m_position;

    public Vertex(Vector3f position) {
        this.m_position = position;
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public void setPosition(Vector3f m_pos) {
        this.m_position = m_pos;
    }
}
