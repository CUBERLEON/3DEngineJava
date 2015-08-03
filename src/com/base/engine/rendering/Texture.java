package com.base.engine.rendering;

import com.base.engine.rendering.resources.TextureBuffer;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {

    private static HashMap<String, WeakReference<TextureBuffer>> m_loadedTextures = new HashMap<>();
    private TextureBuffer m_buffer;
    private String m_filePath;

    public Texture(String filePath) {
        m_filePath = filePath;

        if (m_loadedTextures.containsKey(filePath)) {
            m_buffer = m_loadedTextures.get(filePath).get();
            m_buffer.addReference();
        } else {
            m_buffer = loadTexture(filePath);
        }
    }

    public void dispose() {
        if (m_buffer != null)
            m_buffer.dispose();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (m_buffer.deleteReference()) {
                if (m_filePath != null) {
                    m_loadedTextures.remove(m_filePath, m_buffer);
                    System.out.println("INFO: Mesh(" + m_filePath + ") was deleted (finalize)");
                }
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    public void bind() {
        bind(0);
    }

    public void bind(int samplerLocation) {
        if (samplerLocation < 0 || samplerLocation > 31) {
            System.err.println("Fatal ERROR: sampler location("+samplerLocation+") is out of valid range [0, 31]");
            new Exception().printStackTrace();
            System.exit(1);
        }
        glActiveTexture(GL_TEXTURE0 + samplerLocation);
        glBindTexture(GL_TEXTURE_2D, m_buffer.getBufferID());
    }

    private TextureBuffer loadTexture(String filePath) {
        String[] splitArray = filePath.split("\\.");
        String ext = splitArray[splitArray.length - 1].toLowerCase();

        try {
            return new TextureBuffer(TextureLoader.getTexture(ext, new FileInputStream(new File("./res/" + filePath))).getTextureID());
        } catch (Exception e) {
            System.err.println("Fatal ERROR: Loading texture '" + filePath + "' failed!");
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
