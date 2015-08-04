package com.cuberleon.engine.rendering;

import com.cuberleon.engine.core.Vector3f;

import java.util.HashMap;

public abstract class MappedValues {

    private HashMap<String, Vector3f> m_vector3fs;
    private HashMap<String, Float> m_floats;
    private HashMap<String, Integer> m_integers;

    public MappedValues() {
        m_vector3fs = new HashMap<>();
        m_floats = new HashMap<>();
        m_integers = new HashMap<>();
    }

    public void addVector3f(String name, Vector3f value) {
        m_vector3fs.put(name, value);
    }

    public void addFloat(String name, float value) {
        m_floats.put(name, value);
    }

    public void addInteger(String name, int value) {
        m_integers.put(name, value);
    }

    public Vector3f getVector3f(String name) {
        return m_vector3fs.get(name);
    }

    public float getFloat(String name) {
        return m_floats.get(name);
    }

    public int getInteger(String name) {
        return m_integers.get(name);
    }
}
