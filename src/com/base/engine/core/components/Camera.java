package com.base.engine.core.components;

import com.base.engine.core.*;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

public class Camera extends GameComponent {

    private boolean m_isLocked;

    private float m_sensitivity;
    private float m_speed;

    private Matrix4f m_projectionTransform;

    public Camera(float left, float right, float bottom, float top, float near, float far) {
        initDefaults();

        m_projectionTransform = new Matrix4f().initOrthographic(left, right, bottom, top, near, far);
    }

    public Camera(float fov, float aspectRatio, float zNear, float zFar) {
        initDefaults();

        m_projectionTransform = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
    }

    @Override
    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.addCamera(this);
    }

    public Matrix4f getViewProjectionTransform() {
        Matrix4f cameraRotation = new Matrix4f().initRotation(getTransform().getRotation());
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(getTransform().getPosition().getMul(-1));

        Matrix4f viewTransform = cameraRotation.getMul(cameraTranslation);

        return m_projectionTransform.getMul(viewTransform);
    }

    public void initDefaults() {
        m_isLocked = false;
        m_sensitivity = 8.0f;
        m_speed = 6.0f;
    }

    @Override
    public void input(float time) {
        if (!m_isLocked) {
            float moveValue = m_speed * time;

            if (Input.getKey(Input.KEY_W))
                move(getForward(), moveValue);
            if (Input.getKey(Input.KEY_S))
                move(getBack(), moveValue);
            if (Input.getKey(Input.KEY_A))
                move(getLeft(), moveValue);
            if (Input.getKey(Input.KEY_D))
                move(getRight(), moveValue);

            float rotateValue = m_sensitivity / 10000.0f;
            Vector2f center = Window.getCenter();
            Vector2f delta = Input.getMousePosition().getSub(center);

            boolean movedX = delta.getX() != 0;
            boolean movedY = delta.getY() != 0;

            Quaternion qx = new Quaternion();
            Quaternion qy = new Quaternion();
            Quaternion qz = new Quaternion();
            Quaternion old = new Quaternion(getTransform().getRotation());

            if (movedY)
                qx.initRotationRad(getRight(), rotateValue * delta.getY());
            if (movedX)
                qy.initRotationRad(Vector3f.yAxis, -rotateValue * delta.getX());

            if (Input.getKey(Input.KEY_Q) ^ Input.getKey(Input.KEY_E)) {
                if (Input.getKey(Input.KEY_Q))
                    qz.initRotationRad(getBack(), 15 * rotateValue);
                if (Input.getKey(Input.KEY_E))
                    qz.initRotationRad(getBack(), -15 * rotateValue);
            }

            getTransform().setRotation(qz.mul(qy.mul(qx.mul(old))));

            if (movedX || movedY) {
                Input.setMousePosition(center);
            }
        } else {

        }

        if (Input.getKeyDown(Input.KEY_L)) {
            Input.setMousePosition(Window.getCenter());
            m_isLocked = !m_isLocked;
        }
    }

    public void move(Vector3f direction, float value) {
        getTransform().getPosition().add(direction.getNormalized().getMul(value));
    }

//    public void rotateXRad(float angle) {
//        getTransform().getRotation().mul(new Quaternion().initRotationRad(getRight(), angle)).normalize();
//    }
//
//    public void rotateYRad(float angle) {
//        getTransform().getRotation().mul(new Quaternion().initRotationRad(Vector3f.yAxis, angle)).normalize();
//    }
//
//    public void rotateZRad(float angle) {
//        getTransform().getRotation().mul(new Quaternion().initRotationRad(getBack(), angle)).normalize();
//    }
//
//    public void rotateXDeg(float angle) {
//        rotateXRad((float) Math.toRadians(angle));
//    }
//
//    public void rotateYDeg(float angle) {
//        rotateYRad((float) Math.toRadians(angle));
//    }
//
//    public void rotateZDeg(float angle) {
//        rotateZRad((float) Math.toRadians(angle));
//    }

    public Vector3f getLeft() {
        return getTransform().getRotation().getLeft();
    }

    public Vector3f getRight() {
        return getTransform().getRotation().getRight();
    }

    public Vector3f getForward() {
        return getTransform().getRotation().getForward();
    }

    public Vector3f getBack() {
        return getTransform().getRotation().getBack();
    }

    public Vector3f getUp() {
        return getTransform().getRotation().getUp();
    }

    public Vector3f getDown() {
        return getTransform().getRotation().getDown();
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
