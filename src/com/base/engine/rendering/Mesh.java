package com.base.engine.rendering;

import com.base.engine.core.Util;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private int m_vbo;
    private int m_ibo;
    private int m_size;

    public Mesh(String filePath) {
        initBuffers();
        loadMesh(filePath);
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        initBuffers();
        setVertices(vertices, indices, calcNormals);
    }

    private void initBuffers() {
        m_vbo = glGenBuffers();
        m_ibo = glGenBuffers();
        m_size = 0;
    }

    private void setVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals)
            calcNormals(vertices, indices);

        m_size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        GL15.glBufferData(GL_ARRAY_BUFFER, Util.createFloatBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createIntBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, m_vbo);
        GL20.glVertexAttribPointer(0, Vector3f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 0);
        GL20.glVertexAttribPointer(1, Vector2f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 4 * Vector3f.SIZE);
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

            Vector3f v1 = vertices[i1].getPosition().getSub(vertices[i0].getPosition());
            Vector3f v2 = vertices[i2].getPosition().getSub(vertices[i0].getPosition());

            Vector3f normal = v1.getCross(v2).normalize();

            vertices[i0].getNormal().add(normal);
            vertices[i1].getNormal().add(normal);
            vertices[i2].getNormal().add(normal);
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].getNormal().normalize();
        }
    }

    private void loadMesh(String filePath) {
        String[] splitArray = filePath.split("\\.");
        String ext = splitArray[splitArray.length - 1].toUpperCase();

        if (!(ext.equals("OBJ"))) {
            System.err.println("Fatal ERROR: " + ext + " mesh format isn't supported!");
            new Exception().printStackTrace();
            System.exit(1);
        }

        IndexedModel model = new OBJModel("./res/" + filePath).toIndexedModel();

        Vertex[] verticesData = new Vertex[model.getPositions().size()];
        Integer[] indicesData = new Integer[model.getIndices().size()];

        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < model.getPositions().size(); i++) {
            vertices.add(new Vertex(model.getPositions().get(i),
                                    model.getTexCoords().get(i),
                                    model.getNormals().get(i)));
        }

        vertices.toArray(verticesData);
        model.getIndices().toArray(indicesData);

        setVertices(verticesData, Util.toIntArray(indicesData), false);
    }
}
