package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private int m_vbo;
    private int m_ibo;
    private int m_size;

    public Mesh() {
        m_vbo = glGenBuffers();
        m_ibo = glGenBuffers();
        m_size = 0;
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        m_size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 3 * 4);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glDrawElements(GL_TRIANGLES, m_size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
}
