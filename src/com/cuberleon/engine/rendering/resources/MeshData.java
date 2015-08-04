package com.cuberleon.engine.rendering.resources;

import com.cuberleon.engine.core.Debug;

import static org.lwjgl.opengl.GL15.*;

public class MeshData {

    private int m_vbo;
    private int m_ibo;
    private int m_indicesCount;

    private int m_referencesCount;

    public MeshData(int indicesCount) {
        m_vbo = glGenBuffers();
        m_ibo = glGenBuffers();
        m_indicesCount = indicesCount;
        m_referencesCount = 1;
    }

    public void dispose() {
        glDeleteBuffers(m_vbo);
        glDeleteBuffers(m_ibo);
        Debug.info("MeshData(" + m_vbo + ", " + m_ibo + ") was diposed");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            Debug.info("MeshData(" + m_vbo + ", " + m_ibo + ") was deleted (finalize)");
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

    public int getIndicesCount() {
        return m_indicesCount;
    }
}
