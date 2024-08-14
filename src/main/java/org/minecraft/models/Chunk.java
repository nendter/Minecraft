package org.minecraft.models;

import java.util.List;
import java.util.UUID;

public class Chunk {

    private final String id;
    private List<Block> blocks;

    public Chunk(List<Block> blocks) {
        this(UUID.randomUUID().toString(), blocks);
    }

    public Chunk(String id, List<Block> blocks) {
        this.id = id;
        this.blocks = blocks;
    }

    public String getId() {
        return id;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}
