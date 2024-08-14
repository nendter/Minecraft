package org.minecraft.rendering;

import org.lwjgl.opengl.GL30;
import org.minecraft.models.Block;
import org.minecraft.models.Chunk;

public class BlockRenderer extends Renderer {


    public void bufferChunk(Chunk chunk) {

        int blockCount = chunk.getBlocks().size();
        int verticesPerBlockCount = Block.MODEL_VERTICES.length;
        int vertexCount = blockCount * verticesPerBlockCount;

        float[] vertices = new float[vertexCount];
        float[] positions = new float[blockCount * 3]; // 3 = x, y, z

        for (int i = 0; i < blockCount; i++) {
            float[] blockVertices = Block.MODEL_VERTICES.clone();
            System.arraycopy(blockVertices, 0, vertices, verticesPerBlockCount * i, blockVertices.length);

            Block block = chunk.getBlocks().get(i);
            positions[3 * i] = block.getC().getX();
            positions[3 * i + 1] = block.getC().getY();
            positions[3 * i + 2] = block.getC().getZ();
        }

        int vertexBufferId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexBufferId);
    }

    @Override
    public void render() {

    }
}
