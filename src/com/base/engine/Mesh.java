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
        addVertices(vertices, indices, false);
    }

    public void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals)
            calcNormals(vertices, indices);

        m_size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        glVertexAttribPointer(0, Vector3f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 0);
        glVertexAttribPointer(1, Vector2f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 4 * Vector3f.SIZE);
        glVertexAttribPointer(2, Vector3f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 4 * (Vector3f.SIZE + Vector2f.SIZE));

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glDrawElements(GL_TRIANGLES, m_size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    private void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setNormal(new Vector3f(0, 0, 0));
        }

        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPosition().sub(vertices[i0].getPosition());
            Vector3f v2 = vertices[i2].getPosition().sub(vertices[i0].getPosition());

            Vector3f normal = v1.cross(v2).normalize();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].getNormal().normalize();
        }
    }
}
