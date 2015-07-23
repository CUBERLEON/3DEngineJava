package com.base.engine.core.components;

import com.base.engine.core.*;
import com.base.engine.rendering.Window;

public class PerspectiveCamera extends Camera {

    public PerspectiveCamera(float fov, float aspectRatio, float zNear, float zFar) {
        m_projectionTransform = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
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

            updateRotation();
        }

        if (Input.getKeyDown(Input.KEY_L)) {
            Input.setMousePosition(Window.getCenter());
            m_isLocked = !m_isLocked;
        }
    }

    public void updateProjection(float fov, float aspectRatio, float zNear, float zFar) {
        m_projectionTransform = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
    }
}
