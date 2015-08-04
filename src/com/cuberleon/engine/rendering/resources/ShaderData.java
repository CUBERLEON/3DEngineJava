package com.cuberleon.engine.rendering.resources;

import com.cuberleon.engine.core.Debug;
import com.cuberleon.engine.rendering.shaders.GLSLvariable;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class ShaderData {

    private int m_program;

    private HashMap<String, Integer> m_uniformLocations;
    private ArrayList<GLSLvariable> m_uniforms; //without sub GLSLvariables
//    private ArrayList<GLSLvariable> m_allUniforms; //all GLSLvariables

    private int m_referencesCount;

    public ShaderData() {
        m_program = glCreateProgram();

        if (m_program == 0)
            Debug.fatalError("shader program creation failed");

        m_uniformLocations = new HashMap<>();
        m_uniforms = new ArrayList<>();
//        m_allUniforms = new ArrayList<>();

        m_referencesCount = 1;
    }

    public void dispose() {
        glDeleteProgram(m_program);
        m_uniforms.clear();
        m_uniformLocations.clear();
        Debug.info("ShaderData(" + m_program + ") disposed");
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            Debug.info("ShaderData(" + m_program + ") deleted (finalize)");
            glDeleteProgram(m_program);
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    public void addReference() {
        m_referencesCount++;
    }

    public boolean deleteReference() {
        m_referencesCount--;
        return m_referencesCount == 0;
    }

    public int getProgram() {
        return m_program;
    }

    public HashMap<String, Integer> getUniformLocations() {
        return m_uniformLocations;
    }

    public ArrayList<GLSLvariable> getUniforms() {
        return m_uniforms;
    }

//    public ArrayList<GLSLvariable> getAllUniforms() {
//        return m_allUniforms;
//    }
}
