package com.github.klima7.client.renderer.texture;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.domain.CubeState;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class RubiksCubeTexture {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    private static final ResourceLocation TEMPLATE_LOCATION =
            new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");

    private final DynamicTexture texture;
    private final ResourceLocation textureResourceLocation;
    private CubeState lastCubeState;

    public RubiksCubeTexture(int id, TextureManager textureManager, ResourceManager resourceManager) {
        this.texture = new DynamicTexture(WIDTH, HEIGHT, true);
        this.textureResourceLocation = textureManager.register("rubiks_cube/" + id, this.texture);
        drawTemplate(resourceManager);
    }

    public ResourceLocation getResourceLocation() {
        return this.textureResourceLocation;
    }

    public void updateIfNeeded(CubeState cubeState) {
        if(isUpdateNeeded(cubeState))
            update(cubeState);
        lastCubeState = CubeState.fromCubeState(cubeState);
    }

    private boolean isUpdateNeeded(CubeState cubeState) {
        return lastCubeState == null || !lastCubeState.equals(cubeState);
    }

    private void update(CubeState cubeState) {
        this.texture.upload();
    }

    private void drawTemplate(ResourceManager resourceManager) {
        try {
            NativeImage template = NativeImage.read(resourceManager.open(TEMPLATE_LOCATION));
            this.texture.setPixels(template);
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
