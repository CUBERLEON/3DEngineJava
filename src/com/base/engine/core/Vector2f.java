package com.base.engine.core;

public class Vector2f {

    public static final int SIZE = 2;

    private float m_x;
    private float m_y;

    public Vector2f(float x, float y) {
        this.m_x = x;
        this.m_y = y;
    }

    public Vector2f(Vector2f r) {
        this(r.getX(), r.getY());
    }

    public float length() {
        return (float) Math.sqrt(m_x * m_x + m_y * m_y);
    }

    public float dot(Vector2f r) {
        return m_x * r.getX() + m_y * r.getY();
    }

    public Vector2f normalize() {
        float length = length();

        if (length == 0) {
            System.err.println("ERROR: division by zero while normalizing a Vector2f.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= length;
        m_y /= length;

        return this;
    }

    public Vector2f getNormalized() {
        return new Vector2f(this).normalize();
    }

    public Vector2f rotateDeg(float angle) {
        return rotateRad((float) Math.toRadians(angle));
    }

    public Vector2f rotateRad(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        m_x = m_x * cos - m_y * sin;
        m_y = m_x * sin + m_y * cos;

        return this;
    }

    public Vector2f getRotatedDeg(float angle) {
        return new Vector2f(this).rotateDeg(angle);
    }

    public Vector2f getRotatedRad(float angle) {
        return new Vector2f(this).rotateRad(angle);
    }

    public Vector2f abs() {
        m_x = Math.abs(m_x);
        m_y = Math.abs(m_y);

        return this;
    }

    public Vector2f getAbs() {
        return new Vector2f(this).abs();
    }

    public Vector2f add(Vector2f r) {
        m_x += r.getX();
        m_y += r.getY();

        return this;
    }

    public Vector2f add(float r) {
        m_x += r;
        m_y += r;

        return this;
    }

    public Vector2f getAdd(Vector2f r) {
        return new Vector2f(this).add(r);
    }

    public Vector2f getAdd(float r) {
        return new Vector2f(this).add(r);
    }

    public Vector2f sub(Vector2f r) {
        m_x -= r.getX();
        m_y -= r.getY();

        return this;
    }

    public Vector2f sub(float r) {
        m_x -= r;
        m_y -= r;

        return this;
    }

    public Vector2f getSub(Vector2f r) {
        return new Vector2f(this).sub(r);
    }

    public Vector2f getSub(float r) {
        return new Vector2f(this).sub(r);
    }

    public Vector2f mul(Vector2f r) {
        m_x *= r.getX();
        m_y *= r.getY();

        return this;
    }

    public Vector2f mul(float r) {
        m_x *= r;
        m_y *= r;

        return this;
    }

    public Vector2f getMul(Vector2f r) {
        return new Vector2f(this).mul(r);
    }

    public Vector2f getMul(float r) {
        return new Vector2f(this).mul(r);
    }

    public Vector2f div(Vector2f r) {
        if (r.getX() == 0 || r.getY() == 0) {
            System.err.println("ERROR: division by zero while dividing Vector2f by Vector2f.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= r.getX();
        m_y /= r.getY();

        return this;
    }

    public Vector2f div(float r) {
        if (r == 0) {
            System.err.println("ERROR: division by zero while dividing Vector2f by float.");
            new Exception().printStackTrace();

            return this;
        }

        m_x /= r;
        m_y /= r;

        return this;
    }

    public Vector2f getDiv(Vector2f r) {
        return new Vector2f(this).div(r);
    }

    public Vector2f getDiv(float r) {
        return new Vector2f(this).div(r);
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
