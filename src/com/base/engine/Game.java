package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Material m_material;
    private Transform m_transform;
    private Camera m_camera;

    public Game() {
        m_mesh = new Mesh();//ResourceLoader.loadMesh("Rubik's Cube.obj");
        m_shader = new BasicShader();
        m_material = new Material(ResourceLoader.loadTexture("test.png"), new Vector3f(0, 1, 0));
        m_transform = new Transform();
        m_camera = new Camera();

        Transform.setCamera(m_camera);
        Input.setCursor(false);

        Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0.0f, 0.5f)),
                                           new Vertex(new Vector3f(0, 1, 0), new Vector2f(1.0f, 0.0f)),
                                           new Vertex(new Vector3f(1, -1, 0), new Vector2f(1.0f, 0.5f)),
                                           new Vertex(new Vector3f(0, 0, 1), new Vector2f(1.0f, 1.0f))};
        int[] indices = new int[] { 0, 1, 2,
                                    1, 0, 3,
                                    2, 1, 3,
                                    0, 2, 3};
        m_mesh.addVertices(vertices, indices);
    }

    public void input() {
        m_camera.input();
    }

    private float tmp = 0.0f;

    public void update() {
        RenderUtil.setClearColor(Transform.getCamera().getPosition().div(128f).abs());
        tmp += Time.getDelta();

        m_transform.setTranslation(0, 0, -2);
        //m_transform.setRotationRad(0, (float) (Math.PI*Math.sin(tmp)), 0);
        m_transform.setScale(0.3f, 0.3f, 0.3f);
    }

    public void render() {
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformM(), m_transform.getPerspectiveTransformM(), m_material);
        m_mesh.draw();
    }
}
