package com.cuberleon.engine.components;

import com.cuberleon.engine.core.*;
import com.cuberleon.engine.rendering.RenderingEngine;
import com.cuberleon.engine.rendering.Window;

public abstract class Camera extends Component {

    protected boolean m_isLocked;

    protected float m_sensitivity;
    protected float m_speed;

    protected Matrix4f m_projectionTransform;

    protected Camera() {
        m_isLocked = false;
        m_sensitivity = 8.0f;
        m_speed = 6.0f;
    }

    @Override
    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addCamera(this);
    }

    public Matrix4f getViewProjectionTransform() {
        Transform t = getTransform();
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(getTransform().getRealPosition().getMul(-1));
        Matrix4f cameraRotation = new Matrix4f().initRotation(getTransform().getRealRotation()).transpose();

        Matrix4f viewTransform = cameraRotation.getMul(cameraTranslation);

        return m_projectionTransform.getMul(viewTransform);
    }

    public void move(Vector3f direction, float value) {
        getTransform().getPosition().add(direction.getNormalized().mul(value));
    }

    protected void updateRotation() {
        float rotateValue = m_sensitivity / 10000.0f;
        Vector2f center = Window.getCenter();
        Vector2f delta = Input.getMousePosition().sub(center);

        boolean movedX = delta.getX() != 0;
        boolean movedY = delta.getY() != 0;

        Quaternion qx = new Quaternion();
        Quaternion qy = new Quaternion();
        Quaternion qz = new Quaternion();

        if (movedY)
            qx.initAxisRad(getRight(), rotateValue * delta.getY());
        if (movedX)
            qy.initAxisRad(Vector3f.yAxis, -rotateValue * delta.getX());

        if (Input.getKey(Input.KEY_Q) ^ Input.getKey(Input.KEY_E)) {
            if (Input.getKey(Input.KEY_Q))
                qz.initAxisRad(getBack(), 15 * rotateValue);
            if (Input.getKey(Input.KEY_E))
                qz.initAxisRad(getBack(), -15 * rotateValue);
        }

        getTransform().rotate(qz.mul(qy.mul(qx)));

        if (movedX || movedY) {
            Input.setMousePosition(center);
        }
    }

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
