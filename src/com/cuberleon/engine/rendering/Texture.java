package com.cuberleon.engine.rendering;

import com.cuberleon.engine.core.Debug;
import com.cuberleon.engine.rendering.resources.TextureData;
import org.lwjgl.BufferUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import javax.imageio.ImageIO;

public class Texture {

    private static HashMap<String, WeakReference<TextureData>> m_loadedTextures = new HashMap<>();
    private TextureData m_data;

    private String m_filePath;

    public Texture(String filePath) {
        m_filePath = filePath;

        if (m_loadedTextures.containsKey(filePath) &&
        (m_data = m_loadedTextures.get(filePath).get()) != null) {
            m_data.addReference();
        } else {
            loadTexture(filePath);
            Debug.info("Texture('%s') was loaded", m_filePath);
        }
    }

    public void dispose() {
        if (m_data != null)
            m_data.dispose();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (m_data != null && m_data.deleteReference()) {
                if (m_filePath != null) {
                    m_loadedTextures.remove(m_filePath, m_data);
                    Debug.info("Mesh(" + m_filePath + ") was deleted (finalize)");
                }
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    public void bind(int samplerLocation) {
        if (samplerLocation < 0 || samplerLocation > 31)
            Debug.fatalError("sampler location(" + samplerLocation + ") is out of valid range [0, 31]");

        glActiveTexture(GL_TEXTURE0 + samplerLocation);
        glBindTexture(GL_TEXTURE_2D, m_data.getBufferID());
    }

    private void loadTexture(String filePath) {
        String[] splitArray = filePath.split("\\.");
//        String ext = splitArray[splitArray.length - 1].toUpperCase();

        try {
            if (!new File("./res/" + filePath).exists())
                throw new Exception("'" + filePath + "' texture doesn't exist");

            BufferedImage image = ImageIO.read(new File("./res/" + filePath));
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

            ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);
            boolean hasAlpha = image.getColorModel().hasAlpha();

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = pixels[y * image.getWidth() + x];

                    buffer.put((byte)((pixel >> 16) & 0xFF));
                    buffer.put((byte)((pixel >> 8) & 0xFF));
                    buffer.put((byte)((pixel) & 0xFF));
                    if (hasAlpha)
                        buffer.put((byte)((pixel >> 24) & 0xFF));
                    else
                        buffer.put((byte)(0xFF));
                }
            }

            buffer.flip();

            m_data = new TextureData();

            glBindTexture(GL_TEXTURE_2D, m_data.getBufferID());

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } catch (Exception e) {
            Debug.fatalError(e.getMessage());
        }

        m_loadedTextures.put(filePath, new WeakReference<>(m_data));
    }
}
