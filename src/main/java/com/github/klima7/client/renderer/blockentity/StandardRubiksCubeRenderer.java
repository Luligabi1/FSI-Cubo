package com.github.klima7.client.renderer.blockentity;

import com.github.klima7.client.model.blockentity.StandardRubiksCubeModel;
import com.github.klima7.common.entity.RubiksCubeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class StandardRubiksCubeRenderer extends BaseRubiksCubeRenderer<RubiksCubeBlockEntity> {

    public StandardRubiksCubeRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new StandardRubiksCubeModel());
    }

}
