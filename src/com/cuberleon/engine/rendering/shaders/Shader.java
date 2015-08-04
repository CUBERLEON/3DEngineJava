package com.cuberleon.engine.rendering.shaders;

import com.cuberleon.engine.components.DirectionalLight;
import com.cuberleon.engine.components.PointLight;
import com.cuberleon.engine.components.SpotLight;
import com.cuberleon.engine.core.*;
import com.cuberleon.engine.rendering.Material;
import com.cuberleon.engine.rendering.RenderingEngine;
import com.cuberleon.engine.rendering.resources.ShaderData;
import org.mentaregex.Regex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL32.*;

public class Shader {

    private static HashMap<String, WeakReference<ShaderData>> m_loadedShaders = new HashMap<>();
    private ShaderData m_data;

    private String m_filePath;

    public Shader(String filePath) {
        m_filePath = filePath;

        if (m_loadedShaders.containsKey(filePath) &&
        (m_data = m_loadedShaders.get(filePath).get()) != null) {
            m_data.addReference();
        } else {
            m_data = new ShaderData();
            m_loadedShaders.put(filePath, new WeakReference<>(m_data));

            String vertexShaderSource = loadShader(filePath + ".vs");
            String fragmentShaderSource = loadShader(filePath + ".fs");

            addVertexShader(vertexShaderSource);
            addFragmentShader(fragmentShaderSource);

            compileShader();

            addAllUniforms(vertexShaderSource);
            addAllUniforms(fragmentShaderSource);

            Debug.info("Shader('%s') was loaded", m_filePath);
        }
    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        for (GLSLvariable variable : m_data.getUniforms()) {
            char firstChar = variable.name.charAt(0);

            switch (firstChar) {
                //transform uniforms
                case 't': {
                    if (variable.name.equals("t_mvpTransform") && variable.type.equals("mat4"))
                        setUniformM4F("t_mvpTransform", transform.getModelViewProjectionTransform(renderingEngine.getMainCamera()));
                    else if (variable.name.equals("t_mTransform") && variable.type.equals("mat4"))
                        setUniformM4F("t_mTransform", transform.getRealModelTransform());

                    break;
                }
                //material uniforms
                case 'm': {
                    if (variable.name.equals("m_diffuse") && variable.type.equals("sampler2D"))
                        material.getTexture("diffuse").bind(renderingEngine.getInteger("diffuse"));
                    else if (variable.name.equals("m_specularIntensity") && variable.type.equals("float"))
                        setUniformF("m_specularIntensity", material.getFloat("specularIntensity"));
                    else if (variable.name.equals("m_specularPower") && variable.type.equals("float"))
                        setUniformF("m_specularPower", material.getFloat("specularPower"));

                    break;
                }
                //light uniforms
                case 'l': {
                    if (variable.name.equals("l_ambient") && variable.type.equals("vec3"))
                        setUniformV3F("l_ambient", renderingEngine.getVector3f("ambientLight"));
                    else if (variable.name.equals("l_directional") && variable.type.equals("DirectionalLight"))
                        setUniform("l_directional", (DirectionalLight) renderingEngine.getActiveLight());
                    else if (variable.name.equals("l_point") && variable.type.equals("PointLight"))
                        setUniform("l_point", (PointLight) renderingEngine.getActiveLight());
                    else if (variable.name.equals("l_spot") && variable.type.equals("SpotLight"))
                        setUniform("l_spot", (SpotLight) renderingEngine.getActiveLight());

                    break;
                }
                //additional vertex shader uniforms
                case 'v': {


                    break;
                }
                //additional fragment shader uniforms
                case 'f': {
                    if (variable.name.equals("f_eyePosition") && variable.type.equals("vec3"))
                        setUniformV3F("f_eyePosition", renderingEngine.getMainCamera().getTransform().getRealPosition());

                    break;
                }
            }
        }
    }

    public void dispose() {
        if (m_data != null)
            m_data.dispose();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (m_data.deleteReference()) {
                if (m_filePath != null) {
                    m_loadedShaders.remove(m_filePath, m_data);
                    Debug.info("Shader(" + m_filePath + ") was deleted (finalize)");
                }
            }
        } catch (Throwable t) {
            throw t;
        } finally {
            super.finalize();
        }
    }

    public void bind() {
        glUseProgram(m_data.getProgram());
    }

    private void addUniform(GLSLvariable uniform) {
        int uniformLocation = glGetUniformLocation(m_data.getProgram(), uniform.name);

        if (uniformLocation == -1)
            Debug.error("unnecessary uniform '" + uniform.name + "' in Shader('" + m_filePath + "')");
        else
            m_data.getUniformLocations().put(uniform.name, uniformLocation);
    }

    private void addAllUniforms(String text) {
        String[] m = Regex.match(text, "/uniform\\s+([^\\s]+)\\s+([^\\s]+)\\s*;/g");

        for (int i = 0; i < m.length; i+=2) {
            GLSLvariable uniform = new GLSLvariable(m[i], m[i+1]);

            m_data.getUniforms().add(uniform);

            ArrayList<GLSLvariable> subUniforms = parseStruct(uniform.type, text);
            if (subUniforms == null) {
                addUniform(uniform);
//                m_allUniforms.add(uniform);
            }
            else
                for (GLSLvariable subUniform : subUniforms) {
                    addUniform(new GLSLvariable(subUniform.type, uniform.name + "." + subUniform.name));
//                    m_allUniforms.add(new GLSLvariable(subUniform.type, uniform.name + "." + subUniform.name));
                }
        }
    }

    private ArrayList<GLSLvariable> parseStruct(String structName, String text) {
        String[] m = Regex.match(text, "/struct\\s+"+structName+"\\s+\\{([^\\}]*)\\};/");
        if (m == null)
            return null;
        else {
            m = Regex.match(m[0], "/([^\\s]+)\\s+([^\\s]+)\\s*;/g");

            ArrayList<GLSLvariable> res = new ArrayList<>();

            for (int i = 0; i < m.length; i+=2) {
                GLSLvariable variable = new GLSLvariable(m[i], m[i+1]);

                ArrayList<GLSLvariable> subVariables = parseStruct(variable.type, text);
                if (subVariables == null)
                    res.add(variable);
                else
                    for (GLSLvariable subVariable : subVariables)
                        res.add(new GLSLvariable(subVariable.type, variable.name + "." + subVariable.name));
            }

            return res;
        }
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
        addShader(text, GL_VERTEX_SHADER);
    }

