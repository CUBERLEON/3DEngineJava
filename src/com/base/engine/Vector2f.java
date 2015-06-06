package com.base.engine;

public class Vector2f {

    private float m_x;
    private float m_y;

    public Vector2f(float x, float y) {
        this.m_x = x;
        this.m_y = y;
    }

    public float length() {
        return (float) Math.sqrt(m_x * m_x + m_y * m_y);
    }

    public float dot(Vector2f r) {
        return m_x * r.getX() + m_y * r.getY();
    }

    public Vector2f normalize() {
        float length = length();

        m_x /= length;
        m_y /= length;

        return this;
    }

    public Vector2f getNormalized() {
        float length = length();
        return new Vector2f(m_x/length, m_y/length);
    }

    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);

        return new Vector2f((float)(m_x * cos - m_y * sin), (float)(m_x * sin + m_y * cos));
    }

    public Vector2f add(Vector2f r) {
        return new Vector2f(m_x + r.getX(), m_y + r.getY());
    }

    public Vector2f add(float r) {
        return new Vector2f(m_x + r, m_y + r);
    }

    public Vector2f mul(Vector2f r) {
        return new Vector2f(m_x * r.getX(), m_y * r.getY());
    }

    public Vector2f mul(float r) {
        return new Vector2f(m_x * r, m_y * r);
    }

    public Vector2f div(Vector2f r) {
        return new Vector2f(m_x / r.getX(), m_y / r.getY());
    }

    public Vector2f div(float r) {
        return new Vector2f(m_x / r, m_y / r);
    }
    public Vector2f sub(Vector2f r) {
        return new Vector2f(m_x - r.getX(), m_y - r.getY());
    }

    public Vector2f sub(float r) {
        return new Vector2f(m_x - r, m_y - r);
    }

    public String toString() {
        return "(" + m_x + " " + m_y + ")";
    }

    public float getX() {
        return m_x;
    }

    public void setX(float x) {
        this.m_x = x;
    }

    public float getY() {
        return m_y;
    }

    public void setY(float y) {
        this.m_y = y;
    }
}
