package com.base.engine.core;

public class Quaternion {

    private float m_x;
    private float m_y;
    private float m_z;
    private float m_w;

    public Quaternion() {
        this(0,0,0,1);
    }

    public Quaternion(Quaternion r) {
        this(r.getX(), r.getY(), r.getZ(), r.getW());
    }

    public Quaternion(float m_x, float m_y, float m_z, float m_w) {
        this.m_x = m_x;
        this.m_y = m_y;
        this.m_z = m_z;
        this.m_w = m_w;
    }

    public Quaternion(Vector3f axis, float angle) {
        initAxisRad(axis, angle);
    }

    public Quaternion(float x, float y, float z) {
        initEulerRad(x, y, z);
    }

    public Quaternion initAxisRad(Vector3f axis, float angle) {
        Vector3f _axis = axis.getNormalized();

        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);

        m_x = _axis.getX() * sinHalfAngle;
        m_y = _axis.getY() * sinHalfAngle;
        m_z = _axis.getZ() * sinHalfAngle;
        m_w = cosHalfAngle;

        return this;
    }

    public Quaternion initEulerRad(float x, float y, float z) {
//        float siny2 = (float) Math.sin(y/2.0f);
//        float cosy2 = (float) Math.cos(y/2.0f);
//
//        m_x = (float) Math.cos((z-x)/2.0f) * siny2;
//        m_y = (float) Math.sin((z-x)/2.0f) * siny2;
//        m_z = (float) Math.sin((z+x)/2.0f) * cosy2;
//        m_w = (float) Math.cos((z+x)/2.0f) * cosy2;
//
//        return this;
        Quaternion quatAroundX = new Quaternion(Vector3f.xAxis, x);
        Quaternion quatAroundY = new Quaternion(Vector3f.yAxis, y);
        Quaternion quatAroundZ = new Quaternion(Vector3f.zAxis, z);
        return quatAroundZ.mul(quatAroundY.mul(quatAroundX));
    }

    public Quaternion initEulerXRad(float x) {
        return initEulerRad(x, 0, 0);
    }

    public Quaternion initEulerYRad(float y) {
        return initEulerRad(0, y, 0);
    }

    public Quaternion initEulerZRad(float z) {
        return initEulerRad(0, 0, z);
    }

    public Quaternion initEulerRad(Vector3f r) {
        return initEulerRad(r.getX(), r.getY(), r.getZ());
    }

    public Quaternion initAxisDeg(Vector3f axis, float angle) {
        return initAxisRad(axis, (float) Math.toRadians(angle));
    }

    public Quaternion initEulerDeg(float x, float y, float z) {
        return initEulerRad(new Vector3f(x, y, z).toRadians());
    }

    public Quaternion initEulerXDeg(float x) {
        return initEulerDeg(x, 0, 0);
    }

    public Quaternion initEulerYDeg(float y) {
        return initEulerDeg(0, y, 0);
    }

    public Quaternion initEulerZDeg(float z) {
        return initEulerDeg(0, 0, z);
    }

    public Quaternion initEulerDeg(Vector3f r) {
        return initEulerRad(r.toRadians());
    }

    public float length() {
        return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
    }

    public Quaternion normalize() {
        float length = length();

        m_x /= length;
        m_y /= length;
        m_z /= length;
        m_w /= length;

        return this;
    }

    public Quaternion getNormalized() {
        return new Quaternion(this).normalize();
    }

    public Quaternion mul(Quaternion r) {
        float w_ = m_w * r.getW() - m_x * r.getX() - m_y * r.getY() - m_z * r.getZ();
        float x_ = m_x * r.getW() + m_w * r.getX() + m_y * r.getZ() - m_z * r.getY();
        float y_ = m_y * r.getW() + m_w * r.getY() + m_z * r.getX() - m_x * r.getZ();
        float z_ = m_z * r.getW() + m_w * r.getZ() + m_x * r.getY() - m_y * r.getX();

        set(x_, y_, z_, w_);

        return this;
    }

    public Quaternion getMul(Quaternion r) {
        return new Quaternion(this).mul(r);
    }

    public Quaternion mul(Vector3f r) {
        float x_ =  m_w * r.getX() + m_y * r.getZ() - m_z * r.getY();
        float y_ =  m_w * r.getY() + m_z * r.getX() - m_x * r.getZ();
        float z_ =  m_w * r.getZ() + m_x * r.getY() - m_y * r.getX();
        float w_ = -m_x * r.getX() - m_y * r.getY() - m_z * r.getZ();

        set(x_, y_, z_, w_);

        return this;
    }

    public Quaternion getMul(Vector3f r) {
        return new Quaternion(this).mul(r);
    }

    public Quaternion conjugate() {
        m_x = -m_x;
        m_y = -m_y;
        m_z = -m_z;
        return this;
    }

    public Quaternion getConjugated() {
        return new Quaternion(this).conjugate();
    }

    public Vector3f getForward() {
        return new Vector3f(0,0,-1).rotate(this).normalize();
    }

    public Vector3f getBack() {
        return new Vector3f(0,0,1).rotate(this).normalize();
    }

    public Vector3f getUp() {
        return new Vector3f(0,1,0).rotate(this).normalize();
    }

    public Vector3f getDown() {
        return new Vector3f(0,-1,0).rotate(this).normalize();
    }

    public Vector3f getRight() {
        return new Vector3f(1,0,0).rotate(this).normalize();
    }

    public Vector3f getLeft() {
        return new Vector3f(-1,0,0).rotate(this).normalize();
    }

    public void set(float x, float y, float z, float w) {
        m_x = x;
        m_y = y;
        m_z = z;
        m_w = w;
    }

    public void set(Quaternion r) {
        set(r.getX(), r.getY(), r.getZ(), r.getW());
    }

    //TODO proper Quaternion to Euler angles conversion
    public Vector3f toEulerAnglesRad() {
        float a = (float)Math.atan2(2*(m_z*m_y+m_x*m_w), 1-2*(m_x*m_x+m_y*m_y));
        float b = (float)Math.asin(2 * (m_w * m_y - m_x * m_z));
        float c = (float)Math.atan2(2*(m_w*m_z+m_x*m_y), 1-2*(m_y*m_y+m_z*m_z));

        return new Vector3f(a, b, c);
    }

    public Vector3f toEulerAnglesDeg() {
        return toEulerAnglesRad().toDegrees();
    }

    public float getX() {
        return m_x;
    }

    public void setX(float m_x) {
        this.m_x = m_x;
    }

    public float getY() {
        return m_y;
    }

    public void setY(float m_y) {
        this.m_y = m_y;
    }

    public float getZ() {
        return m_z;
    }

    public void setZ(float m_z) {
        this.m_z = m_z;
    }

    public float getW() {
        return m_w;
    }

    public void setW(float m_w) {
        this.m_w = m_w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quaternion that = (Quaternion) o;

        if (Float.compare(that.m_x, m_x) != 0) return false;
        if (Float.compare(that.m_y, m_y) != 0) return false;
        if (Float.compare(that.m_z, m_z) != 0) return false;
        return Float.compare(that.m_w, m_w) == 0;
    }

    @Override
    public int hashCode() {
        int result = (m_x != +0.0f ? Float.floatToIntBits(m_x) : 0);
        result = 31 * result + (m_y != +0.0f ? Float.floatToIntBits(m_y) : 0);
        result = 31 * result + (m_z != +0.0f ? Float.floatToIntBits(m_z) : 0);
        result = 31 * result + (m_w != +0.0f ? Float.floatToIntBits(m_w) : 0);
        return result;
    }
}
