package com.github.klima7.client.texture;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import net.minecraft.resources.ResourceLocation;

public class RubiksCubeStaticTexture extends RubiksCubeTexture {

    @Override
    public ResourceLocation getResourceLocation() {
        return FACES_LOCATION;
    }

    @Override
    public void updateIfNeeded(CubeStickers cubeStickers) {}
}
