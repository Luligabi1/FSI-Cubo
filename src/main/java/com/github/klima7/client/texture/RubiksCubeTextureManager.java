package com.github.klima7.client.texture;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Objects;

public class RubiksCubeTextureManager {

    private static RubiksCubeTextureManager instance;

    private final Int2ObjectMap<RubiksCubeDynamicTexture> rubiksCubes = new Int2ObjectOpenHashMap<>();

    private RubiksCubeTextureManager() {}

    public static RubiksCubeTextureManager getInstance() {
        if(instance == null) {
            instance = new RubiksCubeTextureManager();
        }

        return instance;
    }

    public RubiksCubeTexture getTexture(int id) {
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        ResourceManager resourceManager = minecraft.getResourceManager();

        if(textureManager == null || resourceManager == null) {
            return new RubiksCubeStaticTexture();
        }

        return this.rubiksCubes.compute(id, (rc_id, existing) ->
                Objects.requireNonNullElseGet(existing, () -> new RubiksCubeDynamicTexture(rc_id, textureManager, resourceManager)));
    }

}
