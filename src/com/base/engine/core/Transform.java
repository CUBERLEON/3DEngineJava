package com.base.engine.core;

import com.base.engine.components.Camera;

public class Transform {

    private Vector3f m_position;
    private Quaternion m_rotation;
    private Vector3f m_scale;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Quaternion(0, 0, 0, 1), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f position, Quaternion rotation, Vector3f scale) {
        m_position = position;
        m_rotation = rotation.getNormalized();
        m_scale = scale;
    }

    public Matrix4f getModelTransform() {
        Matrix4f position = new Matrix4f().initTranslation(m_position);
        Matrix4f rotation = new Matrix4f().initRotation(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        return position.getMul(rotation.getMul(scale));
    }

    public Matrix4f getModelViewProjectionTransform(Camera camera) {
        return camera.getViewProjectionTransform().getMul(getModelTransform());
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public Transform setPosition(Vector3f position) {
        this.m_position = position;
        return this;
    }

    public Transform setPosition(float x, float y, float z) {
        this.m_position.set(x, y, z);
        return this;
    }

    public Transform setRotation(Quaternion rotation) {
        this.m_rotation = rotation.getNormalized();
        return this;
    }

    public Quaternion getRotation() {
        return m_rotation;
    }

//    public void setRotationRad(float x, float y, float z) {
//        this.m_rotation = new Vector3f(x, y, z);
//    }
//
//    public void setRotationDeg(Vector3f rotation) {
//        this.setRotationDeg(rotation.getX(), rotation.getY(), rotation.getZ());
//    }
//
//    public void setRotationDeg(float x, float y, float z) {
//        this.m_rotation = new Vector3f((float)Math.toRadians(x), (float)Math.toRadians(y), (float)Math.toRadians(z));
//    }

    public Vector3f getScale() {
        return m_scale;
    }

    public Transform setScale(Vector3f scale) {
        this.m_scale = scale;
        return this;
    }

    public Transform setScale(float x, float y, float z) {
        this.m_scale.set(x, y, z);
        return this;
    }
}
