package com.base.engine.rendering;

import com.base.engine.core.Util;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
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
        GL15.glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
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

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vertex> texCoords = new ArrayList<>();
        ArrayList<Vertex> normals = new ArrayList<>();

        ArrayList<Integer> vertIndices = new ArrayList<>();
        ArrayList<Integer> texIndices = new ArrayList<>();
        ArrayList<Integer> normIndices = new ArrayList<>();

        try {
            BufferedReader meshReader = new BufferedReader(new FileReader("./res/" + filePath));

            String line;
            while ((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#"))
                    continue;

                if (tokens[0].equals("v") && tokens.length == 4) {
                    vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3]))));
                } else if (tokens[0].equals("f") && tokens.length == 4) {
                    for (int i = 1; i <= 3; i++) {
                        String[] values;
                        if (tokens[i].contains("//"))
                            values = tokens[i].split("//");
                        else if (tokens[i].contains("/"))
                            values = tokens[i].split("/");
                        else values = new String[]{tokens[i]};

                        switch (values.length) {
                            case 1: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            case 2: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                normIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            case 3: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                texIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                normIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            default:
                                throw new Exception("Fatal ERROR: " + filePath + " faces data was corrupted!");
                        }
                    }
                } if (tokens[0].equals("vt") && tokens.length == 4) {
                    texCoords.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3]))));
                } if (tokens[0].equals("vn") && tokens.length == 4) {
                    normals.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3]))));
                }
            }

            meshReader.close();

            if (vertices.size() == 0 || vertIndices.size() == 0)
                throw new Exception("Fatal ERROR: " + filePath + " data was corrupted!");

            Vertex[] verticesData = new Vertex[vertices.size()];
            Integer[] indicesData = new Integer[vertIndices.size()];

            vertices.toArray(verticesData);
            vertIndices.toArray(indicesData);

            setVertices(verticesData, Util.toIntArray(indicesData), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
