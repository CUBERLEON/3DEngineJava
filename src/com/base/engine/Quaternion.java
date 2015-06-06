package com.base.engine;

public class Quaternion {

    private float m_x;
    private float m_y;
    private float m_z;
    private float m_w;

    public Quaternion(float m_x, float m_y, float m_z, float m_w) {
        this.m_x = m_x;
        this.m_y = m_y;
        this.m_z = m_z;
        this.m_w = m_w;
    }

    public float length() {
        return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
    }

    public Quaternion normalize() {
        float length = length();

        m_x /= length;
        m_y /= length;
        m_z /= length;
        m_w /= length;

        return this;
    }

    public Quaternion getNormalized() {
        float length = length();
        return new Quaternion(m_x/length, m_y/length, m_z/length, m_w/length);
    }

    public Quaternion mul(Quaternion r) {
        float w_ = m_w * r.getW() - m_x * r.getX() - m_y * r.getY() - m_z * r.getZ();
        float x_ = m_x * r.getW() + m_w * r.getX() + m_y * r.getZ() - m_z * r.getY();
        float y_ = m_y * r.getW() + m_w * r.getY() + m_z * r.getX() - m_x * r.getZ();
        float z_ = m_z * r.getW() + m_w * r.getZ() + m_x * r.getY() - m_y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector3f r) {
        float w_ = -m_x * r.getX() - m_y * r.getY() - m_z * r.getZ();
        float x_ =  m_w * r.getX() + m_y * r.getZ() - m_z * r.getY();
        float y_ =  m_w * r.getY() + m_z * r.getX() - m_x * r.getZ();
        float z_ =  m_w * r.getZ() + m_x * r.getY() - m_y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion conjugate() {
        m_x = -m_x;
        m_y = -m_y;
        m_z = -m_z;
        return this;
    }

    public Quaternion getConjugated() {
        return new Quaternion(-m_x, -m_y, -m_z, m_w);
    }

    public float getX() {
        return m_x;
    }

    public void setX(float m_x) {
        this.m_x = m_x;
    }

    public float getY() {
        return m_y;
    }

    public void setY(float m_y) {
        this.m_y = m_y;
    }

    public float getZ() {
        return m_z;
    }

    public void setZ(float m_z) {
        this.m_z = m_z;
    }

    public float getW() {
        return m_w;
    }

    public void setW(float m_w) {
        this.m_w = m_w;
    }
}
