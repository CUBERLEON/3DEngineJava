package com.cuberleon.engine.rendering;

import com.cuberleon.engine.core.Debug;
import com.cuberleon.engine.core.Util;
import com.cuberleon.engine.core.Vector2f;
import com.cuberleon.engine.core.Vector3f;
import com.cuberleon.engine.rendering.meshLoading.IndexedModel;
import com.cuberleon.engine.rendering.meshLoading.OBJModel;
import com.cuberleon.engine.rendering.resources.MeshData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {

    private static HashMap<String, WeakReference<MeshData>> m_loadedMeshes = new HashMap<>();
    private MeshData m_data;

    private String m_filePath;

    public Mesh(String filePath) {
        m_filePath = filePath;

        if (m_loadedMeshes.containsKey(filePath) &&
        (m_data = m_loadedMeshes.get(filePath).get()) != null) {
            m_data.addReference();
        } else {
            loadMesh(filePath);
            Debug.info("Mesh('%s') was loaded", m_filePath);
        }
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        setVertices(vertices, indices, calcNormals);
    }

    public void dispose() {
        if (m_data != null)
            m_data.dispose();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (m_data.deleteReference()) {
                if (m_filePath != null) {
                    m_loadedMeshes.remove(m_filePath, m_data);
                    Debug.info("Mesh(" + m_filePath + ") was deleted (finalize)");
                }
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    private void setVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals)
            calcNormals(vertices, indices);

        m_data = new MeshData(indices.length);
        m_loadedMeshes.put(m_filePath, new WeakReference<>(m_data));

        glBindBuffer(GL_ARRAY_BUFFER, m_data.getVertexBufferID());
        glBufferData(GL_ARRAY_BUFFER, Util.createFloatBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_data.getIndicesBufferID());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createIntBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, m_data.getVertexBufferID());
        glVertexAttribPointer(0, Vector3f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 0);
        glVertexAttribPointer(1, Vector2f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 4 * Vector3f.SIZE);
        glVertexAttribPointer(2, Vector3f.SIZE, GL_FLOAT, false, 4 * Vertex.SIZE, 4 * (Vector3f.SIZE + Vector2f.SIZE));

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_data.getIndicesBufferID());
        glDrawElements(GL_TRIANGLES, m_data.getIndicesCount(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    private void calcNormals(Vertex[] vertices, int[] indices) {
        for (Vertex vertex : vertices)
            vertex.setNormal(new Vector3f(0, 0, 0));

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

        for (Vertex vertex : vertices)
            vertex.getNormal().normalize();
    }

    private void loadMesh(String filePath) {
        String[] splitArray = filePath.split("\\.");
        String ext = splitArray[splitArray.length - 1].toUpperCase();

        if (!ext.equals("OBJ"))
            Debug.fatalError(ext + " mesh format isn't supported!");

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
