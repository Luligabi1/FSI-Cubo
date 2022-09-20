package com.github.klima7.client.texture;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Objects;

public class RubiksCubeTextureManager {

    private final TextureManager textureManager;
    private final ResourceManager resourceManager;
    private final Int2ObjectMap<RubiksCubeTexture> rubiksCubes;
    private RubiksCubeTexture solvedTexture;
    private RubiksCubeTexture stickerlessTexture;

    public RubiksCubeTextureManager(TextureManager textureManager, ResourceManager resourceManager) {
        this.textureManager = textureManager;
        this.resourceManager = resourceManager;
        this.rubiksCubes = new Int2ObjectOpenHashMap<>();
    }

    public RubiksCubeTexture getTexture(int id) {
        return this.rubiksCubes.compute(id, (rc_id, existing) ->
                Objects.requireNonNullElseGet(existing, () -> new RubiksCubeTexture(rc_id, textureManager, resourceManager)));
    }

    public RubiksCubeTexture getSolvedTexture() {
        if(solvedTexture == null) {
            solvedTexture = createSolvedTexture();
        }
        return solvedTexture;
    }

    public RubiksCubeTexture getStickerlessTexture() {
        if(stickerlessTexture == null) {
            stickerlessTexture = createStickerlessTexture();
        }
        return stickerlessTexture;
    }

    private RubiksCubeTexture createSolvedTexture() {
        RubiksCubeTexture texture = new RubiksCubeTexture(0, textureManager, resourceManager);
        texture.updateIfNeeded(CubeStickers.getSolved());
        return texture;
    }

    private RubiksCubeTexture createStickerlessTexture() {
        RubiksCubeTexture texture = new RubiksCubeTexture(0, textureManager, resourceManager);
        texture.updateIfNeeded(CubeStickers.getStickerless());
        return texture;
    }

}
