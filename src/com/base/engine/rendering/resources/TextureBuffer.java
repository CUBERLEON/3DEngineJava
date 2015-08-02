package com.base.engine.rendering.resources;

import static org.lwjgl.opengl.GL15.*;

public class TextureBuffer {

    private int m_id;

    private int m_referencesCount;

    public TextureBuffer(int id) {
        m_id = id;
        m_referencesCount = 1;
    }

    public void dispose() {
        glDeleteBuffers(m_id);
        System.out.println("INFO: TextureBuffer(" + m_id + ") deleted from the GPU");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("INFO: TextureBuffer(" + m_id + ") deleted from the GPU");
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