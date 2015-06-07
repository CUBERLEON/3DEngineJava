package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int m_id;

    public Texture(int id) {
        m_id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, m_id);
    }

    public int getID() {
        return m_id;
    }
}
