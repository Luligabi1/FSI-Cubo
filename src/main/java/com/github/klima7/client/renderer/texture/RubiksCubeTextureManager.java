package com.github.klima7.client.renderer.texture;

import com.github.klima7.common.data.RubiksCubeSavedData;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class RubiksCubeTextureManager {

    private final TextureManager textureManager;
    private final Int2ObjectMap<RubiksCubeInstance> rubiksCubes = new Int2ObjectOpenHashMap<>();

    public RubiksCubeTextureManager(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void update(int id, RubiksCubeSavedData data) {
        this.rubiksCubes.compute(id, (rc_id, existingInstance) -> {
            if (existingInstance == null) {
                return new RubiksCubeInstance(rc_id, data, textureManager);
            } else {
                existingInstance.replaceData(data);
                return existingInstance;
            }
        });
    }

    public ResourceLocation getTexture(int id) {
        return this.rubiksCubes.get(id).getTextureResourceLocation();
    }

}
