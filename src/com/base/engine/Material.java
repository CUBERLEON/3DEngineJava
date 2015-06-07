package com.base.engine;

public class Material {

    private Texture m_texture;
    private Vector3f m_color;

    Material(Texture texture, Vector3f color) {
        m_texture = texture;
        m_color = color;
    }

    public Texture getTexture() {
        return m_texture;
    }

    public void setTexture(Texture texture) {
        this.m_texture = texture;
    }

    public Vector3f getColor() {
        return m_color;
    }

    public void setColor(Vector3f color) {
        this.m_color = color;
    }
}
