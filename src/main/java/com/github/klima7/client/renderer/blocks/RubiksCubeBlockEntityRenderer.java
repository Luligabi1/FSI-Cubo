package com.github.klima7.client.renderer.blocks;

import com.github.klima7.client.model.blockentity.RubiksCubeModel;
import com.github.klima7.common.entities.RubiksCubeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeBlockEntityRenderer extends GeoBlockRenderer<RubiksCubeBlockEntity> {

    private final RubiksCubeTexture texture;

    public RubiksCubeBlockEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
        this.texture = new RubiksCubeTexture();
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeBlockEntity instance) {
        texture.update();
        return texture.getResourceLocation();
    }
}
