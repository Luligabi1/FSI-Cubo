package com.github.klima7.client.renderer.texture;

import com.github.klima7.common.domain.cube.stickers.CubeStickers;
import net.minecraft.resources.ResourceLocation;

public class RubiksCubeStaticTexture extends RubiksCubeTexture {

    @Override
    public ResourceLocation getResourceLocation() {
        return TEMPLATE_LOCATION;
    }

    @Override
    public void updateIfNeeded(CubeStickers cubeStickers) {}
}
