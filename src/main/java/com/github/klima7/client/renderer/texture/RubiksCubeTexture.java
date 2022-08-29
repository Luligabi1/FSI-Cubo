package com.github.klima7.client.renderer.texture;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.domain.CubeState;
import com.github.klima7.common.domain.FaceState;
import com.github.klima7.common.domain.Sticker;
import com.github.klima7.common.domain.StickerOnFacePos;
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

    private void drawTemplate(ResourceManager resourceManager) {
        try {
            NativeImage template = NativeImage.read(resourceManager.open(TEMPLATE_LOCATION));
            this.texture.setPixels(template);
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private boolean isUpdateNeeded(CubeState cubeState) {
        return lastCubeState == null || !lastCubeState.equals(cubeState);
    }

    private void update(CubeState cubeState) {
        updateFace(cubeState.getFaceState(Sticker.WHITE), 16, 16);
        updateFace(cubeState.getFaceState(Sticker.YELLOW), 48, 16);
        updateFace(cubeState.getFaceState(Sticker.BLUE), 16, 0);
        updateFace(cubeState.getFaceState(Sticker.GREEN), 16, 32);
        updateFace(cubeState.getFaceState(Sticker.RED), 32, 16);
        updateFace(cubeState.getFaceState(Sticker.ORANGE), 0, 16);
        this.texture.upload();
    }

    private void updateFace(FaceState faceState, int shift_x, int shift_y) {
        for(int sticker_x=0; sticker_x<3; sticker_x++) {
            for(int sticker_y=0; sticker_y<3; sticker_y++) {
                Sticker sticker = faceState.getSticker(new StickerOnFacePos(sticker_x, sticker_y));
                int color = sticker.getColor();
                int pos_x = shift_x + 1 + sticker_x * 5;
                int pos_y = shift_y + 1 + sticker_y * 5;
                this.texture.getPixels().fillRect(pos_x, pos_y, 4, 4, color);
            }
        }
    }
}
