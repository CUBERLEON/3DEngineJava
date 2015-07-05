package com.base.engine.core;

import com.base.game.Game;
import com.base.engine.rendering.RenderUtil;
import com.base.engine.rendering.Window;

public class CoreEngine {

    private int m_width;
    private int m_height;
    private String m_title;

    private double m_fpsLimit;
    private long m_fpsRefreshTime;
    private boolean m_fpsUnlimited;

    private Game m_game;

    private boolean m_isRunning;

    public CoreEngine(Game game, double fpsLimit, boolean fpsUnlimited) {
        m_width = 800;
        m_height = 600;
        m_title = "No title";

        m_fpsLimit = fpsLimit;
        m_fpsRefreshTime = (long)(1.0 * Time.SECOND);
        m_fpsUnlimited = fpsUnlimited;

        m_game = game;
        m_isRunning = false;
    }

    private void initRenderingEngine() {
        System.out.println(RenderUtil.getOpenGLVersion());
        RenderUtil.initGraphics();
    }

    public void createWindow(int width, int height, String title) {
        m_width = width;
        m_height = height;
        m_title = title;

        Window.createWindow(width, height, title);
        initRenderingEngine();
    }

    public void start() {
        if (!Window.isCreated()) {
            System.err.println("Error: trying to start Core Engine when Window wasn't created!");
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
                System.out.printf("%.2f\n", frames/(fpsTime/(double)Time.SECOND));
                fpsTime = 0;
                frames = 0;
            }

            if (render || m_fpsUnlimited) {
                render();
                frames++;
            } else {
                try {
                    Thread.sleep(0, 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    private void render() {
        RenderUtil.clearScreen();
        m_game.render();
        Window.render();
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