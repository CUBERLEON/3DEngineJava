package com.cuberleon.engine.rendering.resources;

import com.cuberleon.engine.core.Debug;

import static org.lwjgl.opengl.GL15.*;

public class TextureData {

    private int m_id;

    private int m_referencesCount;

    public TextureData() {
        m_id = glGenBuffers();
        m_referencesCount = 1;
    }

    public void dispose() {
        glDeleteBuffers(m_id);
        Debug.info("TextureData(" + m_id + ") was disposed");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            Debug.info("TextureData(" + m_id + ") was deleted (finalize)");
            glDeleteBuffers(m_id);
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    public void addReference() {
        m_referencesCount++;
    }

    public boolean deleteReference() {
        m_referencesCount--;
        return m_referencesCount == 0;
    }

    public int getBufferID() {
        return m_id;
    }
}