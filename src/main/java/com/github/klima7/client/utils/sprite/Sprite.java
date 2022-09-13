package com.github.klima7.client.utils.sprite;

import com.mojang.blaze3d.platform.NativeImage;

public class Sprite {

    private final NativeImage image;

    public Sprite(NativeImage image) {
        this.image = image;
    }

    public void blit(NativeImage screen, int x, int y) {
        for(int shift_y=0; shift_y<image.getHeight(); shift_y++) {
            for(int shift_x=0; shift_x<image.getWidth(); shift_x++) {
                int pixel = image.getPixelRGBA(shift_x, shift_y);
                screen.setPixelRGBA(x+shift_x, y+shift_y, pixel);
            }
        }
    }

}
