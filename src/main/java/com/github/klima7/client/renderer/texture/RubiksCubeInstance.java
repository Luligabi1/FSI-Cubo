package com.github.klima7.client.renderer.texture;

import com.github.klima7.common.data.RubiksCubeSavedData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Objects;

public class RubiksCubeInstance {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    private final DynamicTexture texture;
    private final ResourceLocation textureResourceLocation;
    private RubiksCubeSavedData data;

    public RubiksCubeInstance(int id, RubiksCubeSavedData data, TextureManager textureManager) {
        this.data = data;
        this.texture = new DynamicTexture(WIDTH, HEIGHT, true);
        this.textureResourceLocation = textureManager.register("rubiks_cube/" + id, this.texture);;
    }

    public void replaceData(RubiksCubeSavedData data) {
        boolean isDifferent = this.data != data;
        this.data = data;
        if(isDifferent) {
            updateTexture();
        }
    }

    public ResourceLocation getTextureResourceLocation() {
        return this.textureResourceLocation;
    }

    private void updateTexture() {
        for(int y = 0; y < WIDTH; ++y) {
            for(int j = 0; j < HEIGHT; ++j) {
                int k = j + y * 128;
                Objects.requireNonNull(this.texture.getPixels()).setPixelRGBA(j, y, MaterialColor.getColorFromPackedId(this.data.colors[k]));
            }
        }

        this.texture.upload();
    }

}
