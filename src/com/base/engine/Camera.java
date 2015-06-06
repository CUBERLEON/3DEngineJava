package com.base.engine;

public class Camera {

    private Vector3f m_position;
    private Vector3f m_forward;
    private Vector3f m_up;

    public Camera() {
        m_position = new Vector3f(0, 0, 0);
        m_forward = new Vector3f(0, 0, -1);
        m_up = new Vector3f(0, 1, 0);
    }

    public Camera(Vector3f position, Vector3f forward, Vector3f up) {
        m_position = position;
        m_forward = forward;
        m_up = up;

        m_forward.normalize();
        m_up.normalize();
    }

    public void move(Vector3f dir, float value) {
        m_position.add(dir.normalize().mul(value));
    }

    public Vector3f getLeft() {
        return m_up.cross(m_forward).normalize();
    }

    public Vector3f getRight() {
        return m_forward.cross(m_up).normalize();
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public void setPosition(Vector3f position) {
        this.m_position = position;
    }

    public Vector3f getForward() {
        return m_forward;
    }

    public void setForward(Vector3f forward) {
        this.m_forward = forward;
    }

    public Vector3f getUp() {
        return m_up;
    }

    public void setUp(Vector3f up) {
        this.m_up = up;
    }
}
