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

    private Vector3f m_realPosition;
    private Quaternion m_realRotation;
    private Vector3f m_realScale;
    private Matrix4f m_realModelTransform;

    private boolean m_needUpdateRealValues;
    private boolean m_needChildrenUpdate;

    public Transform() {
        this(new Vector3f(0, 0, 0), new Quaternion(0, 0, 0, 1), new Vector3f(1, 1, 1));
    }

    public Transform(Vector3f position, Quaternion rotation, Vector3f scale) {
        m_position = position;
        m_rotation = rotation.getNormalized();
        m_scale = scale;

        m_needUpdateRealValues = true;
    }

    public void update() {
        m_needChildrenUpdate = hasChanged() || (m_parent != null && m_parent.isChildrenUpdateNeeded());

        if (m_needChildrenUpdate)
            m_needUpdateRealValues = true;

        if (m_needChildrenUpdate || m_modelTransform == null) {
            updateModelTransform();

            m_oldPosition.set(m_position);
            m_oldRotation.set(m_rotation);
            m_oldScale.set(m_scale);
        }
    }

    private boolean hasChanged() {
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

    private void updateRealValues() {
        if (m_parent != null) {
            m_realRotation = m_rotation.getMul(m_parent.getRealRotation());
            m_realScale = m_scale.getMul(m_parent.getRealScale());
            m_realPosition = m_position.getMul(m_parent.getRealScale()).rotate(m_parent.getRotation()).getAdd(m_parent.getRealPosition());
            m_realModelTransform = m_parent.getRealModelTransform().getMul(getModelTransform());
        } else {
            m_realPosition = m_position;
            m_realRotation = m_rotation;
            m_realScale = m_scale;
            m_realModelTransform = getModelTransform();
        }

        m_needUpdateRealValues = false;
    }

    private void updateModelTransform() {
        Matrix4f position = new Matrix4f().initTranslation(m_position);
        Matrix4f rotation = new Matrix4f().initRotation(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        m_modelTransform = position.getMul(rotation.getMul(scale));
    }

    public Matrix4f getModelTransform() {
        if (m_modelTransform == null)
            updateModelTransform();

        return m_modelTransform;
    }

    public Matrix4f getRealModelTransform() {
        if (m_needUpdateRealValues)
            updateRealValues();

        return m_realModelTransform;
    }

    public Matrix4f getModelViewProjectionTransform(Camera camera) {
        return camera.getViewProjectionTransform().getMul(getRealModelTransform());
    }

    public Transform rotate(Quaternion rotation) {
        m_rotation = rotation.getMul(m_rotation);

        return this;
    }

    public boolean isChildrenUpdateNeeded() {
        return m_needChildrenUpdate;
    }

    public Transform getParent() {
        return m_parent;
    }

    public void setParent(Transform parent) {
        m_parent = parent;
    }

    public Vector3f getRealPosition() {
        if (m_needUpdateRealValues)
            updateRealValues();

        return m_realPosition;
    }

    public Quaternion getRealRotation() {
        if (m_needUpdateRealValues)
            updateRealValues();

        return m_realRotation;
    }

    public Vector3f getRealScale() {
        if (m_needUpdateRealValues)
            updateRealValues();

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
}
