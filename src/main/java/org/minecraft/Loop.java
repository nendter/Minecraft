package org.minecraft;

import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;
import org.minecraft.utils.Logging;

public class Loop {

    private static final Logger LOG = Logging.get(Inputs.class);
    private final Window window;

    public Loop(Window window) {
        this.window = window;

    }

    public boolean start() {
        GL.createCapabilities();
        setupMatrices();

        while (!GLFW.glfwWindowShouldClose(this.window.getId())) exec();

        return true;
    }

    private void exec() {
        GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT | GL11C.GL_DEPTH_BUFFER_BIT);

        drawTestCube();

        GLFW.glfwSwapBuffers(this.window.getId());
        GLFW.glfwPollEvents();
    }


    private void setupMatrices() {
        GL11C.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11C.glEnable(GL11C.GL_DEPTH_TEST);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        float ratio = Integer.valueOf(this.window.getWidth()).floatValue()
                / Integer.valueOf(this.window.getHeight()).floatValue();

        GL11.glFrustum(-ratio, ratio, -1.0, 1.0, 1.5, 100.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glTranslatef(0.0f, -2.0f, -6.0f);
    }

    private void drawTestCube() {
        GL11.glBegin(GL11C.GL_QUADS);

        // Front face (z = 1.0f)
        GL11.glColor3f(1.0f, 0.0f, 0.0f); // Red
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);

        // Back face (z = -1.0f)
        GL11.glColor3f(0.0f, 1.0f, 0.0f); // Green
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);

        // Top face (y = 1.0f)
        GL11.glColor3f(0.0f, 0.0f, 1.0f); // Blue
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);

        // Bottom face (y = -1.0f)
        GL11.glColor3f(1.0f, 1.0f, 0.0f); // Yellow
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right face (x = 1.0f)
        GL11.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
        GL11.glVertex3f(1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, -1.0f);
        GL11.glVertex3f(1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(1.0f, -1.0f, 1.0f);

        // Left face (x = -1.0f)
        GL11.glColor3f(0.0f, 1.0f, 1.0f); // Cyan
        GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
        GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
        GL11.glVertex3f(-1.0f, 1.0f, -1.0f);

        GL11.glEnd();
    }

}
