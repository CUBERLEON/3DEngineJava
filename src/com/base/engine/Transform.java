package com.base.engine;

public class Transform {

    private Vector3f m_translation;
    private Vector3f m_rotation; //angles in radians
    private Vector3f m_scale;

    private static float m_zNear;
    private static float m_zFar;
    private static float m_FOV;
    private static Matrix4f m_projection;
    private static Camera m_camera;

    public Transform() {
        m_translation = new Vector3f(0, 0, 0);
        m_rotation = new Vector3f(0, 0, 0);
        m_scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformM() {
        Matrix4f translation = new Matrix4f().initTranslation(m_translation);
        Matrix4f rotation = new Matrix4f().initRotationRad(m_rotation);
        Matrix4f scale = new Matrix4f().initScale(m_scale);

        return translation.mul(rotation.mul(scale));
    }

    public Matrix4f getProjectedTransformM() {
        if (m_projection == null) {
            System.err.println("Error: projection matrix wasn't initialized!");
            new Exception().printStackTrace();
            System.exit(1);
        }
        if (m_camera == null) {
            System.err.println("Error: camera wasn't set to transform!");
            new Exception().printStackTrace();
            System.exit(1);
        }

        Matrix4f cameraRotation = new Matrix4f().initCamera(m_camera.getForward(), m_camera.getUp());
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(m_camera.getPosition().mul(-1));

        return m_projection.mul(cameraRotation.mul(cameraTranslation.mul(getTransformM())));
    }

    public static void setProjection(float fov, float zNear, float zFar) {
        m_FOV = fov;
        m_zNear = zNear;
        m_zFar = zFar;
        updateProjection();
    }

    public static void updateProjection() {
        m_projection = new Matrix4f().initProjection(m_FOV, Window.getWidth(), Window.getHeight(), m_zNear, m_zFar);
    }

    public static Camera getCamera() {
        return m_camera;
    }

    public static void setCamera(Camera m_camera) {
        Transform.m_camera = m_camera;
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
