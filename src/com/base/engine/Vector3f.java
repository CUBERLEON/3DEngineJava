package com.base.engine;

public class Vector3f {

    private float m_x;
    private float m_y;
    private float m_z;

    public Vector3f(float x, float y, float z) {
        this.m_x = x;
        this.m_y = y;
        this.m_z = z;
    }

    public float length() {
        return (float) Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
    }

    public float dot(Vector3f r) {
        return m_x * r.getX() + m_y * r.getY() + m_z * r.getZ();
    }

    public Vector3f normalize() {
        float length = length();

        m_x /= length;
        m_y /= length;
        m_z /= length;

        return this;
    }

    public Vector3f rotate(float angle) {
        return null;
    }

    public Vector3f add(Vector3f r) {
        return new Vector3f(m_x + r.getX(), m_y + r.getY(), m_z + r.getZ());
    }

    public Vector3f add(float r) {
        return new Vector3f(m_x + r, m_y + r, m_z + r);
    }

    public Vector3f sub(Vector3f r) {
        return new Vector3f(m_x - r.getX(), m_y - r.getY(), m_z - r.getZ());
    }

    public Vector3f sub(float r) {
        return new Vector3f(m_x - r, m_y - r, m_z - r);
    }

    public Vector3f mul(Vector3f r) {
        return new Vector3f(m_x * r.getX(), m_y * r.getY(), m_z * r.getZ());
    }

    public Vector3f mul(float r) {
        return new Vector3f(m_x * r, m_y * r, m_z * r);
    }

    public Vector3f div(Vector3f r) {
        return new Vector3f(m_x / r.getX(), m_y / r.getY(), m_z / r.getZ());
    }

    public Vector3f div(float r) {
        return new Vector3f(m_x / r, m_y / r, m_z / r);
    }

    public Vector3f cross(Vector3f r) {
        float x_ = m_y * r.getZ() - m_z * r.getY();
        float y_ = m_z * r.getX() - m_x * r.getZ();
        float z_ = m_x * r.getY() - m_y * r.getX();

        return new Vector3f(x_, y_, z_);
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

    public float getZ() {
        return m_z;
    }

    public void setZ(float z) {
        this.m_z = z;
    }
}
