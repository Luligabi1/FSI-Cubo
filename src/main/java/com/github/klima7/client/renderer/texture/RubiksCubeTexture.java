package com.github.klima7.client.renderer.texture;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.domain.CubeStickers;
import com.github.klima7.common.domain.FaceStickers;
import com.github.klima7.common.domain.Sticker;
import com.github.klima7.common.domain.StickerFaceLocation;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
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
    private CubeStickers lastCubeStickers;

    public RubiksCubeTexture(int id, TextureManager textureManager, ResourceManager resourceManager) {
        this.texture = new DynamicTexture(WIDTH, HEIGHT, true);
        this.textureResourceLocation = textureManager.register("rubiks_cube/" + id, this.texture);
        drawTemplate(resourceManager);
    }

    public ResourceLocation getResourceLocation() {
        return this.textureResourceLocation;
    }

    public void updateIfNeeded(CubeStickers cubeStickers) {
        if(isUpdateNeeded(cubeStickers))
            update(cubeStickers);
        lastCubeStickers = CubeStickers.copyOf(cubeStickers);
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

    private boolean isUpdateNeeded(CubeStickers cubeStickers) {
        return lastCubeStickers == null || !lastCubeStickers.equals(cubeStickers);
    }

    private void update(CubeStickers cubeStickers) {
        updateFace(cubeStickers.getFaceStickers(Direction.DOWN), 16, 16);
        updateFace(cubeStickers.getFaceStickers(Direction.UP), 48, 16);
        updateFace(cubeStickers.getFaceStickers(Direction.NORTH), 16, 0);
        updateFace(cubeStickers.getFaceStickers(Direction.SOUTH), 16, 32);
        updateFace(cubeStickers.getFaceStickers(Direction.WEST), 32, 16);
        updateFace(cubeStickers.getFaceStickers(Direction.EAST), 0, 16);
        this.texture.upload();
    }

    private void updateFace(FaceStickers faceStickers, int shift_x, int shift_y) {
        for(int sticker_x=0; sticker_x<3; sticker_x++) {
            for(int sticker_y=0; sticker_y<3; sticker_y++) {
                Sticker sticker = faceStickers.getSticker(new StickerFaceLocation(sticker_x, sticker_y));
                int color = sticker.getColor();
                int pos_x = shift_x + 1 + sticker_x * 5;
                int pos_y = shift_y + 1 + sticker_y * 5;
                this.texture.getPixels().fillRect(pos_x, pos_y, 4, 4, color);
            }
        }
    }
}
