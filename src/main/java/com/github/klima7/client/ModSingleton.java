package com.github.klima7.client;

import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.client.texture.StickerSpritesManager;
import net.minecraft.client.Minecraft;

public class ModSingleton {

    private static final ModSingleton instance = new ModSingleton();

    private StickerSpritesManager stickerSpritesManager;
    private RubiksCubeTextureManager rubiksCubeTextureManager;

    private ModSingleton() {}

    public static ModSingleton getInstance() {
        return instance;
    }

    public void initialize() throws Exception {
        Minecraft minecraft = Minecraft.getInstance();

        stickerSpritesManager = new StickerSpritesManager(minecraft.getResourceManager());
        rubiksCubeTextureManager = new RubiksCubeTextureManager(minecraft.getTextureManager(), minecraft.getResourceManager());
    }

    public StickerSpritesManager getStickerSpritesManager() {
        return stickerSpritesManager;
    }

    public RubiksCubeTextureManager getRubiksCubeTextureManager() {
        return rubiksCubeTextureManager;
    }

}
