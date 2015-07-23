package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Matrix4f;
import com.base.engine.rendering.Window;

public class OrthographicCamera extends Camera {

    public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
        m_projectionTransform = new Matrix4f().initOrthographic(left, right, bottom, top, near, far);
    }

    @Override
    public void input(float time) {
        if (!m_isLocked) {
            float moveValue = m_speed * time;

            if (Input.getKey(Input.KEY_W))
                move(getUp(), moveValue);
            if (Input.getKey(Input.KEY_S))
                move(getDown(), moveValue);
            if (Input.getKey(Input.KEY_A))
                move(getLeft(), moveValue);
            if (Input.getKey(Input.KEY_D))
                move(getRight(), moveValue);

            updateRotation();
        }

        if (Input.getKeyDown(Input.KEY_L)) {
            Input.setMousePosition(Window.getCenter());
            m_isLocked = !m_isLocked;
        }
    }

    public void updateProjection(float left, float right, float bottom, float top, float near, float far) {
        m_projectionTransform = new Matrix4f().initOrthographic(left, right, bottom, top, near, far);
    }
}
