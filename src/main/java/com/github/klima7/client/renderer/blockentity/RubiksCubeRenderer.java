package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.RubiksCubeModel;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class RubiksCubeRenderer extends AbstractRubiksCubeRenderer<RubiksCubeBlockEntity> {

    public RubiksCubeRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new RubiksCubeModel());
    }

}
