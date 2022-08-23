package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.RubiksCubeModel;
import com.github.klima7.client.renderer.texture.RubiksCubeTextureManager;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeBlockEntityRenderer extends GeoBlockRenderer<RubiksCubeBlockEntity> {

    private final RubiksCubeTextureManager textureManager;

    public RubiksCubeBlockEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
        this.textureManager = new RubiksCubeTextureManager(Minecraft.getInstance().getTextureManager());
    }

    @Override
    public ResourceLocation getTextureLocation(RubiksCubeBlockEntity instance) {
        return textureManager.getTexture(0);    // TODO
    }

}
