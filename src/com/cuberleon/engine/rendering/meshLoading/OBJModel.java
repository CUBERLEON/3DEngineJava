package com.cuberleon.engine.rendering.meshLoading;

import com.cuberleon.engine.core.Util;
import com.cuberleon.engine.core.Vector2f;
import com.cuberleon.engine.core.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class OBJModel {

	private ArrayList<Vector3f> m_positions;
	private ArrayList<Vector2f> m_texCoords;
	private ArrayList<Vector3f> m_normals;
	private ArrayList<OBJIndex> m_indices;

    private boolean m_hasTexCoords;
	private boolean m_hasNormals;

	public OBJModel(String fileName) {
		m_positions = new ArrayList<>();
		m_texCoords = new ArrayList<>();
		m_normals = new ArrayList<>();
		m_indices = new ArrayList<>();

		m_hasTexCoords = false;
		m_hasNormals = false;

		try {
            BufferedReader meshReader = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = meshReader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#"))
					continue;

				if (tokens[0].equals("v")) {
					m_positions.add(new Vector3f(Float.valueOf(tokens[1]),
                                                 Float.valueOf(tokens[2]),
                                                 Float.valueOf(tokens[3])));
				}
				else if (tokens[0].equals("vt")) {
					m_texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
							                     1.0f - Float.valueOf(tokens[2])));
				}
				else if (tokens[0].equals("vn")) {
					m_normals.add(new Vector3f(Float.valueOf(tokens[1]),
                                               Float.valueOf(tokens[2]),
                                               Float.valueOf(tokens[3])));
				}
				else if (tokens[0].equals("f")) {
					for (int i = 0; i < tokens.length - 3; i++) {
						m_indices.add(parseOBJIndex(tokens[1]));
						m_indices.add(parseOBJIndex(tokens[2 + i]));
						m_indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IndexedModel toIndexedModel() {
		IndexedModel result = new IndexedModel();

        HashMap<OBJIndex, Integer> usedIndexes = new HashMap<>();

		for (OBJIndex currentIndex : m_indices) {
			Vector3f currentPosition = m_positions.get(currentIndex.getVertexIndex());
			Vector2f currentTexCoord;
			Vector3f currentNormal;

			if (m_hasTexCoords)
				currentTexCoord = m_texCoords.get(currentIndex.getTexCoordIndex());
			else
				currentTexCoord = new Vector2f(0,0);

			if (m_hasNormals)
				currentNormal = m_normals.get(currentIndex.getNormalIndex());
			else
				currentNormal = new Vector3f(0,0,0);

            if (usedIndexes.containsKey(currentIndex)) {
                result.getIndices().add(usedIndexes.get(currentIndex));
            } else {
                usedIndexes.put(currentIndex, result.getPositions().size());
                result.getIndices().add(result.getPositions().size());
                result.getPositions().add(currentPosition);
                result.getTexCoords().add(currentTexCoord);
                result.getNormals().add(currentNormal);
            }
		}

		if (!m_hasNormals)
			result.calcNormals();

//		result.calcTangents();

		return result;
	}

	private OBJIndex parseOBJIndex(String token) {
        String[] values;
        boolean doubleSlashes = false;
		if (token.contains("//")) {
            values = token.split("//");
            doubleSlashes = true;
        } else
            values = token.split("/");

		OBJIndex result = new OBJIndex();

		result.setVertexIndex(Integer.parseInt(values[0]) - 1);

		if (values.length > 1) {
			if (values.length == 2) {
                if (doubleSlashes) {
                    m_hasNormals = true;
                    result.setNormalIndex(Integer.parseInt(values[1]) - 1);
                } else {
                    m_hasTexCoords = true;
                    result.setTexCoordIndex(Integer.parseInt(values[1]) - 1);
                }
			}

			if (values.length == 3) {
                m_hasTexCoords = true;
                result.setTexCoordIndex(Integer.parseInt(values[1]) - 1);
				m_hasNormals = true;
				result.setNormalIndex(Integer.parseInt(values[2]) - 1);
			}
		}

		return result;
	}
}
