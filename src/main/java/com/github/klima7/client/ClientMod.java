package com.github.klima7.client;

import com.github.klima7.client.texture.RubiksCubeTextureManager;
import com.github.klima7.client.texture.helpers.StickerSpritesManager;
import net.minecraft.client.Minecraft;

public class ClientMod {

    private static final ClientMod instance = new ClientMod();

    private StickerSpritesManager stickerSpritesManager;
    private RubiksCubeTextureManager rubiksCubeTextureManager;

    private ClientMod() {}

    public static ClientMod getInstance() {
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
