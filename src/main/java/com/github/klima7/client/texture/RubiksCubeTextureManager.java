package com.github.klima7.client.texture;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Objects;

public class RubiksCubeTextureManager {

    private final TextureManager textureManager;
    private final ResourceManager resourceManager;
    private final Int2ObjectMap<RubiksCubeTexture> rubiksCubes;

    public RubiksCubeTextureManager(TextureManager textureManager, ResourceManager resourceManager) {
        this.textureManager = textureManager;
        this.resourceManager = resourceManager;
        this.rubiksCubes = new Int2ObjectOpenHashMap<>();
    }

    public RubiksCubeTexture getTexture(int id) {
        return this.rubiksCubes.compute(id, (rc_id, existing) ->
                Objects.requireNonNullElseGet(existing, () -> new RubiksCubeTexture(rc_id, textureManager, resourceManager)));
    }

}
