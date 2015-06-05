package com.base.engine;

public class Transform {

    private Vector3f m_translation;
    private Vector3f m_rotation; //angles in radians
    private Vector3f m_scale;

    public Transform() {
        m_translation = new Vector3f(0, 0, 0);
        m_rotation = new Vector3f(0, 0, 0);
        m_scale = new Vector3f(0, 0, 0);
    }

    public Matrix4f getTransformM() {
        Matrix4f translation = new Matrix4f().initTranslation(m_translation);
        Matrix4f rotation = new Matrix4f().initRotationRad(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        return translation.mul(rotation.mul(scale));
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
