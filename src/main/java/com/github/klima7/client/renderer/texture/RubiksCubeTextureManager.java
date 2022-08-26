package com.github.klima7.client.renderer.texture;

import com.github.klima7.common.data.RubiksCubeSavedData;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureManager;

import java.util.Objects;

public class RubiksCubeTextureManager {

    private final TextureManager textureManager;
    private final Int2ObjectMap<RubiksCubeTexture> rubiksCubes = new Int2ObjectOpenHashMap<>();

    public RubiksCubeTextureManager(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void update(int id, RubiksCubeSavedData data) {

    }

    public RubiksCubeTexture getTexture(int id) {
        return this.rubiksCubes.compute(id, (rc_id, existing) ->
                Objects.requireNonNullElseGet(existing, () -> new RubiksCubeTexture(rc_id, textureManager)));
    }

}
