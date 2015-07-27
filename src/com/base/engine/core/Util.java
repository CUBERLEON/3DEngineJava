package com.base.engine.core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.base.engine.rendering.Vertex;
import org.lwjgl.BufferUtils;

public class Util {

    public static IntBuffer createIntBuffer(int... values) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);

        buffer.put(values);

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFloatBuffer(Vertex[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);

        for(int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].getPosition().getX());
            buffer.put(vertices[i].getPosition().getY());
            buffer.put(vertices[i].getPosition().getZ());
            buffer.put(vertices[i].getTexCoord().getX());
            buffer.put(vertices[i].getTexCoord().getY());
            buffer.put(vertices[i].getNormal().getX());
            buffer.put(vertices[i].getNormal().getY());
            buffer.put(vertices[i].getNormal().getZ());
        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFloatBuffer(Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                buffer.put(value.get(i, j));

        buffer.flip();

        return buffer;
    }

    public static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static int[] toIntArray(Integer[] data) {
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i].intValue();

        return result;
    }
}
