package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

import java.util.HashMap;

public class Material extends MappedValues {

    private HashMap<String, Texture> m_textures;

    public Material() {
        m_textures = new HashMap<>();
    }

    public void addTexture(String name, Texture value) {
        m_textures.put(name, value);
    }

    public Texture getTexture(String name) {
        return m_textures.get(name);
    }
}
