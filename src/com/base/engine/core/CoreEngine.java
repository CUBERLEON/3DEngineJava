package com.base.engine.core;

import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.Window;

public class CoreEngine {

    private double m_fpsLimit;
    private long m_fpsRefreshTime;
    private boolean m_fpsUnlimited;

    private RenderingEngine m_renderingEngine;

    private Game m_game;
    private boolean m_isRunning;

    public CoreEngine(Game game, double fpsLimit, boolean fpsUnlimited) {
        m_fpsLimit = fpsLimit;
        m_fpsRefreshTime = (long)(1.0 * Time.SECOND);
        m_fpsUnlimited = fpsUnlimited;

        m_game = game;
        m_isRunning = false;

        System.out.println("INFO: CoreEngine(fpsLimit = " + fpsLimit + ", fpsUnlimited = " + fpsUnlimited + ") was successfully created");
    }

    public void createWindow(int width, int height, String title) {
        Window.createWindow(width, height, title);

        m_renderingEngine = new RenderingEngine();

        Shader.setRenderingEngine(m_renderingEngine);
        m_game.setRenderingEngine(m_renderingEngine);
    }

    public void start() {
        if (!Window.isCreated()) {
            System.err.println("ERROR: trying to start CoreEngine when Window wasn't created!");
            new Exception().printStackTrace();
            return;
        }

        if (m_isRunning)
            return;

        m_isRunning = true;
        m_game.init();
        run();
    }

    private void run() {
        int frames = 0;
        long fpsTime = 0;

        long startTime = Time.getTime();
        long frameTime = (long)(Time.SECOND / m_fpsLimit);
        long unprocessedTime = 0;

        while (m_isRunning) {
            if (Window.isCloseRequested())
                stop();

            long endTime = Time.getTime();
            long passedTime = endTime - startTime;
            startTime = endTime;

            unprocessedTime += passedTime;
            fpsTime += passedTime;

            boolean render = false;

//            Time.setDelta((double)passedTime / (double)Time.SECOND);
//            m_game.input();
//            Input.update();

            if (unprocessedTime >= frameTime) {
                long sceneTime = frameTime * (unprocessedTime / frameTime);
                unprocessedTime -= sceneTime;
                Time.setDelta(sceneTime / (double)Time.SECOND);

                render = true;

                m_game.input();
                m_game.update();
                Input.update();
            }

            if (fpsTime >= m_fpsRefreshTime) {
                System.out.printf("INFO: %.2f fps\n", frames/(fpsTime/(double)Time.SECOND));
                fpsTime = 0;
                frames = 0;
            }

            if (render || m_fpsUnlimited) {
                m_renderingEngine.render(m_game.getRootObject());
                frames++;
            } else {
                try {
                    Thread.sleep(0, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    public void stop() {
        if (!m_isRunning)
            return;

        m_isRunning = false;
    }

    private void cleanUp() {
        Window.dispose();
    }

    public double getFPSLimit() {
        return m_fpsLimit;
    }

    public void setFPSLimit(double limit) {
        this.m_fpsLimit = limit;
    }

    public double getFPSRefreshTime() {
        return m_fpsRefreshTime / (double)Time.SECOND;
    }

    public void setFPSRefreshTime(double fpsRefreshTime) {
        m_fpsRefreshTime = (long)(fpsRefreshTime * Time.SECOND);
    }

    public boolean isFPSUnlimited() {
        return m_fpsUnlimited;
    }

    public void setFPSUnlimited(boolean fpsUnlimited) {
        m_fpsUnlimited = fpsUnlimited;
    }
}