//    private void addGeometryShader(String text) {
//        addShader(text, GL_GEOMETRY_SHADER);
//    }

    private void addFragmentShader(String text) {
        addShader(text, GL_FRAGMENT_SHADER);
    }

    private void compileShader() {
        glLinkProgram(m_data.getProgram());
        if (glGetProgram(m_data.getProgram(), GL_LINK_STATUS) == 0)
            Debug.fatalError(glGetShaderInfoLog(m_data.getProgram(), 1024));

        glValidateProgram(m_data.getProgram());
        if (glGetProgram(m_data.getProgram(), GL_VALIDATE_STATUS) == 0)
            Debug.fatalError(glGetShaderInfoLog(m_data.getProgram(), 1024));
    }

    private void addShader(String text, int type) {
        int shader = glCreateShader(type);

        String shaderType = String.valueOf(type);
        if (type == GL_VERTEX_SHADER) shaderType = "GL_VERTEX_SHADER";
        if (type == GL_FRAGMENT_SHADER) shaderType = "GL_FRAGMENT_SHADER";
//        if (type == GL_GEOMETRY_SHADER) shaderType = "GL_GEOMETRY_SHADER";

        if (shader == 0)
            Debug.fatalError("could not find valid memory location when adding shader type='" + shaderType + "' in Shader('" + m_filePath + "')");

        glShaderSource(shader, text);
        glCompileShader(shader);
        if (glGetShader(shader, GL_COMPILE_STATUS) == 0)
            Debug.fatalError("shader type='" + shaderType + "' compilation failed in Shader('" + m_filePath + "')\n" + glGetShaderInfoLog(shader, 1024));

        glAttachShader(m_data.getProgram(), shader);
    }

    private static String loadShader(String filePath) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filePath));

            String line;
            while ((line = shaderReader.readLine()) != null) {
                if (line.startsWith("include")) {
                    String[] m = Regex.match(line, "/include\\s+\"([^\"]+)\"/g");
                    for (String include : m)
                        shaderSource.append(loadShader(include));
                } else {
                    shaderSource.append(line).append("\n");
                }
            }

            shaderReader.close();
        } catch (Exception e) {
            Debug.fatalError("loading shader '" + filePath + "' failed!");
        }

        return shaderSource.toString();
    }

    protected void setUniformI(String uniformName, int value) {
        glUniform1i(m_data.getUniformLocations().get(uniformName), value);
    }

    protected void setUniformF(String uniformName, float value) {
        glUniform1f(m_data.getUniformLocations().get(uniformName), value);
    }

    protected void setUniformV3F(String uniformName, Vector3f value) {
        glUniform3f(m_data.getUniformLocations().get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    protected void setUniform3F(String uniformName, float x, float y, float z) {
        glUniform3f(m_data.getUniformLocations().get(uniformName), x, y, z);
    }

    protected void setUniformV2F(String uniformName, Vector2f value) {
        glUniform2f(m_data.getUniformLocations().get(uniformName), value.getX(), value.getY());
    }

    protected void setUniform2F(String uniformName, float x, float y) {
        glUniform2f(m_data.getUniformLocations().get(uniformName), x, y);
    }

    protected void setUniformM4F(String uniformName, Matrix4f value) {
        glUniformMatrix4(m_data.getUniformLocations().get(uniformName), true, Util.createFloatBuffer(value));
    }

    protected void setUniform(String uniformName, DirectionalLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformV3F(uniformName + ".direction", value.getDirection());
    }

    protected void setUniform(String uniformName, PointLight value) {
        setUniformV3F(uniformName + ".color", value.getColor());
        setUniformF(uniformName + ".intensity", value.getIntensity());
        setUniformF(uniformName + ".attenuation.constant", value.getAttenuation().getConstant());
        setUniformF(uniformName + ".attenuation.linear", value.getAttenuation().getLinear());
        setUniformF(uniformName + ".attenuation.exponent", value.getAttenuation().getExponent());
        setUniformV3F(uniformName + ".position", value.getTransform().getPosition());
        setUniformF(uniformName + ".range", value.getRange());
    }

    protected void setUniform(String uniformName, SpotLight value) {
        setUniform(uniformName + ".pointLight", (PointLight) value);
        setUniformV3F(uniformName + ".direction", value.getDirection());
        setUniformF(uniformName + ".cutoff", value.getCutoff());
    }
}
