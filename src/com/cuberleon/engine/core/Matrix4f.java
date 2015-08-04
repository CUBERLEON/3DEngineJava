package com.cuberleon.engine.core;

public class Matrix4f {

    private float[][] m;

    public Matrix4f(Matrix4f r) {
        m = r.getArray();
    }

    public Matrix4f() {
        m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(Vector3f r) {
        return this.initTranslation(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotationRad(Vector3f r) {
        return this.initRotationRad(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initRotationRad(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rx.m[0][0] = 1; rx.m[0][1] = 0;                  rx.m[0][2] = 0;                   rx.m[0][3] = 0;
        rx.m[1][0] = 0; rx.m[1][1] = (float)Math.cos(x); rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0; rx.m[2][1] = (float)Math.sin(x); rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0; rx.m[3][1] = 0;                  rx.m[3][2] = 0;                   rx.m[3][3] = 1;

        ry.m[0][0] = (float)Math.cos(y);  ry.m[0][1] = 0; ry.m[0][2] = (float)Math.sin(y); ry.m[0][3] = 0;
        ry.m[1][0] = 0;                   ry.m[1][1] = 1; ry.m[1][2] = 0;                  ry.m[1][3] = 0;
        ry.m[2][0] = -(float)Math.sin(y); ry.m[2][1] = 0; ry.m[2][2] = (float)Math.cos(y); ry.m[2][3] = 0;
        ry.m[3][0] = 0;                   ry.m[3][1] = 0; ry.m[3][2] = 0;                  ry.m[3][3] = 1;

        rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); rz.m[0][2] = 0; rz.m[0][3] = 0;
        rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  rz.m[1][2] = 0; rz.m[1][3] = 0;
        rz.m[2][0] = 0;                  rz.m[2][1] = 0;                   rz.m[2][2] = 1; rz.m[2][3] = 0;
        rz.m[3][0] = 0;                  rz.m[3][1] = 0;                   rz.m[3][2] = 0; rz.m[3][3] = 1;

        m = rz.getMul(ry.getMul(rx)).getArray();

        return this;
    }

    public Matrix4f initRotationXRad(float x) {
        return initRotationRad(x, 0, 0);
    }

    public Matrix4f initRotationYRad(float y) {
        return initRotationRad(0, y, 0);
    }

    public Matrix4f initRotationZRad(float z) {
        return initRotationRad(0, 0, z);
    }

    public Matrix4f initRotationDeg(Vector3f r) {
        return this.initRotationRad(r.toRadians());
    }

    public Matrix4f initRotationDeg(float x, float y, float z) {
        return this.initRotationRad(new Vector3f(x, y, z).toRadians());
    }

    public Matrix4f initRotationXDeg(float x) {
        return initRotationDeg(x, 0, 0);
    }

    public Matrix4f initRotationYDeg(float y) {
        return initRotationDeg(0, y, 0);
    }

    public Matrix4f initRotationZDeg(float z) {
        return initRotationDeg(0, 0, z);
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
        Vector3f f = forward.getNormalized();
        Vector3f u = up.getNormalized();
        Vector3f r = right.getNormalized();

        m[0][0] = r.getX(); m[0][1] = u.getX(); m[0][2] = -f.getX(); m[0][3] = 0;
        m[1][0] = r.getY(); m[1][1] = u.getY(); m[1][2] = -f.getY(); m[1][3] = 0;
        m[2][0] = r.getZ(); m[2][1] = u.getZ(); m[2][2] = -f.getZ(); m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {
        Vector3f f = forward.getNormalized();
        Vector3f u = up.getNormalized();
        Vector3f r = f.getCross(u);
        return initRotation(f, u, r);
    }

    public Matrix4f initRotation(Quaternion rotation) {
        float x = rotation.getX();
        float y = rotation.getY();
        float z = rotation.getZ();
        float w = rotation.getW();

        float x2 = x*x; float y2 = y*y; float z2 = z*z;

        m[0][0] = 1-2*y2-2*z2; m[0][1] = 2*x*y-2*w*z; m[0][2] = 2*x*z+2*w*y; m[0][3] = 0;
        m[1][0] = 2*x*y+2*w*z; m[1][1] = 1-2*x2-2*z2; m[1][2] = 2*y*z-2*w*x; m[1][3] = 0;
        m[2][0] = 2*x*z-2*w*y; m[2][1] = 2*y*z+2*w*x; m[2][2] = 1-2*x2-2*y2; m[2][3] = 0;
        m[3][0] = 0;           m[3][1] = 0;           m[3][2] = 0;           m[3][3] = 1;

        return this;
    }

    public Matrix4f initScale(Vector3f r) {
        return this.initScale(r.getX(), r.getY(), r.getZ());
    }

    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * @param fov full angle of field of view in radians (Y-axis)
     * @param aspectRatio aspect ratio of window
     * @param zNear near clipping plane
     * @param zFar far clipping plane
     * @return perspective projection
     */
    public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        float tanHalfFOV = (float) Math.tan(fov/2.0f);
        float zRange = zFar - zNear;

        m[0][0] = 1.0f/(tanHalfFOV * aspectRatio); m[0][1] = 0;               m[0][2] = 0;                    m[0][3] = 0;
        m[1][0] = 0;                               m[1][1] = 1.0f/tanHalfFOV; m[1][2] = 0;                    m[1][3] = 0;
        m[2][0] = 0;                               m[2][1] = 0;               m[2][2] = -(zFar+zNear)/zRange; m[2][3] = -2*zFar*zNear/zRange;
        m[3][0] = 0;                               m[3][1] = 0;               m[3][2] = -1;                   m[3][3] = 0;

        return this;
    }

    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        m[0][0] = 2/(right - left); m[0][1] = 0;                m[0][2] = 0;               m[0][3] = (right + left)/(right - left);
        m[1][0] = 0;                m[1][1] = 2/(top - bottom); m[1][2] = 0;               m[1][3] = (top + bottom)/(top - bottom);
        m[2][0] = 0;                m[2][1] = 0;                m[2][2] = -2/(far - near); m[2][3] = (far + near)/(far - near);
        m[3][0] = 0;                m[3][1] = 0;                m[3][2] = 0;               m[3][3] = 1;

        return this;
    }

    public Matrix4f transpose() {
        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 4; j++) {
                float tmp = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = tmp;
            }
        }

        return this;
    }

    public Matrix4f getTransposed() {
        return new Matrix4f(this).transpose();
    }

    public Matrix4f mul(Matrix4f r) {
        m = getMul(r).getArray();
        return this;
    }

    public Matrix4f getMul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, m[i][0] * r.get(0, j) +
                              m[i][1] * r.get(1, j) +
                              m[i][2] * r.get(2, j) +
                              m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }

    public float[][] getArray() {
        float[][] res = new float[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i][j] = m[i][j];
        return res;
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                r.append(m[i][j]);
                if (j < 3) r.append(", ");
            }
            r.append("\n");
        }

        return r.toString();
    }
}
