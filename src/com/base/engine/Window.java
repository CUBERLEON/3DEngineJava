package com.base.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {

    public static void createWindow(int width, int height, String title) {
        Display.setTitle(title);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    public static void render() {
        if (Display.isCreated())
            Display.update();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public static boolean isCreated() {
        return Display.isCreated();
    }

    public static int getWidth() {
        return Display.getDisplayMode().getWidth();
    }

    public static int getHeight() {
        return Display.getDisplayMode().getHeight();
    }

    public static String getTitle() {
        return Display.getTitle();
    }

    public static void changeResolution(int width, int height) {
        if (width == getWidth() && height == getHeight())
            return;

        String title = getTitle();

        Window.dispose();
        Window.createWindow(width, height, title);
        RenderUtil.initGraphics();
    }

    public static void changeTitle(String title) {
        if (title.equals(getTitle()))
            return;

        int width = getWidth();
        int height = getHeight();

        Window.dispose();
        Window.createWindow(width, height, title);
    }
}
