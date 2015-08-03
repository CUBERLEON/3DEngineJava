package com.base.engine.rendering.shaders;

import com.base.engine.core.*;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.RenderingEngine;
import javafx.util.Pair;
import org.mentaregex.Regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL32.*;

public abstract class Shader {

    private class GLSLvariable {
        public String type;
        public String name;

        public GLSLvariable(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    private int m_program;
    private HashMap<String, Integer> m_uniformLocations;

    private ArrayList<GLSLvariable> m_uniforms;

    protected Shader(String fileName) {
        m_program = glCreateProgram();
        m_uniformLocations = new HashMap<>();

        m_uniforms = new ArrayList<>();

        if (m_program == 0) {
            System.err.println("Fatal ERROR: Shader creation failed. Could not find valid memory location in constructor");
            new Exception().printStackTrace();
            System.exit(1);
        }

        String vertexShaderSource = loadShader(fileName+".vs");
        String fragmentShaderSource = loadShader(fileName+".fs");

        addVertexShader(vertexShaderSource);
        addFragmentShader(fragmentShaderSource);

        compileShader();

        addAllUniforms(vertexShaderSource);
        addAllUniforms(fragmentShaderSource);
    }

    public void bind() {
        glUseProgram(m_program);
    }

    private void addUniform(String name) {
        int uniformLocation = glGetUniformLocation(m_program, name);

        if (uniformLocation == -1) {
            System.err.println("Fatal ERROR: Could not find uniform: " + name);
            new Exception().printStackTrace();
            System.exit(1);
        }

        m_uniformLocations.put(name, uniformLocation);
    }

    private void addAllUniforms(String text) {
        String[] m = Regex.match(text, "/uniform\\s+([^\\s]+)\\s+([^\\s]+)\\s*;/g");

        for (int i = 0; i < m.length; i+=2) {
            String uniformType = m[i];
            String uniformName = m[i+1];

            ArrayList<GLSLvariable> subUniforms = parseStruct(uniformType, text);
            if (subUniforms == null) {
                addUniform(uniformName);

                m_uniforms.add(new GLSLvariable(uniformType, uniformName));
            }
            else {
                for (GLSLvariable subUniform : subUniforms) {
                    addUniform(uniformName + "." + subUniform.name);

                    m_uniforms.add(new GLSLvariable(subUniform.type, uniformName + "." + subUniform.name));
                }
            }
        }
    }

    private ArrayList<GLSLvariable> parseStruct(String structName, String text) {
        String[] m = Regex.match(text, "/struct\\s+"+structName+"\\s+\\{([^\\}]*)\\};/");
        if (m == null) {
            return null;
        }
        else {
            if (m.length != 1) {
                System.err.println("Fatal ERROR: adding shader uniforms failed on parsing struct '" + structName + "'");
                new Exception().printStackTrace();
                System.exit(1);
            }

            m = Regex.match(m[0], "/([^\\s]+)\\s+([^\\s]+)\\s*;/g");

            ArrayList<GLSLvariable> res = new ArrayList<>();

            for (int i = 0; i < m.length; i+=2) {
                String fieldType = m[i];
                String fieldName = m[i+1];

                ArrayList<GLSLvariable> subFields = parseStruct(fieldType, text);

                if (subFields == null) {
                    res.add(new GLSLvariable(fieldType, fieldName));
                } else {
                    for (GLSLvariable subField : subFields) {
                        res.add(new GLSLvariable(subField.type, fieldName + "." + subField.name));
                    }
                }
            }

            return res;
        }
    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
//        for (int i = 0; i < )
    }

    protected void setUniformI(String uniformName, int value) {
        glUniform1i(m_uniformLocations.get(uniformName), value);
    }

    protected void setUniformF(String uniformName, float value) {
        glUniform1f(m_uniformLocations.get(uniformName), value);
    }

    protected void setUniformV3F(String uniformName, Vector3f value) {
        glUniform3f(m_uniformLocations.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    protected void setUniform3F(String uniformName, float x, float y, float z) {
        glUniform3f(m_uniformLocations.get(uniformName), x, y, z);
    }

    protected void setUniformV2F(String uniformName, Vector2f value) {
        glUniform2f(m_uniformLocations.get(uniformName), value.getX(), value.getY());
    }

    protected void setUniform2F(String uniformName, float x, float y) {
        glUniform2f(m_uniformLocations.get(uniformName), x, y);
    }

    protected void setUniformM4F(String uniformName, Matrix4f value) {
        glUniformMatrix4(m_uniformLocations.get(uniformName), true, Util.createFloatBuffer(value));
    }

    private void addVertexShaderFromFile(String fileName) {
        addVertexShader(loadShader(fileName));
    }

//    private void addGeometryShaderFromFile(String fileName) {
//        addGeometryShader(loadShader(fileName));
//    }

    private void addFragmentShaderFromFile(String fileName) {
        addFragmentShader(loadShader(fileName));
    }

    private void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

//    private void addGeometryShader(String text) {
//        addProgram(text, GL_GEOMETRY_SHADER);
//    }

    private void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    private void compileShader() {
        glLinkProgram(m_program);
        if (glGetProgram(m_program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(m_program, 1024));
            System.exit(1);
        }

        glValidateProgram(m_program);
        if (glGetProgram(m_program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(m_program, 1024));
            System.exit(1);
        }
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Fatal ERROR: Shader creation failed! Could not find valid memory location when adding shader program");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);
        if (glGetShader(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println("Fatal ERROR: Shader compilation failed!");
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(m_program, shader);
    }

    private static String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));

            String line;
            while ((line = shaderReader.readLine()) != null) {
                if (line.startsWith("include")) {
                    Matcher m = Pattern.compile("\\s*include\\s+\"([^\"]+)\"\\s*").matcher(line);
                    if (m.find())
                        shaderSource.append(loadShader(m.group(1)));
                } else {
                    shaderSource.append(line + "\n");
                }
            }

            shaderReader.close();
        } catch (Exception e) {
            System.err.println("Fatal ERROR: Loading shader '" + fileName + "' failed!");
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }
}
