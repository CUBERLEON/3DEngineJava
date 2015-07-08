package com.base.engine.core;

import com.base.engine.rendering.Shader;

public interface GameComponent {

    void input(Transform transform);
    void update(Transform transform);
    void render(Transform transform, Shader shader);
}
