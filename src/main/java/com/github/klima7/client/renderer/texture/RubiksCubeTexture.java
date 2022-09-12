package com.github.klima7.client.renderer.texture;

import com.github.klima7.RubiksCubeMod;
import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import net.minecraft.resources.ResourceLocation;

public abstract class RubiksCubeTexture {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 48;

    public static final ResourceLocation TEMPLATE_LOCATION =
            new ResourceLocation(RubiksCubeMod.MODID+":textures/block/rubiks_cube.png");

    public abstract ResourceLocation getResourceLocation();

    public abstract void updateIfNeeded(CubeStickers cubeStickers);

}
