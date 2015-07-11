package com.base.engine.core;

import com.base.engine.rendering.Shader;

public interface GameComponent {

    void input(Transform transform, float time);
    void update(Transform transform, float time);
    void render(Transform transform, Shader shader);
}
