package org.minecraft;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.minecraft.utils.Logging;
import org.minecraft.utils.Settings;

import java.nio.IntBuffer;

public class Window {
    private static final Logger LOG = Logging.get(Window.class);

    private long id;
    private int width;
    private int height;

    public Window() {
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
    }

    public boolean open() {
        this.width = Settings.<Integer>get(Settings.SettingKey.WINDOW_WIDTH);
        this.height = Settings.<Integer>get(Settings.SettingKey.WINDOW_HEIGHT);
        this.id = GLFW.glfwCreateWindow(width, height, Settings.<String>get(Settings.SettingKey.WINDOW_NAME),
                MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.id < 0) {
            LOG.error("Could not create window!");
            return false;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(this.id, pWidth, pHeight);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (vidmode == null) {
                LOG.error("Could not get video mode for primary monitor!");
                return false;
            }
            GLFW.glfwSetWindowPos(
                    this.id,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(this.id);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(this.id);

        return true;
    }

    public long getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
