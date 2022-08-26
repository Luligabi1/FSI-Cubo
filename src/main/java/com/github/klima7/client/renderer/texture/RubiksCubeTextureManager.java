package com.github.klima7.client.renderer.texture;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Objects;

public class RubiksCubeTextureManager {

    private final TextureManager textureManager = Minecraft.getInstance().getTextureManager();
    private final ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

    private final Int2ObjectMap<RubiksCubeTexture> rubiksCubes = new Int2ObjectOpenHashMap<>();

    public RubiksCubeTexture getTexture(int id) {
        return this.rubiksCubes.compute(id, (rc_id, existing) ->
                Objects.requireNonNullElseGet(existing, () -> new RubiksCubeTexture(rc_id, textureManager, resourceManager)));
    }

}
