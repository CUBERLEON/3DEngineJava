package com.cuberleon.engine.rendering.resources;

import static org.lwjgl.opengl.GL15.*;

public class MeshBuffer {

    private int m_vbo;
    private int m_ibo;

    private int m_referencesCount;

    public MeshBuffer() {
        m_vbo = glGenBuffers();
        m_ibo = glGenBuffers();
        m_referencesCount = 1;
    }

    public void dispose() {
        glDeleteBuffers(m_vbo);
        glDeleteBuffers(m_ibo);
        System.out.println("INFO: MeshBuffer(" + m_vbo + ", " + m_ibo + ") deleted from the GPU");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            System.out.println("INFO: MeshBuffer(" + m_vbo + ", " + m_ibo + ") deleted from the GPU (finalize)");
            glDeleteBuffers(m_vbo);
            glDeleteBuffers(m_ibo);
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

    public int getVertexBufferID() {
        return m_vbo;
    }

    public int getIndicesBufferID() {
        return m_ibo;
    }
}
