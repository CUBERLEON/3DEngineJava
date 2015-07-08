package com.base.engine.core;

import com.base.engine.rendering.Window;

public class Camera {

    private Vector3f m_position;
    private Vector3f m_forward;
    private Vector3f m_up;

    private boolean m_isLocked;

    private float m_sensitivity;
    private float m_speed;

    private Matrix4f m_projectionTransform;

    public Camera(float fov, float aspectRatio, float zNear, float zFar) {
        this(new Vector3f(5, 5, 5), new Vector3f(-1, -1, -1), new Vector3f(-1, 2, -1), fov, aspectRatio, zNear, zFar);
    }

    public Camera(Vector3f position, Vector3f forward, Vector3f up, float fov, float aspectRatio, float zNear, float zFar) {
        m_position = position;
        m_forward = forward;
        m_up = up;

        m_forward.getNormalized();
        m_up.getNormalized();

        m_isLocked = false;
        m_sensitivity = 8.0f;
        m_speed = 6.0f;

        m_projectionTransform = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getViewProjectionTransform() {
        Matrix4f cameraRotation = new Matrix4f().initRotation(m_forward, m_up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(m_position.getMul(-1));

        Matrix4f viewTransform = cameraRotation.getMul(cameraTranslation);

        return m_projectionTransform.getMul(viewTransform);
    }

    public void input() {
        if (!m_isLocked) {
            float moveValue = (float) (m_speed * Time.getDelta());

            if (Input.getKey(Input.KEY_W))
                move(getForward(), moveValue);
            if (Input.getKey(Input.KEY_S))
                move(getForward(), -moveValue);
            if (Input.getKey(Input.KEY_A))
                move(getLeft(), moveValue);
            if (Input.getKey(Input.KEY_D))
                move(getRight(), moveValue);
        }

        float rotateValue = m_sensitivity / 10000;
        Vector2f center = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
        Vector2f delta = Input.getMousePosition().getSub(center);

        boolean rotX = delta.getX() != 0;
        boolean rotY = delta.getY() != 0;

        if (rotX)
            rotateYRad(-rotateValue * delta.getX());
        if (rotY)
            rotateXRad(rotateValue * delta.getY());

        if (rotX || rotY) {
            Input.setMousePosition(center);
        }

        if (Input.getKey(Input.KEY_Q))
            rotateZDeg(2*rotateValue);
        if (Input.getKey(Input.KEY_E))
            rotateZDeg(-2 * rotateValue);

        if (Input.getKeyDown(Input.KEY_L))
            setLockedStatus(!m_isLocked);
    }

    public void move(Vector3f dir, float value) {
        m_position = m_position.getAdd(dir.getNormalized().getMul(value));
    }

    public void rotateXRad(float angle) {
        Vector3f horizon = Vector3f.yAxis.getCross(m_forward).normalize();

        m_forward.rotateRad(horizon, -angle).normalize();
        m_up = m_forward.getCross(horizon).normalize();
//        Vector3f left = getLeft();
//
//        m_forward.rotateRad(left, -angle).normalize();
//        m_up = m_forward.getCross(left).normalize();
    }

    public void rotateYRad(float angle) {
        Vector3f horizon = Vector3f.yAxis.getCross(m_forward).normalize();

        m_forward.rotateRad(Vector3f.yAxis, angle).normalize();
        m_up = m_forward.getCross(horizon).normalize();
//        m_forward.rotateRad(m_up, angle).normalize();
    }

    public void rotateZRad(float angle) {
        m_up.rotateRad(m_forward, -angle).normalize();
    }

    public void rotateXDeg(float angle) {
        rotateXRad((float) Math.toRadians(angle));
    }

    public void rotateYDeg(float angle) {
        rotateYRad((float) Math.toRadians(angle));
    }

    public void rotateZDeg(float angle) {
        rotateZRad((float) Math.toRadians(angle));
    }

    public Vector3f getLeft() {
        return m_up.getCross(m_forward).normalize();
    }

    public Vector3f getRight() {
        return m_forward.getCross(m_up).normalize();
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
        m_forward = forward.getNormalized();

        Vector3f horizon = Vector3f.yAxis.getCross(m_forward).normalize();
        m_up = m_forward.getCross(horizon).normalize();
    }

    public Vector3f getUp() {
        return m_up;
    }

    public void setUp(Vector3f up) {
        m_up = up.getNormalized();
    }

    public boolean isLocked() {
        return m_isLocked;
    }

    public void lock() {
        m_isLocked = true;
    }

    public void unlock() {
        m_isLocked = false;
    }

    public void setLockedStatus(boolean status) {
        m_isLocked = status;
    }

    public float getSpeed() {
        return m_speed;
    }

    public void setSpeed(float speed) {
        this.m_speed = Math.max(0.5f, speed);
    }

    public float getSensitivity() {
        return m_sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.m_sensitivity = Math.max(0.5f, sensitivity);
    }
}
