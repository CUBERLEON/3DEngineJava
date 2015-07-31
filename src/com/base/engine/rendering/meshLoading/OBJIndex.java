package com.base.engine.rendering.meshLoading;

public class OBJIndex {

	private int m_vertexIndex;
	private int m_texCoordIndex;
	private int m_normalIndex;

    public OBJIndex() {
        m_vertexIndex = -1;
        m_texCoordIndex = -1;
        m_normalIndex = -1;
    }

	public int getVertexIndex() {
		return m_vertexIndex;
	}

	public int getTexCoordIndex() {
        return m_texCoordIndex;
    }

	public int getNormalIndex() {
        return m_normalIndex;
    }

	public void setVertexIndex(int value) {
        m_vertexIndex = value;
    }

	public void setTexCoordIndex(int value) {
        m_texCoordIndex = value;
    }

	public void setNormalIndex(int value) {
        m_normalIndex = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OBJIndex objIndex = (OBJIndex) o;

        if (m_vertexIndex != objIndex.m_vertexIndex) return false;
        if (m_texCoordIndex != objIndex.m_texCoordIndex) return false;
        return m_normalIndex == objIndex.m_normalIndex;

    }

    @Override
    public int hashCode() {
        int result = m_vertexIndex;
        result = 31 * result + m_texCoordIndex;
        result = 31 * result + m_normalIndex;
        return result;
    }
}
