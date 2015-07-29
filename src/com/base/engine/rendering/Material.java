package com.base.engine.rendering;

import com.base.engine.core.Vector3f;

import java.util.HashMap;

public class Material {

    private HashMap<String, Texture> m_textures;
    private HashMap<String, Vector3f> m_vector3fs;
    private HashMap<String, Float> m_floats;

    public Material() {
        m_textures = new HashMap<>();
        m_vector3fs = new HashMap<>();
        m_floats = new HashMap<>();
    }

    public Material addTexture(String name, Texture value) {
        m_textures.put(name, value);
        return this;
    }

    public Material addVector3f(String name, Vector3f value) {
        m_vector3fs.put(name, value);
        return this;
    }

    public Material addFloat(String name, float value) {
        m_floats.put(name, value);
        return this;
    }

    public Texture getTexture(String name) {
        return m_textures.get(name);
    }

    public Vector3f getVector3f(String name) {
        return m_vector3fs.get(name);
    }

    public float getFloat(String name) {
        return m_floats.get(name);
    }
}
