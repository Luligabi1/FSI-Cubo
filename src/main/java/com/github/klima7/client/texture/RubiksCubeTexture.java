package com.github.klima7.client.texture;

import com.github.klima7.client.ClientMod;
import com.github.klima7.client.utils.sprite.Sprite;
import com.github.klima7.common.domain.cube.locations.OnFaceLocation;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import com.github.klima7.common.domain.cube.stickers.FaceStickers;
import com.github.klima7.common.domain.cube.stickers.Sticker;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class RubiksCubeTexture {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    private final DynamicTexture texture;
    private final ResourceLocation textureResourceLocation;
    private CubeStickers lastCubeStickers;

    public RubiksCubeTexture(int id, TextureManager textureManager, ResourceManager resourceManager) {
        this.texture = new DynamicTexture(WIDTH, HEIGHT, true);
        this.textureResourceLocation = textureManager.register("rubiks_cube/" + id, this.texture);
        this.texture.setPixels(FacesImageCreator.createFacesImage(resourceManager));
    }

    public ResourceLocation getResourceLocation() {
        return this.textureResourceLocation;
    }

    public void updateIfNeeded(CubeStickers cubeStickers) {
        if(isUpdateNeeded(cubeStickers)) {
            update(cubeStickers);
        }
        lastCubeStickers = CubeStickers.copyOf(cubeStickers);
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
        StickerSpritesManager stickerSpritesManager = ClientMod.getInstance().getStickerSpritesManager();

        for(int sticker_x=0; sticker_x<3; sticker_x++) {
            for(int sticker_y=0; sticker_y<3; sticker_y++) {
                Sticker sticker = faceStickers.getSticker(new OnFaceLocation(sticker_x, sticker_y));
                Sprite sprite = stickerSpritesManager.getSpriteForSticker(sticker);
                int pos_x = shift_x + 1 + sticker_x * 5;
                int pos_y = shift_y + 1 + sticker_y * 5;
                sprite.blit(this.texture.getPixels(), pos_x, pos_y);
            }
        }
    }

}
