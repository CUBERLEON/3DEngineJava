package com.base.engine;

public class Game {

    private Mesh m_mesh1;
    private Mesh m_mesh2;
    private Shader m_shader;
    private Material m_material;
    private Transform m_transform;
    private Camera m_camera;
    private PointLight pointLights[] = new PointLight[] {   new PointLight(new Vector3f(1, 1, 0), 2.0f, new Attenuation(0, 0, 1), new Vector3f(0, 1.7f, 0)),
                                                            new PointLight(new Vector3f(0, 1, 1), 2.0f, new Attenuation(0, 0, 1), new Vector3f(1.7f, 1.7f, 0))};
    private DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.1f, new Vector3f(0, -1, -1));

    public Game() {
        m_mesh1 = new Mesh();
        m_mesh2 = ResourceLoader.loadMesh("models/test/teapot.obj");
        m_shader = PhongShader.getInstance();
        m_material = new Material(ResourceLoader.loadTexture("models/cube/default.png"),
                                  new Vector3f(1, 1, 1), 0.9f, 32);
        m_transform = new Transform();
        m_camera = new Camera(new Vector3f(0, 2, 0), new Vector3f(0, -1, 0.5f));

        Transform.setCamera(m_camera);
        Input.setCursor(false);

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                                            new Vertex( new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                                            new Vertex( new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int indices[] = { 0, 1, 2,
                          2, 1, 3};
        m_mesh1.addVertices(vertices, indices, true);

        PhongShader.setDirectionalLight(directionalLight);
        PhongShader.setPointLights(pointLights);
    }

    private int k = 0;

    public void input() {
        m_camera.input();

        if (Input.getKeyDown(Input.KEY_C))
            k = (k + 1) % pointLights.length;

        if (Input.getKey(Input.KEY_UP))
            pointLights[k].getPosition().add(new Vector3f(0, 0, (float) -Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_DOWN))
            pointLights[k].getPosition().add(new Vector3f(0, 0, (float) Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_LEFT))
            pointLights[k].getPosition().add(new Vector3f((float) -Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_RIGHT))
            pointLights[k].getPosition().add(new Vector3f((float) Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_SPACE))
            pointLights[k].getPosition().add(new Vector3f(0, (float) Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_LSHIFT))
            pointLights[k].getPosition().add(new Vector3f(0, (float) -Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_ADD))
            pointLights[k].setIntensity(pointLights[k].getIntensity() + (float) Time.getDelta() * 1.0f);
        if (Input.getKey(Input.KEY_SUBTRACT))
            pointLights[k].setIntensity(pointLights[k].getIntensity() - (float) Time.getDelta() * 1.0f);
    }

    private float tmp = 0.0f;

    public void update() {
//        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(Transform.getCamera().getPosition().div(1f).getAbs(), 0.3f), new Vector3f(0, -1, -1)));
//        RenderUtil.setClearColor(Transform.getCamera().getPosition().div(128f).getAbs());
        tmp += Time.getDelta();

//        m_transform.setTranslation(0, -0.5f, -3);
        m_transform.setRotationDeg(0, 90, 0);
//        m_transform.setRotationRad((float) (Math.PI*Math.sin(tmp)), 0, 0);
//        m_transform.setScale(0.3f, 0.3f, 0.3f);
    }

    public void render() {
        m_shader.bind();
        m_shader.updateUniforms(m_transform.getTransformM(), m_transform.getProjectedTransformM(), m_material);
        m_mesh1.draw();
        m_mesh2.draw();
    }
}
