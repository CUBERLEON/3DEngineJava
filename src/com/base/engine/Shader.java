package com.base.engine;

import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

public class Shader {

    private int m_program;
    private HashMap<String, Integer> m_uniforms;

    public Shader() {
        m_program = glCreateProgram();
        m_uniforms = new HashMap<String, Integer>();

        if (m_program == 0) {
            System.err.println("Shader creation failed: could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    public void bind() {
        glUseProgram(m_program);
    }

    public void addUniform(String name) {
        int uniformLocation = glGetUniformLocation(m_program, name);

        if (uniformLocation == -1) {
            System.err.println("Error: Could not find uniform: " + name);
            new Exception().printStackTrace();
            System.exit(1);
        }

        m_uniforms.put(name, uniformLocation);
    }

    public void updateUniforms(Matrix4f world, Matrix4f perspective, Material material) {}

    public void setUniformI(String uniformName, int value) {
        glUniform1i(m_uniforms.get(uniformName), value);
    }

    public void setUniformF(String uniformName, float value) {
        glUniform1f(m_uniforms.get(uniformName), value);
    }

    public void setUniformV3F(String uniformName, Vector3f value) {
        glUniform3f(m_uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform3F(String uniformName, float x, float y, float z) {
        glUniform3f(m_uniforms.get(uniformName), x, y, z);
    }

    public void setUniformV2F(String uniformName, Vector2f value) {
        glUniform2f(m_uniforms.get(uniformName), value.getX(), value.getY());
    }

    public void setUniform2F(String uniformName, float x, float y) {
        glUniform2f(m_uniforms.get(uniformName), x, y);
    }

    public void setUniformM4F(String uniformName, Matrix4f value) {
        glUniformMatrix4(m_uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
    }

    public void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    public void addGeometryShader(String text) {
        addProgram(text, GL_GEOMETRY_SHADER);
    }

    public void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void compileShader() {
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
            System.err.println("Shader creation failed: could not find valid memory location when adding shader program");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);
        if (glGetShader(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(m_program, shader);
    }
}
