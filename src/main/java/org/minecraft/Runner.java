package org.minecraft;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.minecraft.utils.Logging;
import org.minecraft.utils.Settings;

import java.nio.IntBuffer;

/**
 * Game entry point
 */
public class Runner {

    private static final Logger LOG = Logging.get(Runner.class);
    private final Inputs inputs;
    private long window;

    public Runner() {
        this.inputs = new Inputs();
    }

    public static void main(String[] args) {
        new Runner().run();
    }

    public void run() {
        LOG.info("Running...");

        boolean initSuccess = init();
        if (!initSuccess) {
            LOG.error("Error during initialization of the game loop!");
        } else {
            boolean loopSuccess = loop();
            if (!loopSuccess) {
                LOG.error("Error during the game loop!");
            }
        }

        Callbacks.glfwFreeCallbacks(this.window);
        try (GLFWErrorCallback cb = GLFW.glfwSetErrorCallback(null)) {
            if (cb != null) {
                cb.free();
            }
        }

    }

    private boolean init() {
        try (GLFWErrorCallback cb = GLFWErrorCallback.createPrint(System.err)) {
            cb.set();
        }

        if (!GLFW.glfwInit()) {
            LOG.error("Could not initialize GLFW!");
            return false;
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        this.window = GLFW.glfwCreateWindow(Settings.<Integer>get(Settings.SettingKey.WINDOW_WIDTH),
                Settings.<Integer>get(Settings.SettingKey.WINDOW_HEIGHT),
                Settings.<String>get(Settings.SettingKey.WINDOW_NAME), MemoryUtil.NULL, MemoryUtil.NULL);

        if (this.window < 0) {
            LOG.error("Could not create window!");
            return false;
        }

        try (GLFWKeyCallback kcb = GLFW.glfwSetKeyCallback(this.window, this.inputs::handleKey)) {

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1);
                IntBuffer pHeight = stack.mallocInt(1);

                GLFW.glfwGetWindowSize(this.window, pWidth, pHeight);
                GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
                if (vidmode == null) {
                    LOG.error("Could not get video mode for primary monitor!");
                    return false;
                }
                GLFW.glfwSetWindowPos(
                        this.window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );
            }

            GLFW.glfwMakeContextCurrent(this.window);
            GLFW.glfwSwapInterval(1);
            GLFW.glfwShowWindow(this.window);

        }

        return true;
    }

    private boolean loop() {
        GL.createCapabilities();
        GL11C.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        while (!GLFW.glfwWindowShouldClose(this.window)) {
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_DEPTH_BUFFER_BIT);
            GLFW.glfwSwapBuffers(this.window);
            GLFW.glfwPollEvents();
        }

        return true;
    }

}
