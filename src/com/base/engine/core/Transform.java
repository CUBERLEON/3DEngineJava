package com.base.engine.core;

import com.base.engine.components.Camera;

import java.lang.ref.WeakReference;

public class Transform {

    private WeakReference<Transform> m_parent;

    private Vector3f m_position;
    private Quaternion m_rotation;
    private Vector3f m_scale;
    
    private Vector3f m_oldPosition;
    private Quaternion m_oldRotation;
    private Vector3f m_oldScale;

    private Vector3f m_realPosition;
    private Quaternion m_realRotation;
    private Vector3f m_realScale;

    private Matrix4f m_modelTransform;
    private Matrix4f m_realModelTransform;

    private boolean m_needChildrenUpdate;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Quaternion(0, 0, 0, 1), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f position, Quaternion rotation, Vector3f scale) {
        m_position = position;
        m_rotation = rotation.getNormalized();
        m_scale = scale;

        m_parent = new WeakReference(null);

        updateModelTransform();
        updateRealValues();

        m_oldPosition = new Vector3f(m_position);
        m_oldRotation = new Quaternion(m_rotation);
        m_oldScale = new Vector3f(m_scale);
    }

    public void update() {
        boolean hasChanged = hasChanged();

        if (hasChanged) {
            updateModelTransform();

            m_oldPosition.set(m_position);
            m_oldRotation.set(m_rotation);
            m_oldScale.set(m_scale);
        }

        if (m_needChildrenUpdate = hasChanged ||
                                   (getParent() != null && getParent().isChildrenUpdateNeeded())) {
            updateRealValues();
        }
    }

    private boolean hasChanged() {
        if (!m_oldPosition.equals(m_position)) return true;
        if (!m_oldRotation.equals(m_rotation)) return true;
        if (!m_oldScale.equals(m_scale)) return true;

        return false;
    }

    private void updateRealValues() {
        if (getParent() != null) {
            m_realRotation = getParent().getRealRotation().getMul(m_rotation);
            m_realScale = m_scale.getMul(getParent().getRealScale());
            m_realPosition = m_position.getMul(getParent().getRealScale()).rotate(getParent().getRealRotation()).add(getParent().getRealPosition());
            m_realModelTransform = getParent().getRealModelTransform().getMul(m_modelTransform);
        } else {
            m_realPosition = m_position;
            m_realRotation = m_rotation;
            m_realScale = m_scale;
            m_realModelTransform = m_modelTransform;
        }
    }

    private void updateModelTransform() {
        Matrix4f translation = new Matrix4f().initTranslation(m_position);
        Matrix4f rotation = new Matrix4f().initRotation(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        m_modelTransform = translation.getMul(rotation.getMul(scale));
    }

    public Matrix4f getModelTransform() {
        return m_modelTransform;
    }

    public Matrix4f getRealModelTransform() {
        return m_realModelTransform;
    }

    public Matrix4f getModelViewProjectionTransform(Camera camera) {
        return camera.getViewProjectionTransform().getMul(m_realModelTransform);
    }

    public Transform rotate(Quaternion rotation) {
        m_rotation = rotation.getMul(m_rotation);

        return this;
    }

    public boolean isChildrenUpdateNeeded() {
        return m_needChildrenUpdate;
    }

    public Vector3f getRealPosition() {
        return m_realPosition;
    }

    public Quaternion getRealRotation() {
        return m_realRotation;
    }

    public Vector3f getRealScale() {
        return m_realScale;
    }

    public Vector3f getPosition() {
        return m_position;
    }

    public Transform setPosition(Vector3f position) {
        m_position = position;
        return this;
    }

    public Transform setPosition(float x, float y, float z) {
        m_position.set(x, y, z);
        return this;
    }

    public Quaternion getRotation() {
        return m_rotation;
    }

    public Transform setRotation(Quaternion rotation) {
        m_rotation = rotation.getNormalized();
        return this;
    }

    public Vector3f getScale() {
        return m_scale;
    }

    public Transform setScale(Vector3f scale) {
        m_scale = scale;
        return this;
    }

    public Transform setScale(float x, float y, float z) {
        m_scale.set(x, y, z);
        return this;
    }

    public void setParent(Transform parent) {
        m_parent = new WeakReference(parent);
    }

    public Transform getParent() {
        return m_parent.get();
    }
}
