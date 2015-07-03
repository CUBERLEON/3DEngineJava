package com.base.engine;

public class Game {

    private Mesh m_mesh1;
    private Mesh m_mesh2;
    private Shader m_shader;
    private Material m_material;
    private Transform m_transform;
    private Camera m_camera;
    private DirectionalLight m_directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.3f, new Vector3f(0, -1, -1));
    private PointLight m_pointLights[] = new PointLight[] { new PointLight(new Vector3f(1, 1, 0), 2.0f, new Attenuation(0, 0, 1), new Vector3f(0, 1.7f, 0)),
                                                            new PointLight(new Vector3f(0, 1, 1), 2.0f, new Attenuation(0, 0, 1), new Vector3f(1.7f, 1.7f, 0))};
    private SpotLight m_spotLights[] = new SpotLight[] { new SpotLight(new PointLight(new Vector3f(1, 1, 1), 2.0f, new Attenuation(1.5f, 0, 0.15f), new Vector3f(3, 3, 5)),
                                                                       new Vector3f(-1, -1, 0), (float)Math.cos(Math.toRadians(15.0f)))};

    public Game() {
        m_mesh2 = new Mesh("models/test/teapot.obj");
        m_shader = PhongShader.getInstance();
        m_material = new Material(new Texture("models/cube/default.png"),
                                  new Vector3f(1, 1, 1), 1.0f, 8);
        m_transform = new Transform();
        m_camera = new Camera(new Vector3f(1, 1, 1), new Vector3f(-1.0f, -1.0f, -1.0f));

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
        m_mesh1 = new Mesh(vertices, indices, true);

        PhongShader.setAmbientLight(new Vector3f(0, 0, 0));
        PhongShader.setDirectionalLight(m_directionalLight);
        PhongShader.setPointLights(m_pointLights);
        PhongShader.setSpotLights(m_spotLights);
    }

    private int k = 0;

    public void input() {
        m_camera.input();

        if (Input.getKeyDown(Input.KEY_C))
            k = (k + 1) % m_pointLights.length;

        if (Input.getKey(Input.KEY_UP))
            m_pointLights[k].getPosition().add(new Vector3f(0, 0, (float) -Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_DOWN))
            m_pointLights[k].getPosition().add(new Vector3f(0, 0, (float) Time.getDelta() * 3.0f));
        if (Input.getKey(Input.KEY_LEFT))
            m_pointLights[k].getPosition().add(new Vector3f((float) -Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_RIGHT))
            m_pointLights[k].getPosition().add(new Vector3f((float) Time.getDelta() * 3.0f, 0, 0));
        if (Input.getKey(Input.KEY_SPACE))
            m_pointLights[k].getPosition().add(new Vector3f(0, (float) Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_LSHIFT))
            m_pointLights[k].getPosition().add(new Vector3f(0, (float) -Time.getDelta() * 3.0f, 0));
        if (Input.getKey(Input.KEY_ADD))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() + (float) Time.getDelta() * 3.0f);
        if (Input.getKey(Input.KEY_SUBTRACT))
            m_pointLights[k].setIntensity(m_pointLights[k].getIntensity() - (float) Time.getDelta() * 3.0f);
    }

    private float tmp = 0.0f;

    public void update() {
//        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(Transform.getCamera().getPosition().getDiv(1f).abs(), 0.3f), new Vector3f(0, -1, -1)));
//        RenderUtil.setClearColor(Transform.getCamera().getPosition().getDiv(2).abs());
        tmp += Time.getDelta();

        m_spotLights[0].setDirection(m_camera.getForward());
        m_spotLights[0].getPointLight().setPosition(m_camera.getPosition());

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
