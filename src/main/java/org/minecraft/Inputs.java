package org.minecraft;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.minecraft.utils.Logging;

public class Inputs {

    private static final Logger LOG = Logging.get(Inputs.class);
    private final Window window;

    public Inputs(Window window) {
        this.window = window;

        GLFWKeyCallback cb = GLFW.glfwSetKeyCallback(this.window.getId(), this::handleKey);
        if (cb != null) {
            cb.close();
        }
    }

    public void handleKey(long window, int key, int scancode, int action, int mods) {
        LOG.debug("Handle Input: {} {} {} {} {}", window, key, scancode, action, mods);

        // Check for exit
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
            GLFW.glfwSetWindowShouldClose(window, true);
            return;
        }

        // TODO: Have Keymap Registry with all sorts of inputs

    }

}
