package com.base.engine;

public class Game {

    private Mesh m_mesh;
    private Shader m_shader;
    private Material m_material;
    private Transform m_transform;
    private Camera m_camera;

    public Game() {
        //m_mesh = ResourceLoader.loadMesh("Rubik's Cube.obj");
        m_mesh = new Mesh();
        m_shader = PhongShader.getInstance();
        m_material = new Material(ResourceLoader.loadTexture("test.png"), new Vector3f(1, 1, 1));
        m_transform = new Transform();
        m_camera = new Camera();

        Transform.setCamera(m_camera);
        Input.setCursor(false);

        Vertex[] vertices = new Vertex[] { new Vertex(new Vector3f(0, 0, -2), new Vector2f(0.0f, 0.0f)),
                                           new Vertex(new Vector3f(-(float)Math.sqrt(3), 0, 1), new Vector2f(1.0f, 0.0f)),
                                           new Vertex(new Vector3f((float)Math.sqrt(3), 0, 1), new Vector2f(0.5f, (float)Math.sqrt(3)/2)),
                                           new Vertex(new Vector3f(0, (float)Math.sqrt(8), 0), new Vector2f(0.5f, (float)Math.sqrt(3)/6))};
        int[] indices = new int[] { 2, 1, 0,
                                    3, 0, 1,
                                    3, 1, 2,
                                    3, 2, 0};
        m_mesh.addVertices(vertices, indices, true);

        PhongShader.setAmbientLight(new Vector3f(0.01f, 0.01f, 0.01f));
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(new Vector3f(1, 1, 1), 0.5f), new Vector3f(0, -1, -1)));
    }

    public void input() {
        m_camera.input();
    }

    private float tmp = 0.0f;

    public void update() {
        //RenderUtil.setClearColor(Transform.getCamera().getPosition().div(128f).abs());
        tmp += Time.getDelta();

        m_transform.setTranslation(0, 0, -8);
        m_transform.setRotationRad(0/*(float) (Math.PI*Math.sin(tmp))*/, (float) (Math.PI * Math.sin(tmp)), 0);
        m_transform.setScale(1.0f, 1.0f, 1.0f);
    }

    public void render() {
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformM(), m_transform.getProjectedTransformM(), m_material);
        m_mesh.draw();
    }
}
