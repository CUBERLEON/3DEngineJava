package com.base.engine;

public class Vertex {

    public static final int SIZE = 3;

    private Vector3f m_pos;

    public Vertex(Vector3f pos) {
        this.m_pos = pos;
    }

    public Vector3f getPos() {
        return m_pos;
    }

    public void setPos(Vector3f m_pos) {
        this.m_pos = m_pos;
    }
}
