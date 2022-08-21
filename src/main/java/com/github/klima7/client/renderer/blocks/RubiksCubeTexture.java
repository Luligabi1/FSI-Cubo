package com.github.klima7.client.renderer.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class RubiksCubeTexture extends DynamicTexture {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    private final TextureManager textureManager;
    private final int id;
    private ResourceLocation resourceLocation;

    public RubiksCubeTexture() {
        super(WIDTH, HEIGHT, true);
        this.textureManager = Minecraft.getInstance().getTextureManager();
        this.id = 1;
        this.resourceLocation = this.textureManager.register("rubiks_cube/" + this.id, this);
        System.out.println("Location:" + this.resourceLocation.toString());
    }

    public void update() {
        int color = 0xff00ff00;
        getPixels().fillRect(0, 0, WIDTH, HEIGHT, color);
        this.upload();
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}
