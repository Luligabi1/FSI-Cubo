package com.github.klima7.client.renderer.texture;

import com.github.klima7.common.domain.CubeState;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class RubiksCubeTexture {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    private final DynamicTexture texture;
    private final ResourceLocation textureResourceLocation;
    private CubeState lastCubeState;

    public RubiksCubeTexture(int id, TextureManager textureManager) {
        this.texture = new DynamicTexture(WIDTH, HEIGHT, true);
        this.textureResourceLocation = textureManager.register("rubiks_cube/" + id, this.texture);;
    }

    public ResourceLocation getResourceLocation() {
        return this.textureResourceLocation;
    }


    public void updateIfNeeded(CubeState cubeState) {
        if(isUpdateNeeded(cubeState)) {
            update(cubeState);
            lastCubeState = CubeState.fromCubeState(cubeState);
        }
    }

    private boolean isUpdateNeeded(CubeState cubeState) {
        return lastCubeState == null || !lastCubeState.equals(cubeState);
    }

    private void update(CubeState cubeState) {

    }
}
