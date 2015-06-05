package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Transform m_transform;

    public Game() {
        m_mesh = new Mesh();
        m_shader = new Shader();
        m_transform = new Transform();

        Vertex[] data = new Vertex[] { new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f)),
                                       new Vertex(new Vector3f(0.0f, 1.0f, 0.0f)),
                                       new Vertex(new Vector3f(1.0f, -1.0f, 0.0f))};
        m_mesh.addVertices(data);

        m_shader.addVertexShader(ResourceLoader.loadShader("basic.vs"));
        m_shader.addFragmentShader(ResourceLoader.loadShader("basic.fs"));
        m_shader.compileShader();

        m_shader.addUniform("clampValue");
        m_shader.addUniform("transform");
    }

    public void input() {

        if (Input.getMouseDown(0))
            System.out.println("You've just clicked at" + Input.getMousePosition().toString() +"!");
    }

    private float tmp = 0.0f;

    public void update() {
        tmp += Time.getDelta();

        m_transform.setTranslation((float)Math.cos(tmp), 0.0f, 0.0f);
        m_transform.setRotationRad(0, 0, (float) (Math.PI*Math.sin(tmp)));
        m_transform.setScale((float)Math.sin(tmp), (float)Math.sin(tmp), 1.0f);

        m_shader.setUniformF("clampValue", (float) Math.abs(Math.sin(tmp)));
        m_shader.setUniformM("transform", m_transform.getTransformM());
    }

    public void render() {
        m_shader.bind();
        m_mesh.draw();
    }
}
