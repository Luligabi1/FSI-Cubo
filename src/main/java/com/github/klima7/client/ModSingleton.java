package com.github.klima7.client;

import com.github.klima7.client.texture.StickerSpritesManager;
import net.minecraft.client.Minecraft;

import java.io.IOException;

public class ModSingleton {

    private static final ModSingleton instance = new ModSingleton();

    private StickerSpritesManager stickerSpritesManager;

    private ModSingleton() {}

    public static ModSingleton getInstance() {
        return instance;
    }

    public void initialize() throws Exception {
        Minecraft minecraft = Minecraft.getInstance();
        stickerSpritesManager = new StickerSpritesManager(minecraft.getResourceManager());
    }

    public StickerSpritesManager getStickerSpritesManager() {
        return stickerSpritesManager;
    }

}
