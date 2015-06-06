package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Transform m_transform;

    public Game() {
        m_mesh = ResourceLoader.loadMesh("cube.obj");
        m_shader = new Shader();
        m_transform = new Transform();

        Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)),
                new Vertex(new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(1, -1, 0)),
                new Vertex(new Vector3f(0, 0, 1))};
        int[] indices = new int[] { 0, 1, 2,
                                    1, 0, 3,
                                    2, 1, 3,
                                    0, 2, 3};
        //m_mesh.addVertices(vertices, indices);

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

        m_transform.setTranslation(0, 0, -1.0f);
        m_transform.setRotationRad(0, (float) (Math.PI*Math.sin(tmp)), 0);
        m_transform.setScale(1, 1, 1);

        m_shader.setUniformM("transform", m_transform.getPerspectiveTransformM());
    }

    public void render() {
        m_shader.bind();
        m_mesh.draw();
    }
}
