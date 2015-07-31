package com.base.engine.rendering.meshLoading;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;

import java.util.ArrayList;

public class IndexedModel {

	private ArrayList<Vector3f> m_positions;
	private ArrayList<Vector2f> m_texCoords;
	private ArrayList<Vector3f> m_normals;
	private ArrayList<Vector3f> m_tangents;
	private ArrayList<Integer> m_indices;

	public IndexedModel() {
		m_positions = new ArrayList<>();
		m_texCoords = new ArrayList<>();
		m_normals = new ArrayList<>();
		m_tangents = new ArrayList<>();
		m_indices = new ArrayList<>();
	}

	public void calcNormals() {
        for (int i = 0; i < m_normals.size(); i++) {
            m_normals.get(i).set(0, 0, 0);
        }

        for (int i = 0; i < m_indices.size(); i += 3) {
            int i0 = m_indices.get(i);
            int i1 = m_indices.get(i + 1);
            int i2 = m_indices.get(i + 2);

            Vector3f v1 = m_positions.get(i1).getSub(m_positions.get(i0));
            Vector3f v2 = m_positions.get(i2).getSub(m_positions.get(i0));

            Vector3f normal = v1.getCross(v2).normalize();

            m_normals.get(i0).add(normal);
            m_normals.get(i1).add(normal);
            m_normals.get(i2).add(normal);
        }

        for (int i = 0; i < m_normals.size(); i++) {
            m_normals.get(i).normalize();
        }
	}

	public void calcTangents() {
		for (int i = 0; i < m_indices.size(); i += 3) {
			int i0 = m_indices.get(i);
			int i1 = m_indices.get(i + 1);
			int i2 = m_indices.get(i + 2);

			Vector3f edge1 = m_positions.get(i1).getSub(m_positions.get(i0));
			Vector3f edge2 = m_positions.get(i2).getSub(m_positions.get(i0));

			float deltaU1 = m_texCoords.get(i1).getX() - m_texCoords.get(i0).getX();
			float deltaV1 = m_texCoords.get(i1).getY() - m_texCoords.get(i0).getY();
			float deltaU2 = m_texCoords.get(i2).getX() - m_texCoords.get(i0).getX();
			float deltaV2 = m_texCoords.get(i2).getY() - m_texCoords.get(i0).getY();

			float dividend = (deltaU1*deltaV2 - deltaU2*deltaV1);

			float f = dividend == 0 ? 0.0f : 1.0f/dividend;

			Vector3f tangent = new Vector3f(0,0,0);
			tangent.setX(f * (deltaV2 * edge1.getX() - deltaV1 * edge2.getX()));
			tangent.setY(f * (deltaV2 * edge1.getY() - deltaV1 * edge2.getY()));
			tangent.setZ(f * (deltaV2 * edge1.getZ() - deltaV1 * edge2.getZ()));

			m_tangents.get(i0).set(m_tangents.get(i0).getAdd(tangent));
			m_tangents.get(i1).set(m_tangents.get(i1).getAdd(tangent));
			m_tangents.get(i2).set(m_tangents.get(i2).getAdd(tangent));
		}

		for (int i = 0; i < m_tangents.size(); i++)
			m_tangents.get(i).normalize();
	}

	public ArrayList<Vector3f> getPositions() {
        return m_positions;
    }

	public ArrayList<Vector2f> getTexCoords() {
        return m_texCoords;
    }

	public ArrayList<Vector3f> getNormals() {
        return m_normals;
    }

	public ArrayList<Vector3f> getTangents() {
        return m_tangents;
    }

	public ArrayList<Integer> getIndices() {
        return m_indices;
    }
}
