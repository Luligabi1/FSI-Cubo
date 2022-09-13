package com.github.klima7.client.utils.sprite;

import com.mojang.blaze3d.platform.NativeImage;

import java.util.ArrayList;
import java.util.List;

public class SpritesImage {

    private final NativeImage image;
    private final int spriteWidth;
    private final int spriteHeight;

    private final List<Sprite> sprites = new ArrayList<>();

    public SpritesImage(NativeImage image, int spriteWidth, int spriteHeight) {
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;

        extractSprites();
    }

    public Sprite getSprite(int index) {
        return sprites.get(index);
    }

    private void extractSprites() {
        int width = image.getWidth();
        int height = image.getHeight();

        if(width % spriteWidth != 0 || height % spriteHeight != 0) {
            throw new IllegalArgumentException("Invalid sizes");
        }

        int x_count = width / spriteWidth;
        int y_count = height / spriteHeight;

        for(int y=0; y<y_count; y++) {
            for(int x=0; x<x_count; x++) {
                Sprite sprite = extractSprite(x, y);
                sprites.add(sprite);
            }
        }
    }

    private Sprite extractSprite(int x, int y) {
        NativeImage copiedImage = new NativeImage(spriteWidth, spriteHeight, true);
        for(int shift_y=0; shift_y<spriteHeight; shift_y++) {
            for(int shift_x=0; shift_x<spriteWidth; shift_x++) {
                copiedImage.setPixelRGBA(shift_x, shift_y, image.getPixelRGBA(x*spriteWidth+shift_x, y*spriteHeight+shift_y));
            }
        }
        return new Sprite(copiedImage);
    }

}
