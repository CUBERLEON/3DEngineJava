package com.cuberleon.engine.rendering;

import com.cuberleon.engine.core.Vector2f;
import com.cuberleon.engine.core.Vector3f;

public class Vertex {

    public static final int SIZE = 8;

    private Vector3f m_position;
    private Vector2f m_texCoord;
    private Vector3f m_normal;

    public Vertex(Vector3f position) {
        this(position, new Vector2f(-1, -1), new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f position, Vector2f texCoord) {
        this(position, texCoord, new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        m_position = position;
        m_texCoord = texCoord;
        m_normal = normal;
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

    public Vector3f getNormal() {
        return m_normal;
    }

    public void setNormal(Vector3f normal) {
        this.m_normal = normal;
    }
}
