package com.base.engine.core;

public class Transform {

    private Vector3f m_translation;
    private Vector3f m_rotation; //angles in radians
    private Vector3f m_scale;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f translation, Vector3f rotationRad, Vector3f scale) {
        m_translation = translation;
        m_rotation = rotationRad;
        m_scale = scale;
    }

    public Matrix4f getModelTransform() {
        Matrix4f translation = new Matrix4f().initTranslation(m_translation);
        Matrix4f rotation = new Matrix4f().initRotationRad(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        return translation.getMul(rotation.getMul(scale));
    }

    public Matrix4f getModelViewProjectionTransform(Camera camera) {
        return camera.getViewProjectionTransform().getMul(getModelTransform());
    }

    public Vector3f getTranslation() {
        return m_translation;
    }

    public void setTranslation(Vector3f translation) {
        this.m_translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.m_translation = new Vector3f(x, y, z);
    }

    public Vector3f getRotationRad() {
        return m_rotation;
    }

    public void setRotationRad(Vector3f rotation) {
        this.m_rotation = rotation;
    }

    public void setRotationRad(float x, float y, float z) {
        this.m_rotation = new Vector3f(x, y, z);
    }

    public void setRotationDeg(Vector3f rotation) {
        this.setRotationDeg(rotation.getX(), rotation.getY(), rotation.getZ());
    }

    public void setRotationDeg(float x, float y, float z) {
        this.m_rotation = new Vector3f((float)Math.toRadians(x), (float)Math.toRadians(y), (float)Math.toRadians(z));
    }

    public Vector3f getScale() {
        return m_scale;
    }

    public void setScale(Vector3f scale) {
        this.m_scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.m_scale = new Vector3f(x, y, z);
    }
}
