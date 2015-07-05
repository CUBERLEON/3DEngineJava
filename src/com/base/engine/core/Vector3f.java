package com.base.engine.core;

public class Vector3f {

    public static final int SIZE = 3;

    public static final Vector3f xAxis = new Vector3f(1, 0, 0);
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    public static final Vector3f zAxis = new Vector3f(0, 0, 1);

    private float m_x;
    private float m_y;
    private float m_z;

    public Vector3f(float x, float y, float z) {
        this.m_x = x;
        this.m_y = y;
        this.m_z = z;
    }

    public Vector3f(Vector3f r) {
        this(r.getX(), r.getY(), r.getZ());
    }

    public float length() {
        return (float) Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z);
    }

    public float dot(Vector3f r) {
        return m_x * r.getX() + m_y * r.getY() + m_z * r.getZ();
    }

    public Vector3f normalize() {
        float length = length();

        if (length == 0) {
            System.err.println("ERROR: division by zero while normalizing Vector3f.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= length;
        m_y /= length;
        m_z /= length;

        return this;
    }

    public Vector3f getNormalized() {
        return new Vector3f(this).normalize();
    }

    public Vector3f rotateDeg(Vector3f axis, float angle) {
        return rotateRad(axis, (float)Math.toDegrees(angle));
    }

    public Vector3f rotateRad(Vector3f axis, float angle) {
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);

        float rX = axis.getX() * sinHalfAngle;
        float rY = axis.getY() * sinHalfAngle;
        float rZ = axis.getZ() * sinHalfAngle;
        float rW = cosHalfAngle;

        Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
        Quaternion m = rotation.mul(this).mul(rotation.conjugate());

        m_x = m.getX();
        m_y = m.getY();
        m_z = m.getZ();

        return this;
    }

    public Vector3f getRotatedRad(Vector3f axis, float angle) {
        return new Vector3f(this).rotateRad(axis, angle);
    }

    public Vector3f getRotatedDeg(Vector3f axis, float angle) {
        return new Vector3f(this).rotateDeg(axis, angle);
    }

    public Vector3f abs() {
        m_x = Math.abs(m_x);
        m_y = Math.abs(m_y);
        m_z = Math.abs(m_z);

        return this;
    }

    public Vector3f getAbs() {
        return new Vector3f(this).abs();
    }

    public Vector3f add(Vector3f r) {
        m_x += r.getX();
        m_y += r.getY();
        m_z += r.getZ();

        return this;
    }

    public Vector3f add(float r) {
        m_x += r;
        m_y += r;
        m_z += r;

        return this;
    }

    public Vector3f getAdd(Vector3f r) {
        return new Vector3f(this).add(r);
    }

    public Vector3f getAdd(float r) {
        return new Vector3f(this).add(r);
    }

    public Vector3f sub(Vector3f r) {
        m_x -= r.getX();
        m_y -= r.getY();
        m_z -= r.getZ();

        return this;
    }

    public Vector3f sub(float r) {
        m_x -= r;
        m_y -= r;
        m_z -= r;

        return this;
    }

    public Vector3f getSub(Vector3f r) {
        return new Vector3f(this).sub(r);
    }

    public Vector3f getSub(float r) {
        return new Vector3f(this).sub(r);
    }

    public Vector3f mul(Vector3f r) {
        m_x *= r.getX();
        m_y *= r.getY();
        m_z *= r.getZ();

        return this;
    }

    public Vector3f mul(float r) {
        m_x *= r;
        m_y *= r;
        m_z *= r;

        return this;
    }

    public Vector3f getMul(Vector3f r) {
        return new Vector3f(this).mul(r);
    }

    public Vector3f getMul(float r) {
        return new Vector3f(this).mul(r);
    }

    public Vector3f div(Vector3f r) {
        if (r.getX() == 0 || r.getY() == 0 || r.getZ() == 0) {
            System.err.println("ERROR: division by zero while dividing Vector3f by Vector3f.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= r.getX();
        m_y /= r.getY();
        m_z /= r.getZ();

        return this;
    }

    public Vector3f div(float r) {
        if (r == 0) {
            System.err.println("ERROR: division by zero while dividing Vector3f by float.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= r;
        m_y /= r;
        m_z /= r;

        return this;
    }

    public Vector3f getDiv(Vector3f r) {
        return new Vector3f(this).div(r);
    }

    public Vector3f getDiv(float r) {
        return new Vector3f(this).div(r);
    }

    public Vector3f cross(Vector3f r) {
        float x_ = m_y * r.getZ() - m_z * r.getY();
        float y_ = m_z * r.getX() - m_x * r.getZ();
        float z_ = m_x * r.getY() - m_y * r.getX();

        m_x = x_;
        m_y = y_;
        m_z = z_;

        return this;
    }

    public Vector3f getCross(Vector3f r) {
        return new Vector3f(this).cross(r);
    }

    public String toString() {
        return "(" + m_x + " " + m_y + " " + m_z + ")";
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
