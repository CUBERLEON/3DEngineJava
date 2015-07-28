package com.base.engine.core;

public class Vector3f {

    public static final int SIZE = 3;

    public static final Vector3f xAxis = new Vector3f(1, 0, 0);
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);
    public static final Vector3f zAxis = new Vector3f(0, 0, 1);

    private float m_x;
    private float m_y;
    private float m_z;

    public Vector3f() {
        this(0, 0, 0);
    }

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

    public float min() {
        return Math.min(m_x, Math.min(m_y, m_z));
    }

    public float max() {
        return Math.max(m_x, Math.max(m_y, m_z));
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

    public Vector3f getNormalized() {
        return new Vector3f(this).normalize();
    }

    public Vector3f rotate(Quaternion rotation) {
        Quaternion m = rotation.getMul(this).mul(rotation.getConjugated());

        set(m.getX(), m.getY(), m.getZ());

        return this;
    }

    public Vector3f getRotated(Quaternion rotation) {
        return new Vector3f(this).rotate(rotation);
    }

    public Vector3f rotateRad(Vector3f axis, float angle) {
        Vector3f m = getRotatedRad(axis, angle);
        set(m.getX(), m.getY(), m.getZ());

        return this;
    }

    public Vector3f getRotatedRad(Vector3f axis, float angle) {
        Vector3f _axis = axis.getNormalized();

        float sinAngle = (float)Math.sin(angle);
        float cosAngle = (float)Math.cos(angle);

        //Rodrigues' rotation formula
        return _axis.getCross(this).getMul(sinAngle).add(
                this.getMul(cosAngle).add(
                        _axis.getMul(_axis.dot(this) * (1.0f - cosAngle))));
    }

    public Vector3f rotateDeg(Vector3f axis, float angle) {
        return rotateRad(axis, (float) Math.toRadians(angle));
    }

    public Vector3f getRotatedDeg(Vector3f axis, float angle) {
        return getRotatedRad(axis, (float) Math.toRadians(angle));
    }

    public Vector3f lerp(Vector3f dest, float factor) {
        return this.add(dest.getSub(this).mul(factor));
    }

    public Vector3f getLerp(Vector3f dest, float factor) {
        return new Vector3f(this).lerp(dest, factor);
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

    public Vector3f mul(Matrix4f r) {
        float x_ = m_x * r.get(0, 0) + m_y * r.get(0, 1) + m_z * r.get(0, 2) + r.get(0, 3);
        float y_ = m_x * r.get(1, 0) + m_y * r.get(1, 1) + m_z * r.get(1, 2) + r.get(1, 3);
        float z_ = m_x * r.get(2, 0) + m_y * r.get(2, 1) + m_z * r.get(2, 2) + r.get(2, 3);

        set(x_, y_, z_);

        return this;
    }

    public Vector3f getMul(Vector3f r) {
        return new Vector3f(this).mul(r);
    }

    public Vector3f getMul(float r) {
        return new Vector3f(this).mul(r);
    }

    public Vector3f getMul(Matrix4f r) {
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
        return "(" + m_x + ", " + m_y + ", " + m_z + ")";
    }

    public void set(float x, float y, float z) {
        m_x = x;
        m_y = y;
        m_z = z;
    }

    public void set(Vector3f r) {
        set(r.getX(), r.getY(), r.getZ());
    }

    public Vector3f toRadians() {
        return new Vector3f((float)Math.toRadians(m_x), (float)Math.toRadians(m_y), (float)Math.toRadians(m_z));
    }

    public Vector3f toDegrees() {
        return new Vector3f((float)Math.toDegrees(m_x), (float)Math.toDegrees(m_y), (float)Math.toDegrees(m_z));
    }

    public Vector2f getXY() {
        return new Vector2f(m_x, m_y);
    }

    public Vector2f getXZ() {
        return new Vector2f(m_x, m_z);
    }

    public Vector2f getYX() {
        return new Vector2f(m_y, m_x);
    }

    public Vector2f getYZ() {
        return new Vector2f(m_y, m_z);
    }

    public Vector2f getZX() {
        return new Vector2f(m_z, m_x);
    }

    public Vector2f getZY() {
        return new Vector2f(m_z, m_y);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3f vector3f = (Vector3f) o;

        if (Float.compare(vector3f.m_x, m_x) != 0 || Float.compare(vector3f.m_y, m_y) != 0) return false;
        return Float.compare(vector3f.m_z, m_z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (m_x != +0.0f ? Float.floatToIntBits(m_x) : 0);
        result = 31 * result + (m_y != +0.0f ? Float.floatToIntBits(m_y) : 0);
        result = 31 * result + (m_z != +0.0f ? Float.floatToIntBits(m_z) : 0);
        return result;
    }
}
