package com.base.engine;

public class Camera {

    private Vector3f m_position;
    private Vector3f m_positionInitial;

    private Vector3f m_forward;
    private Vector3f m_forwardInitial;

    private Vector3f m_up;
    private Vector3f m_upInitial;

    private boolean m_isLocked;

    private float m_sensitivity;
    private float m_speed;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f position) {
        this(position, new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f position, Vector3f forward) {
        this(position, forward, new Vector3f(0, 1, 0)); //up direction is undefined

        Vector3f horizon = Vector3f.yAxis.getCross(m_forward).normalize();
        m_up = m_forward.getCross(horizon).normalize();
        m_upInitial = new Vector3f(m_up);
    }

    public Camera(Vector3f position, Vector3f forward, Vector3f up) {
        m_position = position;
        m_forward = forward;
        m_up = up;

        m_positionInitial = new Vector3f(m_position);
        m_forwardInitial = new Vector3f(m_forward);
        m_upInitial = new Vector3f(m_up);

        m_forward.normalize();
        m_up.normalize();

        m_isLocked = false;
        m_sensitivity = 6.0f;
        m_speed = 6.0f;
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

        float rotateValue = m_sensitivity * 0.01f;
        Vector2f center = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
        Vector2f delta = Input.getMousePosition().getSub(center);

        boolean rotX = delta.getX() != 0;
        boolean rotY = delta.getY() != 0;

        if (rotX)
            rotateYDeg(-rotateValue * delta.getX());
        if (rotY)
            rotateXDeg(rotateValue * delta.getY());

        if (rotX || rotY) {
            Input.setMousePosition(center);
        }

        if (Input.getKey(Input.KEY_Q))
            rotateZDeg(2*rotateValue);
        if (Input.getKey(Input.KEY_E))
            rotateZDeg(-2*rotateValue);

        if (Input.getKeyDown(Input.KEY_R))
            reset();
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

    public void reset() {
        m_position = new Vector3f(m_positionInitial);
        m_forward = new Vector3f(m_forwardInitial);
        m_up = new Vector3f(m_upInitial);
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

    public Vector3f getPositionInitial() {
        return m_positionInitial;
    }

    public void setPositionInitial(Vector3f position) {
        this.m_positionInitial = position;
    }

    public Vector3f getForwardInitial() {
        return m_forwardInitial;
    }

    public void setForwardInitial(Vector3f forward) {
        m_forwardInitial = forward.getNormalized();

        Vector3f horizon = Vector3f.yAxis.getCross(m_forwardInitial).normalize();
        m_upInitial = m_forwardInitial.getCross(horizon).normalize();
    }

    public Vector3f getUpInitial() {
        return m_upInitial;
    }

    public void setUpInitial(Vector3f up) {
        m_upInitial = up.getNormalized();
    }

}
