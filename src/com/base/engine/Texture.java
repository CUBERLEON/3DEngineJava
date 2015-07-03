package com.base.engine;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int m_id;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    public Texture(int id) {
        m_id = id;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, m_id);
    }

    public int getID() {
        return m_id;
    }

    private static int loadTexture(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1].toLowerCase();

        try {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/" + fileName))).getTextureID();
            return id;
        } catch (Exception e) {
            System.err.println("Fatal ERROR: Loading texture '" + fileName + "' failed!");
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }
}
