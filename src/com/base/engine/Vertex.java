package com.base.engine;

public class Vertex {

    public static final int SIZE = 5;

    private Vector3f m_position;
    private Vector2f m_texCoord;

    public Vertex(Vector3f position) {
        this(position, new Vector2f(0, 0));
    }

    public Vertex(Vector3f position, Vector2f texCoord) {
        m_position = position;
        m_texCoord = texCoord;
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public void setPosition(Vector3f m_pos) {
        this.m_position = m_pos;
    }

    public Vector2f getTexCoord() {
        return m_texCoord;
    }

    public void setTexCoord(Vector2f texCoord) {
        m_texCoord = texCoord;
    }
}
