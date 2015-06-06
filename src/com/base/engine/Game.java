package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Transform m_transform;

    public Game() {
        m_mesh = ResourceLoader.loadMesh("EAGLE_1.OBJ");
        m_shader = new Shader();
        m_transform = new Transform();

        m_shader.addVertexShader(ResourceLoader.loadShader("basic.vs"));
        m_shader.addFragmentShader(ResourceLoader.loadShader("basic.fs"));
        m_shader.compileShader();

        m_shader.addUniform("transform");
    }

    public void input() {

        if (Input.getMouseDown(0))
            System.out.println("You've just clicked at" + Input.getMousePosition().toString() +"!");
    }

    private float tmp = 0.0f;

    public void update() {
        tmp += Time.getDelta();

        m_transform.setTranslation((float)Math.cos(tmp)/2.0f, -0.5f, 0);
        m_transform.setRotationRad(0, (float) (Math.PI*Math.sin(tmp)), 0);
        m_transform.setScale(3.0f, 3.0f, 3.0f);

        m_shader.setUniformM("transform", m_transform.getTransformM());
    }

    public void render() {
        m_shader.bind();
        m_mesh.draw();
    }
}
