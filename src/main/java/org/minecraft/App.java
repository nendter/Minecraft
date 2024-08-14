package org.minecraft;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.minecraft.utils.Logging;

public class App {

    private static final Logger LOG = Logging.get(App.class);

    private Window window;
    private Inputs inputs;
    private Loop loop;

    public App() {
        boolean setupSuccess = initGLFW();
        if (!setupSuccess) {
            LOG.error("Failed to setup GLFW!");
        }
    }

    public static void main(String[] args) {
        new App().start();
    }

    public void start() {
        this.window = new Window();
        boolean windowSuccess = this.window.open();
        if (!windowSuccess) {
            LOG.error("Could not open window!");
            return;
        }
        this.inputs = new Inputs(this.window);
        this.loop = new Loop(this.window);
        boolean loopSuccess = this.loop.start();
        if (loopSuccess) {
            this.stop();
        }
    }

    private void stop() {
        this.cleanUpGLFW();
    }

    private boolean initGLFW() {
        try (GLFWErrorCallback cb = GLFWErrorCallback.createPrint(System.err)) {
            cb.set();
        }
        if (!GLFW.glfwInit()) {
            LOG.error("Could not initialize GLFW!");
            return false;
        }
        return true;
    }

    private void cleanUpGLFW() {
        Callbacks.glfwFreeCallbacks(this.window.getId());
        try (GLFWErrorCallback cb = GLFW.glfwSetErrorCallback(null)) {
            if (cb != null) {
                cb.free();
            }
        }
    }
}
