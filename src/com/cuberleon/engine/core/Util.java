package com.cuberleon.engine.core;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.cuberleon.engine.rendering.Vertex;
import org.lwjgl.BufferUtils;

public class Util {

    public static IntBuffer createIntBuffer(int... values) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);

        buffer.put(values);

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createBuffer(Vertex[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPosition().getX());
            buffer.put(vertex.getPosition().getY());
            buffer.put(vertex.getPosition().getZ());
            buffer.put(vertex.getTexCoord().getX());
            buffer.put(vertex.getTexCoord().getY());
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFloatBuffer(Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                buffer.put(value.get(i, j));

        buffer.flip();

        return buffer;
    }

    public static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<>();

        for (String str : data)
            if (!str.equals(""))
                result.add(str);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static int[] toIntArray(Integer[] data) {
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++)
            result[i] = data[i];

        return result;
    }
}
