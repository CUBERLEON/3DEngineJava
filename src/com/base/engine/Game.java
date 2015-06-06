package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Transform m_transform;
    private Camera m_camera;

    public Game() {
        m_mesh = ResourceLoader.loadMesh("Rubik's Cube.obj");
        m_shader = new Shader();
        m_transform = new Transform();
        m_camera = new Camera();

        Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0)),
                new Vertex(new Vector3f(0, 1, 0)),
                new Vertex(new Vector3f(1, -1, 0)),
                new Vertex(new Vector3f(0, 0, 1))};
        int[] indices = new int[] { 0, 1, 2,
                                    1, 0, 3,
                                    2, 1, 3,
                                    0, 2, 3};
        //m_mesh.addVertices(vertices, indices);

        Transform.setCamera(m_camera);

        m_shader.addVertexShader(ResourceLoader.loadShader("basic.vs"));
        m_shader.addFragmentShader(ResourceLoader.loadShader("basic.fs"));
        m_shader.compileShader();

        m_shader.addUniform("transform");
    }

    public void input() {
        float moveValue = (float) (50.0f * Time.getDelta());
        float rotateValue = (float) (3.0f * Time.getDelta());

        if (Input.getKey(Input.KEY_W))
            m_camera.move(m_camera.getForward(), moveValue);
        if (Input.getKey(Input.KEY_S))
            m_camera.move(m_camera.getForward(), -moveValue);
        if (Input.getKey(Input.KEY_A))
            m_camera.move(m_camera.getLeft(), moveValue);
        if (Input.getKey(Input.KEY_D))
            m_camera.move(m_camera.getRight(), moveValue);

        if (Input.getKey(Input.KEY_UP))
            m_camera.rotateX(rotateValue);
        if (Input.getKey(Input.KEY_DOWN))
            m_camera.rotateX(-rotateValue);
        if (Input.getKey(Input.KEY_LEFT))
            m_camera.rotateY(rotateValue);
        if (Input.getKey(Input.KEY_RIGHT))
            m_camera.rotateY(-rotateValue);
    }

    private float tmp = 0.0f;

    public void update() {
        tmp += Time.getDelta();

        m_transform.setTranslation(0, 0, -200);
        m_transform.setRotationRad(0, (float) (Math.PI*Math.sin(tmp)), 0);
        m_transform.setScale(0.3f, 0.3f, 0.3f);

        m_shader.setUniformM("transform", m_transform.getPerspectiveTransformM());
    }

    public void render() {
        m_shader.bind();
        m_mesh.draw();
    }
}
