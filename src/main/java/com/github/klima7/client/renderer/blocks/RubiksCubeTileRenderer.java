package com.github.klima7.client.renderer.blocks;

import com.github.klima7.client.model.tile.RubiksCubeModel;
import com.github.klima7.common.entities.RubiksCubeTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeTileRenderer extends GeoBlockRenderer<RubiksCubeTileEntity> {

    private final RubiksCubeTexture texture;

    public RubiksCubeTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
        this.texture = new RubiksCubeTexture();
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeTileEntity instance) {
        texture.update();
        return texture.getResourceLocation();
    }
}
