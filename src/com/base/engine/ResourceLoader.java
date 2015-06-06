package com.base.engine;

import java.io.BufferedReader;
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

    public static Mesh loadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1].toLowerCase();

        if (!(ext.equals("obj"))) {
            System.err.println("Error: " + ext + " mesh format isn't supported!");
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        try {
            BufferedReader meshReader = new BufferedReader(new FileReader("./res/models/" + fileName));

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
                            case 1: indices.add(Integer.valueOf(values[0]) - 1);
                                break;
                            case 2: indices.add(Integer.valueOf(values[0]) - 1);
                                //vertex normal
                                break;
                            case 3: indices.add(Integer.valueOf(values[0]) - 1);
                                //texture vertex
                                //vertex normal
                                break;
                            default:
                                throw new Exception("Error: " + fileName + " faces data was corrupted!");
                        }
                    }
                }
            }

            meshReader.close();

            //System.out.println(indices.size());

            Vertex[] verticesData = new Vertex[vertices.size()];
            Integer[] indicesData = new Integer[indices.size()];

            vertices.toArray(verticesData);
            indices.toArray(indicesData);

            Mesh res = new Mesh();
            res.addVertices(verticesData, Util.toIntArray(indicesData));

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
