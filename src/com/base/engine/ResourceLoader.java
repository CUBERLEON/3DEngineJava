package com.base.engine;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class ResourceLoader {

    public static String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

            String line;
            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public static Mesh loadMesh(String filePath) {
        String[] splitArray = filePath.split("\\.");
        String ext = splitArray[splitArray.length - 1].toUpperCase();

        if (!(ext.equals("OBJ"))) {
            System.err.println("ERROR: " + ext + " mesh format isn't supported!");
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Vertex> texCoords = new ArrayList<>();
        ArrayList<Vertex> normals = new ArrayList<>();

        ArrayList<Integer> vertIndices = new ArrayList<>();
        ArrayList<Integer> texIndices = new ArrayList<>();
        ArrayList<Integer> normIndices = new ArrayList<>();

        try {
            BufferedReader meshReader = new BufferedReader(new FileReader("./res/" + filePath));

            String line;
            while ((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#"))
                    continue;

                if (tokens[0].equals("v") && tokens.length == 4) {
                    vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                                                         Float.valueOf(tokens[2]),
                                                         Float.valueOf(tokens[3]))));
                } else if (tokens[0].equals("f") && tokens.length == 4) {
                    for (int i = 1; i <= 3; i++) {
                        String[] values;
                        if (tokens[i].contains("//"))
                            values = tokens[i].split("//");
                        else if (tokens[i].contains("/"))
                            values = tokens[i].split("/");
                        else values = new String[]{tokens[i]};

                        switch (values.length) {
                            case 1: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            case 2: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                    normIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            case 3: vertIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                    texIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                    normIndices.add(Math.abs(Integer.valueOf(values[0])) - 1);
                                break;
                            default:
                                throw new Exception("ERROR: " + filePath + " faces data was corrupted!");
                        }
                    }
                } if (tokens[0].equals("vt") && tokens.length == 4) {
                    texCoords.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                                                          Float.valueOf(tokens[2]),
                                                          Float.valueOf(tokens[3]))));
                } if (tokens[0].equals("vn") && tokens.length == 4) {
                    normals.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                                                        Float.valueOf(tokens[2]),
                                                        Float.valueOf(tokens[3]))));
                }
            }

            meshReader.close();

            if (vertices.size() == 0 || vertIndices.size() == 0)
                throw new Exception("ERROR: " + filePath + " data was corrupted!");

            Vertex[] verticesData = new Vertex[vertices.size()];
            Integer[] indicesData = new Integer[vertIndices.size()];

            vertices.toArray(verticesData);
            vertIndices.toArray(indicesData);

            Mesh res = new Mesh();
            res.addVertices(verticesData, Util.toIntArray(indicesData), true);

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public static Texture loadTexture(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1].toLowerCase();

        try {
            int id = TextureLoader.getTexture(ext, new FileInputStream(new File("./res/" + fileName))).getTextureID();
            return new Texture(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
