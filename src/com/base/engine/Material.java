package com.base.engine;

public class Material {

    private Texture m_texture;
    private Vector3f m_color;
    private float m_specularIntensity;
    private float m_specularPower;

    Material(Texture texture) {
        this(texture, new Vector3f(1, 1, 1));
    }

    Material(Texture texture, Vector3f color) {
        this(texture, color, 0.3f, 16);
    }

    Material(Texture texture, Vector3f color, float specularIntensity, float specularPower) {
        m_texture = texture;
        m_color = color;
        m_specularIntensity = specularIntensity;
        m_specularPower = specularPower;
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

    public float getSpecularIntensity() {
        return m_specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.m_specularIntensity = specularIntensity;
    }

    public float getSpecularPower() {
        return m_specularPower;
    }

    public void setSpecularPower(float specularPower) {
        this.m_specularPower = specularPower;
    }
}
