package com.github.klima7.client.renderer.blocks;

import com.github.klima7.client.model.tile.RubiksCubeModel;
import com.github.klima7.common.entities.RubiksCubeTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class RubiksCubeTileRenderer extends GeoBlockRenderer<RubiksCubeTileEntity> {

    public RubiksCubeTileRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new RubiksCubeModel());
    }

}
