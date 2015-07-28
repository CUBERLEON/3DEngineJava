package com.base.engine.core;

import com.base.engine.components.Camera;

public class Transform {

    private Transform m_parent;

    private Matrix4f m_modelTransform;

    private Vector3f m_position;
    private Quaternion m_rotation;
    private Vector3f m_scale;

    private Vector3f m_oldPosition;
    private Quaternion m_oldRotation;
    private Vector3f m_oldScale;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Quaternion(0, 0, 0, 1), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f position, Quaternion rotation, Vector3f scale) {
        m_position = position;
        m_rotation = rotation.getNormalized();
        m_scale = scale;
    }

    public boolean hasChanged() {
        if (m_oldPosition == null) {
            m_oldPosition = new Vector3f(m_position);
            m_oldRotation = new Quaternion(m_rotation);
            m_oldScale = new Vector3f(m_scale);
            return true;
        }

        if (!m_oldPosition.equals(m_position)) return true;
        if (!m_oldRotation.equals(m_rotation)) return true;
        if (!m_oldScale.equals(m_scale)) return true;

        return false;
    }

    public Matrix4f getModelTransform() {
        if (hasChanged() || m_modelTransform == null) {
            Matrix4f position = new Matrix4f().initTranslation(m_position);
            Matrix4f rotation = new Matrix4f().initRotation(m_rotation);
            Matrix4f scale = new Matrix4f().initScale(m_scale);

            m_modelTransform = position.getMul(rotation.getMul(scale));
        }

        m_oldPosition.set(m_position);
        m_oldRotation.set(m_rotation);
        m_oldScale.set(m_scale);

        if (m_parent == null)
            return new Matrix4f(m_modelTransform);
        else
            return m_parent.getModelTransform().getMul(m_modelTransform);
    }

    public Matrix4f getModelViewProjectionTransform(Camera camera) {
        return camera.getViewProjectionTransform().getMul(getModelTransform());
    }

    public Transform rotate(Quaternion rotation) {
        m_rotation = rotation.getMul(m_rotation);

        return this;
    }

    public Transform getParent() {
        return m_parent;
    }

    public void setParent(Transform parent) {
        m_parent = parent;
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
